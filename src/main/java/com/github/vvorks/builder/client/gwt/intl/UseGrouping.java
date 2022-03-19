package com.github.vvorks.builder.client.gwt.intl;

/**
 * 数値フォーマットオプション：グループ化する区切り記号を使用するかどうか
 */
public enum UseGrouping implements BooleanOption {

	TRUE(true),

	FALSE(false);

	private final boolean value;

	private UseGrouping(boolean value) {
		this.value = value;
	}

	@Override
	public String key() {
		return "useGrouping";
	}

	@Override
	public Boolean value() {
		return value;
	}

}
