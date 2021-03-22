package com.example.managementsystem.FragementMenu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.DragAndDropPermissionsCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private DatabaseReference dbRefFacture;
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
    private Button savePdf;
    private TableLayout table;
    private ArrayList<Marchandise> m;

    private TextView toatBill;



    // save as PDF part
    Bitmap bmp, scaledbmp;
    ArrayList<String> T;
    int pageWidth = 1200;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getSupportActionBar().hide(); //hide the toolbar
        setContentView(R.layout.activity_facture_add);
        btnHide = findViewById(R.id.addC);
        btnHide.setVisibility(ImageView.INVISIBLE);

        //==========================================
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bill);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 1200, 518, false);


//        SavePdf= findViewById(R.id.SavePdf);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


        //==========================================

        m = new ArrayList<Marchandise>();

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
        adapterMer = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerDataMer);
        mardescSpinner.setAdapter(adapterMer);
        retrieveMerchandiseSpinner();

        eprix = (EditText) findViewById(R.id.Price);

        savePdf = findViewById(R.id.SavePdf);

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
                        for (DataSnapshot sn : snapshot.getChildren()) {
                            Marchandise m = sn.getValue(Marchandise.class);
                            if (m.getDescription() == mardescSpinner.getSelectedItem()) {
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

        dbRefFacture = FirebaseDatabase.getInstance().getReference().child("Bill");
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
                if (eQuantity.getText().toString().isEmpty()) {
                    eQuantity.setError("Please enter the quantity");
                } else {
                    addMarchandise();//implementing the arraylist and the tableLyout
                }

            }
        });

//        savePdf.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onClick(View v) {
//                String id = eID.getText().toString();
//                String da = edDate.getText().toString();
//                String n = customerName.getSelectedItem().toString();
//                Facture f =new Facture(id,da,n,m);
//                dbRefFacture.push().setValue(f);
//                m.clear();
//                Toast.makeText(FactureAdd.this,"The bill was added successfully",Toast.LENGTH_SHORT).show();
//                finish();
//                startActivity(getIntent());
//                createPDF();
//
//
//            }
//        });


        savePdf.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String id = eID.getText().toString();
                String da = edDate.getText().toString();
                String n = customerName.getSelectedItem().toString();

                if (id.isEmpty()) {
                    eID.setError("The id cannot be empty");
                } else {
                    if (da.isEmpty())
                        edDate.setError("The date cannot be empty");
                    else {
                        Facture f = new Facture(id, da, n, m);
                        dbRefFacture.push().setValue(f);
                        m.clear();
                        Toast.makeText(FactureAdd.this, "The bill was added successfully", Toast.LENGTH_SHORT).show();
                        //pfd function

                        createPDF(f);
                        finish();
                        startActivity(getIntent());
                    }
                }
            }
        });

    }

    public void addMarchandise() {
        //  String id = eID.getText().toString();
        // String da = edDate.getText().toString();
        //String n = customerName.getSelectedItem().toString();
        //ArrayList<Marchandise> m = new ArrayList<Marchandise>();
        //    Marchandise m1 = new Marchandise("oo","15",4);
        //  m.add(m1);
        //Facture f = new Facture(id,da,n,m);
        //String key = dbRefFacture.push().getKey();
        //dbRefFacture.child(key).setValue(f);
        //Toast.makeText(FactureAdd.this,key,Toast.LENGTH_SHORT).show();

        String prod = mardescSpinner.getSelectedItem().toString();
        String pr = eprix.getText().toString();
        String[] p = pr.split(" ");
        String pp = p[0];
        int price = Integer.parseInt(pp);
        int qt = Integer.parseInt(eQuantity.getText().toString());
        int tot = price * qt;

        Marchandise marchandise = new Marchandise(prod, pp + " Dhs", eQuantity.getText().toString());
        m.add(marchandise);

        data.add(prod);
        data1.add(String.valueOf(qt));
        data2.add(String.valueOf(tot));

        table = findViewById(R.id.tb1);
        TableRow row = new TableRow(this);
        TextView t1 = new TextView(this);
        TextView t2 = new TextView(this);
        TextView t3 = new TextView(this);

        toatBill = findViewById(R.id.prixTotal);
        int sum = 0;
        for (int i = 0; i < data.size(); i++) {
            String pro = data.get(i);
            String q = data1.get(i);
            String t = data2.get(i);
            t1.setText(pro);
            t2.setText(q);
            t3.setText(t);
            sum += Integer.parseInt(t);
            toatBill.setText(sum + " Dhs");
            //  Marchandise m = new Marchandise(pro,pp,Integer.parseInt(q));
            // dbRefFacture.child(key).child("merchandises").push().setValue(m);
        }
        row.addView(t1);
        row.addView(t2);
        row.addView(t3);
        table.addView(row);
        eQuantity.setText("");
    }

    public void retrieveCustomerSpinner() {
        listener = dbRefCustomer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //String data = snapshot.child("cin").getValue(String.class);
                //spinnerData.add(data);
                for (DataSnapshot sn : snapshot.getChildren()) {
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

    public void retrieveMerchandiseSpinner() {
        dbRefMerchandise.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sn : snapshot.getChildren()) {
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

    public void backD() {
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

    //======================================================
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPDF(Facture f) {


        String n=customerName.getSelectedItem().toString().trim();


        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();
        Paint titlePaint = new Paint();

        PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
        PdfDocument.Page mypage1 = myPdfDocument.startPage(myPageInfo1);
        Canvas canvas = mypage1.getCanvas();


        canvas.drawBitmap(scaledbmp, 0, 0, myPaint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titlePaint.setTextSize(70);
//                    canvas.drawText("BillFast",pageWidth/2,270,titlePaint);

        myPaint.setColor(Color.rgb(0, 113, 188));
        myPaint.setTextSize(30f);
        myPaint.setTextAlign(Paint.Align.RIGHT);
//                    canvas.drawText("Reference ="+eID.getText().toString(),1160,40,myPaint);

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(35f);
        myPaint.setColor(Color.BLACK);


        myPaint.setColor(Color.rgb(49, 140, 231));
        myPaint.setColor(Color.BLACK);


        canvas.drawText("Bill Reference : " + f.getId(), 20, 620, myPaint);
        canvas.drawText("Customer Name : " + f.getCustomer(), 20, 660, myPaint);
        myPaint.setTextAlign(Paint.Align.RIGHT);



        // contenue
        canvas.drawText("Date :" + f.getDate(), 1080, 620, myPaint);

        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(2);
        canvas.drawRect(20, 780, pageWidth - 20, 860, myPaint);
        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setStyle(Paint.Style.FILL);
//                canvas.drawText("Number ",40,830,myPaint);
        canvas.drawText("Merchandise Description ", 40, 850, myPaint);
        canvas.drawText("Price", 700, 850, myPaint);
        canvas.drawText("Quantity", 890, 850, myPaint);
        canvas.drawText("Total ", 1050, 850, myPaint);

//                canvas.drawLine(180,790,180,840,myPaint);
        canvas.drawLine(680, 790, 680, 860, myPaint);
        canvas.drawLine(880, 790, 880, 860, myPaint);
        canvas.drawLine(1030, 790, 1030, 860, myPaint);


//               description display

        int y = 970, x = 40;
        for (int i = 0; i < data.size(); i++) {
            canvas.drawText(data.get(i), x, y, myPaint);
            y = y + 50;

        }
        x = 700;
        y = 970;

        //price's display
        for (int i = 0; i < data1.size(); i++) {
            int price = Integer.parseInt(data2.get(i)) / Integer.parseInt(data1.get(i));
            canvas.drawText(String.valueOf(price), x, y, myPaint);
            y = y + 50;

        }
        x = 900;
        y = 970;

        //quantity display
        for (int i = 0; i < data1.size(); i++) {
            canvas.drawText(data1.get(i), x, y, myPaint);
            y = y + 50;
        }

        // total display
        x = 1050;
        y = 970;
        for (int i = 0; i < data2.size(); i++) {
            canvas.drawText(data2.get(i), x, y, myPaint);
            y = y + 50;
        }

        // bill's Total


//                int sum=
//                canvas.drawText("Total = ",900,1750,myPaint);
        myPaint.setColor(Color.rgb(49, 140, 231));
        canvas.drawRect(680, 1350, pageWidth - 20, 1450, myPaint);
        myPaint.setColor(Color.BLACK);
        myPaint.setTextSize(50f);
        myPaint.setTextAlign(Paint.Align.LEFT);


        canvas.drawText("Bill's Total  " + toatBill.getText().toString(), 680, 1395, myPaint);


        myPdfDocument.finishPage(mypage1);

        File file = new File(Environment.getExternalStorageDirectory(), "/Bill.pdf");

        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        myPdfDocument.close();

    }




//                public String getNumCustomer(String name){
//                    final String[] tel = new String[1];
//
//                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("customers");
//
//                    reference.orderByChild("name").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            for(DataSnapshot ds : dataSnapshot.getChildren()){
//
//                                tel[0] = ds.child("telephone").getValue(String.class);
//
//                            }
//                        }
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                            throw databaseError.toException();
//                        }
//                    });
//                    return tel[0];
//
//                }

//                });
//            }



}
