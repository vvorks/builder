package com.github.vvorks.builder.server.common.io;

import java.io.IOException;
import java.io.Writer;
import java.util.function.Consumer;

import com.github.vvorks.builder.server.common.util.Patterns;
import com.github.vvorks.builder.shared.common.lang.Asserts;

public class LoggerWriter extends Writer {

	private final Consumer<String> out;

	private StringBuilder lineBuffer;

	public LoggerWriter(Consumer<String> out) {
		this.out = out;
		this.lineBuffer = new StringBuilder();
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		Asserts.requireNotNull(cbuf);
		Asserts.require(0 <= off && off       <= cbuf.length);
		Asserts.require(0 <= len && off + len <= cbuf.length);
		if (len == 0) {
			return;
		}
		write0(new String(cbuf, off, len));
	}

	@Override
	public void write(String str) throws IOException {
		Asserts.requireNotNull(str);
		if (str.length() == 0) {
			return;
		}
		write0(str);
	}

	@Override
	public void write(String str, int off, int len) throws IOException {
		Asserts.requireNotNull(str);
		Asserts.require(0 <= off && off       <= str.length());
		Asserts.require(0 <= len && off + len <= str.length());
		if (len == 0) {
			return;
		}
		write0(str.substring(off, off + len));
	}

	private void write0(String str) throws IOException {
		String[] array = Patterns.EOL.split(str, -1);
		if (array.length == 1) {
			lineBuffer.append(str);
		} else {
			lineBuffer.append(array[0]);
			flush();
			for (int i = 1; i < array.length - 1; i++) {
				out.accept(array[i]);
			}
			lineBuffer.append(array[array.length - 1]);
		}
	}

	@Override
	public void flush() throws IOException {
		out.accept(lineBuffer.toString());
		lineBuffer.setLength(0);
	}

	@Override
	public void close() throws IOException {
		flush();
	}

}
