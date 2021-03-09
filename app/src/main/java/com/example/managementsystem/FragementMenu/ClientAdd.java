package com.example.managementsystem.FragementMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.managementsystem.Classes.Client;
import com.example.managementsystem.R;
import com.example.managementsystem.drawer_activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClientAdd extends AppCompatActivity {
    private EditText id,name,phone,email,add;
    private ImageView save, back;
    DatabaseReference clientDbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_client_add);
        id = (EditText) findViewById(R.id.id_client);
        name = (EditText) findViewById(R.id.nom_client);
        phone = (EditText) findViewById(R.id.telephone);
        email = (EditText) findViewById(R.id.email);
        add = (EditText) findViewById(R.id.addresse);
        save = (ImageView)  findViewById(R.id.addC);
        back = (ImageView)  findViewById(R.id.back);
        clientDbRef = FirebaseDatabase.getInstance().getReference().child("customers");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertCustomerData();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayHome();
            }
        });

    }
    public void insertCustomerData(){
        String ID = id.getText().toString();
        String n = name.getText().toString();
        String p= phone.getText().toString();
        String e = email.getText().toString();
        String a = add.getText().toString();
        if(ID.isEmpty() || n.isEmpty() || p.isEmpty() || e.isEmpty() || a.isEmpty()){
            Toast.makeText(this,"Fill all the fieds",Toast.LENGTH_LONG).show();
        }
        else{
            Client c = new Client(n,p,e,a,ID);
           // clientDbRef.push().setValue(c);
            //Toast.makeText(this,"Customer data has been inserted successfully",Toast.LENGTH_LONG).show();
            //displayHome();
            clientDbRef.orderByChild("cin").equalTo(c.getCin()).addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange(DataSnapshot dataSnapshot){
                    if(dataSnapshot.exists()) {
                        Toast.makeText(getApplicationContext(), "The customer ID you have entred already exists", Toast.LENGTH_LONG).show();
                         displayHome();
                    }
                    else{
                        clientDbRef.push().setValue(c);
                        Toast.makeText(getApplicationContext(),"Customer data has been inserted successfully",Toast.LENGTH_LONG).show();
                        displayHome();
                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }

    }
    public void displayHome(){
        Intent teacher = new Intent(this, drawer_activity.class);
        startActivity(teacher);
        /*ClientOperation newGamefragment = new ClientOperation();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ope, newGamefragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
    }

}