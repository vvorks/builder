package com.github.vvorks.builder.client.gwt.util;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.client.ScriptInjector;

public final class GlobalizeJs extends JavaScriptObject {

	private static String defaultLocale;

	public static void install(String[] jsCode) {
		for (String js : jsCode) {
			ScriptInjector.fromString(js).inject();
		}
	}

	public static void load(String[] cldrData) {
		@SuppressWarnings("unchecked")
		JsArray<JavaScriptObject> array = (JsArray<JavaScriptObject>) createArray();
		for (String json : cldrData) {
			array.push(JsonUtils.safeEval(json));
		}
		load(array);

	}

	private static native void load(JsArray<JavaScriptObject> array)/*-{
		Globalize.load(array);
	}-*/;

	public static void setLocale(String locale) {
		defaultLocale = locale;
		setNativeLocale(locale);
	}

	private static native void setNativeLocale(String loc)/*-{
		Globalize.locale(loc);
	}-*/;

	public static GlobalizeJs create() {
		return create(defaultLocale);
	}

	public static native GlobalizeJs create(String loc)/*-{
		return new Globalize(loc);
	}-*/;

	protected GlobalizeJs() {
	}

	public final String formatDate(Date date) {
		return formatDate(JsDate.create((double)date.getTime()));
	}

	public final native String formatDate(JsDate date)/*-{
		return this.formatDate(date);
	}-*/;

}
