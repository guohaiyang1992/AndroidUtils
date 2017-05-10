package com.hfga.resumebooklib.utils.thread;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtils {
    private static Handler handler = null;

    static {

        handler = new Handler(Looper.getMainLooper());
    }

    private static ExecutorService mCacheExcutor = Executors.newCachedThreadPool();


    public static void runInUiThread(Runnable r) {
        if (handler != null) {
            handler.post(r);
        }
    }


    public static ExecutorService getmCacheExcutor() {
        return mCacheExcutor;
    }

    public static void runInBackGroundThread(Runnable r) {
        if (mCacheExcutor != null) {
            mCacheExcutor.execute(r);
        }
    }


    public static void ondestroy() {
        if (mCacheExcutor != null) {
            mCacheExcutor.shutdown();
        }
    }


}