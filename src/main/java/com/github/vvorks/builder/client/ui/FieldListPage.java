package com.github.vvorks.builder.client.ui;

import java.util.Map;

import com.github.vvorks.builder.client.common.net.JsonRpcClient;
import com.github.vvorks.builder.client.common.ui.DataSource;
import com.github.vvorks.builder.client.common.ui.UiApplication;
import com.github.vvorks.builder.client.common.ui.UiDataField;
import com.github.vvorks.builder.client.common.ui.UiNodeBuilder;
import com.github.vvorks.builder.client.common.ui.UiPage;
import com.github.vvorks.builder.client.common.ui.UiVerticalList;

public class FieldListPage extends UiPage {

	public FieldListPage(String name, UiApplication app, Map<String, String> params) {
		super(name, app);
	}

	protected FieldListPage(FieldListPage source) {
		super(source);
	}

	@Override
	public FieldListPage copy() {
		return new FieldListPage(this);
	}

	@Override
	protected void initialize() {
		UiApplication app = getApplication();
		JsonRpcClient rpc = app.getRpcClient();
		DataSource ds = new BuilderRpcDataSource(rpc, "listField", 20, 100);
		final double NA = UiNodeBuilder.NA;
		UiNodeBuilder b = new UiNodeBuilder(this, "em");
		//リスト
		b.enter(new UiVerticalList("list"));
			b.style(BuilderUiApplication.ENABLE);
			b.source(ds);
			b.locate(1.0, 1.0, 1.0, 1.0, NA, NA);
			b.loop(false);
			b.flushSoon(false);
			//
			b.enter(new UiDataField("ownerClassId"));
				b.style(BuilderUiApplication.BASIC);
				b.locate( 0.0, 0.0, NA, NA, 10.0, 2.0);
			b.leave();
			b.enter(new UiDataField("fieldName"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(10.0, 0.0, NA, NA, 10.0, 2.0);
			b.leave();
			b.enter(new UiDataField("type"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(20.0, 0.0, NA, NA, 10.0, 2.0);
			b.leave();
			b.enter(new UiDataField("width"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(30.0, 0.0, NA, NA,  5.0, 2.0);
			b.leave();
			b.enter(new UiDataField("scale"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(35.0, 0.0, NA, NA,  5.0, 2.0);
			b.leave();
			b.enter(new UiDataField("crefClassId"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(40.0, 0.0, NA, NA, 10.0, 2.0);
			b.leave();
			b.enter(new UiDataField("erefEnumId"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(50.0, 0.0, NA, NA, 10.0, 2.0);
			b.leave();
			b.enter(new UiDataField("frefFieldId"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(60.0, 0.0, NA, NA, 10.0, 2.0);
			b.leave();
			b.enter(new UiDataField("pk"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(60.0, 0.0, NA, NA, 5.0, 2.0);
			b.leave();
			b.enter(new UiDataField("nullable"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(65.0, 0.0, NA, NA, 5.0, 2.0);
			b.leave();
			b.enter(new UiDataField("needsSum"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(70.0, 0.0, NA, NA, 5.0, 2.0);
			b.leave();
			b.enter(new UiDataField("needsAvg"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(75.0, 0.0, NA, NA, 5.0, 2.0);
			b.leave();
			b.enter(new UiDataField("needsMax"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(80.0, 0.0, NA, NA, 5.0, 2.0);
			b.leave();
			b.enter(new UiDataField("needsMin"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(85.0, 0.0, NA, NA, 5.0, 2.0);
			b.leave();
			b.enter(new UiDataField("title"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(90.0, 0.0, NA, NA, 20.0, 2.0);
			b.leave();
			b.enter(new UiDataField("description"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(110.0, 0.0, NA, NA, 20.0, 2.0);
			b.leave();
			b.enter(new UiDataField("note"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(130.0, 0.0, NA, NA, 30.0, 2.0);
			b.leave();
		b.leave();
	}

}
