package com.github.vvorks.builder.client.common.logging;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * ブラウザコンソールにログ出力するハンドラ
 */
public class AltConsoleHandler extends Handler {

	public static void setup() {
		Logger.getGlobal().addHandler(new AltConsoleHandler());
	}

	@Override
	public void publish(LogRecord record) {
		if (!isLoggable(record)) {
			return;
		}
		log(getFormatter().format(record));
	}
	private native void log(String message) /*-{
		setTimeout(window.console.log.bind(window.console, message))
		//window.console.log(message);
	}-*/;

	@Override
	public void flush() {
	}

	@Override
	public void close() throws SecurityException {
	}

}
