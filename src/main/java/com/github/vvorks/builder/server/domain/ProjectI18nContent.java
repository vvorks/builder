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
	 * 所属プロジェクトのプロジェクトID
	 */
	private int ownerProjectId;

	/**
	 * 対象ロケールのロケールID
	 */
	private String targetLocaleId;

	/**
	 * I18nラベル
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
	 * 所属プロジェクトのタイトル
	 */
	private String owner_title;

	/**
	 * 対象ロケールのタイトル
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
	 * @param ownerProjectId 所属プロジェクトのプロジェクトID
	 * @param targetLocaleId 対象ロケールのロケールID
	 */
	public ProjectI18nContent(
		int ownerProjectId,
		String targetLocaleId
	) {
		this.ownerProjectId = ownerProjectId;
		this.targetLocaleId = targetLocaleId;
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
	 * I18nラベルを取得する
	 *
	 * @return I18nラベル
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * I18nラベルを設定する
	 *
	 * @param label 設定するI18nラベル
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
