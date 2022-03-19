package com.github.vvorks.builder.client.gwt.intl;

/**
 * 数値オプション：マイナス記号の表現形式
 */
public enum CurrencySign implements StringOption {

	STANDARD("standard"),

	ACCOUNTING("accounting");

	private final String value;

	private CurrencySign(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "currencySign";
	}

	@Override
	public final String value() {
		return value;
	}

}
