package com.github.vvorks.builder.common.text;

public class StringFormatter extends Formatter {

	@Override
	public CharSequence apply(Object obj) {
		String str = asString(obj);
		int len = str.length();
		int pre = getParams().getPrecision();
		if (0 < pre && pre < len) {
			str = str.substring(0, pre);
		}
		return fill(str, false);
	}

}
