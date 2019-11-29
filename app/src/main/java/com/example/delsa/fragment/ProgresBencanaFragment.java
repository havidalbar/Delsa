package com.example.delsa.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delsa.POJO.Bencana;
import com.example.delsa.R;
import com.example.delsa.activities.ProgresBencanaActivity;
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
public class ProgresBencanaFragment extends Fragment {

    private RecyclerView rcvListProgresBencana;
    private Query query;
    private FirebaseRecyclerAdapter<Bencana, BencanaViewHolder> firebaseRecyclerAdapter;

    public ProgresBencanaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_progres_bencana, container, false);

        rcvListProgresBencana = view.findViewById(R.id.rcv_progres_bencana);
        rcvListProgresBencana.setLayoutManager(new LinearLayoutManager(getActivity()));

        query = FirebaseDatabase.getInstance().getReference().child("Bencana").orderByChild("status").equalTo(true);

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

                        Intent bencanaIntent = new Intent(getActivity(), ProgresBencanaActivity.class);
                        bencanaIntent.putExtra(ProgresBencanaActivity.ID_BENCANA, idBencana);
                        bencanaIntent.putExtra(ProgresBencanaActivity.ID_USER, idUser);
                        bencanaIntent.putExtra(ProgresBencanaActivity.NAMA_BENCANA, namaBencana);
                        bencanaIntent.putExtra(ProgresBencanaActivity.LOKASI_BENCANA, lokasiBencana);
                        bencanaIntent.putExtra(ProgresBencanaActivity.DESKRIPSI_BENCANA, deskripsiBencana);
                        bencanaIntent.putExtra(ProgresBencanaActivity.FOTO_BENCANA, fotoBencana);
                        startActivity(bencanaIntent);
                    }
                });
            }

            @NonNull
            @Override
            public BencanaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_progres_bencana, parent, false);

                return new BencanaViewHolder(view1);
            }
        };

        rcvListProgresBencana.setAdapter(firebaseRecyclerAdapter);

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
            ImageView fotoBencana = view.findViewById(R.id.img_progres_bencana);

            Picasso.get().load(foto).placeholder(R.drawable.person).into(fotoBencana);
        }

        public void setDisplayNamaBencana (String bencanaName) {
            TextView namaBencana = view.findViewById(R.id.tv_nama_progres_bencana);

            namaBencana.setText(bencanaName);
        }

        public void setDisplayNamaUser (String userNama) {
            TextView namaUser = view.findViewById(R.id.tv_progres_nama_user_bencana);

            namaUser.setText(userNama);
        }

        public void setDisplayDeskripsiBencana(String des_bencana){
            TextView deskripsiBencana = view.findViewById(R.id.tv_deskripsi_progres_bencana);

            deskripsiBencana.setText(des_bencana);
        }
    }

}

