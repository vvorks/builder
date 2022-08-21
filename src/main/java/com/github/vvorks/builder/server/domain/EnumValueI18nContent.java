/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * 列挙値(I18n)
 */
public class EnumValueI18nContent {

	/**
	 * 所属列挙値の所属列挙の列挙ID
	 */
	private int ownerOwnerEnumId;

	/**
	 * 所属列挙値の列挙名
	 */
	private String ownerValueId;

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
	 * last updated at
	 */
	private java.util.Date _lastUpdatedAt;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 所属列挙値のタイトル
	 */
	private String owner_title;

	/**
	 * 対象ロケールのタイトル
	 */
	private String target_title;

	/**
	 * 空の列挙値(I18n)を作成する
	 */
	public EnumValueI18nContent() {
	}

	/**
	 * 指定のキー項目を持つ列挙値(I18n)を作成する
	 *
	 * @param ownerOwnerEnumId 所属列挙値の所属列挙の列挙ID
	 * @param ownerValueId 所属列挙値の列挙名
	 * @param targetLocaleId 対象ロケールのロケールID
	 */
	public EnumValueI18nContent(
		int ownerOwnerEnumId,
		String ownerValueId,
		String targetLocaleId
	) {
		this.ownerOwnerEnumId = ownerOwnerEnumId;
		this.ownerValueId = ownerValueId;
		this.targetLocaleId = targetLocaleId;
	}

	/**
	 * 所属列挙値の所属列挙の列挙IDを取得する
	 *
	 * @return 所属列挙値の所属列挙の列挙ID
	 */
	public int getOwnerOwnerEnumId() {
		return this.ownerOwnerEnumId;
	}

	/**
	 * 所属列挙値の所属列挙の列挙IDを設定する
	 *
	 * @param ownerOwnerEnumId 設定する所属列挙値の所属列挙の列挙ID
	 */
	public void setOwnerOwnerEnumId(int ownerOwnerEnumId) {
		this.ownerOwnerEnumId = ownerOwnerEnumId;
	}

	/**
	 * 所属列挙値の列挙名を取得する
	 *
	 * @return 所属列挙値の列挙名
	 */
	public String getOwnerValueId() {
		return this.ownerValueId;
	}

	/**
	 * 所属列挙値の列挙名を設定する
	 *
	 * @param ownerValueId 設定する所属列挙値の列挙名
	 */
	public void setOwnerValueId(String ownerValueId) {
		this.ownerValueId = ownerValueId;
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
	 * 所属列挙値のタイトルを取得する
	 *
	 * @return 所属列挙値のタイトル
	 */
	public String getOwner_title() {
		return this.owner_title;
	}

	/**
	 * 所属列挙値のタイトルを設定する
	 *
	 * @param owner_title 設定する所属列挙値のタイトル
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
			ownerOwnerEnumId,
			ownerValueId,
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
			return equals((EnumValueI18nContent) obj);
		}
	}

	private boolean equals(EnumValueI18nContent other) {
		return
			this.ownerOwnerEnumId == other.ownerOwnerEnumId && 
			Objects.equals(this.ownerValueId, other.ownerValueId) && 
			Objects.equals(this.targetLocaleId, other.targetLocaleId) && 
			Objects.equals(this.title, other.title) && 
			Objects.equals(this.description, other.description) && 
			Objects.equals(this._lastUpdatedAt, other._lastUpdatedAt)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("EnumValueI18nContent [");
		sb.append("ownerOwnerEnumId=").append(ownerOwnerEnumId).append(", ");
		sb.append("ownerValueId=").append(ownerValueId).append(", ");
		sb.append("targetLocaleId=").append(targetLocaleId).append(", ");
		sb.append("title=").append(title).append(", ");
		sb.append("description=").append(description).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
