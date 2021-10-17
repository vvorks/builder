/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * クラス
 */
public class ClassDto {

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
	@NotNull
	private String className;

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
			classId,
			ownerProjectId,
			className,
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
			return equals((ClassDto) obj);
		}
	}

	private boolean equals(ClassDto other) {
		return
			this.classId == other.classId && 
			this.ownerProjectId == other.ownerProjectId && 
			Objects.equals(this.className, other.className) && 
			Objects.equals(this.title, other.title) && 
			Objects.equals(this.description, other.description) && 
			Objects.equals(this.note, other.note)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ClassDto [");
		sb.append("classId=").append(classId).append(", ");
		sb.append("ownerProjectId=").append(ownerProjectId).append(", ");
		sb.append("className=").append(className).append(", ");
		sb.append("title=").append(title).append(", ");
		sb.append("description=").append(description).append(", ");
		sb.append("note=").append(note);
		sb.append("]");
		return sb.toString();
	}

}
