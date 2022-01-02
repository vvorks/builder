package com.github.vvorks.builder.client.gwt.ui;

import java.util.Objects;

import com.github.vvorks.builder.client.common.ui.Colors;
import com.github.vvorks.builder.client.common.ui.CssStyle;
import com.github.vvorks.builder.client.common.ui.DomElement;
import com.github.vvorks.builder.client.common.ui.Length;
import com.github.vvorks.builder.client.common.ui.UiAtomicStyle;
import com.github.vvorks.builder.client.common.ui.UiNode;
import com.github.vvorks.builder.common.util.DelayedExecuter;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;

public class GwtDomElement implements DomElement {

	private static final String PROP_CLASS = "class";
	private static final String PROP_STYLE = "style";

	protected static final int CHANGED_LEFT				= 0x0001;
	protected static final int CHANGED_TOP				= 0x0002;
	protected static final int CHANGED_WIDTH			= 0x0004;
	protected static final int CHANGED_HEIGHT			= 0x0008;
	protected static final int CHANGED_SCROLL_X			= 0x0010;
	protected static final int CHANGED_SCROLL_Y			= 0x0020;
	protected static final int CHANGED_SCROLL_WIDTH		= 0x0040;
	protected static final int CHANGED_SCROLL_HEIGHT	= 0x0080;

	protected final GwtDomDocument document;

	protected final Element nativeElement;

	/** 内部テキスト要素 */
	private Element innerElement;

	/** スクロール範囲設定用要素 */
	private Element endElement;

	protected String text;

	protected String imageUrl;

	protected UiAtomicStyle definedStyle;

	protected CssStyle localStyle;

	protected int left;

	protected int top;

	protected int width;

	protected int height;

	protected int scrollX;

	protected int scrollY;

	protected int scrollWidth;

	protected int scrollHeight;

	protected int changed;

	protected GwtDomElement(GwtDomDocument doc, String tag) {
		this.document = doc;
		this.nativeElement = Document.get().createElement(tag);
	}

	protected GwtDomElement(GwtDomDocument doc, Element nativeElement) {
		this.document = doc;
		this.nativeElement = nativeElement;
	}

	public Element getNativeElement() {
		return nativeElement;
	}

	protected boolean hasNativeElement() {
		return nativeElement != null;
	}

	@Override
	public void setParent(DomElement newParent) {
		Element np = newParent != null ? ((GwtDomElement) newParent).getNativeElement() : null;
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

	@Override
	public void setDefinedStyle(UiAtomicStyle style) {
		this.definedStyle = style;
		if (nativeElement == null) {
			return;
		}
		if (style != null) {
			nativeElement.setAttribute(PROP_CLASS, style.getCssClassName());
		} else {
			nativeElement.removeAttribute(PROP_CLASS);
		}
		if (innerElement == null) {
			return;
		}
		String align;
		String valign;
		if (style != null) {
			align = style.getTextAlign();
			valign = style.getVerticalAlign();
		} else {
			align = UiAtomicStyle.TEXT_ALIGN_LEFT;
			valign = UiAtomicStyle.VERTICAL_ALIGN_TOP;
		}
		CssStyle.Builder sb = new CssStyle.Builder()
				.property("position", "absolute")
				.property("overflow", "hidden")
				.property("border-width", Length.ZERO)
				.property("background-color", Colors.toCssColor(Colors.TRANSPARENT))
				.left(Length.ZERO)
				.width(Length.FULL)
				.property("text-align", align);
		if (Objects.equals(valign, UiAtomicStyle.VERTICAL_ALIGN_MIDDLE)) {
			//上下中央ぞろえ
			sb.top(Length.HALF).property("transform", "translateY(-50%)");
		} else if (Objects.equals(valign, UiAtomicStyle.VERTICAL_ALIGN_BOTTOM)) {
			//下ぞろえ
			sb.bottom(Length.ZERO);
		} else {
			//上ぞろえ
			sb.top(Length.ZERO);
		}
		innerElement.setAttribute(PROP_STYLE, document.toCssString(sb.build()));
	}

	@Override
	public void setLocalStyle(CssStyle style) {
		this.localStyle = style;
		if (nativeElement == null) {
			return;
		}
		nativeElement.setAttribute(PROP_STYLE, document.toCssString(style));
	}

	@Override
	public void setInnerText(String text) {
		this.text = text;
		if (nativeElement == null) {
			return;
		}
		if (text != null) {
			ensureInnerElement().setInnerText(text);
		} else {
			removeInnerElement();
		}
	}

	@Override
	public void setBounds(int left, int top, int width, int height) {
		if (this.left != left) {
			this.left = left;
			changed |= CHANGED_LEFT;
		}
		if (this.top != top) {
			this.top = top;
			changed |= CHANGED_TOP;
		}
		if (this.width != width) {
			this.width = width;
			changed |= CHANGED_WIDTH;
		}
		if (this.height != height) {
			this.height = height;
			changed |= CHANGED_HEIGHT;
		}
	}

	@Override
	public void setScrollBounds(int x, int y, int width, int height) {
		if (this.scrollX != x) {
			this.scrollX = x;
			changed |= CHANGED_SCROLL_X;
		}
		if (this.scrollY != y) {
			this.scrollY = y;
			changed |= CHANGED_SCROLL_Y;
		}
		if (this.scrollWidth != width) {
			this.scrollWidth = width;
			changed |= CHANGED_SCROLL_WIDTH;
		}
		if (this.scrollHeight != height) {
			this.scrollHeight = height;
			changed |= CHANGED_SCROLL_HEIGHT;
		}
		if (nativeElement == null) {
			return;
		}
		if (width != 0 || height != 0) {
			Element end = ensureEndElement();
			CssStyle.Builder sb = new CssStyle.Builder()
					.property("position", "absolute")
					.left(new Length(scrollWidth - 1))
					.top(new Length(scrollHeight - 1))
					.width(new Length(1))
					.height(new Length(1));
			end.setAttribute(PROP_STYLE, document.toCssString(sb.build()));
		} else {
			removeEndElement();
		}
		nativeElement.setScrollLeft(x);
		nativeElement.setScrollTop(y);
		if (nativeElement.getScrollLeft() != x || nativeElement.getScrollTop() != y) {
			//要素作成直後（でおそらく未Reflow）の場合、scrollLeft,scrolllTopが設定できない。
			//致し方ないので遅延実行でリトライする
			DelayedExecuter.get().runLator(() -> {
				nativeElement.setScrollLeft(x);
				nativeElement.setScrollTop(y);
			});
		}
	}

	@Override
	public void setImageUrl(String url) {
		this.imageUrl = url;
		if (nativeElement == null || !nativeElement.getTagName().equalsIgnoreCase(UiNode.HTML_IMG)) {
			return;
		}
		if (imageUrl != null) {
			nativeElement.setAttribute("src", imageUrl);
		} else {
			nativeElement.removeAttribute("src");
		}
	}

	@Override
	public void sync() {
		//NOP
	}

	private Element ensureInnerElement() {
		if (innerElement == null) {
			innerElement = Document.get().createDivElement();
			nativeElement.appendChild(innerElement);
		}
		return innerElement;
	}

	private void removeInnerElement() {
		if (innerElement != null) {
			nativeElement.removeChild(innerElement);
			innerElement = null;
		}
	}

	private Element ensureEndElement() {
		if (endElement == null) {
			endElement = Document.get().createDivElement();
			nativeElement.appendChild(endElement);
		}
		return endElement;
	}

	private void removeEndElement() {
		if (endElement != null) {
			nativeElement.removeChild(endElement);
			endElement = null;
		}
	}

}
