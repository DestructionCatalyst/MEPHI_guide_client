package com.example.mephiguide.data_types;

public class News {
    public int id,institution,idPlace;
    public String name,t,text,place;
    byte[]top_img;
    public News(int id, int institution, int idPlace, String name, String t, String text, String place){
        this.id = id;
        this.institution = institution;
        this.idPlace = idPlace;
        this.name = name;
        this.t = t;
        this.text = text;
        this.place = place;
    }
}
