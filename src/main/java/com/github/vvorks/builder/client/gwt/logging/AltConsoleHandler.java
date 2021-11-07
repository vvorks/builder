package com.github.vvorks.builder.client.gwt.logging;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * ブラウザコンソールにログ出力するハンドラ
 */
public class AltConsoleHandler extends Handler {

	@Override
	public void publish(LogRecord rec) {
		if (!isLoggable(rec)) {
			return;
		}
		log(getFormatter().format(rec));
	}
	private native void log(String message) /*-{
		setTimeout(window.console.log.bind(window.console, message))
		//window.console.log(message);
	}-*/;

	@Override
	public void flush() {
		//NOP
	}

	@Override
	public void close() throws SecurityException {
		//NOP
	}

}
