package com.example.managementsystem.FragementMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.managementsystem.R;

public class MarchandiseAdd extends AppCompatActivity {

    private TextView t; //the textview of the toolbar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide(); //hide the title bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marchandise_add);
        t = (TextView) findViewById(R.id.textbar);
        t.setText("Marchandise");
    }
}