package com.example.managementsystem.FragementMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.managementsystem.Classes.Marchandise;
import com.example.managementsystem.MainActivity;
import com.example.managementsystem.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MarchandiseAdd extends AppCompatActivity {

    //the toolbar button
    private TextView t; //the textview of the toolbar
    private ImageView back;
    private ImageView mButtonUpload;
    //End toolbar button
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage;
    private EditText reference, description,price;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseref;
    private StorageTask mUploadTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide(); //hide the title bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marchandise_add);
        t = (TextView) findViewById(R.id.textbar);
        t.setText("Marchandise");
        back = (ImageView) findViewById(R.id.back);
        mButtonUpload = (ImageView) findViewById(R.id.addC);
        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);
        reference = findViewById(R.id.reference);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        mStorageRef = FirebaseStorage.getInstance().getReference("merchandises");
        mDatabaseref = FirebaseDatabase.getInstance().getReference("merchandises");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayHomeMerchandise();
            }
        });

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUploadTask !=null && mUploadTask.isInProgress()){
                    Toast.makeText(MarchandiseAdd.this,"Upolad in progress",Toast.LENGTH_SHORT).show();
                }else{
                    uploadFile();
                }
            }
        });

    }
    public void displayHomeMerchandise(){
        Intent teacher = new Intent(this, MarchandiseFragment.class);
        startActivity(teacher);
    }
    public void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }
    //this method is called when we pick a image to get the uri of the image that we clicked
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null
                && data.getData()!=null){
            mImageUri = data.getData(); //this variable contain the uri of the image we clicked
            Picasso.with(this).load(mImageUri).into(mImageView); // or mImageView.setImageURI(mImageUri but using Picasso is better
        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile(){
        /*if(mImageUri != null){
            StorageReference fileReferenece = mStorageRef.child(System.currentTimeMillis()
            +"."+getFileExtension(mImageUri)); //it create a big number+jpg or png (the file name

            mUploadTask = fileReferenece.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(0);
                        }
                    },500);
                    Toast.makeText(MarchandiseAdd.this,"Merchandise data successfully uploaded",Toast.LENGTH_LONG).show();
                    if(reference.getText().toString().isEmpty() || description.getText().toString().isEmpty() || price.getText().toString().isEmpty()){
                        Toast.makeText(MarchandiseAdd.this,"Please fill up all the fields",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Marchandise upload = new Marchandise(taskSnapshot.getUploadSessionUri().toString(), reference.getText().toString().trim(),
                                description.getText().toString().trim(),price.getText().toString());
                        String uploadId = mDatabaseref.push().getKey();
                        mDatabaseref.child(uploadId).setValue(upload);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MarchandiseAdd.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() /snapshot.getTotalByteCount());
                    mProgressBar.setProgress((int)progress);
                }
            });
        }else{
            Toast.makeText(this,"No image is selected",Toast.LENGTH_SHORT).show();
        }*/

        if (mImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            fileReference.putFile(mImageUri).continueWithTask(
                    new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException(); }
                            return fileReference.getDownloadUrl();
                        } })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) { Uri downloadUri = task.getResult();
                            String p = price.getText().toString();
                            String pp = p.concat(" Dhs");
                                Marchandise upload = new Marchandise(downloadUri.toString(),reference.getText().toString().trim(),
                                        description.getText().toString().trim(),pp);
                                mDatabaseref.push().setValue(upload);
                                Toast.makeText(MarchandiseAdd.this, "Upload successful", Toast.LENGTH_LONG).show();
                            }
                            else { Toast.makeText(MarchandiseAdd.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MarchandiseAdd.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_LONG).show();
        }
    }

}