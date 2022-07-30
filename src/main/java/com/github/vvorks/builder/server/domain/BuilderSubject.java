/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * Builderの見出し
 */
public class BuilderSubject {

	/**
	 * className
	 */
	private String className;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のBuilderの見出しを作成する
	 */
	public BuilderSubject() {
	}

	/**
	 * 指定のキー項目を持つBuilderの見出しを作成する
	 *
	 * @param className className
	 */
	public BuilderSubject(
		String className
	) {
		this.className = className;
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
			className
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
			return equals((BuilderSubject) obj);
		}
	}

	private boolean equals(BuilderSubject other) {
		return
			Objects.equals(this.className, other.className)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("BuilderSubject [");
		sb.append("className=").append(className);
		sb.append("]");
		return sb.toString();
	}

}
