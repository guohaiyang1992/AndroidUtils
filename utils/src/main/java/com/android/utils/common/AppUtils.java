package com.android.utils.common;

import android.app.Application;

/**
 * 特点：
 * 通过反射获取application实例，支持自定义application
 * <p>
 * Author:Simon
 */

public class AppUtils {
    // application 的引用
    public static final Application INSTANCE;

    /**
     * 通过反射在加载类的时候获取application,仅仅第一次需要后续则不需要（不需要考虑过多性能的损耗）
     */
    static {
        Application app = null;
        try {
            app = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
            if (app == null) {
                throw new IllegalStateException("Static initialization of Applications must be on main thread.");
            }
        } catch (final Exception e) {

            try {
                app = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
            } catch (final Exception ex) {

            }
        } finally {
            INSTANCE = app;
        }
    }
}
