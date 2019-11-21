package com.example.delsa.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delsa.R;
import com.example.delsa.activities.MainActivity;
import com.example.delsa.activities.MenuActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment implements View.OnClickListener {

    private LinearLayout ll_settings;
    private Button btn_settings;
    private TextView tv_namaProfil, tv_emailProfil, tv_nomorProfil, tv_logout;
    private CircleImageView civ_photoProfil;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private byte[] dataFoto;

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
        civ_photoProfil = view.findViewById(R.id.civ_profilimage);
        tv_logout.setOnClickListener(this);
        btn_settings.setOnClickListener(this);
        civ_photoProfil.setOnClickListener(this);
        return view;
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
        }
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
        }
    }
}
