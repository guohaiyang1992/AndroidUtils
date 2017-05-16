package com.guohaiyang.bitmaprecycledemo.common;

import android.text.TextUtils;

/**
 * Created by guohaiyang on 2017/5/16.
 * 字符串工具类
 */

public class StringUtils {
    //--默认值--
    private static final int DEFAULT_INT = Integer.MIN_VALUE;
    private static final float DEFAULT_FLOAT = Float.MIN_VALUE;
    private static final double DEFAULT_DOUBLE = Double.MIN_VALUE;

    //--检查类型方法--

    /**
     * 判断是否内容不为空不为null
     *
     * @param s --内容
     * @return
     */
    public static boolean checkValueIsUsefull(String s) {
        return !TextUtils.isEmpty(s);
    }

    //--转换类型方法--

    /**
     * 安全的字符串转int
     *
     * @param s --内容
     * @return
     */
    public static int safeStringToInt(String s) {
        return safeStringToInt(s, DEFAULT_INT);
    }

    /**
     * 安全的转字符串转int
     *
     * @param s         --内容
     * @param defaulInt --默认值
     * @return
     */
    public static int safeStringToInt(String s, int defaulInt) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return defaulInt;
        }
    }


    /**
     * 安全的字符串转float
     *
     * @param s --内容
     * @return
     */
    public static float safeStringToFloat(String s) {
        return safeStringToFloat(s, DEFAULT_FLOAT);
    }

    /**
     * 安全的转字符串转float
     *
     * @param s            --内容
     * @param defaultFloat --默认值
     * @return
     */
    public static float safeStringToFloat(String s, float defaultFloat) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return defaultFloat;
        }
    }

    /**
     * 安全的字符串转double
     *
     * @param s --内容
     * @return
     */
    public static double safeStringToDouble(String s) {
        return safeStringToDouble(s, DEFAULT_DOUBLE);
    }

    /**
     * 安全的转字符串转double
     *
     * @param s             --内容
     * @param defaultDouble --默认值
     * @return
     */
    public static double safeStringToDouble(String s, double defaultDouble) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return defaultDouble;
        }
    }
}
