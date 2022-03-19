package com.github.vvorks.builder.client.gwt.intl;

/**
 * 日付時刻フォーマットオプション：分の表現
 */
public enum Minute implements StringOption {

	NUMERIC("numeric"),

	TWODIGIT("2-digit");

	private final String value;

	private Minute(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "minute";
	}

	@Override
	public final String value() {
		return value;
	}

}