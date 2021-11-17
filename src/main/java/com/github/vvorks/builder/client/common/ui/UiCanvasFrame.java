package com.github.vvorks.builder.client.common.ui;

public class UiCanvasFrame extends UiNode {

	public UiCanvasFrame(String name) {
		super(name);
	}

	protected UiCanvasFrame(UiCanvasFrame src) {
		super(src);
	}

	@Override
	public UiCanvasFrame copy() {
		return new UiCanvasFrame(this);
	}

	@Override
	protected DomElement createDomElement(String ns, String tag, UiNode owner) {
		if (owner != this) {
			return getDocument().createElement(NS_CANVAS, tag, owner);
		}
		return super.createDomElement(NS_HTML, HTML_CANVAS, owner);
	}

}
