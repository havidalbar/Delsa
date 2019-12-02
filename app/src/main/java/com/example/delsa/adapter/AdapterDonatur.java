package com.example.delsa.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.example.delsa.POJO.Donatur;
import com.example.delsa.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterDonatur extends RecyclerView.Adapter<AdapterDonatur.ViewHolder> {

    private final Context context;
    private final ArrayList<Donatur> list_donatur = new ArrayList<>();

    public AdapterDonatur(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Donatur> items) {
        list_donatur.clear();
        list_donatur.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_donatur, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.bind(list_donatur.get(position));
    }

    @Override
    public int getItemCount() {
        return list_donatur.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView iv_fotodonatur;
        private final TextView tv_namadonatur;
        private final TextView tv_pesandonatur;
        private final TextView tv_tgldonatur;

        ViewHolder(View itemView) {
            super(itemView);
            iv_fotodonatur = itemView.findViewById(R.id.civ_profildonatur);
            tv_namadonatur = itemView.findViewById(R.id.tv_namadonatur);
            tv_pesandonatur = itemView.findViewById(R.id.tv_pesandonatur);
            tv_tgldonatur = itemView.findViewById(R.id.tv_tanggaldonasi);
        }

        void bind(Donatur donatur){
            tv_namadonatur.setText(donatur.getNama());
            tv_tgldonatur.setText(donatur.getTanggaldonasi());
            tv_pesandonatur.setText(donatur.getPesan());
        }
    }
}