/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * クラスのサマリー情報
 */
public class ClassSummary {

	/** 件数 */
	private int count;

	/** （初期）フォーカス位置 */
	private int focus;

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
			return equals((ClassSummary) obj);
		}
	}

	private boolean equals(ClassSummary other) {
		return
			this.count == other.count && this.focus == other.focus;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ClassContent [");
		sb.append("count=").append(count);
		sb.append("focus=").append(focus);
		sb.append("]");
		return sb.toString();
	}

}
