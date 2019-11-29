package com.example.delsa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.delsa.POJO.Bencana;
import com.example.delsa.R;
import com.example.delsa.adapter.AdapterBencana;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import static java.security.AccessController.getContext;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener {

    private SearchView sv_searchbencana;
    private FirebaseDatabase database;
    private ArrayList<Bencana> listbencana;
    private RecyclerView rv_search;
    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        sv_searchbencana = findViewById(R.id.sv_search_bencana_kategori);
        sv_searchbencana.setIconifiedByDefault(true);
        sv_searchbencana.setFocusable(true);
        sv_searchbencana.setIconified(false);
        sv_searchbencana.requestFocusFromTouch();
        sv_searchbencana.setOnQueryTextListener(this);

        rv_search = findViewById(R.id.rv_search);

        database = FirebaseDatabase.getInstance();
        listbencana = new ArrayList<>();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        search(newText);
        return false;
    }

    private void search(String s) {
        Query query = database.getReference("Bencana").orderByChild("judul")
                .startAt(s)
                .endAt(s + "\uf0ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    listbencana.clear();
                    for (DataSnapshot dss : dataSnapshot.getChildren()){
                        final Bencana bencana = dss.getValue(Bencana.class);
                        listbencana.add(bencana);
                    }

                }
                else if (!dataSnapshot.hasChildren()){
                    listbencana.clear();
                }
                AdapterBencana adapterBencana = new AdapterBencana(getApplicationContext());
                adapterBencana.setData(listbencana);
                adapterBencana.notifyDataSetChanged();
                rv_search.setAdapter(adapterBencana);
                rv_search.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                onBackPressed();
                break;
        }
    }
}
