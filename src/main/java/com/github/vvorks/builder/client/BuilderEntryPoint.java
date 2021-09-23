package com.github.vvorks.builder.client;

import com.github.vvorks.builder.client.app.GwtMain;
import com.github.vvorks.builder.client.common.json.GwtJsonContext;
import com.github.vvorks.builder.client.common.net.GwtWebSocket;
import com.github.vvorks.builder.client.common.net.WebSocket;
import com.github.vvorks.builder.client.common.util.GwtDelayedExecuter;
import com.github.vvorks.builder.common.json.JsonContext;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.lang.Main;
import com.github.vvorks.builder.common.logging.JavaLogger;
import com.github.vvorks.builder.common.util.DelayedExecuter;
import com.github.vvorks.builder.common.util.Logger;
import com.google.gwt.core.client.EntryPoint;

public class BuilderEntryPoint implements EntryPoint {

	@SuppressWarnings("unused")
	private Main main;

	public void onModuleLoad() {
		// configure factory
		Factory.configure()
				.bindTo(Main.class, args -> new GwtMain((String[])args))
				.bindTo(Logger.class, args -> new JavaLogger((Class<?>) args[0]))
				.bindTo(JsonContext.class, args -> new GwtJsonContext(args[0]))
				.bindTo(WebSocket.class, args -> new GwtWebSocket((String) args[0]))
				.bindIn(DelayedExecuter.class, args -> new GwtDelayedExecuter())
				;
		//launch main
		String[] args = new String[0];
		main = Factory.newInstance(Main.class, (Object[]) args);
	}

}
