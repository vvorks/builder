/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * ローカライズドリソースの見出し
 */
public class LocalizedResourceSubject {

	/**
	 * 所属リソースのリソースID
	 */
	private int ownerResourceId;

	/**
	 * ロケール
	 */
	private String locale;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のローカライズドリソースの見出しを作成する
	 */
	public LocalizedResourceSubject() {
	}

	/**
	 * 指定のキー項目を持つローカライズドリソースの見出しを作成する
	 *
	 * @param ownerResourceId 所属リソースのリソースID
	 * @param locale ロケール
	 */
	public LocalizedResourceSubject(
		int ownerResourceId,
		String locale
	) {
		this.ownerResourceId = ownerResourceId;
		this.locale = locale;
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
			ownerResourceId,
			locale
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
			return equals((LocalizedResourceSubject) obj);
		}
	}

	private boolean equals(LocalizedResourceSubject other) {
		return
			this.ownerResourceId == other.ownerResourceId && 
			Objects.equals(this.locale, other.locale)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("LocalizedResourceSubject [");
		sb.append("ownerResourceId=").append(ownerResourceId).append(", ");
		sb.append("locale=").append(locale);
		sb.append("]");
		return sb.toString();
	}

}
