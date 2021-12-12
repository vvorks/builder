package com.github.vvorks.builder.client.app;

import java.util.Map;

import com.github.vvorks.builder.client.common.ui.UiApplication;
import com.github.vvorks.builder.client.common.ui.UiButton;
import com.github.vvorks.builder.client.common.ui.UiCanvasFrame;
import com.github.vvorks.builder.client.common.ui.UiGroup;
import com.github.vvorks.builder.client.common.ui.UiHorizontalGroup;
import com.github.vvorks.builder.client.common.ui.UiNode;
import com.github.vvorks.builder.client.common.ui.UiNodeBuilder;
import com.github.vvorks.builder.client.common.ui.UiPage;
import com.github.vvorks.builder.client.common.ui.UiVerticalGroup;
import com.github.vvorks.builder.client.common.ui.UiVerticalScrollBar;
import com.github.vvorks.builder.common.logging.Logger;

public class GroupTestPage extends UiPage {

	private static final Logger LOGGER = Logger.createLogger(GroupTestPage.class);

	public GroupTestPage(String name, UiApplication app, Map<String, String> params) {
		super(name, app);
	}

	protected GroupTestPage(GroupTestPage source) {
		super(source);
	}

	@Override
	public GroupTestPage copy() {
		return new GroupTestPage(this);
	}

	@Override
	protected void initialize() {
		LOGGER.trace("%s.initialize()", getFullName());
		final double NA = UiNodeBuilder.NA;
		UiNode g1;
		UiNodeBuilder b = new UiNodeBuilder(this, "em");
		int numChild = 100;
		//全体
		b.enter(new UiGroup("group"));
			b.style(BuilderUiApplication.NOBORDER);
			b.locate(1.0, 1.0, 1.0, 1.0, NA, NA);
			//DOMの縦グループ
			b.enter(g1 = new UiVerticalGroup("group1"));
				b.style(BuilderUiApplication.ENABLE);
				b.spacing(1.0);
				b.locate(1.0, 1.0, NA, 1.0, 10.0, NA);
				for (int i = 1; i <= numChild; i++) {
					b.enter(new UiButton("item" + i));
						b.text("項目" + i);
						b.style(BuilderUiApplication.BASIC);
						b.locate(NA, NA, NA, NA, NA, (i % 2) != 0 ? 2.0 : 3.0);
					b.leave();
				}
			b.leave();
			//スクロールバー
			b.enter(new UiVerticalScrollBar("sb1", g1));
				b.style(BuilderUiApplication.SB);
				b.focusable(true);
				b.locate(12.0, 1.0, NA, 1.0, 1.0, NA);
			b.leave();
			//CANVAS枠
			b.enter(new UiCanvasFrame("canvasFrame1"));
				b.style(BuilderUiApplication.NOBORDER);
				b.locate(14.0, 1.0, NA, 1.0, 10.0, NA);
				//CANVAS枠中の縦グループ
				b.enter(new UiVerticalGroup("group2"));
					b.style(BuilderUiApplication.ENABLE);
					b.spacing(1.0);
					b.locate(0.0, 0.0, 0.0, 0.0, NA, NA);
					for (int i = 1; i <= numChild; i++) {
						b.enter(new UiButton("item" + i));
							b.text("項目" + i);
							b.style(BuilderUiApplication.BASIC);
							b.locate(NA, NA, NA, NA, NA, (i % 2) != 0 ? 2.0 : 3.0);
						b.leave();
					}
				b.leave();
			b.leave();
			//DOMの横グループ
			b.enter(new UiHorizontalGroup("group3"));
				b.style(BuilderUiApplication.ENABLE);
				b.spacing(1.0);
				b.locate(25.0, 1.0, 1.0, NA, NA, 4.0);
				for (int i = 1; i <= numChild; i++) {
					b.enter(new UiButton("item" + i));
						b.text("項目" + i);
						b.style(BuilderUiApplication.BASIC);
						b.locate(NA, NA, NA, NA, (i % 2) != 0 ? 6.0 : 10.0, NA);
					b.leave();
				}
			b.leave();
			//CANVAS枠
			b.enter(new UiCanvasFrame("canvasFrame2"));
				b.style(BuilderUiApplication.NOBORDER);
				b.locate(25.0, 6.0, 1.0, NA, NA, 4.0);
				//CANVAS枠中の横グループ
				b.enter(new UiHorizontalGroup("group4"));
					b.style(BuilderUiApplication.ENABLE);
					b.spacing(1.0);
					b.locate(0.0, 0.0, 0.0, 0.0, NA, NA);
					for (int i = 1; i <= numChild; i++) {
						b.enter(new UiButton("item" + i));
							b.text("項目" + i);
							b.style(BuilderUiApplication.BASIC);
							b.locate(NA, NA, NA, NA, (i % 2) != 0 ? 6.0 : 10.0, NA);
						b.leave();
					}
				b.leave();
			b.leave();
		b.leave();
	}

}
