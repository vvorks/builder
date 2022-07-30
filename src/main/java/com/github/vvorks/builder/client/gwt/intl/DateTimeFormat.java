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
		double dt = date.getTime();
		return format0(dt);
	}

	public final String format(long t) {
		double dt = t;
		return format0(dt);
	}

	private final native String format0(double time)/*-{
		var jsDate = new Date(time);
		return this.format(jsDate);
	}-*/;

	public final List<FormatPart> formatToParts(Date date) {
		double dt = date.getTime();
		return IntlUtil.toFormatParts(formatToParts0(dt));
	}

	public final List<FormatPart> formatToParts(long t) {
		double dt = t;
		return IntlUtil.toFormatParts(formatToParts0(dt));
	}

	private final native JsArray<JavaScriptObject> formatToParts0(double time)/*-{
		var jsDate = new Date(time);
		return this.formatToParts(jsDate);
	}-*/;

}
