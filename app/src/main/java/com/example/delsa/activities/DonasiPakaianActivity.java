package com.example.delsa.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.delsa.R;

import static android.view.View.VISIBLE;

public class DonasiPakaianActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private LinearLayout ll_adddonasipakaian, ll_tidakadaform, ll_itemdonasi2;
    private Button btn_adddonasipakaian;

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
        }
    }
}
