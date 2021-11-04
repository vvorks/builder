package com.github.vvorks.builder.common.logging;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.github.vvorks.builder.common.lang.Strings;

public class JavaLogger implements com.github.vvorks.builder.common.logging.Logger {

	/**
	 * 最小ログフォーマッタ
	 */
	public static class TinyFormatter extends Formatter {
		@Override
		public String format(LogRecord record) {
			return record.getMessage();
		}
	}

	private static final Map<Level, String> LEVEL_CHARS = new HashMap<>();
	static {
		LEVEL_CHARS.put(Level.SEVERE, "E");
		LEVEL_CHARS.put(Level.WARNING, "W");
		LEVEL_CHARS.put(Level.INFO, "I");
		LEVEL_CHARS.put(Level.FINE, "D");
		LEVEL_CHARS.put(Level.FINER, "V");
		LEVEL_CHARS.put(Level.FINEST, "T");
	}

	private final Logger logger;

	public JavaLogger(Class<?> cls) {
		logger = Logger.getLogger(cls.getName());
		Formatter formatter = new TinyFormatter();
		Handler[] handlers = logger.getHandlers();
		if (handlers == null || handlers.length == 0) {
			for (Handler handler : Logger.getGlobal().getHandlers()) {
				logger.addHandler(handler);
				if (handler.getFormatter() == null) {
					handler.setFormatter(formatter);
				}
			}
		} else {
			for (Handler handler : handlers) {
				if (handler.getFormatter() == null) {
					handler.setFormatter(formatter);
				}
			}
		}
	}

	@Override
	public void trace(String format, Object... args) {
		log(Level.FINEST, null, format, args);
	}

	@Override
	public void verbose(String format, Object... args) {
		log(Level.FINER, null, format, args);
	}

	@Override
	public void debug(String format, Object... args) {
		log(Level.FINE, null, format, args);
	}

	@Override
	public void info(String format, Object... args) {
		log(Level.INFO, null, format, args);
	}

	@Override
	public void warn(String format, Object... args) {
		log(Level.WARNING, null, format, args);
	}

	@Override
	public void error(String format, Object... args) {
		log(Level.SEVERE, null, format, args);
	}

	@Override
	public void error(Throwable err) {
		log(Level.SEVERE, err, err.getMessage());
	}

	@Override
	public void error(Throwable err, String format, Object... args) {
		log(Level.SEVERE, err, format, args);
	}

	@SuppressWarnings("deprecation")
	private void log(Level level, Throwable err, String format, Object... args) {
		if (!logger.isLoggable(level)) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		Date time = new Date();
		sb.append(Strings.sprintf(
				"%02d/%02d/%02d %02d:%02d:%02d.%03d %s ",
				(time.getYear() + 1900) - 2000,
				time.getMonth() + 1,
				time.getDate(),
				time.getHours(),
				time.getMinutes(),
				time.getSeconds(),
				time.getTime() % 1000,
				LEVEL_CHARS.get(level)));
		if (args == null || args.length == 0 || format.indexOf('%') == -1) {
			sb.append(format);
		} else {
			String str;
			try {
				str = Strings.sprintf(format, args);
			} catch(Exception e) {
				appendStackTrace(e, sb);
				str = "";
			}
			sb.append(str);
		}
		if (level.intValue() <= Level.FINE.intValue()) {
			StackTraceElement caller = getCaller();
			if (caller != null) {
				sb.append(" ");
				sb.append(caller.getFileName());
				sb.append(":");
				sb.append(caller.getLineNumber());
			}
		}
		if (err != null) {
			sb.append("\n");
			appendStackTrace(err, sb);
		}
		Level useLevel = level.intValue() < Level.INFO.intValue() ? Level.INFO : level;
		logger.log(useLevel, sb.toString(), err);
	}

	/**
	 * 呼び出し元スタックフレームを取得する
	 */
	private StackTraceElement getCaller() {
		Exception err = new Exception();
		StackTraceElement[] stack = err.getStackTrace();
		int i = 0;
		for (; i < stack.length; i++) {
			if ( stack[i].getFileName().endsWith("JavaLogger.java")) {
				break;
			}
		}
		for (; i < stack.length; i++) {
			if (!stack[i].getFileName().endsWith("JavaLogger.java")) {
				break;
			}
		}
		return i < stack.length ? stack[i] : null;
	}

	private void appendStackTrace(Throwable err, StringBuilder sb) {
		while (err != null) {
			sb.append(err.getClass().getName());
			sb.append("(").append(err.getMessage()).append(")");
			sb.append("\n");
			for (StackTraceElement e : err.getStackTrace()) {
				sb.append("at ");
				sb.append(e.getFileName());
				sb.append(":");
				sb.append(e.getLineNumber());
				sb.append("\n");
			}
			err = err.getCause();
			if (err != null) {
				sb.append("cause: ");
			}
		}
	}

}
