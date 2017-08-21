package com.android.utils.common;

import android.text.TextUtils;

/**
 * 字符串工具类
 * Author:Simon
 */

public class StringUtils {

    private StringUtils() {
        throw new AssertionError();
    }

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
    public static int safeStrToInt(String s) {
        return safeStrToInt(s, DEFAULT_INT);
    }

    /**
     * 安全的转字符串转int
     *
     * @param s         --内容
     * @param defaulInt --默认值
     * @return
     */
    public static int safeStrToInt(String s, int defaulInt) {
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
    public static float safeStrToFloat(String s) {
        return safeStrToFloat(s, DEFAULT_FLOAT);
    }

    /**
     * 安全的转字符串转float
     *
     * @param s            --内容
     * @param defaultFloat --默认值
     * @return
     */
    public static float safeStrToFloat(String s, float defaultFloat) {
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
    public static double safeStrToDouble(String s) {
        return safeStrToDouble(s, DEFAULT_DOUBLE);
    }

    /**
     * 安全的转字符串转double
     *
     * @param s             --内容
     * @param defaultDouble --默认值
     * @return
     */
    public static double safeStrToDouble(String s, double defaultDouble) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return defaultDouble;
        }
    }
}
