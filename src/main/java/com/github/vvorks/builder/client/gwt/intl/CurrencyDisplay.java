package com.github.vvorks.builder.client.gwt.intl;

/**
 * 数値オプション：通貨の書式で通貨を表示する方法
 */
public enum CurrencyDisplay implements StringOption {

	SYMBOL("symbol"),

	NARROW_SYMBOL("narrowSymbol"),

	CODE("code"),

	NAME("name");

	private final String value;

	private CurrencyDisplay(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "currencyDisplay";
	}

	@Override
	public final String value() {
		return value;
	}

}
