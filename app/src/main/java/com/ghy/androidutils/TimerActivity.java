package com.ghy.androidutils;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.android.utils.common.timer.ITimerChangeCallback;
import com.android.utils.common.timer.MTimer;
import com.android.utils.common.timer.TimerUtils;

/**
 * description: 用于测试TimerUtils
 * author: Simon
 * created at 2017/8/10 下午1:37
 */

public class TimerActivity extends Activity {
    private String tag = "timer1";
    private MTimer timer2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        initTimer1();

        initTimer2();
    }

    private void initTimer2() {
        //--不设置 tag 只能通过其对象操作--
        timer2 = TimerUtils.makeBuilder().setInitDelay(0).setDelay(1000).setCallbacks(new ITimerChangeCallback() {
            @Override
            public void onTimeChange(long time) {
                Log.v("test", time + "--> timer2");
            }
        }).build();
    }

    //--设置 tag 后可以通过 tag 操作--
    private void initTimer1() {
        TimerUtils.makeBuilder().setTag(tag).setInitDelay(0).setDelay(1000).setCallbacks(new ITimerChangeCallback() {
            @Override
            public void onTimeChange(long time) {
                Log.v("test", time + "--> timer1");
            }
        }).build();

    }

    public void onTimer1Start(View view) {
        TimerUtils.startTimer(tag);

    }

    public void onTimer1Pause(View view) {
        TimerUtils.pauseTimer(tag);
    }

    public void onTimer1Resume(View view) {
        TimerUtils.resumeTimer(tag);
    }

    public void onTimer1Stop(View view) {
        TimerUtils.stopTimer(tag);
    }

    public void onTimer2Start(View view) {
        timer2.startTimer();
    }

    public void onTimer2Pause(View view) {
        timer2.pauseTimer();

    }

    public void onTimer2Resume(View view) {
        timer2.resumeTimer();

    }

    public void onTimer2Stop(View view) {
        timer2.stopTimer();
    }
}
