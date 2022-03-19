package com.github.vvorks.builder.client.gwt.intl;

/**
 * 日付時刻フォーマットオプション：タイムゾーン名の表現
 */
public enum TimeZoneName implements StringOption {

	LONG("long"),

	SHORT("short"),

	LONGOFFSET("longOffset"),

	SHORTOFFSET("shortOffset"),

	LONGGENERIC("longGeneric"),

	SHORTGENERIC("shortGeneric");

	private final String value;

	private TimeZoneName(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "timeZoneName";
	}

	@Override
	public final String value() {
		return value;
	}

}