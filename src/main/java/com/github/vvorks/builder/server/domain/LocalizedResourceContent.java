/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * ローカライズドリソース
 */
public class LocalizedResourceContent {

	/**
	 * ownerのresourceId
	 */
	private int ownerResourceId;

	/**
	 * locale
	 */
	private String locale;

	/**
	 * value
	 */
	private String value;

	/**
	 * 最終更新時刻
	 */
	private java.util.Date _lastUpdatedAt;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * ownerのタイトル
	 */
	private String owner_title;

	/**
	 * 空のローカライズドリソースを作成する
	 */
	public LocalizedResourceContent() {
	}

	/**
	 * 指定のキー項目を持つローカライズドリソースを作成する
	 *
	 * @param ownerResourceId ownerのresourceId
	 * @param locale locale
	 */
	public LocalizedResourceContent(
		int ownerResourceId,
		String locale
	) {
		this.ownerResourceId = ownerResourceId;
		this.locale = locale;
	}

	/**
	 * ownerのresourceIdを取得する
	 *
	 * @return ownerのresourceId
	 */
	public int getOwnerResourceId() {
		return this.ownerResourceId;
	}

	/**
	 * ownerのresourceIdを設定する
	 *
	 * @param ownerResourceId 設定するownerのresourceId
	 */
	public void setOwnerResourceId(int ownerResourceId) {
		this.ownerResourceId = ownerResourceId;
	}

	/**
	 * localeを取得する
	 *
	 * @return locale
	 */
	public String getLocale() {
		return this.locale;
	}

	/**
	 * localeを設定する
	 *
	 * @param locale 設定するlocale
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * valueを取得する
	 *
	 * @return value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * valueを設定する
	 *
	 * @param value 設定するvalue
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 最終更新時刻を取得する
	 *
	 * @return 最終更新時刻
	 */
	public java.util.Date get_lastUpdatedAt() {
		return this._lastUpdatedAt;
	}

	/**
	 * 最終更新時刻を設定する
	 *
	 * @param _lastUpdatedAt 設定する最終更新時刻
	 */
	public void set_lastUpdatedAt(java.util.Date _lastUpdatedAt) {
		this._lastUpdatedAt = _lastUpdatedAt;
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

	/**
	 * ownerのタイトルを取得する
	 *
	 * @return ownerのタイトル
	 */
	public String getOwner_title() {
		return this.owner_title;
	}

	/**
	 * ownerのタイトルを設定する
	 *
	 * @param owner_title 設定するownerのタイトル
	 */
	public void setOwner_title(String owner_title) {
		this.owner_title = owner_title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			ownerResourceId,
			locale,
			value,
			_lastUpdatedAt
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
			return equals((LocalizedResourceContent) obj);
		}
	}

	private boolean equals(LocalizedResourceContent other) {
		return
			this.ownerResourceId == other.ownerResourceId && 
			Objects.equals(this.locale, other.locale) && 
			Objects.equals(this.value, other.value) && 
			Objects.equals(this._lastUpdatedAt, other._lastUpdatedAt)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("LocalizedResourceContent [");
		sb.append("ownerResourceId=").append(ownerResourceId).append(", ");
		sb.append("locale=").append(locale).append(", ");
		sb.append("value=").append(value).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
