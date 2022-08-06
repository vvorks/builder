package com.github.vvorks.builder.shared.common.text;

import java.util.Date;
import java.util.List;

import com.github.vvorks.builder.shared.common.lang.Iterables;

public class DateFormatter extends Formatter {

	private static final String[] WEEK2 = {
			"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"
	};

	private static final String[] WEEK3 = {
			"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"
	};

	public DateFormatter(List<Pattern> patterns) {
		super(patterns);
	}

	@Override
	public String format(Object value) {
		Date date = asDate(value);
		StringBuilder sb = new StringBuilder();
		boolean hasDayPeriod = Iterables.exists(patterns, e -> e.isDayPeriod());
		for (Pattern p : patterns) {
			switch (p.getCommand()) {
			case Pattern.CMD_DATE:
				sb.append(formatYear(date, p));
				sb.append("-");
				sb.append(formatMonth(date, p));
				sb.append("-");
				sb.append(formatDay(date, p));
				break;
			case Pattern.CMD_DATE_ERA:
				break;
			case Pattern.CMD_DATE_YEAR:
				sb.append(formatYear(date, p));
				break;
			case Pattern.CMD_DATE_MONTH:
				sb.append(formatMonth(date, p));
				break;
			case Pattern.CMD_DATE_DAY:
				sb.append(formatDay(date, p));
				break;
			case Pattern.CMD_DATE_WEEK:
				sb.append(formatWeek(date, p));
				break;
			case Pattern.CMD_TIME:
				sb.append(formatHour24(date, p));
				sb.append(":");
				sb.append(formatMinute(date, p));
				sb.append(":");
				sb.append(formatSecond(date, p));
				break;
			case Pattern.CMD_TIME_DAY_PERIOD:
				sb.append(formatAMPM(date, p));
				break;
			case Pattern.CMD_TIME_HOUR:
				if (hasDayPeriod) {
					sb.append(formatHour12(date, p));
				} else {
					sb.append(formatHour24(date, p));
				}
				break;
			case Pattern.CMD_TIME_MINUTE:
				sb.append(formatMinute(date, p));
				break;
			case Pattern.CMD_TIME_SECOND:
				sb.append(formatSecond(date, p));
				break;
			case Pattern.CMD_TIME_MSEC:
				sb.append(formatMsec(date, p));
				break;
			case Pattern.CMD_TIME_TIMEZONE:
				sb.append(formatTimezone(date, p));
				break;
			case Pattern.CMD_LITERAL:
				sb.append(p.getLiteral());
				break;
			default:
				break;
			}
		}
		String result = sb.toString();
		LOGGER.debug("%s -> %s", date, result);
		return result;
	}

	protected Date asDate(Object obj) {
		if (obj instanceof Date) {
			return (Date) obj;
		} else if (obj instanceof Number) {
			return new Date(((Number)obj).longValue());
		} else if (obj == null) {
			return new Date(0L);
		}
		throw new IllegalArgumentException();
	}

	private String formatYear(Date date, Pattern p) {
		@SuppressWarnings("deprecation")
		int year = date.getYear() + 1900;
		String str = Integer.toString(year);
		return fill(str, p, true);
	}

	private String formatMonth(Date date, Pattern p) {
		@SuppressWarnings("deprecation")
		int month = date.getMonth() + 1;
		String str = Integer.toString(month);
		return fill(str, p, true);
	}

	private String formatDay(Date date, Pattern p) {
		@SuppressWarnings("deprecation")
		int day = date.getDate();
		String str = Integer.toString(day);
		return fill(str, p, true);
	}

	private String formatWeek(Date date, Pattern p) {
		@SuppressWarnings("deprecation")
		int week = date.getDay();
		String[] array = (p.getPreferredWidth() < 3) ? WEEK2 : WEEK3;
		String str = array[week];
		return fill(str, p, true);
	}

	private String formatAMPM(Date date, Pattern p) {
		@SuppressWarnings("deprecation")
		int hour24 = date.getHours();
		String str = (hour24 < 12) ? "AM" : "PM";
		return fill(str, p, false);
	}

	private String formatHour24(Date date, Pattern p) {
		@SuppressWarnings("deprecation")
		int hour24 = date.getHours();
		String str = Integer.toString(hour24);
		return fill(str, p, true);
	}

	private String formatHour12(Date date, Pattern p) {
		@SuppressWarnings("deprecation")
		int hour = date.getHours() % 12;
		int hour12 = (hour == 0) ? 12 : hour;
		String str = Integer.toString(hour12);
		return fill(str, p, true);
	}

	private String formatMinute(Date date, Pattern p) {
		@SuppressWarnings("deprecation")
		int minute = date.getMinutes();
		String str = Integer.toString(minute);
		return fill(str, p, true);
	}

	private String formatSecond(Date date, Pattern p) {
		@SuppressWarnings("deprecation")
		int sec = date.getSeconds();
		String str = Integer.toString(sec);
		return fill(str, p, true);
	}

	private String formatMsec(Date date, Pattern p) {
		int msec = (int)(date.getTime() % 1000);
		String str = Integer.toString(msec);
		return fill(str, p, true);
	}

	private String formatTimezone(Date date, Pattern p) {
		@SuppressWarnings("deprecation")
		int v = date.getTimezoneOffset();
		String s = v < 0 ? "+" : "-";
		int a = Math.abs(v);
		int h = a / 60;
		int m = a % 60;
		String t = "0000" + Integer.toString(h * 100 + m);
		String str = s + t.substring(t.length() - 4);
		return fill(str, p, false);
	}

}
