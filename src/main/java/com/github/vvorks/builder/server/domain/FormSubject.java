/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * フォームの見出し
 */
public class FormSubject {

	/**
	 * formId
	 */
	private int formId;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のフォームの見出しを作成する
	 */
	public FormSubject() {
	}

	/**
	 * 指定のキー項目を持つフォームの見出しを作成する
	 *
	 * @param formId formId
	 */
	public FormSubject(
		int formId
	) {
		this.formId = formId;
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
			formId
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
			return equals((FormSubject) obj);
		}
	}

	private boolean equals(FormSubject other) {
		return
			this.formId == other.formId
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FormSubject [");
		sb.append("formId=").append(formId);
		sb.append("]");
		return sb.toString();
	}

}
