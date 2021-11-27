package com.github.vvorks.builder.client.common.ui;

import java.util.ArrayDeque;
import java.util.Deque;

import com.github.vvorks.builder.common.lang.Asserts;

public class UiNodeBuilder {

	private final Deque<UiNode> stack;

	private final Length.Unit unit;

	public static final double NA = Double.NaN;

	public UiNodeBuilder(UiNode node, String defaultUnit) {
		stack = new ArrayDeque<>();
		stack.add(node);
		this.unit = Length.unitOf(defaultUnit);
	}

	public UiNodeBuilder enter(UiNode node) {
		stack.peek().appendChild(node);
		stack.push(node);
		return this;
	}

	public UiNodeBuilder leave() {
		stack.pop();
		return this;
	}

	public UiNodeBuilder text(String text) {
		UiNode node = stack.peek();
		if (node instanceof UiButton) {
			UiButton textNode = (UiButton) node;
			textNode.setText(text);
		}
		return this;
	}

	public UiNodeBuilder loop(boolean loop) {
		UiNode node = stack.peek();
		if (node instanceof UiList) {
			UiList listNode = (UiList) node;
			listNode.setLoopMode(loop);
		}
		return this;
	}

	public UiNodeBuilder locate(String left, String top, String right, String bottom, String width, String height) {
		UiNode node = stack.peek();
		node.setBounds(left, top, right, bottom, width, height);
		return this;
	}

	public UiNodeBuilder locate(double left, double top, double right, double bottom, double width, double height) {
		UiNode node = stack.peek();
		node.setBounds(
				encode(left), encode(top), encode(right), encode(bottom),
				encode(width), encode(height));
		return this;
	}

	private Length encode(double value) {
		if (Double.isNaN(value)) {
			return null;
		} else {
			return new Length(value, unit);
		}
	}

	public UiNodeBuilder size(String width, String height) {
		UiNode node = stack.peek();
		node.setBounds(null, null, null, null, width, height);
		return this;
	}

	public UiNodeBuilder size(double width, double height) {
		UiNode node = stack.peek();
		node.setBounds(null, null, null, null, encode(width), encode(height));
		return this;
	}

	public UiNodeBuilder style(UiStyle s) {
		UiNode node = stack.peek();
		node.setStyle(s);
		return this;
	}

	public UiNodeBuilder spacing(String value) {
		UiNode node = stack.peek();
		Asserts.assume(node instanceof UiGroup);
		UiGroup group = (UiGroup) node;
		group.setSpacing(value);
		return this;
	}

	public UiNodeBuilder spacing(double value) {
		UiNode node = stack.peek();
		Asserts.assume(node instanceof UiGroup);
		UiGroup group = (UiGroup) node;
		group.setSpacing(encode(value));
		return this;
	}

	public UiNodeBuilder source(DataSource source) {
		UiNode node = stack.peek();
		Asserts.assume(node instanceof UiList);
		UiList list = (UiList) node;
		list.setDataSource(source);
		return this;
	}

	public UiNode build() {
		return stack.peekLast();
	}

}