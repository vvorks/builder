package com.github.vvorks.builder.common.text;

import com.github.vvorks.builder.common.lang.Strings;

public class LineFormatter extends Formatter {

	@Override
	public int preferredWidth() {
		return 0;
	}

	@Override
	public CharSequence apply(Object obj) {
		int width = Math.max(1, getParams().getWidth());
		return Strings.repeat("\n", width);
	}

}
