package com.example.mephiguide.JSONHelpers;

import com.example.mephiguide.JSONStrategy;
import com.example.mephiguide.data_types.Reminder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class ReminderJSONHelper implements JSONStrategy {

    public ArrayList<Reminder> importFromJSON(String jsonString) {

        ArrayList<Reminder> rems;
        try{
            Gson gson = new Gson();
            rems = new ArrayList<>(Arrays.asList(gson.fromJson(jsonString, Reminder[].class)));
            return rems;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

}
