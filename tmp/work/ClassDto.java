package com.github.vvorks.builder.server.domain;

import java.util.Objects;

public class ClassDto {

	private int classId;

	private int ownerProjectId;

	private String className;

	private String title;

	private String description;

	private String note;

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getOwnerProjectId() {
		return ownerProjectId;
	}

	public void setOwnerProjectId(int ownerProjectId) {
		this.ownerProjectId = ownerProjectId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
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
		return Objects.hash(classId, className, description, note, ownerProjectId, title);
	}

	private boolean equals(ClassDto other) {
		return	classId == other.classId &&
				Objects.equals(className, other.className) &&
				Objects.equals(description, other.description) &&
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
			return equals((ClassDto) obj);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ClassDto [");
		sb.append("classId=").append(classId);
		sb.append(", ownerProjectId=").append(ownerProjectId);
		sb.append(", className=").append(className);
		sb.append(", title=").append(title);
		sb.append(", description=").append(description);
		sb.append(", note=").append(note);
		sb.append("]");
		return sb.toString();
	}


}
