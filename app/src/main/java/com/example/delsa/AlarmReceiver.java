package com.example.delsa;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.delsa.POJO.Bencana;
import com.example.delsa.POJO.User;
import com.example.delsa.activities.MenuAdminActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String TYPE_NEWUSER = "NewUser";
    public static final String TYPE_NEWBENCANA = "NewBencana";
    private static final String EXTRA_TYPE = "type";


    private final static int ID_NEWUSER = 100;
    private final static int ID_NEWBENCANA = 101;

    private int idNotifUser;
    private int idNotifBencana;

    @Override
    public void onReceive(final Context context, Intent intent) {
        idNotifUser = 1;
        idNotifBencana = 2;
        String type = intent.getStringExtra(EXTRA_TYPE);
        if (type.equalsIgnoreCase(TYPE_NEWUSER)) {
            checkNewUsers(new NotifikasiCallback() {
                @Override
                public void onSuccess(int newUser) {
                    if (newUser != 0) {
                        showAlarmNotification(context, "User Baru", "Ada " + newUser + " user yang belum diverifikasi", idNotifUser);
                    }
                }

                @Override
                public void onError(boolean failure) {

                }
            });
        } else if (type.equalsIgnoreCase(TYPE_NEWBENCANA)) {
            checkNewBencana(new NotifikasiCallback() {
                @Override
                public void onSuccess(int newUser) {
                    int newBencana = newUser;
                    if (newBencana != 0) {
                        showAlarmNotification(context, "Bencana Baru", "Ada " + newBencana + " bencana yang belum diverifikasi", idNotifBencana);
                    }
                }

                @Override
                public void onError(boolean failure) {

                }
            });
        }
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmManager channel";

        Intent intent = new Intent(context.getApplicationContext(), MenuAdminActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }

    }

    public void setNewUsersAndBencanaAlarm(Context context, String type) {
        Log.d("cek", "masuk setrepeating" + type);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_NEWUSER, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1 * 60 * 1000, pendingIntent);
        }
        Toast.makeText(context, "Reminder notif on", Toast.LENGTH_SHORT).show();
    }


    private void checkNewUsers(final NotifikasiCallback notifikasiCallback) {
        Log.d("cek", "masuk checknewusers");
        DatabaseReference sewaRef = FirebaseDatabase.getInstance().getReference().child("Users");
        sewaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int newUser = 0;
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    if (!dt.child("status").getValue().toString().equalsIgnoreCase("")) {
                        User mUser = dt.getValue(User.class);
                        if (!mUser.isStatus()) {
                            newUser++;
                        }
                    }
                }
                notifikasiCallback.onSuccess(newUser);
                notifikasiCallback.onError(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkNewBencana(final NotifikasiCallback notifikasiCallback) {
        Log.d("cek", "masuk checknewbencana");
        DatabaseReference sewaRef = FirebaseDatabase.getInstance().getReference().child("Bencana");
        sewaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int newBencana = 0;
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Bencana mBencana = dt.getValue(Bencana.class);
                    if (!mBencana.isStatus()) {
                        newBencana++;
                    }
                }
                notifikasiCallback.onSuccess(newBencana);
                notifikasiCallback.onError(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
