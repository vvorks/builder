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
import com.github.vvorks.builder.client.common.ui.UiNode;
import com.github.vvorks.builder.common.lang.Asserts;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;

public class GwtDomDocument implements DomDocument {

	private static final String[] BOUNDS_KEYS = {
		"left", "right", "width", "top", "bottom", "height"
	};

	private static final int LEFT_BIT	= 0x0001;
	private static final int RIGHT_BIT	= 0x0002;
	private static final int WIDTH_BIT	= 0x0004;
	private static final int TOP_BIT	= 0x0008;
	private static final int BOTTOM_BIT	= 0x0010;
	private static final int HEIGHT_BIT	= 0x0020;
	private static final int HORZ_BITS	= LEFT_BIT|RIGHT_BIT|WIDTH_BIT;
	private static final int VERT_BITS	= TOP_BIT|BOTTOM_BIT|HEIGHT_BIT;

	private static int getBoundsBit(String key) {
		for (int i = 0; i < BOUNDS_KEYS.length; i++) {
			if (BOUNDS_KEYS[i].equals(key)) {
				return 1 << i;
			}
		}
		return 0;
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
				((ns == null || UiNode.NS_HTML.equals(ns)) && UiNode.HTML_CANVAS.equals(tag));
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
			boundsBits |= getBoundsBit(propStr);
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
			sb.append("left:50%;top:50%;transform:translate(-50%,-50%)");
		} else if ((boundsBits & HORZ_BITS) == WIDTH_BIT ) {
			sb.append("left:50%;transform:translateX(-50%)");
		} else if ((boundsBits & VERT_BITS) == HEIGHT_BIT) {
			sb.append("top:50%;transform:translateY(-50%)");
		}
		return sb.toString();
	}

	private int toPixel(CssStyle style, String name) {
		return ((Length) style.getProperty(name)).px(()->0);
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

}
