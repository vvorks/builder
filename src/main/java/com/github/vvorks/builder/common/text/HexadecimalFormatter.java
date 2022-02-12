package com.github.vvorks.builder.common.text;

public class HexadecimalFormatter extends Formatter {

	@Override
	public CharSequence apply(Object obj) {
		long value = asNumber(obj).longValue();
		String str = Long.toHexString(value);
		return fill(str, true);
	}

}
