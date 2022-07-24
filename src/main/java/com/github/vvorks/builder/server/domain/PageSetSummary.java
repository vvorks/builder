/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.List;
import java.util.Objects;

/**
 * ページセットのサマリー情報
 */
public class PageSetSummary<T> {

	/** ページセットIdの最大値 */
	private int maxPageSetId;

	/** 最終更新時刻の最大値 */
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
	 * ページセットIdの最大値を取得する
	 *
	 * @return ページセットIdの最大値
	 */
	public int getMaxPageSetId() {
		return maxPageSetId;
	}

	/**
	 * ページセットIdの最大値を設定する
	 *
	 * @param maxPageSetId ページセットIdの最大値
	 */
	public void setMaxPageSetId(int maxPageSetId) {
		this.maxPageSetId = maxPageSetId;
	}

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
			maxPageSetId,
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
			PageSetSummary<T> other = (PageSetSummary<T>) obj;
			return equals(other);
		}
	}

	private boolean equals(PageSetSummary<T> other) {
		return
			this.maxPageSetId == other.maxPageSetId &&
			Objects.equals(this.max_lastUpdatedAt, other.max_lastUpdatedAt) &&
			this.count == other.count &&
			this.focus == other.focus &&
			this.offset == other.offset &&
			Objects.equals(this.contents, other.contents);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PageSetContent [");
		sb.append("maxPageSetId=").append(maxPageSetId).append(", ");
		sb.append("max_lastUpdatedAt=").append(max_lastUpdatedAt).append(", ");
		sb.append("count=").append(count).append(", ");
		sb.append("focus=").append(focus).append(", ");
		sb.append("offset=").append(offset).append(", ");
		sb.append("contents=").append(contents);
		sb.append("]");
		return sb.toString();
	}

}
