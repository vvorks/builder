/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * フィールド
 */
public class FieldDto {

	/**
	 * フィールドID
	 *
	 * （代理キー）
	 */
	private int fieldId;

	/**
	 * 所属クラスのクラスID
	 */
	private int ownerClassId;

	/**
	 * フィールド名
	 */
	@NotNull
	private String fieldName;

	/**
	 * フィールド型
	 */
	@NotNull
	private DataType type;

	/**
	 * フィールド型の幅
	 */
	private int width;

	/**
	 * フィールド型精度
	 */
	private int scale;

	/**
	 * クラス参照先のクラスID
	 */
	private int crefClassId;

	/**
	 * 列挙参照先の列挙ID
	 */
	private int erefEnumId;

	/**
	 * フィールド参照先のフィールドID
	 */
	private int frefFieldId;

	/**
	 * プライマリキー
	 */
	private boolean pk;

	/**
	 * NULL許容
	 */
	private boolean nullable;

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
	 * フィールドIDを取得する
	 *
	 * @return フィールドID
	 */
	public int getFieldId() {
		return this.fieldId;
	}

	/**
	 * フィールドIDを設定する
	 *
	 * @param fieldId 設定するフィールドID
	 */
	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * 所属クラスのクラスIDを取得する
	 *
	 * @return 所属クラスのクラスID
	 */
	public int getOwnerClassId() {
		return this.ownerClassId;
	}

	/**
	 * 所属クラスのクラスIDを設定する
	 *
	 * @param ownerClassId 設定する所属クラスのクラスID
	 */
	public void setOwnerClassId(int ownerClassId) {
		this.ownerClassId = ownerClassId;
	}

	/**
	 * フィールド名を取得する
	 *
	 * @return フィールド名
	 */
	public String getFieldName() {
		return this.fieldName;
	}

	/**
	 * フィールド名を設定する
	 *
	 * @param fieldName 設定するフィールド名
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * フィールド型を取得する
	 *
	 * @return フィールド型
	 */
	public DataType getType() {
		return this.type;
	}

	/**
	 * フィールド型を設定する
	 *
	 * @param type 設定するフィールド型
	 */
	public void setType(DataType type) {
		this.type = type;
	}

	/**
	 * フィールド型の幅を取得する
	 *
	 * @return フィールド型の幅
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * フィールド型の幅を設定する
	 *
	 * @param width 設定するフィールド型の幅
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * フィールド型精度を取得する
	 *
	 * @return フィールド型精度
	 */
	public int getScale() {
		return this.scale;
	}

	/**
	 * フィールド型精度を設定する
	 *
	 * @param scale 設定するフィールド型精度
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}

	/**
	 * クラス参照先のクラスIDを取得する
	 *
	 * @return クラス参照先のクラスID
	 */
	public int getCrefClassId() {
		return this.crefClassId;
	}

	/**
	 * クラス参照先のクラスIDを設定する
	 *
	 * @param crefClassId 設定するクラス参照先のクラスID
	 */
	public void setCrefClassId(int crefClassId) {
		this.crefClassId = crefClassId;
	}

	/**
	 * 列挙参照先の列挙IDを取得する
	 *
	 * @return 列挙参照先の列挙ID
	 */
	public int getErefEnumId() {
		return this.erefEnumId;
	}

	/**
	 * 列挙参照先の列挙IDを設定する
	 *
	 * @param erefEnumId 設定する列挙参照先の列挙ID
	 */
	public void setErefEnumId(int erefEnumId) {
		this.erefEnumId = erefEnumId;
	}

	/**
	 * フィールド参照先のフィールドIDを取得する
	 *
	 * @return フィールド参照先のフィールドID
	 */
	public int getFrefFieldId() {
		return this.frefFieldId;
	}

	/**
	 * フィールド参照先のフィールドIDを設定する
	 *
	 * @param frefFieldId 設定するフィールド参照先のフィールドID
	 */
	public void setFrefFieldId(int frefFieldId) {
		this.frefFieldId = frefFieldId;
	}

	/**
	 * プライマリキーを取得する
	 *
	 * @return プライマリキー
	 */
	public boolean isPk() {
		return this.pk;
	}

	/**
	 * プライマリキーを設定する
	 *
	 * @param pk 設定するプライマリキー
	 */
	public void setPk(boolean pk) {
		this.pk = pk;
	}

	/**
	 * NULL許容を取得する
	 *
	 * @return NULL許容
	 */
	public boolean isNullable() {
		return this.nullable;
	}

	/**
	 * NULL許容を設定する
	 *
	 * @param nullable 設定するNULL許容
	 */
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
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
			nullable,
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
			this.width == other.width && 
			this.scale == other.scale && 
			this.crefClassId == other.crefClassId && 
			this.erefEnumId == other.erefEnumId && 
			this.frefFieldId == other.frefFieldId && 
			this.pk == other.pk && 
			this.nullable == other.nullable && 
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
		sb.append("nullable=").append(nullable).append(", ");
		sb.append("title=").append(title).append(", ");
		sb.append("description=").append(description).append(", ");
		sb.append("note=").append(note);
		sb.append("]");
		return sb.toString();
	}

}
