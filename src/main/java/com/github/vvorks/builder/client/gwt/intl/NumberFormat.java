package com.github.vvorks.builder.client.gwt.intl;

import java.util.List;

import com.github.vvorks.builder.client.gwt.util.JsObject;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/**
 * 数値フォーマット
 */
public class NumberFormat extends JavaScriptObject {

	public static NumberFormat create(String locale, Option<?>... options) {
		JsObject js = JsObject.create();
		for (Option<?> opt : options) {
			opt.setInto(js);
		}
		return create(locale, js);
	}

	private static native NumberFormat create(String locale, JavaScriptObject options)/*-{
		return new Intl.NumberFormat(locale, options);
	}-*/;

	protected NumberFormat() {
	}

	public final String format(double value) {
		return format0(value);
	}

	private final native String format0(double value)/*-{
		return this.format(value);
	}-*/;

	public final List<FormatPart> formatToParts(double value) {
		return IntlUtil.toFormatParts(formatToParts0(value));
	}

	private final native JsArray<JavaScriptObject> formatToParts0(double value)/*-{
		return this.formatToParts(value);
	}-*/;

}
