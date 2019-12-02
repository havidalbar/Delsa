package com.example.delsa.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.delsa.POJO.Bencana;
import com.example.delsa.R;
import com.example.delsa.activities.DetailBencanaVerifActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterBencanaVerif extends RecyclerView.Adapter<AdapterBencanaVerif.ViewHolder> {

    private final Context context;
    private final ArrayList<Bencana> list_bencana = new ArrayList<>();

    public AdapterBencanaVerif(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Bencana> items) {
        list_bencana.clear();
        list_bencana.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_verif_bencana, parent, false);
        final ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.bind(list_bencana.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idBencana = list_bencana.get(position).getIdbencana();
                String idUser = list_bencana.get(position).getIdUser();
                String namaBencana = list_bencana.get(position).getJudul();
                String lokasiBencana = list_bencana.get(position).getAlamat();
                String deskripsiBencana = list_bencana.get(position).getDeskripsi();
                String fotoBencana = list_bencana.get(position).getFotoBencana();

                Intent bencanaIntent = new Intent(context, DetailBencanaVerifActivity.class);
                bencanaIntent.putExtra(DetailBencanaVerifActivity.ID_BENCANA, idBencana);
                bencanaIntent.putExtra(DetailBencanaVerifActivity.ID_USER, idUser);
                bencanaIntent.putExtra(DetailBencanaVerifActivity.NAMA_BENCANA, namaBencana);
                bencanaIntent.putExtra(DetailBencanaVerifActivity.LOKASI_BENCANA, lokasiBencana);
                bencanaIntent.putExtra(DetailBencanaVerifActivity.DESKRIPSI_BENCANA, deskripsiBencana);
                bencanaIntent.putExtra(DetailBencanaVerifActivity.FOTO_BENCANA, fotoBencana);
                context.startActivity(bencanaIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list_bencana.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_fotobencana;
        private final TextView tv_judulbencana;
        private final TextView tv_namaUserBencana;
        private final TextView tv_deskripsi;

        ViewHolder(View itemView) {
            super(itemView);
            iv_fotobencana = itemView.findViewById(R.id.img_bencana_verif);
            tv_judulbencana = itemView.findViewById(R.id.tv_nama_bencana_verif);
            tv_namaUserBencana = itemView.findViewById(R.id.tv_nama_user_bencana_verif);
            tv_deskripsi = itemView.findViewById(R.id.tv_deskripsi_bencana_verif);
        }

        void bind(Bencana bencana){
            tv_judulbencana.setText(bencana.getJudul());
            tv_deskripsi.setText(bencana.getDeskripsi());
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(bencana.getIdUser());
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String namaUser = dataSnapshot.child("nama").getValue().toString();
                    tv_namaUserBencana.setText(namaUser);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(20));
            Glide.with(itemView.getContext())
                    .load(bencana.getFotoBencana())
                    .apply(requestOptions)
                    .into(iv_fotobencana);
        }
    }
}