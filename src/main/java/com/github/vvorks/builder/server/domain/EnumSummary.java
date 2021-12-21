/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * 列挙のサマリー情報
 */
public class EnumSummary {

	/** 最終更新時刻の最大値 */
	private java.util.Date max_lastUpdatedAt;

	/** 件数 */
	private int count;

	/** （初期）フォーカス位置 */
	private int focus;

	/**
	 * 最終更新時刻の最大値を取得する
	 *
	 * @return 最終更新時刻の最大値
	 */
	public java.util.Date getMax_lastUpdatedAt() {
		return max_lastUpdatedAt;
	}

	/**
	 * 最終更新時刻の最大値を設定する
	 *
	 * @param max_lastUpdatedAt 最終更新時刻の最大値
	 */
	public void setMax_lastUpdatedAt(java.util.Date max_lastUpdatedAt) {
		this.max_lastUpdatedAt = max_lastUpdatedAt;
	}

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

	/**
	 * （初期）フォーカス位置を取得する
	 *
	 * @return 件数
	 */
	public int getFocus() {
		return focus;
	}

	/**
	 * （初期）フォーカス位置を設定する
	 *
	 * @param focus （初期）フォーカス位置
	 */
	public void setFocus(int focus) {
		this.focus = focus;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			max_lastUpdatedAt,
			count, focus);
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
			return equals((EnumSummary) obj);
		}
	}

	private boolean equals(EnumSummary other) {
		return
			Objects.equals(this.max_lastUpdatedAt, other.max_lastUpdatedAt) &&
			this.count == other.count && this.focus == other.focus;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("EnumContent [");
		sb.append("max_lastUpdatedAt=").append(max_lastUpdatedAt).append(", ");
		sb.append("count=").append(count).append(", ");
		sb.append("focus=").append(focus);
		sb.append("]");
		return sb.toString();
	}

}
