/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Map;
import java.util.Objects;

/**
 * メッセージ(I18n)の見出し
 */
public class MessageI18nSubject extends Subject {

	public static Subject createFrom(Map<String, Object> args) {
		MessageI18nSubject subject = new MessageI18nSubject(
				asInt(args.get("ownerMessageId")),
				asString(args.get("targetLocaleId"))
				);
		subject.set_title(asString(args.get("_title")));
		return subject;
	}

	/**
	 * 所属列挙値のメッセージID
	 */
	private int ownerMessageId;

	/**
	 * 対象ロケールのロケールID
	 */
	private String targetLocaleId;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のメッセージ(I18n)の見出しを作成する
	 */
	public MessageI18nSubject() {
	}

	/**
	 * 指定のキー項目を持つメッセージ(I18n)の見出しを作成する
	 *
	 * @param ownerMessageId 所属列挙値のメッセージID
	 * @param targetLocaleId 対象ロケールのロケールID
	 */
	public MessageI18nSubject(
		int ownerMessageId,
		String targetLocaleId
	) {
		this.ownerMessageId = ownerMessageId;
		this.targetLocaleId = targetLocaleId;
	}

	/**
	 * 所属列挙値のメッセージIDを取得する
	 */
	public int getOwnerMessageId() {
		return ownerMessageId;
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
			ownerMessageId,
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
			return equals((MessageI18nSubject) obj);
		}
	}

	private boolean equals(MessageI18nSubject other) {
		return
			this.ownerMessageId == other.ownerMessageId && 
			Objects.equals(this.targetLocaleId, other.targetLocaleId)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("MessageI18nSubject [");
		sb.append("ownerMessageId=").append(ownerMessageId).append(", ");
		sb.append("targetLocaleId=").append(targetLocaleId);
		sb.append("]");
		return sb.toString();
	}

}
