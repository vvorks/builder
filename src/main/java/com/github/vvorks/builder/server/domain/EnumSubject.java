/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * 列挙の見出し
 */
public class EnumSubject {

	/**
	 * 列挙ID
	 *
	 * （代理キー）
	 */
	private int enumId;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空の列挙の見出しを作成する
	 */
	public EnumSubject() {
	}

	/**
	 * 指定のキー項目を持つ列挙の見出しを作成する
	 *
	 * @param enumId 列挙ID
	 */
	public EnumSubject(
		int enumId
	) {
		this.enumId = enumId;
	}

	/**
	 * タイトルを取得する
	 *
	 * @return タイトル
	 */
	public String get_title() {
		return this._title;
	}

	/**
	 * タイトルを設定する
	 *
	 * @param _title 設定するタイトル
	 */
	public void set_title(String _title) {
		this._title = _title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			enumId
			);
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
			return equals((EnumSubject) obj);
		}
	}

	private boolean equals(EnumSubject other) {
		return
			this.enumId == other.enumId
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("EnumSubject [");
		sb.append("enumId=").append(enumId);
		sb.append("]");
		return sb.toString();
	}

}
