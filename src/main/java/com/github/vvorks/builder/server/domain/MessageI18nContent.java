/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * メッセージ(I18n)
 */
public class MessageI18nContent {

	/**
	 * 所属列挙値のメッセージID
	 */
	private int ownerMessageId;

	/**
	 * 対象ロケールのロケールID
	 */
	private String targetLocaleId;

	/**
	 * メッセージ
	 */
	private String message;

	/**
	 * last updated at
	 */
	private java.util.Date _lastUpdatedAt;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 所属列挙値のタイトル
	 */
	private String owner_title;

	/**
	 * 対象ロケールのタイトル
	 */
	private String target_title;

	/**
	 * 空のメッセージ(I18n)を作成する
	 */
	public MessageI18nContent() {
	}

	/**
	 * 指定のキー項目を持つメッセージ(I18n)を作成する
	 *
	 * @param ownerMessageId 所属列挙値のメッセージID
	 * @param targetLocaleId 対象ロケールのロケールID
	 */
	public MessageI18nContent(
		int ownerMessageId,
		String targetLocaleId
	) {
		this.ownerMessageId = ownerMessageId;
		this.targetLocaleId = targetLocaleId;
	}

	/**
	 * 所属列挙値のメッセージIDを取得する
	 *
	 * @return 所属列挙値のメッセージID
	 */
	public int getOwnerMessageId() {
		return this.ownerMessageId;
	}

	/**
	 * 所属列挙値のメッセージIDを設定する
	 *
	 * @param ownerMessageId 設定する所属列挙値のメッセージID
	 */
	public void setOwnerMessageId(int ownerMessageId) {
		this.ownerMessageId = ownerMessageId;
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
	 * メッセージを取得する
	 *
	 * @return メッセージ
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * メッセージを設定する
	 *
	 * @param message 設定するメッセージ
	 */
	public void setMessage(String message) {
		this.message = message;
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

	/**
	 * 所属列挙値のタイトルを取得する
	 *
	 * @return 所属列挙値のタイトル
	 */
	public String getOwner_title() {
		return this.owner_title;
	}

	/**
	 * 所属列挙値のタイトルを設定する
	 *
	 * @param owner_title 設定する所属列挙値のタイトル
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
			ownerMessageId,
			targetLocaleId,
			message,
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
			return equals((MessageI18nContent) obj);
		}
	}

	private boolean equals(MessageI18nContent other) {
		return
			this.ownerMessageId == other.ownerMessageId && 
			Objects.equals(this.targetLocaleId, other.targetLocaleId) && 
			Objects.equals(this.message, other.message) && 
			Objects.equals(this._lastUpdatedAt, other._lastUpdatedAt)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("MessageI18nContent [");
		sb.append("ownerMessageId=").append(ownerMessageId).append(", ");
		sb.append("targetLocaleId=").append(targetLocaleId).append(", ");
		sb.append("message=").append(message).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
