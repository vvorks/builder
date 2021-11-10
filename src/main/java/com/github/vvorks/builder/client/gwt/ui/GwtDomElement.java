package com.github.vvorks.builder.client.gwt.ui;

import com.github.vvorks.builder.client.common.ui.DomElement;
import com.github.vvorks.builder.client.common.ui.UiNode;
import com.google.gwt.dom.client.Element;

public abstract class GwtDomElement implements DomElement {

	public static GwtDomElement create(String ns, String tag, UiNode owner) {
		if (UiNode.NS_CANVAS.equals(ns)) {
			return new GwtCanvasElement(false);
		}  else if (UiNode.NS_HTML.equals(ns) && UiNode.HTML_CANVAS.equals(tag)) {
			return new GwtCanvasElement(true);
		} else {
			return new GwtHtmlElement(tag);
		}
	}

	protected final Element nativeElement;

	protected GwtDomElement(Element nativeElement) {
		this.nativeElement = nativeElement;
	}

	public Element getNativeElement() {
		return nativeElement;
	}

	@Override
	public void setParent(DomElement newParent) {
		Element np = newParent != null ? ((GwtHtmlElement) newParent).getNativeElement() : null;
		Element me = this.getNativeElement();
		Element op = me.getParentElement();
		if (np != op) {
			if (op != null) {
				op.removeChild(me);
			}
			if (np != null) {
				np.appendChild(me);
			}
		}
	}

}
