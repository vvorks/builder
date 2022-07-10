/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * スタイルの見出し
 */
public class StyleSubject {

	/**
	 * スタイルID
	 */
	private int styleId;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のスタイルの見出しを作成する
	 */
	public StyleSubject() {
	}

	/**
	 * 指定のキー項目を持つスタイルの見出しを作成する
	 *
	 * @param styleId スタイルID
	 */
	public StyleSubject(
		int styleId
	) {
		this.styleId = styleId;
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
			styleId
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
			return equals((StyleSubject) obj);
		}
	}

	private boolean equals(StyleSubject other) {
		return
			this.styleId == other.styleId
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("StyleSubject [");
		sb.append("styleId=").append(styleId);
		sb.append("]");
		return sb.toString();
	}

}
