package com.github.vvorks.builder.server.domain;

import java.util.Objects;

public class EnumValueDto {

	private String valueId;

	private int ownerEnumId;

	private int valueNo;

	private String title;

	private String description;

	private String note;

	public String getValueId() {
		return valueId;
	}

	public void setValueId(String valueId) {
		this.valueId = valueId;
	}

	public int getOwnerEnumId() {
		return ownerEnumId;
	}

	public void setOwnerEnumId(int ownerEnumId) {
		this.ownerEnumId = ownerEnumId;
	}

	public int getValueNo() {
		return valueNo;
	}

	public void setValueNo(int valueNo) {
		this.valueNo = valueNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, note, ownerEnumId, title, valueId, valueNo);
	}

	private boolean equals(EnumValueDto other) {
		return	Objects.equals(description, other.description) &&
				Objects.equals(note, other.note) &&
				ownerEnumId == other.ownerEnumId &&
				Objects.equals(title, other.title) &&
				Objects.equals(valueId, other.valueId) &&
				valueNo == other.valueNo;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("EnumValueDto [");
		sb.append("valueId=").append(valueId);
		sb.append(", ownerEnumId=").append(ownerEnumId);
		sb.append(", valueNo=").append(valueNo);
		sb.append(", title=").append(title);
		sb.append(", description=").append(description);
		sb.append(", note=").append(note);
		sb.append("]");
		return sb.toString();
	}

}
