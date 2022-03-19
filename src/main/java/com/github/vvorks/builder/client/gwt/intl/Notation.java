package com.github.vvorks.builder.client.gwt.intl;

/**
 * 数値オプション：数値を表示するための書式
 */
public enum Notation implements StringOption {

	/** 通常の数値の書式 */
	STANDARD("standard"),

	/** 整形された数値の次数 */
	SCIENTIFIC("scientific"),

	/** 10のべき乗 */
	ENGINEERING("engineering"),

	/** 指数表記 */
	COMPACT("compact");

	private final String value;

	private Notation(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "notation";
	}

	@Override
	public final String value() {
		return value;
	}

}
