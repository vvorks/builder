package com.github.vvorks.builder.client.common.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.json.Jsonizable;
import com.github.vvorks.builder.common.lang.Asserts;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.lang.Value;
import com.github.vvorks.builder.common.logging.Logger;

/**
 * 単位付き値
 */
public class Length implements Jsonizable {

	private static final Logger LOGGER = Logger.createLogger(Length.class);

	public enum Unit {

		/** 相対単位／基準フォントの'M'のサイズ */
		REM("rem"),
		/** 相対単位／基準フォントの'x'の高さ */
		REX("rex"),
		/** 相対単位／親座標系の百分率 */
		PCT("%"),
		/** インチ */
		IN("in"),
		/** センチメートル */
		CM("cm"),
		/** ミリメートル */
		MM("mm"),
		/** ポイント */
		PT("pt"),
		/** パイカ */
		PC("pc"),
		/** ピクセル */
		PX("px");

		private final String cssName;

		private Unit(String cssName) {
			this.cssName = cssName;
		}

		@Override
		public String toString() {
			return cssName;
		}

	}

	public static final Length NULL = null;

	public static final Length ZERO = new Length(0, Unit.PX);

	public static final Length HALF = new Length(50, Unit.PCT);

	public static final Length FULL = new Length(100, Unit.PCT);

	private static final Map<String, Unit> UNIT_NAME_MAP = new HashMap<>();
	static {
		for (Unit unit : Unit.values()) {
			UNIT_NAME_MAP.put(unit.name(), unit);
		}
		UNIT_NAME_MAP.put("EM", Unit.REM);
		UNIT_NAME_MAP.put("EX", Unit.REX);
		UNIT_NAME_MAP.put("%", Unit.PCT);
	}

	/** 値 */
	private final double value;

	/** 単位 */
	private final Unit unit;

	public Length(String str) {
		Asserts.requireNotEmpty(str);
		//単位と数値の区切りを検索
		int len = str.length();
		int pos = len;
		char ch = --pos >= 0 ? str.charAt(pos) : (char)0;
		while (ch != 0 && !('0' <= ch && ch <= '9') && ch != '.') {
			ch = --pos >= 0 ? str.charAt(pos) : (char)0;
		}
		pos++;
		Asserts.require(pos > 0);
		double v;
		Unit u;
		if (pos < len) {
			//（おそらく）単位付き数値
			v = Double.parseDouble(str.substring(0, pos));
			u = UNIT_NAME_MAP.get(str.substring(pos).toUpperCase());
			Asserts.require(u != null);
		} else {
			//（おそらく）単なる数値
			v = Double.parseDouble(str);
			u = Unit.PX;
		}
		this.value = v;
		this.unit = u;
	}

	/**
	 * 単位付き値を作成する
	 *
	 * @param value
	 * 		値
	 * @param unit
	 * 		単位
	 */
	public Length(double value, Unit unit) {
		this.value = value;
		this.unit = unit;
	}

	/**
	 * 単位付き値を作成する
	 *
	 * @param value
	 * 		値（ピクセル単位）
	 */
	public Length(int value) {
		this(value, Unit.PX);
	}

	/**
	 * 値を取得する
	 *
	 * @return
	 * 		値
	 */
	public double getValue() {
		return value;
	}

	/**
	 * 単位を取得する
	 *
	 * @return
	 * 		単位
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * ピクセル単位での値を整数で取得する
	 *
	 * @param parentValue
	 * 		親座標系のピクセル値（％計算で使用する）。遅延実行のため
	 * @return
	 * 		ピクセル単位の値（小数点以下は四捨五入される）
	 */
	public int px(Value<Integer> parentValue) {
		return (int) Math.round(getValuePx(parentValue));
	}

	/**
	 * ピクセル単位での値を整数で取得する
	 *
	 * @param parentValue
	 * 		親座標系のピクセル値（％計算で使用する）。遅延実行のため
	 * @return
	 * 		ピクセル単位の値（小数点以下は四捨五入される）
	 */
	public int px(int parentValue) {
		return (int) Math.round(getValuePx(parentValue));
	}

	/**
	 * ピクセル単位での値を取得する
	 *
	 * @param parentValue
	 * 		親座標系のピクセル値（％計算で使用する）
	 * @return
	 * 		ピクセル単位の値（小数点以下の処理は呼び出し元で行う事）
	 */
	public double getValuePx(Value<Integer> parentValue) {
		double px;
		if (unit == Unit.PCT) {
			px = (value * parentValue.getValue()) / 100;
		} else {
			px = getValuePxNotPct();
		}
		return px;
	}

	/**
	 * ピクセル単位での値を取得する
	 *
	 * @param parentValue
	 * 		親座標系のピクセル値（％計算で使用する）
	 * @return
	 * 		ピクセル単位の値（小数点以下の処理は呼び出し元で行う事）
	 */
	public double getValuePx(int parentValue) {
		double px;
		if (unit == Unit.PCT) {
			px = (value * parentValue) / 100;
		} else {
			px = getValuePxNotPct();
		}
		return px;
	}

	private double getValuePxNotPct() {
		Metrics met = Factory.getInstance(Metrics.class);
		double px;
		switch (unit) {
		case IN:
			px = value * met.getInSize();
			break;
		case CM:
			px = value * met.getCmSize();
			break;
		case MM:
			px = value * met.getMmSize();
			break;
		case PT:
			px = value * met.getPtSize();
			break;
		case PC:
			px = value * met.getPcSize();
			break;
		case PX:
			px = value;
			break;
		case REM:
			px = value * met.getEmSize();
			break;
		case REX:
			px = value * met.getExSize();
			break;
		default:
			throw new IllegalArgumentException();
		}
		return px;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, unit);
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
			return equals((Length) obj);
		}
	}

	private boolean equals(Length other) {
		return	unit == other.unit &&
				Double.doubleToLongBits(value) == Double.doubleToLongBits(other.value);
	}

	public String toString() {
		return String.valueOf(value) + unit;
	}

	@Override
	public Json toJson() {
		Json json = Json.createObject();
		json.setDouble("value", value);
		json.setString("unit", unit);
		return json;
	}

	public static Unit unitOf(String unitName) {
		Unit unit = UNIT_NAME_MAP.get(unitName.toUpperCase());
		if (unit == null) {
			LOGGER.warn("unit %s not found", unitName);
			unit = Unit.PX;
		}
		return unit;
	}

	public static Length fromPx(double px, Unit unit, Value<Integer> parentValue) {
		Metrics met = Factory.getInstance(Metrics.class);
		double value;
		switch (unit) {
		case PCT:
			value = (px / parentValue.getValue()) * 100;
			break;
		case IN:
			value = px / met.getInSize();
			break;
		case CM:
			value = px / met.getCmSize();
			break;
		case MM:
			value = px / met.getMmSize();
			break;
		case PT:
			value = px / met.getPtSize();
			break;
		case PC:
			value = px / met.getPcSize();
			break;
		case PX:
			value = px;
			break;
		case REM:
			value = px / met.getEmSize();
			break;
		case REX:
			value = px / met.getExSize();
			break;
		default:
			throw new IllegalArgumentException();
		}
		return new Length(value, unit);
	}

	public static Length emOf(double value) {
		return new Length(value, Unit.REM);
	}

	public static Length exOf(double value) {
		return new Length(value, Unit.REX);
	}

	public static Length pctOf(double value) {
		return new Length(value, Unit.PCT);
	}

	public static Length inOf(double value) {
		return new Length(value, Unit.IN);
	}

	public static Length cmOf(double value) {
		return new Length(value, Unit.CM);
	}

	public static Length mmOf(double value) {
		return new Length(value, Unit.MM);
	}

	public static Length ptOf(double value) {
		return new Length(value, Unit.PT);
	}

	public static Length pcOf(double value) {
		return new Length(value, Unit.PC);
	}

	public static Length pxOf(double value) {
		return new Length(value, Unit.PX);
	}

}
