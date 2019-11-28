package com.example.delsa.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.delsa.POJO.Bencana;
import com.example.delsa.R;

public class DetailBencanaActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_donasiSekarang;
    private TextView tv_judulbencana, tv_alamatbencana, tv_deskripsibencana;
    private ImageView iv_fotobencana;
    private Bencana bencana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bencana);

        bencana = getIntent().getParcelableExtra("bencana");
        if(bencana != null) {
            Toast.makeText(this, bencana.getKategori() + bencana.getAlamat() + bencana.getJudul(), Toast.LENGTH_SHORT).show();
        }

        tv_judulbencana = findViewById(R.id.tv_judulbencana);
        tv_alamatbencana = findViewById(R.id.tv_alamatbencana);
        tv_deskripsibencana = findViewById(R.id.tv_deskripsibencana);
        iv_fotobencana = findViewById(R.id.iv_fotobencana);

        displayDetailBencana(bencana);

        btn_donasiSekarang = findViewById(R.id.btn_donasiSekarang);
        btn_donasiSekarang.setOnClickListener(this);
    }

    private void displayDetailBencana(Bencana bencana) {
        tv_judulbencana.setText(bencana.getJudul());
        tv_alamatbencana.setText(bencana.getAlamat());
        tv_deskripsibencana.setText(bencana.getDeskripsi());
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
        }
    }
}
