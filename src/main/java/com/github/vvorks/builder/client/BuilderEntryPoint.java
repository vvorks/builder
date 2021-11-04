package com.github.vvorks.builder.client;

import com.github.vvorks.builder.client.app.RpcTest;
import com.github.vvorks.builder.client.common.json.GwtJson;
import com.github.vvorks.builder.client.common.logging.AltConsoleHandler;
import com.github.vvorks.builder.client.common.net.GwtWebSocket;
import com.github.vvorks.builder.client.common.net.WebSocket;
import com.github.vvorks.builder.client.common.util.GwtDelayedExecuter;
import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.lang.Main;
import com.github.vvorks.builder.common.logging.JavaLogger;
import com.github.vvorks.builder.common.logging.Logger;
import com.github.vvorks.builder.common.util.DelayedExecuter;
import com.google.gwt.core.client.EntryPoint;

public class BuilderEntryPoint implements EntryPoint {

	@SuppressWarnings("unused")
	private Main main;

	public void onModuleLoad() {
		AltConsoleHandler.setup();
		// configure factory
		Factory.configure()
				//.bindTo(Main.class, args -> new GwtMain((String[])args))
				.bindTo(Main.class, args -> new RpcTest((String[])args))
				.bindTo(Logger.class, args -> new JavaLogger((Class<?>) args[0]))
				.bindTo(Json.class, args -> new GwtJson(args[0]))
				.bindTo(WebSocket.class, args -> new GwtWebSocket((String) args[0]))
				.bindIn(DelayedExecuter.class, args -> new GwtDelayedExecuter())
				;
		//launch main
		String[] args = new String[0];
		main = Factory.newInstance(Main.class, (Object[]) args);
	}

}
