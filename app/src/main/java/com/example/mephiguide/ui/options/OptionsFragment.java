package com.example.mephiguide.ui.options;

import androidx.core.app.TaskStackBuilder;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class OptionsFragment extends Fragment {

    final String FILE_NAME = "theme";

    String s [] = new String[4];

    Spinner spinner;


    private OptionsViewModel mViewModel;

    public static OptionsFragment newInstance() {
        return new OptionsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_options, container, false);

        spinner = root.findViewById(R.id.spinner);
        initializeSpinner(((MainActivity)this.getActivity()).selectedTheme);

        final TextView about = root.findViewById(R.id.about);
        about.setText(getString(R.string.lorem_ipsum)+getString(R.string.lorem_ipsum));

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(OptionsViewModel.class);
        // TODO: Use the ViewModel
    }

    private void initializeSpinner(int theme){
        s[0] = "Автоматически";
        s[1] = "Стандартная";
        s[2] = "ИЯФиТ";
        s[3] = "ЛаПлаз";


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
                    //intent.putExtra("position", position);
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

    void saveTheme(int theme){

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
