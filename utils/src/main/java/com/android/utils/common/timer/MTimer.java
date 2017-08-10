package com.android.utils.common.timer;

import android.text.TextUtils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

public class MTimer implements ITimer {
    //--real timer--
    private Timer timer;
    //--default task--
    private TimerTask task;
    //--default initdelay--
    private long initDelay = 0l;
    //--default delay--
    private long delay = 0l;
    //--call back--
    private ITimerChangeCallback[] callbacks = null;
    //--real time--
    private AtomicLong time;//时间的记录工具

    private MTimer(long initDelay, long delay, ITimerChangeCallback[] callbacks) {
        this.initDelay = initDelay;
        this.delay = delay;
        this.callbacks = callbacks;
        this.task = createTask();
    }

    //-----------------外部方法------------------------

    /**
     * 用于生成MTimer 对象
     *
     * @return --MTimer
     */
    public static Builder makeTimerBuilder() {
        return new Builder();
    }

    /**
     * 开启 timer
     */
    @Override
    public void startTimer() {
        realStartTimer(true);
    }

    /**
     * 暂停timer
     */
    @Override
    public void pauseTimer() {
        realStopTimer(false);
    }

    /**
     * 重启timer
     */
    @Override
    public void resumeTimer() {
        realStartTimer(false);
    }

    /**
     * 关闭timer
     */
    @Override
    public void stopTimer() {
        realStopTimer(true);
    }

    //-----------------内部方法------------------------

    /**
     * timer 真正的开始方法
     *
     * @param isToZero --是否清除数据
     */
    private void realStartTimer(boolean isToZero) {
        //清空记录时间
        if (isToZero) {
            time = new AtomicLong(0);
        }
        //重新生成timer,以及任务,如果不等于null,说明本身就是运行状态，或者resume状态
        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(task, initDelay, delay);
        }
    }

    /**
     * timer 真正的关闭方法
     *
     * @param isToZero --是否清除数据
     */
    private void realStopTimer(boolean isToZero) {
        //清空记录时间
        if (isToZero) {
            time = new AtomicLong(0);
        }
        //关闭当前的timer
        if (timer != null) {
            timer.purge();
            timer.cancel();
            timer = null;
            task = createTask();//重新创建task
        }
    }


    /**
     * 判断是否设置监听回调
     *
     * @return -- true 表示设置了回调，反之表示没设置
     */
    private boolean checkCallback() {
        return callbacks != null && callbacks.length > 0;
    }

    /**
     * 创建task
     *
     * @return
     */
    private TimerTask createTask() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                time.incrementAndGet();
                notifyCallback(time);
            }
        };
        return task;
    }

    /**
     * 通知callback
     *
     * @param time --间距走的次数（花费时间=次数*delay+initDelay）
     */
    private void notifyCallback(AtomicLong time) {
        if (checkCallback()) {
            for (ITimerChangeCallback callback : callbacks) {
                callback.onTimeChange(time.longValue());
            }
        }
    }

    public static class Builder {

        //--default initdelay--
        private long initDelay = 0l;
        //--default delay--
        private long delay = 0l;
        //--call back--
        private ITimerChangeCallback[] callbacks = null;
        //--tag--
        private String tag;

        public Builder setTag(String tag) {
            if (TextUtils.isEmpty(tag)) {
                throw new NullPointerException("设置的tag无效！=>setTag(String tag)");
            }
            this.tag = tag;
            return this;
        }


        /**
         * 设置执行当前任务的时候首次执行时的延迟时间
         *
         * @param initDelay --首次执行的延迟时间(ms)
         */
        public Builder setInitDelay(long initDelay) {
            this.initDelay = initDelay;
            return this;
        }

        /**
         * 设置时间回调
         *
         * @param callbacks
         */
        public Builder setCallbacks(ITimerChangeCallback... callbacks) {
            this.callbacks = callbacks;
            return this;
        }

        /**
         * 设置后续的延迟时间
         *
         * @param delay --后续延迟时间(ms)
         */
        public Builder setDelay(long delay) {
            this.delay = delay;
            return this;
        }

        /**
         * 外部会重用此对象，所以需要重置其参数
         */
        public void reset() {
            tag = null;
            initDelay = 0l;
            delay = 0l;
            callbacks = null;
        }

        /**
         * 最终的生成方法，如果不调用此处，timer无法运行
         */
        public MTimer build() {
            //--check delay--
            if (initDelay < 0 || delay < 0) {
                throw new AssertionError("initDelay或delay 不允许小于0");
            }
            //--build timer--
            MTimer timer = new MTimer(initDelay, delay, callbacks);
            //--add to cache--
            if (!TextUtils.isEmpty(tag)) {
                TimerUtils.addTimerToCache(tag, timer);
            }
            //--return timer--
            return timer;
        }

    }
}