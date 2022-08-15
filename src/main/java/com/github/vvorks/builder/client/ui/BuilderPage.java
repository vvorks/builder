package com.github.vvorks.builder.client.ui;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

import com.github.vvorks.builder.client.agent.Agents;
import com.github.vvorks.builder.client.common.net.JsonRpcClient;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.client.common.ui.DataSource;
import com.github.vvorks.builder.client.common.ui.LayoutParam;
import com.github.vvorks.builder.client.common.ui.UiApplication;
import com.github.vvorks.builder.client.common.ui.UiDataPanel;
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
import com.github.vvorks.builder.client.common.ui.UiVerticalList;
import com.github.vvorks.builder.client.common.ui.UiVerticalScrollBar;
import com.github.vvorks.builder.shared.common.json.Json;
import com.github.vvorks.builder.shared.common.lang.Strings;

public class BuilderPage extends UiPage {

	private static final String CREF_BUNDLE_PREFIX = "ClassConstants/";

	private static final String FREF_BUNDLE_PREFIX = "FieldConstants/";

	private static final String EREF_BUNDLE_PREFIX = "EnumConstants/";

	private static final String MREF_BUNDLE_PREFIX = "Message/";

	private static final Map<String, BiFunction<BuilderPage, Json, UiNode>>
		NODE_CREATORS = new LinkedHashMap<>();
	static {
		NODE_CREATORS.put("SIMPLE_PANE", (me, json) -> me.newSimplePaneNode(json));
		NODE_CREATORS.put("PARTED_PANE", (me, json) -> me.newPartedPaneNode(json));
		NODE_CREATORS.put("TABBED_PANE", (me, json) -> me.newTabbedPaneNode(json));
		NODE_CREATORS.put("TAB", (me, json) -> me.newTabNode(json));
		NODE_CREATORS.put("V_LIST", (me, json) -> me.newVListNode(json));
		NODE_CREATORS.put("H_LIST", (me, json) -> me.newHListNode(json));
		NODE_CREATORS.put("V_SCROLLBAR", (me, json) -> me.newVScrollbarNode(json));
		NODE_CREATORS.put("H_SCROLLBAR", (me, json) -> me.newHScrollbarNode(json));
		NODE_CREATORS.put("LABEL", (me, json) -> me.newLabelNode(json));
		NODE_CREATORS.put("FIELD", (me, json) -> me.newFieldNode(json));
		NODE_CREATORS.put("INPUT", (me, json) -> me.newInputNode(json));
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
			content = agent.getContentCriteria(param);
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
		//UiNode化
		UiNode root = createNode(pageInfo, rels, "/");
		//関連付け
		for (Relation r : rels.values()) {
			r.relate(rels);
		}
		this.appendChild(root);
	}

	private UiNode createNode(Json json, Map<String, Relation> rels, String path) {
		String fullName = path + json.getString("name");
		String type = json.getString("type");
		UiNode node = NODE_CREATORS.get(type).apply(this, json);
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
				UiNode childNode = createNode(childJson, rels, cpath);
				String lp = childJson.getString("layoutParam", null);
				LayoutParam param = node.parseLayoutParam(lp);
				node.appendChild(childNode, param);
			}
		}
		rels.put(fullName, new Relation(node, json.getString("related", null)));
		return node;
	}

	protected UiNode newDataPanelNode(Json json) {
		String name = json.getString("name");
		UiNode node = new UiDataPanel(name);
		if (pageClassName != null) {
			DataSource ds = new BuilderRpcSingleDataSource(rpc, "get" + pageClassName);
			ds.setCriteria(content, null);
			node.setDataSource(ds);
		}
		node.setStyle(BuilderStyles.GROUP);  //TODO 仮
		return node;
	}

	protected UiNode newSimplePaneNode(Json json) {
		String name = json.getString("name");
		if (name.equals("detail")) {
			return newDataPanelNode(json); //TODO 仮
		}
		UiNode node = new UiGroup(name);
		node.setStyle(BuilderStyles.GROUP);  //TODO 仮
		return node;
	}

	protected UiNode newPartedPaneNode(Json json) {
		String name = json.getString("name");
		UiSplitGroup node = new UiSplitGroup(name);
		node.setStyle(BuilderStyles.GROUP_SPLITTER);  //TODO 仮
		node.setSpacing("8px");
		return node;
	}

	protected UiNode newTabbedPaneNode(Json json) {
		String name = json.getString("name");
		UiNode node = new UiDeckGroup(name);
		node.setStyle(BuilderStyles.GROUP);  //TODO 仮
		return node;
	}

	protected UiNode newTabNode(Json json) {
		String name = json.getString("name");
		String fref = json.getString("fref", null);
		String cref = json.getString("cref", null);
		UiNode node = new UiTab(name);
		if (fref != null) {
			node.setResourcePath(FREF_BUNDLE_PREFIX + fref);
		} else {
			node.setResourcePath(CREF_BUNDLE_PREFIX + cref);
		}
		node.setStyle(BuilderStyles.FIELD);  //TODO 仮
		return node;
	}

	protected UiNode newVListNode(Json json) {
		String name = json.getString("name");
		UiNode node = new UiVerticalList(name);
		node.setStyle(BuilderStyles.GROUP);  //TODO 仮
		DataSource ds;
		String fref = json.getString("fref", null);
		if (fref == null) {
			String cref = json.getString("cref", null);
			String apiName = "list" + cref;
			ds = new BuilderRpcDataSource(rpc, apiName);
		} else {
			int sep = fref.lastIndexOf('/');
			String fieldName = fref.substring(sep + 1);
			String upperName = Strings.toFirstUpper(fieldName);
			String apiName = "list" + pageClassName + upperName;
			ds = new BuilderRpcDataSource(rpc, apiName);
		}
		ds.setCriteria(content, null);
		node.setDataSource(ds);
		return node;
	}

	protected UiNode newHListNode(Json json) {
		//TODO 要実装
		throw new UnsupportedOperationException();
	}

	protected UiNode newVScrollbarNode(Json json) {
		String name = json.getString("name");
		UiNode node = new UiVerticalScrollBar(name);
		node.setStyle(BuilderStyles.SB);  //TODO 仮
		return node;
	}

	protected UiNode newHScrollbarNode(Json json) {
		String name = json.getString("name");
		UiNode node = new UiHorizontalScrollBar(name);
		node.setStyle(BuilderStyles.SB);  //TODO 仮
		return node;
	}

	protected UiNode newLabelNode(Json json) {
		String name = json.getString("name");
		String fref = json.getString("fref", null);
		UiNode node = new UiText(name);
		node.setResourcePath(FREF_BUNDLE_PREFIX + fref);
		node.setStyle(BuilderStyles.FIELD);  //TODO 仮
		return node;
	}

	protected UiNode newFieldNode(Json json) {
		String name = json.getString("name");
		String dataType = json.getString("dataType");
		String fref = json.getString("fref", null);
		UiNode node;
		if (dataType.equals("REF")) {
			String dataTypeParam = json.getString("dataTypeParam");
			DataRecordAgent agent = Agents.get(dataTypeParam);
			node = new UiPickerField(name, agent);
		} else {
			node = new UiEditField(name);
		}
		node.setResourcePath(FREF_BUNDLE_PREFIX + fref);
		node.setStyle(BuilderStyles.FIELD); //TODO 仮
		return node;
	}

	protected UiNode newInputNode(Json json) {
		return newFieldNode(json); //TODO 仮
	}

}
