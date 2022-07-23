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
	 * レイアウトId
	 */
	private int layoutId;

	/**
	 * 所属ページのページId
	 */
	private int ownerPageId;

	/**
	 * 親レイアウトのレイアウトId
	 */
	private Integer parentLayoutId;

	/**
	 * レイアウト種別
	 */
	private LayoutType layoutType;

	/**
	 * 対象フィールドのフィールドID
	 */
	private Integer targetFieldId;

	/**
	 * 対象メッセージのメッセージID
	 */
	private Integer constMessageId;

	/**
	 * 使用WidgetのウィジェットId
	 */
	private Integer widgetWidgetId;

	/**
	 * レイアウトパラメータ
	 *
	 * 定義値はWidget毎に異なる
	 */
	private String layoutParam;

	/**
	 * 使用スタイルのスタイルID
	 */
	private Integer styleStyleId;

	/**
	 * 左位置
	 */
	private String left;

	/**
	 * 上位置
	 */
	private String top;

	/**
	 * 右位置
	 */
	private String right;

	/**
	 * 下位置
	 */
	private String bottom;

	/**
	 * 幅
	 */
	private String width;

	/**
	 * 高さ
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
	 * 所属ページのタイトル
	 */
	private String owner_title;

	/**
	 * 親レイアウトのタイトル
	 */
	private String parent_title;

	/**
	 * 対象フィールドのタイトル
	 */
	private String target_title;

	/**
	 * 対象メッセージのタイトル
	 */
	private String const_title;

	/**
	 * 使用Widgetのタイトル
	 */
	private String widget_title;

	/**
	 * 使用スタイルのタイトル
	 */
	private String style_title;

	/**
	 * レイアウト種別のタイトル
	 */
	private String layoutType_title;

	/**
	 * 空のレイアウトを作成する
	 */
	public LayoutContent() {
	}

	/**
	 * 指定のキー項目を持つレイアウトを作成する
	 *
	 * @param layoutId レイアウトId
	 */
	public LayoutContent(
		int layoutId
	) {
		this.layoutId = layoutId;
	}

	/**
	 * レイアウトIdを取得する
	 *
	 * @return レイアウトId
	 */
	public int getLayoutId() {
		return this.layoutId;
	}

	/**
	 * レイアウトIdを設定する
	 *
	 * @param layoutId 設定するレイアウトId
	 */
	public void setLayoutId(int layoutId) {
		this.layoutId = layoutId;
	}

	/**
	 * 所属ページのページIdを取得する
	 *
	 * @return 所属ページのページId
	 */
	public int getOwnerPageId() {
		return this.ownerPageId;
	}

	/**
	 * 所属ページのページIdを設定する
	 *
	 * @param ownerPageId 設定する所属ページのページId
	 */
	public void setOwnerPageId(int ownerPageId) {
		this.ownerPageId = ownerPageId;
	}

	/**
	 * 親レイアウトのレイアウトIdを取得する
	 *
	 * @return 親レイアウトのレイアウトId
	 */
	public Integer getParentLayoutId() {
		return this.parentLayoutId;
	}

	/**
	 * 親レイアウトのレイアウトIdを設定する
	 *
	 * @param parentLayoutId 設定する親レイアウトのレイアウトId
	 */
	public void setParentLayoutId(Integer parentLayoutId) {
		this.parentLayoutId = parentLayoutId;
	}

	/**
	 * レイアウト種別を取得する
	 *
	 * @return レイアウト種別
	 */
	public LayoutType getLayoutType() {
		return this.layoutType;
	}

	/**
	 * レイアウト種別を設定する
	 *
	 * @param layoutType 設定するレイアウト種別
	 */
	public void setLayoutType(LayoutType layoutType) {
		this.layoutType = layoutType;
	}

	/**
	 * 対象フィールドのフィールドIDを取得する
	 *
	 * @return 対象フィールドのフィールドID
	 */
	public Integer getTargetFieldId() {
		return this.targetFieldId;
	}

	/**
	 * 対象フィールドのフィールドIDを設定する
	 *
	 * @param targetFieldId 設定する対象フィールドのフィールドID
	 */
	public void setTargetFieldId(Integer targetFieldId) {
		this.targetFieldId = targetFieldId;
	}

	/**
	 * 対象メッセージのメッセージIDを取得する
	 *
	 * @return 対象メッセージのメッセージID
	 */
	public Integer getConstMessageId() {
		return this.constMessageId;
	}

	/**
	 * 対象メッセージのメッセージIDを設定する
	 *
	 * @param constMessageId 設定する対象メッセージのメッセージID
	 */
	public void setConstMessageId(Integer constMessageId) {
		this.constMessageId = constMessageId;
	}

	/**
	 * 使用WidgetのウィジェットIdを取得する
	 *
	 * @return 使用WidgetのウィジェットId
	 */
	public Integer getWidgetWidgetId() {
		return this.widgetWidgetId;
	}

	/**
	 * 使用WidgetのウィジェットIdを設定する
	 *
	 * @param widgetWidgetId 設定する使用WidgetのウィジェットId
	 */
	public void setWidgetWidgetId(Integer widgetWidgetId) {
		this.widgetWidgetId = widgetWidgetId;
	}

	/**
	 * レイアウトパラメータを取得する
	 *
	 * @return レイアウトパラメータ
	 */
	public String getLayoutParam() {
		return this.layoutParam;
	}

	/**
	 * レイアウトパラメータを設定する
	 *
	 * @param layoutParam 設定するレイアウトパラメータ
	 */
	public void setLayoutParam(String layoutParam) {
		this.layoutParam = layoutParam;
	}

	/**
	 * 使用スタイルのスタイルIDを取得する
	 *
	 * @return 使用スタイルのスタイルID
	 */
	public Integer getStyleStyleId() {
		return this.styleStyleId;
	}

	/**
	 * 使用スタイルのスタイルIDを設定する
	 *
	 * @param styleStyleId 設定する使用スタイルのスタイルID
	 */
	public void setStyleStyleId(Integer styleStyleId) {
		this.styleStyleId = styleStyleId;
	}

	/**
	 * 左位置を取得する
	 *
	 * @return 左位置
	 */
	public String getLeft() {
		return this.left;
	}

	/**
	 * 左位置を設定する
	 *
	 * @param left 設定する左位置
	 */
	public void setLeft(String left) {
		this.left = left;
	}

	/**
	 * 上位置を取得する
	 *
	 * @return 上位置
	 */
	public String getTop() {
		return this.top;
	}

	/**
	 * 上位置を設定する
	 *
	 * @param top 設定する上位置
	 */
	public void setTop(String top) {
		this.top = top;
	}

	/**
	 * 右位置を取得する
	 *
	 * @return 右位置
	 */
	public String getRight() {
		return this.right;
	}

	/**
	 * 右位置を設定する
	 *
	 * @param right 設定する右位置
	 */
	public void setRight(String right) {
		this.right = right;
	}

	/**
	 * 下位置を取得する
	 *
	 * @return 下位置
	 */
	public String getBottom() {
		return this.bottom;
	}

	/**
	 * 下位置を設定する
	 *
	 * @param bottom 設定する下位置
	 */
	public void setBottom(String bottom) {
		this.bottom = bottom;
	}

	/**
	 * 幅を取得する
	 *
	 * @return 幅
	 */
	public String getWidth() {
		return this.width;
	}

	/**
	 * 幅を設定する
	 *
	 * @param width 設定する幅
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * 高さを取得する
	 *
	 * @return 高さ
	 */
	public String getHeight() {
		return this.height;
	}

	/**
	 * 高さを設定する
	 *
	 * @param height 設定する高さ
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
	 * 所属ページのタイトルを取得する
	 *
	 * @return 所属ページのタイトル
	 */
	public String getOwner_title() {
		return this.owner_title;
	}

	/**
	 * 所属ページのタイトルを設定する
	 *
	 * @param owner_title 設定する所属ページのタイトル
	 */
	public void setOwner_title(String owner_title) {
		this.owner_title = owner_title;
	}

	/**
	 * 親レイアウトのタイトルを取得する
	 *
	 * @return 親レイアウトのタイトル
	 */
	public String getParent_title() {
		return this.parent_title;
	}

	/**
	 * 親レイアウトのタイトルを設定する
	 *
	 * @param parent_title 設定する親レイアウトのタイトル
	 */
	public void setParent_title(String parent_title) {
		this.parent_title = parent_title;
	}

	/**
	 * 対象フィールドのタイトルを取得する
	 *
	 * @return 対象フィールドのタイトル
	 */
	public String getTarget_title() {
		return this.target_title;
	}

	/**
	 * 対象フィールドのタイトルを設定する
	 *
	 * @param target_title 設定する対象フィールドのタイトル
	 */
	public void setTarget_title(String target_title) {
		this.target_title = target_title;
	}

	/**
	 * 対象メッセージのタイトルを取得する
	 *
	 * @return 対象メッセージのタイトル
	 */
	public String getConst_title() {
		return this.const_title;
	}

	/**
	 * 対象メッセージのタイトルを設定する
	 *
	 * @param const_title 設定する対象メッセージのタイトル
	 */
	public void setConst_title(String const_title) {
		this.const_title = const_title;
	}

	/**
	 * 使用Widgetのタイトルを取得する
	 *
	 * @return 使用Widgetのタイトル
	 */
	public String getWidget_title() {
		return this.widget_title;
	}

	/**
	 * 使用Widgetのタイトルを設定する
	 *
	 * @param widget_title 設定する使用Widgetのタイトル
	 */
	public void setWidget_title(String widget_title) {
		this.widget_title = widget_title;
	}

	/**
	 * 使用スタイルのタイトルを取得する
	 *
	 * @return 使用スタイルのタイトル
	 */
	public String getStyle_title() {
		return this.style_title;
	}

	/**
	 * 使用スタイルのタイトルを設定する
	 *
	 * @param style_title 設定する使用スタイルのタイトル
	 */
	public void setStyle_title(String style_title) {
		this.style_title = style_title;
	}

	/**
	 * レイアウト種別のタイトルを取得する
	 *
	 * @return レイアウト種別のタイトル
	 */
	public String getLayoutType_title() {
		return this.layoutType_title;
	}

	/**
	 * レイアウト種別のタイトルを設定する
	 *
	 * @param layoutType_title 設定するレイアウト種別のタイトル
	 */
	public void setLayoutType_title(String layoutType_title) {
		this.layoutType_title = layoutType_title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			layoutId,
			ownerPageId,
			parentLayoutId,
			layoutType,
			targetFieldId,
			constMessageId,
			widgetWidgetId,
			layoutParam,
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
			this.ownerPageId == other.ownerPageId && 
			Objects.equals(this.parentLayoutId, other.parentLayoutId) && 
			Objects.equals(this.layoutType, other.layoutType) && 
			Objects.equals(this.targetFieldId, other.targetFieldId) && 
			Objects.equals(this.constMessageId, other.constMessageId) && 
			Objects.equals(this.widgetWidgetId, other.widgetWidgetId) && 
			Objects.equals(this.layoutParam, other.layoutParam) && 
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
		sb.append("ownerPageId=").append(ownerPageId).append(", ");
		sb.append("parentLayoutId=").append(parentLayoutId).append(", ");
		sb.append("layoutType=").append(layoutType).append(", ");
		sb.append("targetFieldId=").append(targetFieldId).append(", ");
		sb.append("constMessageId=").append(constMessageId).append(", ");
		sb.append("widgetWidgetId=").append(widgetWidgetId).append(", ");
		sb.append("layoutParam=").append(layoutParam).append(", ");
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
