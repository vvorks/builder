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
	 * メッセージID
	 */
	private int messageId;

	/**
	 * 所属プロジェクトのプロジェクトID
	 */
	private int ownerProjectId;

	/**
	 * メッセージ名
	 */
	private String messageName;

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
	 * @param messageId メッセージID
	 */
	public MessageContent(
		int messageId
	) {
		this.messageId = messageId;
	}

	/**
	 * メッセージIDを取得する
	 *
	 * @return メッセージID
	 */
	public int getMessageId() {
		return this.messageId;
	}

	/**
	 * メッセージIDを設定する
	 *
	 * @param messageId 設定するメッセージID
	 */
	public void setMessageId(int messageId) {
		this.messageId = messageId;
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
	 * メッセージ名を取得する
	 *
	 * @return メッセージ名
	 */
	public String getMessageName() {
		return this.messageName;
	}

	/**
	 * メッセージ名を設定する
	 *
	 * @param messageName 設定するメッセージ名
	 */
	public void setMessageName(String messageName) {
		this.messageName = messageName;
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
			messageId,
			ownerProjectId,
			messageName,
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
			return equals((MessageContent) obj);
		}
	}

	private boolean equals(MessageContent other) {
		return
			this.messageId == other.messageId && 
			this.ownerProjectId == other.ownerProjectId && 
			Objects.equals(this.messageName, other.messageName) && 
			Objects.equals(this.message, other.message) && 
			Objects.equals(this._lastUpdatedAt, other._lastUpdatedAt)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("MessageContent [");
		sb.append("messageId=").append(messageId).append(", ");
		sb.append("ownerProjectId=").append(ownerProjectId).append(", ");
		sb.append("messageName=").append(messageName).append(", ");
		sb.append("message=").append(message).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
