package com.example.mephiguide.data_types;

public class Dot {
    private int id, x,y;
    private String name;
    public Dot(int id, String name, int x, int y){
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return name;
    }
}
