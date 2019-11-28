package com.example.delsa.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.delsa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BencanaVerifFragment extends Fragment {


    public BencanaVerifFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bencana_verif, container, false);



        return view;
    }

}
