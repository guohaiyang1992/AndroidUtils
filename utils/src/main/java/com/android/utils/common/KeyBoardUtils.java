package com.android.utils.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;

/**
 * 特点：
 * 关闭键盘的方法，支持dialog、activity、fragement,不需要知道是哪个控件获取的焦点
 * <p>
 * Author:Simon
 */

public class KeyBoardUtils {

    private KeyBoardUtils() {
        throw new AssertionError();
    }

    /**
     * 关闭键盘
     *
     * @param mContext --activity
     */
    public static void closeKeybord(Activity mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mContext.getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 关闭键盘
     *
     * @param mContext --dialog
     */
    public static void closeKeybord(Dialog mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mContext.getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 关闭键盘
     *
     * @param mContext --fragement
     */
    public static void closeKeybord(Fragment mContext) {
        closeKeybord(mContext.getActivity());
    }
}
