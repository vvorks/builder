package com.github.vvorks.builder.client.gwt.intl;

/**
 * 日付時刻フォーマットオプション：日付の書式化スタイル
 */
public enum DateStyle implements StringOption {

	FULL("full"),

	LONG("long"),

	MEDIUM("medium"),

	SHORT("short");

	private final String value;

	private DateStyle(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "dateStyle";
	}

	@Override
	public final String value() {
		return value;
	}

}