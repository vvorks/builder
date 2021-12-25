/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * プロジェクトの見出し
 */
public class ProjectSubject {

	/**
	 * プロジェクトID
	 *
	 * （代理キー）
	 */
	private int projectId;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 空のプロジェクトの見出しを作成する
	 */
	public ProjectSubject() {
	}

	/**
	 * 指定のキー項目を持つプロジェクトの見出しを作成する
	 *
	 * @param projectId プロジェクトID
	 */
	public ProjectSubject(
		int projectId
	) {
		this.projectId = projectId;
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
			projectId
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
			return equals((ProjectSubject) obj);
		}
	}

	private boolean equals(ProjectSubject other) {
		return
			this.projectId == other.projectId
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ProjectSubject [");
		sb.append("projectId=").append(projectId);
		sb.append("]");
		return sb.toString();
	}

}
