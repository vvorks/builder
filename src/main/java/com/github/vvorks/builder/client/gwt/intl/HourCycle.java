package com.github.vvorks.builder.client.gwt.intl;

/**
 * 日付時刻フォーマットオプション：使用する時の周期
 */
public enum HourCycle implements StringOption {

	H11("h11"),

	H12("h12"),

	H23("h23"),

	H24("h24");

	private final String value;

	private HourCycle(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "hourCycle";
	}

	@Override
	public final String value() {
		return value;
	}

}