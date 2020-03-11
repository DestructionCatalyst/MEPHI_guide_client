package com.example.mephiguide;

import java.util.ArrayList;

public interface IOpensJson {

    void open(String jsonStr);
    void connFailed(String swearing);
    void displayJson(ArrayList a);

}
