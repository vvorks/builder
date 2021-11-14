package com.github.vvorks.builder.client.common.ui;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.common.logging.Logger;

public class UiAtomicStyle extends UiStyle {

	public static final Class<?> THIS = UiAtomicStyle.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

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

	/** ボーダーの色 */
	private Integer borderColor;

	/** 数値／日付・時刻フォーマットパターン */
	private String format;

	public UiAtomicStyle(Class<?> owner, String name, UiAtomicStyle baseStyle) {
		super(owner, name);
		this.baseStyle = baseStyle;
	}

	protected UiAtomicStyle(UiAtomicStyle src) {
		super(src);
		baseStyle = src.baseStyle;
		color = src.color;
		backgroundColor = src.backgroundColor;
		backgroundImage = src.backgroundImage;
		fontSize = src.fontSize;
		fontFamily = src.fontFamily;
		lineHeight = src.lineHeight;
		textAlign = src.textAlign;
		verticalAlign = src.verticalAlign;
		borderColor = src.borderColor;
		format = src.format;
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

	public Integer getBorderColor() {
		return getProperty(s -> s.borderColor, Colors.BLACK);
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
				fontSize,
				fontFamily,
				lineHeight,
				textAlign,
				verticalAlign,
				borderColor,
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
		json.setNumber("color", toNumber(color));
		json.setNumber("backgroundColor", toNumber(backgroundColor));
		json.setString("backgroundImage", backgroundImage);
		json.set("fontSize", Json.valueOf(fontSize));
		json.setString("fontFamily", fontFamily);
		json.set("fontSize", Json.valueOf(lineHeight));
		json.setString("textAlign", textAlign);
		json.setString("verticalAlign", verticalAlign);
		json.setNumber("borderColor", toNumber(borderColor));
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
				Objects.equals(this.fontSize, other.fontSize) &&
				Objects.equals(this.fontFamily , other.fontFamily) &&
				Objects.equals(this.lineHeight , other.lineHeight) &&
				Objects.equals(this.textAlign , other.textAlign) &&
				Objects.equals(this.verticalAlign , other.verticalAlign) &&
				Objects.equals(this.borderColor , other.borderColor) &&
				Objects.equals(this.format , other.format);
	}

	public CssStyle getCssStyle() {
		return new CssStyle.Builder()
				.property("color", toCssColor(getColor()))
				.property("background-color", toCssColor(getBackgroundColor()))
				.property("background-image", getBackgroundImage())
				.property("font-size", getFontSize())
				.property("font-family", getFontFamily())
				.property("line-height", getLineHeight())
				.property("border-color", toCssColor(getBorderColor()))
				.build();
	}

	private String toCssColor(Integer color) {
		if (color == null) {
			return null;
		}
		int c = color.intValue();
		if (Colors.isOpaque(c)) {
			return Strings.sprintf("#%06x", c & 0xFFFFFF);
		}
		return Strings.sprintf("rgba(%d,%d,%d,%g)",
				Colors.getRValue(c),
				Colors.getGValue(c),
				Colors.getBValue(c),
				Colors.getAValue(c));
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

		public Builder borderColor(int borderColor) {
			style.borderColor = borderColor;
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
