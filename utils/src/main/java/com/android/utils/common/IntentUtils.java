package com.android.utils.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * author: lys
 * date: 2017/6/2 09:31
 * email: njzy_lys@163.com
 */

public class IntentUtils {
    private IntentUtils() {
        throw new AssertionError();
    }

    /**
     * 在调用系统有关软件时，需要贤判断一下，该类型软件是否存在，避免找不到activity异常。
     * @param context 上下文
     * @param intent  意图
     * @return
     */
    public static boolean isIntentAvaileble(Context context, Intent intent) {
        List<ResolveInfo> resolves = context.getPackageManager().queryIntentActivities(intent, 0);
        return resolves.size() > 0;
    }
}
