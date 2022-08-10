package com.github.vvorks.builder.server.common.handlebars;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Options.Buffer;

public class IndentHelper implements Helper<Object> {

	private static final String DEFAULT_INDENT = "\t";

	private final String indent;

	public IndentHelper() {
		this(DEFAULT_INDENT);
	}

	public IndentHelper(String indent) {
		this.indent = indent;
	}

	@Override
	public Object apply(Object context, Options options) throws IOException {
		Buffer buffer = options.buffer();
		CharSequence fn = options.fn();
		BufferedReader in = new BufferedReader(new StringReader(fn.toString()));
		for (String line; (line = in.readLine()) != null; ) {
			if (!line.trim().isEmpty()) {
				buffer.append(indent);
				buffer.append(line);
			}
			buffer.append("\n");
		}
		return buffer;
	}

}
