package com.github.vvorks.builder.server.common.handlebars;

import java.io.IOException;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Options.Buffer;

/**
 * 出力順と処理順を反転させるヘルパー
 */
public class ReverseHelper implements Helper<Object> {

	@Override
	public Object apply(Object context, Options options) throws IOException {
		Buffer buffer = options.buffer();
		CharSequence inv = options.inverse();
		CharSequence fn = options.fn();
		buffer.append(fn);
		buffer.append(inv);
		return buffer;
	}

}
