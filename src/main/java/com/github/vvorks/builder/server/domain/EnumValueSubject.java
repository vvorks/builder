/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Map;
import java.util.Objects;

/**
 * 列挙値の見出し
 */
public class EnumValueSubject extends Subject {

	public static Subject createFrom(Map<String, Object> args) {
		EnumValueSubject subject = new EnumValueSubject(
				asInt(args.get("ownerEnumId")),
				asString(args.get("valueId"))
				);
		subject.set_title(asString(args.get("_title")));
		return subject;
	}

	/**
	 * 所属列挙の列挙ID
	 */
	private int ownerEnumId;

	/**
	 * 列挙名
	 */
	private String valueId;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空の列挙値の見出しを作成する
	 */
	public EnumValueSubject() {
	}

	/**
	 * 指定のキー項目を持つ列挙値の見出しを作成する
	 *
	 * @param ownerEnumId 所属列挙の列挙ID
	 * @param valueId 列挙名
	 */
	public EnumValueSubject(
		int ownerEnumId,
		String valueId
	) {
		this.ownerEnumId = ownerEnumId;
		this.valueId = valueId;
	}

	/**
	 * 所属列挙の列挙IDを取得する
	 */
	public int getOwnerEnumId() {
		return ownerEnumId;
	}

	/**
	 * 列挙名を取得する
	 */
	public String getValueId() {
		return valueId;
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
			valueId
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
			return equals((EnumValueSubject) obj);
		}
	}

	private boolean equals(EnumValueSubject other) {
		return
			this.ownerEnumId == other.ownerEnumId && 
			Objects.equals(this.valueId, other.valueId)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("EnumValueSubject [");
		sb.append("ownerEnumId=").append(ownerEnumId).append(", ");
		sb.append("valueId=").append(valueId);
		sb.append("]");
		return sb.toString();
	}

}
