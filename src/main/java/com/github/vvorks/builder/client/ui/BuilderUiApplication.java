/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.ui;

import java.util.Map;

import com.github.vvorks.builder.client.common.ui.DomDocument;
import com.github.vvorks.builder.client.common.ui.UiApplication;
import com.github.vvorks.builder.client.constants.ConstantsBundle;

public class BuilderUiApplication extends UiApplication {

	@SuppressWarnings("unchecked")
	public BuilderUiApplication(DomDocument doc) {
		super(doc);
		final UiApplication app = this;
		addPage("", args -> new BuilderPage("ShowPage", app, (Map<String, String>)args[0]));
		addPage("show", args -> new BuilderPage("ShowPage", app, (Map<String, String>)args[0]));
		addPage("showProject", args -> new BuilderPage("ShowProjectPage", app, (Map<String, String>)args[0]));
		addPage("showProjectI18n", args -> new BuilderPage("ShowProjectI18nPage", app, (Map<String, String>)args[0]));
		addPage("showClass", args -> new BuilderPage("ShowClassPage", app, (Map<String, String>)args[0]));
		addPage("showClassI18n", args -> new BuilderPage("ShowClassI18nPage", app, (Map<String, String>)args[0]));
		addPage("showField", args -> new BuilderPage("ShowFieldPage", app, (Map<String, String>)args[0]));
		addPage("showFieldI18n", args -> new BuilderPage("ShowFieldI18nPage", app, (Map<String, String>)args[0]));
		addPage("showQuery", args -> new BuilderPage("ShowQueryPage", app, (Map<String, String>)args[0]));
		addPage("showEnum", args -> new BuilderPage("ShowEnumPage", app, (Map<String, String>)args[0]));
		addPage("showEnumI18n", args -> new BuilderPage("ShowEnumI18nPage", app, (Map<String, String>)args[0]));
		addPage("showEnumValue", args -> new BuilderPage("ShowEnumValuePage", app, (Map<String, String>)args[0]));
		addPage("showEnumValueI18n", args -> new BuilderPage("ShowEnumValueI18nPage", app, (Map<String, String>)args[0]));
		addPage("showMessage", args -> new BuilderPage("ShowMessagePage", app, (Map<String, String>)args[0]));
		addPage("showMessageI18n", args -> new BuilderPage("ShowMessageI18nPage", app, (Map<String, String>)args[0]));
		addPage("showStyle", args -> new BuilderPage("ShowStylePage", app, (Map<String, String>)args[0]));
		addPage("showPageSet", args -> new BuilderPage("ShowPageSetPage", app, (Map<String, String>)args[0]));
		addPage("showPage", args -> new BuilderPage("ShowPagePage", app, (Map<String, String>)args[0]));
		addPage("showLayout", args -> new BuilderPage("ShowLayoutPage", app, (Map<String, String>)args[0]));
		addPage("showLocale", args -> new BuilderPage("ShowLocalePage", app, (Map<String, String>)args[0]));
		registerStyle(BuilderStyles.FIELD);
		registerStyle(BuilderStyles.FIELD_CLICKING);
		registerStyle(BuilderStyles.FIELD_DISABLE);
		registerStyle(BuilderStyles.FIELD_FOCUS);
		registerStyle(BuilderStyles.GROUP);
		registerStyle(BuilderStyles.GROUP_SPLITTER);
		registerStyle(BuilderStyles.SB);
		registerStyle(BuilderStyles.SB_INNER);
		registerStyle(BuilderStyles.SB_FOCUS);
		registerStyle(BuilderStyles.TRANSPARENT);
		setResourceBundle(ConstantsBundle.get());
	}

}
