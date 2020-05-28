package com.example.mephiguide.ui.reminder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mephiguide.FileHelper;
import com.example.mephiguide.MyLog;
import com.example.mephiguide.R;
import com.example.mephiguide.data_types.Reminder;
import com.example.mephiguide.ui.LoadErrorMessage.LEMState;
import com.example.mephiguide.ui.LoadErrorMessage.LoadErrorMessage;

import java.util.ArrayList;
import java.util.HashSet;

public class ReminderFragment extends Fragment {

    private final String FILE_NAME_REMINDERS = "reminders";

    private ReminderViewModel reminderViewModel;

    private ListView listView;
    private Switch sw;
    private LoadErrorMessage lem;

    private ArrayList<Reminder> reminders;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reminderViewModel =
                 new ViewModelProvider(this).get(ReminderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reminder, container, false);

        reminderViewModel.getReminders().observe(getViewLifecycleOwner(), new Observer<ArrayList>() {
            @Override
            public void onChanged(@Nullable ArrayList rems) {

                if(rems != null) {
                    reminders = rems;
                    setRemindersAdapter();
                    loadReminders();
                    lem.changeStatus(LEMState.LOAD_FINISHED);
                }
                else {
                    MyLog.w("Unable to load reminders!");
                    lem.changeStatus(LEMState.LOAD_ERROR);
                }
            }
        });

        listView = root.findViewById(R.id.reminder_listView);

        sw = root.findViewById(R.id.reminder_switch);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setRemindersAdapter();
            }
        });

        lem = root.findViewById(R.id.reminder_lem);
        lem.changeStatus(LEMState.LOAD_PROGRESS);

        return root;
    }

    void setRemindersAdapter(){

        ArrayList show;
        if (sw.isChecked()) {
            show = new ArrayList<Reminder>();
            for (Reminder cur : reminders) {
                if (!cur.isChecked()) show.add(cur);
            }
        }
        else{
            show = reminders;
        }
        ReminderAdapter reminderAdapter = new ReminderAdapter(this.getActivity(), this, show);
        listView.setAdapter(reminderAdapter);
    }

    void saveReminders(){
        MyLog.i("Saving reminders");
        StringBuilder write = new StringBuilder();
        for (Reminder cur: reminders) {
            if (cur.isChecked()) write.append(cur.getID()).append("~");
        }
        FileHelper fhelp = new FileHelper(this.getActivity());
        fhelp.writeFile(FILE_NAME_REMINDERS, write.toString());
    }

    private void loadReminders(){
        MyLog.i("Loading reminders");
        FileHelper fhelp = new FileHelper(this.getActivity());
        String [] tmp= fhelp.readFile(FILE_NAME_REMINDERS).split("~");
        HashSet ids = new HashSet();
        int id = 0;

        for (String s : tmp) {
            if (!s.equals("")) id = Integer.parseInt(s);
            ids.add(id);
        }

        Reminder cur;
        for (int i = 0; i<this.reminders.size(); i++){
            cur = reminders.get(i);
            if(ids.contains(cur.getID()))
                cur.setChecked(true);
        }

    }

    public boolean getSwitchChecked(){
        return sw.isChecked();
    }

}