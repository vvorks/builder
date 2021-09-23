package com.github.vvorks.builder.server.common.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import com.google.googlejavaformat.java.JavaFormatterOptions;

public class JavaFormatter {

	private static final String DEFAULT_INDENT = "\t";

	private static final String DEFAULT_EOL = String.format("%n");

	public String format(String source) {
		return format(source, DEFAULT_INDENT, DEFAULT_EOL);
	}

	public String format(String source, String indent) {
		return format(source, indent, DEFAULT_EOL);
	}

	public String format(String source, String indent, String eol) {
		String result;
		try {
			//一旦 GoogleのJavaFormatterで整形する
			JavaFormatterOptions option = JavaFormatterOptions.builder()
				.style(JavaFormatterOptions.Style.AOSP)
				.build();
			String formattedSource = new Formatter(option).formatSource(source);
			//インデントのスペースをタブで置き換え
			char defaultIndentChar = ' ';
			int defaultIndentCount = 4;
			BufferedReader in = new BufferedReader(new StringReader(formattedSource));
			StringBuilder sb = new StringBuilder();
			String line = readLine(in);
			while (line != null) {
				int level = getIndentCount(line, defaultIndentChar) / defaultIndentCount;
				for (int i = 0; i < level; i++) {
					sb.append(indent);
				}
				sb.append(line.subSequence(level * defaultIndentCount, line.length()));
				sb.append(eol);
				line = readLine(in);
			}
			result = sb.toString();
		} catch (FormatterException e) {
			result = source;
		}
		return result;
	}

	private String readLine(BufferedReader in) {
		try {
			return in.readLine();
		} catch (IOException e) {
			return null;
		}

	}

	private int getIndentCount(CharSequence line, char indentChar) {
		int count = 0;
		while (count < line.length() && line.charAt(count) == indentChar) {
			count++;
		}
		return count;
	}

}
