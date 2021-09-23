package com.github.vvorks.builder.server.domain;

import java.util.Objects;

public class ProjectDto {

	private int projectId;

	private String projectName;

	private String title;

	private String description;

	private String note;

	private String copyrights;

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public String getCopyrights() {
		return copyrights;
	}

	public void setCopyrights(String copyrights) {
		this.copyrights = copyrights;
	}


	@Override
	public int hashCode() {
		return Objects.hash(copyrights, description, note, projectId, projectName, title);
	}

	private boolean equals(ProjectDto other) {
		return	Objects.equals(copyrights, other.copyrights) &&
				Objects.equals(description, other.description) &&
				Objects.equals(note, other.note) &&
				projectId == other.projectId &&
				Objects.equals(projectName, other.projectName) &&
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
			return equals((ProjectDto) obj);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ProjectDto [");
		sb.append("projectId=").append(projectId);
		sb.append(", projectName=").append(projectName);
		sb.append(", title=").append(title);
		sb.append(", description=").append(description);
		sb.append(", note=").append(note);
		sb.append(", copyrights=").append(copyrights);
		sb.append("]");
		return sb.toString();
	}

}
