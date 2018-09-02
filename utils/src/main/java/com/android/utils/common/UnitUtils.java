package com.android.utils.common;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author Simon
 * @ClassName: UnitUtils
 * @Description: 单位转换工具 fix:去除存储Context,修改为每次传入
 * @date 2016-9-12 上午11:14:37
 */
public class UnitUtils {

    private UnitUtils() {
        throw new AssertionError();
    }

    /**
     * dp转px
     *
     * @param context 上下文
     * @param dp      float的dp数据
     * @return px
     */
    public static int Dp2Px(@NonNull Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param context 上下文
     * @param px      float的px数据
     * @return dp
     */
    public static int Px2Dp(@NonNull Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param px float的px数据
     * @return sp
     */
    public static int Px2Sp(@NonNull Context context, float px) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param sp float的sp数据
     * @return px
     */
    public static int Sp2Px(Context context, float sp) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }
}
