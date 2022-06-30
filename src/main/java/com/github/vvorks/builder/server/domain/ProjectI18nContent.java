/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * プロジェクト(I18n)
 */
public class ProjectI18nContent {

	/**
	 * ownerのプロジェクトID
	 */
	private int ownerProjectId;

	/**
	 * targetのlocaleId
	 */
	private String targetLocaleId;

	/**
	 * label
	 */
	private String label;

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
	 * targetのタイトル
	 */
	private String target_title;

	/**
	 * 空のプロジェクト(I18n)を作成する
	 */
	public ProjectI18nContent() {
	}

	/**
	 * 指定のキー項目を持つプロジェクト(I18n)を作成する
	 *
	 * @param ownerProjectId ownerのプロジェクトID
	 * @param targetLocaleId targetのlocaleId
	 */
	public ProjectI18nContent(
		int ownerProjectId,
		String targetLocaleId
	) {
		this.ownerProjectId = ownerProjectId;
		this.targetLocaleId = targetLocaleId;
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
	 * targetのlocaleIdを取得する
	 *
	 * @return targetのlocaleId
	 */
	public String getTargetLocaleId() {
		return this.targetLocaleId;
	}

	/**
	 * targetのlocaleIdを設定する
	 *
	 * @param targetLocaleId 設定するtargetのlocaleId
	 */
	public void setTargetLocaleId(String targetLocaleId) {
		this.targetLocaleId = targetLocaleId;
	}

	/**
	 * labelを取得する
	 *
	 * @return label
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * labelを設定する
	 *
	 * @param label 設定するlabel
	 */
	public void setLabel(String label) {
		this.label = label;
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

	/**
	 * targetのタイトルを取得する
	 *
	 * @return targetのタイトル
	 */
	public String getTarget_title() {
		return this.target_title;
	}

	/**
	 * targetのタイトルを設定する
	 *
	 * @param target_title 設定するtargetのタイトル
	 */
	public void setTarget_title(String target_title) {
		this.target_title = target_title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			ownerProjectId,
			targetLocaleId,
			label,
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
			return equals((ProjectI18nContent) obj);
		}
	}

	private boolean equals(ProjectI18nContent other) {
		return
			this.ownerProjectId == other.ownerProjectId && 
			Objects.equals(this.targetLocaleId, other.targetLocaleId) && 
			Objects.equals(this.label, other.label) && 
			Objects.equals(this._lastUpdatedAt, other._lastUpdatedAt)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ProjectI18nContent [");
		sb.append("ownerProjectId=").append(ownerProjectId).append(", ");
		sb.append("targetLocaleId=").append(targetLocaleId).append(", ");
		sb.append("label=").append(label).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
