/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * メッセージの見出し
 */
public class MessageSubject {

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
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のメッセージの見出しを作成する
	 */
	public MessageSubject() {
	}

	/**
	 * 指定のキー項目を持つメッセージの見出しを作成する
	 *
	 * @param ownerProjectId 所属プロジェクトのプロジェクトID
	 * @param messageId メッセージID
	 * @param localeId ロケール
	 */
	public MessageSubject(
		int ownerProjectId,
		String messageId,
		String localeId
	) {
		this.ownerProjectId = ownerProjectId;
		this.messageId = messageId;
		this.localeId = localeId;
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

	@Override
	public int hashCode() {
		return Objects.hash(
			ownerProjectId,
			messageId,
			localeId
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
			return equals((MessageSubject) obj);
		}
	}

	private boolean equals(MessageSubject other) {
		return
			this.ownerProjectId == other.ownerProjectId && 
			Objects.equals(this.messageId, other.messageId) && 
			Objects.equals(this.localeId, other.localeId)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("MessageSubject [");
		sb.append("ownerProjectId=").append(ownerProjectId).append(", ");
		sb.append("messageId=").append(messageId).append(", ");
		sb.append("localeId=").append(localeId);
		sb.append("]");
		return sb.toString();
	}

}
