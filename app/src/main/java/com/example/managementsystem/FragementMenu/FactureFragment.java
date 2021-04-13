package com.example.managementsystem.FragementMenu;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.managementsystem.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import static android.app.Activity.RESULT_OK;


public class FactureFragment extends Fragment {
    private TextView btnBill;
    private ImageView imagebrowse, imageupload,filelogo,cancelfile;
    private Uri filepath;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private EditText filetitle;
    private TextView ShowUploads;
    public FactureFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facture, container, false);
        btnBill = view.findViewById(R.id.add_bill);
        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddBillActivity();
            }
        });
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("PdfBills");
        filetitle = view.findViewById(R.id.filetitle);
        filelogo = view.findViewById(R.id.filelogo);
        imagebrowse = view.findViewById(R.id.imagebrowse);
        imageupload = view.findViewById(R.id.imageupload);
        ShowUploads = view.findViewById(R.id.showUplads);
        ShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPdfs();
            }
        });
        cancelfile = view.findViewById(R.id.cancelfile);
        filelogo.setVisibility(View.INVISIBLE);
        cancelfile.setVisibility(View.INVISIBLE);
        cancelfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filelogo.setVisibility(View.INVISIBLE);
                cancelfile.setVisibility(View.INVISIBLE);
                imagebrowse.setVisibility(View.VISIBLE);
            }
        });

        imagebrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(getContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent();
                                intent.setType("application/pdf");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent,"Select Pdf Files"),101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        imageupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filepath!=null && filetitle.getText().toString().isEmpty()==false)
                    processupload(filepath);

                else if (filetitle.getText().toString().isEmpty())
                    filetitle.setError("Please enter the file title");
                else
                    Toast.makeText(getContext(),"No file is selectd! Please select a file",Toast.LENGTH_SHORT).show();
            }
        });
/*imageupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filepath!=null && filetitle.getText().toString().isEmpty()==false)
                    processupload(filepath);

                else if (filetitle.getText().toString().isEmpty())
                    filetitle.setError("Please enter the file title");
                else
                    Toast.makeText(getContext(),"No file is selectd! Please select a file",Toast.LENGTH_SHORT).show();
            }
        });*/


        return view;
    }
    public void openAddBillActivity(){
        Intent myIntent = new Intent(getContext(), FactureAdd.class);
        startActivity(myIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101 && resultCode == RESULT_OK){
            filepath = data.getData(); //Uri
            filelogo.setVisibility(View.VISIBLE);
            cancelfile.setVisibility(View.VISIBLE);
            imagebrowse.setVisibility(View.INVISIBLE);
        }
    }
    public void processupload(Uri filepath){
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("File Uploading..!");
        pd.show();
        final StorageReference reference = storageReference.child("PdfUploads/"+System.currentTimeMillis()+"/.pdf");
        reference.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //insert in realtimeDatabase
                                PdfBillUpload obj = new PdfBillUpload(filetitle.getText().toString(),uri.toString());
                                databaseReference.child(databaseReference.push().getKey()).setValue(obj);
                                pd.dismiss();
                                Toast.makeText(getContext(),"File Uploaded",Toast.LENGTH_SHORT).show();
                                filelogo.setVisibility(View.INVISIBLE);
                                cancelfile.setVisibility(View.INVISIBLE);
                                imagebrowse.setVisibility(View.VISIBLE);
                                filetitle.setText("");
                            }
                        });

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                float percent = (100 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                pd.setMessage("Uploaded : "+ (int)percent+"%");
            }
        });
    }
    public void showPdfs(){
        Intent myIntent = new Intent(getContext(), RetrievePdf.class);
        startActivity(myIntent);
    }
}