package com.example.mephiguide;

import android.util.Log;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class MyLog {

    private static final MyLog ourInstance = new MyLog();

    public static MyLog getInstance() {
        return ourInstance;
    }

    private MyLog() {}
    
    private static final String TAG = "MyLogs";

    public static void v(String message){
        Log.v(TAG, message);
    }
    public static void d(String message){
        Log.d(TAG, message);
    }
    public static void i(String message){
        Log.i(TAG, message);
    }
    public static void w(String message){
        Log.w(TAG, message);
        FirebaseCrashlytics.getInstance().log(message);
    }
    public static void e(String message){
        Log.e(TAG, message);
        FirebaseCrashlytics.getInstance().log(message);
    }
    public static void e(String message, Throwable tr){
        Log.e(TAG, message, tr);
        FirebaseCrashlytics.getInstance().log(message);
        FirebaseCrashlytics.getInstance().recordException(tr);
    }
    public static void wtf(String message){
        Log.wtf(TAG, message);
        FirebaseCrashlytics.getInstance().log(message);
    }
    public static void wtf(String message, Throwable tr){
        Log.wtf(TAG, message, tr);
        FirebaseCrashlytics.getInstance().log(message);
        FirebaseCrashlytics.getInstance().recordException(tr);
    }

}
