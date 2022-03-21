package com.github.vvorks.builder.client.gwt.text;

import java.util.ArrayList;
import java.util.List;

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
import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.common.text.DecimalFormatter;
import com.google.gwt.i18n.client.LocaleInfo;

public class GwtDecimalFormatter extends DecimalFormatter {

	private NumberFormat formatter;

	@Override
	protected void setParams(Params params) {
		super.setParams(params);
		prepareFormatter(params);
	}

	private void prepareFormatter(Params params) {
		List<Option<?>> options = new ArrayList<>();
		char cc = params.getCurrencyChar();
		if (cc != 0) {
			options.add(Style.CURRENCY);
			options.add(CurrencyDisplay.NARROW_SYMBOL);
			options.add(getCurrency(cc));
		}
		if (params.isGrouping()) {
			options.add(UseGrouping.TRUE);
		}
		int precision = params.getPrecision();
		if (precision >= 0) {
			options.add(MinimumFractionDigits.of(precision));
			options.add(MaximumFractionDigits.of(precision));
		}
		char pc = params.getPositiveChar();
		if (pc != 0) {
			options.add(getSignDisplay(pc));
		}
		formatter = NumberFormat.create(
				IntlUtil.getLocale(),
				options.toArray(new Option[options.size()]));
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
	public int preferredWidth() {
		Params params = getParams();
		int preferred = 0;
		preferred += params.getPositiveChar() != 0 ? 1 : 0;
		preferred += params.getCurrencyChar() != 0 ? 1 : 0;
		int width = Math.max(1, params.getWidth());
		preferred += width;
		if (params.isGrouping() && width > 3) {
			preferred += (width - 1) / 3;
		}
		int precision = params.getPrecision();
		if (precision > 0) {
			preferred += 1 + precision;
		}
		return preferred;
	}

	@Override
	public CharSequence apply(Object obj) {
		Params params = getParams();
		Number number = asNumber(obj);
		double value = number.doubleValue();
		String str = formatter.format(value);
		if (params.isZeroFill()) {
			str = insertZero(str, value);
		}
		if (params.getPositiveChar() == ' ') {
			str = str.replace('+', ' ');
		}
		str = str.replace('￥', '¥');
		return fill(str, false);
	}

	private String insertZero(String str, double value) {
		//ZERO挿入が必要か否か判定
		int width = preferredWidth();
		int len = measureText(str);
		if (!(len < width)) {
			return str;
		}
		//ZERO挿入ポイントを探索
		List<FormatPart> parts = formatter.formatToParts(value);
		int index = getIndexOfInteger(parts);
		if (index < 0) {
			return str;
		}
		//処理準備
		Params params = getParams();
		StringBuilder sb = new StringBuilder();
		//追加ZEROデータを作成
		sb.setLength(0);
		int remain = width - len;
		String ins;
		if (params.isGrouping()) {
			String sep = IntlUtil.getGroupSymbol();
			int n = 3 - parts.get(index).getValue().length();
			sb.append(Strings.repeat("0", Math.min(remain, n)));
			remain -= n;
			while (remain > 0) {
				sb.append(sep);
				n = 3;
				sb.append(Strings.repeat("0", Math.min(remain, n)));
				remain -= n;
			}
			ins = sb.reverse().toString();
		} else {
			ins = Strings.repeat("0", remain);
		}
		//文字列を編集して返す
		sb.setLength(0);
		for (int i = 0; i < index; i++) {
			sb.append(parts.get(i).getValue());
		}
		int after = sb.length();
		sb.append(ins);
		sb.append(str.substring(after));
		return sb.toString();
	}

	private int getIndexOfInteger(List<FormatPart> parts) {
		for (int i = 0; i < parts.size(); i++) {
			if (parts.get(i).getType().equals("integer")) {
				return i;
			}
		}
		return -1;
	}

}
