package com.example.mephiguide.ui.home;

import android.os.Bundle;
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
import com.example.mephiguide.MyLog;
import com.example.mephiguide.R;
import com.example.mephiguide.ValueKeeper;
import com.example.mephiguide.data_types.Group;
import com.example.mephiguide.data_types.News;
import com.example.mephiguide.ui.LoadErrorMessage.LoadErrorMessage;
import com.example.mephiguide.ui.LoadErrorMessage.LoadStateController;

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

    private LoadErrorMessage lem;
    private LoadStateController lsc;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel.getNews().observe(getViewLifecycleOwner(), new Observer<ArrayList<News>>() {
            @Override
            public void onChanged(ArrayList<News> news) {
                if (news != null) {
                    setNewsAdapter(news);
                    lsc.increaseLevel();
                }
                else {
                    MyLog.w("Unable to load news!");
                    lsc.showError();
                }
            }
        });

        homeViewModel.getGroups().observe(getViewLifecycleOwner(), new Observer<ArrayList<Group>>() {
            @Override
            public void onChanged(ArrayList<Group> groups) {
                if (groups != null) {
                    setGroupsAdapter(groups);
                    lsc.increaseLevel();

                }
                else {
                    MyLog.w("Unable to load groups!");
                    lsc.showError();
                }
            }
        });

        listView = root.findViewById(R.id.home_listView);

        spinner = root.findViewById(R.id.home_spinner_groups);
        setSpinnerListener();

        sw = root.findViewById(R.id.home_switch_target_mode);
        sw.setChecked(loadChecked());
        setSwitchListener();

        textView = root.findViewById(R.id.home_textView_forme);

        lem = root.findViewById(R.id.home_lem);
        lsc = new LoadStateController(lem, 2);


        return root;
    }

    private void setNewsAdapter(ArrayList<News> news){
        NewsAdapter newsAdapter = new NewsAdapter(this.getActivity(), this, news);
        listView.setAdapter(newsAdapter);
        MyLog.i("News refreshed!");
    }

    private void setGroupsAdapter(ArrayList<Group> groups){
        this.groups = groups;
        readGroupsFile();
        ArrayAdapter groupAdapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, groups);
        groupAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(groupAdapter);
        spinner.setSelection(ValueKeeper.getInstance().curGroup.getId());
    }

    private void changeGroup(){
        MyLog.i("Group changed to " + ValueKeeper.getInstance().curGroup.getName());

        FileHelper fhelp = new FileHelper(this.getActivity());
        fhelp.writeFile(FILE_NAME_GROUP, "" + ValueKeeper.getInstance().curGroup.getId());
        fhelp.writeFile(FILE_NAME_AUTO, ""+ ValueKeeper.getInstance().curGroup.getIdInst());

        if (ValueKeeper.getInstance().curGroup.getIdInst() == 0){
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

    private void refresh(boolean targeting){
        MyLog.i("Refreshing news...");
        if(targeting){
            homeViewModel.updateNews(ValueKeeper.getInstance().curGroup.getIdInst());
        }
        else{
            homeViewModel.updateNews(0);
        }
    }

    private void readGroupsFile(){

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
                MyLog.w("Group file corrupted!");
                ValueKeeper.getInstance().curGroup = new Group(0,"(Гость)",0);
            }

        }
    }

    private void saveChecked(boolean checked){
        String toWrite = checked ? "true" : "false";
        FileHelper fhelp = new FileHelper(this.getActivity());
        fhelp.writeFile(FILE_NAME_CHECK, toWrite);
    }

    private boolean loadChecked(){
        FileHelper fhelp = new FileHelper(this.getActivity());
        String tmp = fhelp.readFile(FILE_NAME_CHECK);
        if (tmp.equals("true"))
            return true;
        else
            return false;
    }

    private void setSpinnerListener(){

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean reset = false;
                if ((((Group) parent.getItemAtPosition(position)).getId() != ValueKeeper.getInstance().curGroup.getId())
                        && (((MainActivity)getActivity()).selectedTheme == 0))
                    reset = true;

                ValueKeeper.getInstance().curGroup = (Group)parent.getItemAtPosition(position);
                changeGroup();
                if (reset) ((MainActivity)getActivity()).reset();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSwitchListener(){

        sw.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveChecked(isChecked);
                refresh(isChecked);
            }
        });
    }
}
