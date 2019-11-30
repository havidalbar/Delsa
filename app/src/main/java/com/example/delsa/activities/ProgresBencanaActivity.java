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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProgresBencanaActivity extends AppCompatActivity {

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

    private Button btnUpdateBencana , btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progres_bencana);

        final String idBencana = getIntent().getStringExtra(ID_BENCANA);
        String namaBencana = getIntent().getStringExtra(NAMA_BENCANA);
        String fotoBencana = getIntent().getStringExtra(FOTO_BENCANA);
        String deskripsiBencana = getIntent().getStringExtra(DESKRIPSI_BENCANA);
        String lokasiBencana = getIntent().getStringExtra(LOKASI_BENCANA);
        String idUser = getIntent().getStringExtra(ID_USER);

        bencanaRef = FirebaseDatabase.getInstance().getReference().child("Bencana");

        tvDetailNamaBencana = findViewById(R.id.tv_nama_progres_bencana);
        tvDetailDeskripsiBencana = findViewById(R.id.tv_deskripsi_progres_bencana);
        tvDetailLokasiBencana = findViewById(R.id.tv_lokasi_progres_bencana);
        tvDetailNamaUserBencana = findViewById(R.id.tv_progres_bencana_nama_user);

        imgDetailFotoBencana = findViewById(R.id.img_foto_progres_bencana);
        imgFotoUserBencana = findViewById(R.id.civ_progres_bencana_foto_user);

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
                if(!fotoUser.isEmpty()) {
                    Picasso.get().load(fotoUser).placeholder(R.drawable.person).into(imgFotoUserBencana);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnUpdateBencana  = findViewById(R.id.btn_progres_bencana);
        btnUpdateBencana .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String id = user.getUid();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String kota = dataSnapshot.child("kota").getValue().toString();

                        Map update_status = new HashMap();
                        update_status.put("statusPengiriman", "Sudah tersalurkan");
//                        update_status.put("statuskota", "true_" + kota);
                        bencanaRef.child(idBencana).updateChildren(update_status).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(ProgresBencanaActivity.this, "Pemberian Barang Postingan Ini Sudah Tersalurkan", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        btnBack = findViewById(R.id.btn_back_progres_bencana);
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