/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.List;
import java.util.Objects;

/**
 * 列挙値情報
 */
public class EnumValueInfo {

	/** Summary情報 */
	private EnumValueSummary summary;

	/** Contentリスト情報 */
	private List<EnumValueContent> contents;

	/** オフセット */
	private int offset;

	/** データ更新フラグ */
	private boolean updated;

	/**
	 * Summary情報を取得する
	 *
	 * @return Summary情報
	 */
	public EnumValueSummary getSummary() {
		return summary;
	}

	/**
	 * Summary情報を設定する
	 *
	 * @param summary サマリー情報
	 */
	public void setSummary(EnumValueSummary summary) {
		this.summary = summary;
	}

	/**
	 * Contentリスト情報を取得する
	 *
	 * @return Contentリスト情報
	 */
	public List<EnumValueContent> getContents() {
		return contents;
	}

	/**
	 * Contentリスト情報を設定する
	 *
	 * @param contents Contentリスト情報
	 */
	public void setContents(List<EnumValueContent> contents) {
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

	/**
	 * データ更新フラグを取得する
	 *
	 * @return データ更新フラグ
	 */
	public boolean isUpdated() {
		return updated;
	}

	/**
	 * データ更新フラグを設定する
	 *
	 * @param updated データ更新フラグ
	 */
	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	@Override
	public int hashCode() {
		return Objects.hash(summary, contents, offset, updated);
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
			return equals((EnumValueInfo) obj);
		}
	}

	private boolean equals(EnumValueInfo other) {
		return	Objects.equals(summary, other.summary) &&
				Objects.equals(contents, other.contents) &&
				offset == other.offset &&
				updated == other.updated;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("EnumValueInfo [");
		sb.append("summary=").append(summary).append(", ");
		sb.append("contents=").append(contents).append(", ");
		sb.append("offset=").append(offset).append(", ");
		sb.append("updated=").append(updated);
		sb.append("]");
		return sb.toString();
	}

}