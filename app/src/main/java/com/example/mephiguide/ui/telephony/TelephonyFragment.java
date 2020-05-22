package com.example.mephiguide.ui.telephony;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mephiguide.R;

import java.util.ArrayList;

public class TelephonyFragment extends Fragment {

    private TelephonyViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_telephony, container, false);

        ListView listView = root.findViewById(R.id.telephony_listView);
        TelephonyAdapter adapter = new TelephonyAdapter(this.getActivity(), this, makeContactList());
        listView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TelephonyViewModel.class);
        // TODO: Use the ViewModel
    }

    private ArrayList<Contact> makeContactList(){
        ArrayList<Contact> contacts = new ArrayList<>();

        contacts.add(new Contact("Основные контакты:", "", true));
        contacts.add(new Contact("Основной номер МИФИ", "+7(495)788-56-99", false));
        contacts.add(new Contact("Приёмная комиссия (бесплатно по Москве)", "+7(495)785-55-25", false));
        contacts.add(new Contact("Приёмная комиссия (бесплатно по всем регионам)", "+7(800)775-15-51", false));
        contacts.add(new Contact("Поликлиника №1 НИЯУ МИФИ)", "+7(499)324-74-06", false));
        contacts.add(new Contact("Дополнительные контакты:", "", true));
        contacts.add(new Contact("Управление информатизации", "+7(495)788-56-99#9999", false));
        contacts.add(new Contact("Отдел безопасности", "+7(495)788-56-99#9737", false));
        contacts.add(new Contact("Отдел платных образовательных услуг", "+7(495)788-56-99#9956", false));
        contacts.add(new Contact("Учебные отделы:", "", true));
        contacts.add(new Contact("Институт ядерной физики и технологий (ИЯФиТ)", "+7(495)788-56-99#8442", false));
        contacts.add(new Contact("Институт лазерных и плазменных технологий (ЛаПлаз)", "+7(495)788-56-99#9752", false));
        contacts.add(new Contact("Инженерно-физический институт биомедицины (ИФИБ)", "+7(495)788-56-99#8993", false));
        contacts.add(new Contact("Институт нанотехнологий в электронике, спинтронике и фотонике (ИНТЭЛ)", "+7(495)788-56-99#8575", false));
        contacts.add(new Contact("Институт интеллектуальных кибернетических систем (ИИКС)", "+7(495)788-56-99#8995", false));
        contacts.add(new Contact("Институт финансовых технологий и экономической безопасности (ИФТЭБ)", "+7(495)788-56-99#9973", false));
        contacts.add(new Contact("Институт международных отношений (ИМО)", "+7(495)788-56-99#9671", false));
        contacts.add(new Contact("Институт физико-технических интеллектуальных систем (ИФТИС)", "+7(495)788-56-99#8442", false));
        contacts.add(new Contact("Факультет бизнес-информатики и управления комплексными системами (ФБИУКС)", "+7(495)788-56-99#9671", false));
        contacts.add(new Contact("Факультет очно-заочного (вечернего) обучения", "+7(499)324-71-04", false));


        return contacts;
    }

}
