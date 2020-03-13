package com.example.mephiguide;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;

public class JSONHelper extends AsyncTask <String,Void,ArrayList>{
    private JSONStrategy jsonStrat;
    private IOpensJson context;
    MutableLiveData target;

    public JSONHelper(IOpensJson context, JSONStrategy strat, MutableLiveData target){
        this.context = context;
        this.jsonStrat = strat;
        this.target = target;
    }

    @Override
    protected ArrayList doInBackground(String... strings) {
        return jsonStrat.importFromJSON(strings[0]);
    }
    @Override
    protected void onPostExecute(ArrayList content) {
        if (context!=null){

            target.postValue(content);
        }

    }
}
