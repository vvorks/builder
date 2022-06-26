package com.github.vvorks.builder.common.text;

public class CalendarSelecterJa implements CalendarSelecter {

	@Override
	public String getCalendarName(String locale, int mode) {
		if (locale.startsWith("ja")) {
			return "japanese";
		}
		return "gregory";
	}

}
