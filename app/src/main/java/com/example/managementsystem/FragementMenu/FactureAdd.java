package com.example.managementsystem.FragementMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.managementsystem.Classes.Client;
import com.example.managementsystem.Classes.Marchandise;
import com.example.managementsystem.R;
import com.example.managementsystem.drawer_activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FactureAdd extends AppCompatActivity {

    private ImageView btnHide;
    private TextView txt; //the text of the toolbar
    private ImageView back;
    private Spinner customerName;
    private Spinner mardescSpinner;
    private DatabaseReference dbRefCustomer;
    private DatabaseReference dbRefMerchandise;
    private ValueEventListener listener;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> spinnerData;
    private ArrayAdapter<String> adapterMer;
    private ArrayList<String> spinnerDataMer;

    //
    private DatePickerDialog mDatePickerDialog;
    private EditText edDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //hide the toolbar
        setContentView(R.layout.activity_facture_add);
        btnHide = findViewById(R.id.addC);
        btnHide.setVisibility(ImageView.INVISIBLE);
        txt = findViewById(R.id.textbar);
        txt.setText("Facture");
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backD();
            }
        });
        //spinner customer
        customerName = (Spinner) findViewById(R.id.custName);
        dbRefCustomer = FirebaseDatabase.getInstance().getReference().child("customers");
        spinnerData = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerData);
        customerName.setAdapter(adapter);
        retrieveCustomerSpinner();

        //spinner description
        mardescSpinner = (Spinner) findViewById(R.id.marDesc);
        dbRefMerchandise = FirebaseDatabase.getInstance().getReference().child("merchandises");
        spinnerDataMer = new ArrayList<>();
        adapterMer = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,spinnerDataMer);
        mardescSpinner.setAdapter(adapterMer);
        retrieveMerchandiseSpinner();

        //show the calendar
        edDate = (EditText) findViewById(R.id.date);
        setDateTimeField();
        edDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialog.show();
                return false;
            }
        });

    }

    public void retrieveCustomerSpinner(){
        listener = dbRefCustomer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //String data = snapshot.child("cin").getValue(String.class);
                //spinnerData.add(data);
                for (DataSnapshot sn : snapshot.getChildren()){
                    Client client = sn.getValue(Client.class);
                    String data = client.getName();
                    spinnerData.add(data);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void retrieveMerchandiseSpinner(){
        dbRefMerchandise.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sn: snapshot.getChildren()){
                    Marchandise m = sn.getValue(Marchandise.class);
                    String data = m.getDescription();
                    spinnerDataMer.add(data);
                }
                adapterMer.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void backD(){
        Intent teacher = new Intent(this, FactureFragment.class);
        startActivity(teacher);
    }
    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy"); 
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);

                edDate.setText(fdate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }
}
/*DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("customers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot sn : snapshot.getChildren()){
                    Client client = sn.getValue(Client.class);
                    String txt = client.toString();

                    list.add(txt);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/