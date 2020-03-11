package com.example.mephiguide.JSONHelpers;

import com.example.mephiguide.JSONStrategy;
import com.example.mephiguide.data_types.News;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class NewsJSONHelper implements JSONStrategy {

    public ArrayList<News> importFromJSON(String jsonString)
    {
        ArrayList<News> newss;
        try{
            Gson gson = new Gson();
            newss = new ArrayList<>(Arrays.asList(gson.fromJson(jsonString, News[].class)));
            return newss;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

}
