package com.github.vvorks.builder.client;

import com.github.vvorks.builder.client.app.BuilderUiApplication;
import com.github.vvorks.builder.client.common.net.WebSocket;
import com.github.vvorks.builder.client.common.ui.DeviceContext;
import com.github.vvorks.builder.client.common.ui.DomElement;
import com.github.vvorks.builder.client.common.ui.Metrics;
import com.github.vvorks.builder.client.common.ui.UiApplication;
import com.github.vvorks.builder.client.gwt.json.GwtJson;
import com.github.vvorks.builder.client.gwt.logging.AltConsoleHandler;
import com.github.vvorks.builder.client.gwt.net.GwtWebSocket;
import com.github.vvorks.builder.client.gwt.ui.CanvasPanel;
import com.github.vvorks.builder.client.gwt.ui.DomPanel;
import com.github.vvorks.builder.client.gwt.ui.GwtDeviceContext;
import com.github.vvorks.builder.client.gwt.ui.GwtPseudoElement;
import com.github.vvorks.builder.client.gwt.util.GwtDelayedExecuter;
import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.logging.JavaLogger;
import com.github.vvorks.builder.common.logging.Logger;
import com.github.vvorks.builder.common.util.DelayedExecuter;

public class ClientConfigure {

	public static final boolean DEBUG = true;

	/** このクラス */
	private static final Class<?> THIS = ClientConfigure.class;

	/** クラス名 */
	private static final String CLASS_NAME = THIS.getName();

	/** パッケージ名 */
	private static final String PACKAGE_NAME = CLASS_NAME.substring(0, CLASS_NAME.lastIndexOf('.'));

	/** モジュール名 */
	public static final String MODULE_NAME = PACKAGE_NAME.substring(0, PACKAGE_NAME.lastIndexOf('.'));

	/** サーバーURL */
	public static final String SERVER_URL = "ws://localhost:8080/builder";

	private ClientConfigure() {
	}

	public static void setup() {
		java.util.logging.Logger.getGlobal().addHandler(new AltConsoleHandler());
		// configure factory
		Factory.configure()
			//implementation settings
			.bindTo(UiApplication.class, args -> new BuilderUiApplication())
			.bindTo(Logger.class, args -> new JavaLogger((Class<?>) args[0]))
			.bindTo(Json.class, args -> new GwtJson(args[0]))
			.bindTo(WebSocket.class, args -> new GwtWebSocket())
		//	.bindTo(DomPanel.class, args -> new DivPanel())
		//	.bindTo(DomElement.class, args -> new GwtDomElement(args[0]))
			.bindTo(DomPanel.class, args -> new CanvasPanel())
			.bindTo(DomElement.class, args -> new GwtPseudoElement(args[0]))
			.bindTo(DeviceContext.class, args -> new GwtDeviceContext())
			//singleton settings
			.bindIn(DelayedExecuter.class, args -> new GwtDelayedExecuter())
			.bindIn(Metrics.class, args -> new Metrics())
			;

	}

}
