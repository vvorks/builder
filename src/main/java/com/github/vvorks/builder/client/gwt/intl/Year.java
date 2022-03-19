package com.github.vvorks.builder.client.gwt.intl;

/**
 * 日付時刻フォーマットオプション：年の表現
 */
public enum Year implements StringOption {

	NUMERIC("numeric"),

	TWODIGIT("2-digit");

	private final String value;

	private Year(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "year";
	}

	@Override
	public final String value() {
		return value;
	}

}