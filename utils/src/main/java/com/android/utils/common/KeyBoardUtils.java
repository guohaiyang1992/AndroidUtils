package com.android.utils.common;

import android.app.Activity;
import android.app.Dialog;

import android.content.Context;

import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;

/**
 * 关闭键盘的方法，支持dialog、activity、fragment,不需要知道是哪个控件获取的焦点
 *
 * @author Simon
 * @version v1.0
 * @date 2018/9/2
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
     * 关闭键盘（V4）
     *
     * @param fragment --fragment
     */
    public static void closeKeybord(Fragment fragment) {
        closeKeybord(fragment.getActivity());
    }

    /**
     * 关闭键盘
     *
     * @param fragment
     */
    public static void closeKeybord(android.app.Fragment fragment) {
        closeKeybord(fragment.getActivity());
    }
}
