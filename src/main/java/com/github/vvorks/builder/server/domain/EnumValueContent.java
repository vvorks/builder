/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * 列挙値
 */
public class EnumValueContent {

	/**
	 * 所属列挙の列挙ID
	 */
	private int ownerEnumId;

	/**
	 * 列挙名
	 */
	private String valueId;

	/**
	 * 列挙コード
	 */
	private int code;

	/**
	 * タイトル
	 *
	 * 仕様書生成用見出し
	 */
	private String title;

	/**
	 * 説明
	 *
	 * 仕様書生成用説明文
	 */
	private String description;

	/**
	 * メモ
	 */
	private String note;

	/**
	 * 最終更新時刻
	 */
	private java.util.Date _lastUpdatedAt;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 所属列挙のタイトル
	 */
	private String owner_title;

	/**
	 * 空の列挙値を作成する
	 */
	public EnumValueContent() {
	}

	/**
	 * 指定のキー項目を持つ列挙値を作成する
	 *
	 * @param ownerEnumId 所属列挙の列挙ID
	 * @param valueId 列挙名
	 */
	public EnumValueContent(
		int ownerEnumId,
		String valueId
	) {
		this.ownerEnumId = ownerEnumId;
		this.valueId = valueId;
	}

	/**
	 * 所属列挙の列挙IDを取得する
	 *
	 * @return 所属列挙の列挙ID
	 */
	public int getOwnerEnumId() {
		return this.ownerEnumId;
	}

	/**
	 * 所属列挙の列挙IDを設定する
	 *
	 * @param ownerEnumId 設定する所属列挙の列挙ID
	 */
	public void setOwnerEnumId(int ownerEnumId) {
		this.ownerEnumId = ownerEnumId;
	}

	/**
	 * 列挙名を取得する
	 *
	 * @return 列挙名
	 */
	public String getValueId() {
		return this.valueId;
	}

	/**
	 * 列挙名を設定する
	 *
	 * @param valueId 設定する列挙名
	 */
	public void setValueId(String valueId) {
		this.valueId = valueId;
	}

	/**
	 * 列挙コードを取得する
	 *
	 * @return 列挙コード
	 */
	public int getCode() {
		return this.code;
	}

	/**
	 * 列挙コードを設定する
	 *
	 * @param code 設定する列挙コード
	 */
	public void setCode(int code) {
		this.code = code;
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
	 * メモを取得する
	 *
	 * @return メモ
	 */
	public String getNote() {
		return this.note;
	}

	/**
	 * メモを設定する
	 *
	 * @param note 設定するメモ
	 */
	public void setNote(String note) {
		this.note = note;
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
	 * 所属列挙のタイトルを取得する
	 *
	 * @return 所属列挙のタイトル
	 */
	public String getOwner_title() {
		return this.owner_title;
	}

	/**
	 * 所属列挙のタイトルを設定する
	 *
	 * @param owner_title 設定する所属列挙のタイトル
	 */
	public void setOwner_title(String owner_title) {
		this.owner_title = owner_title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			ownerEnumId,
			valueId,
			code,
			title,
			description,
			note,
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
			return equals((EnumValueContent) obj);
		}
	}

	private boolean equals(EnumValueContent other) {
		return
			this.ownerEnumId == other.ownerEnumId && 
			Objects.equals(this.valueId, other.valueId) && 
			this.code == other.code && 
			Objects.equals(this.title, other.title) && 
			Objects.equals(this.description, other.description) && 
			Objects.equals(this.note, other.note) && 
			Objects.equals(this._lastUpdatedAt, other._lastUpdatedAt)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("EnumValueContent [");
		sb.append("ownerEnumId=").append(ownerEnumId).append(", ");
		sb.append("valueId=").append(valueId).append(", ");
		sb.append("code=").append(code).append(", ");
		sb.append("title=").append(title).append(", ");
		sb.append("description=").append(description).append(", ");
		sb.append("note=").append(note).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
