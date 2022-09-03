/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Map;
import java.util.Objects;

/**
 * 列挙(I18n)の見出し
 */
public class EnumI18nSubject extends Subject {

	public static Subject createFrom(Map<String, Object> args) {
		EnumI18nSubject subject = new EnumI18nSubject(
				asInt(args.get("ownerEnumId")),
				asString(args.get("targetLocaleId"))
				);
		subject.set_title(asString(args.get("_title")));
		return subject;
	}

	/**
	 * 所属列挙の列挙ID
	 */
	private int ownerEnumId;

	/**
	 * 対象ロケールのロケールID
	 */
	private String targetLocaleId;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空の列挙(I18n)の見出しを作成する
	 */
	public EnumI18nSubject() {
	}

	/**
	 * 指定のキー項目を持つ列挙(I18n)の見出しを作成する
	 *
	 * @param ownerEnumId 所属列挙の列挙ID
	 * @param targetLocaleId 対象ロケールのロケールID
	 */
	public EnumI18nSubject(
		int ownerEnumId,
		String targetLocaleId
	) {
		this.ownerEnumId = ownerEnumId;
		this.targetLocaleId = targetLocaleId;
	}

	/**
	 * 所属列挙の列挙IDを取得する
	 */
	public int getOwnerEnumId() {
		return ownerEnumId;
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
			ownerEnumId,
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
			return equals((EnumI18nSubject) obj);
		}
	}

	private boolean equals(EnumI18nSubject other) {
		return
			this.ownerEnumId == other.ownerEnumId && 
			Objects.equals(this.targetLocaleId, other.targetLocaleId)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("EnumI18nSubject [");
		sb.append("ownerEnumId=").append(ownerEnumId).append(", ");
		sb.append("targetLocaleId=").append(targetLocaleId);
		sb.append("]");
		return sb.toString();
	}

}
