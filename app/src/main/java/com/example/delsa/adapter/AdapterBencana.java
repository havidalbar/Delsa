package com.example.delsa.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delsa.R;

public class AdapterBencana extends RecyclerView.Adapter<AdapterBencana.ViewHolder> {

    private final Context context;

    public AdapterBencana(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_bencana, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_fotobencana;
        private final TextView tv_judulbencana;
        private final TextView tv_sisahari;

        ViewHolder(View itemView) {
            super(itemView);
            iv_fotobencana = itemView.findViewById(R.id.iv_fotobencana);
            tv_judulbencana = itemView.findViewById(R.id.tv_judulbencana);
            tv_sisahari = itemView.findViewById(R.id.tv_sisaharibencana);
        }
    }
}