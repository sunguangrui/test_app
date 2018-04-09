package com.example.iris.product.util;

import android.text.TextUtils;
import android.util.Log;


/**
 * log工具类---调试用
 * 
 * @author wangbin
 * 
 */
public class MyLogUtils {
	/**
	 * log日志过滤的名字
	 */
	private final static String FILTER_NAME = "MyLogUtils";
	private final static boolean IS_SHOW_DEBUG=true;

	/**
	 * 错误日志---红色字体
	 * 
	 * @param msg
	 *            打印信息
	 */
	public static void error(String msg) {
		if (IS_SHOW_DEBUG && !TextUtils.isEmpty(msg)) {
			Log.e(FILTER_NAME, msg);
		}
	}

	/**
	 * 调试日志---蓝色字体
	 * 
	 * @param msg
	 *            打印信息
	 */
	public static void degug(String msg) {
		if (IS_SHOW_DEBUG && !TextUtils.isEmpty(msg)) {
			Log.d(FILTER_NAME, msg);
		}
	}

	/**
	 * 调试日志---绿色字体
	 * 
	 * @param msg
	 *            打印信息
	 */
	public static void info(String msg) {
		if (IS_SHOW_DEBUG && !TextUtils.isEmpty(msg)) {
			Log.i(FILTER_NAME, msg);
		}
	}

}
