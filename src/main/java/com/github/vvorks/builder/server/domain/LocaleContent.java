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
	 * ロケールID
	 */
	private String localeId;

	/**
	 * 所属プロジェクトのプロジェクトID
	 */
	private int ownerProjectId;

	/**
	 * タイトル
	 */
	private String title;

	/**
	 * 説明
	 */
	private String description;

	/**
	 * last updated at
	 */
	private java.util.Date _lastUpdatedAt;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 所属プロジェクトのタイトル
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
	 * @param localeId ロケールID
	 */
	public LocaleContent(
		String localeId
	) {
		this.localeId = localeId;
	}

	/**
	 * ロケールIDを取得する
	 *
	 * @return ロケールID
	 */
	public String getLocaleId() {
		return this.localeId;
	}

	/**
	 * ロケールIDを設定する
	 *
	 * @param localeId 設定するロケールID
	 */
	public void setLocaleId(String localeId) {
		this.localeId = localeId;
	}

	/**
	 * 所属プロジェクトのプロジェクトIDを取得する
	 *
	 * @return 所属プロジェクトのプロジェクトID
	 */
	public int getOwnerProjectId() {
		return this.ownerProjectId;
	}

	/**
	 * 所属プロジェクトのプロジェクトIDを設定する
	 *
	 * @param ownerProjectId 設定する所属プロジェクトのプロジェクトID
	 */
	public void setOwnerProjectId(int ownerProjectId) {
		this.ownerProjectId = ownerProjectId;
	}

	/**
	 * タイトルを取得する
	 *
	 * @return タイトル
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * タイトルを設定する
	 *
	 * @param title 設定するタイトル
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 説明を取得する
	 *
	 * @return 説明
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * 説明を設定する
	 *
	 * @param description 設定する説明
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * last updated atを取得する
	 *
	 * @return last updated at
	 */
	public java.util.Date get_lastUpdatedAt() {
		return this._lastUpdatedAt;
	}

	/**
	 * last updated atを設定する
	 *
	 * @param _lastUpdatedAt 設定するlast updated at
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
	 * 所属プロジェクトのタイトルを取得する
	 *
	 * @return 所属プロジェクトのタイトル
	 */
	public String getOwner_title() {
		return this.owner_title;
	}

	/**
	 * 所属プロジェクトのタイトルを設定する
	 *
	 * @param owner_title 設定する所属プロジェクトのタイトル
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
