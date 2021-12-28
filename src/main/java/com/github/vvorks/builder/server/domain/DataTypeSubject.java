/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * データ型の見出し
 */
public class DataTypeSubject {

	private final DataType value;

	/**
	 * 指定の列挙値と見出しを持つデータ型の見出しを作成する
	 *
	 * @param value 列挙値
	 */
	public DataTypeSubject(DataType value) {
		this.value = value;
	}

	public DataType getValue() {
		return value;
	}

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
			return equals((DataTypeSubject) obj);
		}
	}

	private boolean equals(DataTypeSubject other) {
		return this.value == other.value;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("DataTypeSubject [");
		sb.append("value=").append(value);
		sb.append("]");
		return sb.toString();
	}

}
