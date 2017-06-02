package com.android.utils.common;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtils {
    private ThreadUtils() {
        throw new AssertionError();
    }

    //--运行相关--
    private static Handler handler = null;

    static {

        handler = new Handler(Looper.getMainLooper());
    }

    private static ExecutorService mCacheExcutor = Executors.newCachedThreadPool();

    /**
     * 在主线程中运行
     *
     * @param r --需要运行的runnable
     */
    public static void runInUiThread(Runnable r) {
        if (handler != null) {
            handler.post(r);
        }
    }


    public static ExecutorService getmCacheExcutor() {
        return mCacheExcutor;
    }

    /**
     * 在子线程中运行
     *
     * @param r --需要运行的runnable
     */
    public static void runInBackGroundThread(Runnable r) {
        if (mCacheExcutor != null) {
            mCacheExcutor.execute(r);
        }
    }

    /**
     * 销毁
     */
    public static void ondestroy() {
        if (mCacheExcutor != null) {
            mCacheExcutor.shutdown();
        }
    }
    //--判断相关--

    /**
     * 判断是不是主线程
     *
     * @return
     */
    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /**
     * 判断是不是后台线程
     *
     * @return
     */
    public static boolean isBackThread() {
        return !isMainThread();
    }

    /**
     * 检查当前运行的线程是否在活动状态
     *
     * @return
     */
    public static boolean checkIsAlive() {
        return Thread.currentThread().isAlive();
    }


}