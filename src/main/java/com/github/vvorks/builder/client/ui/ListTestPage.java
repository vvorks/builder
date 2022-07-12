package com.github.vvorks.builder.client.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.vvorks.builder.client.common.net.JsonRpcClient;
import com.github.vvorks.builder.client.common.ui.DataSource;
import com.github.vvorks.builder.client.common.ui.ListDataSource;
import com.github.vvorks.builder.client.common.ui.UiApplication;
import com.github.vvorks.builder.client.common.ui.UiButton;
import com.github.vvorks.builder.client.common.ui.UiEditField;
import com.github.vvorks.builder.client.common.ui.UiGroup;
import com.github.vvorks.builder.client.common.ui.UiNodeBuilder;
import com.github.vvorks.builder.client.common.ui.UiPage;
import com.github.vvorks.builder.client.common.ui.UiVerticalList;
import com.github.vvorks.builder.client.common.ui.UiVerticalScrollBar;
import com.github.vvorks.builder.common.json.Json;

public class ListTestPage extends UiPage {

	public ListTestPage(String name, UiApplication app, Map<String, String> params) {
		super(name, app);
	}

	protected ListTestPage(ListTestPage source) {
		super(source);
	}

	@Override
	public ListTestPage copy() {
		return new ListTestPage(this);
	}

	@Override
	protected void initialize() {
		UiApplication app = getApplication();
		@SuppressWarnings("unused")
		JsonRpcClient rpc = app.getRpcClient();
		DataSource ds = createDataSource();
		final double NA = UiNodeBuilder.NA;
		UiNodeBuilder b = new UiNodeBuilder(this, "em");
		UiVerticalList list;
		//全体
		b.enter(new UiGroup("group"));
			b.style(BuilderStyles.GROUP);
			b.locate(1.0, 1.0, 1.0, 1.0, NA, NA);
			//header
			b.enter(new UiButton("header"));
				b.text("header");
				b.style(BuilderStyles.FIELD);
				b.locate(1.0, 1.0, 1.0, NA, NA, 3.0);
			b.leave();
			//left side
			b.enter(new UiButton("leftSide"));
				b.text("leftSide");
				b.style(BuilderStyles.FIELD);
				b.locate(1.0, 5.0, NA, 5.0, 3.0, NA);
			b.leave();
			//list body
			b.enter(list = new UiVerticalList("list1"));
				b.style(BuilderStyles.FIELD);
				b.source(ds);
				b.locate(5.0, 5.0, 5.0, 5.0, NA, NA);
				b.loop(false);
				b.flushSoon(false);
				b.enter(new UiEditField("_key"));
					b.style(BuilderStyles.FIELD);
					b.locate(0.0, 0.0, NA, NA, 5.0, 4.0);
				b.leave();
				b.enter(new UiEditField("title"));
					b.style(BuilderStyles.FIELD);
					b.locate(5.0, 0.0, 0.0, NA, NA, 2.0);
				b.leave();
				b.enter(new UiEditField("description"));
					b.style(BuilderStyles.FIELD);
					b.locate(5.0, 2.0, 0.0, NA, NA, 2.0);
				b.leave();
			b.leave();
			//right side
			b.enter(new UiVerticalScrollBar("rightSide", list));
				b.style(BuilderStyles.SB);
				b.focusable(true);
				b.locate(NA, 5.0, 1.0, 5.0, 3.0, NA);
			b.leave();
			//footer
			b.enter(new UiButton("footer"));
				b.text("footer");
				b.style(BuilderStyles.FIELD);
				b.locate(1.0, NA, 1.0, 1.0, NA, 3.0);
			b.leave();
		b.leave();
	}

	private DataSource createDataSource() {
		List<Json> list = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Json json = Json.createObject();
			json.setString("_key", i);
			json.setString("title", "title " + i);
			json.setString("description", "description " + i);
			list.add(json);
		}
		return new ListDataSource(list);
	}

}
