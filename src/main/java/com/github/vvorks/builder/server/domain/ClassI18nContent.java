/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * クラス(I18n)
 */
public class ClassI18nContent {

	/**
	 * 所属クラスのクラスID
	 */
	private int ownerClassId;

	/**
	 * 対象ロケールのロケールID
	 */
	private String targetLocaleId;

	/**
	 * タイトル
	 */
	private String title;

	/**
	 * 説明
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
	 * 所属クラスのタイトル
	 */
	private String owner_title;

	/**
	 * 対象ロケールのタイトル
	 */
	private String target_title;

	/**
	 * 空のクラス(I18n)を作成する
	 */
	public ClassI18nContent() {
	}

	/**
	 * 指定のキー項目を持つクラス(I18n)を作成する
	 *
	 * @param ownerClassId 所属クラスのクラスID
	 * @param targetLocaleId 対象ロケールのロケールID
	 */
	public ClassI18nContent(
		int ownerClassId,
		String targetLocaleId
	) {
		this.ownerClassId = ownerClassId;
		this.targetLocaleId = targetLocaleId;
	}

	/**
	 * 所属クラスのクラスIDを取得する
	 *
	 * @return 所属クラスのクラスID
	 */
	public int getOwnerClassId() {
		return this.ownerClassId;
	}

	/**
	 * 所属クラスのクラスIDを設定する
	 *
	 * @param ownerClassId 設定する所属クラスのクラスID
	 */
	public void setOwnerClassId(int ownerClassId) {
		this.ownerClassId = ownerClassId;
	}

	/**
	 * 対象ロケールのロケールIDを取得する
	 *
	 * @return 対象ロケールのロケールID
	 */
	public String getTargetLocaleId() {
		return this.targetLocaleId;
	}

	/**
	 * 対象ロケールのロケールIDを設定する
	 *
	 * @param targetLocaleId 設定する対象ロケールのロケールID
	 */
	public void setTargetLocaleId(String targetLocaleId) {
		this.targetLocaleId = targetLocaleId;
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
	 * 所属クラスのタイトルを取得する
	 *
	 * @return 所属クラスのタイトル
	 */
	public String getOwner_title() {
		return this.owner_title;
	}

	/**
	 * 所属クラスのタイトルを設定する
	 *
	 * @param owner_title 設定する所属クラスのタイトル
	 */
	public void setOwner_title(String owner_title) {
		this.owner_title = owner_title;
	}

	/**
	 * 対象ロケールのタイトルを取得する
	 *
	 * @return 対象ロケールのタイトル
	 */
	public String getTarget_title() {
		return this.target_title;
	}

	/**
	 * 対象ロケールのタイトルを設定する
	 *
	 * @param target_title 設定する対象ロケールのタイトル
	 */
	public void setTarget_title(String target_title) {
		this.target_title = target_title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			ownerClassId,
			targetLocaleId,
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
			return equals((ClassI18nContent) obj);
		}
	}

	private boolean equals(ClassI18nContent other) {
		return
			this.ownerClassId == other.ownerClassId && 
			Objects.equals(this.targetLocaleId, other.targetLocaleId) && 
			Objects.equals(this.title, other.title) && 
			Objects.equals(this.description, other.description) && 
			Objects.equals(this._lastUpdatedAt, other._lastUpdatedAt)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ClassI18nContent [");
		sb.append("ownerClassId=").append(ownerClassId).append(", ");
		sb.append("targetLocaleId=").append(targetLocaleId).append(", ");
		sb.append("title=").append(title).append(", ");
		sb.append("description=").append(description).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
