package com.github.vvorks.builder.common.text;

import java.util.Date;

public class DateFormatter extends Formatter {

	public DateFormatter() {
		Params params = getParams();
		if (params.getWidth() == 0) {
			params.setWidth(getDefaultWidth(params.getCommand()));
			params.setFlag(Params.FLAGS_ZERO, true);
		}
	}

	protected int getDefaultWidth(int command) {
		switch (command) {
			case 'G': return 2;
			case 'Y': return 4;
			case 'A': return 3;
			case 'm': return 2;
			case 'D': return 2;
			case 'P': return 2;
			case 'H': return 2;
			case 'I': return 2;
			case 'M': return 2;
			case 'S': return 2;
			case 'L': return 3;
			case 'Z': return 5;
			case 'F': return 10;
			case 'R': return 5;
			case 'T': return 8;
			default:  return 0;
		}
	}

	@Override
	public CharSequence apply(Object obj) {
		Date date = asDate(obj);
		Params params = getParams();
		int command = params.getCommand();
		if ("GPZFRT".indexOf((char)command) >= 0) {
			switch (command) {
				case 'G': return fill(getEra(date), false);
				case 'P': return fill(getAMPM(date), false);
				case 'Z': return fill(getTimezone(date), false);
				case 'F': return fill(getDate(date), false);
				case 'R': return fill(getHHMM(date), false);
				case 'T': return fill(getTime(date), false);
				default: throw new AssertionError();
			}
		} else {
			switch (command) {
				case 'Y': return fill(toString(getYear(date)), true);
				case 'm': return fill(toString(getMonth(date)), true);
				case 'D': return fill(toString(getDay(date)), true);
				case 'H': return fill(toString(getHour24(date)), true);
				case 'I': return fill(toString(getHour12(date)), true);
				case 'M': return fill(toString(getMinutes(date)), true);
				case 'S': return fill(toString(getSeconds(date)), true);
				case 'L': return fill(toString(getMillis(date)), true);
				default: throw new IllegalArgumentException();
			}
		}
	}

	protected String getEra(Date date) {
		return "AD";
	}

	protected int getYear(Date date) {
		return date.getYear() + 1900;
	}

	protected int getMonth(Date date) {
		return date.getMonth() + 1;
	}

	protected int getDay(Date date) {
		return date.getDate();
	}

	private static final String[] WEEK_NAMES = {
		"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"
	};

	protected String getWeek(Date date) {
		return WEEK_NAMES[date.getDay() % 7];
	}

	protected String getAMPM(Date date) {
		int hours = date.getHours();
		return 1 <= hours && hours <= 12 ? "AM" : "PM";
	}

	protected int getHour24(Date date) {
		return date.getHours();
	}

	protected int getHour12(Date date) {
		int hours = date.getHours();
		if (hours == 0) {
			return 12;
		}
		return 1 <= hours && hours <= 12 ? hours : hours - 12;
	}

	protected int getMinutes(Date date) {
		return date.getMinutes();
	}

	protected int getSeconds(Date date) {
		return date.getSeconds();
	}

	protected int getMillis(Date date) {
		return (1000 + (int)(date.getTime() % 1000)) % 1000;
	}

	protected String getTimezone(Date date) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	protected String getDate(Date date) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	protected String getHHMM(Date date) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	protected String getTime(Date date) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	protected String toString(int value) {
		return Integer.toString(value);
	}

}
