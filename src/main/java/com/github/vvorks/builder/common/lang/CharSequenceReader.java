package com.github.vvorks.builder.common.lang;

public class CharSequenceReader {

	private final CharSequence str;

	private int pos;

	public CharSequenceReader(CharSequence str) {
		this.str = str != null ? str : "";
		this.pos = 0;
	}

	public boolean hasNext() {
		return pos < str.length();
	}

	public int next() {
		if (pos < str.length()) {
			return str.charAt(pos++);
		}
		return -1;
	}

	public int peek() {
		if (pos < str.length()) {
			return str.charAt(pos);
		}
		return -1;
	}

	public void skip() {
		if (pos < str.length()) {
			pos++;
		}
	}

	public int position() {
		return pos;
	}

	public CharSequence substring(int spos, int epos) {
		return str.subSequence(spos, epos);
	}

}