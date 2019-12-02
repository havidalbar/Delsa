package com.example.delsa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.delsa.AlarmReceiver;
import com.example.delsa.POJO.User;
import com.example.delsa.R;
import com.example.delsa.fragment.BencanaVerifFragment;
import com.example.delsa.fragment.DataDiriVerifFragment;
import com.example.delsa.fragment.ProfilFragment;
import com.example.delsa.fragment.ProgresBencanaFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class MenuAdminActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_HOUR = "hour";
    private final static int ID_REMINDER = 100;

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
                case R.id.progresbutton:
                    ProgresBencanaFragment progres_bencana = new ProgresBencanaFragment();
                    fragmentTransaction.replace(R.id.main_frame_admin, progres_bencana, "Progres Bencana Fragment");
                    fragmentTransaction.commit();
                    setTitle("Progres Bencana");
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

        auth = FirebaseAuth.getInstance();

        DatabaseReference sewaRef = FirebaseDatabase.getInstance().getReference().child("Users");
        sewaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<User> listuser = new ArrayList<>();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    User mUser = dt.getValue(User.class);
                    if (!mUser.isStatus()) {
                        listuser.add(mUser);
                    }
                }
                showalarm(listuser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showalarm(ArrayList<User> listuser) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, listuser);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), ID_REMINDER, intent, 0);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    60 * 60 * 1000, pendingIntent);
        }
    }
}
