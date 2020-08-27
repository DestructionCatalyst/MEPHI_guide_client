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
import com.example.mephiguide.data_types.Group;
import com.example.mephiguide.data_types.News;
import com.example.mephiguide.ui.LoadErrorMessage.LoadErrorMessage;
import com.example.mephiguide.ui.LoadErrorMessage.LoadStateController;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private final String FILE_NAME_CHECK = "checked";

    private HomeViewModel homeViewModel;

    private ListView listView;
    private Spinner spinner;
    private Switch sw;
    private TextView textView;

    private LoadErrorMessage lem;
    private LoadStateController lsc;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        initializeUIComponents(root);

        initializeViewModel();

        homeViewModel.updateGroups();

        //updateNews(sw.isChecked());
        homeViewModel.preLoadNews((MainActivity) getActivity(), sw.isChecked());

        return root;
    }

    private void initializeUIComponents(View root) {
        listView = root.findViewById(R.id.home_listView);

        spinner = root.findViewById(R.id.home_spinner_groups);
        setSpinnerListener(spinner);

        sw = root.findViewById(R.id.home_switch_target_mode);
        sw.setChecked(loadChecked());
        setSwitchListener(sw);

        textView = root.findViewById(R.id.home_textView_forme);

        lem = root.findViewById(R.id.home_lem);
        lsc = new LoadStateController(lem, 2);
    }

    private void initializeViewModel() {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

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
    }

    private void setNewsAdapter(ArrayList<News> news){
        NewsAdapter newsAdapter = new NewsAdapter(this.getActivity(), this, news);
        listView.setAdapter(newsAdapter);
        MyLog.i("News refreshed!");
    }

    private void setGroupsAdapter(ArrayList<Group> groups){
        homeViewModel.readGroupsFile(getActivity());
        ArrayAdapter groupAdapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, groups);
        groupAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(groupAdapter);
        spinner.setSelection(homeViewModel.getCurrentGroup().getId());

    }

    private void handleGroupChange(){

        if (homeViewModel.getCurrentGroup().getIdInst() == 0){
            sw.setChecked(false);
            sw.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
        }
        else {

            updateNews(sw.isChecked());
            sw.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    private void updateNews(boolean targeting){
        MyLog.i("Refreshing news...");

        homeViewModel.updateNews(targeting);
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

    private void setSpinnerListener(Spinner spinner){

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean reset = homeViewModel.changeGroup((Group)parent.getItemAtPosition(position),
                        ((MainActivity)getActivity()).selectedTheme, getActivity());
                handleGroupChange();
                if (reset) ((MainActivity)getActivity()).reset();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSwitchListener(Switch sw){

        sw.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveChecked(isChecked);
                updateNews(isChecked);
            }
        });
    }
}
