package com.android.utils.common;

import android.net.Uri;

/**
 * url操作相关的工具类
 *
 * @author Simon
 * @version v1.0
 * @date 2018/2/24
 */

public class UrlUtils {
    /**
     * Url分隔字符
     */
    private static final String SEPARATE_CHARACTERS = ",/?:@&=+$#";


    /**
     * 对url 进行编码 参照 js中encodeURI的实现，对分隔符内容不进行编码<br/>
     * 例子：encodeURI("http://www.w3school.com.cn/My first/")=>http://www.w3school.com.cn/My%20first/<br/>
     *
     * @param content 待编码内容
     * @return 返回编码后的url
     */
    public static String encodeURI(String content) {
        return Uri.encode(content, SEPARATE_CHARACTERS);
    }

    /**
     * 对url进行编码 参照js中encodeURIComponent的实现，对分割也进行编码<br/>
     * 例子：encodeURIComponent("http://www.w3school.com.cn/p 1/")=>http%3A%2F%2Fwww.w3school.com.cn%2Fp%201%2F
     *
     * @param content 待编码内容
     * @return 返回编码后的url
     */
    public static String encodeURIComponent(String content) {
        return Uri.encode(content);
    }

    /**
     * 对url 进行解码
     *
     * @param content 待解码内容
     * @return 返回解码后的url
     */
    public static String decodeURI(String content) {
        return Uri.decode(content);
    }

    /**
     * 对url进行解码
     * @param content 待解码内容
     * @return 返回解码后的url
     */
    public static String decodeURIComponent(String content) {
        return Uri.decode(content);
    }
}
