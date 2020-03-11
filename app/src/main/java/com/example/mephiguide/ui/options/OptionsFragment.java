package com.example.mephiguide.ui.options;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mephiguide.MainActivity;
import com.example.mephiguide.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class OptionsFragment extends Fragment {

    private final String FILE_NAME = "theme";

    private Spinner spinner;


    private OptionsViewModel mViewModel;

    public static OptionsFragment newInstance() {
        return new OptionsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_options, container, false);

        spinner = root.findViewById(R.id.options_spinner);
        initializeSpinner(((MainActivity)this.getActivity()).selectedTheme);

        final TextView support = root.findViewById(R.id.options_support);
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMail();
            }
        });

        final TextView about = root.findViewById(R.id.options_about);
        about.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showMessage();
            }
        });


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(OptionsViewModel.class); //ViewModelProviders.of(this).get(OptionsViewModel.class);
        // TODO: Use the ViewModel
    }

    private void showMessage(){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Приложение «Путеводитель по НИЯУ МИФИ»\n" +
                "Версия " +getString(R.string.app_version)+"\n\n"+
                "©\n" +
                "Национальный исследовательский ядерный университет «МИФИ»,\n" +
                "Институт Интеллектуальных Кибернетических Систем (ИИКС)\n" +
                "Кафедра №22 «Кибернетика»\n" +
                "Разработано в рамках курса «Проектная практика»\n" +
                "Руководитель проекта: Немешаев Сергей Александрович\n" +
                "Куратор проекта: Дадтеев Казбек Маирбекович\n" +
                "Разработчик: Комза Владислав Петрович")
                .setTitle("О программе")
                .setPositiveButton("OK", null);
        AlertDialog dialog1 = builder1.create();
        dialog1.show();
    }

    private void openMail(){

        try
        {
            Intent contactintent = new Intent(Intent.ACTION_SENDTO);
            contactintent.setData(android.net.Uri.parse("mailto:" + "mephiapp@gmail.com"));

            startActivity(contactintent);

        }
        catch (ActivityNotFoundException anfe)
        {
            Toast.makeText(this.getActivity(), "На устройстве не найден почтовый клиент", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeSpinner(int theme){

        String [] s = new String[11];
        s[0] = "Автоматически";
        s[1] = "Стандартная";
        s[2] = "ИЯФиТ";
        s[3] = "ЛаПлаз";
        s[4] = "ИФИБ";
        s[5] = "ИНТЕЛ";
        s[6] = "ИИКС";
        s[7] = "ИФТИС";
        s[8] = "ИФТЭБ";
        s[9] = "ИМО";
        s[10] = "ФБИУКС";

        ArrayAdapter <String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, s);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);
        spinner.setSelection(theme);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != ((MainActivity)getActivity()).selectedTheme) {

                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    saveTheme(position);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    private void saveTheme(int theme){

        FileOutputStream fos;

        try {
            fos = getActivity().openFileOutput(FILE_NAME, MODE_PRIVATE);
            String write = ""+theme;
            fos.write(write.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Ошибка сохранения данных!", Toast.LENGTH_SHORT).show();
        }
    }

}
