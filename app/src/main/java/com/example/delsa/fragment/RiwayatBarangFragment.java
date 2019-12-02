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
import android.widget.Toast;

import com.example.delsa.POJO.Bencana;
import com.example.delsa.POJO.DonasiBarang;
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
public class RiwayatBarangFragment extends Fragment {

    private FirebaseAuth auth;
    private RecyclerView rcvDonasiBarang;


    public RiwayatBarangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riwayat_barang, container, false);

        rcvDonasiBarang = view.findViewById(R.id.rcv_donasiBarang);

        auth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Donasi Barang");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<DonasiBarang> userDonasiBarang = new ArrayList<>();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    DonasiBarang donasiBarang = dt.getValue(DonasiBarang.class);
                    if (donasiBarang.getIdUser().equals(auth.getUid())) {
                        userDonasiBarang.add(donasiBarang);
                        Log.d("cek", donasiBarang.getIdUser());
                    }
                }

//                Toast.makeText(getActivity(), String.valueOf(userDonasiBarang.size()), Toast.LENGTH_SHORT).show();

//                DatabaseReference donasiBarangRef = FirebaseDatabase.getInstance().getReference().child("DonasiBarang");
//                donasiBarangRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        final ArrayList<DonasiBarang> userDonasiBarang = new ArrayList<>();
//                        for (DataSnapshot dt : dataSnapshot.getChildren()){
//                            DonasiBarang donasiBarang = dt.getValue(DonasiBarang.class);
//                            if (donasiBarang.getIdUser().equals(auth.getUid())){
//                                userDonasiBarang.add(donasiBarang);
//                            }
//                        }
//
//                        userDonasiBarang.addAll(2, userDonasiBarang);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });


                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("Bencana");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Bencana> listbencana = new ArrayList<>();
                        for (DataSnapshot dt : dataSnapshot.getChildren()) {
                            Bencana bencana = dt.getValue(Bencana.class);
                            for (DonasiBarang d : userDonasiBarang) {
                                if (d.getIdBencana().equals(bencana.getIdbencana())) {
                                    listbencana.add(bencana);
                                }
                            }
                        }
                        AdapterRiwayat adapterRiwayat = new AdapterRiwayat(getContext());
                        adapterRiwayat.setData(listbencana);
                        rcvDonasiBarang.setAdapter(adapterRiwayat);
                        rcvDonasiBarang.setLayoutManager(new LinearLayoutManager(getContext()));
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
