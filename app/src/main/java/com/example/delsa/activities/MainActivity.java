package com.example.delsa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delsa.POJO.User;
import com.example.delsa.R;
import com.example.delsa.data.dataJawaTimur;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button goLogin, goRegis;
    private LinearLayout llLogin, llregis, llregiscompletedata;
    private BottomSheetBehavior bottomSheetBehaviorLogin, bottomSheetBehaviorRegis, bottomSheetBehaviorRegisCompleteData;
    private EditText etEmailLogin, etPasswordLogin;
    private Button btnLogin, btnLoginGoogle;
    private TextView tvGoregis, tvGologin;
    private FirebaseAuth auth;
    private EditText etNamaRegis, etNoRegis, etKotaRegis, etEmailRegis, etPasswordRegis, etRePasswordRegis;
    private Button btnRegisternext, btnRegister;
    private DatabaseReference databaseReference, createUserRef;
    private String nama, no, kota, email, password, rePassword;
    private ProgressDialog PD;
    private GoogleSignInClient mGoogleSignInClient;

    public final int RC_SIGN_IN = 9001;
    public final String TAG = "GoogleActivity";

    private GoogleSignInAccount account;
    private boolean checkLoginGoogleExixst = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goLogin = findViewById(R.id.btn_goLogin);
        goRegis = findViewById(R.id.btn_goRegis);
        llLogin = findViewById(R.id.bottom_sheet_login);
        llregis = findViewById(R.id.bottom_sheet_regis);
        llregiscompletedata = findViewById(R.id.bottom_sheet_regisdata);
        bottomSheetBehaviorLogin = BottomSheetBehavior.from(llLogin);
        bottomSheetBehaviorRegis = BottomSheetBehavior.from(llregis);
        bottomSheetBehaviorRegisCompleteData = BottomSheetBehavior.from(llregiscompletedata);
        goLogin.setOnClickListener(this);
        goRegis.setOnClickListener(this);

        etEmailLogin = findViewById(R.id.et_emailLogin);
        etPasswordLogin = findViewById(R.id.et_passwordLogin);

        btnLogin = findViewById(R.id.btn_Login);
        btnLogin.setOnClickListener(this);

        btnLoginGoogle = findViewById(R.id.btn_Google);
        btnLoginGoogle.setOnClickListener(this);

        tvGoregis = findViewById(R.id.tv_goRegis);
        tvGoregis.setOnClickListener(this);

        tvGologin = findViewById(R.id.tv_goLogin);
        tvGologin.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

        etNamaRegis = findViewById(R.id.et_namaRegister);
        etNoRegis = findViewById(R.id.et_noTelpRegister);
        etKotaRegis = findViewById(R.id.et_kotaRegister);
        etEmailRegis = findViewById(R.id.et_emailRegister);
        etPasswordRegis = findViewById(R.id.et_passwordRegister);
        etRePasswordRegis = findViewById(R.id.et_ulangiPasswordRegister);

        btnRegisternext = findViewById(R.id.btn_registernext);
        btnRegisternext.setOnClickListener(this);

        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_goLogin:
                bottomSheetBehaviorLogin.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.btn_Google:
                loginGoogle();
                break;
            case R.id.btn_goRegis:
                bottomSheetBehaviorRegis.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.btn_Login:
                email = etEmailLogin.getText().toString();
                password = etPasswordLogin.getText().toString();
                if (!(email.equals("") && password.equals(""))){
                    Log.d("Cek", email + password);
                    loginUser(email, password);
                } else {
                    Toast.makeText(this, "Silahkan lengkapi data", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_goRegis:
                bottomSheetBehaviorLogin.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomSheetBehaviorRegis.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.tv_goLogin:
                bottomSheetBehaviorRegis.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomSheetBehaviorLogin.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.btn_registernext:

                email = etEmailRegis.getText().toString();
                password = etPasswordRegis.getText().toString();
                rePassword = etRePasswordRegis.getText().toString();
                if (!(email.equals("") && password.equals("") && rePassword.equals(""))) {
                    if (password.equals(rePassword)) {
                        bottomSheetBehaviorRegis.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        bottomSheetBehaviorRegisCompleteData.setState(BottomSheetBehavior.STATE_EXPANDED);
                    } else {
                        Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Silahkan lengkapi data terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_register:
                PD = new ProgressDialog(MainActivity.this);
                PD.setMessage("Loading...");
                PD.setCancelable(true);
                PD.setCanceledOnTouchOutside(false);
                PD.show();
                email = etEmailRegis.getText().toString();
                password = etPasswordRegis.getText().toString();
                rePassword = etRePasswordRegis.getText().toString();
                if (!(email.equals("") && password.equals("") && rePassword.equals(""))) {
                    nama = etNamaRegis.getText().toString();
                    no = etNoRegis.getText().toString();
                    kota = etKotaRegis.getText().toString();

                    if (!(nama.equals("") && no.equals("") && kota.equals(""))){
                        if (checkKota(nama)) {
                            adminExist(nama);
                        } else {
                            registerUser(nama, no, kota, email, password);
                        }
                    } else {
                        Toast.makeText(this, "Silahkan lengkapi data terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    nama = etNamaRegis.getText().toString();
                    no = etNoRegis.getText().toString();
                    kota = etKotaRegis.getText().toString();
                    if (!(nama.equals("") && no.equals("") && kota.equals(""))){
                        firebaseAuthWithGoogle(account);
                    }else{
                        Toast.makeText(this, "Silahkan lengkapi data terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }




                }
                PD.dismiss();

        }
    }

    private void loginUser(String email, String password) {
        PD = new ProgressDialog(MainActivity.this);
        PD.setMessage("Loading...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);
        PD.show();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = currentUser.getUid();

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String nama = dataSnapshot.child("nama").getValue().toString();
                            Toast.makeText(MainActivity.this, nama, Toast.LENGTH_LONG).show();

                            if (checkAdmin(nama)) {
                                Intent intent = new Intent(MainActivity.this, MenuAdminActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(MainActivity.this, MainUserActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    PD.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                    PD.dismiss();
                }
            }
        });
    }

    private void loginGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                account = task.getResult(ApiException.class);

                Query databaseReference =  FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("email").equalTo(account.getEmail().toString());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()){
                            bottomSheetBehaviorLogin.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            bottomSheetBehaviorRegisCompleteData.setState(BottomSheetBehavior.STATE_EXPANDED);
//                            firebaseAuthWithGoogle(account);
                        } else {
                            checkLoginGoogleExixst = true;
                            PD = new ProgressDialog(MainActivity.this);
                            PD.setMessage("Loading...");
                            PD.setCancelable(true);
                            PD.setCanceledOnTouchOutside(false);
                            PD.show();
                            firebaseAuthWithGoogle(account);
                            bottomSheetBehaviorLogin.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (checkLoginGoogleExixst){
                                Intent intent = new Intent(MainActivity.this, MainUserActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                FirebaseUser user = auth.getCurrentUser();

                                email = user.getEmail().toString();

                                writeUser(nama, no, kota, email);
                            }

                        } else {
                            Toast.makeText(MainActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private boolean checkAdmin(String nama) {
        boolean checkKota = false;
        dataJawaTimur dt = new dataJawaTimur();
        for (String kotaJatim : dt.kota) {

            if (nama.equalsIgnoreCase(kotaJatim)) {
                checkKota = true;
            }
        }
        return checkKota;
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference fotoIdRef;

            fotoIdRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

            fotoIdRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String fotoIdentidas = dataSnapshot.child("fotoIdentitas").getValue().toString();
                    if (fotoIdentidas.equals("")) {
                        showAlert();
                    } else {
                        Intent intent = new Intent(MainActivity.this, MainUserActivity.class);
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

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Atur Foto identitas sekarang?")
                .setTitle("Perhatian!!");
        builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(MainActivity.this, BuktiDataDiriActivity.class);
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

    private boolean checkKota(String nama) {
        boolean checkKota = false;
        dataJawaTimur dt = new dataJawaTimur();
        for (String kotaJatim : dt.kota) {

            if (nama.equalsIgnoreCase(kotaJatim)) {
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
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    if (nama.equalsIgnoreCase(childSnapshot.child("nama").getValue().toString())) {
                        Toast.makeText(MainActivity.this, "Anda telah terdaftar", Toast.LENGTH_SHORT).show();
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
                if (task.isSuccessful()) {
                    writeUser(namaUser, noUser, kotaUser, emailUser);
                } else {
                    Toast.makeText(MainActivity.this, "Gagal buat akun", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehaviorLogin.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehaviorLogin.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else if (bottomSheetBehaviorRegis.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehaviorRegis.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    private void writeUser(final String namaUser, String noUser, String kotaUser, String emailUser){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();

        createUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        User user = new User(namaUser, noUser, kotaUser, emailUser, "", "", false);
        createUserRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (checkKota(namaUser)) {
                        Intent intent = new Intent(MainActivity.this, MenuAdminActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(MainActivity.this, BuktiDataDiriActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Gagal masukin data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        PD.dismiss();
//        Intent intent = new Intent(MainActivity.this,BuktiDataDiriActivity.class);
//        startActivity(intent);
//        finish();
    }


}
