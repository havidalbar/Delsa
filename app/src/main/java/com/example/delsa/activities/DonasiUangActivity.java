package com.example.delsa.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.delsa.POJO.Bencana;
import com.example.delsa.POJO.DonasiUang;
import com.example.delsa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.delsa.activities.KategoriDonasiActivity.EXTRA_BENCANA;

public class DonasiUangActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    private Toolbar toolbar;
    private RadioButton radio_transferbank, radio_kartukredit, radio_gopay, radio_jeniuspay;
    private LinearLayout ll_transferbank, ll_kartukredit, ll_gopay, ll_jeniuspay;
    private EditText et_nominaldonasi, et_pesandonasi;
    private Button btn_donasikan;
    private Switch sw_anonim;
    private Bencana bencana;
    private int random;
    private DonasiUang donasiUang;
    private String key;
    private ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi_uang);

        bencana = getIntent().getParcelableExtra(EXTRA_BENCANA);

        random = (int) (Math.random() * (999 - 0));

        toolbar = findViewById(R.id.toolbar_donasi_uang);
        toolbar.setTitle("DonasiUang Uang");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        et_nominaldonasi = findViewById(R.id.et_nominaldonasi);
        et_pesandonasi = findViewById(R.id.et_pesandonasi);

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
                String metode ="";
                if(radio_transferbank.isChecked()){
                    metode = "Transfer Bank";
                } else if (radio_kartukredit.isChecked()){
                    metode = "Kartu Kredit";
                } else if (radio_gopay.isChecked()){
                    metode = "GO-PAY";
                } else if (radio_jeniuspay.isChecked()){
                    metode = "Jenius Pay";
                } else{
                    Toast.makeText(this, "Silahkan Pilih Metode Pembayaran", Toast.LENGTH_SHORT).show();
                }

                if(!metode.equals("")) {
                    PD = new ProgressDialog(this);
                    PD.setMessage("Loading...");
                    PD.setCancelable(true);
                    PD.setCanceledOnTouchOutside(false);
                    PD.show();
                    donasiBencanaKeDatabase(bencana,et_nominaldonasi.getText().toString(),et_pesandonasi.getText().toString(),metode,sw_anonim.isChecked());
                    Intent intent = new Intent(DonasiUangActivity.this,PembayaranActivity.class);
                    intent.putExtra("donasiUang", donasiUang);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    private void donasiBencanaKeDatabase(Bencana bencana, String nominaldonasi, String pesan, String metode, boolean anonim) {

        int nominal = Integer.parseInt(nominaldonasi);
        int total = nominal + random;

        FirebaseAuth auth = FirebaseAuth.getInstance();
        key = FirebaseDatabase.getInstance().getReference().child("Donasi Uang").push().getKey();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Donasi Uang").child(key);

        donasiUang = new DonasiUang(key,auth.getUid(),bencana.getIdbencana(),String.valueOf(nominal),String.valueOf(random),String.valueOf(total),metode,pesan,getTomorrowDate(),getTodayDate(),anonim,false);
        myRef.setValue(donasiUang);
        PD.dismiss();
    }

    private String getTomorrowDate(){
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        String formattedDate = df.format(tomorrow);
        return formattedDate;
    }

    private String getTodayDate(){
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 0);
        Date tomorrow = calendar.getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        String formattedDate = df.format(tomorrow);
        return formattedDate;
    }
}
