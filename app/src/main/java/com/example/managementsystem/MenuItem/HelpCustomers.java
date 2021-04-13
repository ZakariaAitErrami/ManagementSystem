package com.example.managementsystem.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.managementsystem.R;
import com.example.managementsystem.drawer_activity;
import com.google.firebase.database.FirebaseDatabase;

public class HelpCustomers extends AppCompatActivity {

    ImageView back;
    ImageView save;
    TextView tp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_help_customers);
        getSupportActionBar().hide(); //hide the title bar
        save = (ImageView)  findViewById(R.id.addC);
        back = (ImageView)  findViewById(R.id.back);
        save.setVisibility(View.INVISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayHome();
            }
        });
        tp = findViewById(R.id.textbar);
        tp.setText("Help");
    }
    public void displayHome(){
        Intent teacher = new Intent(this, drawer_activity.class);
        startActivity(teacher);
    }
}