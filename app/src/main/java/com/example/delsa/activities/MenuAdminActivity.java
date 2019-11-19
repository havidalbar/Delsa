package com.example.delsa.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.delsa.R;
import com.google.firebase.auth.FirebaseAuth;

public class MenuAdminActivity extends AppCompatActivity {

    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        btnLogout = findViewById(R.id.btn_logoutAdmin);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MenuAdminActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MenuAdminActivity.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
}
