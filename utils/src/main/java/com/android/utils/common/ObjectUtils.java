package com.android.utils.common;

/**
 * description: 对象的工具类
 * autour: Simon
 * created at 2017/6/14
 */
public class ObjectUtils {

    /**
     * 判断多个内容都不为null
     *
     * @param objects 数量大于0即可
     * @return
     */
    public static boolean notNull(Object... objects) {
        if (objects != null && objects.length > 0) {
            for (Object ob : objects) {
                if (ob == null) {
                    return false;
                }
            }
            return true;

        } else {
            return false;
        }
    }
}
