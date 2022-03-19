package com.github.vvorks.builder.client.gwt.intl;

/**
 * 日付時刻フォーマットオプション：日単位の期間の表現の仕方
 */
public enum DayPeriod implements StringOption {

	NARROW("narrow"),

	SHORT("short"),

	LONG("long");

	private final String value;

	private DayPeriod(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "dayPeriod";
	}

	@Override
	public final String value() {
		return value;
	}

}