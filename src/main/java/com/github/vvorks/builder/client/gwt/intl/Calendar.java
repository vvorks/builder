package com.github.vvorks.builder.client.gwt.intl;

/**
 * 日付時刻フォーマットオプション：暦
 */
public enum Calendar implements StringOption {

    /** Thai Buddhist calendar */
    BUDDHIST("buddhist"),

    /** Traditional Chinese calendar */
	CHINESE("chinese"),

    /** Coptic calendar */
	COPTIC("coptic"),

    /** Ethiopic calendar, Amete Alem (epoch approx. 5493 B.C.E) */
	ETHIOPIA("ethiopia"),

    /** Ethiopic calendar, Amete Mihret (epoch approx, 8 C.E.) */
	ETHIOPIC("ethiopic"),

    /** Gregorian calendar */
	GREGORY("gregory"),

    /** Traditional Hebrew calendar */
	HEBREW("hebrew"),

    /** Indian calendar */
	INDIAN("indian"),

    /** Islamic calendar */
	ISLAMIC("islamic"),

    /** ISO calendar (Gregorian calendar using the ISO 8601 calendar week rules) */
	ISO8601("iso8601"),

    /** Japanese Imperial calendar */
	JAPANESE("japanese"),

    /** Persian calendar */
	PERSIAN("persian"),

    /** Republic of China calendar */
	ROC("roc");

	private final String value;

	private Calendar(String value) {
		this.value = value;
	}

	@Override
	public final String key() {
		return "calendar";
	}

	@Override
	public final String value() {
		return value;
	}

}