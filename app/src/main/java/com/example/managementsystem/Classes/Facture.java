package com.example.managementsystem.Classes;

import java.util.ArrayList;
import java.util.List;

public class Facture {
    private String id;
    private String date;
    private String name;
    private ArrayList<Marchandise> marchandises;

    public Facture(String id, String date, String name, ArrayList<Marchandise> marchandises) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.marchandises = marchandises;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Marchandise> getMarchandises() {
        return marchandises;
    }

    public void setMarchandises(ArrayList<Marchandise> marchandises) {
        this.marchandises = marchandises;
    }
}
