package com.github.vvorks.builder.client.gwt.intl;

/**
 * 日付時刻フォーマットオプション：時の表現
 */
public enum Hour implements StringOption {

	NUMERIC("numeric"),

	TWODIGIT("2-digit");

	private final String value;

	private Hour(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "hour";
	}

	@Override
	public final String value() {
		return value;
	}

}