package com.github.vvorks.builder.server.common.sql;

import java.util.Date;

public class SqliteHelper extends SqlHelper {

	@Override
	public String getBooleanValue(boolean b) {
		return b ? "1" : "0";
	}

	public String getDateValue(Date d) {
		return String.valueOf(d.getTime());
	}

	@Override
	public String getNow() {
		return "(strftime('%s', 'now') * 1000)";
	}

	public String quote(String str) {
		int firstQuote = str.indexOf(SINGLE_QUOTE);
		if (firstQuote < 0) {
			firstQuote = str.length();
		}
		StringBuilder sb = new StringBuilder();
		sb.append(SINGLE_QUOTE);
		sb.append(str.substring(0, firstQuote));
		for (int i = firstQuote; i < str.length() ; i++) {
			char ch = str.charAt(i);
			if (ch == SINGLE_QUOTE) {
				sb.append(SINGLE_QUOTE);
			}
			sb.append(ch);
		}
		sb.append(SINGLE_QUOTE);
		return sb.toString();
	}

	@Override
	public String disableForeignKey() {
		return "PRAGMA foreign_keys = false";
	}

	@Override
	public String enableForeignKey() {
		return "PRAGMA foreign_keys = true";
	}

}
