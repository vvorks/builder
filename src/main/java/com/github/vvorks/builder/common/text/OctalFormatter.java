package com.github.vvorks.builder.common.text;

public class OctalFormatter extends Formatter {

	@Override
	public CharSequence apply(Object obj) {
		long value = asNumber(obj).longValue();
		String str = Long.toOctalString(value);
		return fill(str, true);
	}

}
