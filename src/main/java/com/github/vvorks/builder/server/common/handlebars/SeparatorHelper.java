package com.github.vvorks.builder.server.common.handlebars;

import java.io.IOException;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Options.Buffer;

/**
 * ループ内において、各ループの出力間にテキストを挟み込むためのヘルパ
 */
public class SeparatorHelper implements Helper<Object> {

	@Override
	public Object apply(Object context, Options options) throws IOException {
		CharSequence last = (CharSequence) options.context.get("@last");
		Buffer buffer = options.buffer();
		if (last.length() == 0) {
			buffer.append(options.fn());
		} else {
			buffer.append(options.inverse());
		}
		return buffer;
	}

}
