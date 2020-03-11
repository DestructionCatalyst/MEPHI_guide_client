package com.example.mephiguide;

import java.util.ArrayList;

public interface IOpensJson {

    void open(String content);
    void connFailed(String swearing);
    void displayJson(ArrayList a);

}
