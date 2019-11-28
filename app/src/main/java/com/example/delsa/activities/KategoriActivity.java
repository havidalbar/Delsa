package com.example.delsa.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.delsa.POJO.Bencana;
import com.example.delsa.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class KategoriActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseRecyclerAdapter<Bencana, BencanaViewHolder> adapterBencana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        String kategori = getIntent().getStringExtra("kategori");

        toolbar = findViewById(R.id.toolbar_donasi_uang);
        toolbar.setTitle(kategori);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_kategori);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        DatabaseReference produkRef = FirebaseDatabase.getInstance().getReference().child("Bencana");

        Query query = produkRef.orderByChild("kategori").equalTo(kategori);
        FirebaseRecyclerOptions<Bencana> options =
                new FirebaseRecyclerOptions.Builder<Bencana>()
                        .setQuery(query, Bencana.class)
                        .build();

        adapterBencana = new FirebaseRecyclerAdapter<Bencana, BencanaViewHolder>(options) {
            @Override
            public BencanaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_bencana_search, parent, false);
                return new BencanaViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(BencanaViewHolder holder, int position, final Bencana model) {
                holder.display(model.getJudul(),model.getFotoBencana(),model.getDeskripsi());
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(KategoriActivity.this, DetailBencanaActivity.class);
                        intent.putExtra("bencana",model);
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapterBencana);

    }

    private class BencanaViewHolder extends RecyclerView.ViewHolder {
        View view;

        public BencanaViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void display(String judulbencana, String fotobencana, String deskripsibencana) {
            TextView tv_judulbencana = view.findViewById(R.id.tv_judulbencana);
            tv_judulbencana.setText(judulbencana);
            TextView tv_deskripsi = view.findViewById(R.id.tv_deskripsibencana);
            tv_deskripsi.setText(deskripsibencana);
            ImageView iv_fotobencana = view.findViewById(R.id.iv_fotobencana);
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(20));
            Glide.with(itemView.getContext())
                    .load(fotobencana)
                    .apply(requestOptions)
                    .into(iv_fotobencana);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterBencana.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterBencana.stopListening();
    }
}
