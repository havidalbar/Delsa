package com.example.delsa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.delsa.R;
import com.example.delsa.fragment.HomeFragment;
import com.example.delsa.fragment.NotifikasiFragment;
import com.example.delsa.fragment.ProfilFragment;
import com.example.delsa.fragment.RiwayatFragment;
import com.example.delsa.fragment.TambahFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainUserActivity extends AppCompatActivity {

    private final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.homebutton:
                    HomeFragment homeFragment = new HomeFragment();
                    fragmentTransaction.replace(R.id.main_frame, homeFragment, "Home Fragment");
                    fragmentTransaction.commit();
                    setTitle("Home");
                    return true;
                case R.id.riwayatbutton:
                    RiwayatFragment riwayatFragment = new RiwayatFragment();
                    fragmentTransaction.replace(R.id.main_frame, riwayatFragment, "Riwayat Fragment");
                    fragmentTransaction.commit();
                    setTitle("Riwayat");
                    return true;
                case R.id.tambahbutton:
                    TambahFragment tambahFragment = new TambahFragment();
                    fragmentTransaction.replace(R.id.main_frame, tambahFragment, "Tambah Fragment");
                    fragmentTransaction.commit();
                    setTitle("Tambah");
                    return true;
//                case R.id.notifikasibutton:
//                    NotifikasiFragment notifikasiFragment = new NotifikasiFragment();
//                    fragmentTransaction.replace(R.id.main_frame, notifikasiFragment, "Notifikasi Fragment");
//                    fragmentTransaction.commit();
//                    setTitle("Notifikasi");
//                    return true;
                case R.id.profilebutton:
                    ProfilFragment profilFragment = new ProfilFragment();
                    fragmentTransaction.replace(R.id.main_frame, profilFragment, "Profil Fragment");
                    fragmentTransaction.commit();
                    setTitle("Profil");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.replace(R.id.main_frame, homeFragment, "Home Fragment");
        fragmentTransaction.commit();
        setTitle("Home");
    }

}
