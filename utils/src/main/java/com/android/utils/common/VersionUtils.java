package com.android.utils.common;

import com.ghy.utils.BuildConfig;

/**
 * description: 用于比较两个version是否相同，或者那个更大
 * author: Simon
 * created at 2017/8/21 上午11:31
 */

public class VersionUtils {

    /**
     * 比较版本
     *
     * @param leftVersion  （一般来至服务器）
     * @param rightVersion （一般来之本地）
     * @return --left 大于 right => true left 小于等于right 返回false
     */
    public static boolean compareVersion(String leftVersion, String rightVersion) {
        //checkversion
        if (leftVersion == null || rightVersion == null) {
            return false;
        }
        //拆分 version
        String[] leftversionArray = leftVersion.split("\\.");
        String[] rightVersionArray = rightVersion.split("\\.");
        //check version
        if (leftversionArray == null || rightVersionArray == null) {
            return false;
        }
        //计算最小的size
        int size = Math.min(leftversionArray.length, rightVersionArray.length);

        //从左到右按位比较(大于返回true,小于返回false ，相等比较下一位)
        for (int i = 0; i < size; i++) {
            //获取对应位置的值
            int leftCode = safeStrToInt(leftversionArray[i]);
            int rightCode = safeStrToInt(rightVersionArray[i]);
            //如果相等比较下一位
            if (leftCode == rightCode) {
                continue;
            }
            //如果不相等则比较究竟谁大
            if (leftCode > rightCode) {//大于=>true， 小于=>false
                return true;
            } else {
                return false;
            }
        }

        //如果前面的公共位数内容相同会走到此处，则说明前面都一致，所以位数多的版本大（只有大于才是true， 小于等于都是false）
        if (leftversionArray.length > rightVersionArray.length) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 判断网络版本是否大于本地版本
     *
     * @param netVersion --传入网络版本号 比如 2.0.5
     * @return true 表示需要更新  false 表示不需要更新
     */
    public static boolean checkNeedUpdate(String netVersion) {
        return compareVersion(netVersion, BuildConfig.VERSION_NAME);
    }


    private static int safeStrToInt(String str) {
        return StringUtils.safeStrToInt(str);
    }
}
