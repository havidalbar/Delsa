package com.example.delsa.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.delsa.activities.DetailBencanaActivity;
import com.example.delsa.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private Button btn_kebakaran, btn_longsor, btn_banjir, btn_gempa, btn_others, btn_lihatsemuabencana;
    private RecyclerView rv_bencanaterdekat, rv_caripahalayuk;
    private TextView tv_lokasi, tv_searchbencana;
    private LinearLayout llkategori;
    private ImageView iv_close;
    private SlidingUpPanelLayout slideup;


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

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_kebakaran:
                break;
            case R.id.btn_longsor:
                break;
            case R.id.btn_banjir:
                break;
            case R.id.btn_gempa:
                break;
            case R.id.btn_others:
                llkategori.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_searchbencana:
                break;
            case R.id.btn_lihatsemuabencanaterdekat:
                Intent intent = new Intent(getActivity(), DetailBencanaActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_close:
                llkategori.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void onPause() {
        if(llkategori.getVisibility() == View.VISIBLE) {
            llkategori.setVisibility(View.INVISIBLE);
        }
        super.onPause();
    }
}
