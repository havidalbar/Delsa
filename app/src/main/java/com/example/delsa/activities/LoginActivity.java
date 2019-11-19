package com.example.delsa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.delsa.R;
import com.example.delsa.data.dataJawaTimur;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmailLogin, etPasswordLogin;
    private Button btnLogin;
    private TextView tvGoregis;
    private FirebaseAuth auth;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmailLogin = findViewById(R.id.et_emailLogin);
        etPasswordLogin = findViewById(R.id.et_passwordLogin);

        btnLogin = findViewById(R.id.btn_Login);
        btnLogin.setOnClickListener(this);

        tvGoregis = findViewById(R.id.tv_goRegis);
        tvGoregis.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_Login:
                String email = etEmailLogin.getText().toString();
                String password = etPasswordLogin.getText().toString();

                loginUser(email, password);
                break;

            case R.id.tv_goRegis:
                Intent regisIntent = new Intent(this, RegisterActivity.class);
                startActivity(regisIntent);
                break;
        }
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = currentUser.getUid();

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String nama = dataSnapshot.child("nama").getValue().toString();

                            if (checkAdmin(nama)){
                                Intent intent = new Intent(LoginActivity.this, MenuAdminActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else {

                }
            }
        });
    }

    private boolean checkAdmin(String nama) {
        boolean checkKota = false;
        dataJawaTimur dt = new dataJawaTimur();
        for (String kotaJatim : dt.kota){

            if (nama.equalsIgnoreCase(kotaJatim)){
                checkKota = true;
            }
        }
        return checkKota;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null){
            String uid = currentUser.getUid();
            DatabaseReference fotoIdRef;

            fotoIdRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

            fotoIdRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String fotoIdentidas = dataSnapshot.child("fotoIdentitas").getValue().toString();
                    if (fotoIdentidas.equals("")){
                        showAlert();
                    }
                    else {
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Atur Foto identitas sekarang?")
                .setTitle("Perhatian!!");
        builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(LoginActivity.this, BuktiDataDiriActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Keluar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                onBackPressed();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
