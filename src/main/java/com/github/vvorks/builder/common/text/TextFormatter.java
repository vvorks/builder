package com.github.vvorks.builder.common.text;

import java.util.List;

public class TextFormatter extends Formatter {

	private final boolean splitMode;

	public TextFormatter(List<Pattern> patterns) {
		super(patterns);
		splitMode = isSplitMode(patterns);
	}

	private boolean isSplitMode(List<Pattern> patterns) {
		int strCount = 0;
		for (Pattern p : patterns) {
			if (p.isTextPattern()) {
				if (p.getWidth() == 0) {
					return false;
				}
				strCount++;
			}
		}
		return strCount > 1;
	}

	@Override
	public String format(Object value) {
		String str = String.valueOf(value);
		StringBuilder sb = new StringBuilder();
		int spos = 0;
		int len = str.length();
		for (Pattern p : patterns) {
			if (p.isLiteral()) {
				sb.append(p.getLiteral());
			} else if (!splitMode) {
				sb.append(fill(str, p, false));
			} else if (spos < len) {
				int epos = spos + p.getWidth();
				if (epos < len) {
					sb.append(str.substring(spos, epos));
					spos = epos;
				} else {
					sb.append(str.substring(spos));
					break;
				}
			}
		}
		return sb.toString();
	}

}
