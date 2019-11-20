package com.example.delsa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.delsa.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class BuktiDataDiriActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgFotoIdentitas;
    private Button btnAmbilFoto, btnUploadFoto;
    private  Uri resultUri;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private StorageReference photoDataDiriRef;
    private FirebaseUser firebaseUser;
    private DatabaseReference accountReference;
    private String uid;
    private byte[] dataFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bukti_data_diri);

        imgFotoIdentitas = findViewById(R.id.img_fotoIdentitas);
        btnAmbilFoto = findViewById(R.id.btn_ambilFoto);
        btnAmbilFoto.setOnClickListener(this);

        btnUploadFoto = findViewById(R.id.btn_uploadFoto);
        btnUploadFoto.setOnClickListener(this);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        accountReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_ambilFoto:
                dispatchTakePictureIntent();
                break;
            case R.id.btn_uploadFoto:
                storePhotoIdentity(dataFoto);
                break;
        }
    }

    private void storePhotoIdentity(byte[] dataFoto) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();

        photoDataDiriRef = FirebaseStorage.getInstance().getReference().child("images").child("photo_identity").child(uid + ".jpg");
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
                    update_photo.put("fotoIdentitas", url_photo);

                    accountReference.updateChildren(update_photo).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()){
                                Toast.makeText(BuktiDataDiriActivity.this, "Foto identitas telah di upload", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(BuktiDataDiriActivity.this, MainUserActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
//                            pDialog.dismiss();
                                Toast.makeText(BuktiDataDiriActivity.this, "Foto identitas gagal upload", Toast.LENGTH_SHORT).show();
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

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgFotoIdentitas.setImageBitmap(imageBitmap);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            dataFoto = baos.toByteArray();

            btnUploadFoto.setEnabled(true);

            //            resultUri = getImageUri(this, imageBitmap);
        }
    }

//    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }
}
