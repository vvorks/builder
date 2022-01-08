package com.github.vvorks.builder.client.ui;

import java.util.Map;

import com.github.vvorks.builder.client.agent.ClassAgent;
import com.github.vvorks.builder.client.agent.DataTypeAgent;
import com.github.vvorks.builder.client.common.net.JsonRpcClient;
import com.github.vvorks.builder.client.common.ui.UiApplication;
import com.github.vvorks.builder.client.common.ui.UiEditField;
import com.github.vvorks.builder.client.common.ui.UiHorizontalScrollBar;
import com.github.vvorks.builder.client.common.ui.UiNodeBuilder;
import com.github.vvorks.builder.client.common.ui.UiPage;
import com.github.vvorks.builder.client.common.ui.UiPickerField;
import com.github.vvorks.builder.client.common.ui.UiVerticalList;
import com.github.vvorks.builder.client.common.ui.UiVerticalScrollBar;

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
		final double NA = UiNodeBuilder.NA;
		UiNodeBuilder b = new UiNodeBuilder(this, "em");
		UiVerticalList list;
		//リスト
		b.enter(list = new UiVerticalList("list"));
			b.style(BuilderUiApplication.ENABLE);
			b.source(new BuilderRpcDataSource(rpc, "listField", 20, 40));
			b.locate(1.0, 1.0, 2.0, 2.0, NA, NA);
			b.loop(true);
			b.flushSoon(false);
			//
			b.enter(new UiEditField(UiVerticalList.ROWID_COLUMN));
				b.style(BuilderUiApplication.BASIC);
				b.locate( 0.0, 0.0, NA, NA,  5.0, 2.0);
			b.leave();
			b.enter(new UiEditField("fieldId"));
				b.style(BuilderUiApplication.BASIC);
				b.locate( 5.0, 0.0, NA, NA,  5.0, 2.0);
			b.leave();
			b.enter(new UiPickerField("owner", ClassAgent.get()));
				b.style(BuilderUiApplication.BASIC);
				b.source(new BuilderRpcDataSource(rpc, "listFieldOwnerCandidate", 20, 40));
				b.locate( 10.0, 0.0, NA, NA, 10.0, 2.0);
			b.leave();
			b.enter(new UiEditField("fieldName"));
				b.style(BuilderUiApplication.BASIC);
				b.locate( 20.0, 0.0, NA, NA, 10.0, 2.0);
			b.leave();
			b.enter(new UiPickerField("type", DataTypeAgent.get()));
				b.style(BuilderUiApplication.BASIC);
				b.source(new BuilderRpcDataSource(rpc, "listFieldTypeCandidate", 20, 40));
				b.locate( 30.0, 0.0, NA, NA, 10.0, 2.0);
			b.leave();
			b.enter(new UiEditField("width"));
				b.style(BuilderUiApplication.BASIC);
				b.locate( 40.0, 0.0, NA, NA,  5.0, 2.0);
			b.leave();
			b.enter(new UiEditField("scale"));
				b.style(BuilderUiApplication.BASIC);
				b.locate( 45.0, 0.0, NA, NA,  5.0, 2.0);
			b.leave();
			b.enter(new UiPickerField("cref", ClassAgent.get()));
				b.style(BuilderUiApplication.BASIC);
				b.source(new BuilderRpcDataSource(rpc, "listFieldCrefCandidate", 20, 40));
				b.locate( 50.0, 0.0, NA, NA, 10.0, 2.0);
			b.leave();
			b.enter(new UiPickerField("eref", ClassAgent.get()));
				b.style(BuilderUiApplication.BASIC);
				b.source(new BuilderRpcDataSource(rpc, "listFieldErefCandidate", 20, 40));
				b.locate( 60.0, 0.0, NA, NA, 10.0, 2.0);
			b.leave();
			b.enter(new UiPickerField("fref", ClassAgent.get()));
				b.style(BuilderUiApplication.BASIC);
				b.source(new BuilderRpcDataSource(rpc, "listFieldFrefCandidate", 20, 40));
				b.locate( 70.0, 0.0, NA, NA, 10.0, 2.0);
			b.leave();
			b.enter(new UiEditField("pk"));
				b.style(BuilderUiApplication.BASIC);
				b.locate( 80.0, 0.0, NA, NA, 5.0, 2.0);
			b.leave();
			b.enter(new UiEditField("nullable"));
				b.style(BuilderUiApplication.BASIC);
				b.locate( 85.0, 0.0, NA, NA, 5.0, 2.0);
			b.leave();
			b.enter(new UiEditField("needsSum"));
				b.style(BuilderUiApplication.BASIC);
				b.locate( 90.0, 0.0, NA, NA, 5.0, 2.0);
			b.leave();
			b.enter(new UiEditField("needsAvg"));
				b.style(BuilderUiApplication.BASIC);
				b.locate( 95.0, 0.0, NA, NA, 5.0, 2.0);
			b.leave();
			b.enter(new UiEditField("needsMax"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(100.0, 0.0, NA, NA, 5.0, 2.0);
			b.leave();
			b.enter(new UiEditField("needsMin"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(105.0, 0.0, NA, NA, 5.0, 2.0);
			b.leave();
			b.enter(new UiEditField("title"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(110.0, 0.0, NA, NA, 20.0, 2.0);
			b.leave();
			b.enter(new UiEditField("description"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(130.0, 0.0, NA, NA, 20.0, 2.0);
			b.leave();
			b.enter(new UiEditField("note"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(150.0, 0.0, NA, NA, 30.0, 2.0);
			b.leave();
		b.leave();
		b.enter(new UiVerticalScrollBar("vsb", list));
			b.style(BuilderUiApplication.SB);
			b.focusable(false);
			b.locate(NA, 1.0, 1.0, 2.0, 1.0, NA);
		b.leave();
		b.enter(new UiHorizontalScrollBar("hsb", list));
			b.style(BuilderUiApplication.SB);
			b.focusable(false);
			b.locate(1.0, NA, 2.0, 1.0, NA, 1.0);
		b.leave();
	}

}
