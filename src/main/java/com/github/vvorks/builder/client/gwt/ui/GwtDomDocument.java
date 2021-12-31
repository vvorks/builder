package com.github.vvorks.builder.client.gwt.ui;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import com.github.vvorks.builder.client.common.ui.CssStyle;
import com.github.vvorks.builder.client.common.ui.DomDocument;
import com.github.vvorks.builder.client.common.ui.DomElement;
import com.github.vvorks.builder.client.common.ui.Length;
import com.github.vvorks.builder.client.common.ui.Rect;
import com.github.vvorks.builder.client.common.ui.UiAtomicStyle;
import com.github.vvorks.builder.client.common.ui.UiNode;
import com.github.vvorks.builder.common.lang.Asserts;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;

public class GwtDomDocument implements DomDocument {

	private static final int LEFT_BIT	= 0x0001;
	private static final int RIGHT_BIT	= 0x0002;
	private static final int WIDTH_BIT	= 0x0004;
	private static final int TOP_BIT	= 0x0008;
	private static final int BOTTOM_BIT	= 0x0010;
	private static final int HEIGHT_BIT	= 0x0020;
	private static final int HORZ_BITS	= LEFT_BIT|RIGHT_BIT|WIDTH_BIT;
	private static final int VERT_BITS	= TOP_BIT|BOTTOM_BIT|HEIGHT_BIT;

	private static final Map<String, Integer> BOUNDS_MAP = new HashMap<>();
	static {
		BOUNDS_MAP.put("left", LEFT_BIT);
		BOUNDS_MAP.put("right", RIGHT_BIT);
		BOUNDS_MAP.put("width", WIDTH_BIT);
		BOUNDS_MAP.put("top", TOP_BIT);
		BOUNDS_MAP.put("bottom", BOTTOM_BIT);
		BOUNDS_MAP.put("height", HEIGHT_BIT);
	}

	/** 使用ユニット毎のスタイル要素を保持するmap */
	private Map<Class<?>, StyleElement> styles = new LinkedHashMap<>();

	private final DomPanel domPanel;

	public GwtDomDocument(DomPanel domPanel) {
		this.domPanel = domPanel;
	}

	@Override
	public DomElement createElement(String ns, String tag, UiNode owner) {
		if (isCanvas(ns, tag)) {
			return new GwtCanvasElement(this, tag);
		} else {
			return new GwtDomElement(this, tag);
		}
	}

	private boolean isCanvas(String ns, String tag) {
		return	UiNode.NS_CANVAS.equals(ns) ||
				((ns == null || UiNode.NS_HTML.equals(ns)) && UiNode.HTML_CANVAS.equalsIgnoreCase(tag));
	}

	@Override
	public void injectStyleSheet(Class<?> cls, Map<String, CssStyle> cssMap) {
		Asserts.requireNotNull(cls);
		Asserts.requireNotNull(cssMap);
		String cssString = toCssString(cssMap);
		StyleElement style = styles.get(cls);
		if (style == null) {
			style = StyleInjector.injectStylesheet(cssString);
			styles.put(cls, style);
		} else if (!cssString.equals(style.getInnerText())) {
			StyleInjector.setContents(style, cssString);
		}
		Set<String> urls = new LinkedHashSet<>();
		visitImageResource(cssMap, url -> urls.add(url));
		addImageResourceSet(cls, urls);
	}

	private void visitImageResource(Map<String, CssStyle> cssMap, Consumer<String> func) {
		for (CssStyle s : cssMap.values()) {
			for (Map.Entry<String, Object> p : s.getProperties()) {
				String propName = p.getKey();
				Object propValue = p.getValue();
				if (propName.equals("background-image") || propName.equals("border-image")) {
					func.accept(propValue.toString());
				}
			}
		}
	}

	@Override
	public void deinjectStyleSheet(Class<?> cls) {
		Asserts.requireNotNull(cls);
		StyleElement style = styles.get(cls);
		if (style != null) {
			styles.remove(cls);
			style.removeFromParent();
		}
		removeImageResourceSet(cls);
	}

	private String toCssString(Map<String, CssStyle> cssMap) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, CssStyle> e : cssMap.entrySet()) {
			sb.append(e.getKey()).append(" {");
			sb.append(toCssString(e.getValue()));
			sb.append("}\n");
		}
		return sb.toString();
	}

	public String toCssString(CssStyle style) {
		StringBuilder sb = new StringBuilder();
		int boundsBits = 0;
		for (Map.Entry<String, Object> p : style.getProperties()) {
			String propName = p.getKey();
			Object propValue = p.getValue();
			String propStr = String.valueOf(propValue);
			boundsBits |= BOUNDS_MAP.getOrDefault(propName, 0);
			if (propName.equals("background-image")) {
				sb.append(propName).append(":");
					sb.append("url(\"").append(propStr).append("\");");
				sb.append("background-size:100% 100%;");
			} else if (propName.equals("border-image")){
				sb.append("border-image-source:");
					sb.append("url(\"").append(propStr).append("\");");
				sb.append("border-image-slice:");
					sb.append(toPixel(style, "border-top-width"));
					sb.append(" ").append(toPixel(style, "border-right-width"));
					sb.append(" ").append(toPixel(style, "border-bottom-width"));
					sb.append(" ").append(toPixel(style, "border-left-width"));
					sb.append(" fill");
					sb.append(";");
			} else {
				sb.append(propName).append(":").append(propStr).append(";");
			}
		}
		if ((boundsBits & HORZ_BITS) == WIDTH_BIT && (boundsBits & VERT_BITS) == HEIGHT_BIT) {
			sb.append("left:0;right:0;top:0;bottom:0;margin:auto;");
		} else if ((boundsBits & HORZ_BITS) == WIDTH_BIT ) {
			sb.append("left:0;right:0;margin:0 auto;");
		} else if ((boundsBits & VERT_BITS) == HEIGHT_BIT) {
			sb.append("top:0;bottom:0;margin:auto 0;");
		}
		return sb.toString();
	}

	private int toPixel(CssStyle style, String name) {
		return ((Length) style.getProperty(name)).px(0);
	}

	public Panel getBackyard() {
		return domPanel.getBackyard();
	}

	private Map<Class<?>, Set<String>> bundleMap;

	private void addImageResourceSet(Class<?> cls, Set<String> newUrls) {
		if (bundleMap == null) {
			bundleMap = new HashMap<>();
		}
		for (String url : newUrls) {
			addImageResource(url);
		}
		Set<String> oldUrls = bundleMap.put(cls, newUrls);
		if (oldUrls != null) {
			for (String url : oldUrls) {
				removeImageResource(url);
			}
		}
	}

	private void removeImageResourceSet(Class<?> cls) {
		if (bundleMap == null) {
			return;
		}
		Set<String> oldUrls = bundleMap.remove(cls);
		if (oldUrls != null) {
			for (String url : oldUrls) {
				removeImageResource(url);
			}
		}
	}

	private Map<String, ImageItem> imageMap;

	private static class ImageItem {
		private int refCount;
		private Image image;
		private ImageItem(Image image) {
			this.image = image;
			this.refCount = 1;
		}
		private int inc() {
			return ++refCount;
		}
		private int dec() {
			return --refCount;
		}
	}

	private void addImageResource(String url) {
		if (imageMap == null) {
			imageMap = new LinkedHashMap<>();
		}
		ImageItem item = imageMap.get(url);
		if (item != null) {
			item.inc();
		} else {
			Image image = new Image();
			image.addLoadHandler(event -> domPanel.onImageLoaded(url));
			image.setUrl(url);
			getBackyard().add(image);
			imageMap.put(url, new ImageItem(image));
		}
	}

	private void removeImageResource(String url) {
		if (imageMap == null) {
			return;
		}
		ImageItem item = imageMap.get(url);
		if (item != null) {
			if (item.dec() == 0) {
				getBackyard().remove(item.image);
				imageMap.remove(url);
			}
		}
	}

	public Image getImageResource(String url) {
		if (imageMap == null) {
			return null;
		}
		ImageItem item = imageMap.get(url);
		if (item == null) {
			return null;
		}
		return item.image;
	}

	@Override
	public void startEditing(UiNode owner, String text) {
		Rect absRect = owner.getRectangleOnRoot();
		UiAtomicStyle style = owner.getStyle().getAtomicStyleOf(owner);
		int w = absRect.getWidth();
		int h = absRect.getHeight();
		absRect.resize(
				+style.getBorderLeft().px(w),
				+style.getBorderTop().px(h),
				-style.getBorderRight().px(w),
				-style.getBorderBottom().px(h));
		domPanel.startEditing(absRect, style, text);
	}

	/**
	 * 編集を終了する
	 *
	 * @param owner 編集対象ノード
	 * @return 結果文字列
	 */
	public String endEditing(UiNode owner) {
		return domPanel.endEditing();
	}

	@Override
	public void setAxis(int x, int y) {
		domPanel.setAxis(x, y);
	}

}
