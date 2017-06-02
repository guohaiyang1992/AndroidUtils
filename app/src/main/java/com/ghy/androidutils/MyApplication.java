package com.ghy.androidutils;

import android.app.Application;
import android.util.Log;

/**
 * Created by guohaiyang on 2017/6/2.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("test", "hh");
    }
}
