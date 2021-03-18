package com.example.managementsystem.FragementMenu;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.managementsystem.R;


public class FactureFragment extends Fragment {
    private Button btnBill;
    public FactureFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facture, container, false);
        btnBill = view.findViewById(R.id.add_bill);
        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddBillActivity();
            }
        });

        return view;
    }
    public void openAddBillActivity(){
        Intent myIntent = new Intent(getContext(), FactureAdd.class);
        startActivity(myIntent);
    }
}