/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * ウィジェットの見出し
 */
public class WidgetSubject {

	/**
	 * ウィジェットId
	 */
	private int widgetId;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のウィジェットの見出しを作成する
	 */
	public WidgetSubject() {
	}

	/**
	 * 指定のキー項目を持つウィジェットの見出しを作成する
	 *
	 * @param widgetId ウィジェットId
	 */
	public WidgetSubject(
		int widgetId
	) {
		this.widgetId = widgetId;
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
			widgetId
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
			return equals((WidgetSubject) obj);
		}
	}

	private boolean equals(WidgetSubject other) {
		return
			this.widgetId == other.widgetId
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("WidgetSubject [");
		sb.append("widgetId=").append(widgetId);
		sb.append("]");
		return sb.toString();
	}

}
