package com.android.utils.common;

import android.content.Context;

/**
 * @author ghy
 * @ClassName: UnitUtils
 * @Description: 单位转换工具s
 * @date 2016-9-12 上午11:14:37
 */
public class UnitUtils {

    private static final String ERR_INFO = "UnitUtils中的context需要初始化！";

    private UnitUtils() {
        throw new AssertionError();
    }

    private static Context context;

    /**
     * 初始化context函数
     *
     * @param context --上下文
     */
    public static void init(Context context) {
        UnitUtils.context = context;
    }

    /**
     * dp转px
     *
     * @param dp --float的dp数据
     * @return int的px数据
     */
    public static int Dp2Px(float dp) {
        if (context != null) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dp * scale + 0.5f);
        } else {
            throw new NullPointerException(ERR_INFO);
        }

    }

    /**
     * px转dp
     *
     * @param px float的px数据
     * @return int的dp数据
     */
    public static int Px2Dp(float px) {
        if (context != null) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (px / scale + 0.5f); // 红米的那个是2.06
        } else {
            return 0;
        }

    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue --float的px数据
     * @return
     */
    public static int Px2Sp(float pxValue) {
        if (context != null) {
            final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (pxValue / fontScale + 0.5f); // 2.06
        } else {
            throw new NullPointerException(ERR_INFO);
        }
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue-- float的sp数据
     * @return int的px数据
     */
    public static int Sp2Px(float spValue) {
        if (context != null) {
            final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (spValue * fontScale + 0.5f);
        } else {
            throw new NullPointerException(ERR_INFO);
        }
    }
}
