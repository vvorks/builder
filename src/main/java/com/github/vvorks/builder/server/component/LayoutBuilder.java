package com.github.vvorks.builder.server.component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;

import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.EnumContent;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.server.domain.LayoutContent;
import com.github.vvorks.builder.server.domain.LayoutType;
import com.github.vvorks.builder.server.domain.MessageContent;
import com.github.vvorks.builder.server.domain.PageContent;
import com.github.vvorks.builder.server.domain.StyleContent;
import com.github.vvorks.builder.server.mapper.LayoutMapper;
import com.github.vvorks.builder.server.mapper.Mappers;
import com.github.vvorks.builder.shared.common.logging.Logger;

public class LayoutBuilder {

	private static final Logger LOGGER = Logger.createLogger(LayoutBuilder.class);

	public static final double NA = Double.NaN;

	private static class Node {
		private final LayoutContent content;
		private final Map<String, Node> children;
		private Node parent;
		private String relatedPath;
		private Node(LayoutContent content) {
			this.content = content;
			this.children = new LinkedHashMap<>();
		}
		private void addChild(Node child) {
			Node p = child.parent;
			if (p != null) {
				p.children.remove(child.content.getLayoutName());
				child.setParent(null);
			}
			p = this;
			p.children.put(child.content.getLayoutName(), child);
			child.setParent(this);
		}
		private Node getParent() {
			return this.parent;
		}
		private void setParent(Node parent) {
			this.parent = parent;
			if (parent != null && parent.content != null) {
				content.setParentLayoutId(parent.content.getLayoutId());
			}
		}
	}

	private final PageContent page;

	private final String defaultUnit;

	private final Mappers mappers;

	private final Deque<Node> stack;

	public LayoutBuilder(PageContent page, String defaultUnit, Mappers mappers) {
		this.page = page;
		this.defaultUnit = defaultUnit;
		this.mappers = mappers;
		this.stack = new ArrayDeque<>();
		this.stack.push(new Node(null));
	}

	public LayoutBuilder enter(LayoutType type, String name) {
		return enter(type, name, "", "");
	}

	public LayoutBuilder enter(LayoutType type, String name, String param) {
		return enter(type, name, param, "");
	}

	public LayoutBuilder enter(LayoutType type, String name, String param, String layoutParam) {
		Node parentNode = stack.peek();
		LayoutContent content = new LayoutContent();
		content.setLayoutId(mappers.newLayoutId());
		content.setOwnerPageId(page.getPageId());
		content.setLayoutName(name);
		content.setLayoutType(type);
		content.setParam(param);
		content.setLayoutParam(layoutParam);
		String zero = encode(0);
		content.setLeft(zero);
		content.setTop(zero);
		content.setRight(zero);
		content.setBottom(zero);
		content.setWidth(null);
		content.setHeight(null);
		Node node = new Node(content);
		parentNode.addChild(node);
		stack.push(node);
		return this;
	}

	public LayoutBuilder leave() {
		stack.pop();
		return this;
	}

	public LayoutBuilder refClass(ClassContent cls) {
		if (cls != null) {
			LayoutContent content = stack.peek().content;
			content.setCrefClassId(cls.getClassId());
		}
		return this;
	}

	public LayoutBuilder refEnum(EnumContent enm) {
		if (enm != null) {
			LayoutContent content = stack.peek().content;
			content.setCrefClassId(enm.getEnumId());
		}
		return this;
	}

	public LayoutBuilder refField(FieldContent field) {
		if (field != null) {
			LayoutContent content = stack.peek().content;
			content.setFrefFieldId(field.getFieldId());
		}
		return this;
	}

	public LayoutBuilder refMessage(MessageContent message) {
		if (message != null) {
			LayoutContent content = stack.peek().content;
			content.setMrefMessageId(message.getMessageId());
		}
		return this;
	}

	public LayoutBuilder related(String relatedPath) {
		if (relatedPath != null) {
			Node node = stack.peek();
			node.relatedPath = relatedPath;
		}
		return this;
	}

	public LayoutBuilder style(StyleContent style) {
		if (style != null) {
			LayoutContent content = stack.peek().content;
			content.setStyleStyleId(style.getStyleId());
		}
		return this;
	}

	public LayoutBuilder locate(String left, String top, String right, String bottom) {
		LayoutContent content = stack.peek().content;
		content.setLeft(left);
		content.setTop(top);
		content.setRight(right);
		content.setBottom(bottom);
		content.setWidth(null);
		content.setHeight(null);
		return this;
	}

	public LayoutBuilder locate(double left, double top, double right, double bottom) {
		LayoutContent content = stack.peek().content;
		content.setLeft(encode(left));
		content.setTop(encode(top));
		content.setRight(encode(right));
		content.setBottom(encode(bottom));
		content.setWidth(null);
		content.setHeight(null);
		return this;
	}

	public LayoutBuilder locate(String width, String height) {
		LayoutContent content = stack.peek().content;
		content.setLeft(null);
		content.setTop(null);
		content.setRight(null);
		content.setBottom(null);
		content.setWidth(width);
		content.setHeight(height);
		return this;
	}

	public LayoutBuilder locate(double width, double height) {
		LayoutContent content = stack.peek().content;
		content.setLeft(null);
		content.setTop(null);
		content.setRight(null);
		content.setBottom(null);
		content.setWidth(encode(width));
		content.setHeight(encode(height));
		return this;
	}

	public LayoutBuilder locate(String left, String top, String right, String bottom, String width, String height) {
		LayoutContent content = stack.peek().content;
		content.setLeft(left);
		content.setTop(top);
		content.setRight(right);
		content.setBottom(bottom);
		content.setWidth(width);
		content.setHeight(height);
		return this;
	}

	public LayoutBuilder locate(double left, double top, double right, double bottom, double width, double height) {
		LayoutContent content = stack.peek().content;
		content.setLeft(encode(left));
		content.setTop(encode(top));
		content.setRight(encode(right));
		content.setBottom(encode(bottom));
		content.setWidth(encode(width));
		content.setHeight(encode(height));
		return this;
	}

	private String encode(double value) {
		if (Double.isNaN(value)) {
			return null;
		} else if (value == 0.0) {
			return "0";
		} else if (Math.floor(value) == value) {
			return String.valueOf((int)value) + defaultUnit;
		} else {
			return String.valueOf(value) + defaultUnit;
		}
	}

	public void finish() {
		Node root = getRoot();
		for (Node node : root.children.values()) {
			insertContent(node);
		}
		for (Node node : root.children.values()) {
			solveRelation(node);
		}
		this.stack.clear();
		this.stack.push(new Node(null));
	}

	private Node getRoot() {
		return this.stack.getLast();
	}

	private void solveRelation(Node node) {
		if (node.relatedPath != null) {
			Node related = findRelatedNode(node);
			if (related != null) {
				LayoutMapper m = mappers.getLayoutMapper();
				LayoutContent c = m.get(node.content.getLayoutId());
				c.setRelatedLayoutId(related.content.getLayoutId());
				m.update(c);
			}
		}
		for (Node child : node.children.values()) {
			solveRelation(child);
		}
	}

	private Node findRelatedNode(Node node) {
		Node root = getRoot();
		String[] names = node.relatedPath.split("/");
		Node curr = names[0].isEmpty() ? root : node.getParent();
		for (String name : names) {
			if (name.equals("..")) {
				curr = curr.getParent();
			} else if (!name.isEmpty() && !name.equals(".")) {
				curr = curr.children.get(name);
			}
			if (curr == null) {
				break;
			}
		}
		return curr;
	}

	private void insertContent(Node node) {
		LOGGER.info("content {%s}", node.content);
		mappers.getLayoutMapper().insert(node.content);
		for (Node child : node.children.values()) {
			insertContent(child);
		}
	}

}
