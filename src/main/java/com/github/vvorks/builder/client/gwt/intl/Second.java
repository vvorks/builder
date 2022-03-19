package com.github.vvorks.builder.client.gwt.intl;

/**
 * 日付時刻フォーマットオプション：秒の表現
 */
public enum Second implements StringOption {

	NUMERIC("numeric"),

	TWODIGIT("2-digit");

	private final String value;

	private Second(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "second";
	}

	@Override
	public final String value() {
		return value;
	}

}