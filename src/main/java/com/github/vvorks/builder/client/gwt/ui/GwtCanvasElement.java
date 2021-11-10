package com.github.vvorks.builder.client.gwt.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.vvorks.builder.client.common.ui.DomElement;
import com.github.vvorks.builder.common.lang.Strings;
import com.google.gwt.dom.client.Document;

public class GwtCanvasElement extends GwtDomElement {

	private GwtCanvasElement parent;

	private List<GwtCanvasElement> children;

	private Map<String, String> attributes;

	private String text;

	private int scrollX;

	private int scrollY;

	public GwtCanvasElement(boolean root) {
		super(root ? Document.get().createCanvasElement() : null);
	}

	@Override
	public void setParent(DomElement newParent) {
		if (nativeElement != null) {
			super.setParent(newParent);
			return;
		}
		GwtCanvasElement np = (GwtCanvasElement) newParent;
		GwtCanvasElement me = this;
		GwtCanvasElement op = me.parent;
		if (np != op) {
			if (op != null) {
				op.removeChild(me);
			}
			if (np != null) {
				np.appendChild(me);
			}
		}
	}

	private void appendChild(GwtCanvasElement newChild) {
		if (children == null) {
			children = new ArrayList<>();
		}
		children.add(newChild);
	}

	public void removeChild(GwtCanvasElement oldChild) {
		if (children != null) {
			children.remove(oldChild);
		}
	}

	@Override
	public void setAttribute(String name, String value) {
		if (nativeElement != null) {
			nativeElement.setAttribute(name, value);
		}
		if (attributes == null) {
			attributes = new LinkedHashMap<>();
		}
		attributes.put(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		if (nativeElement != null) {
			nativeElement.removeAttribute(name);
		}
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
		this.text = Strings.unescapeHtml(html);
	}

	@Override
	public void setScrollPosition(int x, int y) {
		if (nativeElement != null) {
			nativeElement.setScrollLeft(x);
			nativeElement.setScrollTop(y);
		}
		this.scrollX = x;
		this.scrollY = y;
	}

}
