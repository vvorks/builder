package com.github.vvorks.builder.client.gwt.intl;

/**
 * 共通フォーマットオプション：数値体系
 */
public enum NumberingSystem implements StringOption {

    /** Arabic-Indic digits */
	ARAB("arab"),

    /** Extended Arabic-Indic digits */
	ARABEXT("arabext"),

    /** Balinese digits */
	BALI("bali"),

    /** Bengali digits */
	BENG("beng"),

    /** Devanagari digits */
	DEVA("deva"),

    /** Full width digits */
	FULLWIDE("fullwide"),

    /** Gujarati digits */
	GUJR("gujr"),

    /** Gurmukhi digits */
	GURU("guru"),

    /** Positional decimal system using Chinese number ideographs as digits */
	HANIDEC("hanidec"),

    /** Khmer digits */
	KHMR("khmr"),

    /** Kannada digits */
	KNDA("knda"),

    /** Lao digits */
	LAOO("laoo"),

    /** Latin digits */
	LATN("latn"),

    /** Limbu digits */
	LIMB("limb"),

    /** Malayalam digits */
	MLYM("mlym"),

    /** Mongolian digits */
	MONG("mong"),

    /** Myanmar digits */
	MYMR("mymr"),

    /** Oriya digits */
	ORYA("orya"),

    /** Modern Tamil decimal digits */
	TAMLDEC("tamldec"),

    /** Telugu digits */
	TELU("telu"),

    /** Thai digits */
	THAI("thai"),

    /** Tibetan digits */
	TIBT("tibt");

	private final String value;

	private NumberingSystem(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "numberingSystem";
	}

	@Override
	public final String value() {
		return value;
	}

}