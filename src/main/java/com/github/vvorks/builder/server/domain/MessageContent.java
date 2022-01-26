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
	 * メッセージのresourceId
	 */
	private int messageResourceId;

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
	 * メッセージのタイトル
	 */
	private String message_title;

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
	 * メッセージのresourceIdを取得する
	 *
	 * @return メッセージのresourceId
	 */
	public int getMessageResourceId() {
		return this.messageResourceId;
	}

	/**
	 * メッセージのresourceIdを設定する
	 *
	 * @param messageResourceId 設定するメッセージのresourceId
	 */
	public void setMessageResourceId(int messageResourceId) {
		this.messageResourceId = messageResourceId;
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

	/**
	 * メッセージのタイトルを取得する
	 *
	 * @return メッセージのタイトル
	 */
	public String getMessage_title() {
		return this.message_title;
	}

	/**
	 * メッセージのタイトルを設定する
	 *
	 * @param message_title 設定するメッセージのタイトル
	 */
	public void setMessage_title(String message_title) {
		this.message_title = message_title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			messageId,
			ownerProjectId,
			messageName,
			messageResourceId,
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
			this.messageResourceId == other.messageResourceId && 
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
		sb.append("messageResourceId=").append(messageResourceId).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
