package com.github.vvorks.builder.client.app;

import java.util.Map;

import com.github.vvorks.builder.client.common.ui.UiApplication;
import com.github.vvorks.builder.client.common.ui.UiButton;
import com.github.vvorks.builder.client.common.ui.UiCanvasFrame;
import com.github.vvorks.builder.client.common.ui.UiGroup;
import com.github.vvorks.builder.client.common.ui.UiLayoutEditor;
import com.github.vvorks.builder.client.common.ui.UiNode;
import com.github.vvorks.builder.client.common.ui.UiPage;
import com.github.vvorks.builder.client.common.ui.UiVerticalGroup;
import com.github.vvorks.builder.common.logging.Logger;

public class TestPage extends UiPage {

	public static final Class<?> THIS = TestPage.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	public TestPage(String name, UiApplication app, Map<String, String> params) {
		super(name, app);
	}

	protected TestPage(TestPage source) {
		super(source);
	}

	@Override
	public TestPage copy() {
		return new TestPage(this);
	}

	@Override
	protected void initialize() {
		LOGGER.trace("%s.initialize()", getFullName());
		String borderWidth = "2px";
		final double NA = UiNode.Builder.NA;
		UiNode.Builder b = new UiNode.Builder(this, "em");
		b.enter(new UiGroup("group"));
			b.style(BuilderUiApplication.BASIC);
			b.border(borderWidth);
			b.locate(1.0, 1.0, 1.0, 1.0, NA, NA);
			b.enter(new UiVerticalGroup("group1"));
				b.style(BuilderUiApplication.BASIC);
				b.border(borderWidth);
				b.spacing(1.0);
				b.locate(1.0, 1.0, NA, 1.0, 10.0, NA);
				for (int i = 1; i <= 10; i++) {
					b.enter(new UiButton("item" + i));
						b.text("項目" + i);
						b.style(BuilderUiApplication.BASIC);
						b.border(borderWidth);
						b.locate(NA, NA, NA, NA, NA, 2.0);
					b.leave();
				}
			b.leave();
			//b.enter(new UiList("list"));
			b.enter(new UiCanvasFrame("canvasFrame"));
				b.style(BuilderUiApplication.BASIC);
				b.border(borderWidth);
				b.locate(12.0, 1.0, 1.0, NA, NA, 8.0);
				b.enter(new UiButton("buttonInCanvas"));
					b.text("button");
					b.style(BuilderUiApplication.BASIC);
					b.border(borderWidth);
					b.locate(1.0, 1.0, 1.0, 1.0, NA, NA);
				b.leave();
			b.leave();
			b.enter(new UiLayoutEditor("layouter"));
				b.style(BuilderUiApplication.BASIC);
				b.border(borderWidth);
				b.locate(12.0, 10.0, 1.0, 1.0, NA, NA);
			b.leave();
		b.leave();
	}

}
