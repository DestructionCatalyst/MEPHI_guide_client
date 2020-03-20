package com.example.mephiguide.JSONHelpers;

import com.example.mephiguide.JSONStrategy;
import com.example.mephiguide.data_types.Way;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class WayJSONHelper implements JSONStrategy {

    public ArrayList<Way> importFromJSON(String jsonString) {

        ArrayList<Way> ways;
        try{
            Gson gson = new Gson();
            ways = new ArrayList<>(Arrays.asList(gson.fromJson(jsonString, Way[].class)));
            return ways;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
