package com.example.delsa.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delsa.POJO.User;
import com.example.delsa.R;
import com.example.delsa.activities.DetailBencanaVerifActivity;
import com.example.delsa.adapter.AdapterDataDiriVerif;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataDiriVerifFragment extends Fragment {

    private RecyclerView rcvListDataDiriVerif;

    public DataDiriVerifFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_diri_verif, container, false);

        rcvListDataDiriVerif = view.findViewById(R.id.rcv_akun_verif);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<User> list_user = new ArrayList<>();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {

                    if (dt.child("status").getValue().toString().equalsIgnoreCase("false") && !dt.child("fotoIdentitas").getValue().toString().equalsIgnoreCase("")){
                        User user = dt.getValue(User.class);
                        list_user.add(user);
                    }

                }

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();

                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                databaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        final ArrayList<User> list_user2 = new ArrayList<>();
                        String kota = dataSnapshot.child("kota").getValue().toString();

                        for (User a : list_user){
                            if (a.getKota().equalsIgnoreCase(kota) ){
                                list_user2.add(a);
                            }

                        }

                        AdapterDataDiriVerif adapterDataDiriVerif = new AdapterDataDiriVerif(getContext());
                        adapterDataDiriVerif.setData(list_user2);
                        rcvListDataDiriVerif.setAdapter(adapterDataDiriVerif);
                        rcvListDataDiriVerif.setLayoutManager(new LinearLayoutManager(getContext()));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

}
