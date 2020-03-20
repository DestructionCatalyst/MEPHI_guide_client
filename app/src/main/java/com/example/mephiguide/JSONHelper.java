package com.example.mephiguide;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class JSONHelper extends AsyncTask <String,Void,ArrayList>{

    private JSONStrategy jsonStrat;
    MutableLiveData target;//Making private phucks up everything? needs testing

    public JSONHelper(JSONStrategy strat, MutableLiveData target){
        this.jsonStrat = strat;
        this.target = target;
    }

    @Override
    protected ArrayList doInBackground(String... strings) {
        return jsonStrat.importFromJSON(strings[0]);
    }
    @Override
    protected void onPostExecute(ArrayList content) {
        Log.d("Connection","Parsed JSON");
        target.postValue(content);
    }
}
