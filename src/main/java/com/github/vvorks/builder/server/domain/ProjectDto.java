/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * プロジェクト型
 */
public class ProjectDto {

	private int projectId;

	private String projectName;

	private String title;

	private String description;

	private String note;

	private String copyrights;

	public int getProjectId() {
		return this.projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public String getCopyrights() {
		return this.copyrights;
	}

	public void setCopyrights(String copyrights) {
		this.copyrights = copyrights;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			projectId,
			projectName,
			title,
			description,
			note,
			copyrights
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
			return equals((ProjectDto) obj);
		}
	}

	private boolean equals(ProjectDto other) {
		return
			this.projectId == other.projectId &&
			Objects.equals(this.projectName, other.projectName) &&
			Objects.equals(this.title, other.title) &&
			Objects.equals(this.description, other.description) &&
			Objects.equals(this.note, other.note) &&
			Objects.equals(this.copyrights, other.copyrights)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ProjectDto [");
		sb.append("projectId=").append(projectId).append(", ");
		sb.append("projectName=").append(projectName).append(", ");
		sb.append("title=").append(title).append(", ");
		sb.append("description=").append(description).append(", ");
		sb.append("note=").append(note).append(", ");
		sb.append("copyrights=").append(copyrights);
		sb.append("]");
		return sb.toString();
	}

}
