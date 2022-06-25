package com.github.vvorks.builder.common.text;

import java.util.Date;
import java.util.List;

public class NumberFormatter extends Formatter {

	public NumberFormatter(List<Pattern> patterns) {
		super(patterns);
	}

	@Override
	public String format(Object value) {
		Number number = asNumber(value);
		StringBuilder sb = new StringBuilder();
		for (Pattern p : patterns) {
			switch (p.getCommand()) {
			case Pattern.CMD_NUMBER_BIN:
				sb.append(formatBin(number, p));
				break;
			case Pattern.CMD_NUMBER_OCT:
				sb.append(formatOct(number, p));
				break;
			case Pattern.CMD_NUMBER_HEX:
				sb.append(formatHex(number, p));
				break;
			case Pattern.CMD_NUMBER_DEC:
				sb.append(formatDec(number, p));
				break;
			case Pattern.CMD_CHAR:
				sb.append(formatChar(number, p));
				break;
			case Pattern.CMD_LITERAL:
				sb.append(p.getLiteral());
				break;
			default:
				break;
			}
		}
		String result = sb.toString();
		return result;
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

	protected String formatBin(Number number, Pattern p) {
		long value = number.longValue();
		String str = Long.toBinaryString(value);
		return fill(str, p, true);
	}

	protected String formatOct(Number number, Pattern p) {
		long value = number.longValue();
		String str = Long.toOctalString(value);
		return fill(str, p, true);
	}

	protected String formatHex(Number number, Pattern p) {
		long value = number.longValue();
		String str = Long.toHexString(value);
		return fill(str, p, true);
	}

	protected String formatDec(Number number, Pattern p) {
		//注意：偽実装
		String result;
		if (p.getPrecision() > 0 && isReal(number)) {
			double value = number.doubleValue();
			result = Double.toString(value);
		} else {
			long value = number.longValue();
			result = Long.toString(value);
		}
		return result;
	}

	private boolean isReal(Number number) {
		return number instanceof Float || number instanceof Double;
	}

	protected String formatChar(Number number, Pattern p) {
		int value = number.intValue();
		String str = new String(Character.toChars(value));
		return fill(str, p, false);
	}

}
