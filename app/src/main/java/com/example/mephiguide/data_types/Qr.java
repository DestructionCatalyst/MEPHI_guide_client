package com.example.mephiguide.data_types;

public class Qr {
    private int id, idPlace;
    private String name,text;
    public Qr(int id, String name, String text, int idPlace){
        this.id = id;
        this.name = name;
        this.text = text;
        this.idPlace = idPlace;
    }
}
