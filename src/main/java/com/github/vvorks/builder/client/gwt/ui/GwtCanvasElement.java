package com.github.vvorks.builder.client.gwt.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.vvorks.builder.client.common.ui.Colors;
import com.github.vvorks.builder.client.common.ui.DomElement;
import com.github.vvorks.builder.client.common.ui.Rect;
import com.github.vvorks.builder.client.common.ui.UiAtomicStyle;
import com.github.vvorks.builder.client.common.ui.UiNode;
import com.github.vvorks.builder.common.lang.Strings;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Image;

public class GwtCanvasElement extends GwtDomElement {

	private GwtCanvasElement parent;

	private List<GwtCanvasElement> children;

	private GwtContext2d context2d;

	public GwtCanvasElement(GwtDomDocument doc, String tag) {
		super(doc, UiNode.HTML_CANVAS.equals(tag) ? Document.get().createCanvasElement() : null);
	}

	@Override
	public void setParent(DomElement newParent) {
		if (hasNativeElement()) {
			super.setParent(newParent);
		} else {
			setParentLocal((GwtCanvasElement) newParent);
		}
	}

	@Override
	public void sync() {
		if (!hasNativeElement()) {
			return;
		}
		CanvasElement canvas = ((CanvasElement) getNativeElement());
		if (context2d == null || (changed & (CHANGED_WIDTH|CHANGED_HEIGHT)) != 0) {
			if (context2d == null) {
				context2d = new GwtContext2d(canvas, width, height);
			} else {
				context2d.reinit(canvas, width, height);
			}
		}
		context2d.clearRect(0, 0, width, height); //TODO 仮
		paintChildren(context2d);
		changed = 0;
	}

	protected void paintChildren(GwtContext2d con) {
		if (children != null) {
			int l = definedStyle.getBorderLeft().px(width);
			int t = definedStyle.getBorderTop().px(height);
			int r = definedStyle.getBorderRight().px(width);
			int b = definedStyle.getBorderBottom().px(height);
			con.moveOrigin(+l, +t);
			con.moveOrigin(-scrollX, -scrollY);
			Rect vp = new Rect(scrollX, scrollY, width - l - r, height - t - b);
			for (GwtCanvasElement child : children) {
				Rect vc = new Rect(child.left, child.top, child.width, child.height);
				if (!vc.intersect(vp).isEmpty()) {
					con.save();
					con.clipRect(vc.getLeft(), vc.getTop(), vc.getWidth(), vc.getHeight());
					con.moveOrigin(+child.left, +child.top);
					child.paint(con);
					con.moveOrigin(-child.left, -child.top);
					con.restore();
				}
			}
			con.moveOrigin(+scrollX, +scrollY);
			con.moveOrigin(-l, -t);
		}
	}

	public void paint(GwtContext2d con) {
		//TOOD ここで描画範囲絞り込み
		paintBackground(con);
		paintContent(con);
		paintChildren(con);
		paintBorder(con);
	}

	protected void paintBackground(GwtContext2d con) {
		String bdUrl = definedStyle.getBorderImage();
		String bkUrl = definedStyle.getBackgroundImage();
		if (bdUrl != null) {
			Image img = document.getImageResource(bdUrl);
			int l = definedStyle.getBorderLeft().px(width);
			int t = definedStyle.getBorderTop().px(height);
			int r = definedStyle.getBorderRight().px(width);
			int b = definedStyle.getBorderBottom().px(height);
			con.drawBorderImage(img, 0, 0, width, height, l, t, r, b);
		} else if (bkUrl != null) {
			Image img = document.getImageResource(bkUrl);
			con.drawImage(img, 0, 0, width, height);
		} else {
			con.setFillColor(definedStyle.getBackgroundColor());
			con.setStrokeColor(Colors.TRANSPARENT);
			con.setStrokeWidth(0);
			con.drawRect(0, 0, width, height);
		}
	}

	protected void paintContent(GwtContext2d con) {
		if (Strings.isEmpty(text)) {
			return;
		}
		con.setFillColor(definedStyle.getColor());
		con.setStrokeColor(Colors.TRANSPARENT);
		con.setFontFamily(definedStyle.getFontFamily());
		con.setFontSize(definedStyle.getFontSize().px(width));
		int l = definedStyle.getBorderLeft().px(width);
		int t = definedStyle.getBorderTop().px(height);
		int r = definedStyle.getBorderRight().px(width);
		int b = definedStyle.getBorderBottom().px(height);
		int lineHeight = definedStyle.getLineHeight().px(height);
		int flags = get2dAlign(definedStyle) | GwtContext2d.DRAW_ELLIPSIS;
		con.drawText(l, t, width - l - r, height - t - b, text, lineHeight, flags);
	}

	protected void paintBorder(GwtContext2d con) {
		String bdUrl = definedStyle.getBorderImage();
		String bkUrl = definedStyle.getBackgroundImage();
		if (bdUrl != null) {
		} else if (bkUrl != null) {
		} else {
			int l = definedStyle.getBorderLeft().px(width);
			int t = definedStyle.getBorderTop().px(height);
			int r = definedStyle.getBorderRight().px(width);
			int b = definedStyle.getBorderBottom().px(height);
			con.setStrokeWidth(0);
			con.setFillColor(definedStyle.getBorderColor());
			con.drawRect(0, 0, width, t);
			con.drawRect(0, t, r, height - t - b);
			con.drawRect(width - l, t, width, height - t - b);
			con.drawRect(0, height - b, width, height);
		}
	}

	private void setParentLocal(GwtCanvasElement newParent) {
		GwtCanvasElement np = newParent;
		GwtCanvasElement me = this;
		GwtCanvasElement op = me.parent;
		if (np != op) {
			if (op != null) {
				op.removeChild(me);
			}
			if (np != null) {
				np.addChild(me);
			}
			me.parent = np;
		}
	}

	private void addChild(GwtCanvasElement child) {
		if (children == null) {
			children = new ArrayList<>();
		}
		children.add(child);
	}

	private void removeChild(GwtCanvasElement child) {
		if (children != null) {
			children.remove(child);
		}
	}

	private static final Map<String, Integer> ALIGN_MAP = new HashMap<>();
	static {
		ALIGN_MAP.put(UiAtomicStyle.TEXT_ALIGN_LEFT,    GwtContext2d.ALIGN_LEFT);
		ALIGN_MAP.put(UiAtomicStyle.TEXT_ALIGN_CENTER,  GwtContext2d.ALIGN_CENTER);
		ALIGN_MAP.put(UiAtomicStyle.TEXT_ALIGN_RIGHT,   GwtContext2d.ALIGN_RIGHT);
		ALIGN_MAP.put(UiAtomicStyle.TEXT_ALIGN_JUSTIFY, GwtContext2d.ALIGN_JUSTIFY);
	}

	private static final Map<String, Integer> VALIGN_MAP = new HashMap<>();
	static {
		VALIGN_MAP.put(UiAtomicStyle.VERTICAL_ALIGN_TOP,    GwtContext2d.VALIGN_TOP);
		VALIGN_MAP.put(UiAtomicStyle.VERTICAL_ALIGN_MIDDLE, GwtContext2d.VALIGN_MIDDLE);
		VALIGN_MAP.put(UiAtomicStyle.VERTICAL_ALIGN_BOTTOM, GwtContext2d.VALIGN_BOTTOM);
	}

	private static int get2dAlign(UiAtomicStyle style) {
		int align = 0;
		Integer hAlign = ALIGN_MAP.get(style.getTextAlign());
		align |= hAlign != null ? hAlign.intValue() : GwtContext2d.ALIGN_JUSTIFY;
		Integer vAlign = VALIGN_MAP.get(style.getVerticalAlign());
		align |= vAlign != null ? vAlign.intValue() : GwtContext2d.VALIGN_TOP;
		return align;
	}

}
