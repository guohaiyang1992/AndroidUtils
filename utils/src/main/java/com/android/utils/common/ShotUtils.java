package com.android.utils.common;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

/**
 * description: 用于activity、View、listView、recycleView、scrollView截图
 * author: Simon
 * created at 2017/7/30 下午6:01
 * 注意：setDrawingCacheEnabled(false) 内部会销毁缓存
 */

public class ShotUtils {
    private static final String TAG = "ShotUtils";


    private ShotUtils() {
        throw new AssertionError();
    }

    /**
     * activity 截图,返回的bitmap 注意销毁
     * 注意：此方法可在任意线程下运行
     */
    public static Bitmap shotActivity(Activity activity) {
        View view = getActivityDecorView(activity);
        return shotView(view);
    }

    /**
     * activity 截图，去除状态栏
     *
     * @param activity 返回的bitmap 注意销毁
     * @return
     */
    public static Bitmap shotActivityNoStatusBar(Activity activity) {
        int statusBarHeight = getStatusBarHeight(activity);
        View view = getActivityDecorView(activity);
        return shotView(view, 0, statusBarHeight, view.getWidth(), view.getHeight() - statusBarHeight);
    }

    /**
     * 截取图片的 其中一种实现
     *
     * @param view 需要生产截图的view ,生成的bitmap 注意不使用的时候需要销毁
     * @return 注意：此方法可在任意线程下运行
     */
    public static Bitmap shotView(View view) {
        return shotView(view, 0, 0, view.getWidth(), view.getHeight());
    }

    /**
     * 截取view 的指定宽高和起始位置
     *
     * @param view   需要生产截图的view ,生成的bitmap 注意不使用的时候需要销毁
     * @param x      左上x起始位置
     * @param y      左上y起始位置
     * @param width  截图的宽度
     * @param height 截图的高度
     * @return 注意：此方法可在任意线程下运行
     */
    public static Bitmap shotView(View view, int x, int y, int width, int height) {
        if (ObjectUtils.notNull(view) && view.getWidth() > 0 && view.getHeight() > 0) {//此处宽高有限制，否则创建图出错
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), x, y, width, height);//重新生成一个bitmap，原始的缓存会被销毁
            view.setDrawingCacheEnabled(false);
            return bitmap;
        } else {
            Log.e(TAG, "对应的view无效或者未绘制完成!");
            return null;
        }
    }

    /**
     * Scrollview截屏
     *
     * @param scrollView 要截图的ScrollView
     * @return Bitmap，有可能会返回null
     */
    public static Bitmap shotScrollView(ScrollView scrollView) {
        if (ObjectUtils.notNull(scrollView)) {
            int h = getScrollViewHeight(scrollView);
            int w = scrollView.getWidth();
            if (h > 0 && w > 0) {
                Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
                final Canvas canvas = new Canvas(bitmap);
                scrollView.draw(canvas); //此处将创建的bitmap构造canvas,然后通过scrollview的draw方法绘制bitmap
                return bitmap;
            }
        }
        return null;
    }

    /**
     * 获取状态栏的高度
     *
     * @param activity
     * @return
     */
    private static int getStatusBarHeight(Activity activity) {
        if (ObjectUtils.notNull(activity)) {
            //获取status_bar_height资源的ID
            int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                //根据资源ID获取响应的尺寸值
                return activity.getResources().getDimensionPixelSize(resourceId);
            }
        }

        return 0; //默认返回0
    }

    /**
     * 获取activity 的DecorView
     *
     * @param activity
     * @return
     */
    private static View getActivityDecorView(Activity activity) {
        if (ObjectUtils.notNull(activity)) {
            return activity.getWindow().getDecorView();
        }
        return null;

    }

    /**
     * 获取scrollView 的高度
     *
     * @param scrollView
     * @return
     */
    private static int getScrollViewHeight(ScrollView scrollView) {
        int h = 0;
        if (ObjectUtils.notNull(scrollView)) {
            int count = scrollView.getChildCount();
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    h += scrollView.getChildAt(i).getHeight();//累加 高度
                    scrollView.getChildAt(i).setBackgroundColor(Color.WHITE);//此处防止绘制的时候背景变黑
                }
                return h;
            } else {
                return scrollView.getHeight();
            }
        }
        return 0;
    }

}
