package com.example.delsa.fragment;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.delsa.activities.KategoriActivity;
import com.example.delsa.POJO.Bencana;
import com.example.delsa.R;
import com.example.delsa.activities.SearchActivity;
import com.example.delsa.adapter.AdapterBencana;
import com.example.delsa.adapter.AdapterBencanaTerdekat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private Button btn_kebakaran, btn_longsor, btn_banjir, btn_gempa, btn_others, btn_lihatsemuabencana, btn_refreshlocation;
    private RecyclerView rv_bencanaterdekat, rv_caripahalayuk;
    private TextView tv_lokasi, tv_searchbencana;
    private LinearLayout llkategori;
    private ImageView iv_close;
    private LinearLayout ll_kebakaran, ll_longsor, ll_banjir, ll_gempabumi, ll_tsunami, ll_gunungmeletus, ll_putingbeliung;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btn_kebakaran = view.findViewById(R.id.btn_kebakaran);
        btn_longsor = view.findViewById(R.id.btn_longsor);
        btn_banjir = view.findViewById(R.id.btn_banjir);
        btn_gempa = view.findViewById(R.id.btn_gempa);
        btn_others = view.findViewById(R.id.btn_others);
        btn_lihatsemuabencana = view.findViewById(R.id.btn_lihatsemuabencanaterdekat);
        btn_kebakaran.setOnClickListener(this);
        btn_longsor.setOnClickListener(this);
        btn_banjir.setOnClickListener(this);
        btn_gempa.setOnClickListener(this);
        btn_others.setOnClickListener(this);
        btn_lihatsemuabencana.setOnClickListener(this);

        iv_close = view.findViewById(R.id.btn_close);
        iv_close.setOnClickListener(this);

        rv_bencanaterdekat = view.findViewById(R.id.rv_bencanaterdekat);
        rv_caripahalayuk = view.findViewById(R.id.rv_caripahalayuk);

        tv_lokasi = view.findViewById(R.id.tv_lokasi);
        tv_searchbencana = view.findViewById(R.id.tv_searchbencana);
        tv_searchbencana.setOnClickListener(this);

        llkategori = view.findViewById(R.id.bottom_sheet_kategori);
        ll_kebakaran = view.findViewById(R.id.ll_kebakaran);
        ll_longsor = view.findViewById(R.id.ll_longsor);
        ll_banjir = view.findViewById(R.id.ll_banjir);
        ll_gempabumi = view.findViewById(R.id.ll_gempabumi);
        ll_tsunami = view.findViewById(R.id.ll_tsunami);
        ll_gunungmeletus = view.findViewById(R.id.ll_gunungmeletus);
        ll_putingbeliung = view.findViewById(R.id.ll_putingbeliung);

        ll_kebakaran.setOnClickListener(this);
        ll_longsor.setOnClickListener(this);
        ll_banjir.setOnClickListener(this);
        ll_gempabumi.setOnClickListener(this);
        ll_tsunami.setOnClickListener(this);
        ll_gunungmeletus.setOnClickListener(this);
        ll_putingbeliung.setOnClickListener(this);

        getBencanaTerdekat();
        getBencana();

        return view;
    }

    private void getBencanaTerdekat() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Bencana");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Bencana> bencanaterdekat = new ArrayList<>();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Bencana bencana = dt.getValue(Bencana.class);
                    Log.d("cek", bencana.getJudul());
                    bencanaterdekat.add(bencana);
                }
                AdapterBencanaTerdekat adapterBencanaTerdekat = new AdapterBencanaTerdekat(getContext());
                adapterBencanaTerdekat.setData(bencanaterdekat);
                rv_bencanaterdekat.setAdapter(adapterBencanaTerdekat);
                rv_bencanaterdekat.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getBencana() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Bencana");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Bencana> list_bencana = new ArrayList<>();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Bencana bencana = dt.getValue(Bencana.class);
                    list_bencana.add(bencana);
                }
                AdapterBencana adapterBencana = new AdapterBencana(getContext());
                adapterBencana.setData(list_bencana);
                rv_caripahalayuk.setAdapter(adapterBencana);
                rv_caripahalayuk.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_kebakaran:
                intentToCategoryActivity("Kebakaran");
                break;
            case R.id.btn_longsor:
                intentToCategoryActivity("Longsor");
                break;
            case R.id.btn_banjir:
                intentToCategoryActivity("Banjir");
                break;
            case R.id.btn_gempa:
                intentToCategoryActivity("Gempa Bumi");
                break;
            case R.id.btn_others:
                llkategori.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_searchbencana:
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_lihatsemuabencanaterdekat:
                break;
            case R.id.btn_close:
                llkategori.setVisibility(View.INVISIBLE);
                break;
            case R.id.ll_kebakaran:
                intentToCategoryActivity("Kebakaran");
                break;
            case R.id.ll_longsor:
                intentToCategoryActivity("Longsor");
                break;
            case R.id.ll_banjir:
                intentToCategoryActivity("Banjir");
                break;
            case R.id.ll_gempabumi:
                intentToCategoryActivity("Gempa Bumi");
                break;
            case R.id.ll_tsunami:
                intentToCategoryActivity("Tsunami");
                break;
            case R.id.ll_gunungmeletus:
                intentToCategoryActivity("Gunung Meletus");
                break;
            case R.id.ll_putingbeliung:
                intentToCategoryActivity("Puting Beliung");
                break;
        }
    }

    private void intentToCategoryActivity(String kategori) {
        Intent intent = new Intent(getContext(), KategoriActivity.class);
        intent.putExtra("kategori", kategori);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        if (llkategori.getVisibility() == View.VISIBLE) {
            llkategori.setVisibility(View.INVISIBLE);
        }
        super.onPause();
    }


    private String getCityAndProvince(Location location) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLatitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String knownName = addresses.get(0).getSubAdminArea();

        String currentLocation = knownName + ", " + city + ", " + state;
        return currentLocation;
    }

}
