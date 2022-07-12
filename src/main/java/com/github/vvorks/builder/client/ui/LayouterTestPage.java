package com.github.vvorks.builder.client.ui;

import java.util.Map;

import com.github.vvorks.builder.client.common.ui.UiApplication;
import com.github.vvorks.builder.client.common.ui.UiGroup;
import com.github.vvorks.builder.client.common.ui.UiLayoutEditor;
import com.github.vvorks.builder.client.common.ui.UiNodeBuilder;
import com.github.vvorks.builder.client.common.ui.UiPage;
import com.github.vvorks.builder.common.logging.Logger;

public class LayouterTestPage extends UiPage {

	private static final Logger LOGGER = Logger.createLogger(LayouterTestPage.class);

	public LayouterTestPage(String name, UiApplication app, Map<String, String> params) {
		super(name, app);
	}

	protected LayouterTestPage(LayouterTestPage source) {
		super(source);
	}

	@Override
	public LayouterTestPage copy() {
		return new LayouterTestPage(this);
	}

	@Override
	protected void initialize() {
		LOGGER.trace("%s.initialize()", getFullName());
		final double NA = UiNodeBuilder.NA;
		UiNodeBuilder b = new UiNodeBuilder(this, "em");
		//全体
		b.enter(new UiGroup("group"));
			b.style(BuilderStyles.GROUP);
			b.locate(1.0, 1.0, 1.0, 1.0, NA, NA);
			//レイアウタ
			b.enter(new UiLayoutEditor("layouter"));
				b.style(BuilderStyles.FIELD);
				b.locate(1.0, 1.0, 1.0, 1.0, NA, NA);
			b.leave();
		b.leave();
	}

}
