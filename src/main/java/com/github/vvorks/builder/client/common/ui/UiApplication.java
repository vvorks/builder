package com.github.vvorks.builder.client.common.ui;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import com.github.vvorks.builder.client.ClientSettings;
import com.github.vvorks.builder.client.common.net.WebSocket;
import com.github.vvorks.builder.common.lang.Asserts;
import com.github.vvorks.builder.common.lang.Creator;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.lang.Iterables;
import com.github.vvorks.builder.common.logging.Logger;

public class UiApplication implements EventHandler {

	public static final Class<?> THIS = UiApplication.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	/** イベント結果フラグ： イベントは無視された */
	public static final int EVENT_IGNORED = 0x00;

	/** イベント結果フラグ： イベントは消費された */
	public static final int EVENT_CONSUMED = 0x01;

	/** イベント結果フラグ： イベントによりUi要素の状態が変化した */
	public static final int EVENT_AFFECTED = 0x02;

	/** イベント結果フラグ： イベントは消費され、かつUi要素の状態が変化した */
	public static final int EVENT_EATEN = EVENT_CONSUMED | EVENT_AFFECTED;

	/** イベント結果フラグ： システム動作の抑止は行わない */
	public static final int EVENT_NOPREVENT = 0x04;

	/** イベント結果フラグ： イベントは消費されたが、システム動作の抑止は行わない */
	public static final int EVENT_THROUGH = EVENT_CONSUMED|EVENT_NOPREVENT;

	/** 軸設定フラグ： 軸設定しない */
	public static final int AXIS_NO = 0x00;

	/** 軸設定フラグ： X軸を設定する */
	public static final int AXIS_X  = 0x01;

	/** 軸設定フラグ： Y軸を設定する */
	public static final int AXIS_Y  = 0x02;

	/** 軸設定フラグ： XY軸を設定する */
	public static final int AXIS_XY = AXIS_X|AXIS_Y;

	private static final CssStyle BODY_STYLE = new CssStyle.Builder()
			.property("user-select", "none")
			.build();

	private static final CssStyle RESET_STYLE = new CssStyle.Builder()
			.property("box-sizing", "border-box")
			.property("margin", "0")
			.property("border-width", "0")
			.property("padding", "0")
			.property("overflow", "hidden")
			.property("border-style", "solid")
			.build();

	private static class LivePage {
		private final UiPage page;
		private final Point axis;
		private UiNode focus;
		private UiNode capture;
		private UiNode click;
		public LivePage(UiPage page) {
			this.page = page;
			this.axis = new Point(0, 0);
			this.focus = null;
			this.capture = null;
			this.click = null;
		}
	}

	private final UiRoot root;

	private final DomDocument document;

	private Map<String, Creator<UiPage>> pages;

	private Deque<LivePage> pageStack;

	private Map<String, UiStyle> styles;

	private final WebSocket socket;

	private final Map<DataSource, Set<UiNode>> dataSourceMap;

	public UiApplication(DomDocument doc) {
		this.document = doc;
		this.root = new UiRoot(document);
		this.pages = new LinkedHashMap<>();
		this.pageStack = new ArrayDeque<>();
		this.styles = new LinkedHashMap<>();
		this.socket = Factory.newInstance(WebSocket.class);
		this.dataSourceMap = new HashMap<>();
		try {
			socket.open(ClientSettings.SERVER_URL);
		} catch (IOException err) {
			throw new RuntimeException(err);
		}
	}

	public DomElement getRootElement() {
		return root.getDomElement();
	}

	public DomDocument getDocument() {
		return document;
	}

	public void registerStyle(UiStyle style) {
		styles.put(style.getName(), style);
	}

	public void unregisterStyle(UiStyle style) {
		styles.remove(style.getName());
	}

	public UiStyle getRegisteredStyle(String name) {
		return styles.get(name);
	}

	public void injectRegisteredStyles() {
		Map<String, CssStyle> cssMap = new LinkedHashMap<>();
		cssMap.put("BODY", BODY_STYLE);
		cssMap.put("BODY,DIV", RESET_STYLE);
		UiStyle.toCssStyles(styles.values(), cssMap);
		document.injectStyleSheet(getClass(), cssMap);
	}

	protected void addPage(String tag, Creator<UiPage> creator) {
		pages.put(tag, creator);
	}

	protected void removePage(String tag) {
		pages.remove(tag);
	}

	public void call(String tag, Map<String, String> params) {
		try {
			call(pages.get(tag).create(params));
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}

	public void call(UiPage newPage) {
		LivePage p = new LivePage(newPage);
		root.appendChild(p.page);
		pageStack.push(p);
		p.page.onMount();
		if (p.focus == null) {
			setFocus(getFirstFocus(p.page), AXIS_XY);
		}
	}

	public UiNode getFirstFocus(UiNode owner) {
		Iterable<UiNode> candidates = Iterables.filter(
				owner.getFocusCandidates(), c -> c.getBlocker() == null);
		return Iterables.getFirst(candidates, null);
	}

	public void back() {
		LivePage p = getLivePage();
		p.page.onUnmount();
		pageStack.pop();
		root.removeChild(p.page);
	}

	private LivePage getLivePage() {
		if (pageStack.isEmpty()) {
			throw new IllegalStateException();
		}
		return pageStack.peek();
	}

	private LivePage getLivePage(LivePage defaultPage) {
		return !pageStack.isEmpty() ? pageStack.peek() : defaultPage;
	}

	private LivePage getLivePageOf(UiPage page) {
		for (LivePage p : pageStack) {
			if (p.page == page) {
				return p;
			}
		}
		throw new IllegalStateException();
	}

	public UiNode getCapture() {
		LivePage p = getLivePage();
		return p.capture;
	}

	public UiNode setCapture(UiNode newCaptureNode) {
		LivePage p = getLivePage();
		UiNode oldCaptureNode = p.capture;
		p.capture = newCaptureNode;
		return oldCaptureNode;
	}

	public UiNode releaseCapture() {
		LivePage p = getLivePage();
		UiNode oldCaptureNode = p.capture;
		p.capture = null;
		return oldCaptureNode;
	}

	public UiNode getFocus(UiNode node) {
		Asserts.requireNotNull(node);
		UiPage page = node.getPage();
		Asserts.requireNotNull(page);
		LivePage p = getLivePageOf(page);
		Asserts.assume(p != null);
		return p.focus;
	}

	private UiNode getFocus() {
		LivePage p = getLivePage();
		Asserts.assume(p != null);
		return p.focus;
	}

	public UiNode setFocus(UiNode newFocusNode) {
		return setFocus(newFocusNode, AXIS_XY);
	}

	public UiNode setFocus(UiNode newFocusNode, int axis) {
		Asserts.requireNotNull(newFocusNode);
		UiPage page = newFocusNode.getPage();
		Asserts.requireNotNull(page);
		LivePage p = getLivePageOf(page);
		UiNode oldFocusNode = p.focus;
		if (oldFocusNode != newFocusNode) {
			LOGGER.debug("FOCUS %s -> %s", getQualifiedName(oldFocusNode, page), getQualifiedName(newFocusNode, page));
			notifyFocus(oldFocusNode, newFocusNode);
			p.focus = newFocusNode;
			adjustAxis(p, axis);
		}
		return oldFocusNode;
	}

	public Point getAxis(UiNode node) {
		Asserts.requireNotNull(node);
		UiPage page = node.getPage();
		Asserts.requireNotNull(page);
		LivePage p = getLivePageOf(page);
		return new Point(p.axis);
	}

	public void adjustAxis(UiNode node, int axis) {
		Asserts.requireNotNull(node);
		UiPage page = node.getPage();
		Asserts.requireNotNull(page);
		LivePage p = getLivePageOf(page);
		adjustAxis(p, axis);
	}

	protected void adjustAxis(LivePage p, int axis) {
		Rect r = p.focus.getRectangleOnRoot();
		if ((axis & AXIS_X) != 0) {
			p.axis.setX(r.getCenterX());
		}
		if ((axis & AXIS_Y) != 0) {
			p.axis.setY(r.getTop());
		}
		if (axis != AXIS_NO) {
			getDocument().setAxis(p.axis.getX(), p.axis.getY());
		}
	}

	public void attachDataSource(UiNode node, DataSource ds) {
		Set<UiNode> nodes = getAttachedNodes(ds);
		if (nodes.isEmpty()) {
			ds.attach(this);
		}
		nodes.add(node);
	}

	public void detachDataSource(UiNode node, DataSource ds) {
		Set<UiNode> nodes = getAttachedNodes(ds);
		nodes.remove(node);
		if (nodes.isEmpty()) {
			ds.detach(this);
		}
	}

	private Set<UiNode> getAttachedNodes(DataSource ds) {
		return dataSourceMap.computeIfAbsent(ds, k -> new LinkedHashSet<>());
	}

	private String getQualifiedName(UiNode node, UiNode qualifier) {
		return node == null ? null : node.getQualifiedName(qualifier);
	}

	private void notifyFocus(UiNode oldFocus, UiNode newFocus) {
		UiNode luca = (oldFocus != null && newFocus != null) ? newFocus.getLucaWith(oldFocus) : null;
		if (oldFocus != null) {
			UiNode node = oldFocus;
			do {
				node.onFocus(oldFocus, false, newFocus);
				node = node.getParent();
			} while (node != null && node != luca);
		}
		if (newFocus != null) {
			UiNode node = newFocus;
			do {
				node.onFocus(newFocus, true, oldFocus);
				node = node.getParent();
			} while (node != null && node != luca);
		}
	}

	public void processInitialize(int screenWidth, int screenHeight) {
		try {
			LOGGER.info("processInitialize(%d, %d)", screenWidth, screenHeight);
			injectRegisteredStyles();
			this.onResize(screenWidth, screenHeight);
			root.onResize(screenWidth, screenHeight);
			refresh();
		} catch (Exception|AssertionError err) {
			LOGGER.error(err);
			throw err;
		}
	}

	public void processLoad(String tag, Map<String, String> params) {
		try {
			LOGGER.info("processLoad(%s, %s)", tag, params);
			call(tag, params);
			refresh();
		} catch (Exception|AssertionError err) {
			LOGGER.error(err);
			throw err;
		}
	}

	public int processKeyDown(int keyCode, int charCode, int mods, int time) {
		try {
			LOGGER.info("processKeyDown(0x%x, 0x%x, 0x%x, %d)", keyCode, charCode, mods, time);
			UiNode target = getKeyTarget();
			UiNode node = target;
			int result = node.onKeyDown(target, keyCode, charCode, mods, time);
			while ((result & EVENT_CONSUMED) == 0 && node.getParent() != null) {
				node = node.getParent();
				result |= node.onKeyDown(target, keyCode, charCode, mods, time);
			}
			if ((result & EVENT_CONSUMED) == 0) {
				//UiNode側の処理でフォーカスが変化している場合があるので、再取得
				UiNode focus = getKeyTarget();
				result |= this.onKeyDown(focus, keyCode, charCode, mods, time);
			}
			if ((result & EVENT_AFFECTED) != 0) {
				refresh();
			}
			return result;
		} catch (Exception|AssertionError err) {
			LOGGER.error(err);
			throw err;
		}
	}

	public int processKeyPress(int keyCode, int charCode, int mods, int time) {
		try {
			LOGGER.info("processKeyPress(0x%x, 0x%x, 0x%x, %d)", keyCode, charCode, mods, time);
			UiNode target = getKeyTarget();
			UiNode node = target;
			int result = node.onKeyPress(target, keyCode, charCode, mods, time);
			while ((result & EVENT_CONSUMED) == 0 && node.getParent() != null) {
				node = node.getParent();
				result |= node.onKeyPress(target, keyCode, charCode, mods, time);
			}
			if ((result & EVENT_CONSUMED) == 0) {
				//UiNode側の処理でフォーカスが変化している場合があるので、再取得
				UiNode focus = getKeyTarget();
				result |= this.onKeyPress(focus, keyCode, charCode, mods, time);
			}
			if ((result & EVENT_AFFECTED) != 0) {
				refresh();
			}
			return result;
		} catch (Exception|AssertionError err) {
			LOGGER.error(err);
			throw err;
		}
	}

	public int processKeyUp(int keyCode, int charCode, int mods, int time) {
		try {
			LOGGER.info("processKeyUp(0x%x, 0x%x, 0x%x, %d)", keyCode, charCode, mods, time);
			UiNode target = getKeyTarget();
			UiNode node = target;
			int result = node.onKeyUp(target, keyCode, charCode, mods, time);
			while ((result & EVENT_CONSUMED) == 0 && node.getParent() != null) {
				node = node.getParent();
				result |= node.onKeyUp(target, keyCode, charCode, mods, time);
			}
			if ((result & EVENT_CONSUMED) == 0) {
				//UiNode側の処理でフォーカスが変化している場合があるので、再取得
				UiNode focus = getKeyTarget();
				result |= this.onKeyUp(focus, keyCode, charCode, mods, time);
			}
			if ((result & EVENT_AFFECTED) != 0) {
				refresh();
			}
			return result;
		} catch (Exception|AssertionError err) {
			LOGGER.error(err);
			throw err;
		}
	}

	public int processMouseMove(int x, int y, int mods, int time) {
		try {
			Point pt = new Point(x, y);
			UiNode target = getMouseTarget(pt);
			UiNode node = target;
			int result = node.onMouseMove(target, pt.getX(), pt.getY(), mods, time);
			while ((result & EVENT_CONSUMED) == 0 && node.getParent() != null) {
				node.translate(pt, -1);
				node = node.getParent();
				result |= node.onMouseMove(target, pt.getX(), pt.getY(), mods, time);
			}
			if ((result & EVENT_CONSUMED) == 0) {
				result |= this.onMouseMove(target, pt.getX(), pt.getY(), mods, time);
			}
			if ((result & EVENT_AFFECTED) != 0) {
				refresh();
			}
			return result;
		} catch (Exception|AssertionError err) {
			LOGGER.error(err);
			throw err;
		}
	}

	public int processMouseDown(int x, int y, int mods, int time) {
		try {
			LOGGER.info("processMouseDown(%d, %d, 0x%x, %d)", x, y, mods, time);
			Point pt = new Point(x, y);
			UiNode target = getMouseTarget(pt);
			UiNode node = target;
			int result = node.onMouseDown(target, pt.getX(), pt.getY(), mods, time);
			while ((result & EVENT_CONSUMED) == 0 && node.getParent() != null) {
				node.translate(pt, -1);
				node = node.getParent();
				result |= node.onMouseDown(target, pt.getX(), pt.getY(), mods, time);
			}
			if ((result & EVENT_CONSUMED) == 0) {
				result |= this.onMouseDown(target, pt.getX(), pt.getY(), mods, time);
			}
			if ((result & EVENT_AFFECTED) != 0) {
				refresh();
			}
			return result;
		} catch (Exception|AssertionError err) {
			LOGGER.error(err);
			throw err;
		}
	}

	public int processMouseUp(int x, int y, int mods, int time) {
		try {
			LOGGER.info("processMouseUp(%d, %d, 0x%x, %d)", x, y, mods, time);
			Point pt = new Point(x, y);
			UiNode target = getMouseTarget(pt);
			UiNode node = target;
			int result = node.onMouseUp(target, pt.getX(), pt.getY(), mods, time);
			while ((result & EVENT_CONSUMED) == 0 && node.getParent() != null) {
				node.translate(pt, -1);
				node = node.getParent();
				result |= node.onMouseUp(target, pt.getX(), pt.getY(), mods, time);
			}
			if ((result & EVENT_CONSUMED) == 0) {
				result |= this.onMouseUp(target, pt.getX(), pt.getY(), mods, time);
			}
			if ((result & EVENT_AFFECTED) != 0) {
				refresh();
			}
			return result;
		} catch (Exception|AssertionError err) {
			LOGGER.error(err);
			throw err;
		}
	}

	public int processMouseClick(int x, int y, int mods, int time) {
		try {
			LOGGER.info("processMouseClick(%d, %d, 0x%x, %d)", x, y, mods, time);
			Point pt = new Point(x, y);
			UiNode target = getMouseTarget(pt);
			UiNode node = target;
			int result = node.onMouseClick(target, pt.getX(), pt.getY(), mods, time);
			while ((result & EVENT_CONSUMED) == 0 && node.getParent() != null) {
				node.translate(pt, -1);
				node = node.getParent();
				result |= node.onMouseClick(target, pt.getX(), pt.getY(), mods, time);
			}
			if ((result & EVENT_CONSUMED) == 0) {
				result |= this.onMouseClick(target, pt.getX(), pt.getY(), mods, time);
			}
			if ((result & EVENT_AFFECTED) != 0) {
				refresh();
			}
			return result;
		} catch (Exception|AssertionError err) {
			LOGGER.error(err);
			throw err;
		}
	}

	public int processMouseWheel(int x, int y, int dx, int dy, int mods, int time) {
		try {
			LOGGER.info("processMouseWheel(%d, %d, %d, %d, 0x%x, %d)", x, y, dx, dy, mods, time);
			Point pt = new Point(x, y);
			UiNode target = getMouseTarget(pt);
			UiNode node = target;
			int result = node.onMouseWheel(target, pt.getX(), pt.getY(), dx, dy, mods, time);
			while ((result & EVENT_CONSUMED) == 0 && node.getParent() != null) {
				node.translate(pt, -1);
				node = node.getParent();
				result |= node.onMouseWheel(target, pt.getX(), pt.getY(), dx, dy, mods, time);
			}
			if ((result & EVENT_CONSUMED) == 0) {
				result |= this.onMouseWheel(target, pt.getX(), pt.getY(), dx, dy, mods, time);
			}
			if ((result & EVENT_AFFECTED) != 0) {
				refresh();
			}
			return result;
		} catch (Exception|AssertionError err) {
			LOGGER.error(err);
			throw err;
		}
	}

	public int processResize(int screenWidth, int screenHeight, int time) {
		try {
			LOGGER.info("processResize(%d, %d, %d)", screenWidth, screenHeight, time);
			this.onResize(screenWidth, screenHeight);
			root.onResize(screenWidth, screenHeight);
			refresh();
			return UiNode.EVENT_EATEN;
		} catch (Exception|AssertionError err) {
			LOGGER.error(err);
			throw err;
		}
	}

	public int processImageLoaded(String url, int time) {
		try {
			LOGGER.info("processImageLoaded(%s, %d)", url, time);
			int result = EVENT_IGNORED;
			result |= root.onImageLoaded(url);
			result |= this.onImageLoaded(url);
			if ((result & EVENT_AFFECTED) != 0) {
				refresh();
			}
			return result;
		} catch (Exception|AssertionError err) {
			LOGGER.error(err);
			throw err;
		}
	}

	public int processDataSourceUpdated(DataSource ds, int time) {
		try {
			LOGGER.info("processDataSourceUpdated(%s, %d)", ds, time);
			int result = EVENT_IGNORED;
			for (UiNode node : getAttachedNodes(ds)) {
				result |= node.onDataSourceUpdated(ds);
			}
			result |= this.onDataSourceUpdated(ds);
			if ((result & EVENT_AFFECTED) != 0) {
				refresh();
			}
			return result;
		} catch (Exception|AssertionError err) {
			LOGGER.error(err);
			throw err;
		}

	}


	private UiNode getKeyTarget() {
		return getFocus();
	}

	private UiNode getMouseTarget(Point pt) {
		LivePage p = getLivePage(null);
		UiNode node;
		if (p == null) {
			node = root;
		} else if (p.capture == null) {
			UiNode child = p.page;
			node = child.getParent();
			while (child != null) {
				node = child;
				node.translate(pt, +1);
				if (node.isHookEvents()) {
					break;
				}
				child = node.getVisibleChildAt(pt.getX(), pt.getY());
			}
		} else {
			node = p.capture;
			while (node != null) {
				node.translate(pt, +1);
				node = node.getParent();
			}
			node = p.capture;
		}
		return node;
	}

	private void refresh() {
//		if (ClientSettings.DEBUG) {
//			LivePage p = getLivePage(null);
//			if (p != null) {
//				StringBuilder sb = new StringBuilder();
//				for (UiNode node : p.page.getDescendantsIf(d -> d.isChanged())) {
//					sb.append(",").append(node.getQualifiedName(p.page));
//				}
//				if (sb.length() > 0) {
//					LOGGER.debug("changed %s", sb.substring(1));
//				}
//			}
//		}
		root.sync();
	}

	@Override
	public int onKeyDown(UiNode target, int keyCode, int charCode, int mods, int time) {
		int result = EVENT_IGNORED;
		Rect tRect = target.getRectangleOn(root);
		UiNode next = null;
		int axis = AXIS_NO;
		switch (keyCode) {
		case KeyCodes.LEFT:
			next = getNearestNode(target, c -> {
				Rect r = c.getRectangleOn(root);
				return r.getRight() <= tRect.getLeft() ? r : null;
			});
			axis = AXIS_X;
			break;
		case KeyCodes.RIGHT:
			next = getNearestNode(target, c -> {
				Rect r = c.getRectangleOn(root);
				return r.getLeft() >= tRect.getRight() ? r : null;
			});
			axis = AXIS_X;
			break;
		case KeyCodes.UP:
			next = getNearestNode(target, c -> {
				Rect r = c.getRectangleOn(root);
				return r.getBottom() <= tRect.getTop() ? r : null;
			});
			axis = AXIS_Y;
			break;
		case KeyCodes.DOWN:
			next = getNearestNode(target, c -> {
				Rect r = c.getRectangleOn(root);
				return r.getTop() >= tRect.getBottom() ? r : null;
			});
			axis = AXIS_Y;
			break;
		case KeyCodes.TAB:
			int dir = (mods & KeyCodes.MOD_SHIFT) != 0 ? -1 : +1;
			next = getAdjacentNode(target, dir, c -> true);
			axis = AXIS_XY;
			result |= EVENT_CONSUMED;
			break;
		case KeyCodes.ENTER:
			result |= clearClicking() | setClicking(target);
			break;
		case KeyCodes.SPACE:
			if (ClientSettings.DEBUG) {
				LOGGER.debug(root.toString());
			}
			break;
		default:
			break;
		}
		if (next != null) {
			scrollFor(next);
			setFocus(next, axis);
			result |= EVENT_AFFECTED;
		}
		return result;
	}

	public UiNode getNearestNode(UiNode curr, Function<UiNode, Rect> getRect) {
		LivePage p = getLivePage();
		UiNode next = null;
		int minDegree = Integer.MAX_VALUE;
		double minDistance = Double.POSITIVE_INFINITY;
		for (UiNode c : p.page.getFocusCandidates(curr)) {
			Rect cRect = getRect.apply(c);
			if (cRect != null) {
				UiNode blocker = c.getBlocker();
				UiNode luca = c.getLucaWith(curr);
				if ((blocker == null || blocker == luca || blocker.isAncestor(luca))) {
					int degree = curr.getDegree(luca);
					double distance = cRect.distance(p.axis);
					if (degree < minDegree || (degree == minDegree && distance < minDistance)) {
						next = c;
						minDegree = degree;
						minDistance = distance;
					}
				}
			}
		}
		return next;
	}

	public UiNode getAdjacentNode(UiNode curr, int dir, Predicate<UiNode> pred) {
		LivePage p = getLivePage();
		Asserts.assume(p != null);
		List<UiNode> list = new ArrayList<>();
		int index = -1;
		for (UiNode c : p.page.getFocusCandidates()) {
			if (pred.test(c)) {
				UiNode blocker = c.getBlocker();
				UiNode luca = c.getLucaWith(curr);
				if ((blocker == null || blocker == luca || blocker.isAncestor(luca))) {
					if (c == curr) {
						index = list.size();
					}
					list.add(c);
				}
			}
		}
		if (index < 0) {
			return null;
		}
		int n = list.size();
		return list.get((index + dir + n) % n);
	}

	public void scrollFor(UiNode node) {
		UiNode target = node;
		UiNode parent = target.getParent();
		while (parent != null) {
			parent.scrollFor(target);
			target = parent;
			parent = target.getParent();
		}
	}

	@Override
	public int onKeyPress(UiNode target, int keyCode, int charCode, int mods, int time) {
		return EVENT_IGNORED;
	}

	@Override
	public int onKeyUp(UiNode target, int keyCode, int charCode, int mods, int time) {
		int result = EVENT_IGNORED;
		if (keyCode == KeyCodes.ENTER) {
			result |= clearClicking();
			//クリックエミュレーション。余計なお世話か？
			result |= target.onMouseClick(target, 0, 0, mods, time);
		}
		return result;
	}

	@Override
	public int onMouseMove(UiNode target, int x, int y, int mods, int time) {
		return EVENT_IGNORED;
	}

	@Override
	public int onMouseDown(UiNode target, int x, int y, int mods, int time) {
		int result = EVENT_IGNORED;
		result |= clearClicking() | setClicking(target);
		return result;
	}

	@Override
	public int onMouseUp(UiNode target, int x, int y, int mods, int time) {
		int result = EVENT_IGNORED;
		result |= clearClicking();
		return result;
	}

	@Override
	public int onMouseClick(UiNode target, int x, int y, int mods, int time) {
		int result = EVENT_IGNORED;
		if (target.isFocusable() && !target.isDeletedAll() && target.isVisibleAll() && target.isEnableAll()) {
			setFocus(target);
			result |= EVENT_AFFECTED;
		}
		return result;
	}

	@Override
	public int onMouseWheel(UiNode target, int x, int y, int dx, int dy, int mods, int time) {
		return EVENT_IGNORED;
	}

	@Override
	public void onResize(int screenWidth, int screenHeight) {
		Metrics met = Factory.getInstance(Metrics.class);
		met.setScreenSize(screenWidth, screenHeight);
	}

	@Override
	public int onImageLoaded(String url) {
		return EVENT_IGNORED;
	}

	@Override
	public int onDataSourceUpdated(DataSource ds) {
		return EVENT_IGNORED;
	}

	private int setClicking(UiNode node) {
		int result = EVENT_IGNORED;
		LivePage p = getLivePage();
		if (p.click == null && node != null && node.isFocusable()) {
			if (!node.isClicking()) {
				node.setClicking(true);
				result |= EVENT_AFFECTED;
			}
			p.click = node;
		}
		return result;
	}

	private int clearClicking() {
		int result = EVENT_IGNORED;
		LivePage p = getLivePage();
		Asserts.assume(p != null);
		if (p.click != null) {
			if (p.click.isClicking()) {
				p.click.setClicking(false);
				result |= EVENT_AFFECTED;
			}
			p.click = null;
		}
		return result;
	}

}
