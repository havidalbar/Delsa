package com.example.delsa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;

public class DonasiUangActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    private Toolbar toolbar;
    private RadioButton radio_transferbank, radio_kartukredit, radio_gopay, radio_jeniuspay;
    private LinearLayout ll_transferbank, ll_kartukredit, ll_gopay, ll_jeniuspay;
    private EditText et_nominaldonasi, et_pesandonasi;
    private Button btn_donasikan;
    private Switch sw_anonim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi_uang);

        toolbar = findViewById(R.id.toolbar_donasi_uang);
        toolbar.setTitle("Donasi Uang");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        radio_transferbank = findViewById(R.id.radio_transferbank);
        radio_kartukredit = findViewById(R.id.radio_kartukredit);
        radio_gopay = findViewById(R.id.radio_gopay);
        radio_jeniuspay = findViewById(R.id.radio_jeniuspay);
        ll_transferbank = findViewById(R.id.ll_transferbank);
        ll_kartukredit = findViewById(R.id.ll_kartukredit);
        ll_gopay = findViewById(R.id.ll_gopay);
        ll_jeniuspay = findViewById(R.id.ll_jeniuspay);
        ll_transferbank.setOnTouchListener(this);
        ll_kartukredit.setOnTouchListener(this);
        ll_gopay.setOnTouchListener(this);
        ll_jeniuspay.setOnTouchListener(this);

        et_nominaldonasi = findViewById(R.id.et_nominaldonasi);
        et_pesandonasi = findViewById(R.id.et_pesandonasi);
        btn_donasikan = findViewById(R.id.btn_donasikan);
        sw_anonim = findViewById(R.id.sw_anonim);

        btn_donasikan.setOnClickListener(this);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radio_transferbank:
                if (checked) {
                    ll_transferbank.setPressed(true);
                    ll_kartukredit.setPressed(false);
                    ll_gopay.setPressed(false);
                    ll_jeniuspay.setPressed(false);
                    break;
                }
            case R.id.radio_kartukredit:
                if (checked) {
                    ll_kartukredit.setPressed(true);
                    ll_transferbank.setPressed(false);
                    ll_gopay.setPressed(false);
                    ll_jeniuspay.setPressed(false);
                    break;
                }
            case R.id.radio_gopay:
                if (checked) {
                    ll_gopay.setPressed(true);
                    ll_transferbank.setPressed(false);
                    ll_kartukredit.setPressed(false);
                    ll_jeniuspay.setPressed(false);
                    break;
                }
            case R.id.radio_jeniuspay:
                if (checked) {
                    ll_jeniuspay.setPressed(true);
                    ll_transferbank.setPressed(false);
                    ll_kartukredit.setPressed(false);
                    ll_gopay.setPressed(false);
                }
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.ll_transferbank:
                radio_transferbank.setChecked(true);
                ll_transferbank.setPressed(true);
                ll_kartukredit.setPressed(false);
                ll_gopay.setPressed(false);
                ll_jeniuspay.setPressed(false);
                break;
            case R.id.ll_kartukredit:
                radio_kartukredit.setChecked(true);
                ll_kartukredit.setPressed(true);
                ll_transferbank.setPressed(false);
                ll_gopay.setPressed(false);
                ll_jeniuspay.setPressed(false);
                break;
            case R.id.ll_gopay:
                radio_gopay.setChecked(true);
                ll_gopay.setPressed(true);
                ll_transferbank.setPressed(false);
                ll_kartukredit.setPressed(false);
                ll_jeniuspay.setPressed(false);
                break;
            case R.id.ll_jeniuspay:
                radio_jeniuspay.setChecked(true);
                ll_jeniuspay.setPressed(true);
                ll_transferbank.setPressed(false);
                ll_kartukredit.setPressed(false);
                ll_gopay.setPressed(false);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_donasikan:

                break;
        }
    }
}
