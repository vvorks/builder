/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * クラスの見出し
 */
public class ClassSubject {

	/**
	 * クラスID
	 *
	 * （代理キー）
	 */
	private int classId;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のクラスの見出しを作成する
	 */
	public ClassSubject() {
	}

	/**
	 * 指定のキー項目を持つクラスの見出しを作成する
	 *
	 * @param classId クラスID
	 */
	public ClassSubject(
		int classId
	) {
		this.classId = classId;
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
			classId
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
			return equals((ClassSubject) obj);
		}
	}

	private boolean equals(ClassSubject other) {
		return
			this.classId == other.classId
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ClassSubject [");
		sb.append("classId=").append(classId);
		sb.append("]");
		return sb.toString();
	}

}
