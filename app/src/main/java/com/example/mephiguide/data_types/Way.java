package com.example.mephiguide.data_types;

public class Way {
    int id, idStart, idEnd;
    double len;

    public Way(int id, int idStart, int idEnd, double len){
        this.id = id;
        this.idStart = idStart;
        this.idEnd = idEnd;
        this.len = len;

    }

    @Override
    public String toString(){
        return id+" "+idStart+" "+idEnd+" "+len;
    }
}
