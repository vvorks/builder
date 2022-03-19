package com.github.vvorks.builder.client.gwt.intl;

/**
 * 数値オプション：CompactDisplay
 */
public enum CompactDisplay implements StringOption {

	SHORT("short"),

	LONG("long");

	private final String value;

	private CompactDisplay(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "dateStyle";
	}

	@Override
	public final String value() {
		return value;
	}

}
