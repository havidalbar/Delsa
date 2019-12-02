package com.example.delsa.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delsa.POJO.User;
import com.example.delsa.R;
import com.example.delsa.activities.BuktiDataDiriActivity;
import com.example.delsa.activities.EditProfileActivity;
import com.example.delsa.activities.MainActivity;
import com.example.delsa.activities.MainUserActivity;
import com.example.delsa.activities.MenuActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment implements View.OnClickListener {

    private LinearLayout ll_settings;
    private Button btn_settings;
    private TextView tv_namaProfil, tv_emailProfil, tv_nomorProfil, tv_logout, tv_editprofil, tv_statusProfil,tv_lokasi_profil;
    private CircleImageView civ_photoProfil;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private byte[] dataFoto;
    private FirebaseAuth auth;
    private StorageReference photoDataDiriRef;
    private DatabaseReference accountReference;


    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        ll_settings = view.findViewById(R.id.ll_settings);
        btn_settings = view.findViewById(R.id.btn_setting);
        tv_namaProfil = view.findViewById(R.id.tv_namaprofil);
        tv_emailProfil = view.findViewById(R.id.tv_emailprofil);
        tv_nomorProfil = view.findViewById(R.id.tv_nomorprofil);
        tv_logout = view.findViewById(R.id.tv_LogOut);
        tv_editprofil = view.findViewById(R.id.tv_editProfil);
        civ_photoProfil = view.findViewById(R.id.civ_profilimage);
        tv_statusProfil = view.findViewById(R.id.tv_status_profil);
        tv_lokasi_profil = view.findViewById(R.id.tv_lokasiprofil);
        tv_logout.setOnClickListener(this);
        btn_settings.setOnClickListener(this);
        civ_photoProfil.setOnClickListener(this);
        tv_editprofil.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
        getProfileData();
        accountReference = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getUid());
        return view;
    }

    private void getProfileData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(Objects.requireNonNull(auth.getUid()));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                tv_namaProfil.setText(Objects.requireNonNull(user).getNama());
                tv_emailProfil.setText(user.getEmail());
                tv_lokasi_profil.setText(user.getKota());
                if(user.isStatus()){
                    tv_statusProfil.setText("Terverifikasi");
                }else{
                    tv_statusProfil.setText("Belum Terverifikasi");
                }
                tv_nomorProfil.setText(user.getNoTelephone());
                if (!user.getFotoProfil().equals("")) {
                    Picasso.get().load(user.getFotoProfil()).into(civ_photoProfil);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            civ_photoProfil.setImageBitmap(imageBitmap);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            dataFoto = baos.toByteArray();

            if(dataFoto != null) {
                storePhotoIdentity(dataFoto);
            }
        }
    }

    private void storePhotoIdentity(byte[] dataFoto) {
        String uid = auth.getUid();

        photoDataDiriRef = FirebaseStorage.getInstance().getReference().child("images").child("photo_profile").child(uid + ".jpg");
        UploadTask uploadTask = photoDataDiriRef.putBytes(dataFoto);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return photoDataDiriRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String url_photo = downloadUri.toString();

                    Map update_photo = new HashMap();
                    update_photo.put("fotoProfil", url_photo);

                    accountReference.updateChildren(update_photo).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getContext(), "Foto profil telah di upload", Toast.LENGTH_SHORT).show();
                            }
                            else {
//                            pDialog.dismiss();
                                Toast.makeText(getContext(), "Foto identitas gagal upload", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_setting:
                if (ll_settings.getVisibility() == View.INVISIBLE) {
                    ll_settings.setVisibility(View.VISIBLE);
                } else {
                    ll_settings.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.tv_LogOut:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finishAffinity();
                break;
            case R.id.civ_profilimage:
                dispatchTakePictureIntent();
                break;
            case R.id.tv_editProfil:
                Intent intentEdit = new Intent(getContext(), EditProfileActivity.class);
                startActivity(intentEdit);
        }
    }


}
