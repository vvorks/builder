/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Map;
import java.util.Objects;

/**
 * フィールド(I18n)の見出し
 */
public class FieldI18nSubject extends Subject {

	public static Subject createFrom(Map<String, Object> args) {
		FieldI18nSubject subject = new FieldI18nSubject(
				asInt(args.get("ownerFieldId")),
				asString(args.get("targetLocaleId"))
				);
		subject.set_title(asString(args.get("_title")));
		return subject;
	}

	/**
	 * 所属フィールドのフィールドID
	 */
	private int ownerFieldId;

	/**
	 * 対象ロケールのロケールID
	 */
	private String targetLocaleId;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のフィールド(I18n)の見出しを作成する
	 */
	public FieldI18nSubject() {
	}

	/**
	 * 指定のキー項目を持つフィールド(I18n)の見出しを作成する
	 *
	 * @param ownerFieldId 所属フィールドのフィールドID
	 * @param targetLocaleId 対象ロケールのロケールID
	 */
	public FieldI18nSubject(
		int ownerFieldId,
		String targetLocaleId
	) {
		this.ownerFieldId = ownerFieldId;
		this.targetLocaleId = targetLocaleId;
	}

	/**
	 * 所属フィールドのフィールドIDを取得する
	 */
	public int getOwnerFieldId() {
		return ownerFieldId;
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
			ownerFieldId,
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
			return equals((FieldI18nSubject) obj);
		}
	}

	private boolean equals(FieldI18nSubject other) {
		return
			this.ownerFieldId == other.ownerFieldId && 
			Objects.equals(this.targetLocaleId, other.targetLocaleId)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FieldI18nSubject [");
		sb.append("ownerFieldId=").append(ownerFieldId).append(", ");
		sb.append("targetLocaleId=").append(targetLocaleId);
		sb.append("]");
		return sb.toString();
	}

}
