package com.github.vvorks.builder.client.ui;

import java.util.Map;

import com.github.vvorks.builder.client.common.ui.UiApplication;
import com.github.vvorks.builder.client.common.ui.UiButton;
import com.github.vvorks.builder.client.common.ui.UiCanvasFrame;
import com.github.vvorks.builder.client.common.ui.UiDeckGroup;
import com.github.vvorks.builder.client.common.ui.UiGroup;
import com.github.vvorks.builder.client.common.ui.UiHorizontalGroup;
import com.github.vvorks.builder.client.common.ui.UiHorizontalScrollBar;
import com.github.vvorks.builder.client.common.ui.UiNodeBuilder;
import com.github.vvorks.builder.client.common.ui.UiPage;
import com.github.vvorks.builder.client.common.ui.UiTab;
import com.github.vvorks.builder.client.common.ui.UiVerticalGroup;
import com.github.vvorks.builder.client.common.ui.UiVerticalScrollBar;
import com.github.vvorks.builder.shared.common.logging.Logger;

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
		UiNodeBuilder b = new UiNodeBuilder(this, "em");
		int numChild = 100;
		//被参照ノードの事前作成
		UiVerticalGroup g1 = new UiVerticalGroup("group1");
		UiHorizontalGroup g3 = new UiHorizontalGroup("group3");
		UiDeckGroup deck = new UiDeckGroup("deck");
		//全体
		b.enter(new UiGroup("group"));
			b.style(BuilderStyles.GROUP);
			b.locate(1.0, 1.0, 1.0, 1.0, NA, NA);
			//DOMの縦グループ
			b.enter(g1);
				b.style(BuilderStyles.FIELD);
				b.spacing(1.0);
				b.locate(1.0, 1.0, NA, 1.0, 10.0, NA);
				for (int i = 1; i <= numChild; i++) {
					b.enter(new UiButton("item" + i));
						b.text("項目" + i);
						b.style(BuilderStyles.FIELD);
						b.locate(NA, NA, NA, NA, NA, (i % 2) != 0 ? 2.0 : 3.0);
					b.leave();
				}
			b.leave();
			//スクロールバー
			b.enter(new UiVerticalScrollBar("sb1", g1));
				b.style(BuilderStyles.SB);
				b.focusable(true);
				b.locate(12.0, 1.0, NA, 1.0, 1.0, NA);
			b.leave();
			//CANVAS枠
			b.enter(new UiCanvasFrame("canvasFrame1"));
				b.style(BuilderStyles.GROUP);
				b.locate(14.0, 1.0, NA, 1.0, 10.0, NA);
				//CANVAS枠中の縦グループ
				b.enter(new UiVerticalGroup("group2", g1));
					b.style(BuilderStyles.FIELD);
					b.spacing(1.0);
					b.locate(0.0, 0.0, 0.0, 0.0, NA, NA);
					for (int i = 1; i <= numChild; i++) {
						b.enter(new UiButton("item" + i));
							b.text("項目" + i);
							b.style(BuilderStyles.FIELD);
							b.locate(NA, NA, NA, NA, NA, (i % 2) != 0 ? 2.0 : 3.0);
						b.leave();
					}
				b.leave();
			b.leave();
			//DOMの横グループ
			b.enter(g3);
				b.style(BuilderStyles.FIELD);
				b.spacing(1.0);
				b.locate(25.0, 1.0, 1.0, NA, NA, 4.0);
				for (int i = 1; i <= numChild; i++) {
					b.enter(new UiButton("item" + i));
						b.text("項目" + i);
						b.style(BuilderStyles.FIELD);
						b.locate(NA, NA, NA, NA, (i % 2) != 0 ? 6.0 : 10.0, NA);
					b.leave();
				}
			b.leave();
			//スクロールバー
			b.enter(new UiHorizontalScrollBar("sb1", g3));
				b.style(BuilderStyles.SB);
				b.focusable(true);
				b.locate(25.0, 6.0, 1.0, NA, NA, 1.0);
			b.leave();
			//CANVAS枠
			b.enter(new UiCanvasFrame("canvasFrame2"));
				b.style(BuilderStyles.GROUP);
				b.locate(25.0, 8.0, 1.0, NA, NA, 4.0);
				//CANVAS枠中の横グループ
				b.enter(new UiHorizontalGroup("group4", g3));
					b.style(BuilderStyles.FIELD);
					b.spacing(1.0);
					b.locate(0.0, 0.0, 0.0, 0.0, NA, NA);
					for (int i = 1; i <= numChild; i++) {
						b.enter(new UiButton("item" + i));
							b.text("項目" + i);
							b.style(BuilderStyles.FIELD);
							b.locate(NA, NA, NA, NA, (i % 2) != 0 ? 6.0 : 10.0, NA);
						b.leave();
					}
				b.leave();
			b.leave();
			//タブ用水平グループ
			b.enter(new UiHorizontalGroup("tab"));
				b.style(BuilderStyles.FIELD);
				b.spacing(0);
				b.locate(25.0, 14.0, 1.0, NA, NA, 2.0);
				for (int i = 0; i < 3; i++) {
					final int index = i;
					b.enter(new UiTab("tab" + i, deck, index));
						b.style(BuilderStyles.FIELD);
						b.locate(NA, NA, NA, NA, 8, NA);
					b.leave();
				}
			b.leave();
			//デッキ
			b.enter(deck);
				b.style(BuilderStyles.FIELD);
				b.spacing(0);
				b.locate(25.0, 16.0, 1.0, 1.0, NA, NA);
				for (int i = 0; i < 3; i++) {
					b.enter(new UiGroup("deck" + i));
						b.style(BuilderStyles.FIELD);
						b.locate(1.0, 1.0, 1.0, 1.0, NA, NA);
						b.enter(new UiButton("button"));
							b.style(BuilderStyles.FIELD);
							b.text("BUTTON " + i);
							switch (i) {
							case 0:
								b.locate(1.0, 1.0, NA, NA, 20.0, 4.0);
								break;
							case 1:
								b.locate(NA, NA, 1.0, 1.0, 20.0, 4.0);
								break;
							case 2:
								b.locate(NA, NA, NA, NA, 20.0, 4.0);
								break;
							default:
								break;
							}
						b.leave();
					b.leave();
				}
			b.leave();
		b.leave();
		deck.select(1);
	}

}
