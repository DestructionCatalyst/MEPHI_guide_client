package com.example.mephiguide.ui.reminder;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.example.mephiguide.R;
import com.example.mephiguide.data_types.Reminder;

import java.util.ArrayList;

public class ReminderAdapter extends BaseAdapter {

    private LayoutInflater lInflater;
    private ArrayList<Reminder> objects;
    private ReminderFragment fragment;

    ReminderAdapter(Context context, ReminderFragment frag, ArrayList<Reminder> reminders) {
        objects = reminders;
        fragment = frag;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.remindersitem, parent, false);
        }

        final Reminder rem = getReminder(position);

        ((TextView) view.findViewById(R.id.tvDescr)).setText(rem.getName());
        ((TextView) view.findViewById(R.id.tvPrice)).setText(rem.getTime());

        final View finalView = view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("data", fragment.getString(R.string.web_start) +
                        "<h4>" + rem.getName() + "</h4><br>" + rem.getText() +
                        "<br>Место: " + rem.getPlace() + "<br>Сроки: " + rem.getTime() +
                        fragment.getString(R.string.web_end));
                Navigation.findNavController(finalView).navigate(R.id.action_navigation_reminder_to_navigation_html, bundle);

            }
        });

        CheckBox cbDone = view.findViewById(R.id.cbBox);
        // присваиваем чекбоксу обработчик
        cbDone.setOnCheckedChangeListener(myCheckChangeList);
        // пишем позицию
        cbDone.setTag(position);
        // заполняем данными из напоминаний
        cbDone.setChecked(rem.isChecked());
        return view;
    }

    // напом. по позиции
    Reminder getReminder(int position) {
        return ((Reminder) getItem(position));
    }


    // обработчик для чекбоксов
    OnCheckedChangeListener myCheckChangeList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {

            getReminder((Integer) buttonView.getTag()).setChecked(isChecked);
            fragment.saveReminders();
            if (fragment.getSwitchChecked())
                fragment.setRemindersAdapter();
        }
    };
}
