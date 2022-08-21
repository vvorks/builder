/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.List;
import java.util.Objects;

/**
 * レイアウトのサマリー情報
 */
public class LayoutSummary<T> {

	/** レイアウトIdの最大値 */
	private int maxLayoutId;

	/** last updated atの最大値 */
	private java.util.Date max_lastUpdatedAt;

	/** 件数 */
	private int count;

	/** （初期）フォーカス位置 */
	private int focus;

	/** オフセット */
	private int offset;

	/** Contentリスト情報 */
	private List<T> contents;

	/**
	 * レイアウトIdの最大値を取得する
	 *
	 * @return レイアウトIdの最大値
	 */
	public int getMaxLayoutId() {
		return maxLayoutId;
	}

	/**
	 * レイアウトIdの最大値を設定する
	 *
	 * @param maxLayoutId レイアウトIdの最大値
	 */
	public void setMaxLayoutId(int maxLayoutId) {
		this.maxLayoutId = maxLayoutId;
	}

	/**
	 * last updated atの最大値を取得する
	 *
	 * @return last updated atの最大値
	 */
	public java.util.Date getMax_lastUpdatedAt() {
		return max_lastUpdatedAt;
	}

	/**
	 * last updated atの最大値を設定する
	 *
	 * @param max_lastUpdatedAt last updated atの最大値
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

	/**
	 * オフセットを取得する
	 *
	 * @return オフセット
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * オフセットを設定する
	 *
	 * @param offset オフセット
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * Contentリスト情報を取得する
	 *
	 * @return Contentリスト情報
	 */
	public List<T> getContents() {
		return contents;
	}

	/**
	 * Contentリスト情報を設定する
	 *
	 * @param contents Contentリスト情報
	 */
	public void setContents(List<T> contents) {
		this.contents = contents;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			maxLayoutId,
			max_lastUpdatedAt,
			count, focus, offset, contents);
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
			@SuppressWarnings("unchecked")
			LayoutSummary<T> other = (LayoutSummary<T>) obj;
			return equals(other);
		}
	}

	private boolean equals(LayoutSummary<T> other) {
		return
			this.maxLayoutId == other.maxLayoutId &&
			Objects.equals(this.max_lastUpdatedAt, other.max_lastUpdatedAt) &&
			this.count == other.count &&
			this.focus == other.focus &&
			this.offset == other.offset &&
			Objects.equals(this.contents, other.contents);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("LayoutContent [");
		sb.append("maxLayoutId=").append(maxLayoutId).append(", ");
		sb.append("max_lastUpdatedAt=").append(max_lastUpdatedAt).append(", ");
		sb.append("count=").append(count).append(", ");
		sb.append("focus=").append(focus).append(", ");
		sb.append("offset=").append(offset).append(", ");
		sb.append("contents=").append(contents);
		sb.append("]");
		return sb.toString();
	}

}
