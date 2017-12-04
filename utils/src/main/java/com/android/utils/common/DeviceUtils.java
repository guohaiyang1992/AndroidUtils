package com.android.utils.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * 该工具类用于获取设备的基础信息
 *
 * @author Simon
 * @version v1.0
 * @date 2017/12/4
 */
public class DeviceUtils {

    private DeviceUtils() {
        throw new AssertionError();
    }

    /**
     * 获取当前运行的系统版本号
     *
     * @return 系统版本号
     */
    public static int getSysVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取设备机型 例如 S8
     *
     * @return 机型
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }

    /**
     * 获取设备厂商 比如：三星
     *
     * @return 设备厂商名称
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }


    /**
     * 获取imei 需要此权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}（缺少权限会报错或者黑屏）
     *
     * @return imei 返回imei 码,没有时返回空字符串
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getIMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) AppUtils.INSTANCE.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei == null ? "" : imei;
    }


    /**
     * 获取imsi  需要此权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}（缺少权限会报错或者黑屏）
     *
     * @return 返回imsi 码,没有时返回空字符串
     */
    @SuppressLint("MissingPermission")
    public static String getIMSI() {
        TelephonyManager telephonyManager = (TelephonyManager) AppUtils.INSTANCE.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = telephonyManager.getSubscriberId();
        return imsi == null ? "" : imsi;
    }


}
