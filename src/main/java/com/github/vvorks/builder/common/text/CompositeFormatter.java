package com.github.vvorks.builder.common.text;

import java.util.List;

public class CompositeFormatter extends Formatter {

	protected final List<Formatter> fragments;

	public CompositeFormatter(List<Formatter> fragments) {
		this.fragments = fragments;
	}

	@Override
	public int preferredWidth() {
		int width = 0;
		for (Formatter f : fragments) {
			width += f.preferredWidth();
		}
		return width;
	}

	@Override
	public int preferredHeight() {
		int height = 1;
		for (Formatter f : fragments) {
			if (f instanceof LineFormatter) {
				height += f.preferredHeight();
			}
		}
		return height;
	}

	@Override
	public CharSequence apply(Object obj) {
		StringBuilder sb = new StringBuilder();
		for (Formatter f : fragments) {
			sb.append(f.apply(obj));
		}
		return sb.toString();
	}

}