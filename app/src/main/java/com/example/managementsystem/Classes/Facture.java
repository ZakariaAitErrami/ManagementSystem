package com.example.managementsystem.Classes;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

public class Facture {
    private String id;
    private String date;
    private String customer;
    private ArrayList<Marchandise> merchandises;
    private String fKey;

    public Facture(String id, String date, String customer) {
        this.id = id;
        this.date = date;
        this.customer = customer;
    }

    public Facture(String id, String date, String customer, ArrayList<Marchandise> merchandises) {
        this.id = id;
        this.date = date;
        this.customer = customer;
        this.merchandises = merchandises;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public ArrayList<Marchandise> getMerchandises() {
        return merchandises;
    }

    public void setMerchandises(ArrayList<Marchandise> merchandises) {
        this.merchandises = merchandises;
    }

    @Exclude
    public String getfKey() {
        return fKey;
    }
    @Exclude
    public void setfKey(String fKey) {
        this.fKey = fKey;
    }
}
