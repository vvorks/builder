package com.github.vvorks.builder.common.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 文字列ユーティリティ
 */
public final class Strings {

	/** 小数点 */
	private static final char DECIMAL_POINT = '.';

	public static class CharReader {

		private final CharSequence str;

		private int pos;

		public CharReader(CharSequence str) {
			this.str = str;
			this.pos = 0;
		}

		public int read() {
			return pos < str.length() ? str.charAt(pos++) : -1;
		}

		public int position() {
			return pos;
		}
	}

	/**
	 * このクラスはインスタンス化されない
	 */
	private Strings() {
	}

	/**
	 * C言語ライクな文字列のフォーマットを行う.
	 *
	 * <p>
	 * GWTのJREでは{@link String#format(String, Object...)}がサポートされて
	 * いないため、そのサブセットを提供する.
	 * </p>
	 *
	 * <table border="1">
	 * <caption>サポートフォーマット</caption>
	 * <tr><td>%d</td><td>１０進数出力</td></tr>
	 * <tr><td>%o</td><td>　８進数出力</td></tr>
	 * <tr><td>%x, %X</td><td>１６進数出力（%Xの場合、A-Fは大文字で出力される）</td></tr>
	 * <tr><td></td><td>１６進数出力</td></tr>
	 * <tr><td>%e, %f, %g</td><td>浮動小数出力</td></tr>
	 * <tr><td>%s</td><td>文字列出力</td></tr>
	 * <tr><td>%c</td><td>１文字出力</td></tr>
	 * <tr><td>%%</td><td>%の出力</td></tr>
	 * </table>
	 *
	 * <table border="1">
	 * <caption>サポートプレフィックス</caption>
	 * <tr><td>-</td><td>左寄せ</td></tr>
	 * <tr><td>+</td><td>符号出力</td></tr>
	 * <tr><td>0</td><td>ゼロ埋め</td></tr>
	 * <tr><td>数値</td><td>桁長および精度。ただし、出力文字数が桁長を超えた場合には無視される</td></tr>
	 * </table>
	 *
	 * @param msg
	 * 		フォーマット文字列
	 * @param args
	 * 		引数の並び
	 * @return
	 * 		フォーマット化された文字列
	 */
	public static String sprintf(String msg, Object... args) {
		StringBuilder sb = new StringBuilder(msg.length());
		CharReader cursor = new CharReader(msg);
		int ch = cursor.read();
		int index = 0;
		while (ch != -1) {
			//copy non format text
			while (ch != -1 && ch != '%') {
				sb.append((char) ch);
				ch = cursor.read();
			}
			//exit if eof
			if (ch == -1) {
				break;
			}
			ch = cursor.read();
			//init flags
			boolean left = false;
			boolean plus = false;
			boolean zero = false;
			int digits = 0;
			int decimal = 0;
			//process justify
			if (ch == '-') {
				left = true;
				ch = cursor.read();
			}
			//process sign/zero
			if (ch == '+') {
				plus = true;
				ch = cursor.read();
			} else if (ch == '0') {
				zero = true;
				ch = cursor.read();
			}
			//process digit
			while ('0' <= ch && ch <= '9') {
				digits = digits * 10 + (ch - '0');
				ch = cursor.read();
			}
			if (ch == DECIMAL_POINT) {
				ch = cursor.read();
				while ('0' <= ch && ch <= '9') {
					decimal = decimal * 10 + (ch - '0');
					ch = cursor.read();
				}
			}
			//exit if eof
			if (ch == -1) {
				break;
			}
			//process format
			Object arg = args[index++];
			String s;
			String sign;
			int len;
			switch (ch) {
			case 'c':
				if (arg instanceof Character) {
					char c = ((Character) arg).charValue();
					sb.append(c);
				} else if (arg instanceof Number) {
					char c = (char)(((Number) arg).intValue());
					sb.append(c);
				} else {
					sb.append(String.valueOf(arg));
				}
				break;
			case 'd':
				if (arg instanceof Number) {
					long d = ((Number) arg).longValue();
					if (d < 0) {
						s = Long.toString(-d);
						sign = "-";
					} else {
						s = Long.toString(+d);
						sign = plus ? "+" : "";
					}
					len = digits - sign.length() - s.length();
					if (zero) {
						sb.append(sign);
						sb.append(Strings.repeat("0", len));
						sb.append(s);
					} else if (left) {
						sb.append(sign);
						sb.append(s);
						sb.append(Strings.repeat(" ", len));
					} else {
						sb.append(Strings.repeat(" ", len));
						sb.append(sign);
						sb.append(s);
					}
				} else {
					sb.append(String.valueOf(arg));
				}
				break;
			case 'o':
				if (arg instanceof Number) {
					long o = ((Number) arg).longValue();
					s = Long.toOctalString(o);
					len = digits - s.length();
					if (len < 0) {
						sb.append(s);
					} else if (zero) {
						sb.append(Strings.repeat("0", len));
						sb.append(s);
					} else if (left) {
						sb.append(s);
						sb.append(Strings.repeat(" ", len));
					} else {
						sb.append(Strings.repeat(" ", len));
						sb.append(s);
					}
				} else {
					sb.append(String.valueOf(arg));
				}
				break;
			case 'x':
			case 'X':
				if (arg instanceof Number) {
					long x = ((Number) arg).longValue();
					if (ch == 'x') {
						s = Long.toHexString(x).toLowerCase();
					} else {
						s = Long.toHexString(x).toUpperCase();
					}
					len = digits - s.length();
					if (len < 0) {
						sb.append(s);
					} else if (zero) {
						sb.append(Strings.repeat("0", len));
						sb.append(s);
					} else if (left) {
						sb.append(s);
						sb.append(Strings.repeat(" ", len));
					} else {
						sb.append(Strings.repeat(" ", len));
						sb.append(s);
					}
				} else {
					sb.append(String.valueOf(arg));
				}
				break;
			case 'e':
			case 'f':
			case 'g':
				if (arg instanceof Number) {
					double g = ((Number) arg).doubleValue();
					//TODO WORK support real formats (using com.google.gwt.i18n.client.NumberFormat).
					s = Double.toString(g);
					len = digits + 1 + decimal - s.length();
					if (left) {
						sb.append(s);
						sb.append(Strings.repeat(" ", len));
					} else {
						sb.append(Strings.repeat(" ", len));
						sb.append(s);
					}
				} else {
					sb.append(String.valueOf(arg));
				}
				break;
			case 's':
				if (arg instanceof Date) {
					s = formatDate((Date) arg, digits);
				} else if (arg instanceof Long) {
					s = formatDate(new Date(((Long) arg).longValue()), digits);
				} else {
					s = Objects.toString(arg);
				}
				len = digits - s.length();
				if (left) {
					sb.append(s);
					sb.append(Strings.repeat(" ", len));
				} else {
					sb.append(Strings.repeat(" ", len));
					sb.append(s);
				}
				break;
			case '%':
				sb.append('%');
				break;
			}
			ch = cursor.read();
		}
		return sb.toString();
	}

	@SuppressWarnings("deprecation")
	private static String formatDate(Date time, int digits) {
		StringBuilder sb = new StringBuilder();
		String year = "0000" + ((time.getYear() + 1900) - 2000);
		String month = "0000" + (time.getMonth() + 1);
		String day = "0000" + time.getDate();
		String hour = "0000" + time.getHours();
		String min = "0000" + time.getMinutes();
		String sec = "0000" + time.getSeconds();
		String msec = "0000" + (time.getTime() % 1000);
		if (digits == 5) {			// hh:mm
			sb.append(hour.substring(hour.length() - 2));
			sb.append(":");
			sb.append(min.substring(min.length() - 2));
		} else if (digits == 8) {			// hh:mm:ss
			sb.append(hour.substring(hour.length() - 2));
			sb.append(":");
			sb.append(min.substring(min.length() - 2));
			sb.append(":");
			sb.append(sec.substring(sec.length() - 2));
		} else if (digits == 10) {	// yyyy/mm/dd
			sb.append(year.substring(year.length() - 4));
			sb.append("/");
			sb.append(month.substring(month.length() - 2));
			sb.append("/");
			sb.append(day.substring(day.length() - 2));
		} else if (digits == 11) {	// mm/dd hh:mm
			sb.append(month.substring(month.length() - 2));
			sb.append("/");
			sb.append(day.substring(day.length() - 2));
			sb.append(" ");
			sb.append(hour.substring(hour.length() - 2));
			sb.append(":");
			sb.append(min.substring(min.length() - 2));
		} else if (digits == 14) {	// mm/dd hh:mm:ss
			sb.append(month.substring(month.length() - 2));
			sb.append("/");
			sb.append(day.substring(day.length() - 2));
			sb.append(" ");
			sb.append(hour.substring(hour.length() - 2));
			sb.append(":");
			sb.append(min.substring(min.length() - 2));
			sb.append(":");
			sb.append(sec.substring(sec.length() - 2));
		} else if (digits == 19) {	// yyyy/mm/dd hh:mm:ss
			sb.append(year.substring(year.length() - 4));
			sb.append("/");
			sb.append(month.substring(month.length() - 2));
			sb.append("/");
			sb.append(day.substring(day.length() - 2));
			sb.append(" ");
			sb.append(hour.substring(hour.length() - 2));
			sb.append(":");
			sb.append(min.substring(min.length() - 2));
			sb.append(":");
			sb.append(sec.substring(sec.length() - 2));
		} else {					// yyyy/mm/dd hh:mm:ss.zzz
			sb.append(year.substring(year.length() - 4));
			sb.append("/");
			sb.append(month.substring(month.length() - 2));
			sb.append("/");
			sb.append(day.substring(day.length() - 2));
			sb.append(" ");
			sb.append(hour.substring(hour.length() - 2));
			sb.append(":");
			sb.append(min.substring(min.length() - 2));
			sb.append(":");
			sb.append(sec.substring(sec.length() - 2));
			sb.append(".");
			sb.append(msec.substring(msec.length() - 3));
		}
		return sb.toString();
	}

	/**
	 * 繰り返し文字列を作成する.
	 *
	 * @param base
	 * 		元文字列。nullまたは空文字が指定された場合、繰り返し回数に
	 * 		かかわらず空文字列が作成される.
	 * @param count
	 * 		繰り返し回数。０以下の場合、元文字列にかかわらず空文字列が
	 * 		作成される.
	 * @return
	 * 		元文字列が回数分繰り返された文字列
	 */
	public static String repeat(String base, int count) {
		if (base == null || base.length() == 0 || count <= 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder(base.length() * count);
		for (int i = 0; i < count; i++) {
			sb.append(base);
		}
		return sb.toString();
	}

	/**
	 * 文字列を反転する.
	 *
	 * @param str
	 * 		文字列
	 * @return
	 * 		順序が逆になった文字列
	 */
	public static String reverse(String str) {
		if (str == null) {
			return str;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = str.length() - 1; i >= 0; i--) {
			char ch = str.charAt(i);
			if (Character.isLowSurrogate(ch) && i > 0) {
				sb.append(str.charAt(--i));
			}
			sb.append(ch);
		}
		return sb.toString();
	}

	/**
	 * 文字列が空か否かを取得する.
	 *
	 * @param str
	 * 		文字列
	 * @return
	 * 		文字列がnull又は空の場合、真
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}

	/**
	 * 先頭１字を大文字にする.
	 *
	 * @param str
	 * 		文字列
	 * @return
	 * 		先頭１字を大文字化した文字列
	 */
	public static String toFirstUpper(String str) {
		if (isEmpty(str)) {
			return str;
		}
		char ch = str.charAt(0);
		if (Character.isUpperCase(ch)) {
			return str;
		} else {
			return Character.toUpperCase(ch) + str.substring(1);
		}
	}

	/**
	 * 先頭１字を小文字にする.
	 *
	 * @param str
	 * 		文字列
	 * @return
	 * 		先頭１字を小文字化した文字列
	 */
	public static String toFirstLower(String str) {
		if (isEmpty(str)) {
			return str;
		}
		char ch = str.charAt(0);
		if (Character.isLowerCase(ch)) {
			return str;
		} else {
			return Character.toLowerCase(ch) + str.substring(1);
		}
	}

	/**
	 * キャメルケースの文字列を大文字のスネークケースに変換する.
	 *
	 * <table border="1">
	 * <caption>例</caption>
	 * <tr><td>inputStreamReader</td><td>INPUT_STREAM_READER</td></tr>
	 * <tr><td>InputStreamReader</td><td>INPUT_STREAM_READER</td></tr>
	 * <tr><td>DOMObject</td><td>DOM_OBJECT</td></tr>
	 * </table>
	 *
	 * @param str
	 * 		キャメルケース文字列
	 * @return
	 * 		スネークケース文字列
	 */
	public static String toUpperSnake(String str) {
		if (isEmpty(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str.length() * 3 / 2);
		String[] array = splitCamel(str);
		sb.append(array[0].toUpperCase());
		for (int i = 1; i < array.length; i++) {
			sb.append('_');
			sb.append(array[i].toUpperCase());
		}
		return sb.toString();
	}

	/** 空文字列配列 */
	private static final String[] EMPTY_STRINGS = {""};

	/**
	 * キャメルケース文字列内の単語を配列に分解する
	 *
	 * @param str
	 * 		キャメルケース文字列
	 * @return
	 * 		str内の単語配列
	 */
	public static String[] splitCamel(String str) {
		if (isEmpty(str)) {
			return EMPTY_STRINGS;
		}
		CharReader in = new CharReader(str);
		List<String> result = new ArrayList<String>();
		int spos = in.position();
		int epos = spos;
		int curr = in.read();
		while (curr != -1 && !Character.isUpperCase((char)curr)) {
			epos = in.position();
			curr = in.read();
		}
		if (spos < epos) {
			result.add(str.substring(spos, epos));
			spos = epos;
		}
		while (curr != -1) {
			spos = epos;
			curr = in.read();
			if (curr != -1 && Character.isUpperCase((char)curr)) {
				int bpos = epos;
				while (curr != -1 && Character.isUpperCase((char)curr)) {
					bpos = epos;
					epos = in.position();
					curr = in.read();
				}
				if (curr == -1) {
					bpos = in.position();
				}
				if (spos < bpos) {
					result.add(str.substring(spos, bpos));
				}
				spos = bpos;
			}
			while (curr != -1 && !Character.isUpperCase((char)curr)) {
				epos = in.position();
				curr = in.read();
			}
			if (curr == -1) {
				epos = in.position();
			}
			if (spos < epos) {
				result.add(str.substring(spos, epos));
			}
		}
		return result.toArray(new String[result.size()]);
	}

	/**
	 * ２つの文字列の共通部分文字列長を返す.
	 *
	 * @param a
	 * 		文字列１
	 * @param b
	 * 		文字列２
	 * @return
	 * 		共通部分文字列長
	 */
	public static int commonLength(String a, String b) {
		if (a == null || b == null) {
			return 0;
		}
		int n = Math.min(a.length(), b.length());
		for (int i = 0; i < n; i++) {
			if (a.charAt(i) != b.charAt(i)) {
				return i;
			}
		}
		return n;
	}

	/**
	 * 複数の文字列を比較する
	 *
	 * @param s1
	 * 		文字列１
	 * @param candidates
	 * 		比較対象文字列の並び
	 * @return
	 * 		s1とcandidatesのいずれかが同値の場合、真
	 */
	public static boolean equals(String s1, String... candidates) {
		if (s1 == null) {
			for (String str : candidates) {
				if (str == null) {
					return true;
				}
			}
		} else {
			for (String str : candidates) {
				if (s1.equals(str)) {
					return true;
				}
			}
		}
		return false;
	}

	/** 複数形が不規則変化するサフィックス */
	private static final String[] IRREGULAR_TAILS = {
			"child", "children",
			"person", "people",
			"man", "men",
			"axis", "axes",
			"money", "monies",
	};

	/** 複数形にはesを付与するサフィックス */
	private static final String[] ES_TAILS = {"s", "sh", "ch", "x"};

	/**
	 * 単語を複数形にする
	 *
	 * @param str
	 * 		単語
	 * @return
	 * 		複数形の単語
	 */
	public static String toPlural(String str) {

		//不規則形の対応
		int len = str.length();
		for (int i = 0; i < IRREGULAR_TAILS.length ; i += 2) {
			String suffix = IRREGULAR_TAILS[i + 0];
			if (str.endsWith(suffix)) {
				int index = len - suffix.length();
				String prefix = str.substring(0, index);
				String plural = IRREGULAR_TAILS[i + 1];
				if (Character.isUpperCase(str.charAt(index))) {
					plural = toFirstUpper(plural);
				}
				return prefix + plural;
			}
		}

		// "sis"で終わる単語の場合、"ses"に置換する.
		if (str.endsWith("sis")) {
			return str.substring(0, len - 3) + "ses";
		}

		// "s", "sh", "ch", "x"で終わる単語の場合、"es"をつける
		for (String t : ES_TAILS) {
			if (str.endsWith(t)) {
				return str + "es";
			}
		}

		// "f"で終わる単語の場合、"f"を除いて"ves"を付与する.
		if (str.endsWith("f")) {
			return str.substring(0, len - 1) + "ves";
		}

		// "fe"で終わる単語の場合、"fe"を除いて"ves"を付与する.
		if (str.endsWith("fe")) {
			return str.substring(0, len - 2) + "ves";
		}

		// <子音> + "y"の場合、"y"を除いて"ies"を付与する
		if (len > 1 && str.endsWith("y") && "aiueo".indexOf(str.charAt(len-2)) == -1) {
			return str.substring(0, len - 1) + "ies";
		}

		//通常の場合、単に"s"を付与する
		return str + "s";
	}

	private static final String ESCAPE_CHARS = "<>&\"'\n";

	private static final String[] ESCAPE_STRINGS = {"&lt;", "&gt;", "&amp;", "&quot;", "&#039;", "<br/>"};

	/**
	 * 文字列をHTML ESCAPE化する
	 *
	 * @param str
	 * 		元の文字列
	 * @return
	 * 		HTML ESCAPE化された文字列
	 */
	public static String escapeHtml(String str) {
		//空文字チェック
		if (isEmpty(str)) {
			//文字列が空の場合はエスケープ不要なので、元の文字列をそのまま返す
			return str;
		}
		//要エスケープのチェック
		int i = 0;
		int n = str.length();
		for (; i < n; i++) {
			char ch = str.charAt(i);
			if (ESCAPE_CHARS.indexOf(ch) >= 0) {
				break;
			}
		}
		if (i == n) {
			//エスケープ不要なので、元の文字列をそのまま返す
			return str;
		}
		StringBuilder sb = new StringBuilder(n);
		sb.append(str.substring(0, i));
		for (; i < n; i++) {
			char ch = str.charAt(i);
			int index = ESCAPE_CHARS.indexOf(ch);
			if (index >= 0) {
				sb.append(ESCAPE_STRINGS[index]);
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

//	/**
//	 * HTML ESCAPE化された文字列を文字列に戻す.
//	 *
//	 * @param html
//	 * 		HTML ESCAPE化された文字列
//	 * @return
//	 * 		元の文字列
//	 */
//	public static String unescapeHtml(String html) {
//		if (isEmpty(html)) {
//			return html;
//		}
//		int n = html.length();
//		StringBuilder sb = new StringBuilder(n);
//		int spos = -1;
//		for (int i = 0; i < n; i++) {
//			char ch = html.charAt(i);
//			sb.append(ch);
//			switch (ch) {
//			case '&':
//				spos = sb.length() - 1;
//				break;
//			case ';':
//				if (spos == -1) {
//					break;
//				}
//				String sym = sb.substring(spos);
//				if (sym.equalsIgnoreCase("&lt;")) {
//					sb.setLength(spos);
//					sb.append('<');
//				} else if (sym.equalsIgnoreCase("&gt;")) {
//					sb.setLength(spos);
//					sb.append('>');
//				} else if (sym.equalsIgnoreCase("&amp;")) {
//					sb.setLength(spos);
//					sb.append('&');
//				} else if (sym.equalsIgnoreCase("&quot;")) {
//					sb.setLength(spos);
//					sb.append('"');
//				} else if (sym.equalsIgnoreCase("&#039;")) {
//					sb.setLength(spos);
//					sb.append('\'');
//				}
//				spos = -1;
//				break;
//			default:
//				break;
//			}
//		}
//		return sb.toString();
//	}

	/**
	 * 文字列が、指定された接頭辞のいずれかで始まるかどうかを判定する.
	 *
	 * @param name
	 * 		文字列
	 * @param prefixes
	 * 		接頭辞の並び
	 * @return
	 * 		文字列が、指定された接頭辞のいずれかで始まる場合、真。
	 */
	public static boolean startsWith(String name, String... prefixes) {
		for (String prefix : prefixes) {
			if (name.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 文字列が、指定された接頭辞のいずれかで始まるかどうかを、大文字小文字を区別せずに判定する.
	 *
	 * @param name
	 * 		文字列
	 * @param prefixes
	 * 		接頭辞の並び
	 * @return
	 * 		文字列が、指定された接頭辞のいずれかで始まる場合、真。（大文字小文字は区別されない）
	 */
	public static boolean startsWithIgnoreCase(String name, String... prefixes) {
		String lowerName = name.toLowerCase();
		for (String prefix : prefixes) {
			if (lowerName.startsWith(prefix.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 文字列が、指定された接尾辞のいずれかで始まるかどうかを判定する.
	 *
	 * @param name
	 * 		文字列
	 * @param suffixes
	 * 		接尾辞の並び
	 * @return
	 * 		文字列が、指定された接尾辞のいずれかで始まる場合、真。
	 */
	public static boolean endsWith(String name, String... suffixes) {
		for (String suffix : suffixes) {
			if (name.endsWith(suffix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 文字列が、指定された接尾辞のいずれかで始まるかどうかを、大文字小文字を区別せずに判定する.
	 *
	 * @param name
	 * 		文字列
	 * @param suffixes
	 * 		接尾辞の並び
	 * @return
	 * 		文字列が、指定された接尾辞のいずれかで始まる場合、真。（大文字小文字は区別されない）
	 */
	public static boolean endsWithIgnoreCase(String name, String... suffixes) {
		String lowerName = name.toLowerCase();
		for (String suffix : suffixes) {
			if (lowerName.endsWith(suffix.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * タブ文字をスペースに置き換える
	 *
	 * @param s
	 * 		文字列
	 * @param tabSize
	 * 		タブサイズ
	 * @return
	 * 		タブ文字がスペースに置換された文字列
	 */
	public static String detab(String s, int tabSize) {
		if (s == null || s.isEmpty()) {
			return s;
		}
		int index = s.indexOf('\t');
		if (index == -1) {
			return s;
		}
		StringBuilder sb = new StringBuilder(s.substring(0, index));
		for (int i = index; i < s.length(); i++) {
			char ch = s.charAt(i);
			if (s.charAt(i) == '\t') {
				sb.append(repeat(" ", tabSize - sb.length() % tabSize));
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	/**
	 * スネークケースの文字列をキャメルケースの文字列に変換する
	 *
	 * @param snake
	 * 		スネークケースの文字列
	 * @param upperFirst
	 * 		先頭を大文字にする場合、真
	 * @return
	 * 		キャメルケースの文字列
	 */
	public static String toCamel(String snake, boolean upperFirst) {
		if (snake == null || snake.isEmpty()) {
			return snake;
		}
		String[] words = snake.split("_");
		StringBuilder sb = new StringBuilder(snake.length());
		if (upperFirst) {
			sb.append(toFirstUpper(words[0].toLowerCase()));
		} else {
			sb.append(words[0].toLowerCase());
		}
		for (int i = 1; i < words.length; i++) {
			if (words[i].isEmpty()) {
				sb.append("_");
			} else {
				sb.append(toFirstUpper(words[i].toLowerCase()));
			}
		}
		return sb.toString();
	}

	/**
	 * 桁長を指定幅にそろえる（空白で埋める）
	 *
	 * @param s
	 * 		文字列
	 * @param len
	 * 		幅
	 * @return
	 * 		右側に空白を付与した文字列
	 */
	public static String pad(String s, int len) {
		int n;
		if (s == null) {
			n = 0;
		} else {
			n = s.length();
		}
		if (n == 0) {
			return repeat(" ", len);
		} else if (n < len) {
			return s + repeat(" ", len - n);
		} else {
			return s;
		}
	}

	/**
	 * 文字列がキャメルケースか否かを判定する
	 *
	 * @param str
	 * 		文字列
	 * @return
	 * 		文字列がキャメルケースの場合、真
	 */
	public static boolean isCamel(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		int upperCount = 0;
		int lowerCount = 0;
		int underCount = 0;
		int spaceCount = 0;
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (Character.isUpperCase(ch)) {
				upperCount++;
			} else if (Character.isLowerCase(ch)) {
				lowerCount++;
			} else if (ch == '_') {
				underCount++;
			} else if (Character.isWhitespace(ch)) {
				spaceCount++;
			}
		}
		return underCount == 0 && spaceCount == 0 && upperCount > 0 && lowerCount > 0;
	}

	public static String concat(String separator, Object... objs) {
		if (objs == null || objs.length == 0 || (objs.length == 1 && objs[0] == null)) {
			return "";
		} else if (objs.length == 1) {
			return objs[0].toString();
		}
		return concat0(separator, Arrays.asList(objs));
	}

	public static String concat(String separator, Collection<?> list) {
		if (list == null || list.size() == 0 || (list.size() == 1 && list.iterator().next() == null)) {
			return "";
		} else if (list.size() == 1) {
			return list.iterator().next().toString();
		}
		return concat0(separator, list);
	}

	private static String concat0(String separator, Collection<?> list) {
		StringBuilder sb = new StringBuilder();
		for (Object obj : list) {
			if (obj != null) {
				String s = obj.toString();
				if (s != null && !s.isEmpty()) {
					if (sb.length() > 0) {
						sb.append(separator);
					}
					sb.append(s);
				}
			}
		}
		return sb.toString();
	}

}
