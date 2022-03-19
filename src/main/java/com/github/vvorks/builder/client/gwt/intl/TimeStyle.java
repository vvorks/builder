package com.github.vvorks.builder.client.gwt.intl;

/**
 * 日付時刻フォーマットオプション：時刻の書式化スタイル
 */
public enum TimeStyle implements StringOption {

	FULL("full"),

	LONG("long"),

	MEDIUM("medium"),

	SHORT("short");

	private final String value;

	private TimeStyle(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "timeStyle";
	}

	@Override
	public final String value() {
		return value;
	}

}