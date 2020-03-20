package com.example.mephiguide.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.mephiguide.R;

@SuppressLint("AppCompatCustomView")
public class LoadErrorMessage extends TextView {
    public static final int LOAD_PROGRESS = 1;
    public static final int LOAD_ERROR = 2;
    public static final int LOAD_FINISHED = 0;

    public LoadErrorMessage(Context context){
        super(context);
    }

    public LoadErrorMessage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void changeStatus(int newStatus){
        if (newStatus == LOAD_FINISHED)this.setVisibility(View.GONE);

        else if (newStatus == LOAD_PROGRESS){
            this.setVisibility(View.VISIBLE);
            this.setText(R.string.lem_loading);
        }

        else if (newStatus == LOAD_ERROR){
            this.setVisibility(View.VISIBLE);
            this.setText(R.string.lem_error);
            Log.d("LoadErrorMessage", "Error message displayed!");
        }
    }


}
