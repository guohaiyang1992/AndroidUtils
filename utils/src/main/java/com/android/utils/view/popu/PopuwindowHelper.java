package com.android.utils.view.popu;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.lang.ref.WeakReference;

import static com.android.utils.view.popu.Direction.ABOVE;
import static com.android.utils.view.popu.Direction.ALIGH_BOTTOM;
import static com.android.utils.view.popu.Direction.ALIGN_LEFT;
import static com.android.utils.view.popu.Direction.ALIGN_RIGHT;
import static com.android.utils.view.popu.Direction.ALIGN_TOP;
import static com.android.utils.view.popu.Direction.BELOW;
import static com.android.utils.view.popu.Direction.CENTER_HOR;
import static com.android.utils.view.popu.Direction.CENTER_VER;
import static com.android.utils.view.popu.Direction.TO_LEFT;
import static com.android.utils.view.popu.Direction.TO_RIGHT;


/**
 * description: 用于增强对popuwindow的操作，比如位置，相对位置，显示（加阴影），隐藏（去阴影）
 * 由于popuwindow 内部可以设置动画，此处就不对动画做处理了
 * author: Simon
 * created at 2017/9/6 上午9:02
 * 注意：如果用户使用了dismiss 回调，需要手动调用 resetBackWindowAlpha
 */
public class PopuwindowHelper implements PopupWindow.OnDismissListener {

    //--activity的背景透明度--
    private float backGroudAlpha = 1.0f;
    //--当前操作的popuwindow--
    private WeakReference<PopupWindow> popuwindowRef = null;
    //--宿主activity--
    private Activity context;
    //--TAG--
    private static final String TAG = "PopuwindowHelper";


    /**
     * helper的构造函数的
     *
     * @param popu           待操作的popuwindow
     * @param backGroudAlpha 背景的透明度（0.0-1.0）
     */
    private PopuwindowHelper(PopupWindow popu, float backGroudAlpha) {
        this.backGroudAlpha = backGroudAlpha;
        popuwindowRef = new WeakReference<PopupWindow>(popu);
        context = (Activity) popu.getContentView().getContext();
    }

    //--------------------具体的显示方法-----------------------

    /**
     * 默认的显示方法，默认显示位置，是view的左下方(ALIGN_LEFT | BELOW)
     *
     * @param anchor --目标view
     */
    public void show(View anchor) {
        show(anchor, ALIGN_LEFT | BELOW, 0, 0, 0, 0);
    }

    /**
     * 可修改显示位置的方法
     *
     * @param anchor    目标view
     * @param direction 使用{@link Direction}类去设置方向
     */
    public void show(View anchor, int direction) {
        show(anchor, direction, 0, 0, 0, 0);
    }

    /**
     * 可修改显示位置和marginLeft
     *
     * @param anchor     目标view
     * @param direction  使用{@link Direction}类去设置方向
     * @param marginLeft marginLeft 单位px
     */
    public void showAndMarginLeft(View anchor, int direction, int marginLeft) {
        show(anchor, direction, marginLeft, 0, 0, 0);
    }

    /**
     * 可修改显示位置和marginRight
     *
     * @param anchor      目标view
     * @param direction   使用{@link Direction}类去设置方向
     * @param marginRight marginRight 单位px
     */
    public void showAndMarginRight(View anchor, int direction, int marginRight) {
        show(anchor, direction, 0, 0, marginRight, 0);
    }

    /**
     * 可修改显示位置和marginRight
     *
     * @param anchor    目标view
     * @param direction 使用{@link Direction}类去设置方向
     * @param marginTop marginTop 单位px
     */
    public void showAndMarginTop(View anchor, int direction, int marginTop) {
        show(anchor, direction, 0, marginTop, 0, 0);
    }

    /**
     * 可修改显示位置和marginRight
     *
     * @param anchor       目标view
     * @param direction    使用{@link Direction}类去设置方向
     * @param marginBottom marginBottom 单位px
     */
    public void showAndMarginBottom(View anchor, int direction, int marginBottom) {
        show(anchor, direction, 0, 0, 0, marginBottom);
    }

    /**
     * 可修改显示位置和margin的方法
     *
     * @param anchor       目标view
     * @param direction    使用{@link Direction}类去设置方向
     * @param marginLeft   marginLeft 单位px
     * @param marginTop    marginTop 单位px
     * @param marginRight  marginRight 单位px
     * @param marginBottom marginBottom 单位px
     */
    public void show(View anchor, int direction, int marginLeft, int marginTop, int marginRight, int marginBottom) {
        //--J检查数据--
        if (!checkPopu() || anchor == null || anchor.getHeight() == 0 || anchor.getWidth() == 0) {
            return;
        }
        //--解析方向中的y--
        int y = 0;
        //--上方--
        if (Direction.hasValue(direction, ABOVE)) {
            y = 0 - popuwindowRef.get().getHeight() - anchor.getHeight();
        }
        //--下方不需要做处理--

        //--center_ver--
        if (Direction.hasValue(direction, CENTER_VER)) {
            y = 0 - anchor.getHeight() / 2;
        }

        //--相邻上方--
        if (Direction.hasValue(direction, ALIGN_TOP)) {
            y = 0 - anchor.getHeight();
        }

        //--相邻下方--
        if (Direction.hasValue(direction, ALIGH_BOTTOM)) {
            y = 0 - popuwindowRef.get().getHeight();
        }

        //--解析方向中的x--
        int x = 0;
        //--在右侧--
        if (Direction.hasValue(direction, TO_RIGHT)) {
            x = 0 + anchor.getWidth();
        }

        //--在左侧--
        if (Direction.hasValue(direction, TO_LEFT)) {
            x = 0 - popuwindowRef.get().getWidth();
        }

        //--相邻右侧--
        if (Direction.hasValue(direction, ALIGN_RIGHT)) {
            x = 0 + anchor.getWidth() - popuwindowRef.get().getWidth();
        }

        //--相邻左侧 是默认的不需处理--

        //--center_hor--
        if (Direction.hasValue(direction, CENTER_HOR)) {
            x = 0 + anchor.getWidth() / 2 - popuwindowRef.get().getWidth() / 2;
        }

        //--处理margin--
        if (marginLeft != 0) {
            x += marginLeft;
        }
        if (marginRight != 0) {
            x -= marginRight;
        }
        if (marginTop != 0) {
            y += marginTop;
        }
        if (marginBottom != 0) {
            y -= marginBottom;
        }

        //--显示popuwindow--
        popuwindowRef.get().showAsDropDown(anchor, x, y);
        //--修改背景窗体透明度--
        changeBackWindowAlpha();
        //--监听消失的时候，恢复透明度--
        popuwindowRef.get().setOnDismissListener(this);

    }


    /**
     * 用于修改背景窗口的透明度
     */
    public void changeBackWindowAlpha() {
        //--修改背景透明度--
        if (backGroudAlpha < 0 || backGroudAlpha > 1) {
            backGroudAlpha = 1.0f;
        }

        //--check context--
        if (context != null && context instanceof Activity) {
            WindowManager.LayoutParams lp = context.getWindow().getAttributes();
            //--透明度和当前的不一致的时候才 设置--
            if (lp.alpha != backGroudAlpha) {
                lp.alpha = backGroudAlpha;
                context.getWindow().setAttributes(lp);
            }
        }
    }

    /**
     * 用于恢复 背景窗口的透明度
     */
    public void resetBackWindowAlpha() {
        //--check context--
        if (context != null && context instanceof Activity) {
            WindowManager.LayoutParams lp = context.getWindow().getAttributes();
            //--透明度和当前的不一致的时候才 设置--
            if (lp.alpha != 1.0f) {
                lp.alpha = 1.0f;
                context.getWindow().setAttributes(lp);
            }
        }
    }


    /**
     * 检查 popuwindow
     */
    private boolean checkPopu() {
        if (popuwindowRef == null || popuwindowRef.get() == null) {
            return false;
        }
        return true;
    }


    /**
     * 释放引用
     */
    public void release() {
        //--释放popuwindow 引用--
        PopupWindow popu = popuwindowRef.get();
        popu = null;
        popuwindowRef = null;
        //--释放 activity 引用--
        context = null;
    }

    @Override
    public void onDismiss() {
        resetBackWindowAlpha();
    }


    //-------------------popuwindow helper 构造器------------------------

    /**
     * popuwindow helper的构造器
     */
    public static class Builder {
        private float backGroudAlpha = 1.0f; //activity的背景透明度
        private PopupWindow popu = null;//当前操作的popuwindow

        /**
         * 用于设置 背景 透明度 （修改activity window）
         *
         * @param backGroudAlpha （0.0f-1.0f）
         */
        public Builder backGroudAlpha(float backGroudAlpha) {
            this.backGroudAlpha = backGroudAlpha;
            return this;
        }


        /**
         * 用于设置popuwindow
         *
         * @param popupWindow --待操作的window
         */
        public Builder popuwindow(PopupWindow popupWindow) {
            this.popu = popupWindow;
            return this;
        }

        /**
         * 最后的构建方法
         *
         * @return popuwindow helper
         */
        public PopuwindowHelper build() {
            //--此为必填项--
            if (popu == null) {
                throw new NullPointerException("popuwindow 不允许为null,检查是否进行了设置？！=>popuwindow(PopupWindow popupWindow) ");
            }
            return new PopuwindowHelper(popu, backGroudAlpha);
        }

    }
    //------------------构造器 end-------------------------

}
