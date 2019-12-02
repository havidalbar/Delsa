package com.example.delsa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.delsa.POJO.Bencana;
import com.example.delsa.POJO.DonasiBarang;
import com.example.delsa.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.delsa.activities.BuktiDataDiriActivity.REQUEST_IMAGE_CAPTURE;
import static com.example.delsa.activities.KategoriDonasiActivity.EXTRA_BENCANA;

public class PenjemputanBarangActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String EXTRA_KATEGORI = "kategori";
    public static final String EXTRA_JUMLAH = "jumlah";
    public static final String EXTRA_DESKRIPSI = "deskripsi";

    private Toolbar toolbar;
    private LinearLayout ll_pakaian, ll_makanan;
    private TextView tv_jumlahbarangpakaian, tv_deskripsi_pakaian, tv_jumlahbarangmakanan, tv_deskripsi_makanan, tv_penjemputanbarang;
    private ImageView iv_fotobarangdonasi;
    private EditText et_ket_lokasi, et_pesandonasi;
    private Button btn_donasibarang;
    private Bencana bencana;
    private Switch sw_anonim;

    private StorageReference photoDataDiriRef;
    private FirebaseUser firebaseUser;
    private DatabaseReference accountReference;
    private String uid;
    private byte[] dataFoto;
    private String url_photo;
    private ProgressDialog PD;

    private String kategori;
    private String jumlah;
    private String deskripsi;
    private String koordinat;
    private String nama;

    static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjemputan_barang);

        getCurrentLocation();

        bencana = getIntent().getParcelableExtra(EXTRA_BENCANA);

        toolbar = findViewById(R.id.toolbar_penjemputan_barang);
        toolbar.setTitle("Penjemputan Barang");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ll_pakaian = findViewById(R.id.ll_pakaian);
        ll_makanan = findViewById(R.id.ll_makanan);

        tv_jumlahbarangpakaian = findViewById(R.id.tv_jumlahbarangpakaian);
        tv_jumlahbarangmakanan = findViewById(R.id.tv_jumlahbarangmakanan);
        tv_deskripsi_pakaian = findViewById(R.id.tv_deskripsi_pakaian);
        tv_deskripsi_makanan = findViewById(R.id.tv_deskripsi_makanan);
        tv_penjemputanbarang = findViewById(R.id.tv_penjemputan_barang);
        sw_anonim = findViewById(R.id.sw_anonim);

        iv_fotobarangdonasi = findViewById(R.id.iv_foto_barang);
        iv_fotobarangdonasi.setOnClickListener(this);
        et_ket_lokasi = findViewById(R.id.et_ket_lokasi);
        et_pesandonasi = findViewById(R.id.et_pesandonasi);
        btn_donasibarang = findViewById(R.id.btn_selesai);
        btn_donasibarang.setOnClickListener(this);

        kategori = getIntent().getStringExtra(EXTRA_KATEGORI);
        jumlah = getIntent().getStringExtra(EXTRA_JUMLAH);
        deskripsi = getIntent().getStringExtra(EXTRA_DESKRIPSI);

        Log.d("cek",kategori);
        Log.d("cek",jumlah);
        Log.d("cek",deskripsi);
        String[] array_kategori = kategori.split(";");
        String[] array_jumlah = jumlah.split(";");
        String[] array_deskripsi = deskripsi.split(";");

        if(!array_kategori[0].isEmpty()){
            tv_jumlahbarangpakaian.setText(array_jumlah[0]);
            tv_deskripsi_pakaian.setText(array_deskripsi[0]);
        } else {
            ll_pakaian.setVisibility(View.GONE);
        }
        if(!array_kategori[1].isEmpty()){
            tv_jumlahbarangmakanan.setText(array_jumlah[1]);
            tv_deskripsi_makanan.setText(array_deskripsi[1]);
        } else{
            ll_makanan.setVisibility(View.GONE);
        }

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        accountReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_selesai:
                PD = new ProgressDialog(this);
                PD.setMessage("Loading...");
                PD.setCancelable(true);
                PD.setCanceledOnTouchOutside(false);
                PD.show();
                storePhotoIdentity(dataFoto);
                break;
            case R.id.iv_foto_barang:
                dispatchTakePictureIntent();
                break;
        }
    }

    private void storePhotoIdentity(byte[] dataFoto) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();

        photoDataDiriRef = FirebaseStorage.getInstance().getReference().child("images").child("photo_donation").child(uid + ".jpg");
        UploadTask uploadTask = photoDataDiriRef.putBytes(dataFoto);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return photoDataDiriRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    url_photo = downloadUri.toString();

                    tambahDonasiKeDatabase(url_photo);

                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            iv_fotobarangdonasi.setImageBitmap(imageBitmap);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            dataFoto = baos.toByteArray();

            btn_donasibarang.setEnabled(true);

        }
    }

    private void tambahDonasiKeDatabase(String url_photo) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String key = FirebaseDatabase.getInstance().getReference().child("Donasi Barang").push().getKey();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Donasi Barang").child(key);

        String alamat = et_ket_lokasi.getText().toString();
        DonasiBarang donasiBarang = new DonasiBarang(key,auth.getUid(),bencana.getIdbencana(),kategori,koordinat, alamat,tv_penjemputanbarang.getText().toString(), jumlah,deskripsi,url_photo,getTodayDate(),et_pesandonasi.getText().toString(),nama,sw_anonim.isChecked());
        myRef.setValue(donasiBarang);
        PD.dismiss();
        Intent intent = new Intent(PenjemputanBarangActivity.this, MainUserActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private String getTodayDate(){
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_YEAR, 0);
        Date tomorrow = calendar.getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        String formattedDate = df.format(tomorrow);
        return formattedDate;
    }

    private String getCityAndProvince(Location location) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        String city = null, state = null, knownName = null;
        geocoder = new Geocoder(PenjemputanBarangActivity.this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String latitude = String.valueOf(location.getLatitude());
            String longitude = String.valueOf(location.getLongitude());
            koordinat = latitude+", "+longitude;
            state = addresses.get(0).getAdminArea();
            city = addresses.get(0).getSubAdminArea();

        } catch (IOException e) {
            e.printStackTrace();
        }

        String currentLocation = city +  ", " + state;
        return currentLocation;
    }

    private void getCurrentLocation(){

        final LocationManager locationManager = (LocationManager) PenjemputanBarangActivity.this.getSystemService(Context.LOCATION_SERVICE);

//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        final String userId = currentUser.getUid();

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null){
                    try {
                        tv_penjemputanbarang.setText(getCityAndProvince(location));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PenjemputanBarangActivity.this);

                // set title dialog
                alertDialogBuilder.setTitle("GPS mati");

                // set pesan dari dialog
                alertDialogBuilder
                        .setMessage("Apakah ingin menghidupkan GPS?")
                        .setCancelable(false)
                        .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // jika tombol diklik, maka akan menutup activity ini
                                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        })
                        .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // jika tombol ini diklik, akan menutup dialog
                                // dan tidak terjadi apa2
                                dialog.cancel();
                            }
                        });

                // membuat alert dialog dari builder
                AlertDialog alertDialog = alertDialogBuilder.create();

                // menampilkan alert dialog
                alertDialog.show();
            }
        };

        if (ActivityCompat.checkSelfPermission(PenjemputanBarangActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(PenjemputanBarangActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 50, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5000,50, locationListener);
    }

    private void getName(){
        nama = "";
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(auth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama = dataSnapshot.child("nama").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        getName();
        super.onStart();
    }
}
