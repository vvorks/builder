package com.github.vvorks.builder.client.gwt.intl;

import java.util.Date;
import java.util.List;

import com.github.vvorks.builder.client.gwt.util.JsObject;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/**
 * 日付時刻フォーマット
 */
public class DateTimeFormat extends JavaScriptObject {

	public static DateTimeFormat create(String locale, Option<?>... options) {
		JsObject js = JsObject.create();
		for (Option<?> opt : options) {
			opt.setInto(js);
		}
		return create(locale, js);
	}

	private static native DateTimeFormat create(String locale, JavaScriptObject options)/*-{
		return new Intl.DateTimeFormat(locale, options);
	}-*/;

	protected DateTimeFormat() {
	}

	public final String format(Date date) {
		return format0((double) date.getTime());
	}

	public final String format(long t) {
		return format0((double) t);
	}

	private final native String format0(double time)/*-{
		var jsDate = new Date(time);
		return this.format(jsDate);
	}-*/;

	public final List<FormatPart> formatToParts(Date date) {
		return FormatUtil.toList(formatToParts0((double) date.getTime()));
	}

	public final List<FormatPart> formatToParts(long t) {
		return FormatUtil.toList(formatToParts0((double) t));
	}

	private final native JsArray<JavaScriptObject> formatToParts0(double time)/*-{
		var jsDate = new Date(time);
		return this.formatToParts(jsDate);
	}-*/;

}
