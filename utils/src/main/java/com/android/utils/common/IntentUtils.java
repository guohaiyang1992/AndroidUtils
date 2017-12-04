package com.android.utils.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lys
 * date: 2017/6/2 09:31
 * email: njzy_lys@163.com
 * fix: 增加条件判断 2017/12/5 by Simon
 */

public class IntentUtils {
    private IntentUtils() {
        throw new AssertionError();
    }

    /**
     * 在调用系统有关软件时，需要先判断一下，该类型软件是否存在，避免找不到activity异常。
     *
     * @param context 上下文
     * @param intent  意图
     * @return true：表示可用 false:表示不可用
     */
    public static boolean isIntentAvaileble(Context context, Intent intent) {
        //基础条件不满足直接返回false
        if (ObjectUtils.hasNull(context, intent)) {
            return false;
        }
        List<ResolveInfo> resolves = context.getPackageManager().queryIntentActivities(intent, 0);
        //返回获取的值不是null且size>0
        return !ObjectUtils.isEmpty(resolves);
    }
}
