package com.cnr.cnrmodule.util;


import android.content.res.Resources;
import android.text.TextUtils;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class StringTool {

	/**
	 * 判断list是否有效
	 */
	public static <T> boolean isListValidate(List<T> list) {
		if (list != null && !list.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断set是否有效
	 */
	public static <T> boolean isSetValidate(HashSet<T> set) {
		if (set != null && set.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 数组转为列表
	 */
	public static ArrayList<String> arrayToList(String[] strs) {
		if (strs == null) {
			return null;
		}
		ArrayList<String> list = new ArrayList<String>();
		for (String str : strs) {
			list.add(str);
		}
		return list;

	}

	public static String trimNull(String text) {
		if (TextUtils.isEmpty(text)) {
			return "";
		} else {
			return text.trim();
		}
	}

	public static String isTextEmpty(String name){
		if (name == null || name.trim().length() == 0) {
			throw new IllegalArgumentException("Cannot build null or empty name.");
		}else {
			return name;
		}
	}
	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	public static int getScreenWidth() {
		return Resources.getSystem().getDisplayMetrics().widthPixels;
	}

	public static int getScreenHeight() {
		return Resources.getSystem().getDisplayMetrics().heightPixels;
	}
}
