/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * クエリーの見出し
 */
public class QuerySubject {

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
