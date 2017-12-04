package com.android.utils.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.text.TextUtils;

/**
 * 网络工具类
 *
 * @author Simon
 * @version v1.0
 * @date 2017/12/5
 */
public class NetWorkUtils {

    private NetWorkUtils() {
        throw new AssertionError();
    }

    /**
     * 跳转到网络设置页面
     */
    public static void gotoWirelessSetting() {

        Intent settingIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //生成的intent 为null 或者不可用直接返回
        if (ObjectUtils.isNull(settingIntent) || !IntentUtils.isIntentAvaileble(AppUtils.INSTANCE, settingIntent)) {
            return;
        }
        //验证完毕后调用
        AppUtils.INSTANCE.startActivity(settingIntent);
    }

    /**
     * 获取活动网络信息 （需要添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}）
     *
     * @return 网络信息
     */
    @SuppressLint("MissingPermission")
    public static NetworkInfo getActiveNetWorkInfo() {
        ConnectivityManager connectivityManager = (ConnectivityManager) AppUtils.INSTANCE.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (ObjectUtils.isNull(connectivityManager)) {
            return null;
        }
        return connectivityManager.getActiveNetworkInfo();
    }

    /**
     * 用于判断网络是否连接（区别于网络是否可用{@link #isAvailable()}）
     * 需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}
     *
     * @return {@code true}:连接<br> {@code false}:未连接
     */
    public static boolean isConnected() {
        NetworkInfo info = getActiveNetWorkInfo();
        return info != null && info.isConnected();
    }


    /**
     * 判断网络是否可用(内部会首先判断网络是否连接，连接后再判断是否可以ping通)
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     * <p>需要异步 ping，如果 ping 不通就说明网络不可用</p>
     * <p>ping 的 ip 为114公共 ip：114.114.114.114</p>
     *
     * @return {@code true}: 可用<br>{@code false}: 不可用
     */
    public static boolean isAvailable() {
        return isAvailable(null);
    }

    /**
     * 判断网络是否可用(内部会首先判断网络是否连接，连接后再判断是否可以ping通)
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     * <p>需要异步 ping，如果 ping 不通就说明网络不可用</p>
     * <p>ping 的 ip 为114公共 ip：114.114.114.114</p>
     *
     * @return {@code true}: 可用<br>{@code false}: 不可用
     */
    public static boolean isAvailable(String ipAddress) {
        //传入ip不可用时候使用114 公共地址
        if (TextUtils.isEmpty(ipAddress)) {
            ipAddress = "114.114.114.114";
        }
        //判断网络是否连接,没有连接直接返回false
        if (!isConnected()) {
            return false;
        }
        //连接可用后开始ping
        ShellUtils.CommandResult result = ShellUtils.execCmd(String.format("ping -c 1 %s", ipAddress), false);
        boolean ret = result.result == 0;
        return ret;
    }
}
