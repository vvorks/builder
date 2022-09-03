/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Map;
import java.util.Objects;

/**
 * プロジェクト(I18n)の見出し
 */
public class ProjectI18nSubject extends Subject {

	public static Subject createFrom(Map<String, Object> args) {
		ProjectI18nSubject subject = new ProjectI18nSubject(
				asInt(args.get("ownerProjectId")),
				asString(args.get("targetLocaleId"))
				);
		subject.set_title(asString(args.get("_title")));
		return subject;
	}

	/**
	 * 所属プロジェクトのプロジェクトID
	 */
	private int ownerProjectId;

	/**
	 * 対象ロケールのロケールID
	 */
	private String targetLocaleId;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のプロジェクト(I18n)の見出しを作成する
	 */
	public ProjectI18nSubject() {
	}

	/**
	 * 指定のキー項目を持つプロジェクト(I18n)の見出しを作成する
	 *
	 * @param ownerProjectId 所属プロジェクトのプロジェクトID
	 * @param targetLocaleId 対象ロケールのロケールID
	 */
	public ProjectI18nSubject(
		int ownerProjectId,
		String targetLocaleId
	) {
		this.ownerProjectId = ownerProjectId;
		this.targetLocaleId = targetLocaleId;
	}

	/**
	 * 所属プロジェクトのプロジェクトIDを取得する
	 */
	public int getOwnerProjectId() {
		return ownerProjectId;
	}

	/**
	 * 対象ロケールのロケールIDを取得する
	 */
	public String getTargetLocaleId() {
		return targetLocaleId;
	}

	/**
	 * タイトルを取得する
	 *
	 * @return タイトル
	 */
	@Override
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
			ownerProjectId,
			targetLocaleId
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
			return equals((ProjectI18nSubject) obj);
		}
	}

	private boolean equals(ProjectI18nSubject other) {
		return
			this.ownerProjectId == other.ownerProjectId && 
			Objects.equals(this.targetLocaleId, other.targetLocaleId)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ProjectI18nSubject [");
		sb.append("ownerProjectId=").append(ownerProjectId).append(", ");
		sb.append("targetLocaleId=").append(targetLocaleId);
		sb.append("]");
		return sb.toString();
	}

}
