package com.android.utils.common;

import android.util.SparseArray;
import android.view.View;

/**
 * 用于循环使用的view中view缓存
 */
public class ViewHolder {
    private ViewHolder() {
        throw new AssertionError();
    }

    /**
     * 从缓存中获取view，有则直接返回反之获取后返回
     *
     * @param view 父布局
     * @param id   需要获取的view的id
     * @param <T>  view的类型
     * @return 返回对应父布局下的对应id的view
     */
    public static <T extends View> T get(View view, int id) {
        // 用于存储view的一个hashmap
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
