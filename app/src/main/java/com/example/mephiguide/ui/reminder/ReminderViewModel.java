package com.example.mephiguide.ui.reminder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mephiguide.IWebUtilities;
import com.example.mephiguide.JSONHelpers.ReminderJSONHelper;
import com.example.mephiguide.NetworkTask;
import com.example.mephiguide.data_types.Reminder;

import java.util.ArrayList;

public class ReminderViewModel extends ViewModel implements IWebUtilities {

    private final String lnkpostReminders = "getrem/";

    private MutableLiveData<ArrayList<Reminder>> remindersArrayList;

    public ReminderViewModel() {

        remindersArrayList = new MutableLiveData<>();
        updateReminders();
    }

    LiveData<ArrayList<Reminder>> getReminders(){
        return remindersArrayList;
    }

    private void updateReminders(){

        NetworkTask task = new NetworkTask(remindersArrayList, new ReminderJSONHelper());
        task.execute(IWebUtilities.linkBase + lnkpostReminders);

    }

}