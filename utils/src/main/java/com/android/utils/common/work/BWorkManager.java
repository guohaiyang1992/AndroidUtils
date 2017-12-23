package com.android.utils.common.work;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

/**
 * 用于添加work,处理work
 *
 * @author Simon
 */
public class BWorkManager implements IWorkManager {
    private boolean isStop = false;
    private boolean isRunning = false;
    private HandlerThread handlerThread = null;
    private Handler handler;

    public BWorkManager() {
        this("WorkManager_" + System.currentTimeMillis());
    }

    public BWorkManager(String name) {
        handlerThread = new HandlerThread(name);
    }


    @Override
    public void start() {
        if (handlerThread == null || isRunning || isStop) {
            return;
        }
        handlerThread.start();
        isRunning = true;
        // HandlerThread 当开始运行后才有Looper,所以需要在start()之后才开始初始化handler
        handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                IWork work = (IWork) msg.obj;
                work.run();
            }
        };
    }

    @Override
    public void stop() {
        isStop = true;
        isRunning = false;
        //销毁了looper 线程将结束,且不会有再执行延迟的消息可能，所以无需销毁removeMsg
        handlerThread.quit();
        handler = null;
        handlerThread = null;
    }


    @Override
    public void addWork(IWork work) {
        addWork(work, 0);
    }

    @Override
    public void addWork(IWork work, int delay) {
        if (handler == null || isStop) {
            return;
        }
        Message message = Message.obtain();
        message.obj = work;
        handler.sendMessageDelayed(message, delay);
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public boolean isStop() {
        return isStop;
    }
}
