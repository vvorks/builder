package com.github.vvorks.builder.client.gwt.intl;

/**
 * 日付時刻フォーマットオプション：曜日の表現
 */
public enum Weekday implements StringOption {

	LONG("long"),

	SHORT("short"),

	NARROW("narrow");

	private final String value;

	private Weekday(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "weekday";
	}

	@Override
	public final String value() {
		return value;
	}

}