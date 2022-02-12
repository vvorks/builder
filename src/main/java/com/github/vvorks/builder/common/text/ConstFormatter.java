package com.github.vvorks.builder.common.text;

public class ConstFormatter extends Formatter {

	private CharSequence text;

	public ConstFormatter(CharSequence text) {
		this.text = text;
	}

	public int preferredWidth() {
		return measureText(text);
	}

	public CharSequence apply(Object obj) {
		return text;
	}

}