package com.github.vvorks.builder.client.gwt.intl;

/**
 * 日付時刻フォーマットオプション：使用する書式一致アルゴリズム
 */
public enum FormatMatcher implements StringOption {

	BASIC("basic"),

	BESTFIT("best fit");

	private final String value;

	private FormatMatcher(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "formatMatcher";
	}

	@Override
	public final String value() {
		return value;
	}

}