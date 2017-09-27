package com.android.utils.common;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
     * <p>
     * 运行环境：宽高可正常获取的情况下
     */
    public static Bitmap shotActivity(Activity activity) {
        View view = getActivityDecorView(activity);
        return shotView(view);
    }

    /**
     * activity 截图,返回的bitmap 注意销毁
     * 注意：此方法可在任意线程下运行
     * <p>
     * 运行环境：可运行在任意情况下，无论是否可以获取宽高
     */
    public static void shotActivity(Activity activity, final ShotCallback callback) {
        final View view = getActivityDecorView(activity);
        if (view != null && callback != null) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    callback.onShot(shotView(view));
                }
            });
        }
    }


    /**
     * activity 截图，去除状态栏
     * <p>
     * 运行环境：宽高可正常获取的情况下
     *
     * @param activity 返回的bitmap 注意销毁
     * @return
     */
    public static Bitmap shotActivityNoStatusBar(Activity activity) {
        int statusBarHeight = getStatusBarHeight(activity);
        View view = getActivityDecorView(activity);
        return shotView(view, 0, statusBarHeight, view.getMeasuredWidth(), view.getMeasuredHeight() - statusBarHeight);
    }

    /**
     * activity 截图，去除状态栏
     * <p>
     * 运行环境：可运行在任意情况下，无论是否可以获取宽高
     *
     * @param activity 返回的bitmap 注意销毁
     * @return
     */
    public static void shotActivityNoStatusBar(Activity activity, final ShotCallback callback) {
        final int statusBarHeight = getStatusBarHeight(activity);
        final View view = getActivityDecorView(activity);
        if (view != null && callback != null) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    callback.onShot(shotView(view, 0, statusBarHeight, view.getMeasuredWidth(), view.getMeasuredHeight() - statusBarHeight));
                }
            });
        }

    }

    /**
     * 截取图片的 其中一种实现
     * <p>
     * 运行环境：宽高可正常获取的情况下
     *
     * @param view 需要生产截图的view ,生成的bitmap 注意不使用的时候需要销毁
     * @return 注意：此方法可在任意线程下运行
     */
    public static Bitmap shotView(View view) {
        return shotView(view, 0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    /**
     * 截取图片的 其中一种实现
     * <p>
     * 运行环境：可运行在任意情况下，无论是否可以获取宽高
     *
     * @param view 需要生产截图的view ,生成的bitmap 注意不使用的时候需要销毁
     * @return 注意：此方法可在任意线程下运行
     */
    public static void shotView(final View view, final ShotCallback callback) {
        if (view != null && callback != null) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    callback.onShot(shotView(view, 0, 0, view.getMeasuredWidth(), view.getMeasuredHeight()));
                }
            });
        }

    }

    /**
     * 截取view 的指定宽高和起始位置
     * <p>
     * 运行环境：宽高可正常获取的情况下
     *
     * @param view   需要生产截图的view ,生成的bitmap 注意不使用的时候需要销毁
     * @param x      左上x起始位置
     * @param y      左上y起始位置
     * @param width  截图的宽度
     * @param height 截图的高度
     * @return 注意：此方法可在任意线程下运行
     */
    public static Bitmap shotView(View view, int x, int y, int width, int height) {
        if (ObjectUtils.notNull(view) && view.getMeasuredWidth() > 0 && view.getMeasuredHeight() > 0) {//此处宽高有限制，否则创建图出错
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
     * 截取view 的指定宽高和起始位置
     * <p>
     * 运行环境：可运行在任意情况下，无论是否可以获取宽高
     *
     * @param view   需要生产截图的view ,生成的bitmap 注意不使用的时候需要销毁
     * @param x      左上x起始位置
     * @param y      左上y起始位置
     * @param width  截图的宽度
     * @param height 截图的高度
     * @return 注意：此方法可在任意线程下运行
     */
    public static void shotView(final View view, final int x, final int y, final int width, final int height, final ShotCallback callback) {
        if (view != null && callback != null) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    callback.onShot(shotView(view, x, y, width, height));
                }
            });
        }
    }

    /**
     * 检查当前是否可以获取到view 的宽高 (view 不是null 且宽高小于等于0 需要测量)
     *
     * @param view
     * @return
     */
    private static boolean checkNeedMeasure(View view) {
        return (view != null) && (view.getHeight() <= 0 || view.getWidth() <= 0);
    }

    private static void ifNotExistWhMeasure(View view) {
        if (checkNeedMeasure(view)) {
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);
            Log.e(TAG, "对应的view无效或者未绘制完成!==>" + view.getMeasuredWidth());
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

    public static interface ShotCallback {
        void onShot(Bitmap bitmap);
    }


}
