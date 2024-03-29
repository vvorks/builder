/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Map;
import java.util.Objects;

/**
 * フィールドの見出し
 */
public class FieldSubject extends Subject {

	public static Subject createFrom(Map<String, Object> args) {
		FieldSubject subject = new FieldSubject(
				asInt(args.get("fieldId"))
				);
		subject.set_title(asString(args.get("_title")));
		return subject;
	}

	/**
	 * フィールドID
	 *
	 * （代理キー）
	 */
	private int fieldId;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のフィールドの見出しを作成する
	 */
	public FieldSubject() {
	}

	/**
	 * 指定のキー項目を持つフィールドの見出しを作成する
	 *
	 * @param fieldId フィールドID
	 */
	public FieldSubject(
		int fieldId
	) {
		this.fieldId = fieldId;
	}

	/**
	 * フィールドIDを取得する
	 */
	public int getFieldId() {
		return fieldId;
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
			fieldId
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
			return equals((FieldSubject) obj);
		}
	}

	private boolean equals(FieldSubject other) {
		return
			this.fieldId == other.fieldId
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FieldSubject [");
		sb.append("fieldId=").append(fieldId);
		sb.append("]");
		return sb.toString();
	}

}
