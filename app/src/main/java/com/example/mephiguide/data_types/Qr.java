package com.example.mephiguide.data_types;

public class Qr {
    private int  id;
    private int idPlace;
    private String name;
    private String text;
    public Qr(int id, String name, String text, int idPlace){
        this.setId(id);
        this.setName(name);
        this.setText(text);
        this.setIdPlace(idPlace);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
