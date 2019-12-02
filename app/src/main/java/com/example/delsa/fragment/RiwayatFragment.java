package com.example.delsa.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.delsa.POJO.Bencana;
import com.example.delsa.POJO.DonasiUang;
import com.example.delsa.R;
import com.example.delsa.adapter.AdapterRiwayat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RiwayatFragment extends Fragment {

    private RecyclerView rv_riwayat;
    private FirebaseAuth auth;

    public RiwayatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riwayat, container, false);
        rv_riwayat = view.findViewById(R.id.rv_riwayat);

        auth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("DonasiUang Uang");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<DonasiUang> usedDonasiUang = new ArrayList<>();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    DonasiUang donasiUang = dt.getValue(DonasiUang.class);
                    if (donasiUang.getIdUser().equals(auth.getUid())) {
                        usedDonasiUang.add(donasiUang);
                        Log.d("cek", donasiUang.getIdUser());
                    }
                }


                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("Bencana");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Bencana> listbencana = new ArrayList<>();
                        for (DataSnapshot dt : dataSnapshot.getChildren()) {
                            Bencana bencana = dt.getValue(Bencana.class);
                            for (DonasiUang d : usedDonasiUang) {
                                if (d.getIdBencana().equals(bencana.getIdbencana())) {
                                    listbencana.add(bencana);
                                }
                            }
                        }
                        AdapterRiwayat adapterRiwayat = new AdapterRiwayat(getContext());
                        adapterRiwayat.setData(listbencana);
                        rv_riwayat.setAdapter(adapterRiwayat);
                        rv_riwayat.setLayoutManager(new LinearLayoutManager(getContext()));
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
