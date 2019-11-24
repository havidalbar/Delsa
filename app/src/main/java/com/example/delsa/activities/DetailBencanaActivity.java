package com.example.delsa.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.delsa.R;

public class DetailBencanaActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_donasiSekarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bencana);

        btn_donasiSekarang = findViewById(R.id.btn_donasiSekarang);
        btn_donasiSekarang.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_donasiSekarang:
                Intent intent = new Intent(DetailBencanaActivity.this, KategoriDonasiActivity.class);
                startActivity(intent);
                break;
        }
    }
}
