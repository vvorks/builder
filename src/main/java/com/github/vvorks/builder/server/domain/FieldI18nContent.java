/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * フィールド(I18n)
 */
public class FieldI18nContent {

	/**
	 * 所属フィールドのフィールドID
	 */
	private int ownerFieldId;

	/**
	 * 対象ロケールのロケールID
	 */
	private String targetLocaleId;

	/**
	 * 書式
	 */
	private String format;

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
	 * 所属フィールドのタイトル
	 */
	private String owner_title;

	/**
	 * 対象ロケールのタイトル
	 */
	private String target_title;

	/**
	 * 空のフィールド(I18n)を作成する
	 */
	public FieldI18nContent() {
	}

	/**
	 * 指定のキー項目を持つフィールド(I18n)を作成する
	 *
	 * @param ownerFieldId 所属フィールドのフィールドID
	 * @param targetLocaleId 対象ロケールのロケールID
	 */
	public FieldI18nContent(
		int ownerFieldId,
		String targetLocaleId
	) {
		this.ownerFieldId = ownerFieldId;
		this.targetLocaleId = targetLocaleId;
	}

	/**
	 * 所属フィールドのフィールドIDを取得する
	 *
	 * @return 所属フィールドのフィールドID
	 */
	public int getOwnerFieldId() {
		return this.ownerFieldId;
	}

	/**
	 * 所属フィールドのフィールドIDを設定する
	 *
	 * @param ownerFieldId 設定する所属フィールドのフィールドID
	 */
	public void setOwnerFieldId(int ownerFieldId) {
		this.ownerFieldId = ownerFieldId;
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
	 * 書式を取得する
	 *
	 * @return 書式
	 */
	public String getFormat() {
		return this.format;
	}

	/**
	 * 書式を設定する
	 *
	 * @param format 設定する書式
	 */
	public void setFormat(String format) {
		this.format = format;
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
	 * 所属フィールドのタイトルを取得する
	 *
	 * @return 所属フィールドのタイトル
	 */
	public String getOwner_title() {
		return this.owner_title;
	}

	/**
	 * 所属フィールドのタイトルを設定する
	 *
	 * @param owner_title 設定する所属フィールドのタイトル
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
			ownerFieldId,
			targetLocaleId,
			format,
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
			return equals((FieldI18nContent) obj);
		}
	}

	private boolean equals(FieldI18nContent other) {
		return
			this.ownerFieldId == other.ownerFieldId && 
			Objects.equals(this.targetLocaleId, other.targetLocaleId) && 
			Objects.equals(this.format, other.format) && 
			Objects.equals(this.title, other.title) && 
			Objects.equals(this.description, other.description) && 
			Objects.equals(this._lastUpdatedAt, other._lastUpdatedAt)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FieldI18nContent [");
		sb.append("ownerFieldId=").append(ownerFieldId).append(", ");
		sb.append("targetLocaleId=").append(targetLocaleId).append(", ");
		sb.append("format=").append(format).append(", ");
		sb.append("title=").append(title).append(", ");
		sb.append("description=").append(description).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
