/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Map;
import java.util.Objects;

/**
 * 列挙値(I18n)の見出し
 */
public class EnumValueI18nSubject extends Subject {

	public static Subject createFrom(Map<String, Object> args) {
		EnumValueI18nSubject subject = new EnumValueI18nSubject(
				asInt(args.get("ownerOwnerEnumId")),
				asString(args.get("ownerValueId")),
				asString(args.get("targetLocaleId"))
				);
		subject.set_title(asString(args.get("_title")));
		return subject;
	}

	/**
	 * 所属列挙値の所属列挙の列挙ID
	 */
	private int ownerOwnerEnumId;

	/**
	 * 所属列挙値の列挙名
	 */
	private String ownerValueId;

	/**
	 * 対象ロケールのロケールID
	 */
	private String targetLocaleId;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空の列挙値(I18n)の見出しを作成する
	 */
	public EnumValueI18nSubject() {
	}

	/**
	 * 指定のキー項目を持つ列挙値(I18n)の見出しを作成する
	 *
	 * @param ownerOwnerEnumId 所属列挙値の所属列挙の列挙ID
	 * @param ownerValueId 所属列挙値の列挙名
	 * @param targetLocaleId 対象ロケールのロケールID
	 */
	public EnumValueI18nSubject(
		int ownerOwnerEnumId,
		String ownerValueId,
		String targetLocaleId
	) {
		this.ownerOwnerEnumId = ownerOwnerEnumId;
		this.ownerValueId = ownerValueId;
		this.targetLocaleId = targetLocaleId;
	}

	/**
	 * 所属列挙値の所属列挙の列挙IDを取得する
	 */
	public int getOwnerOwnerEnumId() {
		return ownerOwnerEnumId;
	}

	/**
	 * 所属列挙値の列挙名を取得する
	 */
	public String getOwnerValueId() {
		return ownerValueId;
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
			ownerOwnerEnumId,
			ownerValueId,
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
			return equals((EnumValueI18nSubject) obj);
		}
	}

	private boolean equals(EnumValueI18nSubject other) {
		return
			this.ownerOwnerEnumId == other.ownerOwnerEnumId && 
			Objects.equals(this.ownerValueId, other.ownerValueId) && 
			Objects.equals(this.targetLocaleId, other.targetLocaleId)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("EnumValueI18nSubject [");
		sb.append("ownerOwnerEnumId=").append(ownerOwnerEnumId).append(", ");
		sb.append("ownerValueId=").append(ownerValueId).append(", ");
		sb.append("targetLocaleId=").append(targetLocaleId);
		sb.append("]");
		return sb.toString();
	}

}
