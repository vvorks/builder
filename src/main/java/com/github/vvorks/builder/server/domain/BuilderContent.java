/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * Builder
 */
public class BuilderContent {

	/**
	 * className
	 */
	private String className;

	/**
	 * surrogateCount
	 */
	private int surrogateCount;

	/**
	 * last updated at
	 */
	private java.util.Date _lastUpdatedAt;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のBuilderを作成する
	 */
	public BuilderContent() {
	}

	/**
	 * 指定のキー項目を持つBuilderを作成する
	 *
	 * @param className className
	 */
	public BuilderContent(
		String className
	) {
		this.className = className;
	}

	/**
	 * classNameを取得する
	 *
	 * @return className
	 */
	public String getClassName() {
		return this.className;
	}

	/**
	 * classNameを設定する
	 *
	 * @param className 設定するclassName
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * surrogateCountを取得する
	 *
	 * @return surrogateCount
	 */
	public int getSurrogateCount() {
		return this.surrogateCount;
	}

	/**
	 * surrogateCountを設定する
	 *
	 * @param surrogateCount 設定するsurrogateCount
	 */
	public void setSurrogateCount(int surrogateCount) {
		this.surrogateCount = surrogateCount;
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

	@Override
	public int hashCode() {
		return Objects.hash(
			className,
			surrogateCount,
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
			return equals((BuilderContent) obj);
		}
	}

	private boolean equals(BuilderContent other) {
		return
			Objects.equals(this.className, other.className) && 
			this.surrogateCount == other.surrogateCount && 
			Objects.equals(this._lastUpdatedAt, other._lastUpdatedAt)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("BuilderContent [");
		sb.append("className=").append(className).append(", ");
		sb.append("surrogateCount=").append(surrogateCount).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
