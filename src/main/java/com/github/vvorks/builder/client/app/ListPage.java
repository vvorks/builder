package com.github.vvorks.builder.client.app;

import java.util.Map;

import com.github.vvorks.builder.client.common.ui.UiApplication;
import com.github.vvorks.builder.client.common.ui.UiButton;
import com.github.vvorks.builder.client.common.ui.UiGroup;
import com.github.vvorks.builder.client.common.ui.UiList;
import com.github.vvorks.builder.client.common.ui.UiNode;
import com.github.vvorks.builder.client.common.ui.UiPage;
import com.github.vvorks.builder.common.logging.Logger;

public class ListPage extends UiPage {

	public static final Class<?> THIS = ListPage.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	public ListPage(String name, UiApplication app, Map<String, String> params) {
		super(name, app);
	}

	protected ListPage(ListPage source) {
		super(source);
	}

	@Override
	public ListPage copy() {
		return new ListPage(this);
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
			b.enter(new UiList("list1"));
				b.style(BuilderUiApplication.ENABLE);
				b.locate(1.0, 1.0, 1.0, 1.0, NA, NA);
				b.enter(new UiButton("template"));
					b.text("template");
					b.style(BuilderUiApplication.BASIC);
					b.locate(0.0, 0.0, 0.0, NA, NA, 3.0);
				b.leave();
			b.leave();
		b.leave();
	}

}
