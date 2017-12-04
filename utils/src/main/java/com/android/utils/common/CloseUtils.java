package com.android.utils.common;

import java.io.Closeable;
import java.io.IOException;

/**
 * 关闭相关工具类
 *
 * @author Simon
 * @version v1.0
 * @date 2017/12/5
 */

public class CloseUtils {

    /**
     * 用于安全关闭可以关闭的流或者操作
     *
     * @param closeables 需要关闭的内容集合
     */
    public static void safeClose(final Closeable... closeables) {
        //如果为null或者内容为空，直接返回不处理
        if (closeables == null || closeables.length == 0) {
            return;
        }
        //遍历关闭
        for (Closeable closeable : closeables) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
