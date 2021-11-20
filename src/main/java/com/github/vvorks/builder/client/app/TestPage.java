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
		final double NA = UiNode.Builder.NA;
		UiNode.Builder b = new UiNode.Builder(this, "em");
		//全体
		b.enter(new UiGroup("group"));
			b.style(BuilderUiApplication.NOBORDER);
			b.locate(1.0, 1.0, 1.0, 1.0, NA, NA);
			//DOMの縦グループ
			b.enter(new UiVerticalGroup("group1"));
				b.style(BuilderUiApplication.ENABLE);
				b.spacing(1.0);
				b.locate(1.0, 1.0, NA, 1.0, 10.0, NA);
				for (int i = 1; i <= 10; i++) {
					b.enter(new UiButton("item" + i));
						b.text("項目" + i);
						b.style(BuilderUiApplication.BASIC);
						b.locate(NA, NA, NA, NA, NA, (i % 2) == 1 ? 2.0 : 3.0);
					b.leave();
				}
			b.leave();
			//CANVAS枠
			b.enter(new UiCanvasFrame("canvasFrame"));
				b.style(BuilderUiApplication.NOBORDER);
				b.locate(12.0, 1.0, NA, 1.0, 10.0, NA);
				//CANVAS枠中の縦グループ
				b.enter(new UiVerticalGroup("group2"));
					b.style(BuilderUiApplication.ENABLE);
					b.spacing(1.0);
					b.locate(0.0, 0.0, 0.0, 0.0, NA, NA);
					for (int i = 1; i <= 10; i++) {
						b.enter(new UiButton("item" + i));
							b.text("項目" + i);
							b.style(BuilderUiApplication.BASIC);
							b.locate(NA, NA, NA, NA, NA, (i % 2) == 1 ? 2.0 : 3.0);
						b.leave();
					}
				b.leave();
			b.leave();
			//レイアウタ
			b.enter(new UiLayoutEditor("layouter"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(23.0, 1.0, 1.0, 1.0, NA, NA);
			b.leave();
		b.leave();
	}

}
