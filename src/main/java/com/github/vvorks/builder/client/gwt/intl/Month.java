package com.github.vvorks.builder.client.gwt.intl;

/**
 * 日付時刻フォーマットオプション：月の表現
 */
public enum Month implements StringOption {

	NUMERIC("numeric"),

	TWODIGIT("2-digit"),

	LONG("long"),

	SHORT("short"),

	NARROW("narrow");

	private final String value;

	private Month(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "month";
	}

	@Override
	public final String value() {
		return value;
	}

}