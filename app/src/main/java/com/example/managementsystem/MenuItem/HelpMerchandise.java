package com.example.managementsystem.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.managementsystem.FragementMenu.MarchandiseFragment;
import com.example.managementsystem.R;

public class HelpMerchandise extends AppCompatActivity {
    ImageView save,back;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_help_merchandise);
        save = (ImageView)  findViewById(R.id.addC);
        back = (ImageView)  findViewById(R.id.back);
        save.setVisibility(View.INVISIBLE);
        t = (TextView) findViewById(R.id.textbar);
        t.setText("Help");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayHome();
            }
        });
    }
    public void displayHome(){
        Intent teacher = new Intent(this, MarchandiseFragment.class);
        startActivity(teacher);
    }
}