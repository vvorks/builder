package com.github.vvorks.builder.server.domain;

import java.util.Objects;

public class FieldDto {

	private int fieldId;

	private int ownerClassId;

	private String fieldName;

	private DataType type;

	private int width;

	private int scale;

	private int crefClassId;

	private int erefEnumId;

	private int frefFieldId;

	private boolean pk;

	private String title;

	private String description;

	private String note;

	public int getFieldId() {
		return fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	public int getOwnerClassId() {
		return ownerClassId;
	}

	public void setOwnerClassId(int ownerClassId) {
		this.ownerClassId = ownerClassId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public DataType getType() {
		return type;
	}

	public void setType(DataType type) {
		this.type = type;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public int getCrefClassId() {
		return crefClassId;
	}

	public void setCrefClassId(int crefClassId) {
		this.crefClassId = crefClassId;
	}

	public int getErefEnumId() {
		return erefEnumId;
	}

	public void setErefEnumId(int erefEnumId) {
		this.erefEnumId = erefEnumId;
	}

	public int getFrefFieldId() {
		return frefFieldId;
	}

	public void setFrefFieldId(int frefFieldId) {
		this.frefFieldId = frefFieldId;
	}

	public boolean isPk() {
		return pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
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
		return Objects.hash(
				description,
				fieldId,
				fieldName,
				note,
				ownerClassId,
				pk,
				crefClassId,
				erefEnumId,
				frefFieldId,
				scale,
				title,
				type,
				width);
	}

	private boolean equals(FieldDto other) {
		return	Objects.equals(description, other.description) &&
				fieldId == other.fieldId &&
				Objects.equals(fieldName, other.fieldName) &&
				Objects.equals(note, other.note) &&
				ownerClassId == other.ownerClassId &&
				pk == other.pk &&
				crefClassId == other.crefClassId &&
				erefEnumId == other.erefEnumId &&
				frefFieldId == other.frefFieldId &&
				scale == other.scale &&
				Objects.equals(title, other.title) &&
				type == other.type &&
				width == other.width;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FieldDto [");
		sb.append("fieldId=").append(fieldId);
		sb.append(", ownerClassId=").append(ownerClassId);
		sb.append(", fieldName=").append(fieldName);
		sb.append(", type=").append(type);
		sb.append(", width=").append(width);
		sb.append(", scale=").append(scale);
		sb.append(", crefClassId=").append(crefClassId);
		sb.append(", erefEnumId=").append(erefEnumId);
		sb.append(", frefFieldId=").append(frefFieldId);
		sb.append(", pk=").append(pk);
		sb.append(", title=").append(title);
		sb.append(", description=").append(description);
		sb.append(", note=").append(note);
		sb.append("]");
		return sb.toString();
	}

}
