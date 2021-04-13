package com.example.managementsystem.FragementMenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.managementsystem.Classes.Client;
import com.example.managementsystem.MenuItem.ContactUs;
import com.example.managementsystem.MenuItem.HelpCustomers;
import com.example.managementsystem.R;
import com.example.managementsystem.drawer_activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

/**
 //* A simple {@link Fragment} subclass.
 //* Use the {@link //ClientFragment#newInstance} factory method to
 //* create an instance of this fragment.
 */
public class ClientFragment extends Fragment {
/*
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;*/
    private int mBackground;
    private ImageButton btnadd;
    private ListView listview;
    static String idFirebase;
    //MaterialLetterIcon mIcon;
    private ImageView mIcon;
    private static final Random RANDOM = new Random();
    private final TypedValue mTypedValue = new TypedValue();
   // private int[] mMaterialColors;
    public ClientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
   //  * @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment ClientFragment.
     */
    // TODO: Rename and change types and number of parameters
   /* public static ClientFragment newInstance(String param1, String param2) {
        ClientFragment fragment = new ClientFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_client, container, false);

       setHasOptionsMenu(true);
       // mMaterialColors = this.getResources().getIntArray(R.array.colors);

        //Toast(getContext()
        btnadd = (ImageButton) view.findViewById(R.id.addbtn);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNewFragment();
            }
        });
        listview = (ListView) view.findViewById(R.id.listcustomer);

        mIcon = (ImageView) view.findViewById(R.id.icon);
        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), R.layout.list_client_item,R.id.custinfo,list);
        listview.setAdapter(adapter);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("customers");
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
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int which = position;
                new AlertDialog.Builder(getContext()).setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?").setMessage("Do you want to delete this customer")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String n = list.get(position);
                               // Toast.makeText(getContext(),n,Toast.LENGTH_LONG).show();
                                String[] a = n.split("\n");
                                String cin = a[0];
                                list.remove(n);
                                adapter.notifyDataSetChanged();
                                deleteRecord(cin);

                            }
                        }).setNegativeButton("No",null).show();
                return true;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String info = list.get(position);
                String [] infoArray = info.split("\n");
                String ID = infoArray[0];

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("customers");
                reference.orderByChild("cin").equalTo(ID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                             idFirebase = ds.getKey(); //it returns the id firebase of the clicked customer in firebase
                            //(String name, String telephone, String email, String addresse, String cin)
                            String name = ds.child("name").getValue(String.class);
                            String tel = ds.child("telephone").getValue(String.class);
                            String email = ds.child("email").getValue(String.class);
                            String add = ds.child("addresse").getValue(String.class);
                            String cc = ds.child("cin").getValue(String.class);
                            showupdate(name,tel,email,add,cc);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        throw databaseError.toException();
                    }
                });




               // showupdate(name,"a","a","a",ID);
            }
        });

        return view;
    }
    public void displayNewFragment(){
        /*ClientOperation newGamefragment = new ClientOperation();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ope, newGamefragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
        Intent teacher = new Intent(getContext(), ClientAdd.class);
        startActivity(teacher);
    }
    public void deleteRecord(String cin){
        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference("customers").child(id);
        //Task<Void> mTask = reference.removeValue();
        //reference.removeValue();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("customers").orderByChild("cin").equalTo(cin);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }
    public void showupdate(String name, String telephone, String email, String add, String cin){
        AlertDialog.Builder mDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View mDialogView = inflater.inflate(R.layout.update_dialog_client,null);
        mDialog.setView(mDialogView);
        mDialog.setTitle("Updating");
        mDialog.show();
        AppCompatButton btn = mDialogView.findViewById(R.id.update);
        EditText custid = mDialogView.findViewById(R.id.idcustomer);
        EditText custname = mDialogView.findViewById(R.id.customername);
        EditText custphone = mDialogView.findViewById(R.id.phone);
        EditText custmail = mDialogView.findViewById(R.id.email);
        EditText custadd = mDialogView.findViewById(R.id.address);
        custid.setText(cin);
        custname.setText(name);
        custphone.setText(telephone);
        custmail.setText(email);
        custadd.setText(add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cin = custid.getText().toString();
                String name = custname.getText().toString();
                String tele = custphone.getText().toString();
                String email = custmail.getText().toString();
                String add = custadd.getText().toString();
                update(idFirebase,cin,name,tele,email,add);
                displayHome();
                Toast.makeText(getContext(),"Customer data updated successfully",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void update(String ID,String cin,String name,String tele,String email, String add) {
        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference("customers").child(ID);
        //(String name, String telephone, String email, String addresse, String cin)
        Client c = new Client(name, tele, email, add, cin);
        DbRef.setValue(c);
    }
    public void displayHome(){
        Intent teacher = new Intent(getContext(), drawer_activity.class);
        startActivity(teacher);
    }

    //Select menu item help and settings


    @Override
    public void onCreateOptionsMenu( Menu menu,  MenuInflater inflater) {
        menu.clear(); //I add it because the menu item appears twice
          inflater.inflate(R.menu.drawer_activity, menu);


            super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.help:
                Intent intent = new Intent(getContext(), HelpCustomers.class);
                startActivity(intent);
                return true;

            case R.id.action_settings:
                //Intent intent1 = new Intent(getContext(), Settings.class);
                //startActivity(intent1);
                View update = LayoutInflater.from(getContext()).inflate(R.layout.change_password,null);

                EditText oldpass = update.findViewById(R.id.edt_old_password);
                EditText newPass = update.findViewById(R.id.edt_new_password);
                EditText confirmPass = update.findViewById(R.id.edt_confirm_password);
                AppCompatButton btn = update.findViewById(R.id.updatePass);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Changing password");
                builder.setView(update);
                AlertDialog dialog = builder.create();
                dialog.show();

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String old = oldpass.getText().toString();
                        String New = newPass.getText().toString();
                        String con = confirmPass.getText().toString();
                        if(old.isEmpty())
                            oldpass.setError("This field cannot be empty");
                        else if (New.isEmpty())
                            newPass.setError("This field cannot be empty");
                        else if (con.isEmpty())
                            confirmPass.setError("This field cannot be empty");
                        else{
                            if (!New.equals(con))
                                Toast.makeText(getContext(),"The new password and confirmation password do not match",Toast.LENGTH_SHORT).show();
                            else{
                                FirebaseDatabase databas = FirebaseDatabase.getInstance();
                                DatabaseReference myRefe = databas.getReference().child("loginadmin");
                                myRefe.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String passwor = snapshot.child("password").getValue().toString();
                                        if(passwor.equals(old)){
                                            myRefe.child("password").setValue(New);
                                            Toast.makeText(getContext(),"Your password was changed",Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                        else{
                                            oldpass.setError("Incorrect password");
                                            newPass.setText("");
                                            confirmPass.setText("");
                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        }

                    }
                });

                break;
            case R.id.contact:
                Intent intent2 = new Intent(getContext(), ContactUs.class);
                startActivity(intent2);
        }
        return super.onOptionsItemSelected(item);
    }
}