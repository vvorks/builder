/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * クエリーのサマリー情報
 */
public class QuerySummary {

	/** 件数 */
	private int count;

	/**
	 * 件数を取得する
	 *
	 * @return 件数
	 */
	public int getCount() {
		return count;
	}

	/**
	 * 件数を設定する
	 *
	 * @param count 件数
	 */
	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			count);
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
			return equals((QuerySummary) obj);
		}
	}

	private boolean equals(QuerySummary other) {
		return
			this.count == other.count;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("QueryContent [");
		sb.append("count=").append(count);
		sb.append("]");
		return sb.toString();
	}

}
