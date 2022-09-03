/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Map;
import java.util.Objects;

/**
 * ページの見出し
 */
public class PageSubject extends Subject {

	public static Subject createFrom(Map<String, Object> args) {
		PageSubject subject = new PageSubject(
				asInt(args.get("pageId"))
				);
		subject.set_title(asString(args.get("_title")));
		return subject;
	}

	/**
	 * ページId
	 */
	private int pageId;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のページの見出しを作成する
	 */
	public PageSubject() {
	}

	/**
	 * 指定のキー項目を持つページの見出しを作成する
	 *
	 * @param pageId ページId
	 */
	public PageSubject(
		int pageId
	) {
		this.pageId = pageId;
	}

	/**
	 * ページIdを取得する
	 */
	public int getPageId() {
		return pageId;
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
			pageId
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
			return equals((PageSubject) obj);
		}
	}

	private boolean equals(PageSubject other) {
		return
			this.pageId == other.pageId
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PageSubject [");
		sb.append("pageId=").append(pageId);
		sb.append("]");
		return sb.toString();
	}

}
