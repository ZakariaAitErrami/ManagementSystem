package com.example.managementsystem.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.managementsystem.FragementMenu.FactureFragment;
import com.example.managementsystem.R;
import com.example.managementsystem.drawer_activity;

public class HelpBill extends AppCompatActivity {
    ImageView save,back;
    TextView v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //hide the toolbar
        setContentView(R.layout.activity_help_bill);
        save = (ImageView)  findViewById(R.id.addC);
        back = (ImageView)  findViewById(R.id.back);
        v = findViewById(R.id.textbar);
        v.setText("Help");
        save.setVisibility(View.INVISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayHome();
            }
        });
    }
    public void displayHome(){
        Intent teacher = new Intent(this, FactureFragment.class);
        startActivity(teacher);
    }
}