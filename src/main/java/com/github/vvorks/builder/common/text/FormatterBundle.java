package com.github.vvorks.builder.common.text;

import java.util.Collections;
import java.util.List;

public class FormatterBundle extends Formatter {

	private List<Formatter> fragments;

	public FormatterBundle(List<Formatter> fragments) {
		super(Collections.emptyList());
		this.fragments = fragments;
	}

	@Override
	public int getPreferredWidth() {
		int width = 0;
		for (Formatter f : fragments) {
			width += f.getPreferredWidth();
		}
		return width;
	}

	@Override
	public int getPreferredHeight() {
		int height = 0;
		for (Formatter f : fragments) {
			height += f.getPreferredHeight0();
		}
		return Math.max(1, height);
	}

	@Override
	public String format(Object value) {
		StringBuilder sb = new StringBuilder();
		for (Formatter f : fragments) {
			sb.append(f.format(value));
		}
		return sb.toString();
	}

}
