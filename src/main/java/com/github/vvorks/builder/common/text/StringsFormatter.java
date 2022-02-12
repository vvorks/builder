package com.github.vvorks.builder.common.text;

import java.util.List;

public class StringsFormatter extends CompositeFormatter {

	public static boolean isAcceptable(List<Formatter> fragments) {
		int count = 0;
		for (Formatter f : fragments) {
			if (f instanceof StringFormatter) {
				count++;
			} else if (f instanceof ConstFormatter) {
				//skip
			} else {
				return false;
			}
		}
		return count > 1;
	}

	public StringsFormatter(List<Formatter> fragments) {
		super(fragments);
		for (Formatter f : fragments) {
			if (f instanceof StringFormatter) {
				f.getParams().setFlag(Params.FLAGS_NOTFILL, true);
			}
		}
	}

	@Override
	public CharSequence apply(Object obj) {
		String value = asString(obj);
		StringBuilder sb = new StringBuilder();
		int len = value.length();
		int spos = 0;
		for (Formatter f : fragments) {
			if (f instanceof StringFormatter) {
				int width = f.getParams().getWidth();
				if (width == 0) {
					width = len - spos;
				}
				int epos = Math.min(spos + width, len);
				sb.append(f.apply(value.substring(spos, epos)));
				spos = epos;
				if (epos == len) {
					break;
				}
			} else {
				sb.append(f.apply(obj));
			}
		}
		return sb.toString();
	}

}