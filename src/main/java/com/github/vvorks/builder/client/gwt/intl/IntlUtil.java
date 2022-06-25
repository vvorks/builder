package com.github.vvorks.builder.client.gwt.intl;

import java.util.ArrayList;
import java.util.List;

import com.github.vvorks.builder.client.common.ui.Metrics;
import com.github.vvorks.builder.client.gwt.util.JsObject;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class IntlUtil {

	private static final String LOCALE;

	private static final String GROUP_SYMBOL;

	private static final String DECIMAL_SYMBOL;

	static {
		LOCALE = Metrics.get().getLocale();
		NumberFormat f = NumberFormat.create(
				LOCALE,
				UseGrouping.TRUE,
				MinimumFractionDigits.of(3));
		String gSym = ",";
		String dSym = ".";
		for (FormatPart p : f.formatToParts(1234.567)) {
			String type = p.getType();
			if (type.equals("group")) {
				gSym = p.getValue();
			} else if (type.equals("decimal")) {
				dSym = p.getValue();
			}
		}
		GROUP_SYMBOL = gSym;
		DECIMAL_SYMBOL = dSym;
	}

	private IntlUtil( ) {
	}

	public static String getGroupSymbol() {
		return GROUP_SYMBOL;
	}

	public static String getDecimalSymbol() {
		return DECIMAL_SYMBOL;
	}

	public static List<FormatPart> toFormatParts(JsArray<JavaScriptObject> array) {
		int n = array.length();
		List<FormatPart> list = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			JsObject e = (JsObject) array.get(i);
			String key = e.getString("type");
			String value = e.getString("value");
			list.add(new FormatPart(key, value));
		}
		return list;
	}

	public static int indexOf(List<FormatPart> parts, String type) {
		for (int i = 0; i < parts.size(); i++) {
			if (parts.get(i).getType().equals(type)) {
				return i;
			}
		}
		return -1;
	}

}
