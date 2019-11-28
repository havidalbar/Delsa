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

import com.example.delsa.POJO.Bencana;
import com.example.delsa.R;
import com.example.delsa.activities.DetailBencanaVerifActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class BencanaVerifFragment extends Fragment {

    private RecyclerView rcvListBencanaVerif;
    private Query query;
    private FirebaseRecyclerAdapter<Bencana, BencanaViewHolder> firebaseRecyclerAdapter;

    public BencanaVerifFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bencana_verif, container, false);

        rcvListBencanaVerif = view.findViewById(R.id.rcv_bencana_verif);
        rcvListBencanaVerif.setLayoutManager(new LinearLayoutManager(getActivity()));

        query = FirebaseDatabase.getInstance().getReference().child("Bencana").orderByChild("status").equalTo(false);

        FirebaseRecyclerOptions<Bencana> options = new FirebaseRecyclerOptions.Builder<Bencana>()
                .setQuery(query, Bencana.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Bencana, BencanaViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final BencanaViewHolder holder, int position, @NonNull final Bencana model) {
                holder.setDisplayPhoto(model.getFotoBencana());
                holder.setDisplayNamaBencana(model.getJudul());
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users");
                userRef.child(model.getIdUser()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String namaUser = dataSnapshot.child("nama").getValue().toString();

                        holder.setDisplayNamaUser(namaUser);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                holder.setDisplayDeskripsiBencana(model.getDeskripsi());

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String idBencana = model.getIdbencana();
                        String namaBencana = model.getJudul();
                        String idUser = model.getIdUser();
                        String lokasiBencana = model.getAlamat();
                        String deskripsiBencana = model.getDeskripsi();
                        String fotoBencana = model.getFotoBencana();

                        Intent bencanaIntent = new Intent(getActivity(), DetailBencanaVerifActivity.class);
                        bencanaIntent.putExtra(DetailBencanaVerifActivity.ID_BENCANA, idBencana);
                        bencanaIntent.putExtra(DetailBencanaVerifActivity.ID_USER, idUser);
                        bencanaIntent.putExtra(DetailBencanaVerifActivity.NAMA_BENCANA, namaBencana);
                        bencanaIntent.putExtra(DetailBencanaVerifActivity.LOKASI_BENCANA, lokasiBencana);
                        bencanaIntent.putExtra(DetailBencanaVerifActivity.DESKRIPSI_BENCANA, deskripsiBencana);
                        bencanaIntent.putExtra(DetailBencanaVerifActivity.FOTO_BENCANA, fotoBencana);
                        startActivity(bencanaIntent);
                    }
                });
            }

            @NonNull
            @Override
            public BencanaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_verif_bencana, parent, false);

                return new BencanaViewHolder(view1);
            }
        };

        rcvListBencanaVerif.setAdapter(firebaseRecyclerAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        firebaseRecyclerAdapter.stopListening();
    }

    public class BencanaViewHolder extends RecyclerView.ViewHolder{

        View view;

        public BencanaViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
        }

        public void setDisplayPhoto (String foto){
            ImageView fotoBencana = view.findViewById(R.id.img_bencana_verif);

            Picasso.get().load(foto).placeholder(R.drawable.person).into(fotoBencana);
        }

        public void setDisplayNamaBencana (String bencanaName) {
            TextView namaBencana = view.findViewById(R.id.tv_nama_bencana_verif);

            namaBencana.setText(bencanaName);
        }

        public void setDisplayNamaUser (String userNama) {
            TextView namaUser = view.findViewById(R.id.tv_nama_user_bencana_verif);

            namaUser.setText(userNama);
        }

        public void setDisplayDeskripsiBencana(String des_bencana){
            TextView deskripsiBencana = view.findViewById(R.id.tv_deskripsi_bencana_verif);

            deskripsiBencana.setText(des_bencana);
        }
    }

}

