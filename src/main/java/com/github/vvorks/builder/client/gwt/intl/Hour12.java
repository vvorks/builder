package com.github.vvorks.builder.client.gwt.intl;

/**
 * 日付時刻フォーマットオプション：(24 時制に対して) 12 時制を使用するかどうか
 */
public enum Hour12 implements BooleanOption {

	TRUE(true),

	FALSE(false);

	private final boolean value;

	private Hour12(boolean value) {
		this.value = value;
	}

	@Override
	public String key() {
		return "hour12";
	}

	@Override
	public Boolean value() {
		return value;
	}

}
