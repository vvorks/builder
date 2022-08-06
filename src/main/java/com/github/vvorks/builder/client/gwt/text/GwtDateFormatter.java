package com.github.vvorks.builder.client.gwt.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.vvorks.builder.client.common.ui.Metrics;
import com.github.vvorks.builder.client.gwt.intl.Calendar;
import com.github.vvorks.builder.client.gwt.intl.DateStyle;
import com.github.vvorks.builder.client.gwt.intl.DateTimeFormat;
import com.github.vvorks.builder.client.gwt.intl.Day;
import com.github.vvorks.builder.client.gwt.intl.DayPeriod;
import com.github.vvorks.builder.client.gwt.intl.Era;
import com.github.vvorks.builder.client.gwt.intl.FormatPart;
import com.github.vvorks.builder.client.gwt.intl.FractionalSecondDigits;
import com.github.vvorks.builder.client.gwt.intl.Hour;
import com.github.vvorks.builder.client.gwt.intl.Hour12;
import com.github.vvorks.builder.client.gwt.intl.IntlUtil;
import com.github.vvorks.builder.client.gwt.intl.Minute;
import com.github.vvorks.builder.client.gwt.intl.Month;
import com.github.vvorks.builder.client.gwt.intl.Option;
import com.github.vvorks.builder.client.gwt.intl.Second;
import com.github.vvorks.builder.client.gwt.intl.TimeStyle;
import com.github.vvorks.builder.client.gwt.intl.TimeZoneName;
import com.github.vvorks.builder.client.gwt.intl.Weekday;
import com.github.vvorks.builder.client.gwt.intl.Year;
import com.github.vvorks.builder.shared.common.lang.Iterables;
import com.github.vvorks.builder.shared.common.text.CalendarSelecter;
import com.github.vvorks.builder.shared.common.text.DateFormatter;
import com.github.vvorks.builder.shared.common.text.Pattern;

public class GwtDateFormatter extends DateFormatter {

	private static final Map<Integer, String> TAGS = new HashMap<>();
	static {
		TAGS.put(Pattern.CMD_LITERAL,			"literal");
		TAGS.put(Pattern.CMD_DATE_ERA,			"era");
		TAGS.put(Pattern.CMD_DATE_YEAR,			"year");
		TAGS.put(Pattern.CMD_DATE_MONTH,		"month");
		TAGS.put(Pattern.CMD_DATE_DAY,			"day");
		TAGS.put(Pattern.CMD_DATE_WEEK,			"weekday");
		TAGS.put(Pattern.CMD_TIME_DAY_PERIOD,	"dayPeriod");
		TAGS.put(Pattern.CMD_TIME_HOUR,			"hour");
		TAGS.put(Pattern.CMD_TIME_MINUTE,		"minute");
		TAGS.put(Pattern.CMD_TIME_SECOND,		"second");
		TAGS.put(Pattern.CMD_TIME_MSEC,			"fractionalSecond");
		TAGS.put(Pattern.CMD_TIME_TIMEZONE,		"timeZoneName");
	}

	private static final List<String> DATE_TAGS = Arrays.asList(
			TAGS.get(Pattern.CMD_DATE_ERA),
			TAGS.get(Pattern.CMD_DATE_YEAR),
			TAGS.get(Pattern.CMD_DATE_MONTH),
			TAGS.get(Pattern.CMD_DATE_DAY),
			TAGS.get(Pattern.CMD_DATE_WEEK));

	private static final List<String> TIME_TAGS = Arrays.asList(
			TAGS.get(Pattern.CMD_TIME_DAY_PERIOD),
			TAGS.get(Pattern.CMD_TIME_HOUR),
			TAGS.get(Pattern.CMD_TIME_MINUTE),
			TAGS.get(Pattern.CMD_TIME_SECOND),
			TAGS.get(Pattern.CMD_TIME_MSEC),
			TAGS.get(Pattern.CMD_TIME_TIMEZONE));

	private final DateTimeFormat format;

	public GwtDateFormatter(List<Pattern> patterns) {
		super(patterns);
		format = createDateTimeFormat(patterns);
	}

	private DateTimeFormat createDateTimeFormat(List<Pattern> patterns) {
		List<Option<?>> options = new ArrayList<>();
		if (Iterables.exists(patterns, e -> e.isCompositeDateTimePattern())) {
			for (Pattern p : patterns) {
				addDateTimeStyleOption(p, options);
			}
		} else {
			boolean hasDayPeriod = Iterables.exists(patterns, e -> e.isDayPeriod());
			options.add(hasDayPeriod ? Hour12.TRUE : Hour12.FALSE);
			for (Pattern p : patterns) {
				if (p.isDatePattern()) {
					addDateOption(p, options);
				} else if (p.isTimePattern()) {
					addTimeOption(p, options);
				}
			}
		}
		String locale = Metrics.get().getLocale();
		return DateTimeFormat.create(locale, options.toArray(new Option[options.size()]));
	}

	private void addDateTimeStyleOption(Pattern p, List<Option<?>> options) {
		String locale = Metrics.get().getLocale();
		int positiveCount = p.getPositiveCount();
		switch (p.getCommand()) {
		case Pattern.CMD_DATE:
			if (p.isAlternative()) {
				options.add(getAlternativeCalendar(locale, p.getAlternativeCount()));
			}
			if (positiveCount == 0) {
				options.add(DateStyle.SHORT);
			} else if (positiveCount == 1) {
				options.add(DateStyle.MEDIUM);
			} else if (positiveCount == 2) {
				options.add(DateStyle.LONG);
			} else {
				options.add(DateStyle.FULL);
			}
			break;
		case Pattern.CMD_TIME:
			if (positiveCount == 0) {
				options.add(TimeStyle.SHORT);
			} else if (positiveCount == 1) {
				options.add(TimeStyle.MEDIUM);
			} else if (positiveCount == 2) {
				options.add(TimeStyle.LONG);
			} else {
				options.add(TimeStyle.FULL);
			}
			break;
		default:
			break;
		}
	}

	private void addDateOption(Pattern p, List<Option<?>> options) {
		String locale = Metrics.get().getLocale();
		int positiveCount = p.getPositiveCount();
		switch (p.getCommand()) {
		case Pattern.CMD_DATE_ERA:
			if (positiveCount == 0) {
				options.add(Era.NARROW);
			} else if (positiveCount == 1) {
				options.add(Era.SHORT);
			} else {
				options.add(Era.LONG);
			}
			break;
		case Pattern.CMD_DATE_YEAR:
			if (p.isAlternative()) {
				options.add(getAlternativeCalendar(locale, p.getAlternativeCount()));
			}
			if (p.isZeroFill() && p.getWidth() == 2) {
				options.add(Year.TWODIGIT);
			} else {
				options.add(Year.NUMERIC);
			}
			break;
		case Pattern.CMD_DATE_MONTH:
			if (positiveCount == 0) {
				if (p.isZeroFill() && p.getWidth() == 2) {
					options.add(Month.TWODIGIT);
				} else {
					options.add(Month.NUMERIC);
				}
			} else if (positiveCount == 1) {
				options.add(Month.NARROW);
			} else if (positiveCount == 2) {
				options.add(Month.SHORT);
			} else {
				options.add(Month.LONG);
			}
			break;
		case Pattern.CMD_DATE_DAY:
			if (p.isZeroFill() && p.getWidth() == 2) {
				options.add(Day.TWODIGIT);
			} else {
				options.add(Day.NUMERIC);
			}
			break;
		case Pattern.CMD_DATE_WEEK:
			if (positiveCount == 0) {
				options.add(Weekday.NARROW);
			} else if (positiveCount == 1) {
				options.add(Weekday.SHORT);
			} else {
				options.add(Weekday.LONG);
			}
			break;
		default:
			break;
		}
	}

	private void addTimeOption(Pattern p, List<Option<?>> options) {
		int positiveCount = p.getPositiveCount();
		switch (p.getCommand()) {
		case Pattern.CMD_TIME_DAY_PERIOD:
			if (positiveCount > 0) {
				if (positiveCount == 1) {
					//apply locale default pattern.
				} else if (positiveCount == 2) {
					options.add(DayPeriod.NARROW);
				} else if (positiveCount == 3) {
					options.add(DayPeriod.SHORT);
				} else {
					options.add(DayPeriod.LONG);
				}
			}
			break;
		case Pattern.CMD_TIME_HOUR:
			if (p.isZeroFill() && p.getWidth() == 2) {
				options.add(Hour.TWODIGIT);
			} else {
				options.add(Hour.NUMERIC);
			}
			break;
		case Pattern.CMD_TIME_MINUTE:
			if (p.isZeroFill() && p.getWidth() == 2) {
				options.add(Minute.TWODIGIT);
			} else {
				options.add(Minute.NUMERIC);
			}
			break;
		case Pattern.CMD_TIME_SECOND:
			if (p.isZeroFill() && p.getWidth() == 2) {
				options.add(Second.TWODIGIT);
			} else {
				options.add(Second.NUMERIC);
			}
			break;
		case Pattern.CMD_TIME_MSEC:
			int msecWidth = p.getWidth();
			if (!(1 <= msecWidth && msecWidth <= 3)) {
				msecWidth = 3;
			}
			options.add(FractionalSecondDigits.of(msecWidth));
			break;
		case Pattern.CMD_TIME_TIMEZONE:
			if (positiveCount == 0) {
				options.add(TimeZoneName.LONGOFFSET);
			} else if (positiveCount == 1) {
				options.add(TimeZoneName.SHORT);
			} else {
				options.add(TimeZoneName.LONG);
			}
			break;
		default:
			break;
		}
	}

	private Calendar getAlternativeCalendar(String locale, int alternativeCount) {
		CalendarSelecter selecter = CalendarSelecter.get();
		String name = selecter.getCalendarName(locale, alternativeCount);
		return Calendar.getCalendar(name);
	}

	@Override
	public String format(Object value) {
		Date date = asDate(value);
		List<FormatPart> parts = format.formatToParts(date);
		StringBuilder sb = new StringBuilder();
		for (Pattern p : patterns) {
			int cmd = p.getCommand();
			if (cmd == Pattern.CMD_LITERAL) {
				sb.append(p.getLiteral());
			} else if (cmd == Pattern.CMD_DATE) {
				composeRange(p, parts, DATE_TAGS, sb);
			} else if (cmd == Pattern.CMD_TIME) {
				composeRange(p, parts, TIME_TAGS, sb);
			} else if (cmd == Pattern.CMD_TIME_DAY_PERIOD && p.getPositiveCount() == 0) {
				String v = findPartValue(p, parts, TAGS.get(cmd));
				if (v != null && !v.isEmpty()) {
					@SuppressWarnings("deprecation")
					int hour24 = date.getHours();
					sb.append(fill((hour24 < 12) ? "AM" : "PM", p, false));
				}
			} else if (cmd == Pattern.CMD_TIME_TIMEZONE && p.getPositiveCount() == 0) {
				String v = findPartValue(p, parts, TAGS.get(cmd));
				if (v.startsWith("GMT") || v.startsWith("UTC")) {
					v = v.substring(3);
				}
				sb.append(fill(v, p, false));
			} else if ((cmd == Pattern.CMD_TIME_MINUTE || cmd == Pattern.CMD_TIME_SECOND)) {
				String v = supressZero(findPartValue(p, parts, TAGS.get(cmd)));
				sb.append(fill(v, p, true));
			} else {
				String v = findPartValue(p, parts, TAGS.get(cmd));
				sb.append(fill(v, p, isInteger(v)));
			}
		}
		return sb.toString();
	}

	private void composeRange(Pattern p, List<FormatPart> parts, List<String> tags, StringBuilder into) {
		//コピー範囲の調査
		int n = parts.size();
		int spos = -1;
		int epos = n;
		for (int i = 0; i < n; i++) {
			FormatPart part = parts.get(i);
			if (spos == -1) {
				if (tags.contains(part.getType())) {
					spos = i;
				}
			} else {
				if (!tags.contains(part.getType()) && !part.isLiteral()) {
					epos = i;
					break;
				}
			}
		}
		if (spos == -1) {
			return;
		}
		//サブリストの値をコピー
		StringBuilder sb = new StringBuilder();
		for (FormatPart part : parts.subList(spos, epos)) {
			sb.append(part.getValue());
		}
		into.append(sb.toString().trim());
	}

	private String findPartValue(Pattern p, List<FormatPart> parts, String tag) {
		int index = IntlUtil.indexOf(parts, tag);
		if (index < 0) {
			return "";
		}
		return parts.get(index).getValue();
	}

	private String supressZero(String s) {
		int index = 0;
		while (index < s.length() && s.charAt(index) == '0') {
			index++;
		}
		if (index == 0) {
			return s;
		} else if (index < s.length()) {
			return s.substring(index);
		} else {
			return s.substring(index - 1);
		}
	}

	private boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}

}
