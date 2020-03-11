package com.example.mephiguide.data_types;

public class Group {
    public int id, idInst;
    public String name;
    public Group(int id, String name, int idInst){
        this.id = id;
        this.idInst = idInst;
        this.name = name;

    }
    @Override
    public String toString(){
        return name;
    }
}
