package com.example.managementsystem.Classes;

public class Marchandise {
    private String mImageUrl;
    private String reference;
    private String description;
    private String price;
    public Marchandise(){

    }

    public Marchandise(String mImageUrl, String reference, String description, String price) {
        this.mImageUrl = mImageUrl;
        this.reference = reference;
        this.description = description;
        this.price = price;
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
}
