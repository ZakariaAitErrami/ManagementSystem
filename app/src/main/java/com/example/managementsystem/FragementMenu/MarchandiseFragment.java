package com.example.managementsystem.FragementMenu;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.managementsystem.Classes.Marchandise;
import com.example.managementsystem.R;
import com.example.managementsystem.drawer_activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class MarchandiseFragment extends Fragment implements ImageAdapter.OnItemClickListener {
    private Button addMer;
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
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
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mProgressCircle = view.findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("merchandises");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    Marchandise upload = postSnapshot.getValue(Marchandise.class);
                    mUploads.add(upload);
                }
                mAdapter = new ImageAdapter(getContext(),mUploads);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(MarchandiseFragment.this);
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
        Toast.makeText(getContext(),"MODIFY Click at position: "+ position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {
        Toast.makeText(getContext(),"Delete Click at position: "+ position,Toast.LENGTH_SHORT).show();
    }
}