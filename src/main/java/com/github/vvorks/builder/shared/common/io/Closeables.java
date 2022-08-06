package com.github.vvorks.builder.shared.common.io;

import java.io.Closeable;
import java.io.IOException;

import com.github.vvorks.builder.shared.common.logging.Logger;

public class Closeables {

	private static final Logger LOGGER = Logger.createLogger(Closeables.class);

	private Closeables() {
	}

	public static void close(Closeable c) {
		try {
			c.close();
		} catch (IOException err) {
			LOGGER.error("I/O ERROR OCCURS ON CLOSE");
		}
	}

}
