package com.github.vvorks.builder.client.gwt.intl;

/**
 * 数値オプション：通貨の書式で使用するための通貨
 */
public enum SignDisplay implements StringOption {

	/** 負の数のみ符号を表示 */
	AUTO("auto"),

	/** 符号を表示しない */
	NEVER("never"),

	/** 常に符号を表示 */
	ALWAYS("always"),

	/** 正の数と負の数には符号を表示するが、ゼロには表示しない */
	EXCEPT_ZERO("exceptZero");

	private final String value;

	private SignDisplay(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "signDisplay";
	}

	@Override
	public final String value() {
		return value;
	}

}
