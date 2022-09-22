package com.github.vvorks.builder.client.gwt.intl;

/**
 * 数値オプション：単位
 */
public class Unit implements StringOption {

	public static final String ACRE = "acre";
	public static final String BIT = "bit";
	public static final String BYTE = "byte";
	public static final String CELSIUS = "celsius";
	public static final String CENTIMETER = "centimeter";
	public static final String DAY = "day";
	public static final String DEGREE = "degree";
	public static final String FAHRENHEIT = "fahrenheit";
	public static final String FLUID_OUNCE = "fluid-ounce";
	public static final String FOOT = "foot";
	public static final String GALLON = "gallon";
	public static final String GIGABIT = "gigabit";
	public static final String GIGABYTE = "gigabyte";
	public static final String GRAM = "gram";
	public static final String HECTARE = "hectare";
	public static final String HOUR = "hour";
	public static final String INCH = "inch";
	public static final String KILOBIT = "kilobit";
	public static final String KILOBYTE = "kilobyte";
	public static final String KILOGRAM = "kilogram";
	public static final String KILOMETER = "kilometer";
	public static final String LITER = "liter";
	public static final String MEGABIT = "megabit";
	public static final String MEGABYTE = "megabyte";
	public static final String METER = "meter";
	public static final String MILE = "mile";
	public static final String MILE_SCANDINAVIAN = "mile-scandinavian";
	public static final String MILLILITER = "milliliter";
	public static final String MILLIMETER = "millimeter";
	public static final String MILLISECOND = "millisecond";
	public static final String MINUTE = "minute";
	public static final String MONTH = "month";
	public static final String OUNCE = "ounce";
	public static final String PERCENT = "percent";
	public static final String PETABYTE = "petabyte";
	public static final String POUND = "pound";
	public static final String SECOND = "second";
	public static final String STONE = "stone";
	public static final String TERABIT = "terabit";
	public static final String TERABYTE = "terabyte";
	public static final String WEEK = "week";
	public static final String YARD = "yard";
	public static final String YEAR = "year";

	private final String value;

	/**
	 * 単位を作成する
	 *
	 * @param value 基本単位又はそれを"-per-"で結合した複合単位
	 * @see <a href="https://tc39.es/proposal-unified-intl-numberformat/section6/locales-currencies-tz_proposed_out.html#sec-issanctionedsimpleunitidentifier">intl</a>
	 */
	public Unit(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "unit";
	}

	@Override
	public final String value() {
		return value;
	}

}
