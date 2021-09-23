/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * 列挙
 */
public class EnumDto {

	private int enumId;

	private int ownerProjectId;

	private String enumName;

	private String title;

	private String description;

	private String note;

	public int getEnumId() {
		return this.enumId;
	}

	public void setEnumId(int enumId) {
		this.enumId = enumId;
	}

	public int getOwnerProjectId() {
		return this.ownerProjectId;
	}

	public void setOwnerProjectId(int ownerProjectId) {
		this.ownerProjectId = ownerProjectId;
	}

	public String getEnumName() {
		return this.enumName;
	}

	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			enumId,
			ownerProjectId,
			enumName,
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
			return equals((EnumDto) obj);
		}
	}

	private boolean equals(EnumDto other) {
		return
			this.enumId == other.enumId && 
			this.ownerProjectId == other.ownerProjectId && 
			Objects.equals(this.enumName, other.enumName) && 
			Objects.equals(this.title, other.title) && 
			Objects.equals(this.description, other.description) && 
			Objects.equals(this.note, other.note)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("EnumDto [");
		sb.append("enumId=").append(enumId).append(", ");
		sb.append("ownerProjectId=").append(ownerProjectId).append(", ");
		sb.append("enumName=").append(enumName).append(", ");
		sb.append("title=").append(title).append(", ");
		sb.append("description=").append(description).append(", ");
		sb.append("note=").append(note);
		sb.append("]");
		return sb.toString();
	}

}
