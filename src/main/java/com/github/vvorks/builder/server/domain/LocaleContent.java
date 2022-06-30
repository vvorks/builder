/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * ロケール
 *
 * ロケールマスタ
 *
 * 必要な分だけ登録
 */
public class LocaleContent {

	/**
	 * localeId
	 */
	private String localeId;

	/**
	 * ownerのプロジェクトID
	 */
	private int ownerProjectId;

	/**
	 * title
	 */
	private String title;

	/**
	 * description
	 */
	private String description;

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
	 * 空のロケールを作成する
	 */
	public LocaleContent() {
	}

	/**
	 * 指定のキー項目を持つロケールを作成する
	 *
	 * @param localeId localeId
	 */
	public LocaleContent(
		String localeId
	) {
		this.localeId = localeId;
	}

	/**
	 * localeIdを取得する
	 *
	 * @return localeId
	 */
	public String getLocaleId() {
		return this.localeId;
	}

	/**
	 * localeIdを設定する
	 *
	 * @param localeId 設定するlocaleId
	 */
	public void setLocaleId(String localeId) {
		this.localeId = localeId;
	}

	/**
	 * ownerのプロジェクトIDを取得する
	 *
	 * @return ownerのプロジェクトID
	 */
	public int getOwnerProjectId() {
		return this.ownerProjectId;
	}

	/**
	 * ownerのプロジェクトIDを設定する
	 *
	 * @param ownerProjectId 設定するownerのプロジェクトID
	 */
	public void setOwnerProjectId(int ownerProjectId) {
		this.ownerProjectId = ownerProjectId;
	}

	/**
	 * titleを取得する
	 *
	 * @return title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * titleを設定する
	 *
	 * @param title 設定するtitle
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * descriptionを取得する
	 *
	 * @return description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * descriptionを設定する
	 *
	 * @param description 設定するdescription
	 */
	public void setDescription(String description) {
		this.description = description;
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
			localeId,
			ownerProjectId,
			title,
			description,
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
			return equals((LocaleContent) obj);
		}
	}

	private boolean equals(LocaleContent other) {
		return
			Objects.equals(this.localeId, other.localeId) && 
			this.ownerProjectId == other.ownerProjectId && 
			Objects.equals(this.title, other.title) && 
			Objects.equals(this.description, other.description) && 
			Objects.equals(this._lastUpdatedAt, other._lastUpdatedAt)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("LocaleContent [");
		sb.append("localeId=").append(localeId).append(", ");
		sb.append("ownerProjectId=").append(ownerProjectId).append(", ");
		sb.append("title=").append(title).append(", ");
		sb.append("description=").append(description).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
