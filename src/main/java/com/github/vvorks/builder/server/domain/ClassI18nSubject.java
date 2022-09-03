/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Map;
import java.util.Objects;

/**
 * クラス(I18n)の見出し
 */
public class ClassI18nSubject extends Subject {

	public static Subject createFrom(Map<String, Object> args) {
		ClassI18nSubject subject = new ClassI18nSubject(
				asInt(args.get("ownerClassId")),
				asString(args.get("targetLocaleId"))
				);
		subject.set_title(asString(args.get("_title")));
		return subject;
	}

	/**
	 * 所属クラスのクラスID
	 */
	private int ownerClassId;

	/**
	 * 対象ロケールのロケールID
	 */
	private String targetLocaleId;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のクラス(I18n)の見出しを作成する
	 */
	public ClassI18nSubject() {
	}

	/**
	 * 指定のキー項目を持つクラス(I18n)の見出しを作成する
	 *
	 * @param ownerClassId 所属クラスのクラスID
	 * @param targetLocaleId 対象ロケールのロケールID
	 */
	public ClassI18nSubject(
		int ownerClassId,
		String targetLocaleId
	) {
		this.ownerClassId = ownerClassId;
		this.targetLocaleId = targetLocaleId;
	}

	/**
	 * 所属クラスのクラスIDを取得する
	 */
	public int getOwnerClassId() {
		return ownerClassId;
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
			ownerClassId,
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
			return equals((ClassI18nSubject) obj);
		}
	}

	private boolean equals(ClassI18nSubject other) {
		return
			this.ownerClassId == other.ownerClassId && 
			Objects.equals(this.targetLocaleId, other.targetLocaleId)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ClassI18nSubject [");
		sb.append("ownerClassId=").append(ownerClassId).append(", ");
		sb.append("targetLocaleId=").append(targetLocaleId);
		sb.append("]");
		return sb.toString();
	}

}
