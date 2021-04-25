package com.example.managementsystem.FragementMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.managementsystem.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class viewpdf extends AppCompatActivity {
    WebView pdfview;
    private ImageView btnHide;
    private ImageView back;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //hide the toolbar
        setContentView(R.layout.activity_viewpdf);
        pdfview = (WebView) findViewById(R.id.viewpdf);
        String filename = getIntent().getStringExtra("filename");
        String fileurl = getIntent().getStringExtra("fileurl");
        ProgressDialog pd = new ProgressDialog(this);
        t = findViewById(R.id.textbar);
        t.setText("Facture");

        btnHide = findViewById(R.id.addC);
        btnHide.setVisibility(ImageView.INVISIBLE);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent myIntent = new Intent(getApplicationContext(), RetrievePdf.class);
                startActivity(myIntent);
            }
        });

        pd.setTitle(filename);
        pd.setMessage("Openning.....!!!");
        pdfview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
            }
        });
        String url = "";
        try{
            url = URLEncoder.encode(fileurl,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            //  e.printStackTrace();
        }
        pdfview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);
    }
}