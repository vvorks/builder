/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * スタイル条件の見出し
 */
public class StyleConditionSubject extends Subject {

	private final StyleCondition value;

	/**
	 * 指定の列挙値と見出しを持つスタイル条件の見出しを作成する
	 *
	 * @param value 列挙値
	 */
	public StyleConditionSubject(StyleCondition value) {
		this.value = value;
	}

	public StyleCondition getValue() {
		return value;
	}

	@Override
	public String get_title() {
		return value.get_title();
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		} else {
			return equals((StyleConditionSubject) obj);
		}
	}

	private boolean equals(StyleConditionSubject other) {
		return this.value == other.value;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("StyleConditionSubject [");
		sb.append("value=").append(value);
		sb.append("]");
		return sb.toString();
	}

}
