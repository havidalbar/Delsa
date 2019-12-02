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

import com.example.delsa.POJO.Bencana;
import com.example.delsa.R;
import com.example.delsa.activities.DetailBencanaVerifActivity;
import com.example.delsa.adapter.AdapterBencanaVerif;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class BencanaVerifFragment extends Fragment {

    private RecyclerView rcvListBencanaVerif;
//    private Query query;
//    private FirebaseRecyclerAdapter<Bencana, BencanaViewHolder> firebaseRecyclerAdapter;

    public BencanaVerifFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bencana_verif, container, false);

        rcvListBencanaVerif = view.findViewById(R.id.rcv_bencana_verif);

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
                            if (!a.isStatus() && a.getKota().equalsIgnoreCase(kota) ){
                                list_bencana2.add(a);
                            }

                        }

                        AdapterBencanaVerif adapterBencanaVerif = new AdapterBencanaVerif(getContext());
                        adapterBencanaVerif.setData(list_bencana2);
                        rcvListBencanaVerif.setAdapter(adapterBencanaVerif);
                        rcvListBencanaVerif.setLayoutManager(new LinearLayoutManager(getContext()));

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

