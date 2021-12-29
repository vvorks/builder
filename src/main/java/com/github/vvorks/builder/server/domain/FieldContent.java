/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * フィールド
 */
public class FieldContent {

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
	private String fieldName;

	/**
	 * フィールド型
	 */
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
	 * needsSum
	 */
	private boolean needsSum;

	/**
	 * needsAvg
	 */
	private boolean needsAvg;

	/**
	 * needsMax
	 */
	private boolean needsMax;

	/**
	 * needsMin
	 */
	private boolean needsMin;

	/**
	 * タイトル
	 */
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
	 * 最終更新時刻
	 */
	private java.util.Date _lastUpdatedAt;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 所属クラスのタイトル
	 */
	private String owner_title;

	/**
	 * クラス参照先のタイトル
	 */
	private String cref_title;

	/**
	 * 列挙参照先のタイトル
	 */
	private String eref_title;

	/**
	 * フィールド参照先のタイトル
	 */
	private String fref_title;

	/**
	 * フィールド型のタイトル
	 */
	private String type_title;

	/**
	 * 空のフィールドを作成する
	 */
	public FieldContent() {
	}

	/**
	 * 指定のキー項目を持つフィールドを作成する
	 *
	 * @param fieldId フィールドID
	 */
	public FieldContent(
		int fieldId
	) {
		this.fieldId = fieldId;
	}

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
	 * needsSumを取得する
	 *
	 * @return needsSum
	 */
	public boolean isNeedsSum() {
		return this.needsSum;
	}

	/**
	 * needsSumを設定する
	 *
	 * @param needsSum 設定するneedsSum
	 */
	public void setNeedsSum(boolean needsSum) {
		this.needsSum = needsSum;
	}

	/**
	 * needsAvgを取得する
	 *
	 * @return needsAvg
	 */
	public boolean isNeedsAvg() {
		return this.needsAvg;
	}

	/**
	 * needsAvgを設定する
	 *
	 * @param needsAvg 設定するneedsAvg
	 */
	public void setNeedsAvg(boolean needsAvg) {
		this.needsAvg = needsAvg;
	}

	/**
	 * needsMaxを取得する
	 *
	 * @return needsMax
	 */
	public boolean isNeedsMax() {
		return this.needsMax;
	}

	/**
	 * needsMaxを設定する
	 *
	 * @param needsMax 設定するneedsMax
	 */
	public void setNeedsMax(boolean needsMax) {
		this.needsMax = needsMax;
	}

	/**
	 * needsMinを取得する
	 *
	 * @return needsMin
	 */
	public boolean isNeedsMin() {
		return this.needsMin;
	}

	/**
	 * needsMinを設定する
	 *
	 * @param needsMin 設定するneedsMin
	 */
	public void setNeedsMin(boolean needsMin) {
		this.needsMin = needsMin;
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

	/**
	 * 最終更新時刻を取得する
	 *
	 * @return 最終更新時刻
	 */
	public java.util.Date get_lastUpdatedAt() {
		return this._lastUpdatedAt;
	}

	/**
	 * 最終更新時刻を設定する
	 *
	 * @param _lastUpdatedAt 設定する最終更新時刻
	 */
	public void set_lastUpdatedAt(java.util.Date _lastUpdatedAt) {
		this._lastUpdatedAt = _lastUpdatedAt;
	}

	/**
	 * タイトルを取得する
	 *
	 * @return タイトル
	 */
	public String get_title() {
		return this._title;
	}

	/**
	 * タイトルを設定する
	 *
	 * @param _title 設定するタイトル
	 */
	public void set_title(String _title) {
		this._title = _title;
	}

	/**
	 * 所属クラスのタイトルを取得する
	 *
	 * @return 所属クラスのタイトル
	 */
	public String getOwner_title() {
		return this.owner_title;
	}

	/**
	 * 所属クラスのタイトルを設定する
	 *
	 * @param owner_title 設定する所属クラスのタイトル
	 */
	public void setOwner_title(String owner_title) {
		this.owner_title = owner_title;
	}

	/**
	 * クラス参照先のタイトルを取得する
	 *
	 * @return クラス参照先のタイトル
	 */
	public String getCref_title() {
		return this.cref_title;
	}

	/**
	 * クラス参照先のタイトルを設定する
	 *
	 * @param cref_title 設定するクラス参照先のタイトル
	 */
	public void setCref_title(String cref_title) {
		this.cref_title = cref_title;
	}

	/**
	 * 列挙参照先のタイトルを取得する
	 *
	 * @return 列挙参照先のタイトル
	 */
	public String getEref_title() {
		return this.eref_title;
	}

	/**
	 * 列挙参照先のタイトルを設定する
	 *
	 * @param eref_title 設定する列挙参照先のタイトル
	 */
	public void setEref_title(String eref_title) {
		this.eref_title = eref_title;
	}

	/**
	 * フィールド参照先のタイトルを取得する
	 *
	 * @return フィールド参照先のタイトル
	 */
	public String getFref_title() {
		return this.fref_title;
	}

	/**
	 * フィールド参照先のタイトルを設定する
	 *
	 * @param fref_title 設定するフィールド参照先のタイトル
	 */
	public void setFref_title(String fref_title) {
		this.fref_title = fref_title;
	}

	/**
	 * フィールド型のタイトルを取得する
	 *
	 * @return フィールド型のタイトル
	 */
	public String getType_title() {
		return this.type_title;
	}

	/**
	 * フィールド型のタイトルを設定する
	 *
	 * @param type_title 設定するフィールド型のタイトル
	 */
	public void setType_title(String type_title) {
		this.type_title = type_title;
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
			needsSum,
			needsAvg,
			needsMax,
			needsMin,
			title,
			description,
			note,
			_lastUpdatedAt
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
			return equals((FieldContent) obj);
		}
	}

	private boolean equals(FieldContent other) {
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
			this.needsSum == other.needsSum && 
			this.needsAvg == other.needsAvg && 
			this.needsMax == other.needsMax && 
			this.needsMin == other.needsMin && 
			Objects.equals(this.title, other.title) && 
			Objects.equals(this.description, other.description) && 
			Objects.equals(this.note, other.note) && 
			Objects.equals(this._lastUpdatedAt, other._lastUpdatedAt)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FieldContent [");
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
		sb.append("needsSum=").append(needsSum).append(", ");
		sb.append("needsAvg=").append(needsAvg).append(", ");
		sb.append("needsMax=").append(needsMax).append(", ");
		sb.append("needsMin=").append(needsMin).append(", ");
		sb.append("title=").append(title).append(", ");
		sb.append("description=").append(description).append(", ");
		sb.append("note=").append(note).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
