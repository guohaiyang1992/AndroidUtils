package com.ghy.androidutils;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.android.utils.anim.expand.ExpandAnimHelper;
import com.android.utils.anim.expand.IExpandAnimLisener;

/**
 * description: 展开动画效果
 * author: Simon
 * created at 2017/8/21 下午4:35
 */

public class ExpandActivity extends Activity implements IExpandAnimLisener {
    private View animView;
    private ExpandAnimHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        animView = findViewById(R.id.testView);
        helper = ExpandAnimHelper.with(animView).setCallback(this).setExpandTime(5000).setCollapseTime(5000);
    }

    public void expand(View view) {
        helper.expand();
    }

    public void toggle(View view) {
        helper.toggle();

    }

    public void collapse(View view) {
        helper.collapse();
    }


    @Override
    public void onAnimStart() {
        Log.v("test", "onAnimStart");
    }

    @Override
    public void onAnimEnd() {
        Log.v("test", "onAnimEnd");
    }

    @Override
    public void onAnimChange(int value) {
        Log.v("test", "onAnimChange:" + value);
    }
}
