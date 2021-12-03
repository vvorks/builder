/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * 列挙
 */
public class EnumContent {

	/**
	 * 列挙ID
	 *
	 * （代理キー）
	 */
	private int enumId;

	/**
	 * 所属プロジェクトのプロジェクトID
	 */
	private int ownerProjectId;

	/**
	 * 列挙名
	 */
	@NotNull
	private String enumName;

	/**
	 * 文字列エンコード
	 *
	 * 文字列エンコードする場合、真
	 */
	private boolean encodeString;

	/**
	 * タイトル
	 */
	@NotNull
	private String title;

	/**
	 * 説明
	 */
	private String description;

	/**
	 * メモ
	 */
	private String note;

	/**
	 * _lastUpdatedAt
	 */
	@NotNull
	private java.util.Date _lastUpdatedAt;

	/**
	 * 空の列挙を作成する
	 */
	public EnumContent() {
	}

	/**
	 * 指定のキー項目を持つ列挙を作成する
	 *
	 * @param enumId 列挙ID
	 */
	public EnumContent(
		int enumId
	) {
		this.enumId = enumId;
	}

	/**
	 * 列挙IDを取得する
	 *
	 * @return 列挙ID
	 */
	public int getEnumId() {
		return this.enumId;
	}

	/**
	 * 列挙IDを設定する
	 *
	 * @param enumId 設定する列挙ID
	 */
	public void setEnumId(int enumId) {
		this.enumId = enumId;
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
	 * 列挙名を取得する
	 *
	 * @return 列挙名
	 */
	public String getEnumName() {
		return this.enumName;
	}

	/**
	 * 列挙名を設定する
	 *
	 * @param enumName 設定する列挙名
	 */
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}

	/**
	 * 文字列エンコードを取得する
	 *
	 * @return 文字列エンコード
	 */
	public boolean isEncodeString() {
		return this.encodeString;
	}

	/**
	 * 文字列エンコードを設定する
	 *
	 * @param encodeString 設定する文字列エンコード
	 */
	public void setEncodeString(boolean encodeString) {
		this.encodeString = encodeString;
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
	 * _lastUpdatedAtを取得する
	 *
	 * @return _lastUpdatedAt
	 */
	public java.util.Date get_lastUpdatedAt() {
		return this._lastUpdatedAt;
	}

	/**
	 * _lastUpdatedAtを設定する
	 *
	 * @param _lastUpdatedAt 設定する_lastUpdatedAt
	 */
	public void set_lastUpdatedAt(java.util.Date _lastUpdatedAt) {
		this._lastUpdatedAt = _lastUpdatedAt;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			enumId,
			ownerProjectId,
			enumName,
			encodeString,
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
			return equals((EnumContent) obj);
		}
	}

	private boolean equals(EnumContent other) {
		return
			this.enumId == other.enumId && 
			this.ownerProjectId == other.ownerProjectId && 
			Objects.equals(this.enumName, other.enumName) && 
			this.encodeString == other.encodeString && 
			Objects.equals(this.title, other.title) && 
			Objects.equals(this.description, other.description) && 
			Objects.equals(this.note, other.note) && 
			Objects.equals(this._lastUpdatedAt, other._lastUpdatedAt)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("EnumContent [");
		sb.append("enumId=").append(enumId).append(", ");
		sb.append("ownerProjectId=").append(ownerProjectId).append(", ");
		sb.append("enumName=").append(enumName).append(", ");
		sb.append("encodeString=").append(encodeString).append(", ");
		sb.append("title=").append(title).append(", ");
		sb.append("description=").append(description).append(", ");
		sb.append("note=").append(note).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
