/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * メッセージ
 */
public class MessageContent {

	/**
	 * 所属プロジェクトのプロジェクトID
	 */
	private int ownerProjectId;

	/**
	 * メッセージID
	 */
	private String messageId;

	/**
	 * ロケール
	 */
	private String localeId;

	/**
	 * メッセージ文言
	 */
	private String text;

	/**
	 * 最終更新時刻
	 */
	private java.util.Date _lastUpdatedAt;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 所属プロジェクトのタイトル
	 */
	private String owner_title;

	/**
	 * 空のメッセージを作成する
	 */
	public MessageContent() {
	}

	/**
	 * 指定のキー項目を持つメッセージを作成する
	 *
	 * @param ownerProjectId 所属プロジェクトのプロジェクトID
	 * @param messageId メッセージID
	 * @param localeId ロケール
	 */
	public MessageContent(
		int ownerProjectId,
		String messageId,
		String localeId
	) {
		this.ownerProjectId = ownerProjectId;
		this.messageId = messageId;
		this.localeId = localeId;
	}

	/**
	 * 所属プロジェクトのプロジェクトIDを取得する
	 *
	 * @return 所属プロジェクトのプロジェクトID
	 */
	public int getOwnerProjectId() {
		return this.ownerProjectId;
	}

	/**
	 * 所属プロジェクトのプロジェクトIDを設定する
	 *
	 * @param ownerProjectId 設定する所属プロジェクトのプロジェクトID
	 */
	public void setOwnerProjectId(int ownerProjectId) {
		this.ownerProjectId = ownerProjectId;
	}

	/**
	 * メッセージIDを取得する
	 *
	 * @return メッセージID
	 */
	public String getMessageId() {
		return this.messageId;
	}

	/**
	 * メッセージIDを設定する
	 *
	 * @param messageId 設定するメッセージID
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	/**
	 * ロケールを取得する
	 *
	 * @return ロケール
	 */
	public String getLocaleId() {
		return this.localeId;
	}

	/**
	 * ロケールを設定する
	 *
	 * @param localeId 設定するロケール
	 */
	public void setLocaleId(String localeId) {
		this.localeId = localeId;
	}

	/**
	 * メッセージ文言を取得する
	 *
	 * @return メッセージ文言
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * メッセージ文言を設定する
	 *
	 * @param text 設定するメッセージ文言
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 最終更新時刻を取得する
	 *
	 * @return 最終更新時刻
	 */
	public java.util.Date get_lastUpdatedAt() {
		return this._lastUpdatedAt;
	}

	/**
	 * 最終更新時刻を設定する
	 *
	 * @param _lastUpdatedAt 設定する最終更新時刻
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
	 * 所属プロジェクトのタイトルを取得する
	 *
	 * @return 所属プロジェクトのタイトル
	 */
	public String getOwner_title() {
		return this.owner_title;
	}

	/**
	 * 所属プロジェクトのタイトルを設定する
	 *
	 * @param owner_title 設定する所属プロジェクトのタイトル
	 */
	public void setOwner_title(String owner_title) {
		this.owner_title = owner_title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			ownerProjectId,
			messageId,
			localeId,
			text,
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
			return equals((MessageContent) obj);
		}
	}

	private boolean equals(MessageContent other) {
		return
			this.ownerProjectId == other.ownerProjectId && 
			Objects.equals(this.messageId, other.messageId) && 
			Objects.equals(this.localeId, other.localeId) && 
			Objects.equals(this.text, other.text) && 
			Objects.equals(this._lastUpdatedAt, other._lastUpdatedAt)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("MessageContent [");
		sb.append("ownerProjectId=").append(ownerProjectId).append(", ");
		sb.append("messageId=").append(messageId).append(", ");
		sb.append("localeId=").append(localeId).append(", ");
		sb.append("text=").append(text).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
