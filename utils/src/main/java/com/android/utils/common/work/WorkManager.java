package com.android.utils.common.work;


import android.os.Handler;
import android.os.Looper;
import android.os.Process;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 用于添加work,处理work
 *
 * @author Simon
 */
public class WorkManager implements IWorkManager, Runnable {
    private Thread workThread = null;
    private LinkedBlockingQueue<IWork> workBlockingDeque = null;
    private Handler handler = null;
    private boolean isStop = false;
    private boolean isRunning = false;
    private List<Runnable> handlerRunnableList = null;

    /**
     * 构造方法
     */
    public WorkManager() {
        this("WorkManager_" + System.currentTimeMillis());
    }

    /**
     * 构造方法
     *
     * @param name workManagerName
     */
    public WorkManager(String name) {
        workThread = new Thread(this, name);
        workBlockingDeque = new LinkedBlockingQueue<>();
        handler = new Handler(Looper.getMainLooper());
        handlerRunnableList = new ArrayList<>();
    }

    /**
     * 开始运行
     */
    @Override
    public void start() {
        //缺少线程、正在运行、已经关闭都不允许再次执行
        if (workThread == null || isRunning || isStop) {
            return;
        }
        workThread.start();
        //用于防止再次运行
        isRunning = true;
    }

    /**
     * 结束运行
     */
    @Override
    public void stop() {
        clearHandler();
        isStop = true;
        isRunning = false;
        workThread = null;
        workBlockingDeque.clear();
        workBlockingDeque = null;
    }

    /**
     * 清理内容
     */
    private void clearHandler() {
        //清理尚未执行的runnable
        if (handler == null || handlerRunnableList == null) {
            return;
        }
        for (Runnable runnable : handlerRunnableList) {
            handler.removeCallbacks(runnable);
        }
        //销毁自身引用
        handler = null;
    }

    /**
     * 添加work 到队列
     *
     * @param work 需要运行的work
     */
    @Override
    public void addWork(IWork work) {
        if (workBlockingDeque == null || isStop) {
            return;
        }
        workBlockingDeque.offer(work);
    }

    /**
     * 添加work 到队列
     *
     * @param work  需要运行的work
     * @param delay 需要延迟的时间(ms)
     */
    @Override
    public void addWork(final IWork work, int delay) {
        //check
        if (handler == null || handlerRunnableList == null || isStop) {
            return;
        }

        //make runnable
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                addWork(work);
            }
        };
        //post
        handlerRunnableList.add(runnable);
        handler.postDelayed(runnable, delay);
    }


    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public boolean isStop() {
        return isStop;
    }

    /**
     * 此方法外部无需调用对外无效
     */
    @Deprecated
    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        while (!isStop) {
            try {
                IWork work = workBlockingDeque.take();
                work.run();
            } catch (InterruptedException e) {
                isStop = true;
                break;
            }
        }
    }
}
