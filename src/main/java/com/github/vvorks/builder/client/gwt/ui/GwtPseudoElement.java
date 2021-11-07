package com.github.vvorks.builder.client.gwt.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.vvorks.builder.client.common.ui.DomElement;

public class GwtPseudoElement implements DomElement {

	private String tag;

	private GwtPseudoElement parent;

	private List<GwtPseudoElement> children;

	private Map<String, String> attributes;

	private String text;

	private int scrollX;

	private int scrollY;

	public GwtPseudoElement(Object tag) {
		this.tag = tag.toString();
	}

	@Override
	public void setParent(DomElement newParent) {
		GwtPseudoElement np = (GwtPseudoElement) newParent;
		GwtPseudoElement me = this;
		GwtPseudoElement op = me.parent;
		if (np != op) {
			if (op != null) {
				op.removeChild0(me);
			}
			if (np != null) {
				np.appendChild0(me);
			}
		}

	}

	@Override
	public DomElement appendChild(DomElement newChild) {
		newChild.setParent(this);
		return newChild;
	}

	private void appendChild0(GwtPseudoElement newChild) {
		if (children == null) {
			children = new ArrayList<>();
		}
		children.add(newChild);
	}

	@Override
	public DomElement removeChild(DomElement oldChild) {
		oldChild.setParent(null);
		return oldChild;
	}

	public void removeChild0(GwtPseudoElement oldChild) {
		if (children != null) {
			children.remove(oldChild);
		}
	}

	@Override
	public void setAttribute(String name, String value) {
		if (attributes == null) {
			attributes = new LinkedHashMap<>();
		}
		attributes.put(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		if (attributes != null) {
			attributes.remove(name);
		}
	}

	@Override
	public void setInnerText(String text) {
		this.text = text;
	}

	@Override
	public void setInnerHtml(String html) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setScrollPosition(int x, int y) {
		this.scrollX = x;
		this.scrollY = y;
	}

}
