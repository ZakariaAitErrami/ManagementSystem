package com.example.managementsystem.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.managementsystem.R;
import com.example.managementsystem.drawer_activity;

public class ContactUs extends AppCompatActivity {
    private ImageView back;
    private ImageView save;
    private Button btSend;
    TextView tt;
    EditText etSubject, etMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        getSupportActionBar().hide(); //hide the title bar
        save = (ImageView)  findViewById(R.id.addC);
        back = (ImageView)  findViewById(R.id.back);
        save.setVisibility(View.INVISIBLE);
        tt= findViewById(R.id.textbar);
        tt.setText("Contact us");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayHome();
            }
        });
        etSubject = findViewById(R.id.et_subject);
        etMessage = findViewById(R.id.et_message);
        btSend= findViewById(R.id.bt_send);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:fastbill.este@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT,etSubject.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT,etMessage.getText().toString());
                startActivity(intent);
            }
        });
    }
    public void displayHome(){
        Intent teacher = new Intent(this, drawer_activity.class);
        startActivity(teacher);
    }
}
