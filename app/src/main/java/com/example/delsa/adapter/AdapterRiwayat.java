package com.example.delsa.adapter;

import android.content.Context;
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

import java.util.ArrayList;

public class AdapterRiwayat extends RecyclerView.Adapter<AdapterRiwayat.ViewHolder> {

    private final Context context;
    private final ArrayList<Bencana> list_bencana = new ArrayList<>();

    public AdapterRiwayat(Context context) {
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
        View v = LayoutInflater.from(context).inflate(R.layout.item_riwayat, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.bind(list_bencana.get(position));
    }

    @Override
    public int getItemCount() {
        return list_bencana.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_fotobencana;
        private final TextView tv_judulriwayat;
        private final TextView tv_statusriwayat;

        ViewHolder(View itemView) {
            super(itemView);
            iv_fotobencana = itemView.findViewById(R.id.iv_fotobencana);
            tv_judulriwayat = itemView.findViewById(R.id.tv_judulriwayat);
            tv_statusriwayat = itemView.findViewById(R.id.tv_statusriwayat);
        }

        void bind(Bencana bencana){
            tv_judulriwayat.setText(bencana.getJudul());
            String status = bencana.getStatusPengiriman();
//            Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
//            if (bencana.isStatus()){
//                status = "Masih dikumpulkan";
//            } else {
//                status = "Sudah tersalurkan";
//
//            }
//            tv_statusriwayat.setTextColor(Color.parseColor("#ff00ddff"));
            tv_statusriwayat.setText(status);
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(20));
            Glide.with(itemView.getContext())
                    .load(bencana.getFotoBencana())
                    .apply(requestOptions)
                    .into(iv_fotobencana);
        }
    }
}