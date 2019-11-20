package com.example.delsa.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.delsa.R;
import com.example.delsa.activities.MainActivity;
import com.example.delsa.activities.MainUserActivity;
import com.example.delsa.activities.MenuActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment implements View.OnClickListener {

    private Button btnLogout;


    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        btnLogout = view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(), "Logout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finishAffinity();
                break;

        }
    }
}
