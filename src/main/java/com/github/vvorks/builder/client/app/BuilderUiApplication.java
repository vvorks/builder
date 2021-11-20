package com.github.vvorks.builder.client.app;

import java.util.Map;

import com.github.vvorks.builder.client.common.ui.Colors;
import com.github.vvorks.builder.client.common.ui.UiApplication;
import com.github.vvorks.builder.client.common.ui.UiAtomicStyle;
import com.github.vvorks.builder.client.common.ui.UiBundleStyle;
import com.github.vvorks.builder.client.common.ui.UiStyle;
import com.github.vvorks.builder.common.logging.Logger;

public class BuilderUiApplication extends UiApplication {

	public static final Class<?> THIS = BuilderUiApplication.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	public static final UiAtomicStyle ENABLE = UiStyle.newAtomicBuilder(THIS, "enable", null)
			.color(Colors.BLACK)
			.backgroundColor(Colors.TRANSPARENT)
			.font("12pt")
			.align(UiAtomicStyle.TEXT_ALIGN_CENTER, UiAtomicStyle.VERTICAL_ALIGN_MIDDLE)
			.borderWidth("4px")
			.borderColor(Colors.BLACK)
			.borderImage("image/rrect-bw.png")
			.build();

	public static final UiAtomicStyle FOCUS = UiStyle.newAtomicBuilder(THIS, "focus", ENABLE)
			.color(Colors.RED)
			.borderImage("image/rrect-yw.png")
			.build();

	public static final UiAtomicStyle CLICKING = UiStyle.newAtomicBuilder(THIS, "clicking", FOCUS)
			.borderImage("image/rrect-yp.png")
			.build();

	public static final UiBundleStyle BASIC = UiStyle.newBundleBuilder(THIS, "basic", ENABLE)
			.clicking(CLICKING)
			.focus(FOCUS)
			.build();

	public static final UiAtomicStyle NOBORDER = UiStyle.newAtomicBuilder(THIS, "noborder", null)
			.backgroundColor(0xCC808080)
			.borderWidth("0px")
			.build();

	@SuppressWarnings("unchecked")
	public BuilderUiApplication() {
		final UiApplication app = this;
		addPage("", args -> new TestPage("testpage", app, (Map<String, String>)args[0]));
		registerStyle(BASIC);
		registerStyle(NOBORDER);
	}

}
