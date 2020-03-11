package com.example.mephiguide.JSONHelpers;

import com.example.mephiguide.JSONStrategy;
import com.example.mephiguide.data_types.Group;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupsJSONHelper implements JSONStrategy {
    public ArrayList<Group> importFromJSON(String jsonString) {

        ArrayList<Group> groups;
        try{
            Gson gson = new Gson();
            groups = new ArrayList<>(Arrays.asList(gson.fromJson(jsonString, Group[].class)));
            return groups;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

}