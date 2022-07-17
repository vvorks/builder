/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * フォームバリエーションの見出し
 */
public class FormVariantSubject {

	/**
	 * variantId
	 */
	private int variantId;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のフォームバリエーションの見出しを作成する
	 */
	public FormVariantSubject() {
	}

	/**
	 * 指定のキー項目を持つフォームバリエーションの見出しを作成する
	 *
	 * @param variantId variantId
	 */
	public FormVariantSubject(
		int variantId
	) {
		this.variantId = variantId;
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
			variantId
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
			return equals((FormVariantSubject) obj);
		}
	}

	private boolean equals(FormVariantSubject other) {
		return
			this.variantId == other.variantId
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FormVariantSubject [");
		sb.append("variantId=").append(variantId);
		sb.append("]");
		return sb.toString();
	}

}
