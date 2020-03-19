package com.example.mephiguide.ui.reminder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mephiguide.JSONHelpers.ReminderJSONHelper;
import com.example.mephiguide.NetworkTask;
import com.example.mephiguide.ValueKeeper;
import com.example.mephiguide.data_types.Reminder;

import java.util.ArrayList;

public class ReminderViewModel extends ViewModel {

    private final String lnkpostReminders = "getrem/";

    private MutableLiveData<ArrayList<Reminder>> remindersArrayList;
    private MutableLiveData<String> mText;

    public ReminderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is reminder fragment");

        remindersArrayList = new MutableLiveData<>();
        updateReminders();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<ArrayList<Reminder>> getReminders (){
        return remindersArrayList;
    }

    public void updateReminders(){

        NetworkTask task = new NetworkTask(remindersArrayList, new ReminderJSONHelper());
        task.execute(ValueKeeper.getInstance().lnkbase + lnkpostReminders);

    }

}