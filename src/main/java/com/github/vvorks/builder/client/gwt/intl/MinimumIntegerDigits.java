package com.github.vvorks.builder.client.gwt.intl;

/**
 * 数値フォーマットオプション：整数部に使用する最小桁数
 */
public class MinimumIntegerDigits implements NumberOption {

	private final double value;

	public static MinimumIntegerDigits of(int value) {
		if (!(1 <= value && value <= 21)) {
			throw new IllegalArgumentException();
		}
		return new MinimumIntegerDigits(value);
	}

	private MinimumIntegerDigits(double value) {
		this.value = value;
	}

	@Override
	public String key() {
		return "minimumIntegerDigits";
	}

	@Override
	public Double value() {
		return value;
	}

}
