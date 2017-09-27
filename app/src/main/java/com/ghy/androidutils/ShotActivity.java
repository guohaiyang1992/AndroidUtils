package com.ghy.androidutils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.android.utils.common.ShotUtils;
import com.android.utils.common.ThreadUtils;

/**
 * description: 用于测试截图工具类
 * author: Simon
 * created at 2017/7/30 下午6:11
 */

public class ShotActivity extends Activity {
    private ImageView showShotIv, testIv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot);
        showShotIv = (ImageView) findViewById(R.id.show_shot_iv);
        testIv = (ImageView) findViewById(R.id.test_shot_iv);
        shotActivity(null);
    }

    //子线程或者主线程都可以截取图片
    //有状态栏
    public void shotActivity(View view) {

        ShotUtils.shotActivity(ShotActivity.this, new ShotUtils.ShotCallback() {
            @Override
            public void onShot(Bitmap bitmap) {
                showShotIv.setImageBitmap(bitmap);
            }
        });

//        ThreadUtils.runInBackGroundThread(new Runnable() {
//            @Override
//            public void run() {
//
//                ShotUtils.shotActivity(ShotActivity.this, new ShotUtils.ShotCallback() {
//                    @Override
//                    public void onShot(Bitmap bitmap) {
//                        showShotIv.setImageBitmap(bitmap);
//                    }
//                });
//            }
//        });

    }

    //没有状态栏
    public void shotActivityNoBar(View view) {
        final Bitmap bitmap = ShotUtils.shotActivityNoStatusBar(ShotActivity.this);
        showShotIv.setImageBitmap(bitmap);
    }
}
