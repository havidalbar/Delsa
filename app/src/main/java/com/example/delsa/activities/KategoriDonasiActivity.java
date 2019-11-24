package com.example.delsa.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.delsa.R;

public class KategoriDonasiActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private LinearLayout ll_donasipakaian, ll_donasiuang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_donasi);

        toolbar = findViewById(R.id.toolbar_donasi);
        toolbar.setTitle("Donasi");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ll_donasipakaian = findViewById(R.id.ll_donasipakaian);
        ll_donasiuang = findViewById(R.id.ll_donasiuang);
        ll_donasipakaian.setOnClickListener(this);
        ll_donasiuang.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ll_donasipakaian:
                Intent intent = new Intent(KategoriDonasiActivity.this, DonasiPakaianActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_donasiuang:
                Intent intentUang = new Intent(KategoriDonasiActivity.this, DonasiUangActivity.class);
                startActivity(intentUang);
                break;
        }
    }
}
