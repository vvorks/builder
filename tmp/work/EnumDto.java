package com.github.vvorks.builder.server.domain;

import java.util.Objects;

public class EnumDto {

	private int enumId;

	private int ownerProjectId;

	private String enumName;

	private String title;

	private String description;

	private String note;

	public int getEnumId() {
		return enumId;
	}

	public void setEnumId(int enumId) {
		this.enumId = enumId;
	}

	public int getOwnerProjectId() {
		return ownerProjectId;
	}

	public void setOwnerProjectId(int ownerProjectId) {
		this.ownerProjectId = ownerProjectId;
	}

	public String getEnumName() {
		return enumName;
	}

	public void setEnumName(String enumName) {
		this.enumName = enumName;
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
		return Objects.hash(description, enumId, enumName, note, ownerProjectId, title);
	}

	private boolean equals(EnumDto other) {
		return	Objects.equals(description, other.description) &&
				enumId == other.enumId &&
				Objects.equals(enumName, other.enumName) &&
				Objects.equals(note, other.note) &&
				ownerProjectId == other.ownerProjectId &&
				Objects.equals(title, other.title);
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("EnumDto [");
		sb.append("enumId=").append(enumId);
		sb.append(", ownerProjectId=").append(ownerProjectId);
		sb.append(", enumName=").append(enumName);
		sb.append(", title=").append(title);
		sb.append(", description=").append(description);
		sb.append(", note=").append(note);
		sb.append("]");
		return sb.toString();
	}

}
