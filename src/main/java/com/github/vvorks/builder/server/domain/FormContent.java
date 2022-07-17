/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * フォーム
 */
public class FormContent {

	/**
	 * formId
	 */
	private int formId;

	/**
	 * ownerのプロジェクトID
	 */
	private int ownerProjectId;

	/**
	 * name
	 */
	private String name;

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
	 * ownerのタイトル
	 */
	private String owner_title;

	/**
	 * 空のフォームを作成する
	 */
	public FormContent() {
	}

	/**
	 * 指定のキー項目を持つフォームを作成する
	 *
	 * @param formId formId
	 */
	public FormContent(
		int formId
	) {
		this.formId = formId;
	}

	/**
	 * formIdを取得する
	 *
	 * @return formId
	 */
	public int getFormId() {
		return this.formId;
	}

	/**
	 * formIdを設定する
	 *
	 * @param formId 設定するformId
	 */
	public void setFormId(int formId) {
		this.formId = formId;
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
	 * nameを取得する
	 *
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * nameを設定する
	 *
	 * @param name 設定するname
	 */
	public void setName(String name) {
		this.name = name;
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
			formId,
			ownerProjectId,
			name,
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
			return equals((FormContent) obj);
		}
	}

	private boolean equals(FormContent other) {
		return
			this.formId == other.formId && 
			this.ownerProjectId == other.ownerProjectId && 
			Objects.equals(this.name, other.name) && 
			Objects.equals(this.title, other.title) && 
			Objects.equals(this.description, other.description) && 
			Objects.equals(this._lastUpdatedAt, other._lastUpdatedAt)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FormContent [");
		sb.append("formId=").append(formId).append(", ");
		sb.append("ownerProjectId=").append(ownerProjectId).append(", ");
		sb.append("name=").append(name).append(", ");
		sb.append("title=").append(title).append(", ");
		sb.append("description=").append(description).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
