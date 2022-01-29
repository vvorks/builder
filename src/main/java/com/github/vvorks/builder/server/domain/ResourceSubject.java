/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * リソースの見出し
 */
public class ResourceSubject {

	/**
	 * リソースID
	 */
	private int resourceId;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のリソースの見出しを作成する
	 */
	public ResourceSubject() {
	}

	/**
	 * 指定のキー項目を持つリソースの見出しを作成する
	 *
	 * @param resourceId リソースID
	 */
	public ResourceSubject(
		int resourceId
	) {
		this.resourceId = resourceId;
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
			resourceId
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
			return equals((ResourceSubject) obj);
		}
	}

	private boolean equals(ResourceSubject other) {
		return
			this.resourceId == other.resourceId
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ResourceSubject [");
		sb.append("resourceId=").append(resourceId);
		sb.append("]");
		return sb.toString();
	}

}
