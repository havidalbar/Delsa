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
import android.widget.Toast;

import com.example.delsa.POJO.Bencana;
import com.example.delsa.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.delsa.activities.KategoriDonasiActivity.EXTRA_BENCANA;
import static com.example.delsa.activities.PenjemputanBarangActivity.EXTRA_DESKRIPSI;
import static com.example.delsa.activities.PenjemputanBarangActivity.EXTRA_JUMLAH;
import static com.example.delsa.activities.PenjemputanBarangActivity.EXTRA_KATEGORI;

public class DonasiPakaianActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private LinearLayout ll_adddonasipakaian, ll_tidakadaform, ll_itemdonasi1, ll_itemdonasi2, btn_hapus1, btn_hapus2;
    private Button btn_adddonasipakaian, btn_lanjutkandonasipakaian;
    private Spinner spn_kategori1, spn_kategori2;
    private EditText et_jumlahitem1, et_jumlahitem2, et_deskripsibarang1, et_deskripsibarang2;
    private Bencana bencana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi_pakaian);

        bencana = getIntent().getParcelableExtra(EXTRA_BENCANA);

        toolbar = findViewById(R.id.toolbar_donasi_pakaian);
        toolbar.setTitle("DonasiUang Pakaian");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ll_adddonasipakaian = findViewById(R.id.ll_add_donasipakaian);
        ll_tidakadaform = findViewById(R.id.ll_tidakadaform);
        ll_itemdonasi1 = findViewById(R.id.item_donasi_1);
        ll_itemdonasi2 = findViewById(R.id.item_donasi_2);
        btn_adddonasipakaian = findViewById(R.id.btn_add_donasipakaian);
        btn_adddonasipakaian.setOnClickListener(this);
        btn_lanjutkandonasipakaian = findViewById(R.id.btn_lanjutkandonasipakaian);
        btn_lanjutkandonasipakaian.setOnClickListener(this);

        btn_hapus1 = findViewById(R.id.ll_hapus1);
        btn_hapus1.setOnClickListener(this);
        btn_hapus2 = findViewById(R.id.ll_hapus2);
        btn_hapus2.setOnClickListener(this);
        spn_kategori1 = findViewById(R.id.spn_kategori1);
        spn_kategori2 = findViewById(R.id.spn_kategori2);

        et_jumlahitem1 = findViewById(R.id.et_jumlahitem1);
        et_jumlahitem2 = findViewById(R.id.et_jumlahitem2);

        et_deskripsibarang1 = findViewById(R.id.et_deskripsi1);
        et_deskripsibarang2 = findViewById(R.id.et_deskripsi2);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_donasipakaian:
                if (ll_tidakadaform.getVisibility() == VISIBLE) {
                    ll_tidakadaform.setVisibility(View.INVISIBLE);
                    ll_adddonasipakaian.setVisibility(VISIBLE);
                    ll_itemdonasi1.setVisibility(VISIBLE);
                    break;
                }
                if (ll_adddonasipakaian.getVisibility() == VISIBLE) {
                    ll_itemdonasi2.setVisibility(VISIBLE);
                } else if (ll_itemdonasi2.getVisibility() == VISIBLE) {
                    ll_itemdonasi1.setVisibility(VISIBLE);
                }
                break;
            case R.id.btn_lanjutkandonasipakaian:
                String kategori = "";
                String jumlah = "";
                String deskripsi = "";
                if (ll_itemdonasi1.getVisibility() == VISIBLE) {
                    kategori = spn_kategori1.getSelectedItem().toString();
                    jumlah = et_jumlahitem1.getText().toString();
                    deskripsi = et_deskripsibarang1.getText().toString();
                }
                if (ll_itemdonasi2.getVisibility() == VISIBLE) {
                    kategori = kategori + ';' + spn_kategori2.getSelectedItem().toString();
                    jumlah = jumlah + ';' + et_jumlahitem2.getText().toString();
                    deskripsi = deskripsi + ';' + et_deskripsibarang2.getText().toString();
                } else {
                    kategori+="; ";
                    jumlah+="; ";
                    deskripsi+="; ";
                }

                if (kategori.equals("Pilih Kategori;") || jumlah.equals(";")) {
                    Toast.makeText(this, "Silahkan isi pilih dan isi terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(DonasiPakaianActivity.this, PenjemputanBarangActivity.class);
                    intent.putExtra(EXTRA_KATEGORI, kategori);
                    intent.putExtra(EXTRA_JUMLAH, jumlah);
                    intent.putExtra(EXTRA_DESKRIPSI, deskripsi);
                    intent.putExtra(EXTRA_BENCANA,bencana);
                    startActivity(intent);
                }
                break;
            case R.id.ll_hapus1:
                ll_itemdonasi1.setVisibility(GONE);
                if (ll_itemdonasi1.getVisibility() == GONE && ll_itemdonasi2.getVisibility() == GONE) {
                    ll_tidakadaform.setVisibility(VISIBLE);
                    ll_adddonasipakaian.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.ll_hapus2:
                ll_itemdonasi2.setVisibility(GONE);
                if (ll_itemdonasi1.getVisibility() == GONE && ll_itemdonasi2.getVisibility() == GONE) {
                    ll_tidakadaform.setVisibility(VISIBLE);
                    ll_adddonasipakaian.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }
}
