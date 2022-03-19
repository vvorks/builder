package com.github.vvorks.builder.client.gwt.intl;

/**
 * 数値オプション：unit の整形で使用する単位の整形スタイル
 */
public enum UnitDisplay implements StringOption {

	LONG("long"),

	SHORT("short"),

	NARROW("narrow");

	private final String value;

	private UnitDisplay(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "unitDisplay";
	}

	@Override
	public final String value() {
		return value;
	}

}
