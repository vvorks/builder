package com.github.vvorks.builder.client.common.ui;

import java.util.Objects;
import java.util.function.Predicate;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.json.Jsonizable;
import com.github.vvorks.builder.common.lang.Asserts;
import com.github.vvorks.builder.common.lang.Copyable;

public class UiNewStyle implements Copyable<UiNewStyle>, Jsonizable {

	/** 条件：指定なし */
	public static final Predicate<UiNode> ALWAYS	= node -> true;

	/** 条件：ノード有効時 */
	public static final Predicate<UiNode> ENSABLE	= node -> node.isEnableAll();

	/** 条件：ノード無効時 */
	public static final Predicate<UiNode> DISABLE	= node -> !node.isEnableAll();

	/** 条件：ノード押下時 */
	public static final Predicate<UiNode> CLICKING	= node -> node.isClicking();

	/** 条件：ノードフォーカス時 */
	public static final Predicate<UiNode> FOCUS	= node -> node.isFocus() || node.hasFocus();

	/** オーナークラス */
	private final Class<?> owner;

	/** スタイル名 */
	private final String name;

	/** 基底スタイル */
	private UiNewStyle basedOn;

	/** 条件 */
	private Predicate<UiNode> condition;

	/** 前景色 */
	private Integer color;

	/** 背景色 */
	private Integer backgroundColor;

	/** 背景画像URL */
	private String backgroundImage;

	/** ボーダーサイズ(Length)（左） */
	private Length borderLeft;

	/** ボーダーサイズ(Length)（上） */
	private Length borderTop;

	/** ボーダーサイズ(Length)（右） */
	private Length borderRight;

	/** ボーダーサイズ(Length)（下） */
	private Length borderBottom;

	/** ボーダー色 */
	private Integer borderColor;

	/** ボーダー画像URL */
	private String borderImage;

	/** フォントサイズ(Length) */
	private Length fontSize;

	/** フォントの種類 */
	private String fontFamily;

	/** 行の高さ(Length) */
	private Length lineHeight;

	/** 行の揃え位置 */
	private String textAlign;

	/** 縦方向の揃え位置 */
	private String verticalAlign;

	private UiNewStyle(Class<?> owner, String name) {
		this.owner = owner;
		this.name = name;
	}

	public UiNewStyle(UiNewStyle src) {
		this.owner = src.owner;
		this.name = src.name;
		this.basedOn = src.basedOn;
		this.condition = src.condition;
		this.color = src.color;
		this.backgroundColor = src.backgroundColor;
		this.backgroundImage = src.backgroundImage;
		this.borderLeft = src.borderLeft;
		this.borderTop = src.borderTop;
		this.borderRight = src.borderRight;
		this.borderBottom = src.borderBottom;
		this.borderColor = src.borderColor;
		this.borderImage = src.borderImage;
		this.fontSize = src.fontSize;
		this.fontFamily = src.fontFamily;
		this.lineHeight = src.lineHeight;
		this.textAlign = src.textAlign;
		this.verticalAlign = src.verticalAlign;
	}

	public UiNewStyle copy() {
		return new UiNewStyle(this);
	}

	public String getCssClassName() {
		if (owner == null) {
			return name;
		}
		return owner.getName().replace('.', '-') + "-" + name;
	}

	public Class<?> getOwner() {
		return owner;
	}

	public String getName() {
		return name;
	}

	public UiNewStyle getBasedOn() {
		return basedOn;
	}

	public Predicate<UiNode> getCondition() {
		return condition;
	}

	public Integer getColor() {
		return color;
	}

	public Integer getBackgroundColor() {
		return backgroundColor;
	}

	public String getBackgroundImage() {
		return backgroundImage;
	}

	public Length getBorderLeft() {
		return borderLeft;
	}

	public Length getBorderTop() {
		return borderTop;
	}

	public Length getBorderRight() {
		return borderRight;
	}

	public Length getBorderBottom() {
		return borderBottom;
	}

	public Integer getBorderColor() {
		return borderColor;
	}

	public String getBorderImage() {
		return borderImage;
	}

	public Length getFontSize() {
		return fontSize;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public Length getLineHeight() {
		return lineHeight;
	}

	public String getTextAlign() {
		return textAlign;
	}

	public String getVerticalAlign() {
		return verticalAlign;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
				condition,
				color,
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
				verticalAlign);
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
			return equals((UiNewStyle) obj);
		}
	}

	private boolean equals(UiNewStyle other) {
		return	Objects.equals(this.basedOn, other.basedOn) &&
				Objects.equals(this.condition, other.condition) &&
				Objects.equals(this.color, other.color) &&
				Objects.equals(this.backgroundColor, other.backgroundColor) &&
				Objects.equals(this.backgroundImage, other.backgroundImage) &&
				Objects.equals(this.borderLeft, other.borderLeft) &&
				Objects.equals(this.borderTop, other.borderTop) &&
				Objects.equals(this.borderRight, other.borderRight) &&
				Objects.equals(this.borderBottom, other.borderBottom) &&
				Objects.equals(this.borderColor , other.borderColor) &&
				Objects.equals(this.borderImage, other.borderImage) &&
				Objects.equals(this.fontSize, other.fontSize) &&
				Objects.equals(this.fontFamily , other.fontFamily) &&
				Objects.equals(this.lineHeight , other.lineHeight) &&
				Objects.equals(this.textAlign , other.textAlign) &&
				Objects.equals(this.verticalAlign , other.verticalAlign);
	}

	@Override
	public Json toJson() {
		Json json = Json.createObject();
		json.setString("name", name);
		json.set("basedOn", Json.valueOf(basedOn));
		json.setDouble("color", toNumber(color));
		json.setDouble("backgroundColor", toNumber(backgroundColor));
		json.setString("backgroundImage", backgroundImage);
		json.set("borderLeft", Json.valueOf(borderLeft));
		json.set("borderTop", Json.valueOf(borderTop));
		json.set("borderRight", Json.valueOf(borderTop));
		json.set("borderBottom", Json.valueOf(borderBottom));
		json.setDouble("borderColor", toNumber(borderColor));
		json.setString("borderImage", borderImage);
		json.set("fontSize", Json.valueOf(fontSize));
		json.setString("fontFamily", fontFamily);
		json.set("fontSize", Json.valueOf(lineHeight));
		json.setString("textAlign", textAlign);
		json.setString("verticalAlign", verticalAlign);
		return json;
	}

	private double toNumber(Integer v) {
		return v == null ? Double.NaN : v.doubleValue();
	}

	public CssStyle getCssStyle() {
		return new CssStyle.Builder()
				.property("color", toCssColor(getColor()))
				.property("background-color", toCssColor(getBackgroundColor()))
				.property("background-image", getBackgroundImage())
				.property("border-left-width", getBorderLeft())
				.property("border-top-width", getBorderTop())
				.property("border-right-width", getBorderRight())
				.property("border-bottom-width", getBorderBottom())
				.property("border-color", toCssColor(getBorderColor()))
				.property("border-image", getBorderImage())
				.property("font-size", getFontSize())
				.property("font-family", getFontFamily())
				.property("line-height", getLineHeight())
				.build();
	}

	private String toCssColor(Integer color) {
		if (color == null) {
			return null;
		} else {
			return Colors.toCssColor(color);
		}
	}

	public static Builder newStyle(Class<?> owner, String name) {
		return new Builder(owner, name);
	}

	/**
	 * スタイルビルダー
	 */
	public static class Builder {

		private UiNewStyle style;

		public Builder(Class<?> owner, String name) {
			Asserts.requireNotNull(owner);
			Asserts.requireNotEmpty(name);
			style = new UiNewStyle(owner, name);
		}

		public Builder basedOn(UiNewStyle basedOn) {
			style.basedOn = basedOn;
			return this;
		}

		public Builder condition(Predicate<UiNode> condition) {
			if (condition == null) {
				condition = ALWAYS;
			}
			style.condition = condition;
			return this;
		}

		public Builder color(Integer color) {
			style.color = color;
			return this;
		}

		public Builder color(String color) {
			if (color == null || color.isEmpty()) {
				style.color = null;
			} else {
				style.color = Colors.parse(color);
			}
			return this;
		}

		public Builder backgroundColor(Integer backgroundColor) {
			style.backgroundColor = backgroundColor;
			return this;
		}

		public Builder backgroundColor(String backgroundColor) {
			if (backgroundColor == null || backgroundColor.isEmpty()) {
				style.backgroundColor = null;
			} else {
				style.backgroundColor = Colors.parse(backgroundColor);
			}
			return this;
		}

		public Builder backgroundImage(String backgroundImage) {
			style.backgroundImage = backgroundImage;
			return this;
		}

		public Builder borderWidth(String edge) {
			Length e = new Length(edge);
			return borderWidth(e, e, e, e);
		}

		public Builder borderWidth(String topBottom, String leftRight) {
			Length tb = new Length(topBottom);
			Length lr = new Length(leftRight);
			return borderWidth(tb, lr, tb, lr);
		}

		public Builder borderWidth(String top, String leftRight, String bottom) {
			Length t = new Length(top);
			Length lr = new Length(leftRight);
			Length b = new Length(bottom);
			return borderWidth(t, lr, b, lr);
		}

		public Builder borderWidth(String top, String right, String bottom, String left) {
			Length t = new Length(top);
			Length r = new Length(right);
			Length b = new Length(bottom);
			Length l = new Length(left);
			return borderWidth(t, r, b, l);
		}

		private Builder borderWidth(Length top, Length right, Length bottom, Length left) {
			style.borderTop = top;
			style.borderRight = right;
			style.borderBottom = bottom;
			style.borderLeft = left;
			return this;
		}

		public Builder borderColor(Integer borderColor) {
			style.borderColor = borderColor;
			return this;
		}

		public Builder borderColor(String borderColor) {
			if (borderColor == null || borderColor.isEmpty()) {
				style.borderColor = null;
			} else {
				style.borderColor = Colors.parse(borderColor);
			}
			return this;
		}

		public Builder borderImage(String borderImage) {
			style.borderImage = borderImage;
			return this;
		}

		public Builder font(String size) {
			style.fontSize = new Length(size);
			return this;
		}

		public Builder font(String family, String size) {
			style.fontFamily = family;
			style.fontSize = new Length(size);
			return this;
		}

		public Builder lineHeight(String lineHeight) {
			style.lineHeight = new Length(lineHeight);
			return this;
		}

		public Builder align(String textAlign, String verticalAlign) {
			style.textAlign = textAlign;
			style.verticalAlign = verticalAlign;
			return this;
		}

		public UiNewStyle build() {
			return style;
		}

	}

}
