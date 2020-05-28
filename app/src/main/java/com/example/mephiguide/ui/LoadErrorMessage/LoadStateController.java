package com.example.mephiguide.ui.LoadErrorMessage;

import com.example.mephiguide.MyLog;

public class LoadStateController {

    private int loadLevel;
    private int maxLevel;
    private LoadErrorMessage lem;
    private OnLoadedListener listener;


    public LoadStateController(LoadErrorMessage l, final int MAXLEVEL){
        loadLevel = 0;
        maxLevel = MAXLEVEL;
        if (l != null) {
            lem = l;
            lem.changeStatus(LEMState.LOAD_PROGRESS);
        }
    }

    public void increaseLevel(int add){
        loadLevel += add;
        if (loadLevel >= maxLevel){
            if (lem != null)
                lem.changeStatus(LEMState.LOAD_FINISHED);
            loadLevel = 0;

            MyLog.i("Loading finished");

            if (listener != null)
                listener.onLoaded();
        }
    }
    public void increaseLevel(){
        increaseLevel(1);
    }

    public void showError(){
        if(lem != null)
            lem.changeStatus(LEMState.LOAD_ERROR);
        loadLevel = 0;
    }

    public interface OnLoadedListener{

        void onLoaded();
    }

    public void setOnLoadedListener(OnLoadedListener l){
        listener = l;
    }
}
