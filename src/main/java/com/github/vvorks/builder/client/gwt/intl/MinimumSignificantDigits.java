package com.github.vvorks.builder.client.gwt.intl;

/**
 * 数値フォーマットオプション：使用する最小有効桁数
 */
public class MinimumSignificantDigits implements NumberOption {

	private final double value;

	public static MinimumSignificantDigits of(int value) {
		if (!(1 <= value && value <= 21)) {
			throw new IllegalArgumentException();
		}
		return new MinimumSignificantDigits(value);
	}

	private MinimumSignificantDigits(double value) {
		this.value = value;
	}

	@Override
	public String key() {
		return "minimumSignificantDigits";
	}

	@Override
	public Double value() {
		return value;
	}

}
