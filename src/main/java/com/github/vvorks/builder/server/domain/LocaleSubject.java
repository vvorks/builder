/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * ロケールの見出し
 *
 * ロケールマスタ
 *
 * 必要な分だけ登録
 */
public class LocaleSubject {

	/**
	 * localeId
	 */
	private String localeId;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のロケールの見出しを作成する
	 */
	public LocaleSubject() {
	}

	/**
	 * 指定のキー項目を持つロケールの見出しを作成する
	 *
	 * @param localeId localeId
	 */
	public LocaleSubject(
		String localeId
	) {
		this.localeId = localeId;
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
			localeId
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
			return equals((LocaleSubject) obj);
		}
	}

	private boolean equals(LocaleSubject other) {
		return
			Objects.equals(this.localeId, other.localeId)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("LocaleSubject [");
		sb.append("localeId=").append(localeId);
		sb.append("]");
		return sb.toString();
	}

}
