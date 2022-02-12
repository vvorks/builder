package com.github.vvorks.builder.common.text;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

import com.github.vvorks.builder.common.lang.CharSequenceReader;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.common.logging.Logger;


public abstract class Formatter {

	protected static final Logger LOGGER = Logger.createLogger(Formatter.class);

	private static final String CODE_SYMBOLS = "bodxcsGYmDAPHIMSLZFRT%n";

	public static class Params {

		public static final String FLAG_SYMBOLS = "-0+ ,$¥€£¤";

		public static final int FLAGS_LEFT     = 0x00000001;

		public static final int FLAGS_ZERO     = 0x00000002;

		public static final int FLAGS_PLUS     = 0x00000004;

		public static final int FLAGS_SPACE    = 0x00000008;

		public static final int FLAGS_GROUP    = 0x00000010;

		public static final int FLAGS_DOLLER   = 0x00000020;

		public static final int FLAGS_YEN      = 0x00000040;

		public static final int FLAGS_EURO     = 0x00000080;

		public static final int FLAGS_POUND    = 0x00000100;

		public static final int FLAGS_CURRENCY = 0x00000200;

		public static final int FLAGS_NOTFILL  = 0x80000000;

		private int flags;

		private int width;

		private int precision;

		public boolean isLeftSide() {
			return (flags & FLAGS_LEFT) != 0;
		}

		public boolean isZeroFill() {
			return (flags & FLAGS_ZERO) != 0;
		}

		public boolean isNotFill() {
			return (flags & FLAGS_NOTFILL) != 0;
		}

		public boolean isGrouping() {
			return (flags & FLAGS_GROUP) != 0;
		}

		public char getPositiveChar() {
			if ((flags & FLAGS_PLUS) != 0) {
				return '+';
			} else if ((flags & FLAGS_SPACE) != 0) {
				return ' ';
			}
			return 0;
		}

		public char getCurrencyChar() {
			if ((flags & FLAGS_CURRENCY) != 0) {
				return '¤';
			} else if ((flags & FLAGS_DOLLER) != 0) {
				return '$';
			} else if ((flags & FLAGS_YEN) != 0) {
				return '¥';
			} else if ((flags & FLAGS_EURO) != 0) {
				return '€';
			} else if ((flags & FLAGS_POUND) != 0) {
				return '£';
			}
			return 0;
		}

		public void setFlag(int flag, boolean on) {
			if (on) {
				this.flags |= flag;
			} else {
				this.flags &= ~flag;
			}
		}

		private void setFlags(int flags) {
			this.flags = flags;
		}

		public int getWidth() {
			return width;
		}

		private void setWidth(int width) {
			this.width = width;
		}

		public int getPrecision() {
			return precision;
		}

		private void setPrecision(int precision) {
			this.precision = precision;
		}

	}

	private static final Map<Integer, IntFunction<Formatter>> FACTORIES = new HashMap<>();
	static {
		FACTORIES.put((int) 'b', code -> newBinaryFormatter(code));
		FACTORIES.put((int) 'o', code -> newOctalFormatter(code));
		FACTORIES.put((int) 'd', code -> newDecimalFormatter(code));
		FACTORIES.put((int) 'x', code -> newHexadecimalFormatter(code));
		FACTORIES.put((int) 'c', code -> newCharFormatter(code));
		FACTORIES.put((int) 's', code -> newStringFormatter(code));
		FACTORIES.put((int) '%', code -> new ConstFormatter("%"));
		FACTORIES.put((int) 'n', code -> new LineFormatter());
	}

	private static Formatter newBinaryFormatter(int code) {
		if (Factory.isRegistered(BinaryFormatter.class)) {
			return Factory.newInstance(BinaryFormatter.class, code);
		} else {
			return new BinaryFormatter();
		}
	}

	private static Formatter newOctalFormatter(int code) {
		if (Factory.isRegistered(OctalFormatter.class)) {
			return Factory.newInstance(OctalFormatter.class, code);
		} else {
			return new OctalFormatter();
		}
	}

	private static Formatter newDecimalFormatter(int code) {
		if (Factory.isRegistered(DecimalFormatter.class)) {
			return Factory.newInstance(DecimalFormatter.class, code);
		} else {
			return new DecimalFormatter();
		}
	}

	private static Formatter newHexadecimalFormatter(int code) {
		if (Factory.isRegistered(HexadecimalFormatter.class)) {
			return Factory.newInstance(HexadecimalFormatter.class, code);
		} else {
			return new HexadecimalFormatter();
		}
	}

	private static Formatter newCharFormatter(int code) {
		if (Factory.isRegistered(CharFormatter.class)) {
			return Factory.newInstance(CharFormatter.class, code);
		} else {
			return new CharFormatter();
		}
	}

	private static Formatter newStringFormatter(int code) {
		if (Factory.isRegistered(StringFormatter.class)) {
			return Factory.newInstance(StringFormatter.class, code);
		} else {
			return new StringFormatter();
		}
	}

	public static CharSequence format(CharSequence pattern, Object value) {
		return parse(pattern).apply(value);
	}

	public static Formatter parse(CharSequence pattern) {
		CharSequenceReader in = new CharSequenceReader(pattern);
		List<Formatter> fragments = new ArrayList<>();
		while (in.hasNext()) {
			if (in.peek() != '%') {
				fragments.add(parseConstFragment(in));
			}
			if (in.peek() == '%') {
				int spos = in.position();
				in.skip();
				Params params = parseFormatterParams(in);
				int ch = in.next();
				IntFunction<Formatter> factory = FACTORIES.get(ch);
				if (factory != null) {
					Formatter newFormatter = factory.apply(ch);
					newFormatter.setParams(params);
					fragments.add(newFormatter);
				} else {
					int epos = in.position();
					fragments.add(new ConstFormatter(in.substring(spos, epos)));
				}
			}
		}
		Formatter result;
		switch (fragments.size()) {
		case 0:
			result = new ConstFormatter("");
			break;
		case 1:
			result = fragments.get(0);
			break;
		default:
			if (StringsFormatter.isAcceptable(fragments)) {
				result = new StringsFormatter(fragments);
			} else {
				result = new CompositeFormatter(fragments);
			}
			break;
		}
		return result;
	}

	private static Formatter parseConstFragment(CharSequenceReader in) {
		int ch = in.peek();
		int spos = in.position();
		while (ch != -1 && ch != '%') {
			in.skip();
			ch = in.peek();
		}
		int epos = in.position();
		return new ConstFormatter(in.substring(spos, epos));
	}

	private static Params parseFormatterParams(CharSequenceReader in) {
		Params params = new Params();
		parseFlags(in, params);
		parseWidth(in, params);
		return params;
	}

	private static void parseFlags(CharSequenceReader in, Params params) {
		int flags = 0;
		int index;
		while ((index = Params.FLAG_SYMBOLS.indexOf((char)in.peek())) >= 0) {
			flags |= 1 << index;
			in.skip();
		}
		params.setFlags(flags);
	}

	private static void parseWidth(CharSequenceReader in, Params params) {
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
		int w = 0;
		int p = 0;
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
		params.setWidth(w);
		params.setPrecision(p);
	}

	private Params params;

	public Params getParams() {
		return params;
	}

	protected void setParams(Params params) {
		this.params = params;
	}

	public int preferredWidth() {
		return params.getWidth();
	}

	public int preferredHeight() {
		return 1;
	}

	public abstract CharSequence apply(Object obj);

	protected int measureText(CharSequence text) {
		return text.length(); //TODO 仮
	}

	protected String asString(Object obj) {
		return String.valueOf(obj);
	}

	protected Number asNumber(Object obj) {
		if (obj instanceof Number) {
			return (Number)obj;
		} else if (obj instanceof Character) {
			return ((Character) obj).charValue() & 0xFFFF;
		} else if (obj instanceof Date) {
			return ((Date)obj).getTime();
		} else if (obj == null) {
			return 0L;
		} else {
			String str = obj.toString();
			try {
				return Long.decode(str);
			} catch (NumberFormatException err) {
			}
			try {
				return Double.parseDouble(str);
			} catch (NumberFormatException err) {
			}
		}
		throw new IllegalArgumentException();
	}

	protected String fill(String str, boolean zeroFill) {
		if (params.isNotFill()) {
			return str;
		}
		int width = preferredWidth();
		int len = measureText(str);
		if (len < width) {
			int size = width - len;
			if (params.isLeftSide()) {
				str = str + Strings.repeat(" ", size);
			} else if (zeroFill && params.isZeroFill()) {
				str = Strings.repeat("0", size) + str;
			} else {
				str = Strings.repeat(" ", size) + str;
			}
		}
		return str;
	}

}
