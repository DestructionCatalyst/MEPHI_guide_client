package com.example.mephiguide.data_types;

public class Group {
    private int id;
    private int idInst;
    private String name;
    public Group(int id, String name, int idInst){
        this.setId(id);
        this.setIdInst(idInst);
        this.setName(name);

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

    public int getIdInst() {
        return idInst;
    }

    public void setIdInst(int idInst) {
        this.idInst = idInst;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
