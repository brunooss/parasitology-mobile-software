package com.android;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        getMyApplicationContext();
    }

    public static Context getMyApplicationContext() {
        return context;
    }
}
