/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * クラス
 */
public class ClassContent {

	/**
	 * クラスID
	 *
	 * （代理キー）
	 */
	private int classId;

	/**
	 * 所属プロジェクトのプロジェクトID
	 */
	private int ownerProjectId;

	/**
	 * クラス名
	 */
	private String className;

	/**
	 * オーダー式
	 *
	 * デフォルトの表示順を表す式
	 */
	private String orderExpr;

	/**
	 * タイトル表示式
	 *
	 * オブジェクトのタイトルを表現する式
	 */
	private String titleExpr;

	/**
	 * タイトル
	 */
	private String title;

	/**
	 * 説明
	 */
	private String description;

	/**
	 * メモ
	 */
	private String note;

	/**
	 * 最終更新時刻
	 */
	private java.util.Date _lastUpdatedAt;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 所属プロジェクトのタイトル
	 */
	private String owner_title;

	/**
	 * 空のクラスを作成する
	 */
	public ClassContent() {
	}

	/**
	 * 指定のキー項目を持つクラスを作成する
	 *
	 * @param classId クラスID
	 */
	public ClassContent(
		int classId
	) {
		this.classId = classId;
	}

	/**
	 * クラスIDを取得する
	 *
	 * @return クラスID
	 */
	public int getClassId() {
		return this.classId;
	}

	/**
	 * クラスIDを設定する
	 *
	 * @param classId 設定するクラスID
	 */
	public void setClassId(int classId) {
		this.classId = classId;
	}

	/**
	 * 所属プロジェクトのプロジェクトIDを取得する
	 *
	 * @return 所属プロジェクトのプロジェクトID
	 */
	public int getOwnerProjectId() {
		return this.ownerProjectId;
	}

	/**
	 * 所属プロジェクトのプロジェクトIDを設定する
	 *
	 * @param ownerProjectId 設定する所属プロジェクトのプロジェクトID
	 */
	public void setOwnerProjectId(int ownerProjectId) {
		this.ownerProjectId = ownerProjectId;
	}

	/**
	 * クラス名を取得する
	 *
	 * @return クラス名
	 */
	public String getClassName() {
		return this.className;
	}

	/**
	 * クラス名を設定する
	 *
	 * @param className 設定するクラス名
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * オーダー式を取得する
	 *
	 * @return オーダー式
	 */
	public String getOrderExpr() {
		return this.orderExpr;
	}

	/**
	 * オーダー式を設定する
	 *
	 * @param orderExpr 設定するオーダー式
	 */
	public void setOrderExpr(String orderExpr) {
		this.orderExpr = orderExpr;
	}

	/**
	 * タイトル表示式を取得する
	 *
	 * @return タイトル表示式
	 */
	public String getTitleExpr() {
		return this.titleExpr;
	}

	/**
	 * タイトル表示式を設定する
	 *
	 * @param titleExpr 設定するタイトル表示式
	 */
	public void setTitleExpr(String titleExpr) {
		this.titleExpr = titleExpr;
	}

	/**
	 * タイトルを取得する
	 *
	 * @return タイトル
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * タイトルを設定する
	 *
	 * @param title 設定するタイトル
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 説明を取得する
	 *
	 * @return 説明
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * 説明を設定する
	 *
	 * @param description 設定する説明
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * メモを取得する
	 *
	 * @return メモ
	 */
	public String getNote() {
		return this.note;
	}

	/**
	 * メモを設定する
	 *
	 * @param note 設定するメモ
	 */
	public void setNote(String note) {
		this.note = note;
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
	 * 所属プロジェクトのタイトルを取得する
	 *
	 * @return 所属プロジェクトのタイトル
	 */
	public String getOwner_title() {
		return this.owner_title;
	}

	/**
	 * 所属プロジェクトのタイトルを設定する
	 *
	 * @param owner_title 設定する所属プロジェクトのタイトル
	 */
	public void setOwner_title(String owner_title) {
		this.owner_title = owner_title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			classId,
			ownerProjectId,
			className,
			orderExpr,
			titleExpr,
			title,
			description,
			note,
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
			return equals((ClassContent) obj);
		}
	}

	private boolean equals(ClassContent other) {
		return
			this.classId == other.classId && 
			this.ownerProjectId == other.ownerProjectId && 
			Objects.equals(this.className, other.className) && 
			Objects.equals(this.orderExpr, other.orderExpr) && 
			Objects.equals(this.titleExpr, other.titleExpr) && 
			Objects.equals(this.title, other.title) && 
			Objects.equals(this.description, other.description) && 
			Objects.equals(this.note, other.note) && 
			Objects.equals(this._lastUpdatedAt, other._lastUpdatedAt)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ClassContent [");
		sb.append("classId=").append(classId).append(", ");
		sb.append("ownerProjectId=").append(ownerProjectId).append(", ");
		sb.append("className=").append(className).append(", ");
		sb.append("orderExpr=").append(orderExpr).append(", ");
		sb.append("titleExpr=").append(titleExpr).append(", ");
		sb.append("title=").append(title).append(", ");
		sb.append("description=").append(description).append(", ");
		sb.append("note=").append(note).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
