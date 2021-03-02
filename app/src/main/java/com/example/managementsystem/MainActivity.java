package com.example.managementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.managementsystem.FragementMenu.MarchandiseFragment;
import com.example.managementsystem.FragementMenu.FactureFragment;
import com.example.managementsystem.FragementMenu.ClientFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title

        getSupportActionBar().hide(); //hide the title bar
        username = (EditText)findViewById(R.id.ed_user);
        password = (EditText)findViewById(R.id.ed_pass);
        btnSignUp = (Button)findViewById(R.id.btnlog);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //listeners
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = database.getReference().child("loginadmin");
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if(user.isEmpty() || pass.isEmpty())
                    Toast.makeText(MainActivity.this,"Fill all the fieds",Toast.LENGTH_LONG).show();
                else{

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String usernam = snapshot.child("username").getValue().toString();
                            String passwor = snapshot.child("password").getValue().toString();
                            if(user.compareTo(usernam)==0 && passwor.compareTo(pass)==0) {
                                Toast.makeText(MainActivity.this, "top", Toast.LENGTH_LONG).show();
                                //Intent teacher = new Intent(this, HomeViewModel.class);
                                //startActivity(teacher);
                                display();
                            }
                            else{
                                username.setText("");
                                password.setText("");
                                Toast.makeText(MainActivity.this,"Username and password do not match",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });
    }
    public void display(){
        Intent teacher = new Intent(this, drawer_activity.class);
        startActivity(teacher);
    }
}