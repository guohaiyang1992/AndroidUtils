package com.android.utils.common;

/**
 * description: 异常工具类，辅助抛出各种异常信息
 * author: Simon
 * created at 2017/9/1 下午4:46
 */

public class ExceptionUtils {
    private static StringBuffer errbuffer = new StringBuffer();

    /**
     * 抛出空指针异常 ，当需要初始化的内容为null时抛出此异常
     *
     * @param msg --错误提示信息
     */
    public static void throwNullPointerException(String msg) {
        throw new NullPointerException(formatErrMsg(msg));
    }

    /**
     * 抛出参数错误异常，当传入的参数不符合你的约束时，抛出此异常
     *
     * @param msg --错误提示信息
     */
    public static void throwIllegalArgumentException(String msg) {
        throw new IllegalArgumentException(formatErrMsg(msg));
    }

    /**
     * 抛出断言异常，当某些行为没有符合你的要求时就可以抛出此异常
     *
     * @param msg --错误提示信息
     */
    public static void throwAssertionError(String msg) {
        throw new AssertionError(formatErrMsg(msg));
    }

    /**
     * 抛出数组越界异常，当访问的位置不在当前元素的范围内时抛出此异常
     *
     * @param msg --错误提示信息
     */
    public static void throwArrayIndexOutOfBoundsException(String msg) {
        throw new ArrayIndexOutOfBoundsException(formatErrMsg(msg));
    }

    /**
     * 抛出算术异常，当计算数据不正常时比如除数是0时抛出此异常
     *
     * @param msg --错误提示信息
     */
    public static void throwArithmeticExecption(String msg) {
        throw new ArithmeticException(formatErrMsg(msg));
    }

    /**
     * 抛出字符串转数字异常，当字符串中不包含数字时抛出此异常
     *
     * @param msg --错误提示信息
     */
    public static void throwNumberFormatException(String msg) {
        throw new NumberFormatException(formatErrMsg(msg));
    }

    /**
     * 格式化输出错误信息
     *
     * @param msg
     * @return
     */
    public static String formatErrMsg(String msg) {
        //--init StirngBuffer--
        if (errbuffer == null) {
            errbuffer = new StringBuffer();
        }

        //--clear StringBuffer--
        errbuffer.delete(0, errbuffer.length());

        //--append errMsg--
        errbuffer.append("\n");
        errbuffer.append("      |￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣").append("\n");
        errbuffer.append("      |    ↣↣↣ 【 ").append(msg).append(" 】 ↢↢↢").append("\n");
        errbuffer.append("      |＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿");

        //--return result--
        return errbuffer.toString();
    }


}
