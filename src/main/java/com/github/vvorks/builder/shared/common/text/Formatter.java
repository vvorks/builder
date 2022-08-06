package com.github.vvorks.builder.shared.common.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.github.vvorks.builder.shared.common.lang.Asserts;
import com.github.vvorks.builder.shared.common.lang.Factory;
import com.github.vvorks.builder.shared.common.lang.Strings;
import com.github.vvorks.builder.shared.common.logging.Logger;
import com.github.vvorks.builder.shared.common.util.JacksonIterator;

public abstract class Formatter {

	protected static final Logger LOGGER = Logger.createLogger(Formatter.class);

	public static String format(CharSequence pattern, Object value) {
		return parse(pattern).format(value);
	}

	public static Formatter parse(CharSequence pattern) {
		List<Pattern> patterns = Pattern.parse(pattern);
		return toFormatter(patterns);
	}

	public static int measureText(String str) {
		return str.length(); //TODO 仮。正確には半角１、全角２として返したい。
	}

	private static Formatter toFormatter(List<Pattern> patterns) {
		Iterator<Pattern> itr = new JacksonIterator<>(patterns);
		Pattern p = itr.next();
		List<Formatter> fragments = new ArrayList<>();
		while (p != null) {
			int cmd = p.getCommand();
			if (p.isLiteral()) {
				fragments.add(newLiteralFormatter(p.getLiteral()));
				p = itr.next();
			} else if (p.isNumberPattern() || cmd == Pattern.CMD_CHAR) {
				fragments.add(newNumberFormatter(asList(p)));
				p = itr.next();
			} else if (p.isTextPattern()) {
				p = bundleTextPattern(p, itr, fragments);
			} else if (p.isDateTimePattern()) {
				p = bundleDateTimePattern(p, itr, fragments);
			} else if (cmd == Pattern.CMD_OTHER_PCT) {
				fragments.add(newLiteralFormatter("%"));
				p = itr.next();
			} else if (cmd == Pattern.CMD_OTHER_LF) {
				fragments.add(newLineFeedFormatter(p));
				p = itr.next();
			} else {
				Asserts.check(false);
			}
		}
		return bundleFragments(fragments);
	}

	private static <E> List<E> asList(E x) {
		return Collections.singletonList(x);
	}

	private static Pattern bundleTextPattern(
			Pattern p, Iterator<Pattern> itr, List<Formatter> fragments) {
		List<Pattern> tPatterns = new ArrayList<>();
		while (p != null && (p.isTextPattern() || p.isLiteral())) {
			tPatterns.add(p);
			p = itr.next();
		}
		fragments.add(newTextFormatter(tPatterns));
		return p;
	}

	private static Pattern bundleDateTimePattern(
			Pattern p, Iterator<Pattern> itr, List<Formatter> fragments) {
		List<Pattern> dtPatterns = new ArrayList<>();
		Set<Integer> dtCommands = new HashSet<>();
		while (p != null && (p.isDateTimePattern() || p.isLiteral())) {
			int c = p.getCommand();
			if (p.isDateTimePattern() && dtCommands.contains(c)) {
				break;
			}
			dtPatterns.add(p);
			dtCommands.add(c);
			p = itr.next();
		}
		fragments.add(newDateFormatter(dtPatterns));
		return p;
	}

	private static Formatter bundleFragments(List<Formatter> fragments) {
		Formatter result;
		switch (fragments.size()) {
		case 0:
			result = newLiteralFormatter("");
			break;
		case 1:
			result = fragments.get(0);
			break;
		default:
			result = newCompositeFormatter(fragments);
			break;
		}
		return result;
	}

	private static Formatter newLiteralFormatter(String literal) {
		return new LiteralFormatter(asList(new Pattern(literal)));
	}

	private static Formatter newNumberFormatter(List<Pattern> patterns) {
		if (Factory.isRegistered(NumberFormatter.class)) {
			return Factory.newInstance(NumberFormatter.class, patterns);
		}
		return new NumberFormatter(patterns);
	}

	private static Formatter newTextFormatter(List<Pattern> patterns) {
		if (Factory.isRegistered(TextFormatter.class)) {
			return Factory.newInstance(TextFormatter.class, patterns);
		}
		return new TextFormatter(patterns);
	}

	private static Formatter newDateFormatter(List<Pattern> patterns) {
		if (Factory.isRegistered(DateFormatter.class)) {
			return Factory.newInstance(DateFormatter.class, patterns);
		}
		return new DateFormatter(patterns);
	}

	private static Formatter newLineFeedFormatter(Pattern p) {
		return new LineFeedFormatter(asList(p));
	}

	private static Formatter newCompositeFormatter(List<Formatter> fragments) {
		return new FormatterBundle(fragments);
	}

	protected final List<Pattern> patterns;

	protected Formatter(List<Pattern> patterns) {
		this.patterns = patterns;
	}

	public int getPreferredWidth() {
		int preferred = 0;
		for (Pattern p : patterns) {
			preferred += p.getPreferredWidth();
		}
		return preferred;
	}

	public int getPreferredHeight() {
		return Math.max(1, getPreferredHeight0());
	}

	protected int getPreferredHeight0() {
		int preferred = 0;
		for (Pattern p : patterns) {
			preferred += p.getPreferredHeight();
		}
		return preferred;
	}

	protected String fill(String str, Pattern p, boolean zeroFill) {
		int width = p.getFormatWidth();
		int len = measureText(str);
		if (len < width) {
			int size = width - len;
			if (p.isLeftSide()) {
				str = str + Strings.repeat(" ", size);
			} else if (zeroFill && p.isZeroFill()) {
				str = Strings.repeat("0", size) + str;
			} else {
				str = Strings.repeat(" ", size) + str;
			}
		}
		return str;
	}

	public abstract String format(Object value);

}
