package com.github.vvorks.builder.server.common.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLogger implements com.github.vvorks.builder.common.logging.Logger {

	private final Logger logger;

	private boolean feedNext;

	public Slf4jLogger(Class<?> cls) {
		logger = LoggerFactory.getLogger(cls.getPackage().getName());
	}

	@Override
	public void trace(String format, Object... args) {
		if (logger.isTraceEnabled()) {
			insertFeed();
			logger.trace(format(format, args));
		}
	}

	@Override
	public void verbose(String format, Object... args) {
		if (logger.isTraceEnabled()) {
			insertFeed();
			logger.trace(format(format, args));
		}
	}

	@Override
	public void debug(String format, Object... args) {
		if (logger.isDebugEnabled()) {
			insertFeed();
			logger.debug(format(format, args));
		}
	}

	@Override
	public void info(String format, Object... args) {
		if (logger.isInfoEnabled()) {
			insertFeed();
			logger.info(format(format, args));
		}
	}

	@Override
	public void warn(String format, Object... args) {
		if (logger.isWarnEnabled()) {
			insertFeed();
			logger.warn(format(format, args));
		}
	}

	@Override
	public void error(String format, Object... args) {
		if (logger.isErrorEnabled()) {
			insertFeed();
			logger.error(format(format, args));
		}
	}

	@Override
	public void error(Throwable err) {
		if (logger.isErrorEnabled()) {
			insertFeed();
			logger.error(err.getMessage(), err);
		}
	}

	@Override
	public void error(Throwable err, String format, Object... args) {
		if (logger.isErrorEnabled()) {
			insertFeed();
			logger.error(format(format, args), err);
		}
	}

	private String format(String format, Object... args) {
		if (args == null || args.length == 0) {
			return format;
		}
		return String.format(format, args);
	}

	public void feed() {
		feedNext = true;
	}

	private void insertFeed() {
		if (feedNext) {
			logger.info(FEED_STRING);
			feedNext = false;
		}
	}

}
