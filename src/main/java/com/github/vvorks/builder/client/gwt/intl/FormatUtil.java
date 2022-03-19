package com.github.vvorks.builder.client.gwt.intl;

import java.util.ArrayList;
import java.util.List;

import com.github.vvorks.builder.client.gwt.util.JsObject;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class FormatUtil {

	private FormatUtil( ) {
	}

	public static List<FormatPart> toList(JsArray<JavaScriptObject> array) {
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

}
