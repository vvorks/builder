/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * クエリー
 */
public class QueryContent {

	/**
	 * クエリーID
	 *
	 * （代理キー）
	 */
	private int queryId;

	/**
	 * 所属クラスのクラスID
	 */
	private int ownerClassId;

	/**
	 * クエリー名
	 */
	@NotNull
	private String queryName;

	/**
	 * 抽出条件
	 *
	 * 抽出条件を式形式で記載する
	 */
	@NotNull
	private String filter;

	/**
	 * ソート条件
	 *
	 * ソート条件を式形式で記載する
	 */
	@NotNull
	private String order;

	/**
	 * タイトル
	 */
	@NotNull
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
	 * クエリーIDを取得する
	 *
	 * @return クエリーID
	 */
	public int getQueryId() {
		return this.queryId;
	}

	/**
	 * クエリーIDを設定する
	 *
	 * @param queryId 設定するクエリーID
	 */
	public void setQueryId(int queryId) {
		this.queryId = queryId;
	}

	/**
	 * 所属クラスのクラスIDを取得する
	 *
	 * @return 所属クラスのクラスID
	 */
	public int getOwnerClassId() {
		return this.ownerClassId;
	}

	/**
	 * 所属クラスのクラスIDを設定する
	 *
	 * @param ownerClassId 設定する所属クラスのクラスID
	 */
	public void setOwnerClassId(int ownerClassId) {
		this.ownerClassId = ownerClassId;
	}

	/**
	 * クエリー名を取得する
	 *
	 * @return クエリー名
	 */
	public String getQueryName() {
		return this.queryName;
	}

	/**
	 * クエリー名を設定する
	 *
	 * @param queryName 設定するクエリー名
	 */
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	/**
	 * 抽出条件を取得する
	 *
	 * @return 抽出条件
	 */
	public String getFilter() {
		return this.filter;
	}

	/**
	 * 抽出条件を設定する
	 *
	 * @param filter 設定する抽出条件
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * ソート条件を取得する
	 *
	 * @return ソート条件
	 */
	public String getOrder() {
		return this.order;
	}

	/**
	 * ソート条件を設定する
	 *
	 * @param order 設定するソート条件
	 */
	public void setOrder(String order) {
		this.order = order;
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

	@Override
	public int hashCode() {
		return Objects.hash(
			queryId,
			ownerClassId,
			queryName,
			filter,
			order,
			title,
			description,
			note
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
			return equals((QueryContent) obj);
		}
	}

	private boolean equals(QueryContent other) {
		return
			this.queryId == other.queryId && 
			this.ownerClassId == other.ownerClassId && 
			Objects.equals(this.queryName, other.queryName) && 
			Objects.equals(this.filter, other.filter) && 
			Objects.equals(this.order, other.order) && 
			Objects.equals(this.title, other.title) && 
			Objects.equals(this.description, other.description) && 
			Objects.equals(this.note, other.note)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("QueryContent [");
		sb.append("queryId=").append(queryId).append(", ");
		sb.append("ownerClassId=").append(ownerClassId).append(", ");
		sb.append("queryName=").append(queryName).append(", ");
		sb.append("filter=").append(filter).append(", ");
		sb.append("order=").append(order).append(", ");
		sb.append("title=").append(title).append(", ");
		sb.append("description=").append(description).append(", ");
		sb.append("note=").append(note);
		sb.append("]");
		return sb.toString();
	}

}
