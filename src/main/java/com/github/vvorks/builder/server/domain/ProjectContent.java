/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * プロジェクト
 */
public class ProjectContent {

	/**
	 * プロジェクトID
	 *
	 * （代理キー）
	 */
	private int projectId;

	/**
	 * プロジェクト名
	 *
	 * ベースパッケージ名を兼ねる
	 */
	private String projectName;

	/**
	 * gradleサブプロジェクト名
	 */
	private String gradleName;

	/**
	 * タイトル
	 */
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
	 * 著作権
	 */
	private String copyrights;

	/**
	 * last updated at
	 */
	private java.util.Date _lastUpdatedAt;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のプロジェクトを作成する
	 */
	public ProjectContent() {
	}

	/**
	 * 指定のキー項目を持つプロジェクトを作成する
	 *
	 * @param projectId プロジェクトID
	 */
	public ProjectContent(
		int projectId
	) {
		this.projectId = projectId;
	}

	/**
	 * プロジェクトIDを取得する
	 *
	 * @return プロジェクトID
	 */
	public int getProjectId() {
		return this.projectId;
	}

	/**
	 * プロジェクトIDを設定する
	 *
	 * @param projectId 設定するプロジェクトID
	 */
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	/**
	 * プロジェクト名を取得する
	 *
	 * @return プロジェクト名
	 */
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * プロジェクト名を設定する
	 *
	 * @param projectName 設定するプロジェクト名
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * gradleサブプロジェクト名を取得する
	 *
	 * @return gradleサブプロジェクト名
	 */
	public String getGradleName() {
		return this.gradleName;
	}

	/**
	 * gradleサブプロジェクト名を設定する
	 *
	 * @param gradleName 設定するgradleサブプロジェクト名
	 */
	public void setGradleName(String gradleName) {
		this.gradleName = gradleName;
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
	 * 著作権を取得する
	 *
	 * @return 著作権
	 */
	public String getCopyrights() {
		return this.copyrights;
	}

	/**
	 * 著作権を設定する
	 *
	 * @param copyrights 設定する著作権
	 */
	public void setCopyrights(String copyrights) {
		this.copyrights = copyrights;
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

	@Override
	public int hashCode() {
		return Objects.hash(
			projectId,
			projectName,
			gradleName,
			title,
			description,
			note,
			copyrights,
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
			return equals((ProjectContent) obj);
		}
	}

	private boolean equals(ProjectContent other) {
		return
			this.projectId == other.projectId && 
			Objects.equals(this.projectName, other.projectName) && 
			Objects.equals(this.gradleName, other.gradleName) && 
			Objects.equals(this.title, other.title) && 
			Objects.equals(this.description, other.description) && 
			Objects.equals(this.note, other.note) && 
			Objects.equals(this.copyrights, other.copyrights) && 
			Objects.equals(this._lastUpdatedAt, other._lastUpdatedAt)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ProjectContent [");
		sb.append("projectId=").append(projectId).append(", ");
		sb.append("projectName=").append(projectName).append(", ");
		sb.append("gradleName=").append(gradleName).append(", ");
		sb.append("title=").append(title).append(", ");
		sb.append("description=").append(description).append(", ");
		sb.append("note=").append(note).append(", ");
		sb.append("copyrights=").append(copyrights).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
