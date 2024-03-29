package com.github.vvorks.builder.client;

import java.util.List;

import com.github.vvorks.builder.client.common.net.WebSocket;
import com.github.vvorks.builder.client.common.ui.DomDocument;
import com.github.vvorks.builder.client.common.ui.Metrics;
import com.github.vvorks.builder.client.common.ui.UiApplication;
import com.github.vvorks.builder.client.constants.ConstantsBundle;
import com.github.vvorks.builder.client.gwt.json.GwtJson;
import com.github.vvorks.builder.client.gwt.logging.AltConsoleHandler;
import com.github.vvorks.builder.client.gwt.net.GwtWebSocket;
import com.github.vvorks.builder.client.gwt.text.GwtDateFormatter;
import com.github.vvorks.builder.client.gwt.text.GwtNumberFormatter;
import com.github.vvorks.builder.client.gwt.util.GwtDelayedExecuter;
import com.github.vvorks.builder.client.gwt.util.GwtTestRunner;
import com.github.vvorks.builder.client.ui.BuilderUiApplication;
import com.github.vvorks.builder.shared.common.json.Json;
import com.github.vvorks.builder.shared.common.lang.Factory;
import com.github.vvorks.builder.shared.common.logging.JavaLogger;
import com.github.vvorks.builder.shared.common.logging.Logger;
import com.github.vvorks.builder.shared.common.text.CalendarSelecter;
import com.github.vvorks.builder.shared.common.text.CalendarSelecterJa;
import com.github.vvorks.builder.shared.common.text.DateFormatter;
import com.github.vvorks.builder.shared.common.text.NumberFormatter;
import com.github.vvorks.builder.shared.common.text.Pattern;
import com.github.vvorks.builder.shared.common.util.DelayedExecuter;
import com.github.vvorks.builder.shared.common.util.TestRunner;
import com.google.gwt.core.client.GWT;

public class ClientSettings {

	public static final boolean DEBUG = true;

	/** クラス名 */
	private static final String CLASS_NAME = ClientSettings.class.getName();

	/** パッケージ名 */
	private static final String PACKAGE_NAME = CLASS_NAME.substring(0, CLASS_NAME.lastIndexOf('.'));

	/** モジュール名 */
	public static final String MODULE_NAME = PACKAGE_NAME.substring(0, PACKAGE_NAME.lastIndexOf('.'));

	/** サーバーURL */
	public static final String SERVER_URL = "ws://localhost:8080/builder";

	/** ホイール係数 */
	public static final int WHEEL_SCALE = 20;

	private ClientSettings() {
	}

	@SuppressWarnings("unchecked")
	public static void setup() {
		java.util.logging.Logger.getGlobal().addHandler(new AltConsoleHandler());
		// configure factory
		Factory.configure()
			//implementation settings
			.bindTo(UiApplication.class, args -> new BuilderUiApplication((DomDocument) args[0]))
			//.bindTo(UiApplication.class, args -> new BuilderUiTestApplication((DomDocument) args[0]))
			.bindTo(Logger.class, args -> new JavaLogger((Class<?>) args[0]))
			.bindTo(Json.class, args -> new GwtJson(args[0]))
			.bindTo(WebSocket.class, args -> new GwtWebSocket())
			.bindTo(NumberFormatter.class, args -> new GwtNumberFormatter((List<Pattern>) args[0]))
			.bindTo(DateFormatter.class, args -> new GwtDateFormatter((List<Pattern>) args[0]))
			//singleton settings
			.bindIn(DelayedExecuter.class, args -> new GwtDelayedExecuter())
			.bindIn(Metrics.class, args -> new Metrics())
			.bindIn(ConstantsBundle.class, args -> GWT.create(ConstantsBundle.class))
			.bindIn(TestRunner.class, args -> GWT.create(GwtTestRunner.class))
			.bindIn(CalendarSelecter.class, args -> new CalendarSelecterJa())
			;
	}

}
