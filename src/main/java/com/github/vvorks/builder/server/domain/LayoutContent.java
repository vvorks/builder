/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * レイアウト
 */
public class LayoutContent {

	/**
	 * layoutId
	 */
	private int layoutId;

	/**
	 * ownerのvariantId
	 */
	private int ownerVariantId;

	/**
	 * name
	 */
	private String name;

	/**
	 * parentのlayoutId
	 */
	private Integer parentLayoutId;

	/**
	 * targetType
	 */
	private LayoutType targetType;

	/**
	 * targetFieldのフィールドID
	 */
	private Integer targetFieldFieldId;

	/**
	 * targetMessageのメッセージID
	 */
	private Integer targetMessageMessageId;

	/**
	 * styleのスタイルID
	 */
	private Integer styleStyleId;

	/**
	 * left
	 */
	private String left;

	/**
	 * top
	 */
	private String top;

	/**
	 * right
	 */
	private String right;

	/**
	 * bottom
	 */
	private String bottom;

	/**
	 * width
	 */
	private String width;

	/**
	 * height
	 */
	private String height;

	/**
	 * 最終更新時刻
	 */
	private java.util.Date _lastUpdatedAt;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * ownerのタイトル
	 */
	private String owner_title;

	/**
	 * parentのタイトル
	 */
	private String parent_title;

	/**
	 * targetFieldのタイトル
	 */
	private String targetField_title;

	/**
	 * targetMessageのタイトル
	 */
	private String targetMessage_title;

	/**
	 * styleのタイトル
	 */
	private String style_title;

	/**
	 * targetTypeのタイトル
	 */
	private String targetType_title;

	/**
	 * 空のレイアウトを作成する
	 */
	public LayoutContent() {
	}

	/**
	 * 指定のキー項目を持つレイアウトを作成する
	 *
	 * @param layoutId layoutId
	 */
	public LayoutContent(
		int layoutId
	) {
		this.layoutId = layoutId;
	}

	/**
	 * layoutIdを取得する
	 *
	 * @return layoutId
	 */
	public int getLayoutId() {
		return this.layoutId;
	}

	/**
	 * layoutIdを設定する
	 *
	 * @param layoutId 設定するlayoutId
	 */
	public void setLayoutId(int layoutId) {
		this.layoutId = layoutId;
	}

	/**
	 * ownerのvariantIdを取得する
	 *
	 * @return ownerのvariantId
	 */
	public int getOwnerVariantId() {
		return this.ownerVariantId;
	}

	/**
	 * ownerのvariantIdを設定する
	 *
	 * @param ownerVariantId 設定するownerのvariantId
	 */
	public void setOwnerVariantId(int ownerVariantId) {
		this.ownerVariantId = ownerVariantId;
	}

	/**
	 * nameを取得する
	 *
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * nameを設定する
	 *
	 * @param name 設定するname
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * parentのlayoutIdを取得する
	 *
	 * @return parentのlayoutId
	 */
	public Integer getParentLayoutId() {
		return this.parentLayoutId;
	}

	/**
	 * parentのlayoutIdを設定する
	 *
	 * @param parentLayoutId 設定するparentのlayoutId
	 */
	public void setParentLayoutId(Integer parentLayoutId) {
		this.parentLayoutId = parentLayoutId;
	}

	/**
	 * targetTypeを取得する
	 *
	 * @return targetType
	 */
	public LayoutType getTargetType() {
		return this.targetType;
	}

	/**
	 * targetTypeを設定する
	 *
	 * @param targetType 設定するtargetType
	 */
	public void setTargetType(LayoutType targetType) {
		this.targetType = targetType;
	}

	/**
	 * targetFieldのフィールドIDを取得する
	 *
	 * @return targetFieldのフィールドID
	 */
	public Integer getTargetFieldFieldId() {
		return this.targetFieldFieldId;
	}

	/**
	 * targetFieldのフィールドIDを設定する
	 *
	 * @param targetFieldFieldId 設定するtargetFieldのフィールドID
	 */
	public void setTargetFieldFieldId(Integer targetFieldFieldId) {
		this.targetFieldFieldId = targetFieldFieldId;
	}

	/**
	 * targetMessageのメッセージIDを取得する
	 *
	 * @return targetMessageのメッセージID
	 */
	public Integer getTargetMessageMessageId() {
		return this.targetMessageMessageId;
	}

	/**
	 * targetMessageのメッセージIDを設定する
	 *
	 * @param targetMessageMessageId 設定するtargetMessageのメッセージID
	 */
	public void setTargetMessageMessageId(Integer targetMessageMessageId) {
		this.targetMessageMessageId = targetMessageMessageId;
	}

	/**
	 * styleのスタイルIDを取得する
	 *
	 * @return styleのスタイルID
	 */
	public Integer getStyleStyleId() {
		return this.styleStyleId;
	}

	/**
	 * styleのスタイルIDを設定する
	 *
	 * @param styleStyleId 設定するstyleのスタイルID
	 */
	public void setStyleStyleId(Integer styleStyleId) {
		this.styleStyleId = styleStyleId;
	}

	/**
	 * leftを取得する
	 *
	 * @return left
	 */
	public String getLeft() {
		return this.left;
	}

	/**
	 * leftを設定する
	 *
	 * @param left 設定するleft
	 */
	public void setLeft(String left) {
		this.left = left;
	}

	/**
	 * topを取得する
	 *
	 * @return top
	 */
	public String getTop() {
		return this.top;
	}

	/**
	 * topを設定する
	 *
	 * @param top 設定するtop
	 */
	public void setTop(String top) {
		this.top = top;
	}

	/**
	 * rightを取得する
	 *
	 * @return right
	 */
	public String getRight() {
		return this.right;
	}

	/**
	 * rightを設定する
	 *
	 * @param right 設定するright
	 */
	public void setRight(String right) {
		this.right = right;
	}

	/**
	 * bottomを取得する
	 *
	 * @return bottom
	 */
	public String getBottom() {
		return this.bottom;
	}

	/**
	 * bottomを設定する
	 *
	 * @param bottom 設定するbottom
	 */
	public void setBottom(String bottom) {
		this.bottom = bottom;
	}

	/**
	 * widthを取得する
	 *
	 * @return width
	 */
	public String getWidth() {
		return this.width;
	}

	/**
	 * widthを設定する
	 *
	 * @param width 設定するwidth
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * heightを取得する
	 *
	 * @return height
	 */
	public String getHeight() {
		return this.height;
	}

	/**
	 * heightを設定する
	 *
	 * @param height 設定するheight
	 */
	public void setHeight(String height) {
		this.height = height;
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
	 * ownerのタイトルを取得する
	 *
	 * @return ownerのタイトル
	 */
	public String getOwner_title() {
		return this.owner_title;
	}

	/**
	 * ownerのタイトルを設定する
	 *
	 * @param owner_title 設定するownerのタイトル
	 */
	public void setOwner_title(String owner_title) {
		this.owner_title = owner_title;
	}

	/**
	 * parentのタイトルを取得する
	 *
	 * @return parentのタイトル
	 */
	public String getParent_title() {
		return this.parent_title;
	}

	/**
	 * parentのタイトルを設定する
	 *
	 * @param parent_title 設定するparentのタイトル
	 */
	public void setParent_title(String parent_title) {
		this.parent_title = parent_title;
	}

	/**
	 * targetFieldのタイトルを取得する
	 *
	 * @return targetFieldのタイトル
	 */
	public String getTargetField_title() {
		return this.targetField_title;
	}

	/**
	 * targetFieldのタイトルを設定する
	 *
	 * @param targetField_title 設定するtargetFieldのタイトル
	 */
	public void setTargetField_title(String targetField_title) {
		this.targetField_title = targetField_title;
	}

	/**
	 * targetMessageのタイトルを取得する
	 *
	 * @return targetMessageのタイトル
	 */
	public String getTargetMessage_title() {
		return this.targetMessage_title;
	}

	/**
	 * targetMessageのタイトルを設定する
	 *
	 * @param targetMessage_title 設定するtargetMessageのタイトル
	 */
	public void setTargetMessage_title(String targetMessage_title) {
		this.targetMessage_title = targetMessage_title;
	}

	/**
	 * styleのタイトルを取得する
	 *
	 * @return styleのタイトル
	 */
	public String getStyle_title() {
		return this.style_title;
	}

	/**
	 * styleのタイトルを設定する
	 *
	 * @param style_title 設定するstyleのタイトル
	 */
	public void setStyle_title(String style_title) {
		this.style_title = style_title;
	}

	/**
	 * targetTypeのタイトルを取得する
	 *
	 * @return targetTypeのタイトル
	 */
	public String getTargetType_title() {
		return this.targetType_title;
	}

	/**
	 * targetTypeのタイトルを設定する
	 *
	 * @param targetType_title 設定するtargetTypeのタイトル
	 */
	public void setTargetType_title(String targetType_title) {
		this.targetType_title = targetType_title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			layoutId,
			ownerVariantId,
			name,
			parentLayoutId,
			targetType,
			targetFieldFieldId,
			targetMessageMessageId,
			styleStyleId,
			left,
			top,
			right,
			bottom,
			width,
			height,
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
			return equals((LayoutContent) obj);
		}
	}

	private boolean equals(LayoutContent other) {
		return
			this.layoutId == other.layoutId && 
			this.ownerVariantId == other.ownerVariantId && 
			Objects.equals(this.name, other.name) && 
			Objects.equals(this.parentLayoutId, other.parentLayoutId) && 
			Objects.equals(this.targetType, other.targetType) && 
			Objects.equals(this.targetFieldFieldId, other.targetFieldFieldId) && 
			Objects.equals(this.targetMessageMessageId, other.targetMessageMessageId) && 
			Objects.equals(this.styleStyleId, other.styleStyleId) && 
			Objects.equals(this.left, other.left) && 
			Objects.equals(this.top, other.top) && 
			Objects.equals(this.right, other.right) && 
			Objects.equals(this.bottom, other.bottom) && 
			Objects.equals(this.width, other.width) && 
			Objects.equals(this.height, other.height) && 
			Objects.equals(this._lastUpdatedAt, other._lastUpdatedAt)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("LayoutContent [");
		sb.append("layoutId=").append(layoutId).append(", ");
		sb.append("ownerVariantId=").append(ownerVariantId).append(", ");
		sb.append("name=").append(name).append(", ");
		sb.append("parentLayoutId=").append(parentLayoutId).append(", ");
		sb.append("targetType=").append(targetType).append(", ");
		sb.append("targetFieldFieldId=").append(targetFieldFieldId).append(", ");
		sb.append("targetMessageMessageId=").append(targetMessageMessageId).append(", ");
		sb.append("styleStyleId=").append(styleStyleId).append(", ");
		sb.append("left=").append(left).append(", ");
		sb.append("top=").append(top).append(", ");
		sb.append("right=").append(right).append(", ");
		sb.append("bottom=").append(bottom).append(", ");
		sb.append("width=").append(width).append(", ");
		sb.append("height=").append(height).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
