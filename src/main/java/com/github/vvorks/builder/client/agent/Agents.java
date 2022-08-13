/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;
import java.util.Map;
import java.util.HashMap;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;

public class Agents {

	private static final Map<String, DataRecordAgent> AGENTS = new HashMap<>();
	static {
		AGENTS.put("Project", ProjectAgent.get());
		AGENTS.put("ProjectI18n", ProjectI18nAgent.get());
		AGENTS.put("Class", ClassAgent.get());
		AGENTS.put("ClassI18n", ClassI18nAgent.get());
		AGENTS.put("Field", FieldAgent.get());
		AGENTS.put("FieldI18n", FieldI18nAgent.get());
		AGENTS.put("Query", QueryAgent.get());
		AGENTS.put("Enum", EnumAgent.get());
		AGENTS.put("EnumI18n", EnumI18nAgent.get());
		AGENTS.put("EnumValue", EnumValueAgent.get());
		AGENTS.put("EnumValueI18n", EnumValueI18nAgent.get());
		AGENTS.put("Message", MessageAgent.get());
		AGENTS.put("MessageI18n", MessageI18nAgent.get());
		AGENTS.put("Style", StyleAgent.get());
		AGENTS.put("PageSet", PageSetAgent.get());
		AGENTS.put("Page", PageAgent.get());
		AGENTS.put("Layout", LayoutAgent.get());
		AGENTS.put("Locale", LocaleAgent.get());
	}

	public static DataRecordAgent get(String name) {
		return AGENTS.get(name);
	}

}
