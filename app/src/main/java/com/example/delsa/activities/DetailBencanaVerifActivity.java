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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailBencanaVerifActivity extends AppCompatActivity {

    public static final String ID_BENCANA = "id_bencana";
    public static final String NAMA_BENCANA = "nama_bencana";
//    public static final String NAMA_USER = "nama_user";
    public static final String FOTO_BENCANA = "foto_bencana";
    public static final String DESKRIPSI_BENCANA = "deskripsi_bencana";
    public static final String LOKASI_BENCANA = "lokasi_bencana";
    public static final String ID_USER = "id_user";

    private TextView tvDetailNamaBencana, tvDetailDeskripsiBencana, tvDetailLokasiBencana, tvDetailNamaUserBencana;
    private ImageView imgDetailFotoBencana;
    private CircleImageView imgFotoUserBencana;
    private DatabaseReference bencanaRef;

    private Button btnTolakBencana, btnTerimaBencana, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bencana_verif);

        final String idBencana = getIntent().getStringExtra(ID_BENCANA);
        String namaBencana = getIntent().getStringExtra(NAMA_BENCANA);
        String fotoBencana = getIntent().getStringExtra(FOTO_BENCANA);
        String deskripsiBencana = getIntent().getStringExtra(DESKRIPSI_BENCANA);
        String lokasiBencana = getIntent().getStringExtra(LOKASI_BENCANA);
        String idUser = getIntent().getStringExtra(ID_USER);

        bencanaRef = FirebaseDatabase.getInstance().getReference().child("Bencana");

        tvDetailNamaBencana = findViewById(R.id.tv_detail_nama_bencana_verif);
        tvDetailDeskripsiBencana = findViewById(R.id.tv_detail_deskripsi_bencana_verif);
        tvDetailLokasiBencana = findViewById(R.id.tv_detail_lokasi_bencana_verif);
        tvDetailNamaUserBencana = findViewById(R.id.tv_detail_bencana_nama_user_verif);

        imgDetailFotoBencana = findViewById(R.id.img_detail_foto_bencana_verif);
        imgFotoUserBencana = findViewById(R.id.civ_detail_bencana_foto_user_verif);

        tvDetailNamaBencana.setText(namaBencana);
        tvDetailDeskripsiBencana.setText(deskripsiBencana);
        tvDetailLokasiBencana.setText(lokasiBencana);

        Picasso.get().load(fotoBencana).into(imgDetailFotoBencana);

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(idUser);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String namaUser = dataSnapshot.child("nama").getValue().toString();
                String fotoUser = dataSnapshot.child("fotoProfil").getValue().toString();
                tvDetailNamaUserBencana.setText(namaUser);
                Picasso.get().load(fotoUser).placeholder(R.drawable.person).into(imgFotoUserBencana);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnTerimaBencana = findViewById(R.id.btn_terima_bencana);
        btnTerimaBencana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map update_status = new HashMap();
                update_status.put("status", true);
                bencanaRef.child(idBencana).updateChildren(update_status).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Toast.makeText(DetailBencanaVerifActivity.this, "Postingan telah diverifikasi", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }
                });
            }
        });

        btnTolakBencana = findViewById(R.id.btn_tolak_bencana);
        btnTolakBencana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bencanaRef.child(idBencana).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(DetailBencanaVerifActivity.this, "Postingan telah ditolak dan dihapus", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }
                });
            }
        });

        btnBack = findViewById(R.id.btn_back_detail_bencana_verif);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
