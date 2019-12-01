package com.example.delsa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.delsa.POJO.Bencana;
import com.example.delsa.POJO.User;
import com.example.delsa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class DetailBencanaActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_donasiSekarang, btn_back;
    private TextView tv_judulbencana, tv_alamatbencana, tv_deskripsibencana, tv_namaprofil,tv_status_bencana;
    private ImageView iv_fotobencana;
    private Bencana bencana;
    private CircleImageView civ_fotoprofil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bencana);

        bencana = getIntent().getParcelableExtra("bencana");

        tv_judulbencana = findViewById(R.id.tv_judulbencana);
        tv_alamatbencana = findViewById(R.id.tv_alamatbencana);
        tv_deskripsibencana = findViewById(R.id.tv_deskripsibencana);
        tv_status_bencana = findViewById(R.id.tv_status_bencana);
        iv_fotobencana = findViewById(R.id.iv_fotobencana);
        tv_namaprofil = findViewById(R.id.tv_namaprofil);
        civ_fotoprofil = findViewById(R.id.civ_profilimage);

        displayDetailBencana(bencana);
        getProfileData();

        btn_donasiSekarang = findViewById(R.id.btn_donasiSekarang);
        btn_back = findViewById(R.id.btn_back);
        btn_donasiSekarang.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    private void displayDetailBencana(Bencana bencana) {
        tv_judulbencana.setText(bencana.getJudul());
        tv_alamatbencana.setText(bencana.getAlamat());
        tv_deskripsibencana.setText(bencana.getDeskripsi());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();

        DatabaseReference userInfo = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        userInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String status = dataSnapshot.child("status").getValue().toString();
                if(!Boolean.valueOf(status)){
                    View btn_donasi = findViewById(R.id.btn_donasiSekarang);
                    btn_donasi.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(bencana.isStatus()){
            tv_status_bencana.setText("Terverifikasi");
        }else{
            tv_status_bencana.setText("Belum Terverifikasi");
        }
        Glide.with(this).load(bencana.getFotoBencana()).into(iv_fotobencana);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_donasiSekarang:
                Intent intent = new Intent(DetailBencanaActivity.this, KategoriDonasiActivity.class);
                intent.putExtra("bencana",bencana);
                startActivity(intent);
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
        }
    }

    private void getProfileData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(Objects.requireNonNull(bencana.getIdUser()));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                tv_namaprofil.setText(Objects.requireNonNull(user).getNama());
                if (!user.getFotoProfil().equals("")) {
                    Glide.with(getApplicationContext()).load(user.getFotoProfil()).into(civ_fotoprofil);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
