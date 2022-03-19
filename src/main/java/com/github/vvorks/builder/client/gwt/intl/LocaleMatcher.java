package com.github.vvorks.builder.client.gwt.intl;

/**
 * 共通フォーマットオプション：使用するロケール照合アルゴリズム
 */
public enum LocaleMatcher implements StringOption {

	LOOKUP("lookup"),

	BESTFIT("best fit");

	private final String value;

	private LocaleMatcher(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "localeMatcher";
	}

	@Override
	public final String value() {
		return value;
	}

}