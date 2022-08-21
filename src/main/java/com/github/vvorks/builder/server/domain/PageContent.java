/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * ページ
 */
public class PageContent {

	/**
	 * ページId
	 */
	private int pageId;

	/**
	 * 所属ページセットのページセットId
	 */
	private int ownerPageSetId;

	/**
	 * コンテキストクラスのクラスID
	 */
	private Integer contextClassId;

	/**
	 * ページ幅
	 */
	private int width;

	/**
	 * ページ高さ
	 */
	private int height;

	/**
	 * last updated at
	 */
	private java.util.Date _lastUpdatedAt;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 所属ページセットのタイトル
	 */
	private String owner_title;

	/**
	 * コンテキストクラスのタイトル
	 */
	private String context_title;

	/**
	 * 空のページを作成する
	 */
	public PageContent() {
	}

	/**
	 * 指定のキー項目を持つページを作成する
	 *
	 * @param pageId ページId
	 */
	public PageContent(
		int pageId
	) {
		this.pageId = pageId;
	}

	/**
	 * ページIdを取得する
	 *
	 * @return ページId
	 */
	public int getPageId() {
		return this.pageId;
	}

	/**
	 * ページIdを設定する
	 *
	 * @param pageId 設定するページId
	 */
	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

	/**
	 * 所属ページセットのページセットIdを取得する
	 *
	 * @return 所属ページセットのページセットId
	 */
	public int getOwnerPageSetId() {
		return this.ownerPageSetId;
	}

	/**
	 * 所属ページセットのページセットIdを設定する
	 *
	 * @param ownerPageSetId 設定する所属ページセットのページセットId
	 */
	public void setOwnerPageSetId(int ownerPageSetId) {
		this.ownerPageSetId = ownerPageSetId;
	}

	/**
	 * コンテキストクラスのクラスIDを取得する
	 *
	 * @return コンテキストクラスのクラスID
	 */
	public Integer getContextClassId() {
		return this.contextClassId;
	}

	/**
	 * コンテキストクラスのクラスIDを設定する
	 *
	 * @param contextClassId 設定するコンテキストクラスのクラスID
	 */
	public void setContextClassId(Integer contextClassId) {
		this.contextClassId = contextClassId;
	}

	/**
	 * ページ幅を取得する
	 *
	 * @return ページ幅
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * ページ幅を設定する
	 *
	 * @param width 設定するページ幅
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * ページ高さを取得する
	 *
	 * @return ページ高さ
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * ページ高さを設定する
	 *
	 * @param height 設定するページ高さ
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * last updated atを取得する
	 *
	 * @return last updated at
	 */
	public java.util.Date get_lastUpdatedAt() {
		return this._lastUpdatedAt;
	}

	/**
	 * last updated atを設定する
	 *
	 * @param _lastUpdatedAt 設定するlast updated at
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
	 * 所属ページセットのタイトルを取得する
	 *
	 * @return 所属ページセットのタイトル
	 */
	public String getOwner_title() {
		return this.owner_title;
	}

	/**
	 * 所属ページセットのタイトルを設定する
	 *
	 * @param owner_title 設定する所属ページセットのタイトル
	 */
	public void setOwner_title(String owner_title) {
		this.owner_title = owner_title;
	}

	/**
	 * コンテキストクラスのタイトルを取得する
	 *
	 * @return コンテキストクラスのタイトル
	 */
	public String getContext_title() {
		return this.context_title;
	}

	/**
	 * コンテキストクラスのタイトルを設定する
	 *
	 * @param context_title 設定するコンテキストクラスのタイトル
	 */
	public void setContext_title(String context_title) {
		this.context_title = context_title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			pageId,
			ownerPageSetId,
			contextClassId,
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
			return equals((PageContent) obj);
		}
	}

	private boolean equals(PageContent other) {
		return
			this.pageId == other.pageId && 
			this.ownerPageSetId == other.ownerPageSetId && 
			Objects.equals(this.contextClassId, other.contextClassId) && 
			this.width == other.width && 
			this.height == other.height && 
			Objects.equals(this._lastUpdatedAt, other._lastUpdatedAt)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PageContent [");
		sb.append("pageId=").append(pageId).append(", ");
		sb.append("ownerPageSetId=").append(ownerPageSetId).append(", ");
		sb.append("contextClassId=").append(contextClassId).append(", ");
		sb.append("width=").append(width).append(", ");
		sb.append("height=").append(height).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
