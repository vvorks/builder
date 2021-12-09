package com.github.vvorks.builder.server;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.logging.Logger;
import com.github.vvorks.builder.server.common.json.JacksonJson;
import com.github.vvorks.builder.server.common.logging.Slf4jLogger;
import com.github.vvorks.builder.server.common.sql.SqlHelper;
import com.github.vvorks.builder.server.common.sql.SqliteHelper;

public class ServerSettings {

	/** デバッグフラグ */
	public static final boolean DEBUG = true;

	/** クラス名 */
	private static final String CLASS_NAME = ServerSettings.class.getName();

	/** パッケージ名 */
	private static final String PACKAGE_NAME = CLASS_NAME.substring(0, CLASS_NAME.lastIndexOf('.'));

	/** モジュール名 */
	public static final String MODULE_NAME = PACKAGE_NAME.substring(0, PACKAGE_NAME.lastIndexOf('.'));

	private ServerSettings() {
	}

	public static void setup() {
		Factory.configure()
			.bindTo(Logger.class,	a -> new Slf4jLogger((Class<?>) a[0]))
			.bindTo(Json.class,		a -> new JacksonJson(a[0]))
			.bindIn(SqlHelper.class,  a -> new SqliteHelper())
			;
	}

}
