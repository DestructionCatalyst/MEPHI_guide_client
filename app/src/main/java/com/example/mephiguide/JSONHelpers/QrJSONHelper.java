package com.example.mephiguide.JSONHelpers;

import com.example.mephiguide.JSONStrategy;
import com.example.mephiguide.MyLog;
import com.example.mephiguide.data_types.Qr;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

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
        catch (JsonSyntaxException e){
            MyLog.i("Scanned QR is not in the database");
        }
        catch (Exception ex){
            MyLog.e("Error reading JSON content: QR", ex);
            ex.printStackTrace();
        }

        return null;
    }

}