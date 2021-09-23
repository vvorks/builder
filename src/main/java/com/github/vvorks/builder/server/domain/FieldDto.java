/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * フィールド
 */
public class FieldDto {

	private int fieldId;

	private int ownerClassId;

	private String fieldName;

	private DataType type;

	private Integer width;

	private Integer scale;

	private int crefClassId;

	private int erefEnumId;

	private int frefFieldId;

	private Boolean pk;

	private String title;

	private String description;

	private String note;

	public int getFieldId() {
		return this.fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	public int getOwnerClassId() {
		return this.ownerClassId;
	}

	public void setOwnerClassId(int ownerClassId) {
		this.ownerClassId = ownerClassId;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public DataType getType() {
		return this.type;
	}

	public void setType(DataType type) {
		this.type = type;
	}

	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getScale() {
		return this.scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	public int getCrefClassId() {
		return this.crefClassId;
	}

	public void setCrefClassId(int crefClassId) {
		this.crefClassId = crefClassId;
	}

	public int getErefEnumId() {
		return this.erefEnumId;
	}

	public void setErefEnumId(int erefEnumId) {
		this.erefEnumId = erefEnumId;
	}

	public int getFrefFieldId() {
		return this.frefFieldId;
	}

	public void setFrefFieldId(int frefFieldId) {
		this.frefFieldId = frefFieldId;
	}

	public Boolean isPk() {
		return this.pk;
	}

	public void setPk(Boolean pk) {
		this.pk = pk;
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
			fieldId,
			ownerClassId,
			fieldName,
			type,
			width,
			scale,
			crefClassId,
			erefEnumId,
			frefFieldId,
			pk,
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
			return equals((FieldDto) obj);
		}
	}

	private boolean equals(FieldDto other) {
		return
			this.fieldId == other.fieldId && 
			this.ownerClassId == other.ownerClassId && 
			Objects.equals(this.fieldName, other.fieldName) && 
			Objects.equals(this.type, other.type) && 
			Objects.equals(this.width, other.width) && 
			Objects.equals(this.scale, other.scale) && 
			this.crefClassId == other.crefClassId && 
			this.erefEnumId == other.erefEnumId && 
			this.frefFieldId == other.frefFieldId && 
			Objects.equals(this.pk, other.pk) && 
			Objects.equals(this.title, other.title) && 
			Objects.equals(this.description, other.description) && 
			Objects.equals(this.note, other.note)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FieldDto [");
		sb.append("fieldId=").append(fieldId).append(", ");
		sb.append("ownerClassId=").append(ownerClassId).append(", ");
		sb.append("fieldName=").append(fieldName).append(", ");
		sb.append("type=").append(type).append(", ");
		sb.append("width=").append(width).append(", ");
		sb.append("scale=").append(scale).append(", ");
		sb.append("crefClassId=").append(crefClassId).append(", ");
		sb.append("erefEnumId=").append(erefEnumId).append(", ");
		sb.append("frefFieldId=").append(frefFieldId).append(", ");
		sb.append("pk=").append(pk).append(", ");
		sb.append("title=").append(title).append(", ");
		sb.append("description=").append(description).append(", ");
		sb.append("note=").append(note);
		sb.append("]");
		return sb.toString();
	}

}
