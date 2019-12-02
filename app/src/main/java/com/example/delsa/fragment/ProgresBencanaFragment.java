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

import com.example.delsa.POJO.Bencana;
import com.example.delsa.R;
import com.example.delsa.adapter.AdapterProgresBencana;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgresBencanaFragment extends Fragment {

    private RecyclerView rcvListProgresBencana;

    public ProgresBencanaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_progres_bencana, container, false);

        rcvListProgresBencana = view.findViewById(R.id.rcv_progres_bencana);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Bencana");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<Bencana> list_bencana = new ArrayList<>();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Bencana bencana = dt.getValue(Bencana.class);
                    list_bencana.add(bencana);
                }

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();

                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                databaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        final ArrayList<Bencana> list_bencana2 = new ArrayList<>();
                        String kota = dataSnapshot.child("kota").getValue().toString();

                        for (Bencana a : list_bencana){
                            if (a.isStatus() && a.getKota().equalsIgnoreCase(kota) && a.getStatusPengiriman().equalsIgnoreCase("Masih dikumpulkan") ){
                                list_bencana2.add(a);
                            }

                        }

                        AdapterProgresBencana adapterProgresBencana = new AdapterProgresBencana(getContext());
                        adapterProgresBencana.setData(list_bencana2);
                        rcvListProgresBencana.setAdapter(adapterProgresBencana);
                        rcvListProgresBencana.setLayoutManager(new LinearLayoutManager(getContext()));

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

