package com.android.utils.anim.expand;

/**
 * description: 展开动画的监听
 * author: Simon
 * created at 2017/8/21 下午3:18
 */
public interface IExpandAnimLisener {
    void onAnimStart();

    void onAnimEnd();

    void onAnimChange(int value);
}