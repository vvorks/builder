/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.util.Objects;

/**
 * スタイル
 */
public class StyleContent {

	/**
	 * スタイルID
	 */
	private int styleId;

	/**
	 * 所属プロジェクトのプロジェクトID
	 */
	private int ownerProjectId;

	/**
	 * スタイル名
	 */
	private String styleName;

	/**
	 * 基底スタイルのスタイルID
	 */
	private Integer parentStyleId;

	/**
	 * 適用条件
	 */
	private StyleCondition cond;

	/**
	 * 適用条件パラメータ
	 */
	private String condParam;

	/**
	 * 前景色
	 */
	private String textColor;

	/**
	 * 背景色
	 */
	private String backgroundColor;

	/**
	 * 背景画像URL
	 */
	private String backgroundImage;

	/**
	 * ボーダーサイズ(Length)（左）
	 */
	private String borderLeft;

	/**
	 * ボーダーサイズ(Length)（上）
	 */
	private String borderTop;

	/**
	 * ボーダーサイズ(Length)（右）
	 */
	private String borderRight;

	/**
	 * ボーダーサイズ(Length)（下）
	 */
	private String borderBottom;

	/**
	 * ボーダー色
	 */
	private String borderColor;

	/**
	 * ボーダー画像URL
	 */
	private String borderImage;

	/**
	 * フォントサイズ(Length)
	 */
	private String fontSize;

	/**
	 * フォントの種類
	 */
	private String fontFamily;

	/**
	 * 行の高さ(Length)
	 */
	private String lineHeight;

	/**
	 * 行の揃え位置
	 */
	private String textAlign;

	/**
	 * 縦方向の揃え位置
	 */
	private String verticalAlign;

	/**
	 * 最終更新時刻
	 */
	private java.util.Date _lastUpdatedAt;

	/**
	 * タイトル
	 */
	private String _title;

	/**
	 * 所属プロジェクトのタイトル
	 */
	private String owner_title;

	/**
	 * 基底スタイルのタイトル
	 */
	private String parent_title;

	/**
	 * 適用条件のタイトル
	 */
	private String cond_title;

	/**
	 * 空のスタイルを作成する
	 */
	public StyleContent() {
	}

	/**
	 * 指定のキー項目を持つスタイルを作成する
	 *
	 * @param styleId スタイルID
	 */
	public StyleContent(
		int styleId
	) {
		this.styleId = styleId;
	}

	/**
	 * スタイルIDを取得する
	 *
	 * @return スタイルID
	 */
	public int getStyleId() {
		return this.styleId;
	}

	/**
	 * スタイルIDを設定する
	 *
	 * @param styleId 設定するスタイルID
	 */
	public void setStyleId(int styleId) {
		this.styleId = styleId;
	}

	/**
	 * 所属プロジェクトのプロジェクトIDを取得する
	 *
	 * @return 所属プロジェクトのプロジェクトID
	 */
	public int getOwnerProjectId() {
		return this.ownerProjectId;
	}

	/**
	 * 所属プロジェクトのプロジェクトIDを設定する
	 *
	 * @param ownerProjectId 設定する所属プロジェクトのプロジェクトID
	 */
	public void setOwnerProjectId(int ownerProjectId) {
		this.ownerProjectId = ownerProjectId;
	}

	/**
	 * スタイル名を取得する
	 *
	 * @return スタイル名
	 */
	public String getStyleName() {
		return this.styleName;
	}

	/**
	 * スタイル名を設定する
	 *
	 * @param styleName 設定するスタイル名
	 */
	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	/**
	 * 基底スタイルのスタイルIDを取得する
	 *
	 * @return 基底スタイルのスタイルID
	 */
	public Integer getParentStyleId() {
		return this.parentStyleId;
	}

	/**
	 * 基底スタイルのスタイルIDを設定する
	 *
	 * @param parentStyleId 設定する基底スタイルのスタイルID
	 */
	public void setParentStyleId(Integer parentStyleId) {
		this.parentStyleId = parentStyleId;
	}

	/**
	 * 適用条件を取得する
	 *
	 * @return 適用条件
	 */
	public StyleCondition getCond() {
		return this.cond;
	}

	/**
	 * 適用条件を設定する
	 *
	 * @param cond 設定する適用条件
	 */
	public void setCond(StyleCondition cond) {
		this.cond = cond;
	}

	/**
	 * 適用条件パラメータを取得する
	 *
	 * @return 適用条件パラメータ
	 */
	public String getCondParam() {
		return this.condParam;
	}

	/**
	 * 適用条件パラメータを設定する
	 *
	 * @param condParam 設定する適用条件パラメータ
	 */
	public void setCondParam(String condParam) {
		this.condParam = condParam;
	}

	/**
	 * 前景色を取得する
	 *
	 * @return 前景色
	 */
	public String getTextColor() {
		return this.textColor;
	}

	/**
	 * 前景色を設定する
	 *
	 * @param textColor 設定する前景色
	 */
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	/**
	 * 背景色を取得する
	 *
	 * @return 背景色
	 */
	public String getBackgroundColor() {
		return this.backgroundColor;
	}

	/**
	 * 背景色を設定する
	 *
	 * @param backgroundColor 設定する背景色
	 */
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * 背景画像URLを取得する
	 *
	 * @return 背景画像URL
	 */
	public String getBackgroundImage() {
		return this.backgroundImage;
	}

	/**
	 * 背景画像URLを設定する
	 *
	 * @param backgroundImage 設定する背景画像URL
	 */
	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	/**
	 * ボーダーサイズ(Length)（左）を取得する
	 *
	 * @return ボーダーサイズ(Length)（左）
	 */
	public String getBorderLeft() {
		return this.borderLeft;
	}

	/**
	 * ボーダーサイズ(Length)（左）を設定する
	 *
	 * @param borderLeft 設定するボーダーサイズ(Length)（左）
	 */
	public void setBorderLeft(String borderLeft) {
		this.borderLeft = borderLeft;
	}

	/**
	 * ボーダーサイズ(Length)（上）を取得する
	 *
	 * @return ボーダーサイズ(Length)（上）
	 */
	public String getBorderTop() {
		return this.borderTop;
	}

	/**
	 * ボーダーサイズ(Length)（上）を設定する
	 *
	 * @param borderTop 設定するボーダーサイズ(Length)（上）
	 */
	public void setBorderTop(String borderTop) {
		this.borderTop = borderTop;
	}

	/**
	 * ボーダーサイズ(Length)（右）を取得する
	 *
	 * @return ボーダーサイズ(Length)（右）
	 */
	public String getBorderRight() {
		return this.borderRight;
	}

	/**
	 * ボーダーサイズ(Length)（右）を設定する
	 *
	 * @param borderRight 設定するボーダーサイズ(Length)（右）
	 */
	public void setBorderRight(String borderRight) {
		this.borderRight = borderRight;
	}

	/**
	 * ボーダーサイズ(Length)（下）を取得する
	 *
	 * @return ボーダーサイズ(Length)（下）
	 */
	public String getBorderBottom() {
		return this.borderBottom;
	}

	/**
	 * ボーダーサイズ(Length)（下）を設定する
	 *
	 * @param borderBottom 設定するボーダーサイズ(Length)（下）
	 */
	public void setBorderBottom(String borderBottom) {
		this.borderBottom = borderBottom;
	}

	/**
	 * ボーダー色を取得する
	 *
	 * @return ボーダー色
	 */
	public String getBorderColor() {
		return this.borderColor;
	}

	/**
	 * ボーダー色を設定する
	 *
	 * @param borderColor 設定するボーダー色
	 */
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	/**
	 * ボーダー画像URLを取得する
	 *
	 * @return ボーダー画像URL
	 */
	public String getBorderImage() {
		return this.borderImage;
	}

	/**
	 * ボーダー画像URLを設定する
	 *
	 * @param borderImage 設定するボーダー画像URL
	 */
	public void setBorderImage(String borderImage) {
		this.borderImage = borderImage;
	}

	/**
	 * フォントサイズ(Length)を取得する
	 *
	 * @return フォントサイズ(Length)
	 */
	public String getFontSize() {
		return this.fontSize;
	}

	/**
	 * フォントサイズ(Length)を設定する
	 *
	 * @param fontSize 設定するフォントサイズ(Length)
	 */
	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * フォントの種類を取得する
	 *
	 * @return フォントの種類
	 */
	public String getFontFamily() {
		return this.fontFamily;
	}

	/**
	 * フォントの種類を設定する
	 *
	 * @param fontFamily 設定するフォントの種類
	 */
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	/**
	 * 行の高さ(Length)を取得する
	 *
	 * @return 行の高さ(Length)
	 */
	public String getLineHeight() {
		return this.lineHeight;
	}

	/**
	 * 行の高さ(Length)を設定する
	 *
	 * @param lineHeight 設定する行の高さ(Length)
	 */
	public void setLineHeight(String lineHeight) {
		this.lineHeight = lineHeight;
	}

	/**
	 * 行の揃え位置を取得する
	 *
	 * @return 行の揃え位置
	 */
	public String getTextAlign() {
		return this.textAlign;
	}

	/**
	 * 行の揃え位置を設定する
	 *
	 * @param textAlign 設定する行の揃え位置
	 */
	public void setTextAlign(String textAlign) {
		this.textAlign = textAlign;
	}

	/**
	 * 縦方向の揃え位置を取得する
	 *
	 * @return 縦方向の揃え位置
	 */
	public String getVerticalAlign() {
		return this.verticalAlign;
	}

	/**
	 * 縦方向の揃え位置を設定する
	 *
	 * @param verticalAlign 設定する縦方向の揃え位置
	 */
	public void setVerticalAlign(String verticalAlign) {
		this.verticalAlign = verticalAlign;
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
	 * 所属プロジェクトのタイトルを取得する
	 *
	 * @return 所属プロジェクトのタイトル
	 */
	public String getOwner_title() {
		return this.owner_title;
	}

	/**
	 * 所属プロジェクトのタイトルを設定する
	 *
	 * @param owner_title 設定する所属プロジェクトのタイトル
	 */
	public void setOwner_title(String owner_title) {
		this.owner_title = owner_title;
	}

	/**
	 * 基底スタイルのタイトルを取得する
	 *
	 * @return 基底スタイルのタイトル
	 */
	public String getParent_title() {
		return this.parent_title;
	}

	/**
	 * 基底スタイルのタイトルを設定する
	 *
	 * @param parent_title 設定する基底スタイルのタイトル
	 */
	public void setParent_title(String parent_title) {
		this.parent_title = parent_title;
	}

	/**
	 * 適用条件のタイトルを取得する
	 *
	 * @return 適用条件のタイトル
	 */
	public String getCond_title() {
		return this.cond_title;
	}

	/**
	 * 適用条件のタイトルを設定する
	 *
	 * @param cond_title 設定する適用条件のタイトル
	 */
	public void setCond_title(String cond_title) {
		this.cond_title = cond_title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			styleId,
			ownerProjectId,
			styleName,
			parentStyleId,
			cond,
			condParam,
			textColor,
			backgroundColor,
			backgroundImage,
			borderLeft,
			borderTop,
			borderRight,
			borderBottom,
			borderColor,
			borderImage,
			fontSize,
			fontFamily,
			lineHeight,
			textAlign,
			verticalAlign,
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
			return equals((StyleContent) obj);
		}
	}

	private boolean equals(StyleContent other) {
		return
			this.styleId == other.styleId && 
			this.ownerProjectId == other.ownerProjectId && 
			Objects.equals(this.styleName, other.styleName) && 
			Objects.equals(this.parentStyleId, other.parentStyleId) && 
			Objects.equals(this.cond, other.cond) && 
			Objects.equals(this.condParam, other.condParam) && 
			Objects.equals(this.textColor, other.textColor) && 
			Objects.equals(this.backgroundColor, other.backgroundColor) && 
			Objects.equals(this.backgroundImage, other.backgroundImage) && 
			Objects.equals(this.borderLeft, other.borderLeft) && 
			Objects.equals(this.borderTop, other.borderTop) && 
			Objects.equals(this.borderRight, other.borderRight) && 
			Objects.equals(this.borderBottom, other.borderBottom) && 
			Objects.equals(this.borderColor, other.borderColor) && 
			Objects.equals(this.borderImage, other.borderImage) && 
			Objects.equals(this.fontSize, other.fontSize) && 
			Objects.equals(this.fontFamily, other.fontFamily) && 
			Objects.equals(this.lineHeight, other.lineHeight) && 
			Objects.equals(this.textAlign, other.textAlign) && 
			Objects.equals(this.verticalAlign, other.verticalAlign) && 
			Objects.equals(this._lastUpdatedAt, other._lastUpdatedAt)
			;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("StyleContent [");
		sb.append("styleId=").append(styleId).append(", ");
		sb.append("ownerProjectId=").append(ownerProjectId).append(", ");
		sb.append("styleName=").append(styleName).append(", ");
		sb.append("parentStyleId=").append(parentStyleId).append(", ");
		sb.append("cond=").append(cond).append(", ");
		sb.append("condParam=").append(condParam).append(", ");
		sb.append("textColor=").append(textColor).append(", ");
		sb.append("backgroundColor=").append(backgroundColor).append(", ");
		sb.append("backgroundImage=").append(backgroundImage).append(", ");
		sb.append("borderLeft=").append(borderLeft).append(", ");
		sb.append("borderTop=").append(borderTop).append(", ");
		sb.append("borderRight=").append(borderRight).append(", ");
		sb.append("borderBottom=").append(borderBottom).append(", ");
		sb.append("borderColor=").append(borderColor).append(", ");
		sb.append("borderImage=").append(borderImage).append(", ");
		sb.append("fontSize=").append(fontSize).append(", ");
		sb.append("fontFamily=").append(fontFamily).append(", ");
		sb.append("lineHeight=").append(lineHeight).append(", ");
		sb.append("textAlign=").append(textAlign).append(", ");
		sb.append("verticalAlign=").append(verticalAlign).append(", ");
		sb.append("_lastUpdatedAt=").append(_lastUpdatedAt);
		sb.append("]");
		return sb.toString();
	}

}
