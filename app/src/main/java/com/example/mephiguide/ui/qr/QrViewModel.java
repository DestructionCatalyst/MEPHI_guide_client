package com.example.mephiguide.ui.qr;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mephiguide.JSONHelpers.QrJSONHelper;
import com.example.mephiguide.NetworkTask;
import com.example.mephiguide.ValueKeeper;
import com.example.mephiguide.data_types.Qr;

import java.util.ArrayList;

public class QrViewModel extends ViewModel {

    private final String lnkpostQr = "getqr?nam=";

    private MutableLiveData<ArrayList<Qr>> qrArrayList;

    public QrViewModel() {

        qrArrayList = new MutableLiveData<>();
    }

    LiveData<ArrayList<Qr>> getQr(){
        return qrArrayList;
    }

    public void updateQr(String prefix){

        NetworkTask task = new NetworkTask(qrArrayList, new QrJSONHelper());
        task.execute(ValueKeeper.getInstance().lnkbase + lnkpostQr + prefix);

    }

}
