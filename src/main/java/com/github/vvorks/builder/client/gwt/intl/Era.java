package com.github.vvorks.builder.client.gwt.intl;

/**
 * 日付時刻フォーマットオプション：時代の表現
 */
public enum Era implements StringOption {

	LONG("long"),

	SHORT("short"),

	NARROW("narrow");

	private final String value;

	private Era(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "era";
	}

	@Override
	public final String value() {
		return value;
	}

}