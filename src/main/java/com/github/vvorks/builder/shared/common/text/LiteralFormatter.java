package com.github.vvorks.builder.shared.common.text;

import java.util.List;

public class LiteralFormatter extends Formatter {

	public LiteralFormatter(List<Pattern> patterns) {
		super(patterns);
	}

	@Override
	public String format(Object value) {
		StringBuilder sb = new StringBuilder();
		for (Pattern p : patterns) {
			sb.append(p.getLiteral());
		}
		return sb.toString();
	}

}
