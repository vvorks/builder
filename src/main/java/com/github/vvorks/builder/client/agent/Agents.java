/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;
import java.util.Map;
import java.util.HashMap;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;

public class Agents {

	private static void addAgent(DataRecordAgent agent) {
		AGENTS.put(agent.getTypeName(), agent);
	}

	private static final Map<String, DataRecordAgent> AGENTS = new HashMap<>();
	static {
		addAgent(ProjectAgent.get());
		addAgent(ProjectI18nAgent.get());
		addAgent(ClassAgent.get());
		addAgent(ClassI18nAgent.get());
		addAgent(FieldAgent.get());
		addAgent(FieldI18nAgent.get());
		addAgent(QueryAgent.get());
		addAgent(EnumAgent.get());
		addAgent(EnumI18nAgent.get());
		addAgent(EnumValueAgent.get());
		addAgent(EnumValueI18nAgent.get());
		addAgent(MessageAgent.get());
		addAgent(MessageI18nAgent.get());
		addAgent(StyleAgent.get());
		addAgent(PageSetAgent.get());
		addAgent(PageAgent.get());
		addAgent(LayoutAgent.get());
		addAgent(LocaleAgent.get());
		addAgent(DataTypeAgent.get());
		addAgent(StyleConditionAgent.get());
		addAgent(LayoutTypeAgent.get());
	}

	public static DataRecordAgent get(String name) {
		return AGENTS.get(name);
	}

}
