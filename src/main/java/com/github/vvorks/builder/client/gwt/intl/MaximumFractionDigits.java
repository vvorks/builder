package com.github.vvorks.builder.client.gwt.intl;

/**
 * 数値フォーマットオプション：小数部に使用する最大桁数
 */
public class MaximumFractionDigits implements NumberOption {

	private final double value;

	public static MaximumFractionDigits of(int value) {
		if (!(0 <= value && value <= 20)) {
			throw new IllegalArgumentException();
		}
		return new MaximumFractionDigits(value);
	}

	private MaximumFractionDigits(double value) {
		this.value = value;
	}

	@Override
	public String key() {
		return "maximumFractionDigits";
	}

	@Override
	public Double value() {
		return value;
	}

}
