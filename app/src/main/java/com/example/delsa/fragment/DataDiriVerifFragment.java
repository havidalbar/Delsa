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
import android.widget.TextView;

import com.example.delsa.POJO.User;
import com.example.delsa.R;
import com.example.delsa.activities.DetailDataDiriVerifActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataDiriVerifFragment extends Fragment {

    private RecyclerView rcvListDataDiriVerif;
    private Query query;
    private FirebaseRecyclerAdapter<User, UserViewHolder> firebaseRecyclerAdapter;

    public DataDiriVerifFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_diri_verif, container, false);

        rcvListDataDiriVerif = view.findViewById(R.id.rcv_akun_verif);
        rcvListDataDiriVerif.setLayoutManager(new LinearLayoutManager(getActivity()));

        query = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("status").equalTo(false);

        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final UserViewHolder holder, final int position, @NonNull final User model) {

//                holder.setDisplayPhoto(model.getFotoProfil());
                holder.setDisplayName(model.getNama());

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id = getRef(position).getKey();
                        String nama = model.getNama();
//                        String foto = model.getFotoProfil();
                        String kota = model.getKota();
                        String email = model.getEmail();
                        String fotoIdentitas = model.getFotoIdentitas();
                        String noTelp = model.getNoTelephone();

                        Intent goDetail = new Intent(getActivity(), DetailDataDiriVerifActivity.class);
                        goDetail.putExtra(DetailDataDiriVerifActivity.ID, id);
                        goDetail.putExtra(DetailDataDiriVerifActivity.NAMA, nama);
                        goDetail.putExtra(DetailDataDiriVerifActivity.NO_TELP, noTelp);
                        goDetail.putExtra(DetailDataDiriVerifActivity.KOTA, kota);
                        goDetail.putExtra(DetailDataDiriVerifActivity.EMAIL, email);
                        goDetail.putExtra(DetailDataDiriVerifActivity.FOTO_ID, fotoIdentitas);
                        startActivity(goDetail);
                    }
                });
            }

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_verif_data_diri, parent, false);

                return new UserViewHolder(view1);
            }
        };

        rcvListDataDiriVerif.setAdapter(firebaseRecyclerAdapter);

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

    public class UserViewHolder extends RecyclerView.ViewHolder{

        View view;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
        }

//        public void setDisplayPhoto (String foto){
//            CircleImageView fotoUser = view.findViewById(R.id.civ_profile_image_verif);
//
//            Picasso.get().load(foto).placeholder(R.drawable.person).into(fotoUser);
//        }

        public void setDisplayName (String nama) {
            TextView namaUser = view.findViewById(R.id.tv_nama_verif);

            namaUser.setText(nama);
        }
    }

}
