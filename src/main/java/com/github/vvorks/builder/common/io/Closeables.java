package com.github.vvorks.builder.common.io;

import java.io.Closeable;
import java.io.IOException;

import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.logging.Logger;

public class Closeables {

	private static final Class<?> THIS = Closeables.class;
	private static final Logger LOGGER = Factory.newInstance(Logger.class, THIS);

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
