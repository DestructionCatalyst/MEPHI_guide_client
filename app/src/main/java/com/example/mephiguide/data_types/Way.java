package com.example.mephiguide.data_types;

public class Way {
    private int id;
    private int idStart;
    private int idEnd;
    private double len;

    public Way(int id, int idStart, int idEnd, double len){
        this.setId(id);
        this.setIdStart(idStart);
        this.setIdEnd(idEnd);
        this.setLen(len);

    }

    @Override
    public String toString(){
        return getId() +" "+ getIdStart() +" "+ getIdEnd() +" "+ getLen();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdStart() {
        return idStart;
    }

    public void setIdStart(int idStart) {
        this.idStart = idStart;
    }

    public int getIdEnd() {
        return idEnd;
    }

    public void setIdEnd(int idEnd) {
        this.idEnd = idEnd;
    }

    public double getLen() {
        return len;
    }

    public void setLen(double len) {
        this.len = len;
    }
}
