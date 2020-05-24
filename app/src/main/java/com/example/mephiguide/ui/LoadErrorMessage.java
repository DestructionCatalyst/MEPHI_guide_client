package com.example.mephiguide.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.mephiguide.MyLog;
import com.example.mephiguide.R;

@SuppressLint("AppCompatCustomView")
public class LoadErrorMessage extends TextView {

    public LoadErrorMessage(Context context){
        super(context);
    }

    public LoadErrorMessage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void changeStatus(LEMState newStatus){
        if (newStatus == LEMState.LOAD_FINISHED)this.setVisibility(View.GONE);

        else if (newStatus == LEMState.LOAD_PROGRESS){
            this.setVisibility(View.VISIBLE);
            this.setText(R.string.lem_loading);
        }

        else if (newStatus == LEMState.LOAD_ERROR){
            this.setVisibility(View.VISIBLE);
            this.setText(R.string.lem_error);
            MyLog.d("LEM Error message displayed!");
        }
    }


}
