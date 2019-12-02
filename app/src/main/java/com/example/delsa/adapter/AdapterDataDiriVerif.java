package com.example.delsa.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delsa.POJO.User;
import com.example.delsa.R;
import com.example.delsa.activities.DetailDataDiriVerifActivity;

import java.util.ArrayList;

public class AdapterDataDiriVerif extends RecyclerView.Adapter<AdapterDataDiriVerif.ViewHolder> {

    private final Context context;
    private final ArrayList<User> list_user = new ArrayList<>();


    public AdapterDataDiriVerif(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<User> items) {
        list_user.clear();
        list_user.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_verif_data_diri, parent, false);
        final ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.bind(list_user.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idUser = list_user.get(position).getUid();
                String namaUser = list_user.get(position).getNama();
                String kotaUser = list_user.get(position).getKota();
                String noTelp = list_user.get(position).getNoTelephone();
                String email = list_user.get(position).getEmail();
                String fotoId = list_user.get(position).getFotoIdentitas();
//
                Intent userIntent = new Intent(context, DetailDataDiriVerifActivity.class);
                userIntent.putExtra(DetailDataDiriVerifActivity.ID, idUser);
                userIntent.putExtra(DetailDataDiriVerifActivity.NAMA, namaUser);
                userIntent.putExtra(DetailDataDiriVerifActivity.KOTA, kotaUser);
                userIntent.putExtra(DetailDataDiriVerifActivity.NO_TELP, noTelp);
                userIntent.putExtra(DetailDataDiriVerifActivity.EMAIL, email);
                userIntent.putExtra(DetailDataDiriVerifActivity.FOTO_ID, fotoId);
                context.startActivity(userIntent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return list_user.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_namaUserVerif;

        ViewHolder(View itemView) {
            super(itemView);
            tv_namaUserVerif = itemView.findViewById(R.id.tv_nama_verif);
        }

        void bind(User userVerif) {
            tv_namaUserVerif.setText(userVerif.getNama());
        }
    }
}