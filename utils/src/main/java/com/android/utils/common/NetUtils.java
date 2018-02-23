package com.android.utils.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 用于获取网络相关的内容
 *
 * @author Simon
 * @version v1.0
 * @date 2018/2/23
 */

public class NetUtils {
    /**
     * 默认无用ip
     */
    private static final String UNUSEFULL_IP = "0.0.0.0";

    /**
     * 获取wifi 的ip(需要权限{@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>})
     * 1.当没有wifi模块时返回0.0.0.0
     * 2.当wifi 模块没有打开时返回 0.0.0.0
     * 3.当wifi连接时（无论可否上网），返回对应分配的ip
     *
     * @return 返回对应的ip地址 形如127.0.0.1
     */
    @SuppressLint("MissingPermission")
    public static String getWiFiIp() {
        WifiManager wifiManager = (WifiManager) AppUtils.INSTANCE.getSystemService(Context.WIFI_SERVICE);
        //if not wifi module
        if (wifiManager == null) {
            return UNUSEFULL_IP;
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ipAddress = intIpToStrIp(wifiInfo.getIpAddress());
        return ipAddress;
    }


    /**
     * 获取移动网络的ip<br/>
     * 1.当前移动网络打开并且连接时 返回对应的分配的ip<br/>
     * 2.没有开启移动网络时，返回0.0.0.0<br/>
     *
     * @return 返回对应的ip地址 形如127.0.0.1
     */
    public static String getMobileIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            return UNUSEFULL_IP;
        }
        return UNUSEFULL_IP;
    }

    /**
     * 获取当前的ip地址 需要权限{@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}
     * 和 权限{@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}<br/>
     * 1.获取当前网络的链接状态，如果尚未开启则<br/>
     *
     * @return 返回当前激活网络的ip 形如127.0.0.1
     */
    @SuppressLint("MissingPermission")
    public static String getIp() {
        NetworkInfo info = ((ConnectivityManager) AppUtils.INSTANCE
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        //get info successful
        if (info != null || info.isConnected()) {
            //check network type
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                return getMobileIp();
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return getWiFiIp();
            }
        }
        //other return default ip
        return UNUSEFULL_IP;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip int类型的ip
     * @return 返回String类型的ip 形如 127.0.0.1
     */
    public static String intIpToStrIp(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }
}
