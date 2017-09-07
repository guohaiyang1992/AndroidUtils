package com.ghy.androidutils;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.utils.view.popu.Direction;
import com.android.utils.view.popu.PopuwindowHelper;

/**
 * description:  PopuwindowHelper 的测试activity
 * author: Simon
 * created at 2017/9/7 下午1:52
 */

public class PopuwindowActivity extends Activity {
    private TextView testBtn;
    private int x[] = {
            Direction.CENTER_HOR, Direction.ALIGN_LEFT, Direction.ALIGN_RIGHT, Direction.TO_LEFT, Direction.TO_RIGHT
    };
    private int y[] = {
            Direction.ABOVE, Direction.BELOW, Direction.ALIGH_BOTTOM, Direction.ALIGN_TOP, Direction.CENTER_VER
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popuwindow);
        testBtn = (TextView) findViewById(R.id.testBtn);
        View view = LayoutInflater.from(this).inflate(R.layout.popuwindow_test, null);
        PopupWindow popupWindow = new PopupWindow(view, 300, 300);
        popupWindow.setOutsideTouchable(true);
        //-------------------此处的backgroundAlpha是辅助popuwindow 出现时背景变灰，如果不需要则可不填写------------------------
        final PopuwindowHelper helper = new PopuwindowHelper.Builder()
//                .backGroudAlpha(0.7f)
                .popuwindow(popupWindow).build();

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //------------------注意：下方的测试方法可以通过取消注释的方式分别试用（只能同时试用一个）-------------------------

                //--此处的方向 是popuwindow 相对 v的--
//                helper.show(v); //默认
//                helper.show(v, Direction.CENTER_VER); //垂直居中
//                helper.show(v, Direction.CENTER_VER | Direction.CENTER_HOR);//垂直水平居中
//                helper.show(v,Direction.ABOVE); //在上方
//                helper.show(v,Direction.ALIGN_RIGHT); //相邻右侧
//                helper.show(v, Direction.ALIGH_BOTTOM);//相邻底部
//                helper.show(v, Direction.ALIGH_BOTTOM | Direction.CENTER_HOR);//相邻底部 且水平居中
//                helper.show(v, Direction.ABOVE | Direction.CENTER_HOR);//在上方

                //--上面是具体的设置，下面是进行覆盖性测试--
                testXY(helper, v); //测试x,y方向上组合的效果
//                testX(helper, v); //测试x方向上组合的效果
//                testY(helper,v);//测试y方向上组合的效果

                //--测试设置margin--
//                helper.showAndMarginLeft(v, Direction.TO_RIGHT | Direction.ALIGN_TOP, 20); //在v的右侧并且设置marginleft=20px(此处最好设置dp)
                //还有其他方法设置margin 此处就不一一测试了，可以自行尝试
            }
        });


    }

    //--测试 xy方向同时切换位置--
    public void testXY(PopuwindowHelper helper, View v) {
//        helper,可以设置x,y 和同时x，y 不过只能设置一次多次设置会被覆盖掉
        int xIndex = randomIndex(5); //x

        int yIndex = randomIndex(5);

        helper.show(v, x[xIndex] | y[yIndex]);


    }

    //--测试 x方向同时切换位置--
    public void testX(PopuwindowHelper helper, View v) {
//        helper,可以设置x,y 和同时x，y 不过只能设置一次多次设置会被覆盖掉
        int xIndex = randomIndex(5); //x

//        int yIndex = randomIndex(5);

        helper.show(v, x[xIndex] | y[0]);


    }

    //--测试 y方向同时切换位置--
    public void testY(PopuwindowHelper helper, View v) {
//        helper,可以设置x,y 和同时x，y 不过只能设置一次多次设置会被覆盖掉
//        int xIndex = randomIndex(5); //x

        int yIndex = randomIndex(5);

        helper.show(v, x[0] | y[yIndex]);


    }

    private int randomIndex(int max) {
        return (int) (Math.random() * max);
    }


}
