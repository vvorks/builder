/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Map;
import java.util.Objects;

/**
 * クエリーの見出し
 */
public class QuerySubject extends Subject {

	public static Subject createFrom(Map<String, Object> args) {
		QuerySubject subject = new QuerySubject(
				asInt(args.get("queryId"))
				);
		subject.set_title(asString(args.get("_title")));
		return subject;
	}

	/**
	 * クエリーID
	 *
	 * （代理キー）
	 */
	private int queryId;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のクエリーの見出しを作成する
	 */
	public QuerySubject() {
	}

	/**
	 * 指定のキー項目を持つクエリーの見出しを作成する
	 *
	 * @param queryId クエリーID
	 */
	public QuerySubject(
		int queryId
	) {
		this.queryId = queryId;
	}

	/**
	 * クエリーIDを取得する
	 */
	public int getQueryId() {
		return queryId;
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
			queryId
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
			return equals((QuerySubject) obj);
		}
	}

	private boolean equals(QuerySubject other) {
		return
			this.queryId == other.queryId
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("QuerySubject [");
		sb.append("queryId=").append(queryId);
		sb.append("]");
		return sb.toString();
	}

}
