package com.github.vvorks.builder.common.text;

public class DecimalFormatter extends Formatter {

	@Override
	public int preferredWidth() {
		Params params = getParams();
		int preferred = 0;
		preferred += 1; //for sign
		int width = Math.max(1, params.getWidth());
		preferred += width;
		int precision = params.getPrecision();
		if (precision > 0) {
			preferred += 1 + precision;
		}
		return preferred;
	}

	@Override
	public CharSequence apply(Object obj) {
		long value = asNumber(obj).longValue();
		String str = Long.toString(value);
		return fill(str, true);
	}

}
