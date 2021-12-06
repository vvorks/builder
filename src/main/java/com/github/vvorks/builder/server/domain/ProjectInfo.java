/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.List;
import java.util.Objects;

/**
 * プロジェクト型情報
 */
public class ProjectInfo {

	/** Summary情報 */
	private ProjectSummary summary;

	/** Contentリスト情報 */
	private List<ProjectContent> contents;

	/** オフセット */
	private int offset;

	/**
	 * Summary情報を取得する
	 *
	 * @return Summary情報
	 */
	public ProjectSummary getSummary() {
		return summary;
	}

	/**
	 * Summary情報を設定する
	 *
	 * @param summary サマリー情報
	 */
	public void setSummary(ProjectSummary summary) {
		this.summary = summary;
	}

	/**
	 * Contentリスト情報を取得する
	 *
	 * @return Contentリスト情報
	 */
	public List<ProjectContent> getContents() {
		return contents;
	}

	/**
	 * Contentリスト情報を設定する
	 *
	 * @param contents Contentリスト情報
	 */
	public void setContents(List<ProjectContent> contents) {
		this.contents = contents;
	}

	/**
	 * オフセットを取得する
	 *
	 * @return オフセット
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * オフセットを設定する
	 *
	 * @param offset オフセット
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public int hashCode() {
		return Objects.hash(summary, contents, offset);
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
			return equals((ProjectInfo) obj);
		}
	}

	private boolean equals(ProjectInfo other) {
		return	Objects.equals(summary, other.summary) &&
				Objects.equals(contents, other.contents) &&
				offset == other.offset;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ProjectInfo [");
		sb.append("summary=").append(summary).append(", ");
		sb.append("contents=").append(contents).append(", ");
		sb.append("offset=").append(offset);
		sb.append("]");
		return sb.toString();
	}

}
