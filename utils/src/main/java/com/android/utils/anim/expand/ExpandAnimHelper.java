package com.android.utils.anim.expand;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;

/**
 * description: 展开动画助手
 * author: Simon
 * created at 2017/8/21 下午3:08
 * <p>
 * 1.可以设置不同的view
 */
public class ExpandAnimHelper {

    //--做动画的view(弱引用方便释放)--
    private WeakReference<View> viewRef = null;

    //--当前的展开状态，默认展开--
    private boolean expanded = true;

    //--动画时间--
    private int expandTime = 0;
    private int collapseTime = 0;

    //--call back--
    private IExpandAnimLisener lisener = null;

    //--方向(水平变化还是垂直变化),未使用--
    private int oritation = LinearLayout.VERTICAL;

    //--动画引用--
    private ValueAnimator expandAnim, collapseAnim;

    //--动画中的实时数据--
    private int targtetHeight = 0;//view 的高度
    private int lastValue = -1;//记录上次的数据，防止重复刷新

    //--做动画的view的真实高度（只获取一次）--
    private int realHeight = 0;//真实的高度


    //--私有化自己的构造函数--
    private ExpandAnimHelper(View view) {
        if (view != null) {
            viewRef = new WeakReference<View>(view);
        } else {
            throw new NullPointerException("传入的view不能为null!=>with(View view)");
        }
    }

    /**
     * 创建helper实例
     *
     * @param view --待做动画的view
     * @return --helper实例
     */
    public static ExpandAnimHelper with(View view) {
        return new ExpandAnimHelper(view);
    }

    /**
     * 设置展开的时间
     *
     * @param expandTime --展开时间
     */
    public ExpandAnimHelper setExpandTime(int expandTime) {
        if (expandTime < 0) {
            expandTime = 0;
        }
        this.expandTime = expandTime;
        return this;
    }

    /**
     * 设置关闭的时间
     *
     * @param collapseTime --关闭时间
     */
    public ExpandAnimHelper setCollapseTime(int collapseTime) {
        if (collapseTime < 0) {
            collapseTime = 0;
        }
        this.collapseTime = collapseTime;
        return this;
    }

    /**
     * 设置监听
     *
     * @param lisener --动画的监听回调
     * @return
     */
    public ExpandAnimHelper setCallback(IExpandAnimLisener lisener) {
        this.lisener = lisener;
        return this;
    }

    @Deprecated
    /**暂未使用*/
    public ExpandAnimHelper setOritation(@LinearLayoutCompat.OrientationMode int oritation) {
        this.oritation = oritation;
        return this;
    }

    /**
     * 获取当前的展开状态
     *
     * @return -true表示展开  -false 表示关闭
     */
    public boolean isExpanded() {
        return expanded;
    }

    @Deprecated
    public int getOritation() {
        return oritation;
    }

    /**
     * 展开（前提是环境满足和未展开过）
     */
    public void expand() {
        //check view || 如果已经展开则不再展开
        if (!checkView() || expanded) {
            return;
        }
        // 修改状态
        expanded = true;
        View view = viewRef.get();
        //先关闭之前的收起的动画
        resetAnim();
        //重新测量当前的高度--获取原始高度
        initHeight();
        targtetHeight = view.getMeasuredHeight();
        expandAnim = createExpandAnimtor(targtetHeight, realHeight);
        //--设置当前的高度为0，从头开始展开--
        view.setVisibility(View.VISIBLE);
        //开始展开动画
        expandAnim.start();
    }

    /**
     * 初始化真实view 高度
     */
    private void initHeight() {
        if (realHeight == 0 && checkView()) {
            realHeight = viewRef.get().getMeasuredHeight();
        }
        targtetHeight = realHeight;
    }

    /**
     * 展开（前提是环境满足和展开过）
     */
    public void collapse() {
        //check view || 如果已经展开则不再展开
        if (!checkView() || !expanded) {
            return;
        }
        // 修改状态
        expanded = false;
        View view = viewRef.get();
        //先关闭之前的收起的动画
        resetAnim();
        //重新测量当前的高度--获取原始高度
        initHeight();
        //注意：此处不需要重新测量，获取当前高度即可
        targtetHeight = view.getMeasuredHeight();
        collapseAnim = createCollapseAnimtor(targtetHeight, realHeight);
        //设置此处可见
        view.setVisibility(View.VISIBLE);
        //开始展开动画
        collapseAnim.start();

    }

    /**
     * 重置anim
     */
    public void resetAnim() {
        if (expandAnim != null) {
            expandAnim.cancel();
            expandAnim = null;
        }

        if (collapseAnim != null) {
            collapseAnim.cancel();
            collapseAnim = null;
        }
    }

    /**
     * 用于切换动画，如果展开则关闭，反之亦然
     */
    public void toggle() {
        if (expanded) {
            collapse();
        } else {
            expand();
        }
    }

    //--用于判断当前环境是否满足--
    private boolean checkView() {
        return viewRef != null && viewRef.get() != null;
    }

    //--用于创建展开动画 from是开始值 result 是结束值--
    private ValueAnimator createExpandAnimtor(int from, final int result) {
        ValueAnimator expandAnim = ValueAnimator.ofInt(from, result).setDuration((long) (expandTime * (result - from) / result * 1.0f));
        expandAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (checkView()) {
                    int currentValue = (int) animation.getAnimatedValue();
                    if (currentValue != lastValue) {
                        viewRef.get().getLayoutParams().height = currentValue;
                        viewRef.get().requestLayout();
                        lastValue = currentValue;
                        notifyChange(currentValue);
                    }
                }
            }
        });
        expandAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                notifyEnd();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                notifyStart();
            }
        });
        expandAnim.setInterpolator(new LinearInterpolator());

        return expandAnim;
    }

    //--用于创建关闭动画 from是开始值 result 是结束值--
    private ValueAnimator createCollapseAnimtor(int from, int result) {
        ValueAnimator collapseAnim = ValueAnimator.ofInt(from, 0).setDuration((long) (collapseTime * from / result * 1.0f));
        collapseAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (checkView()) {
                    int currentValue = (int) animation.getAnimatedValue();
                    if (currentValue != lastValue) {
                        viewRef.get().getLayoutParams().height = (int) animation.getAnimatedValue();
                        viewRef.get().requestLayout();
                        lastValue = currentValue;
                        notifyChange(currentValue);
                    }
                }
            }
        });
        collapseAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                notifyEnd();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                notifyStart();
            }
        });
        collapseAnim.setInterpolator(new LinearInterpolator());
        return collapseAnim;
    }

    //--通知动画开启--
    private void notifyStart() {
        if (lisener != null) {
            lisener.onAnimStart();
        }
    }

    //--通知动画变化--
    private void notifyChange(int value) {
        if (lisener != null) {
            lisener.onAnimChange(value);
        }
    }

    //--通知动画完毕--
    private void notifyEnd() {
        if (lisener != null) {
            lisener.onAnimEnd();
        }
    }


}
