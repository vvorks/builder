package com.github.vvorks.builder.common.text;

import com.github.vvorks.builder.common.lang.Factory;

public interface CalendarSelecter {

	public static CalendarSelecter get() {
		return Factory.getInstance(CalendarSelecter.class);
	}

	public String getCalendarName(String locale, int mode);

}
