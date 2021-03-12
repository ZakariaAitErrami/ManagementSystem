package com.example.managementsystem.FragementMenu;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.managementsystem.R;
import com.example.managementsystem.drawer_activity;


public class MarchandiseFragment extends Fragment {
    private Button addMer;
    public MarchandiseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marchandise, container, false);
        addMer = view.findViewById(R.id.add_merchandise);
        addMer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFormMerchandise();
            }
        });

        return view;
    }
    public void displayFormMerchandise(){
        Intent teacher = new Intent(getContext(), MarchandiseAdd.class);
        startActivity(teacher);
    }
}