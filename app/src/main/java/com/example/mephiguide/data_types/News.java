package com.example.mephiguide.data_types;

public class News {
    private int id;
    private int institution;
    private int idPlace;
    private String name;
    private String t;
    private String text;
    private String place;
    private byte[]top_img;
    public News(int id, int institution, int idPlace, String name, String t, String text, String place){
        this.setId(id);
        this.institution = institution;
        this.idPlace = idPlace;
        this.setName(name);
        this.setT(t);
        this.setText(text);
        this.setPlace(place);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
