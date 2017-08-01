package com.ghy.androidutils;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.android.utils.common.SpUtils;

/**
 * Created by guohaiyang on 2017/8/1.
 */

public class SpActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        SpUtils.init(SpActivity.this);
        final String key = "key1";
        findViewById(R.id.getBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SpActivity.this, SpUtils.get(key+1, 1) + ":" + SpUtils.get(key+2, 2L) + SpUtils.get(key+3, false) + SpUtils.get(key+4, 1.1f), Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.putBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpUtils.put(key+1, 11);
                SpUtils.put(key+2, 22L);
                SpUtils.put(key+3, true);
                SpUtils.put(key+4, 2.2f);
            }
        });
    }
}
