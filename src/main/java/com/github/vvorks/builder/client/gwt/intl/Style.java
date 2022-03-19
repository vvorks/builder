package com.github.vvorks.builder.client.gwt.intl;

/**
 * 数値オプション：整形スタイル
 */
public enum Style implements StringOption {

	/** 数値の整形 */
	DECIMAL("decimal"),

	/** 通貨の整形 */
	CURRENCY("currency"),

	/** パーセントの整形 */
	PERCENT("percent"),

	/** 単位の整形 */
	UNIT("unit");

	private final String value;

	private Style(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "style";
	}

	@Override
	public final String value() {
		return value;
	}

}
