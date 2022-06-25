package com.github.vvorks.builder.client.gwt.intl;

/**
 * 日付時刻フォーマットオプション：秒の小数点以下を表すために使用される数字の桁数
 */
public class FractionalSecondDigits implements NumberOption {

	private final double value;

	public static FractionalSecondDigits of(int value) {
		if (!(1 <= value && value <= 3)) {
			throw new IllegalArgumentException();
		}
		return new FractionalSecondDigits(value);
	}

	private FractionalSecondDigits(double value) {
		this.value = value;
	}

	@Override
	public String key() {
		return "fractionalSecondDigits";
	}

	@Override
	public Double value() {
		return value;
	}

}
