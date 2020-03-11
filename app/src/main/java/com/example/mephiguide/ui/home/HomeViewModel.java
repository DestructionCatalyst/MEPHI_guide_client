package com.example.mephiguide.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mephiguide.IOpensJson;
import com.example.mephiguide.JSONHelper;
import com.example.mephiguide.JSONHelpers.NewsJSONHelper;
import com.example.mephiguide.NetworkTask;
import com.example.mephiguide.R;
import com.example.mephiguide.data_types.News;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel implements IOpensJson {

    public final String lnkbase="http://194.87.232.95:45555/home/";
    private final String lnkpost = "getnews?inst=";

    private MutableLiveData<String> mText;
    private MutableLiveData<ArrayList<News>> listData;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
        listData = new MutableLiveData<>();
        update();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<ArrayList<News>> getNews() {
        return listData;
    }

    public void update(){

        NetworkTask task = new NetworkTask(this);
        task.execute(lnkbase+lnkpost+"0");
    }

    @Override
    public void open(String jsonStr) {

        JSONHelper helper1 = new JSONHelper(this, new NewsJSONHelper());
        helper1.execute(jsonStr);
    }

    @Override
    public void connFailed(String swearing) {

    }

    @Override
    public void displayJson(ArrayList a) {

        Object tmp;

        for (int i = 0; i<a.size()/2; i++){//Перевернуть массив новостей, чтобы сначала были новые
            tmp = a.get(i);
            a.set(i, a.get(a.size()-1-i));
            a.set(a.size()-1-i, tmp);
        }
        listData.setValue(a);
    }
}