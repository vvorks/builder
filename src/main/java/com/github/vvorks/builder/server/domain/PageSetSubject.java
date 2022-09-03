/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Map;
import java.util.Objects;

/**
 * ページセットの見出し
 */
public class PageSetSubject extends Subject {

	public static Subject createFrom(Map<String, Object> args) {
		PageSetSubject subject = new PageSetSubject(
				asInt(args.get("pageSetId"))
				);
		subject.set_title(asString(args.get("_title")));
		return subject;
	}

	/**
	 * ページセットId
	 */
	private int pageSetId;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のページセットの見出しを作成する
	 */
	public PageSetSubject() {
	}

	/**
	 * 指定のキー項目を持つページセットの見出しを作成する
	 *
	 * @param pageSetId ページセットId
	 */
	public PageSetSubject(
		int pageSetId
	) {
		this.pageSetId = pageSetId;
	}

	/**
	 * ページセットIdを取得する
	 */
	public int getPageSetId() {
		return pageSetId;
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
			pageSetId
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
			return equals((PageSetSubject) obj);
		}
	}

	private boolean equals(PageSetSubject other) {
		return
			this.pageSetId == other.pageSetId
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PageSetSubject [");
		sb.append("pageSetId=").append(pageSetId);
		sb.append("]");
		return sb.toString();
	}

}
