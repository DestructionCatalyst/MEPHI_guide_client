package com.example.mephiguide.JSONHelpers;

import com.example.mephiguide.JSONStrategy;
import com.example.mephiguide.MyLog;
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
            groups.add(0,(new Group(0,"(Гость)",0)));
            return groups;
        }
        catch (Exception ex){
            MyLog.e("Error reading JSON content: Ways", ex);
            ex.printStackTrace();
        }
        return null;
    }

}