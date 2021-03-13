package com.example.managementsystem.FragementMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.managementsystem.R;
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
}