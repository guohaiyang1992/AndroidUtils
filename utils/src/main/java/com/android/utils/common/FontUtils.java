package com.android.utils.common;

import android.content.Context;
import android.util.TypedValue;

/**
 * 用于通过文字的大小计算文字的宽高（通过公式估算,以中文为基准计算,误差在1px之内）
 *
 * @author Simon
 * @version v1.0
 * @date 2018/5/20
 */

public class FontUtils {

    /**
     * 通过文字大小获取对应的文字高度,返回结果是文字的高度，单位dp
     * 注：映射关系是文字 1sp 高度 (1 + 0.00000007f) / 0.7535f
     */
    public static float fontSizeToHeight(Context context, float fontSize) {
        return fontSizeToHeight(context, TypedValue.COMPLEX_UNIT_SP, fontSize);
    }

    /**
     * 通过文字大小获取对应的文字高度,返回结果是文字的高度，单位dp
     * 注：映射关系是文字 1sp 高度 (1 + 0.00000007f) / 0.7535f
     */
    public static float fontSizeToHeight(Context context, int unit, float fontSize) {
        fontSize = unit2sp(context, unit, fontSize);
        return (fontSize + 0.00000007f) / 0.7535f;
    }

    /**
     * 通过文字大小获取对应的文字宽度,返回结果是文字的宽度，单位dp
     * 注：映射关系是 文字1sp 宽度是1dp
     */
    public static float fontSizeToWidth(Context context, float fontSize) {
        return fontSizeToWidth(context, TypedValue.COMPLEX_UNIT_SP, fontSize);
    }

    /**
     * 通过文字大小获取对应的文字宽度,返回结果是文字的宽度，单位dp
     * 注：映射关系是 文字1sp 宽度是1dp
     */
    public static float fontSizeToWidth(Context context, int unit, float fontSize) {
        fontSize = unit2sp(context, unit, fontSize);
        return fontSize;
    }

    /**
     * 各种单位转sp
     */
    private static float unit2sp(Context context, int unit, float fontSize) {
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                fontSize = px2sp(context, fontSize);
                break;
            case TypedValue.COMPLEX_UNIT_DIP:
                fontSize = px2sp(context, dp2px(context, fontSize));
                break;
            default:
                break;
        }
        return fontSize;
    }

    /**
     * px转sp
     */
    private static float px2sp(Context context, float px) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return px / fontScale + 0.5f;
    }

    /**
     * dp转px
     */
    private static float dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

}
