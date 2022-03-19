package com.github.vvorks.builder.client.gwt.intl;

/**
 * 数値フォーマットオプション：小数部に使用する最小桁数
 */
public class MinimumFractionDigits implements NumberOption {

	private final double value;

	public static MinimumFractionDigits of(int value) {
		if (!(0 <= value && value <= 20)) {
			throw new IllegalArgumentException();
		}
		return new MinimumFractionDigits(value);
	}

	private MinimumFractionDigits(double value) {
		this.value = value;
	}

	@Override
	public String key() {
		return "minimumFractionDigits";
	}

	@Override
	public Double value() {
		return value;
	}

}
