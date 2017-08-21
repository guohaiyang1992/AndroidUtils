package com.android.utils.common;

import android.os.Build;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SimpleArrayMap;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * description:
 * 对象的工具类
 * 1.判断对象集是不是null
 * 2.判断对象是不是 空
 * <p>
 * autour: Simon
 * created at 2017/6/14
 */

public class ObjectUtils {

    /**
     * 判断传入参数是否有null
     *
     * @param objects -需要判断的对象集
     * @return true：没有null false:有null
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

    /**
     * 判断对象是不是为 空
     *
     * @param obj -需要判断的对象
     * @return true：空 false:非空
     */
    public static boolean isEmpty(final Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String && obj.toString().length() == 0) {
            return true;
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof SimpleArrayMap && ((SimpleArrayMap) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof SparseArray && ((SparseArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseBooleanArray && ((SparseBooleanArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseIntArray && ((SparseIntArray) obj).size() == 0) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (obj instanceof SparseLongArray && ((SparseLongArray) obj).size() == 0) {
                return true;
            }
        }
        if (obj instanceof LongSparseArray && ((LongSparseArray) obj).size() == 0) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (obj instanceof android.util.LongSparseArray && ((android.util.LongSparseArray) obj).size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对象是不是不是空
     *
     * @param obj -需要判断的对象
     * @return true：非空 false:空
     */
    public static boolean isNotEmpty(final Object obj) {
        return !isEmpty(obj);
    }


}
