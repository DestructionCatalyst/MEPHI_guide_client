package com.example.mephiguide.data_types;

public class Dot {
    private int id;
    private int x;
    private int y;
    private String name;
    public Dot(int id, String name, int x, int y){
        this.setId(id);
        this.setName(name);
        this.setX(x);
        this.setY(y);
    }

    @Override
    public String toString(){
        return getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
