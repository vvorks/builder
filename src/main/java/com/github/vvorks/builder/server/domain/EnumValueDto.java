/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * 列挙値
 */
public class EnumValueDto {

	private String valueId;

	private int ownerEnumId;

	private Integer valueNo;

	private String title;

	private String description;

	private String note;

	public String getValueId() {
		return this.valueId;
	}

	public void setValueId(String valueId) {
		this.valueId = valueId;
	}

	public int getOwnerEnumId() {
		return this.ownerEnumId;
	}

	public void setOwnerEnumId(int ownerEnumId) {
		this.ownerEnumId = ownerEnumId;
	}

	public Integer getValueNo() {
		return this.valueNo;
	}

	public void setValueNo(Integer valueNo) {
		this.valueNo = valueNo;
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
			valueId,
			ownerEnumId,
			valueNo,
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
			return equals((EnumValueDto) obj);
		}
	}

	private boolean equals(EnumValueDto other) {
		return
			Objects.equals(this.valueId, other.valueId) && 
			this.ownerEnumId == other.ownerEnumId && 
			Objects.equals(this.valueNo, other.valueNo) && 
			Objects.equals(this.title, other.title) && 
			Objects.equals(this.description, other.description) && 
			Objects.equals(this.note, other.note)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("EnumValueDto [");
		sb.append("valueId=").append(valueId).append(", ");
		sb.append("ownerEnumId=").append(ownerEnumId).append(", ");
		sb.append("valueNo=").append(valueNo).append(", ");
		sb.append("title=").append(title).append(", ");
		sb.append("description=").append(description).append(", ");
		sb.append("note=").append(note);
		sb.append("]");
		return sb.toString();
	}

}
