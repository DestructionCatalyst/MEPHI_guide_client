package com.example.mephiguide.JSONHelpers;

import com.example.mephiguide.JSONStrategy;
import com.example.mephiguide.data_types.Qr;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class QrJSONHelper implements JSONStrategy {

    public ArrayList<Qr> importFromJSON(String jsonString) {
        ArrayList<Qr> qrs;

        try{

            Gson gson = new Gson();
            qrs = new ArrayList<>(Arrays.asList(gson.fromJson(jsonString, Qr[].class)));
            if (qrs.isEmpty()){return null;}
            else return qrs;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

}