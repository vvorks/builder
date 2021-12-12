package com.github.vvorks.builder.client.app;

import java.util.Map;

import com.github.vvorks.builder.client.common.ui.Colors;
import com.github.vvorks.builder.client.common.ui.DomDocument;
import com.github.vvorks.builder.client.common.ui.UiApplication;
import com.github.vvorks.builder.client.common.ui.UiAtomicStyle;
import com.github.vvorks.builder.client.common.ui.UiBundleStyle;
import com.github.vvorks.builder.client.common.ui.UiStyle;

public class BuilderUiApplication extends UiApplication {

	public static final Class<?> THIS = BuilderUiApplication.class;

	public static final UiAtomicStyle ENABLE = UiStyle.newAtomicBuilder(THIS, "enable", null)
			.color(Colors.BLACK)
			.backgroundColor(Colors.TRANSPARENT)
			.font("12pt")
			.align(UiAtomicStyle.TEXT_ALIGN_LEFT, UiAtomicStyle.VERTICAL_ALIGN_MIDDLE)
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

	public static final UiAtomicStyle SB_OUTER = UiStyle.newAtomicBuilder(THIS, "sbouter", null)
			.backgroundColor(Colors.GRAY)
			.borderWidth("2px")
			.borderColor(Colors.PINK)
			.build();

	public static final UiAtomicStyle SB_NORMAL = UiStyle.newAtomicBuilder(THIS, "sbnormal", null)
			.backgroundColor(Colors.WHITE)
			.borderWidth("0px")
			.build();

	public static final UiAtomicStyle SB_FOCUS = UiStyle.newAtomicBuilder(THIS, "sbfocus", null)
			.backgroundColor(Colors.ORANGE)
			.borderWidth("0px")
			.build();

	public static final UiBundleStyle SB_INNER = UiStyle.newBundleBuilder(THIS, "sbinner", SB_NORMAL)
			.when(node -> node.getParent().isFocus(), SB_FOCUS)
			.build();

	public static final UiBundleStyle SB = UiStyle.newBundleBuilder(THIS, "sb", SB_OUTER)
			.named("thumb", SB_INNER)
			.build();

	@SuppressWarnings("unchecked")
	public BuilderUiApplication(DomDocument doc) {
		super(doc);
		final UiApplication app = this;
		addPage("", args -> new GroupTestPage("testPage", app, (Map<String, String>)args[0]));
		addPage("#list", args -> new ListTestPage("listPage", app, (Map<String, String>)args[0]));
		addPage("#layout", args -> new LayouterTestPage("layoutPage", app, (Map<String, String>)args[0]));
		registerStyle(BASIC);
		registerStyle(NOBORDER);
		registerStyle(SB);
	}

}
