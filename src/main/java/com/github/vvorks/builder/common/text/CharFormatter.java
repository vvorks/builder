package com.github.vvorks.builder.common.text;

public class CharFormatter extends Formatter {

	@Override
	public CharSequence apply(Object obj) {
		int codePoint = (int) asNumber(obj).longValue();
		String str = new String(Character.toChars(codePoint));
		return fill(str, false);
	}

}
