package com.github.vvorks.builder.common.text;

import java.util.List;

import com.github.vvorks.builder.common.lang.Strings;

public class LineFeedFormatter extends Formatter {

	public LineFeedFormatter(List<Pattern> patterns) {
		super(patterns);
	}

	@Override
	public String format(Object value) {
		StringBuilder sb = new StringBuilder();
		for (Pattern p : patterns) {
			sb.append(Strings.repeat("\n", Math.max(1, p.getWidth())));
		}
		return sb.toString();
	}

}
