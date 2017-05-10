package com.hfga.resumebooklib.utils.thread;

import android.util.Log;

/**
 * Created by guohaiyang on 2017/4/10.
 */

public abstract class Task implements Runnable {
    private Task afterTask;
    private boolean isBackGround = false;

    /**
     * 构造函数
     *
     * @param isBackGround 标志：是不是子线程执行
     */
    public Task(boolean isBackGround) {
        this.isBackGround = isBackGround;
    }

    public Task() {
        this.isBackGround = false;
    }

    public Task getAfterTask() {
        return afterTask;
    }

    public boolean isBackGround() {
        return isBackGround;
    }

    @Override
    public void run() {
        if (isBackGround) {
            ThreadUtils.runInBackGroundThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        runInTask();
                        runAfterTask();
                    } catch (Exception e) {
                        Log.v("task_error", "back_ground_info:" + e.toString());
                    }

                }
            });

        } else {
            ThreadUtils.runInUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        runInTask();
                        runAfterTask();
                    } catch (Exception e) {
                        Log.v("task_error", "ui_info:" + e.toString());
                    }

                }
            });
        }
    }

    protected void runAfterTask() {
        if (afterTask != null) {
            afterTask.run();
        }
    }

    public abstract void runInTask() throws IllegalAccessException;

    /**
     * 添加下一个task
     *
     * @param task 需要添加的task
     * @return 返回当前添加的task用于下次添加
     */
    public Task addNextTask(Task task) {
        setAfterTask(task);
        return task;
    }

    private void setAfterTask(Task afterTask) {
        this.afterTask = afterTask;
    }
}
