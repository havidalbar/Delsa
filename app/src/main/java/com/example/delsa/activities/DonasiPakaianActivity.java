package com.example.delsa.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.delsa.POJO.Donasi;
import com.example.delsa.R;

import static android.view.View.VISIBLE;

public class DonasiPakaianActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private LinearLayout ll_adddonasipakaian, ll_tidakadaform, ll_itemdonasi2, btn_hapus1, btn_hapus2;
    private Button btn_adddonasipakaian, btn_lanjutkandonasipakaian;
    private Spinner spn_kategori1, spn_kategori2;
    private EditText et_jumlahitem1, et_jumlahitem2, et_deskripsibarang1, et_deskripsibarang2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi_pakaian);

        toolbar = findViewById(R.id.toolbar_donasi_pakaian);
        toolbar.setTitle("Donasi Pakaian");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ll_adddonasipakaian = findViewById(R.id.ll_add_donasipakaian);
        ll_tidakadaform = findViewById(R.id.ll_tidakadaform);
        ll_itemdonasi2 = findViewById(R.id.item_donasi_2);
        btn_adddonasipakaian = findViewById(R.id.btn_add_donasipakaian);
        btn_adddonasipakaian.setOnClickListener(this);
        btn_lanjutkandonasipakaian = findViewById(R.id.btn_lanjutkandonasipakaian);
        btn_lanjutkandonasipakaian.setOnClickListener(this);

        btn_hapus1 = findViewById(R.id.ll_hapus1);
        btn_hapus2 = findViewById(R.id.ll_hapus2);
        spn_kategori1 = findViewById(R.id.spn_kategori1);
        spn_kategori2 = findViewById(R.id.spn_kategori2);

        et_jumlahitem1 = findViewById(R.id.et_jumlahitem1);
        et_jumlahitem2 = findViewById(R.id.et_jumlahitem2);

        et_deskripsibarang1 = findViewById(R.id.et_deskripsi1);
        et_deskripsibarang2 = findViewById(R.id.et_deskripsi2);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_add_donasipakaian:
                if(ll_tidakadaform.getVisibility() == VISIBLE){
                    ll_tidakadaform.setVisibility(View.INVISIBLE);
                    ll_adddonasipakaian.setVisibility(VISIBLE);
                } else if (ll_adddonasipakaian.getVisibility() == VISIBLE){
                    ll_itemdonasi2.setVisibility(VISIBLE);
                }
                break;
            case R.id.btn_lanjutkandonasipakaian:
                Intent intent = new Intent(DonasiPakaianActivity.this, PenjemputanBarangActivity.class);
                startActivity(intent);
        }
    }
}
