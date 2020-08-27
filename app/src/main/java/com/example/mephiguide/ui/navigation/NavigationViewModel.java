package com.example.mephiguide.ui.navigation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mephiguide.IWebUtilities;
import com.example.mephiguide.JSONHelpers.DotJSONHelper;
import com.example.mephiguide.JSONHelpers.WayJSONHelper;
import com.example.mephiguide.NetworkTask;
import com.example.mephiguide.data_types.Dot;
import com.example.mephiguide.data_types.Way;

import java.util.ArrayList;

public class NavigationViewModel extends ViewModel implements IWebUtilities {

    private final String lnkpostDots = "getdots/";
    private final String lnkpostWays = "getways/";

    private MutableLiveData<ArrayList<Dot>> dotsArrayList;
    private MutableLiveData<ArrayList<Way>> waysArrayList;



    public NavigationViewModel() {
        dotsArrayList = new MutableLiveData<>();
        waysArrayList = new MutableLiveData<>();
        updateDots();
        updateWays();
    }

    LiveData<ArrayList<Dot>> getDots() {
        return dotsArrayList;
    }

    LiveData<ArrayList<Way>> getWays(){
        return waysArrayList;
    }

    private void updateDots(){
        NetworkTask task = new NetworkTask(dotsArrayList, new DotJSONHelper());
        task.execute(IWebUtilities.linkBase + lnkpostDots);
    }

    private void updateWays(){
        NetworkTask task = new NetworkTask(waysArrayList, new WayJSONHelper());
        task.execute(IWebUtilities.linkBase + lnkpostWays);
    }

}