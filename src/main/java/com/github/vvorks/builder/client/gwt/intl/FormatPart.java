package com.github.vvorks.builder.client.gwt.intl;

/**
 * {@link DateTimeFormat#formatToParts(java.util.Date)}の戻り値で使用されるデータ型
 *
 * @see DateTimeFormat#formatToParts(java.util.Date)
 */
public class FormatPart {

	private final String type;

	private final String value;

	public FormatPart(String type, String value) {
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public boolean isLiteral() {
		return "literal".equals(type);
	}

}
