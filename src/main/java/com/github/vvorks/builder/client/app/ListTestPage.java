package com.github.vvorks.builder.client.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.vvorks.builder.client.ClientSettings;
import com.github.vvorks.builder.client.common.net.JsonRpcClient;
import com.github.vvorks.builder.client.common.ui.DataSource;
import com.github.vvorks.builder.client.common.ui.ListDataSource;
import com.github.vvorks.builder.client.common.ui.UiApplication;
import com.github.vvorks.builder.client.common.ui.UiButton;
import com.github.vvorks.builder.client.common.ui.UiDataField;
import com.github.vvorks.builder.client.common.ui.UiGroup;
import com.github.vvorks.builder.client.common.ui.UiNode;
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
		JsonRpcClient rpc = app.getRpcClient();
		DataSource ds = ClientSettings.DEBUG ? createDataSource(rpc) : createDataSource();
		final double NA = UiNodeBuilder.NA;
		UiNodeBuilder b = new UiNodeBuilder(this, "em");
		UiNode list;
		//全体
		b.enter(new UiGroup("group"));
			b.style(BuilderUiApplication.NOBORDER);
			b.locate(1.0, 1.0, 1.0, 1.0, NA, NA);
			//header
			b.enter(new UiButton("header"));
				b.text("header");
				b.style(BuilderUiApplication.BASIC);
				b.locate(1.0, 1.0, 1.0, NA, NA, 3.0);
			b.leave();
			//left side
			b.enter(new UiButton("leftSide"));
				b.text("leftSide");
				b.style(BuilderUiApplication.BASIC);
				b.locate(1.0, 5.0, NA, 5.0, 3.0, NA);
			b.leave();
			//list body
			b.enter(list = new UiVerticalList("list1"));
				b.style(BuilderUiApplication.ENABLE);
				b.source(ds);
				b.locate(5.0, 5.0, 5.0, 5.0, NA, NA);
				b.loop(true);
				b.flushSoon(false);
				b.enter(new UiDataField("_key"));
					b.style(BuilderUiApplication.BASIC);
					b.locate(0.0, 0.0, NA, NA, 5.0, 4.0);
				b.leave();
				b.enter(new UiDataField("title"));
					b.style(BuilderUiApplication.BASIC);
					b.locate(5.0, 0.0, 0.0, NA, NA, 2.0);
				b.leave();
				b.enter(new UiDataField("description"));
					b.style(BuilderUiApplication.BASIC);
					b.locate(5.0, 2.0, 0.0, NA, NA, 2.0);
				b.leave();
			b.leave();
			//right side
			b.enter(new UiVerticalScrollBar("rightSide", list));
				b.style(BuilderUiApplication.SB);
				b.focusable(true);
				b.locate(NA, 5.0, 1.0, 5.0, 3.0, NA);
			b.leave();
			//footer
			b.enter(new UiButton("footer"));
				b.text("footer");
				b.style(BuilderUiApplication.BASIC);
				b.locate(1.0, NA, 1.0, 1.0, NA, 3.0);
			b.leave();
		b.leave();
	}

	private DataSource createDataSource(JsonRpcClient rpc) {
		return new TestDataSource(rpc, "listClass", 20, 100);
	}

	private DataSource createDataSource() {
		List<Json> list = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			Json json = Json.createObject();
			json.setString("_key", i);
			json.setString("title", "title " + i);
			json.setString("description", "description " + i);
			list.add(json);
		}
		return new ListDataSource(list);
	}

}
