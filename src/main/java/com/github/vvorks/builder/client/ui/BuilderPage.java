package com.github.vvorks.builder.client.ui;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import com.github.vvorks.builder.client.agent.ClassAgent;
import com.github.vvorks.builder.client.common.ui.LayoutParam;
import com.github.vvorks.builder.client.common.ui.ListDataSource;
import com.github.vvorks.builder.client.common.ui.UiApplication;
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
import com.github.vvorks.builder.client.constants.ConstantsBundle;
import com.github.vvorks.builder.shared.common.json.Json;

public class BuilderPage extends UiPage {

	public BuilderPage(String name, UiApplication app, Map<String, String> param) {
		super(name, app);
	}

	public BuilderPage(BuilderPage src) {
		super(src);
	}

	@Override
	public BuilderPage copy() {
		return new BuilderPage(this);
	}

	private static final Map<String, Function<Json, UiNode>> NODE_CREATORS = new LinkedHashMap<>();
	static {
		NODE_CREATORS.put("SIMPLE_PANE", json -> newSimplePaneNode(json));
		NODE_CREATORS.put("PARTED_PANE", json -> newPartedPaneNode(json));
		NODE_CREATORS.put("TABBED_PANE", json -> newTabbedPaneNode(json));
		NODE_CREATORS.put("TAB", json -> newTabNode(json));
		NODE_CREATORS.put("V_LIST", json -> newVListNode(json));
		NODE_CREATORS.put("H_LIST", json -> newHListNode(json));
		NODE_CREATORS.put("V_SCROLLBAR", json -> newVScrollbarNode(json));
		NODE_CREATORS.put("H_SCROLLBAR", json -> newHScrollbarNode(json));
		NODE_CREATORS.put("LABEL", json -> newLabelNode(json));
		NODE_CREATORS.put("FIELD", json -> newFieldNode(json));
		NODE_CREATORS.put("INPUT", json -> newInputNode(json));
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

	@Override
	protected void initialize() {
		Json json = ConstantsBundle.get().getResource("ShowClassPage");
		Map<String, Relation> rels = new LinkedHashMap<>();
		//UiNode化
		UiNode root = createNode(json, rels, "/");
		//関連付け
		for (Relation r : rels.values()) {
			r.relate(rels);
		}
		this.appendChild(root);
	}

	private UiNode createNode(Json json, Map<String, Relation> rels, String path) {
		String fullName = path + json.getString("name");
		String type = json.getString("type");
		UiNode node = NODE_CREATORS.get(type).apply(json);
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
				String lp = childJson.getString("layoutparam", null);
				LayoutParam param = node.parseLayoutParam(lp);
				node.appendChild(childNode, param);
			}
		}
		rels.put(fullName, new Relation(node, json.getString("related", null)));
		return node;
	}

	private static UiNode newSimplePaneNode(Json json) {
		String name = json.getString("name");
		UiNode node = new UiGroup(name);
		node.setStyle(BuilderStyles.GROUP);  //TODO 仮
		return node;
	}

	private static UiNode newPartedPaneNode(Json json) {
		String name = json.getString("name");
		UiSplitGroup node = new UiSplitGroup(name);
		node.setStyle(BuilderStyles.GROUP_SPLITTER);  //TODO 仮
		node.setSpacing("8px");
		return node;
	}

	private static UiNode newTabbedPaneNode(Json json) {
		String name = json.getString("name");
		UiNode node = new UiDeckGroup(name);
		node.setStyle(BuilderStyles.GROUP);  //TODO 仮
		return node;
	}

	private static UiNode newTabNode(Json json) {
		String name = json.getString("name");
		UiNode node = new UiTab(name);
		node.setStyle(BuilderStyles.FIELD);  //TODO 仮
		return node;
	}

	private static UiNode newVListNode(Json json) {
		String name = json.getString("name");
		UiNode node = new UiVerticalList(name);
		node.setStyle(BuilderStyles.GROUP);  //TODO 仮
		node.setDataSource(new ListDataSource(Collections.emptyList())); //TODO 仮
		return node;
	}

	private static UiNode newHListNode(Json json) {
		//TODO 要実装
		throw new UnsupportedOperationException();
	}

	private static UiNode newVScrollbarNode(Json json) {
		String name = json.getString("name");
		UiNode node = new UiVerticalScrollBar(name);
		node.setStyle(BuilderStyles.SB);  //TODO 仮
		return node;
	}

	private static UiNode newHScrollbarNode(Json json) {
		String name = json.getString("name");
		UiNode node = new UiHorizontalScrollBar(name);
		node.setStyle(BuilderStyles.SB);  //TODO 仮
		return node;
	}

	private static UiNode newLabelNode(Json json) {
		String name = json.getString("name");
		UiNode node = new UiText(name);
		node.setStyle(BuilderStyles.FIELD);  //TODO 仮
		return node;
	}

	private static UiNode newFieldNode(Json json) {
		String name = json.getString("name");
		String dataType = json.getString("datatype");
		UiNode node;
		if (dataType.equals("REF")) {
			node = new UiPickerField(name, ClassAgent.get());
		} else {
			node = new UiEditField(name);
		}
		node.setStyle(BuilderStyles.FIELD); //TODO 仮
		return node;
	}

	private static UiNode newInputNode(Json json) {
		return newFieldNode(json); //TODO 仮
	}

}
