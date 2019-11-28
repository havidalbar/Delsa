package com.example.delsa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.delsa.R;
import com.example.delsa.fragment.BencanaVerifFragment;
import com.example.delsa.fragment.DataDiriVerifFragment;
import com.example.delsa.fragment.ProfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MenuAdminActivity extends AppCompatActivity {

    private Button btnLogout;

    private final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()) {
                case R.id.homebutton:
                    DataDiriVerifFragment dataDiriVerifFragment = new DataDiriVerifFragment();
                    fragmentTransaction.replace(R.id.main_frame_admin, dataDiriVerifFragment, "Data Diri Verif Fragment");
                    fragmentTransaction.commit();
                    setTitle("Data diri");
                    return true;
                case R.id.riwayatbutton:
                    BencanaVerifFragment bencanaVerifFragment = new BencanaVerifFragment();
                    fragmentTransaction.replace(R.id.main_frame_admin, bencanaVerifFragment, "Bencana Verif Fragment");
                    fragmentTransaction.commit();
                    setTitle("Bencana");

                    return true;
                case R.id.profilebutton:
                    ProfilFragment profilFragment = new ProfilFragment();
                    fragmentTransaction.replace(R.id.main_frame_admin, profilFragment, "Profil Fragment");
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
        setContentView(R.layout.activity_menu_admin);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_admin);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DataDiriVerifFragment dataDiriVerifFragment = new DataDiriVerifFragment();
        fragmentTransaction.replace(R.id.main_frame_admin, dataDiriVerifFragment, "Data Diri Verif Fragment");
        fragmentTransaction.commit();
        setTitle("Data diri");



//        btnLogout = findViewById(R.id.btn_logoutAdmin);
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                Toast.makeText(MenuAdminActivity.this, "Logout", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MenuAdminActivity.this, MainActivity.class);
//                startActivity(intent);
//                finishAffinity();
//            }
//        });
    }
}
