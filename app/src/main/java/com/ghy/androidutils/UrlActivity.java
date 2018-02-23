package com.ghy.androidutils;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.android.utils.common.UrlUtils;

/**
 * 用于测试url 工具类功能
 *
 * @author Simon
 * @version v1.0
 * @date 2018/2/24
 */

public class UrlActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
        final TextView contentTv = (TextView) findViewById(R.id.content_tv);
        findViewById(R.id.sep_en_de_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clear
                contentTv.setText("");
                String url = "http://www.w3school.com.cn/My first/";
                contentTv.append("输入内容：" + url + "\n");
                String encodeUrl1 = UrlUtils.encodeURI(url);
                contentTv.append("编码1次输出：" + encodeUrl1+ "\n");
                contentTv.append("解码1次输出：" + UrlUtils.decodeURI(encodeUrl1)+ "\n");
                String encodeUrl2 = UrlUtils.encodeURI(UrlUtils.encodeURI(url));
                contentTv.append("编码2次输出：" + encodeUrl2+ "\n");
                contentTv.append("解码2次输出：" + UrlUtils.decodeURI(UrlUtils.decodeURI(encodeUrl2))+ "\n");
            }
        });

        findViewById(R.id.en_de_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clear
                contentTv.setText("");
                String url = "http://www.w3school.com.cn/My first/";
                contentTv.append("输入内容：" + url + "\n");
                String encodeUrl1 = UrlUtils.encodeURIComponent(url);
                contentTv.append("编码1次输出：" + encodeUrl1+ "\n");
                contentTv.append("解码1次输出：" + UrlUtils.decodeURIComponent(encodeUrl1)+ "\n");
                String encodeUrl2 = UrlUtils.encodeURIComponent(UrlUtils.encodeURIComponent(url));
                contentTv.append("编码2次输出：" + encodeUrl2+ "\n");
                contentTv.append("解码2次输出：" + UrlUtils.decodeURIComponent(UrlUtils.decodeURIComponent(encodeUrl2))+ "\n");
            }
        });

    }
}
