package com.example.delsa.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.delsa.R;

public class PenjemputanBarangActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjemputan_barang);

        Spinner pakaianSpinner = (Spinner) findViewById(R.id.spinner_penjemputan_pakaian);
        ArrayAdapter<String> pakaianAdapter = new ArrayAdapter<String>(PenjemputanBarangActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.penjemputan_barang_pakaian));
        pakaianAdapter.setDropDownViewResource(R.array.penjemputan_barang_pakaian);
        pakaianSpinner.setAdapter(pakaianAdapter);

        Spinner makananSpinner = (Spinner) findViewById(R.id.spinner_penjemputan_makanan);
        ArrayAdapter<String> makananAdapter = new ArrayAdapter<String>(PenjemputanBarangActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.penjemputan_barang_makanan));
        pakaianAdapter.setDropDownViewResource(R.array.penjemputan_barang_makanan);
        pakaianSpinner.setAdapter(makananAdapter);
    }
}
