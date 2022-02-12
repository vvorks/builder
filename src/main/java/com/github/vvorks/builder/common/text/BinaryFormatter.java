package com.github.vvorks.builder.common.text;

public class BinaryFormatter extends Formatter {

	@Override
	public CharSequence apply(Object obj) {
		long value = asNumber(obj).longValue();
		String str = Long.toBinaryString(value);
		return fill(str, true);
	}

}
