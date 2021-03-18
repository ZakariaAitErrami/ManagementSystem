package com.example.managementsystem.Classes;

import com.google.firebase.database.Exclude;

public class Marchandise {
    private String mImageUrl;
    private String reference;
    private String description;
    private String price;
    private String mKey;
    private int quantity;
    public Marchandise(){

    }

    public Marchandise(String mImageUrl, String reference, String description, String price) {
        this.mImageUrl = mImageUrl;
        this.reference = reference;
        this.description = description;
        this.price = price;
    }
    public Marchandise(String description,String price,int quantity){
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    @Exclude
    public String getKey() {
        return mKey;
    }
    @Exclude
    public void setKey(String mKey) {
        this.mKey = mKey;
    }
}
