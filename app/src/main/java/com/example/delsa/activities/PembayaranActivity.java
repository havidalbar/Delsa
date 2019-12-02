package com.example.delsa.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delsa.POJO.DonasiUang;
import com.example.delsa.R;

public class PembayaranActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private DonasiUang donasiUang;
    private TextView tv_total, tv_nominal, tv_kodeunik, tv_totalharga;
    private Button btn_selesai;
    private TextView tv_salinbri, tv_salinmandiri, tv_salinbni, tv_peringatanpembayaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        donasiUang = getIntent().getParcelableExtra("donasiUang");

        tv_total = findViewById(R.id.tv_total);
        tv_nominal = findViewById(R.id.tv_nominal);
        tv_kodeunik = findViewById(R.id.tv_kodeunik);
        tv_totalharga = findViewById(R.id.tv_totalharga);
        btn_selesai = findViewById(R.id.btn_selesai);
        tv_salinbri = findViewById(R.id.btn_salinbri);
        tv_salinmandiri = findViewById(R.id.btn_salinmandiri);
        tv_salinbni = findViewById(R.id.btn_salinbni);
        tv_peringatanpembayaran = findViewById(R.id.tv_peringatanpembayaran);

        tv_salinbri.setOnClickListener(this);
        tv_salinmandiri.setOnClickListener(this);
        tv_salinbni.setOnClickListener(this);
        btn_selesai.setOnClickListener(this);
        displayData();

        toolbar = findViewById(R.id.toolbar_donasi_uang);
        toolbar.setTitle("Pembayaran");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void displayData() {
        tv_total.setText("Rp " + donasiUang.getTotalpembayaran());
        tv_nominal.setText("Rp " + donasiUang.getNominal());
        tv_kodeunik.setText("Rp " + donasiUang.getKodeunik());
        tv_totalharga.setText("Rp " + donasiUang.getTotalpembayaran());
        tv_peringatanpembayaran.setText("Lakukan pembayaran sebelum "+ donasiUang.getBataspembayaran()+" 18.00 WIB sebelum donasi dibatalkan oleh sistem");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_selesai:
                Intent intent = new Intent(PembayaranActivity.this,MainUserActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_salinbni:
                rekeningToClipboard("90132872389");
                Toast.makeText(this, "Nomor rekening telah dimasukan kedalam clipboard", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_salinmandiri:
                rekeningToClipboard("54129219192");
                Toast.makeText(this, "Nomor rekening telah dimasukan kedalam clipboard", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_salinbri:
                rekeningToClipboard("32198713298");
                Toast.makeText(this, "Nomor rekening telah dimasukan kedalam clipboard", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void rekeningToClipboard(String rekening){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Rekening", rekening);
        clipboard.setPrimaryClip(clip);
    }

}
