package com.hf.FollowTheInternetFly.utils;

import android.content.Context;

/**
 * @ClassName: UnitUtils
 * @Description: 单位转换工具
 * @author ghy
 * @date 2016-9-12 上午11:14:37
 * 
 */
public class UnitUtils {
	private static Context context;

	public static void init(Context context) {
		UnitUtils.context = context;
	}

	public static int Dp2Px(float dp) {
		if (context != null) {
			final float scale = context.getResources().getDisplayMetrics().density;
			return (int) (dp * scale + 0.5f);
		} else {
			return 0;
		}

	}

	public static int Px2Dp(float px) {
		if (context != null) {
			final float scale = context.getResources().getDisplayMetrics().density;
			return (int) (px / scale + 0.5f); // 红米的那个是2.06
		} else {
			return 0;
		}

	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int Px2Sp(float pxValue) {
		if (context != null) {
			final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
			return (int) (pxValue / fontScale + 0.5f); // 2.06
		} else {
			return 0;
		}
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int Sp2Px(float spValue) {
		if (context != null) {
			final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
			return (int) (spValue * fontScale + 0.5f);
		} else {
			return 0;
		}
	}
}
