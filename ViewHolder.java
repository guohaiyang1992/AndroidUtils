package com.hfga.resumebooklib.utils.common;

import android.util.SparseArray;
import android.view.View;

public class ViewHolder {
	// 使用了泛型，必须是T必须是view 的子类，且返回为T （此处会转型为对应的类型）
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
