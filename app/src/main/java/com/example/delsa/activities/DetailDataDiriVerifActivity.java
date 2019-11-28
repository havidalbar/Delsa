package com.example.delsa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delsa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class DetailDataDiriVerifActivity extends AppCompatActivity {

    public static final String ID = "id_user";
    public static final String NAMA = "nama_user";
    public static final String KOTA = "kota_user";
    public static final String NO_TELP = "no_telp";
    public static final String EMAIL = "email";
//    public static final String FOTO_PROFILE = "foto_profile";
    public static final String FOTO_ID = "foto_identitas";

    private DatabaseReference konfirmasiRef;

    private TextView tvNama, tvNo, tvKota, tvEmail;
    private ImageView imgFotoId;
    private Button btnAccept;
    private Button btnDecline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data_diri_verif);

        final String id = getIntent().getStringExtra(ID);
        String nama = getIntent().getStringExtra(NAMA);
        String kota = getIntent().getStringExtra(KOTA);
        String noTelp = getIntent().getStringExtra(NO_TELP);
        String email = getIntent().getStringExtra(EMAIL);
        String foto = getIntent().getStringExtra(FOTO_ID);

        konfirmasiRef = FirebaseDatabase.getInstance().getReference().child("Users");

        btnAccept = findViewById(R.id.btn_terima);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map update_status = new HashMap();
                update_status.put("status", true);
                konfirmasiRef.child(id).updateChildren(update_status).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Toast.makeText(DetailDataDiriVerifActivity.this, "Akun baru telah diaktifkan", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }
                });
            }
        });

        btnDecline = findViewById(R.id.btn_tolak);
        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map update_status = new HashMap();
                update_status.put("status", "");
                konfirmasiRef.child(id).updateChildren(update_status).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Toast.makeText(DetailDataDiriVerifActivity.this, "Akun baru ditolak", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }
                });
            }
        });

        tvNama = findViewById(R.id.tv_nama_detail_verif);
        tvNo = findViewById(R.id.tv_no_detail_verif);
        tvKota = findViewById(R.id.tv_kota_detail_verif);
        tvEmail = findViewById(R.id.tv_email_detail_verif);
        imgFotoId = findViewById(R.id.img_fotoId_detail_verif);

        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        tvNama.setText(nama);
        tvNo.setText(noTelp);
        tvKota.setText(kota);
        tvEmail.setText(email);

        Picasso.get().load(foto).into(imgFotoId);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
