package com.example.managementsystem.FragementMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.managementsystem.Classes.Client;
import com.example.managementsystem.Classes.Facture;
import com.example.managementsystem.Classes.Marchandise;
import com.example.managementsystem.R;
import com.example.managementsystem.drawer_activity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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
    private EditText eID;
    private EditText eQuantity;
    private EditText eprix;
    private Button addM;
    //aRRAYLIST tablelayoyt
    private ArrayList<String> data;
    private ArrayList<String> data1;
    private ArrayList<String> data2;

    private TableLayout table;
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

        eprix = (EditText) findViewById(R.id.Price);

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


        //change the price editTEXT it depend on the select merchandise
        mardescSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
               // Toast.makeText(FactureAdd.this,""+parentView.getItemIdAtPosition(position),Toast.LENGTH_SHORT).show();
               // String d =  parentView.getItemIdAtPosition(position)

                //eprix.setText(mardescSpinner.getSelectedItem().toString());
                dbRefMerchandise.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot sn : snapshot.getChildren()){
                            Marchandise m = sn.getValue(Marchandise.class);
                            if (m.getDescription() == mardescSpinner.getSelectedItem()){
                                eprix.setText(m.getPrice());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        eID = (EditText) findViewById(R.id.billID);
        eQuantity = (EditText) findViewById(R.id.quantity);
        data = new ArrayList<String>();
        data1 = new ArrayList<String>();
        data2 = new ArrayList<String>();

        //button add merchandise to the bill
        addM = findViewById(R.id.addM);
        addM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(FactureAdd.this,mardescSpinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                addMarchandise();
            }
        });
    }

    public void addMarchandise(){
        String prod = mardescSpinner.getSelectedItem().toString();
        String pr = eprix.getText().toString(); String [] p = pr.split(" ");
        String pp = p[0];
        int price =Integer.parseInt(pp);
        int qt = Integer.parseInt(eQuantity.getText().toString());
        int tot = price*qt;

        data.add(prod);
        data1.add(String.valueOf(qt));
        data2.add(String.valueOf(tot));

        table = findViewById(R.id.tb1);
        TableRow row = new TableRow(this);
        TextView t1 = new TextView(this);
        TextView t2 = new TextView(this);
        TextView t3 = new TextView(this);

        TextView toatBill = findViewById(R.id.prixTotal);
        int sum =0;
        for (int i=0;i<data.size();i++){
            String pro = data.get(i);
            String q = data1.get(i);
            String t = data2.get(i);
            t1.setText(pro);
            t2.setText(q);
            t3.setText(t);
            sum+=Integer.parseInt(t);
            toatBill.setText(sum+" Dhs");
        }
        row.addView(t1);
        row.addView(t2);
        row.addView(t3);
        table.addView(row);
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
