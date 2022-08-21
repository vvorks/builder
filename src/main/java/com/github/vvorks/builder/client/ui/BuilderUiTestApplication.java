package com.github.vvorks.builder.client.ui;

import java.util.Map;

import com.github.vvorks.builder.client.common.ui.DomDocument;
import com.github.vvorks.builder.client.common.ui.UiApplication;

public class BuilderUiTestApplication extends UiApplication {

	public static final Class<?> THIS = BuilderUiTestApplication.class;

	@SuppressWarnings("unchecked")
	public BuilderUiTestApplication(DomDocument doc) {
		super(doc);
		final UiApplication app = this;
		addPage("test", args -> new GroupTestPage("testPage", app, (Map<String, String>)args[0]));
		addPage("list", args -> new ListTestPage("listPage", app, (Map<String, String>)args[0]));
		addPage("layout", args -> new LayouterTestPage("layoutPage", app, (Map<String, String>)args[0]));
		addPage("fieldList", args -> new FieldListPage("fieldListPage", app, (Map<String, String>)args[0]));
		addPage("split", args -> new SplitTestPage("SplitTestPage", app, (Map<String, String>)args[0]));
		registerStyle(BuilderStyles.FIELD);
		registerStyle(BuilderStyles.GROUP);
		registerStyle(BuilderStyles.SB);
		registerStyle(BuilderStyles.TRANSPARENT);
	}

}
