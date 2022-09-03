/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * レイアウト種別の見出し
 */
public class LayoutTypeSubject extends Subject {

	private final LayoutType value;

	/**
	 * 指定の列挙値と見出しを持つレイアウト種別の見出しを作成する
	 *
	 * @param value 列挙値
	 */
	public LayoutTypeSubject(LayoutType value) {
		this.value = value;
	}

	public LayoutType getValue() {
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
			return equals((LayoutTypeSubject) obj);
		}
	}

	private boolean equals(LayoutTypeSubject other) {
		return this.value == other.value;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("LayoutTypeSubject [");
		sb.append("value=").append(value);
		sb.append("]");
		return sb.toString();
	}

}
