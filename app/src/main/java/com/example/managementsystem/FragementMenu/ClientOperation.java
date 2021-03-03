package com.example.managementsystem.FragementMenu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.managementsystem.Classes.Client;
import com.example.managementsystem.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClientOperation extends Fragment {

    private EditText id,name,phone,email,add;
    private Button save;
    DatabaseReference clientDbRef;
    public ClientOperation() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_client_operation, container, false);
        id = (EditText) v.findViewById(R.id.id_client);
        name = (EditText) v.findViewById(R.id.nom_client);
        phone = (EditText) v.findViewById(R.id.telephone);
        email = (EditText) v.findViewById(R.id.email);
        add = (EditText) v.findViewById(R.id.addresse);
        save = (Button)  v.findViewById(R.id.addC);
        clientDbRef = FirebaseDatabase.getInstance().getReference().child("customers");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertCustomerData();
            }
        });
        return v;
    }
    public void insertCustomerData(){
        String ID = id.getText().toString();
        String n = name.getText().toString();
        String p= phone.getText().toString();
        String e = email.getText().toString();
        String a = add.getText().toString();
        if(ID.isEmpty() || n.isEmpty() || p.isEmpty() || e.isEmpty() || a.isEmpty()){
            Toast.makeText(getContext(),"Fill all the fieds",Toast.LENGTH_LONG).show();
        }
        else{
            Client c = new Client(n,p,e,a,ID);
            clientDbRef.push().setValue(c);
            Toast.makeText(getContext(),"Customer data has been inserted successfully",Toast.LENGTH_LONG).show();
            displayClientHomeFragment();
        }

    }
    public void displayClientHomeFragment(){
        /*ClientFragment newGamefragment = new ClientFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.customerForm, newGamefragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
        getFragmentManager().popBackStack();

    }
}