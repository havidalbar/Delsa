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
import com.example.delsa.activities.DetailBencanaActivity;

import java.util.ArrayList;

public class AdapterNotifikasi extends RecyclerView.Adapter<AdapterNotifikasi.ViewHolder> {

    private final Context context;
    private final ArrayList<Bencana> list_bencana = new ArrayList<>();

    public AdapterNotifikasi(Context context) {
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
        View v = LayoutInflater.from(context).inflate(R.layout.item_notifikasi, parent, false);
        final ViewHolder holder = new ViewHolder(v);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), DetailBencanaActivity.class);
                intent.putExtra("bencana", (Parcelable)list_bencana.get(holder.getAdapterPosition()));
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
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
        private final TextView tv_judulnotifikasi;
        private final TextView tv_kategorinotifikasi;
        private final TextView tv_waktunotifikasi;

        ViewHolder(View itemView) {
            super(itemView);
            tv_judulnotifikasi = itemView.findViewById(R.id.tv_judulnotifikasi);
            tv_kategorinotifikasi = itemView.findViewById(R.id.tv_kategorinotifikasi);
            tv_waktunotifikasi = itemView.findViewById(R.id.tv_waktunotifikasi);
        }

        void bind(Bencana bencana){
//            tv_judulbencana.setText(bencana.getJudul());
//            tv_deskripsi.setText(bencana.getDeskripsi());
//            RequestOptions requestOptions = new RequestOptions();
//            requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(20));
//            Glide.with(itemView.getContext())
//                    .load(bencana.getFotoBencana())
//                    .apply(requestOptions)
//                    .into(iv_fotobencana);
        }
    }
}