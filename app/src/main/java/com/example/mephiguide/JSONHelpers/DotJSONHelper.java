package com.example.mephiguide.JSONHelpers;

import com.example.mephiguide.JSONStrategy;
import com.example.mephiguide.data_types.Dot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class DotJSONHelper implements JSONStrategy {

    public ArrayList<Dot> importFromJSON(String jsonString) {

        ArrayList<Dot> dots;
        try{
            Gson gson = new Gson();
            dots = new ArrayList<>(Arrays.asList(gson.fromJson(jsonString, Dot[].class)));
            return dots;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

}
