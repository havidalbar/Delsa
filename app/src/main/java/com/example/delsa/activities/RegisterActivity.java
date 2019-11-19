package com.example.delsa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.delsa.POJO.User;
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

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etNamaRegis, etNoRegis, etKotaRegis, etEmailRegis, etPasswordRegis, etRePasswordRegis;
    private Button btnRegister;
    private DatabaseReference databaseReference, createUserRef;
    private String nama, no, kota, email, password, rePassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNamaRegis = findViewById(R.id.et_namaRegister);
        etNoRegis = findViewById(R.id.et_noTelpRegister);
        etKotaRegis = findViewById(R.id.et_kotaRegister);
        etEmailRegis = findViewById(R.id.et_emailRegister);
        etPasswordRegis = findViewById(R.id.et_passwordRegister);
        etRePasswordRegis = findViewById(R.id.et_ulangiPasswordRegister);

        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        auth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                nama = etNamaRegis.getText().toString();
                no = etNoRegis.getText().toString();
                kota = etKotaRegis.getText().toString();
                email = etEmailRegis.getText().toString();
                password = etPasswordRegis.getText().toString();
                rePassword = etRePasswordRegis.getText().toString();

                if (password.equals(rePassword)){
                    if (checkKota(nama)) {
                        adminExist(nama);
                    } else {
                        registerUser(nama, no, kota, email, password);
                    }
                } else {
                    Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private boolean checkKota(String nama) {
        boolean checkKota = false;
        dataJawaTimur dt = new dataJawaTimur();
        for (String kotaJatim : dt.kota){

            if (nama.equalsIgnoreCase(kotaJatim)){
                checkKota = true;
            }
        }
        return checkKota;
    }

    private void adminExist(final String nama) {
        databaseReference.child("Users").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean checkAdmin = false;
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                    if (nama.equalsIgnoreCase(childSnapshot.child("nama").getValue().toString())){
                        Toast.makeText(RegisterActivity.this, "Anda telah terdaftar", Toast.LENGTH_SHORT).show();
                        checkAdmin = true;
                        break;
                    }
                }

                if (!checkAdmin) {
                    registerUser(nama, no, kota, email, password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void registerUser(final String namaUser, final String noUser, final String kotaUser, final String emailUser, String passwordUser) {
        auth.createUserWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String userId = currentUser.getUid();

                    createUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                    User user = new User(namaUser, noUser, kotaUser, emailUser, "", "", false);
                    createUserRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                if (checkKota(namaUser)) {
                                    Intent intent = new Intent(RegisterActivity.this, MenuAdminActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(RegisterActivity.this, BuktiDataDiriActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            } else {
                                Toast.makeText(RegisterActivity.this, "Gagal masukin data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "Gagal buat akun", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
