package com.github.vvorks.builder.client.ui;

import java.util.Map;

import com.github.vvorks.builder.client.common.ui.UiApplication;
import com.github.vvorks.builder.client.common.ui.UiButton;
import com.github.vvorks.builder.client.common.ui.UiNodeBuilder;
import com.github.vvorks.builder.client.common.ui.UiPage;
import com.github.vvorks.builder.client.common.ui.UiSplitGroup;
import com.github.vvorks.builder.common.logging.Logger;

public class SplitTestPage extends UiPage {

	private static final Logger LOGGER = Logger.createLogger(SplitTestPage.class);

	public SplitTestPage(String name, UiApplication app, Map<String, String> params) {
		super(name, app);
	}

	protected SplitTestPage(SplitTestPage source) {
		super(source);
	}

	@Override
	public SplitTestPage copy() {
		return new SplitTestPage(this);
	}

	@Override
	protected void initialize() {
		LOGGER.trace("%s.initialize()", getFullName());
		final double NA = UiNodeBuilder.NA;
		UiNodeBuilder b = new UiNodeBuilder(this, "em");
		//全体
		b.enter(new UiSplitGroup("split"));
			b.style(BuilderStyles.GROUP_SPLITTER);
			b.spacing("8px");
			b.locate(1.0, 1.0, 1.0, 1.0, NA, NA);
			//左
			b.enter(new UiButton("left"), UiSplitGroup.Param.LEFT);
				b.style(BuilderStyles.FIELD);
				b.locate(NA, NA, NA, NA, 15.0, NA);
			b.leave();
			//右
			b.enter(new UiButton("right"), UiSplitGroup.Param.RIGHT);
				b.style(BuilderStyles.FIELD);
				b.locate(NA, NA, NA, NA, 10.0, NA);
			b.leave();
			//上1
			b.enter(new UiButton("top1"), UiSplitGroup.Param.TOP);
				b.style(BuilderStyles.FIELD);
				b.locate(NA, NA, NA, NA, NA, 4.0);
			b.leave();
			//上2
			b.enter(new UiButton("top2"), UiSplitGroup.Param.TOP);
				b.style(BuilderStyles.FIELD);
				b.locate(NA, NA, NA, NA, NA, 4.0);
			b.leave();
			//下1
			b.enter(new UiButton("bottom1"), UiSplitGroup.Param.BOTTOM);
				b.style(BuilderStyles.FIELD);
				b.locate(NA, NA, NA, NA, NA, 6.0);
			b.leave();
			//下2
			b.enter(new UiButton("bottom2"), UiSplitGroup.Param.BOTTOM);
				b.style(BuilderStyles.FIELD);
				b.locate(NA, NA, NA, NA, NA, 6.0);
			b.leave();
			//右２
			b.enter(new UiButton("right2"), UiSplitGroup.Param.RIGHT);
				b.style(BuilderStyles.FIELD);
				b.locate(NA, NA, NA, NA, 4.0, NA);
			b.leave();
			//中央
			b.enter(new UiButton("center"), UiSplitGroup.Param.CENTER);
				b.style(BuilderStyles.FIELD);
				b.locate(NA, NA, NA, NA, NA, NA);
			b.leave();
		b.leave();
	}

}
