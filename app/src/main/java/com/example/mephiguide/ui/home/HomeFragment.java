package com.example.mephiguide.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mephiguide.FileHelper;
import com.example.mephiguide.MainActivity;
import com.example.mephiguide.R;
import com.example.mephiguide.ValueKeeper;
import com.example.mephiguide.data_types.Group;
import com.example.mephiguide.data_types.News;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private final String FILE_NAME_GROUP = "group";
    private final String FILE_NAME_AUTO = "autotheme";
    private final String FILE_NAME_CHECK = "checked";
    private HomeViewModel homeViewModel;

    private ListView listView;
    private Spinner spinner;
    private Switch sw;
    private TextView textView;
    private ArrayList<Group> groups;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel.getNews().observe(getViewLifecycleOwner(), new Observer<ArrayList<News>>() {
            @Override
            public void onChanged(ArrayList<News> news) {
                setNewsAdapter(news);

            }
        });

        homeViewModel.getGroups().observe(getViewLifecycleOwner(), new Observer<ArrayList<Group>>() {
            @Override
            public void onChanged(ArrayList<Group> groups) {
                setGroupsAdapter(groups);

            }
        });

        listView = root.findViewById(R.id.home_listView);
        spinner = root.findViewById(R.id.home_spinner_groups);
        sw = root.findViewById(R.id.home_switch_target_mode);
        textView = root.findViewById(R.id.home_textView_forme);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean reset = false;
                if (((Group)parent.getItemAtPosition(position)).id != ValueKeeper.getInstance().curGroup.id)
                    reset = true;
                ValueKeeper.getInstance().curGroup = (Group)parent.getItemAtPosition(position);
                Log.d("Mics", ValueKeeper.getInstance().curGroup.name);
                changeGroup();
                if (reset) ((MainActivity)getActivity()).reset();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sw.setChecked(loadChecked());
        sw.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveChecked(isChecked);
                if (sw.getVisibility() == View.VISIBLE) {//If it is visible, then not a 'guest' is selected
                    refresh(isChecked);
                }
                else refresh(false);
            }
        });

        return root;
    }

    void setNewsAdapter(ArrayList<News> news){
        NewsAdapter newsAdapter = new NewsAdapter(this.getActivity(), this, news);
        listView.setAdapter(newsAdapter);
    }

    void setGroupsAdapter(ArrayList<Group> groups){
        this.groups = groups;
        readFile();
        ArrayAdapter groupAdapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, groups);
        groupAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(groupAdapter);
        spinner.setSelection(ValueKeeper.getInstance().curGroup.id);
    }

    void changeGroup(){

        FileHelper fhelp = new FileHelper(this.getActivity());
        fhelp.writeFile(FILE_NAME_GROUP, "" + ValueKeeper.getInstance().curGroup.id);
        fhelp.writeFile(FILE_NAME_AUTO, ""+ValueKeeper.getInstance().curGroup.idInst);

        if (ValueKeeper.getInstance().curGroup.idInst == 0){
            sw.setChecked(false);
            sw.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
        }
        else {

            refresh(sw.isChecked());
            sw.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    void refresh(boolean targeting){
        if(targeting){
            homeViewModel.updateNews(ValueKeeper.getInstance().curGroup.idInst);
        }
        else{
            homeViewModel.updateNews(0);
        }
    }

    private void readFile(){

        FileHelper fhelp = new FileHelper(this.getActivity());
        String toRead = fhelp.readFile(FILE_NAME_GROUP);
        if ((toRead.equals(""))||(toRead.equals("0"))){//Если файла нет, он пуст или там гость, то у нас гость
            ValueKeeper.getInstance().curGroup = new Group(0,"(Гость)",0);
        }
        else {
            try {
                int tmp = Integer.parseInt(toRead);
                ValueKeeper.getInstance().curGroup = groups.get(tmp);
            }
            catch (NumberFormatException e){
                ValueKeeper.getInstance().curGroup = new Group(0,"(Гость)",0);
            }

        }
    }

    void saveChecked(boolean checked){
        String toWrite = checked ? "true" : "false";
        FileHelper fhelp = new FileHelper(this.getActivity());
        fhelp.writeFile(FILE_NAME_CHECK, toWrite);
    }

    boolean loadChecked(){
        FileHelper fhelp = new FileHelper(this.getActivity());
        String tmp = fhelp.readFile(FILE_NAME_CHECK);
        if (tmp.equals("true"))
            return true;
        else
            return false;
    }
}
