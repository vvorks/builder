/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * レイアウトの見出し
 */
public class LayoutSubject {

	/**
	 * layoutId
	 */
	private int layoutId;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のレイアウトの見出しを作成する
	 */
	public LayoutSubject() {
	}

	/**
	 * 指定のキー項目を持つレイアウトの見出しを作成する
	 *
	 * @param layoutId layoutId
	 */
	public LayoutSubject(
		int layoutId
	) {
		this.layoutId = layoutId;
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
			layoutId
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
			return equals((LayoutSubject) obj);
		}
	}

	private boolean equals(LayoutSubject other) {
		return
			this.layoutId == other.layoutId
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("LayoutSubject [");
		sb.append("layoutId=").append(layoutId);
		sb.append("]");
		return sb.toString();
	}

}
