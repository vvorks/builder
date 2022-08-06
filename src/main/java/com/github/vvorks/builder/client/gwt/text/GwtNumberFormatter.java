package com.github.vvorks.builder.client.gwt.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.vvorks.builder.client.common.ui.Metrics;
import com.github.vvorks.builder.client.gwt.intl.Currency;
import com.github.vvorks.builder.client.gwt.intl.CurrencyDisplay;
import com.github.vvorks.builder.client.gwt.intl.FormatPart;
import com.github.vvorks.builder.client.gwt.intl.IntlUtil;
import com.github.vvorks.builder.client.gwt.intl.MaximumFractionDigits;
import com.github.vvorks.builder.client.gwt.intl.MinimumFractionDigits;
import com.github.vvorks.builder.client.gwt.intl.NumberFormat;
import com.github.vvorks.builder.client.gwt.intl.Option;
import com.github.vvorks.builder.client.gwt.intl.SignDisplay;
import com.github.vvorks.builder.client.gwt.intl.Style;
import com.github.vvorks.builder.client.gwt.intl.UseGrouping;
import com.github.vvorks.builder.shared.common.lang.Strings;
import com.github.vvorks.builder.shared.common.text.NumberFormatter;
import com.github.vvorks.builder.shared.common.text.Pattern;
import com.google.gwt.i18n.client.LocaleInfo;

public class GwtNumberFormatter extends NumberFormatter {

	private Map<Pattern, NumberFormat> numberFormats = new HashMap<>();

	public GwtNumberFormatter(List<Pattern> patterns) {
		super(patterns);
	}

	private NumberFormat referNumberFormat(Pattern p) {
		NumberFormat f = numberFormats.get(p);
		if (f == null) {
			f = createNumberFormat(p);
			numberFormats.put(p, f);
		}
		return f;
	}

	private NumberFormat createNumberFormat(Pattern p) {
		List<Option<?>> options = new ArrayList<>();
		char cc = p.getCurrencyChar();
		if (cc != 0) {
			options.add(Style.CURRENCY);
			options.add(CurrencyDisplay.NARROW_SYMBOL);
			options.add(getCurrency(cc));
		}
		if (p.isGrouping()) {
			options.add(UseGrouping.TRUE);
		} else {
			options.add(UseGrouping.FALSE);
		}
		int precision = p.getPrecision();
		if (precision >= 0) {
			options.add(MinimumFractionDigits.of(precision));
			options.add(MaximumFractionDigits.of(precision));
		}
		char pc = p.getPositiveChar();
		if (pc != 0) {
			options.add(getSignDisplay(pc));
		}
		String locale = Metrics.get().getLocale();
		return NumberFormat.create(locale, options.toArray(new Option[options.size()]));
	}

	private Currency getCurrency(char cc) {
		switch (cc) {
		case '¥': return Currency.JPY;
		case '€': return Currency.EUR;
		case '£': return Currency.GBP;
		case '$': return Currency.USD;
		case '¤':
		default:
			String code = LocaleInfo
					.getCurrentLocale()
					.getNumberConstants()
					.defCurrencyCode();
			try {
				return Currency.valueOf(code);
			} catch (IllegalArgumentException e) {
				return Currency.USD;
			}
		}
	}

	private SignDisplay getSignDisplay(char pc) {
		switch (pc) {
		case '+': return SignDisplay.ALWAYS;
		case ' ': return SignDisplay.ALWAYS; //一旦'+'で整形。後処理で' 'に置換
		default:  return SignDisplay.AUTO;
		}
	}

	@Override
	protected String formatDec(Number number, Pattern p) {
		double value = number.doubleValue();
		NumberFormat f = referNumberFormat(p);
		List<FormatPart> parts = f.formatToParts(value);
		//通貨によって、自動的に小数桁が付与されるため、パターンを補正する
		if (p.getPrecision() < 0) {
			int index = IntlUtil.indexOf(parts, "fraction");
			if (index >= 0) {
				p = new Pattern(p, parts.get(index).getValue().length());
			}
		}
		String str = composeText(p, parts);
		return fill(str, p, false);
	}

	private String composeText(Pattern p, List<FormatPart> parts) {
		String zeros = getZeros(p, parts);
		//partsを合成
		StringBuilder sb = new StringBuilder();
		for (FormatPart f : parts) {
			String type = f.getType();
			String value = f.getValue();
			if (type.equals("integer") && zeros != null) {
				sb.append(zeros);
				zeros = null;
			}
			if (type.equals("plusSign") && p.getPositiveChar() == ' ') {
				sb.append(" ");
			} else if (type.equals("currency") && value.equals("￥")) {
				sb.append("¥");
			} else {
				sb.append(value);
			}
		}
		return sb.toString();
	}

	private String getZeros(Pattern p, List<FormatPart> parts) {
		String zeros = null;
		if (p.isZeroFill()) {
			//整数桁長を調べる
			int len = 0;
			int len1st = 0;
			for (FormatPart f : parts) {
				if (f.getType().equals("integer")) {
					len += f.getValue().length();
					if (len1st == 0) {
						len1st = len;
					}
				}
			}
			//zeroFill文字列を作成
			int width = p.getWidth();
			if (len < width) {
				int remain = width - len;
				if (p.isGrouping()) {
					zeros = makeGroupedZeros(remain, len1st);
				} else {
					zeros = Strings.repeat("0", remain);
				}
			}
		}
		return zeros;
	}

	private String makeGroupedZeros(int remain, int len1st) {
		StringBuilder sb = new StringBuilder();
		String sep = IntlUtil.getGroupSymbol();
		int n = 3 - len1st;
		sb.append(Strings.repeat("0", Math.min(remain, n)));
		remain -= n;
		while (remain > 0) {
			sb.append(sep);
			n = 3;
			sb.append(Strings.repeat("0", Math.min(remain, n)));
			remain -= n;
		}
		return sb.reverse().toString();
	}

}
