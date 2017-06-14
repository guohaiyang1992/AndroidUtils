package com.android.utils.common;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import java.lang.reflect.Field;

/**
 * 软键盘监听辅助类
 * 此工具类可以监听dialog 和 activity ，前提必须设置resize属性，支持activity沉浸式
 * todo:
 * todo 目前dialog 设置Gravity.top 布局不动会导致失效
 * todo dialog 全屏可能会导致失效
 * <p>
 * //dialog使用说明：
 * 1.在dialog的oncreate或者构造方法内进行创建 -》 getInstance(object)
 * 2.设置监听 setKeyBoardStatusLisener
 * 3.一般情况下上面两步即可，但特殊情况下会导致监听丢失，可在dilaog.show()内使用 reset（）方法即可
 * //activity使用说明：
 * 1.在oncreate方法内进行创建-》etInstance(object)
 * 2.设置监听 setKeyBoardStatusLisener
 * <p>
 * Author:Simon
 */

public class KeyBoardHelper {
    //---activity配置---
    private View mChildOfContent;
    private int usableHeightPrevious;
    private int statusBarHeight;
    private Activity mActivity;
    //---dialog配置---
    private Dialog mDialog;
    private int locationY = Integer.MAX_VALUE;

    //---公共状态值---
    private boolean isActivity = false;
    private IKeyBoardStatusLisener lisener = null;
    private int DEFAULT_KEYBOARD_HEIGHT = 300;
    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = null;

    /**
     * 初始化函数
     *
     * @param object --接收dialog或者activity
     */
    private KeyBoardHelper(Object object) {
        if (object != null) {
            if (object instanceof Activity) {
                mActivity = (Activity) object;
                mDialog = null;
                isActivity = true;
            } else if (object instanceof Dialog) {
                mDialog = (Dialog) object;
                mActivity = null;
                isActivity = false;
            } else {
                throw new RuntimeException("暂不支持该类型：" + object.getClass().getSimpleName());
            }
        } else {
            throw new NullPointerException("getInstance(Object object)中 object不允许为null");
        }
        initConfig();
    }

    /**
     * 初始化函数，用于发现主布局添加布局变化监听
     */
    private void initConfig() {
        FrameLayout content = findContentView();
        mChildOfContent = content.getChildAt(0);
        statusBarHeight = getStatusBarHeight();
        globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                dispatchLayoutChange();
            }
        };
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
    }

    /**
     * 分发界面变化的事件
     */
    private void dispatchLayoutChange() {
        Log.i("ghy", "currHeight is change ");
        if (isActivity()) {
            onActivityChanghe();
        } else {
            onDialogChange();
        }
    }

    /**
     * dialog界面发生变化的时候回调
     */
    private void onDialogChange() {
        Log.v("ghy", "onDialogChange");

        int locationYNow = computeLocationY();

        if (locationYNow != locationY) {

            int heightDifference = Math.abs(locationY - locationYNow);
            if (heightDifference > DEFAULT_KEYBOARD_HEIGHT) {
                Log.v("ghy", locationY + ":" + locationYNow);
                if (locationY > locationYNow) { //上一次大于当前的，说明之前是关闭，现在是打开 参考window左上Y坐标
                    notifyLisner(true);
                } else {
                    notifyLisner(false);
                }
            }

            locationY = locationYNow;
        }
        Log.v("ghy", "合并" + locationY + ":" + locationYNow);
    }

    /**
     * 寻找对应的contentview
     *
     * @return
     */
    private FrameLayout findContentView() {
        if (isActivity()) {
            return (FrameLayout) mActivity.findViewById(android.R.id.content);
        } else {
            return (FrameLayout) mDialog.findViewById(android.R.id.content);
        }
    }

    /**
     * 封装的实例化方法
     *
     * @param object dialog/activity
     * @return
     */
    public static KeyBoardHelper getInstance(Object object) {
        return new KeyBoardHelper(object);
    }

    /**
     * 判断模式是不是activity模式
     *
     * @return
     */
    private boolean isActivity() {
        return isActivity;
    }

    /**
     * 计算dialog当前所在屏幕位置
     *
     * @return
     */
    private int computeLocationY() {
        int[] loc = new int[2];
        mChildOfContent.getLocationOnScreen(loc);
        if (loc != null && loc.length == 2) {
            return loc[1];
        }
        return 0;
    }

    /**
     * activity 界面变化回调
     */
    private void onActivityChanghe() {
        //计算当前可用高度
        int usableHeightNow = computeUsableHeight();
        //当前高度不等于之前的，进行判断
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;//计算出高度差值
            if (heightDifference > DEFAULT_KEYBOARD_HEIGHT) {//大于默认键盘高度则认为显示了键盘，反之隐藏
                notifyLisner(true);
            } else {
                notifyLisner(false);
            }
            usableHeightPrevious = usableHeightNow;
        }
    }

    /**
     * 传递消息给回调接口
     *
     * @param isShow --键盘的显示状态
     */
    private void notifyLisner(boolean isShow) {
        if (lisener != null) {
            lisener.onKeyBoardStatusChange(isShow);
        }
    }

    /**
     * 计算当前界面可用高度
     *
     * @return 可用高度
     */
    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return r.bottom - r.top + statusBarHeight;
    }

    /**
     * 获取状态栏的高度，针对于 使用了沉浸式状态栏的情况
     *
     * @return 状态栏高度
     */
    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            int dimensionPixelSize = mActivity.getResources().getDimensionPixelSize(x);
            return dimensionPixelSize;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 键盘状态监听接口
     */
    public interface IKeyBoardStatusLisener {
        void onKeyBoardStatusChange(boolean isShow);
    }

    /**
     * 设置键盘状态监听回调
     *
     * @param lisener
     */
    public void setKeyBoardStatusLisener(IKeyBoardStatusLisener lisener) {
        this.lisener = lisener;
    }

    /**
     * 释放资源
     */
    public void release() {
        if (mChildOfContent != null && globalLayoutListener != null) {
            mChildOfContent.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
        }
    }


    /**
     * 重置数据：该方法适用于外部触发Dialog 的dismiss,而不是由于键盘关闭引起的dismiss
     * 使用：在Dialog的show()方法内，使用即可
     * 使用频率：较高
     */
    public void resetValue() {
        //当dialog 进行dismiss 的时候，如果不reset此处会导致判断结果不一致，如果是手动关闭输入法，此处不需要重置
        locationY = Integer.MAX_VALUE;
    }

    /**
     * 重置数据和绑定，当同时出现上述说明的两种情况则一起调用
     * 使用频率：较低
     */
    public void resetBindAndValue() {
        resetBind();
        resetValue();
    }

    /**
     * 重置绑定：该方法适用于当第一次回调成功后，再次回调无响应的情况。
     * 使用：在Dialog的show()方法内，使用此方法即可
     * 使用频率：较低
     */
    public void resetBind() {
        if (!isEmptyContent()) {
            mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
        }
    }

    private boolean isEmptyContent() {
        return mChildOfContent == null;
    }


}
