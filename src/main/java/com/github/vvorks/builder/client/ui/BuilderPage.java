package com.github.vvorks.builder.client.ui;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;

import com.github.vvorks.builder.client.agent.Agents;
import com.github.vvorks.builder.client.common.net.JsonRpcClient;
import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.client.common.ui.DataSource;
import com.github.vvorks.builder.client.common.ui.LayoutParam;
import com.github.vvorks.builder.client.common.ui.UiApplication;
import com.github.vvorks.builder.client.common.ui.UiButtonField;
import com.github.vvorks.builder.client.common.ui.UiDeckGroup;
import com.github.vvorks.builder.client.common.ui.UiEditField;
import com.github.vvorks.builder.client.common.ui.UiGroup;
import com.github.vvorks.builder.client.common.ui.UiHorizontalScrollBar;
import com.github.vvorks.builder.client.common.ui.UiNode;
import com.github.vvorks.builder.client.common.ui.UiPage;
import com.github.vvorks.builder.client.common.ui.UiPickerField;
import com.github.vvorks.builder.client.common.ui.UiSplitGroup;
import com.github.vvorks.builder.client.common.ui.UiTab;
import com.github.vvorks.builder.client.common.ui.UiText;
import com.github.vvorks.builder.client.common.ui.UiTextField;
import com.github.vvorks.builder.client.common.ui.UiVerticalList;
import com.github.vvorks.builder.client.common.ui.UiVerticalScrollBar;
import com.github.vvorks.builder.shared.common.json.Json;
import com.github.vvorks.builder.shared.common.lang.Strings;

public class BuilderPage extends UiPage {

	private static final String CREF_BUNDLE_PREFIX = "ClassConstants/";

	private static final String FREF_BUNDLE_PREFIX = "FieldConstants/";

	private static final String EREF_BUNDLE_PREFIX = "EnumConstants/";

	private static final String MREF_BUNDLE_PREFIX = "Message/";

	@FunctionalInterface
	interface NodeCreator {
		UiNode create(BuilderPage page, Json json, Deque<DataRecordAgent> stack);
	}

	private static final Map<String, NodeCreator>
		NODE_CREATORS = new LinkedHashMap<>();
	static {
		NODE_CREATORS.put("SIMPLE_PANE", (me, json, stack) -> me.newSimplePaneNode(json, stack));
		NODE_CREATORS.put("PARTED_PANE", (me, json, stack) -> me.newPartedPaneNode(json, stack));
		NODE_CREATORS.put("TABBED_PANE", (me, json, stack) -> me.newTabbedPaneNode(json, stack));
		NODE_CREATORS.put("TAB", (me, json, stack) -> me.newTabNode(json, stack));
		NODE_CREATORS.put("V_LIST", (me, json, stack) -> me.newVListNode(json, stack));
		NODE_CREATORS.put("H_LIST", (me, json, stack) -> me.newHListNode(json, stack));
		NODE_CREATORS.put("V_SCROLLBAR", (me, json, stack) -> me.newVScrollbarNode(json, stack));
		NODE_CREATORS.put("H_SCROLLBAR", (me, json, stack) -> me.newHScrollbarNode(json, stack));
		NODE_CREATORS.put("LABEL", (me, json, stack) -> me.newLabelNode(json, stack));
		NODE_CREATORS.put("FIELD", (me, json, stack) -> me.newFieldNode(json, stack));
		//NODE_CREATORS.put("FIELD", (me, json, stack) -> me.newInputNode(json, stack));
		NODE_CREATORS.put("INPUT", (me, json, stack) -> me.newInputNode(json, stack));
	}

	private static class Relation {
		private final UiNode node;
		private final String related;
		public Relation(UiNode node, String related) {
			this.node = node;
			this.related = related;
		}
		public void relate(Map<String, Relation> rels) {
			if (related != null) {
				Relation r = rels.get(related);
				if (r != null) {
					node.setRelated(r.node);
				}
			}
		}
	}

	private final Json pageInfo;

	private final String pageClassName;

	private final Json content;

	private final JsonRpcClient rpc;

	public BuilderPage(String name, UiApplication app, Map<String, String> param) {
		super(name, app);
		setResourcePath(name);
		rpc = app.getRpcClient();
		pageInfo = getResource();
		pageClassName = pageInfo.getString("cref", null);
		if (pageClassName == null) {
			content = null;
		} else {
			DataRecordAgent agent = Agents.get(pageClassName);
			content = agent.fromParam(param);
		}
	}

	public BuilderPage(BuilderPage src) {
		super(src);
		rpc = src.rpc;
		pageInfo = src.pageInfo;
		pageClassName = src.pageClassName;
		content = src.content;
	}

	@Override
	public BuilderPage copy() {
		return new BuilderPage(this);
	}

	@Override
	protected void initialize() {
		Map<String, Relation> rels = new LinkedHashMap<>();
		Deque<DataRecordAgent> stack = new ArrayDeque<>();
		//自身をマスターデータソース保持者として設定
		if (pageClassName != null) {
			DataSource ds = new BuilderRpcSingleDataSource(rpc, pageClassName, "get" + pageClassName);
			ds.setCriteria(content, null);
			DataRecordAgent agent = Agents.get(pageClassName);
			this.setDataSource(ds);
			stack.push(agent);
		}
		//UiNode化
		UiNode root = createNode(pageInfo, rels, "/", stack);
		//関連付け
		for (Relation r : rels.values()) {
			r.relate(rels);
		}
		this.appendChild(root);
	}

	private UiNode createNode(Json json, Map<String, Relation> rels, String path, Deque<DataRecordAgent> stack) {
		int depth = stack.size();
		String fullName = path + json.getString("name");
		String type = json.getString("type");
		UiNode node = NODE_CREATORS.get(type).create(this, json, stack);
		node.setLeft(json.getString("left", null));
		node.setTop(json.getString("top", null));
		node.setRight(json.getString("right", null));
		node.setBottom(json.getString("bottom", null));
		node.setWidth(json.getString("width", null));
		node.setHeight(json.getString("height", null));
		Json children = json.get("children");
		if (children != null) {
			String cpath = fullName + "/";
			for (int i = 0; i < children.size(); i++) {
				Json childJson = children.get(i);
				UiNode childNode = createNode(childJson, rels, cpath, stack);
				String lp = childJson.getString("layoutParam", null);
				LayoutParam param = node.parseLayoutParam(lp);
				node.appendChild(childNode, param);
			}
		}
		rels.put(fullName, new Relation(node, json.getString("related", null)));
		while (stack.size() > depth) {
			stack.pop();
		}
		return node;
	}

	protected UiNode newSimplePaneNode(Json json, Deque<DataRecordAgent> stack) {
		String name = json.getString("name");
		UiNode node = new UiGroup(name);
		node.setStyle(BuilderStyles.GROUP);  //TODO 仮
		return node;
	}

	protected UiNode newPartedPaneNode(Json json, Deque<DataRecordAgent> stack) {
		String name = json.getString("name");
		UiSplitGroup node = new UiSplitGroup(name);
		node.setStyle(BuilderStyles.GROUP_SPLITTER);  //TODO 仮
		node.setSpacing("8px");
		return node;
	}

	protected UiNode newTabbedPaneNode(Json json, Deque<DataRecordAgent> stack) {
		String name = json.getString("name");
		UiNode node = new UiDeckGroup(name);
		node.setStyle(BuilderStyles.GROUP);  //TODO 仮
		return node;
	}

	protected UiNode newTabNode(Json json, Deque<DataRecordAgent> stack) {
		String name = json.getString("name");
		UiNode node = new UiTab(name);
		node.setResourcePath(getResourcePathFrom(json));
		node.setStyle(BuilderStyles.FIELD);  //TODO 仮
		return node;
	}

	protected UiNode newVListNode(Json json, Deque<DataRecordAgent> stack) {
		String name = json.getString("name");
		UiNode node = new UiVerticalList(name);
		node.setStyle(BuilderStyles.GROUP);  //TODO 仮
		String dataName;
		String apiName;
		String cref = json.getString("cref", null);
		if (stack.isEmpty()) {
			dataName = cref;
			apiName = "list" + cref;
		} else {
			String typeName = stack.peek().getTypeName();
			String fref = json.getString("fref", null);
			String dataTypeParam = json.getString("dataTypeParam");
			int sep = fref.lastIndexOf('/');
			String fieldName = fref.substring(sep + 1);
			String upperName = Strings.toFirstUpper(fieldName);
			dataName = dataTypeParam;
			apiName = "list" + typeName + upperName;
		}
		DataSource ds = new BuilderRpcDataSource(rpc, dataName, apiName);
		DataRecordAgent agent = Agents.get(cref);
		if (stack.size() < 2) {
			ds.setCriteria(content, null);
		}
		node.setDataSource(ds);
		stack.push(agent);
		return node;
	}

	protected UiNode newHListNode(Json json, Deque<DataRecordAgent> stack) {
		//TODO 要実装
		throw new UnsupportedOperationException();
	}

	protected UiNode newVScrollbarNode(Json json, Deque<DataRecordAgent> stack) {
		String name = json.getString("name");
		UiNode node = new UiVerticalScrollBar(name);
		node.setStyle(BuilderStyles.SB);  //TODO 仮
		return node;
	}

	protected UiNode newHScrollbarNode(Json json, Deque<DataRecordAgent> stack) {
		String name = json.getString("name");
		UiNode node = new UiHorizontalScrollBar(name);
		node.setStyle(BuilderStyles.SB);  //TODO 仮
		return node;
	}

	protected UiNode newLabelNode(Json json, Deque<DataRecordAgent> stack) {
		String name = json.getString("name");
		UiNode node = new UiText(name);
		node.setResourcePath(getResourcePathFrom(json));
		node.setStyle(BuilderStyles.FIELD);  //TODO 仮
		return node;
	}

	protected UiNode newFieldNode(Json json, Deque<DataRecordAgent> stack) {
		String name = json.getString("name");
		String dataType = json.getString("dataType");
		UiNode node;
		if (dataType.equals("REF")) {
			String dataTypeParam = json.getString("dataTypeParam");
			DataRecordAgent agent = Agents.get(dataTypeParam);
			UiButtonField button = new UiButtonField(name, agent);
			button.setAction(n -> {
				DataRecord rec = n.getDataRecord();
				String dataName = n.getDataName();
				Json data = agent.getValue(rec, dataName);
				return transit(n, agent, data);
			});
			node = button;
		} else if (!stack.isEmpty()) {
			String typeName = stack.peek().getTypeName();
			DataRecordAgent agent = Agents.get(typeName);
			UiButtonField button = new UiButtonField(name);
			button.setAction(n -> {
				Json data = n.getDataRecord().getData();
				return transit(n, agent, data);
			});
			node = button;
		} else {
			node = new UiTextField(name);
		}
		node.setResourcePath(getResourcePathFrom(json));
		node.setStyle(BuilderStyles.FIELD); //TODO 仮
		return node;
	}

	private int transit(UiNode node, DataRecordAgent agent, Json data) {
		LOGGER.debug("transit %s %s", agent.getTypeName(), data);
		UiApplication app = getApplication();
		String tag = "show" + agent.getTypeName();
		Map<String, String> param = agent.toParam(data);
		app.transit(tag, param);
		return EVENT_EATEN;
	}

	protected UiNode newInputNode(Json json, Deque<DataRecordAgent> stack) {
		String name = json.getString("name");
		String dataType = json.getString("dataType");
		UiNode node;
		if (dataType.equals("REF") || dataType.equals("ENUM")) {
			String typeName = stack.peek().getTypeName();
			String dataTypeParam = json.getString("dataTypeParam");
			DataRecordAgent agent = Agents.get(dataTypeParam);
			node = new UiPickerField(name, agent);
			String fref = json.getString("fref", null);
			int sep = fref.lastIndexOf('/');
			String fieldName = fref.substring(sep + 1);
			String upperName = Strings.toFirstUpper(fieldName);
			String apiName = "list" + typeName + upperName + "Candidate";
			DataSource ds = new BuilderRpcDataSource(rpc, dataTypeParam, apiName);
			node.setDataSource(ds);
			stack.push(agent);
		} else {
			node = new UiEditField(name);
		}
		node.setResourcePath(getResourcePathFrom(json));
		node.setStyle(BuilderStyles.FIELD); //TODO 仮
		return node;
	}

	private String getResourcePathFrom(Json json) {
		String result;
		String fref = json.getString("fref", null);
		String cref = json.getString("cref", null);
		String eref = json.getString("eref", null);
		String mref = json.getString("mref", null);
		if (fref != null) {
			result = FREF_BUNDLE_PREFIX + fref;
		} else if (cref != null) {
			result = CREF_BUNDLE_PREFIX + cref;
		} else if (eref != null) {
			result = EREF_BUNDLE_PREFIX + eref;
		} else if (mref != null) {
			result = MREF_BUNDLE_PREFIX + mref;
		} else {
			result = "";
		}
		return result;
	}

}
