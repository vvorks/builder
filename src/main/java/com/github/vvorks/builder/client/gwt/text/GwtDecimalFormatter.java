package com.github.vvorks.builder.client.gwt.text;

import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.common.text.DecimalFormatter;
import com.google.gwt.i18n.client.NumberFormat;

public class GwtDecimalFormatter extends DecimalFormatter {

	private NumberFormat formatter;

	@Override
	protected void setParams(Params params) {
		super.setParams(params);
		prepareFormatter(params);
	}

	private void prepareFormatter(Params params) {
		StringBuilder sb = new StringBuilder();
		char cc = params.getCurrencyChar();
		if (cc != 0) {
			sb.append(cc);
		}
		int width = params.getWidth();
		String udigit = params.isZeroFill() ? "0" : "#";
		if (params.isGrouping() && width > 3) {
			for (int i = width; i > 1; i--) {
				if (i % 3 == 0) {
					sb.append(",");
				}
				sb.append(udigit);
			}
			sb.append("0");
		} else {
			sb.append(Strings.repeat(udigit, width - 1));
			sb.append("0");
		}
		int precision = params.getPrecision();
		if (precision > 0) {
			sb.append(".").append(Strings.repeat("0", precision));
		}
		String format = sb.toString();
		char pc = params.getPositiveChar();
		if (pc != 0) {
			sb.setLength(0);
			sb.append(pc).append(format);
			sb.append(';');
			sb.append('-').append(format);
			format = sb.toString();
		}
		formatter = NumberFormat.getFormat(format);
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
		Number number = asNumber(obj);
		String str = formatter.format(number);
		str = str.replace('￥', '¥');
		return fill(str, false);
	}

}
