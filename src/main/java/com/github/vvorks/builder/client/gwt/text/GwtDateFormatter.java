package com.github.vvorks.builder.client.gwt.text;

import com.github.vvorks.builder.common.text.DateFormatter;
import com.google.gwt.i18n.client.DateTimeFormat;

public class GwtDateFormatter extends DateFormatter {

	private DateTimeFormat formatter;

	public GwtDateFormatter(String locale) {
		formatter = DateTimeFormat.getFormat("YYYY");

		// TODO 自動生成されたコンストラクター・スタブ
	}


}
