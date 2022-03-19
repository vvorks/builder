package com.github.vvorks.builder.client.gwt.intl;

/**
 * 数値フォーマットオプション：使用する最大有効桁数
 */
public class MaximumSignificantDigits implements NumberOption {

	private final double value;

	public static MaximumSignificantDigits of(int value) {
		if (!(1 <= value && value <= 21)) {
			throw new IllegalArgumentException();
		}
		return new MaximumSignificantDigits(value);
	}

	private MaximumSignificantDigits(double value) {
		this.value = value;
	}

	@Override
	public String key() {
		return "maximumSignificantDigits";
	}

	@Override
	public Double value() {
		return value;
	}

}
