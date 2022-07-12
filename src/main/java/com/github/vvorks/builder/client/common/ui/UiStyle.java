package com.github.vvorks.builder.client.common.ui;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.json.Jsonizable;
import com.github.vvorks.builder.common.lang.Copyable;
import com.github.vvorks.builder.common.logging.Logger;

public class UiStyle implements Copyable<UiStyle>, Jsonizable {

	protected static final Logger LOGGER = Logger.createLogger(UiStyle.class);

	public static final String ALIGN_CENTER		= "center";

	public static final String ALIGN_JUSTIFY	= "justify";

	public static final String ALIGN_LEFT		= "left";

	public static final String ALIGN_RIGHT		= "right";

	public static final String ALIGN_TOP		= "top";

	public static final String ALIGN_MIDDLE		= "middle";

	public static final String ALIGN_BOTTOM		= "bottom";

	/** 自身の完全修飾名 */
	private static final String CLASS_NAME = UiStyle.class.getName();

	/** uiパッケージ名 */
	private static final String UI_PACKAGE_NAME = CLASS_NAME.substring(0, CLASS_NAME.lastIndexOf('.'));

	/** clientパッケージ名 */
	private static final String CLIENT_PACKAGE_NAME = UI_PACKAGE_NAME.substring(0, UI_PACKAGE_NAME.lastIndexOf('.'));

	/** モジュール名 */
	private static final String MODULE_NAME = CLIENT_PACKAGE_NAME.substring(0, CLIENT_PACKAGE_NAME.lastIndexOf('.'));

	/** 条件：既定 */
	private static final Predicate<UiNode> DEFAULT	= node -> true;

	/** 条件：ノード有効時 */
	private static final Predicate<UiNode> ENABLE	= node -> node.isEnableAll();

	/** 条件：ノード無効時 */
	private static final Predicate<UiNode> DISABLE	= node -> !node.isEnableAll();

	/** 条件：ノードフォーカス時 */
	private static final Predicate<UiNode> FOCUS	= node -> node.inFocus();

	/** 条件：ノード押下時 */
	private static final Predicate<UiNode> CLICKING	= node -> node.isClicking();

	private static final Map<String, Integer> CONDITION_ORDER = new HashMap<>();
	static {
		CONDITION_ORDER.put("NAMED",    0);
		CONDITION_ORDER.put("CLICKING", 1);
		CONDITION_ORDER.put("FOCUS",    2);
		CONDITION_ORDER.put("ENABLE",   3);
		CONDITION_ORDER.put("DISABLE",  4);
	}

	private static final Length DEFAULT_FONT_SIZE = new Length(12, Length.Unit.PT);

	private static final Comparator<UiStyle> BY_CONDITION_ORDER = new Comparator<UiStyle>() {
		public int compare(UiStyle s, UiStyle t) {
			return s.getConditionOrder() - t.getConditionOrder();
		}
	};

	/** スタイル名 */
	private final String name;

	/** 上位スタイル */
	private UiStyle parent;

	/** 下位スタイル */
	private Set<UiStyle> children;

	/** 条件名 */
	private String conditionName;

	/** 条件 */
	private Predicate<UiNode> condition;

	/** 前景色 */
	private Integer textColor;

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

	private UiStyle(String name) {
		this.name = name;
		this.conditionName = "DEFAULT";
		this.condition = DEFAULT;
		this.children = new TreeSet<>(BY_CONDITION_ORDER);
	}

	public UiStyle(UiStyle src) {
		this.name = src.name;
		this.parent = src.parent;
		this.children = new TreeSet<>(BY_CONDITION_ORDER);
		this.children.addAll(src.children);
		this.conditionName = src.conditionName;
		this.condition = src.condition;
		this.textColor = src.textColor;
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

	public UiStyle copy() {
		return new UiStyle(this);
	}

	public String getCssClassName() {
		return MODULE_NAME.replace('.', '-') + "-" + name;
	}

	public String getName() {
		return name;
	}

	public UiStyle getParent() {
		return parent;
	}

	public Set<UiStyle> getChildren() {
		return Collections.unmodifiableSet(children);
	}

	public UiStyle getEffectiveStyle(UiNode node) {
		UiStyle effective = this;
		while (!effective.children.isEmpty()) {
			UiStyle found = effective.findChild(node);
			if (found == null) {
				break;
			}
			effective = found;
		}
		return effective;
	}

	private UiStyle findChild(UiNode node) {
		for (UiStyle child : children) {
			if (child.condition.test(node)) {
				return child;
			}
		}
		return null;
	}

	public String getConditionName() {
		return conditionName;
	}

	public Predicate<UiNode> getCondition() {
		return condition;
	}

	public int getConditionOrder() {
		Integer ret = CONDITION_ORDER.get(conditionName);
		if (ret == null) {
			return Short.MAX_VALUE;
		}
		return ret;
	}

	public Integer getTextColor() {
		return getProperty(s -> s.textColor, Colors.BLACK);
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

	public Length getFontSize() {
		return getProperty(s -> s.fontSize, DEFAULT_FONT_SIZE);
	}

	public String getFontFamily() {
		return getProperty(s -> s.fontFamily, "sans-serif");
	}

	public Length getLineHeight() {
		Length prop = getProperty(s -> s.lineHeight, null);
		return (prop != null) ? prop : getFontSize();
	}

	public String getTextAlign() {
		return getProperty(s -> s.textAlign, "justify");
	}

	public String getVerticalAlign() {
		return getProperty(s -> s.verticalAlign, "top");
	}

	private <R> R getProperty(Function<UiStyle, R> func, R defaultValue) {
		UiStyle s = this;
		while (s != null) {
			R result = func.apply(s);
			if (result != null) {
				return result;
			}
			s = s.getParent();
		}
		return defaultValue;
	}

	private String getParentName() {
		return parent != null ? parent.getName() : "";
	}

	private String getChildrenNames() {
		if (children.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (UiStyle s : children) {
			sb.append(",");
			sb.append(s.getName());
		}
		return sb.substring(1);
	}

	@Override
	public int hashCode() {
		return Objects.hash(
				getParentName(),
				getChildrenNames(),
				condition,
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
			return equals((UiStyle) obj);
		}
	}

	private boolean equals(UiStyle other) {
		return	Objects.equals(this.getParentName(), other.getParentName()) &&
				Objects.equals(this.getChildrenNames(), other.getChildrenNames()) &&
				Objects.equals(this.condition, other.condition) &&
				Objects.equals(this.textColor, other.textColor) &&
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
		json.setString("parent", getParentName());
		json.setString("children", getChildrenNames());
		json.setDouble("textColor", toNumber(textColor));
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

	public void toCssStyle(Map<String, CssStyle> cssMap) {
		cssMap.put("." + getCssClassName(), getCssStyle());
		for (UiStyle c : children) {
			c.toCssStyle(cssMap);
		}
	}

	public CssStyle getCssStyle() {
		return new CssStyle.Builder()
				.property("textColor", toCssColor(getTextColor()))
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

	public static Builder newBuilder(String name) {
		return new Builder(new UiStyle(name));
	}

	public Builder configure() {
		return new Builder(this);
	}

	/**
	 * スタイルビルダー
	 */
	public static class Builder {

		private UiStyle style;

		public Builder(UiStyle style) {
			this.style = style;
		}

		public Builder parent(UiStyle parent) {
			if (style.parent != null) {
				style.parent.children.remove(style);
			}
			style.parent = parent;
			if (style.parent != null) {
				style.parent.children.add(style);
			}
			return this;
		}

		public Builder condition(String condName) {
			return condition(condName, null);
		}

		public Builder condition(String condName, String condParam) {
			style.conditionName = condName;
			if (condName == null) {
				style.condition = DEFAULT;
			} else if (condName.equals("NAMED")) {
				style.condition = node -> node.getName().equals(condParam);
			} else if (condName.equals("CLICKING")) {
				style.condition = CLICKING;
			} else if (condName.equals("FOCUS")) {
				style.condition = FOCUS;
			} else if (condName.equals("ENABLE")) {
				style.condition = ENABLE;
			} else if (condName.equals("DISABLE")) {
				style.condition = DISABLE;
			} else {
				style.condition = DEFAULT;
			}
			return this;
		}

		public Builder textColor(Integer textColor) {
			style.textColor = textColor;
			return this;
		}

		public Builder textColor(String textColor) {
			if (textColor == null || textColor.isEmpty()) {
				style.textColor = null;
			} else {
				style.textColor = Colors.parse(textColor);
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

		public Builder borderLeft(String left) {
			style.borderLeft = new Length(left);
			return this;
		}

		public Builder borderTop(String top) {
			style.borderTop = new Length(top);
			return this;
		}

		public Builder borderRight(String right) {
			style.borderRight = new Length(right);
			return this;
		}

		public Builder borderBottom(String bottom) {
			style.borderBottom = new Length(bottom);
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

		public Builder fontSize(String size) {
			style.fontSize = new Length(size);
			return this;
		}

		public Builder fontFamily(String family) {
			style.fontFamily = family;
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

		public Builder textAlign(String textAlign) {
			style.textAlign = textAlign;
			return this;
		}

		public Builder verticalAlign(String verticalAlign) {
			style.verticalAlign = verticalAlign;
			return this;
		}

		public Builder align(String textAlign, String verticalAlign) {
			style.textAlign = textAlign;
			style.verticalAlign = verticalAlign;
			return this;
		}

		public UiStyle build() {
			return style;
		}

	}

}
