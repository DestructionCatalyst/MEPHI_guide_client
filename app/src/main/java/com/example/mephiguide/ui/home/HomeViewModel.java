package com.example.mephiguide.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mephiguide.JSONHelpers.GroupsJSONHelper;
import com.example.mephiguide.JSONHelpers.NewsJSONHelper;
import com.example.mephiguide.NetworkTask;
import com.example.mephiguide.ValueKeeper;
import com.example.mephiguide.data_types.Group;
import com.example.mephiguide.data_types.News;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private final String lnkpostNews = "getnews?inst=";
    private final String lnkpostGroups = "getgroups/";

    private MutableLiveData<ArrayList<News>> newsArrayList;
    private MutableLiveData<ArrayList<Group>> groupArrayList;

    public HomeViewModel() {

        newsArrayList = new MutableLiveData<>();
        groupArrayList = new MutableLiveData<>();
        updateNews(0);
        updateGroups();
    }

    public LiveData<ArrayList<News>> getNews() {
        return newsArrayList;
    }

    public LiveData<ArrayList<Group>> getGroups(){
        return groupArrayList;
    }

    public void updateNews(int group){

        NetworkTask task = new NetworkTask(newsArrayList, new NewsJSONHelper());
        task.execute(ValueKeeper.getInstance().lnkbase + lnkpostNews + group);

    }

    public void updateGroups(){

        NetworkTask task1 = new NetworkTask(groupArrayList, new GroupsJSONHelper());
        task1.execute(ValueKeeper.getInstance().lnkbase + lnkpostGroups);
    }

}