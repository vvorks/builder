/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Map;
import java.util.Objects;

/**
 * メッセージの見出し
 */
public class MessageSubject extends Subject {

	public static Subject createFrom(Map<String, Object> args) {
		MessageSubject subject = new MessageSubject(
				asInt(args.get("messageId"))
				);
		subject.set_title(asString(args.get("_title")));
		return subject;
	}

	/**
	 * メッセージID
	 */
	private int messageId;

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
	 * @param messageId メッセージID
	 */
	public MessageSubject(
		int messageId
	) {
		this.messageId = messageId;
	}

	/**
	 * メッセージIDを取得する
	 */
	public int getMessageId() {
		return messageId;
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
			messageId
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
			this.messageId == other.messageId
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("MessageSubject [");
		sb.append("messageId=").append(messageId);
		sb.append("]");
		return sb.toString();
	}

}
