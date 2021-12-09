package com.github.vvorks.builder.client.common.ui;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import com.github.vvorks.builder.common.json.Json;

public class UiAtomicStyle extends UiStyle {

	public static final String TEXT_ALIGN_CENTER = "center";
	public static final String TEXT_ALIGN_JUSTIFY = "justify";
	public static final String TEXT_ALIGN_LEFT = "left";
	public static final String TEXT_ALIGN_RIGHT = "right";

	public static final String VERTICAL_ALIGN_TOP = "top";
	public static final String VERTICAL_ALIGN_MIDDLE = "middle";
	public static final String VERTICAL_ALIGN_BOTTOM = "bottom";

	private static final String DEFAULT_FONT_FAMILY = "sans-serif";
	private static final Length DEFAULT_FONT_SIZE = new Length("12pt");

	/** 基底スタイル */
	private final UiAtomicStyle baseStyle;

	/** 前景色 */
	private Integer color;

	/** 背景色 */
	private Integer backgroundColor;

	/** 背景画像 */
	private String backgroundImage;

	/** ボーダーサイズ（左） */
	private Length borderLeft;

	/** ボーダーサイズ（上） */
	private Length borderTop;

	/** ボーダーサイズ（右） */
	private Length borderRight;

	/** ボーダーサイズ（下） */
	private Length borderBottom;

	/** ボーダーの色 */
	private Integer borderColor;

	/** ボーダーイメージ */
	private String borderImage;

	/** フォントサイズ */
	private Length fontSize;

	/** フォントの種類 */
	private String fontFamily;

	/** 行の高さ */
	private Length lineHeight;

	/** 行の揃え位置・均等割付 */
	private String textAlign;

	/** 縦方向の揃え位置 */
	private String verticalAlign;

	/** 数値／日付・時刻フォーマットパターン */
	private String format;

	public UiAtomicStyle(Class<?> owner, String name, UiAtomicStyle baseStyle) {
		super(owner, name);
		this.baseStyle = baseStyle;
	}

	protected UiAtomicStyle(UiAtomicStyle src) {
		super(src);
		this.baseStyle = src.baseStyle;
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
		this.format = src.format;
	}

	public UiAtomicStyle copy() {
		return new UiAtomicStyle(this);
	}

	@Override
	public UiAtomicStyle getAtomicStyleOf(UiNode node) {
		return this;
	}

	@Override
	public void getAtomicStyles(Map<String, UiAtomicStyle> map) {
		map.put(this.getName(), this);
	}

	public UiAtomicStyle getBaseStyle() {
		return baseStyle;
	}

	private <R> R getProperty(Function<UiAtomicStyle, R> func, R defaultValue) {
		UiAtomicStyle s = this;
		while (s != null) {
			R result = func.apply(s);
			if (result != null) {
				return result;
			}
			s = s.getBaseStyle();
		}
		return defaultValue;
	}

	public Integer getColor() {
		return getProperty(s -> s.color, Colors.BLACK);
	}

	public Integer getBackgroundColor() {
		return getProperty(s -> s.backgroundColor, Colors.WHITE);
	}

	public String getBackgroundImage() {
		return getProperty(s -> s.backgroundImage, null);
	}

	public Length getBorderLeft() {
		return getProperty(s -> s.borderLeft, Length.ZERO);
	}

	public Length getBorderTop() {
		return getProperty(s -> s.borderTop, Length.ZERO);
	}

	public Length getBorderRight() {
		return getProperty(s -> s.borderRight, Length.ZERO);
	}

	public Length getBorderBottom() {
		return getProperty(s -> s.borderBottom, Length.ZERO);
	}

	public Integer getBorderColor() {
		return getProperty(s -> s.borderColor, Colors.BLACK);
	}

	public String getBorderImage() {
		return getProperty(s -> s.borderImage, null);
	}

	public String getFontFamily() {
		return getProperty(s -> s.fontFamily, DEFAULT_FONT_FAMILY);
	}

	public Length getFontSize() {
		return getProperty(s -> s.fontSize, DEFAULT_FONT_SIZE);
	}

	public Length getLineHeight() {
		Length prop = getProperty(s -> s.lineHeight, null);
		return (prop != null) ? prop : getFontSize();
	}

	public String getTextAlign() {
		return getProperty(s -> s.textAlign, UiAtomicStyle.TEXT_ALIGN_JUSTIFY);
	}

	public String getVerticalAlign() {
		return getProperty(s -> s.verticalAlign, UiAtomicStyle.VERTICAL_ALIGN_TOP);
	}

	public String getFormat() {
		return getProperty(s -> s.format, null);
	}

	@Override
	public int hashCode() {
		return Objects.hash(
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
				verticalAlign,
				format);
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
			return equals((UiAtomicStyle) obj);
		}
	}

	@Override
	public Json toJson() {
		Json json = super.toJson();
		json.set("baseStyle", Json.valueOf(baseStyle));
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
		json.setString("format", format);
		return json;
	}

	private double toNumber(Integer v) {
		return v == null ? Double.NaN : v.doubleValue();
	}

	private boolean equals(UiAtomicStyle other) {
		return	Objects.equals(this.baseStyle, other.baseStyle) &&
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
				Objects.equals(this.verticalAlign , other.verticalAlign) &&
				Objects.equals(this.format , other.format);
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

	public static class Builder {

		private final UiAtomicStyle style;

		public Builder(Class<?> owner, String name, UiAtomicStyle baseStyle) {
			style = new UiAtomicStyle(owner, name, baseStyle);
		}

		public Builder color(int color) {
			style.color = color;
			return this;
		}

		public Builder backgroundColor(int color) {
			style.backgroundColor = color;
			return this;
		}

		public Builder backgroundImage(String image) {
			style.backgroundImage = image;
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

		public Builder borderColor(int color) {
			style.borderColor = color;
			return this;
		}

		public Builder borderImage(String image) {
			style.borderImage = image;
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

		public Builder format(String format) {
			style.format = format;
			return this;
		}

		public UiAtomicStyle build() {
			return style;
		}

	}

}
