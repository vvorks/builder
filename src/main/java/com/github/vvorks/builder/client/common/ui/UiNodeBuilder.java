package com.github.vvorks.builder.client.common.ui;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Function;

import com.github.vvorks.builder.shared.common.lang.Asserts;

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

	public UiNodeBuilder enter(UiNode node, LayoutParam param) {
		stack.peek().appendChild(node, param);
		stack.push(node);
		return this;
	}

	public UiNodeBuilder leave() {
		stack.pop();
		return this;
	}

	public UiNodeBuilder text(String text) {
		UiNode node = stack.peek();
		if (node instanceof UiText) {
			UiText textNode = (UiText) node;
			textNode.setText(text);
		}
		return this;
	}

	public UiNodeBuilder action(Function<UiNode, Integer> action) {
		UiNode node = stack.peek();
		if (node instanceof UiButton) {
			UiButton textNode = (UiButton) node;
			textNode.setAction(action);
		}
		return this;
	}

	public UiNodeBuilder loop(boolean loop) {
		UiNode node = stack.peek();
		if (node instanceof UiVerticalList) {
			UiVerticalList listNode = (UiVerticalList) node;
			listNode.setLoopMode(loop);
		}
		return this;
	}

	public UiNodeBuilder flushSoon(boolean soon) {
		UiNode node = stack.peek();
		if (node instanceof UiVerticalList) {
			UiVerticalList listNode = (UiVerticalList) node;
			listNode.setFlushSoon(soon);
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

	public UiNodeBuilder locate(Rect rect) {
		UiNode node = stack.peek();
		node.setBounds(
				Length.pxOf(rect.getLeft()), Length.pxOf(rect.getTop()),
				null, null,
				Length.pxOf(rect.getWidth()), Length.pxOf(rect.getHeight())
				);
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

	public UiNodeBuilder enable(boolean value) {
		UiNode node = stack.peek();
		node.setEnable(value);
		return this;
	}

	public UiNodeBuilder focusable(boolean value) {
		UiNode node = stack.peek();
		node.setFocusable(value);
		return this;
	}

	public UiNodeBuilder source(DataSource source) {
		UiNode node = stack.peek();
		node.setDataSource(source);
		return this;
	}

	public UiNodeBuilder related(UiNode related) {
		UiNode node = stack.peek();
		node.setRelated(related);
		return this;
	}

	public UiNode build() {
		return stack.peekLast();
	}

}