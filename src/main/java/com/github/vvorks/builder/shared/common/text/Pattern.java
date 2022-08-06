package com.github.vvorks.builder.shared.common.text;

import java.util.ArrayList;
import java.util.List;

import com.github.vvorks.builder.shared.common.lang.CharSequenceReader;

public class Pattern {
	//											  "01234567890"
	private static final String FLAG_SYMBOLS	= "-0+ ,$¥€£¤#";
	private static final int FLAGS_LEFT			= 0;
	private static final int FLAGS_ZERO			= 1;
	private static final int FLAGS_PLUS			= 2;
	private static final int FLAGS_SPACE		= 3;
	private static final int FLAGS_GROUP		= 4;
	private static final int FLAGS_DOLLER		= 5;
	private static final int FLAGS_YEN			= 6;
	private static final int FLAGS_EURO			= 7;
	private static final int FLAGS_POUND		= 8;
	private static final int FLAGS_CURRENCY		= 9;
	private static final int FLAGS_ALTERNATIVE	= 10;

	//										      "123456789012345678901"
	private static final String COMMAND_CHARS	= "bodxcsFGYmDATPHMSLZ%n";
	public static final int CMD_LITERAL			= 0;
	public static final int CMD_NUMBER_BIN		= 1;
	public static final int CMD_NUMBER_OCT		= 2;
	public static final int CMD_NUMBER_DEC		= 3;
	public static final int CMD_NUMBER_HEX		= 4;
	public static final int CMD_CHAR			= 5;
	public static final int CMD_STRING			= 6;
	public static final int CMD_DATE			= 7;
	public static final int CMD_DATE_ERA		= 8;
	public static final int CMD_DATE_YEAR		= 9;
	public static final int CMD_DATE_MONTH		= 10;
	public static final int CMD_DATE_DAY		= 11;
	public static final int CMD_DATE_WEEK		= 12;
	public static final int CMD_TIME			= 13;
	public static final int CMD_TIME_DAY_PERIOD	= 14;
	public static final int CMD_TIME_HOUR		= 15;
	public static final int CMD_TIME_MINUTE		= 16;
	public static final int CMD_TIME_SECOND		= 17;
	public static final int CMD_TIME_MSEC		= 18;
	public static final int CMD_TIME_TIMEZONE	= 19;
	public static final int CMD_OTHER_PCT		= 20;
	public static final int CMD_OTHER_LF		= 21;

	private static final int[] DEFAULT_WIDTHS = {
		0,	32,	11,	10,	8,	1,	0,	10,	2,	4,
		2,	2,	3,	8,	2,	2,	2,	2,	2,	3,
		5,	1,	0,
	};

	private final int command;

	private final int[] flags;

	private final int width;

	private final int precision;

	private final String literal;

	protected Pattern(String literal) {
		this.command = CMD_LITERAL;
		this.flags = new int[FLAG_SYMBOLS.length()];
		this.width = literal.length();
		this.precision = -1;
		this.literal = literal;
	}

	protected Pattern(int command, int[] flags, int width, int precision) {
		this.command = command;
		this.flags = flags;
		this.width = width;
		this.precision = precision;
		this.literal = "";
	}

	public Pattern(Pattern src, int precision) {
		this.command = src.command;
		this.flags = src.flags;
		this.width = src.width;
		this.precision = precision;
		this.literal = "";
	}

	public static List<Pattern> parse(CharSequence pattern) {
		CharSequenceReader in = new CharSequenceReader(pattern);
		List<Pattern> patterns = new ArrayList<>();
		while (in.hasNext()) {
			if (in.peek() != '%') {
				patterns.add(parseLiteral(in));
			}
			if (in.peek() == '%') {
				patterns.add(parsePattern(in));
			}
		}
		return patterns;
	}

	private static Pattern parseLiteral(CharSequenceReader in) {
		int ch = in.peek();
		int spos = in.position();
		while (ch != -1 && ch != '%') {
			in.skip();
			ch = in.peek();
		}
		int epos = in.position();
		return new Pattern(in.substring(spos, epos).toString());
	}

	private static Pattern parsePattern(CharSequenceReader in) {
		int spos = in.position();
		in.skip();
		int[] flags = parseFlags(in);
		int[] wp = parseWidthAndPrecition(in);
		int ch = in.next();
		int index = COMMAND_CHARS.indexOf(ch) + 1;
		Pattern result;
		if (index > 0) {
			//normal pattern
			result = new Pattern(index, flags, wp[0], wp[1]);
		} else {
			//literal
			int epos = in.position();
			result = new Pattern(in.substring(spos, epos).toString());
		}
		return result;
	}

	private static int[] parseFlags(CharSequenceReader in) {
		int[] flags = new int[FLAG_SYMBOLS.length()];
		int index;
		while ((index = FLAG_SYMBOLS.indexOf((char)in.peek())) >= 0) {
			flags[index]++;
			in.skip();
		}
		return flags;
	}

	private static int[] parseWidthAndPrecition(CharSequenceReader in) {
		int ch = in.peek();
		int spos = in.position();
		int mpos = -1;
		while (('0' <= ch && ch <= '9') || (ch == '.')) {
			if (ch == '.') {
				if (mpos != -1) {
					throw new IllegalArgumentException();
				}
				mpos = in.position();
			}
			in.skip();
			ch = in.peek();
		}
		int epos = in.position();
		int w = -1;
		int p = -1;
		if (spos == epos) {
			//empty. do nothing
		} else if (mpos == -1) {
			w = Integer.parseInt(in.substring(spos, epos).toString());
		} else if (spos == mpos) {
			p = Integer.parseInt(in.substring(mpos + 1, epos).toString());
		} else if (mpos + 1 < epos) {
			w = Integer.parseInt(in.substring(spos, mpos).toString());
			p = Integer.parseInt(in.substring(mpos + 1, epos).toString());
		} else {
			w = Integer.parseInt(in.substring(spos, mpos).toString());
		}
		return new int[] {w,p};
	}

	public int getCommand() {
		return command;
	}

	public int[] getFlags() {
		return flags;
	}

	public boolean isLeftSide() {
		return flags[FLAGS_LEFT] > 0;
	}

	public boolean isZeroFill() {
		return flags[FLAGS_ZERO] > 0;
	}

	public boolean isGrouping() {
		return flags[FLAGS_GROUP] > 0;
	}

	public boolean isPositive() {
		return flags[FLAGS_PLUS] > 0;
	}

	public int getPositiveCount() {
		return flags[FLAGS_PLUS];
	}

	public char getPositiveChar() {
		if ((flags[FLAGS_PLUS]) > 0) {
			return '+';
		} else if ((flags[FLAGS_SPACE]) > 0) {
			return ' ';
		}
		return 0;
	}

	public char getCurrencyChar() {
		if ((flags[FLAGS_CURRENCY]) > 0) {
			return '¤';
		} else if ((flags[FLAGS_DOLLER]) > 0) {
			return '$';
		} else if ((flags[FLAGS_YEN]) > 0) {
			return '¥';
		} else if ((flags[FLAGS_EURO]) > 0) {
			return '€';
		} else if ((flags[FLAGS_POUND]) > 0) {
			return '£';
		}
		return 0;
	}

	public boolean isAlternative() {
		return flags[FLAGS_ALTERNATIVE] > 0;
	}

	public int getAlternativeCount() {
		return flags[FLAGS_ALTERNATIVE];
	}

	public int getWidth() {
		return width;
	}

	public int getPrecision() {
		return precision;
	}

	public String getLiteral() {
		return literal;
	}

	public boolean isLiteral() {
		return command == CMD_LITERAL;
	}

	public boolean isDayPeriod() {
		return command == CMD_TIME_DAY_PERIOD;
	}

	public boolean isNumberPattern() {
		return CMD_NUMBER_BIN <= command && command <= CMD_NUMBER_HEX;
	}

	public boolean isTextPattern() {
		return command == CMD_STRING;
	}

	public boolean isDateTimePattern() {
		return CMD_DATE <= command && command <= CMD_TIME_TIMEZONE;
	}

	public boolean isDatePattern() {
		return	CMD_DATE <= command && command <= CMD_DATE_WEEK;
	}

	public boolean isTimePattern() {
		return	CMD_TIME <= command && command <= CMD_TIME_TIMEZONE;
	}

	public boolean isCompositeDateTimePattern() {
		return command == CMD_DATE || command == CMD_TIME;
	}

	public int getFormatWidth() {
		return makeWidth(false);
	}

	public int getPreferredWidth() {
		return makeWidth(true);
	}

	private int makeWidth(boolean useDefault) {
		int preferred = 0;
		preferred += getPositiveChar() != 0 ? 1 : 0;
		preferred += getCurrencyChar() != 0 ? 1 : 0;
		int w = getWidth();
		if (w <= 0 && useDefault) {
			w = DEFAULT_WIDTHS[command];
		}
		preferred += w;
		if (isGrouping() && w > 3) {
			preferred += (w - 1) / 3;
		}
		int prec = getPrecision();
		if (prec > 0) {
			preferred += 1 + prec;
		}
		return preferred;
	}

	public int getPreferredHeight() {
		if (getCommand() == CMD_OTHER_LF) {
			return Math.max(1, getWidth());
		} else {
			return 0;
		}
	}

}
