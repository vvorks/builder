/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * リソース
 */
public class ResourceContent {

	/**
	 * resourceId
	 */
	private int resourceId;

	/**
	 * ownerのプロジェクトID
	 */
	private int ownerProjectId;

	/**
	 * defaultValue
	 */
	private String defaultValue;

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
	 * 空のリソースを作成する
	 */
	public ResourceContent() {
	}

	/**
	 * 指定のキー項目を持つリソースを作成する
	 *
	 * @param resourceId resourceId
	 */
	public ResourceContent(
		int resourceId
	) {
		this.resourceId = resourceId;
	}

	/**
	 * resourceIdを取得する
	 *
	 * @return resourceId
	 */
	public int getResourceId() {
		return this.resourceId;
	}

	/**
	 * resourceIdを設定する
	 *
	 * @param resourceId 設定するresourceId
	 */
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * ownerのプロジェクトIDを取得する
	 *
	 * @return ownerのプロジェクトID
	 */
	public int getOwnerProjectId() {
		return this.ownerProjectId;
	}

	/**
	 * ownerのプロジェクトIDを設定する
	 *
	 * @param ownerProjectId 設定するownerのプロジェクトID
	 */
	public void setOwnerProjectId(int ownerProjectId) {
		this.ownerProjectId = ownerProjectId;
	}

	/**
	 * defaultValueを取得する
	 *
	 * @return defaultValue
	 */
	public String getDefaultValue() {
		return this.defaultValue;
	}

	/**
	 * defaultValueを設定する
	 *
	 * @param defaultValue 設定するdefaultValue
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
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
			resourceId,
			ownerProjectId,
			defaultValue,
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
			return equals((ResourceContent) obj);
		}
	}

	private boolean equals(ResourceContent other) {
		return
			this.resourceId == other.resourceId && 
			this.ownerProjectId == other.ownerProjectId && 
			Objects.equals(this.defaultValue, other.defaultValue) && 
			Objects.equals(this._lastUpdatedAt, other._lastUpdatedAt)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ResourceContent [");
		sb.append("resourceId=").append(resourceId).append(", ");
		sb.append("ownerProjectId=").append(ownerProjectId).append(", ");
		sb.append("defaultValue=").append(defaultValue).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
