package com.github.vvorks.builder.server;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.logging.Logger;
import com.github.vvorks.builder.server.common.json.JacksonJson;
import com.github.vvorks.builder.server.common.logging.Slf4jLogger;

public class ServerSettings {

	public static final boolean DEBUG = true;

	private ServerSettings() {
	}

	public static void setup() {
		Factory.configure()
			.bindTo(Logger.class,	a -> new Slf4jLogger((Class<?>) a[0]))
			.bindTo(Json.class,		a -> new JacksonJson(a[0]))
			;
	}

}
