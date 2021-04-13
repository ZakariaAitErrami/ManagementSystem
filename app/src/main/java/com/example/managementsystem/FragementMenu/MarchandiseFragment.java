package com.example.managementsystem.FragementMenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.managementsystem.Classes.Marchandise;
import com.example.managementsystem.MenuItem.ContactUs;
import com.example.managementsystem.MenuItem.HelpCustomers;
import com.example.managementsystem.MenuItem.HelpMerchandise;
import com.example.managementsystem.R;
import com.example.managementsystem.drawer_activity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MarchandiseFragment extends Fragment implements ImageAdapter.OnItemClickListener {
    private Button addMer;
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private FirebaseStorage mStorage;
    private ValueEventListener mDBListener;
    private List<Marchandise> mUploads;
    private ProgressBar mProgressCircle;
    public MarchandiseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marchandise, container, false);
        addMer = view.findViewById(R.id.add_merchandise);
        addMer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFormMerchandise();
            }
        });

        setHasOptionsMenu(true);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mProgressCircle = view.findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();

        mAdapter = new ImageAdapter(getContext(),mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(MarchandiseFragment.this);

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("merchandises");
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    Marchandise upload = postSnapshot.getValue(Marchandise.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }
                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }
    public void displayFormMerchandise(){
        Intent teacher = new Intent(getContext(), MarchandiseAdd.class);
        startActivity(teacher);
    }

    //implements the method of OnClickListeneer that We create in Image Adapter class


    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(),"Normal Click at position: "+ position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWhatEverClick(int position) {
        //Toast.makeText(getContext(),"MODIFY Click at position: "+ position,Toast.LENGTH_SHORT).show();
        AlertDialog.Builder mDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();


        View mDialogView = inflater.inflate(R.layout.update_dialog_marchandise,null);
        mDialog.setView(mDialogView);
        mDialog.setTitle("Updating");
        mDialog.show();
        AppCompatButton btn = mDialogView.findViewById(R.id.update_merchandise);
        EditText ref = mDialogView.findViewById(R.id.reference_update);
        EditText price = mDialogView.findViewById(R.id.price_update);
        EditText des = mDialogView.findViewById(R.id.description_update);
        Marchandise selectedItem = mUploads.get(position);
        ref.setText(selectedItem.getReference());
        price.setText(selectedItem.getPrice());
        des.setText(selectedItem.getDescription());
        String selectedKey = selectedItem.getKey();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseRef = FirebaseDatabase.getInstance().getReference("merchandises");
                mDatabaseRef.child(selectedKey).child("price").setValue(price.getText().toString());
                mDatabaseRef.child(selectedKey).child("reference").setValue(ref.getText().toString());
                mDatabaseRef.child(selectedKey).child("description").setValue(des.getText().toString());
                Toast.makeText(getContext(),"Merchandise data successfully updated",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDeleteClick(int position) {
        //Toast.makeText(getContext(),"Delete Click at position: "+ position,Toast.LENGTH_SHORT).show();
        Marchandise selectedItem = mUploads.get(position);
        String selectedKey = selectedItem.getKey();
        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getmImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(getContext(), "Merchandise deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear(); //I add it because the menu item appears twice
        inflater.inflate(R.menu.drawer_activity, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help:
                Intent intent = new Intent(getContext(), HelpMerchandise.class);
                startActivity(intent);
                return true;
            case R.id.contact:
                Intent intent1 = new Intent(getContext(), ContactUs.class);
                startActivity(intent1);
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

        }
        return super.onOptionsItemSelected(item);
    }
}