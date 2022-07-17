/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * フォームバリエーション
 */
public class FormVariantContent {

	/**
	 * variantId
	 */
	private int variantId;

	/**
	 * ownerのformId
	 */
	private int ownerFormId;

	/**
	 * width
	 */
	private int width;

	/**
	 * height
	 */
	private int height;

	/**
	 * 最終更新時刻
	 */
	private java.util.Date _lastUpdatedAt;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * ownerのタイトル
	 */
	private String owner_title;

	/**
	 * 空のフォームバリエーションを作成する
	 */
	public FormVariantContent() {
	}

	/**
	 * 指定のキー項目を持つフォームバリエーションを作成する
	 *
	 * @param variantId variantId
	 */
	public FormVariantContent(
		int variantId
	) {
		this.variantId = variantId;
	}

	/**
	 * variantIdを取得する
	 *
	 * @return variantId
	 */
	public int getVariantId() {
		return this.variantId;
	}

	/**
	 * variantIdを設定する
	 *
	 * @param variantId 設定するvariantId
	 */
	public void setVariantId(int variantId) {
		this.variantId = variantId;
	}

	/**
	 * ownerのformIdを取得する
	 *
	 * @return ownerのformId
	 */
	public int getOwnerFormId() {
		return this.ownerFormId;
	}

	/**
	 * ownerのformIdを設定する
	 *
	 * @param ownerFormId 設定するownerのformId
	 */
	public void setOwnerFormId(int ownerFormId) {
		this.ownerFormId = ownerFormId;
	}

	/**
	 * widthを取得する
	 *
	 * @return width
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * widthを設定する
	 *
	 * @param width 設定するwidth
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * heightを取得する
	 *
	 * @return height
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * heightを設定する
	 *
	 * @param height 設定するheight
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * 最終更新時刻を取得する
	 *
	 * @return 最終更新時刻
	 */
	public java.util.Date get_lastUpdatedAt() {
		return this._lastUpdatedAt;
	}

	/**
	 * 最終更新時刻を設定する
	 *
	 * @param _lastUpdatedAt 設定する最終更新時刻
	 */
	public void set_lastUpdatedAt(java.util.Date _lastUpdatedAt) {
		this._lastUpdatedAt = _lastUpdatedAt;
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

	/**
	 * ownerのタイトルを取得する
	 *
	 * @return ownerのタイトル
	 */
	public String getOwner_title() {
		return this.owner_title;
	}

	/**
	 * ownerのタイトルを設定する
	 *
	 * @param owner_title 設定するownerのタイトル
	 */
	public void setOwner_title(String owner_title) {
		this.owner_title = owner_title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			variantId,
			ownerFormId,
			width,
			height,
			_lastUpdatedAt
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
			return equals((FormVariantContent) obj);
		}
	}

	private boolean equals(FormVariantContent other) {
		return
			this.variantId == other.variantId && 
			this.ownerFormId == other.ownerFormId && 
			this.width == other.width && 
			this.height == other.height && 
			Objects.equals(this._lastUpdatedAt, other._lastUpdatedAt)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FormVariantContent [");
		sb.append("variantId=").append(variantId).append(", ");
		sb.append("ownerFormId=").append(ownerFormId).append(", ");
		sb.append("width=").append(width).append(", ");
		sb.append("height=").append(height).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
