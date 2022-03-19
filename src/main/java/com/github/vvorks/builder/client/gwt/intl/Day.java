package com.github.vvorks.builder.client.gwt.intl;

/**
 * 日付時刻フォーマットオプション：日の表現
 */
public enum Day implements StringOption {

	NUMERIC("numeric"),

	TWODIGIT("2-digit");

	private final String value;

	private Day(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "day";
	}

	@Override
	public final String value() {
		return value;
	}

}