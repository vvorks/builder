package com.github.vvorks.builder.client.common.ui;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
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
import com.github.vvorks.builder.client.common.net.JsonRpcClient;
import com.github.vvorks.builder.client.common.net.WebSocket;
import com.github.vvorks.builder.shared.common.lang.Asserts;
import com.github.vvorks.builder.shared.common.lang.Creator;
import com.github.vvorks.builder.shared.common.lang.Factory;
import com.github.vvorks.builder.shared.common.lang.RichIterable;
import com.github.vvorks.builder.shared.common.logging.Logger;
import com.github.vvorks.builder.shared.common.net.URLFragment;
import com.github.vvorks.builder.shared.common.util.DelayedExecuter;
import com.github.vvorks.builder.shared.common.util.JsonResourceBundle;

public class UiApplication implements EventHandler {

	private static final Logger LOGGER = Logger.createLogger(UiApplication.class);

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
		private UiNode click;
		private int xLast;
		private int yLast;
		public LivePage(UiPage page) {
			this.page = page;
			this.axis = new Point(0, 0);
			this.focus = null;
			this.click = null;
			this.xLast = 0;
			this.yLast = 0;
		}
	}

	/** ルートノード */
	private final UiRoot root;

	/** DOMドキュメント */
	private final DomDocument document;

	/** 登録ページマップ */
	private Map<String, Creator<UiPage>> pages;

	/**実行中ページスタック */
	private Deque<LivePage> pageStack;

	/** スタイルマップ */
	private Map<String, UiStyle> styles;

	/** リソース */
	private JsonResourceBundle resourceBundle;

	/** Webソケット */
	private final WebSocket socket;

	/** JsonRpcクライアント */
	private final JsonRpcClient rpcClient;

	/** データソースマップ */
	private final Map<DataSource, Set<UiNode>> dataSourceMap;

	/** キーダウン中セット（リピートフラグ算出用） */
	private final BitSet keyDowns;

	/** ビジーフラグ */
	private boolean busy;

	private boolean loaded;

	private UiNode capture;

	public UiApplication(DomDocument doc) {
		this.document = doc;
		this.root = new UiRoot(document);
		this.pages = new LinkedHashMap<>();
		this.pageStack = new ArrayDeque<>();
		this.styles = new LinkedHashMap<>();
		this.socket = Factory.newInstance(WebSocket.class);
		this.rpcClient = new JsonRpcClient(this.socket);
		this.dataSourceMap = new HashMap<>();
		this.keyDowns = new BitSet(KeyCodes.MAX_KEY_CODE);
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

	public JsonRpcClient getRpcClient() {
		return rpcClient;
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
		for (UiStyle s : styles.values()) {
			s.toCssStyle(cssMap);
		}
		document.injectStyleSheet(getClass(), cssMap);
	}

	public String getLocale() {
		return document.getLocale();
	}

	public void setLocale(String newLocale) {
		document.setLocale(newLocale);
	}

	public JsonResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public void setResourceBundle(JsonResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}


	protected void addPage(String tag, Creator<UiPage> creator) {
		pages.put(tag, creator);
	}

	protected void removePage(String tag) {
		pages.remove(tag);
	}

	public void transit(String tag, Map<String, String> params) {
		document.changePage(new URLFragment(tag, params).encode());
	}

	public void load(String tag, Map<String, String> params) {
		while (!pageStack.isEmpty()) {
			LivePage p = pageStack.pop();
			root.deleteChild(p.page);
		}
		keyDowns.clear();
		try {
			Creator<UiPage> creator = pages.get(tag);
			if (creator == null) {
				LOGGER.warn("%s NOT FOUND, call default", tag);
				creator = pages.get("");
			}
			call(creator.create(params));
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
			setFocus(getFirstFocusable(p.page), AXIS_XY);
		}
		keyDowns.clear();
	}

	public UiNode getFirstFocusable(UiNode owner) {
		return RichIterable
			.from(owner.getFocusCandidates())
			.filter(c -> c.getBlocker() == null)
			.first(null);
	}

	public void back() {
		LivePage p = getLivePage();
		p.page.onUnmount();
		pageStack.pop();
		root.deleteChild(p.page);
		keyDowns.clear();
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
		return null;
	}

	public UiNode getCapture() {
		return capture;
	}

	public UiNode setCapture(UiNode newCaptureNode) {
		Asserts.require(newCaptureNode != null);
		UiNode oldCaptureNode = capture;
		if (oldCaptureNode == null) {
			capture = newCaptureNode;
			getDocument().setCapture(true);
		} else if (oldCaptureNode != newCaptureNode) {
			LOGGER.warn("setCapture changed(%s -> %s)",
					oldCaptureNode.getDisplayName(),
					newCaptureNode.getDisplayName());
			capture = newCaptureNode;
		} else {
			LOGGER.warn("setCapture again");
		}
		return oldCaptureNode;
	}

	public UiNode releaseCapture(UiNode relCaptureNode) {
		Asserts.require(relCaptureNode != null);
		UiNode oldCaptureNode = capture;
		if (oldCaptureNode == null) {
			LOGGER.warn("releaseCapture nop");
		} else if (oldCaptureNode != relCaptureNode) {
			LOGGER.warn("releaseCapture ignored");
		} else {
			capture = null;
			getDocument().setCapture(false);
		}
		return oldCaptureNode;
	}

	public UiNode getFocus(UiNode node) {
		Asserts.requireNotNull(node);
		UiPage page = node.getPage();
		Asserts.requireNotNull(page);
		LivePage p = getLivePageOf(page);
		return p != null ? p.focus : null;
	}

	public UiNode setFocus(UiNode newFocusNode) {
		return setFocus(newFocusNode, AXIS_XY);
	}

	public UiNode setFocus(UiNode newFocusNode, int axis) {
		Asserts.requireNotNull(newFocusNode);
		UiPage page = newFocusNode.getPage();
		Asserts.requireNotNull(page);
		LivePage p = getLivePageOf(page);
		Asserts.requireNotNull(p);
		UiNode oldFocusNode = p.focus;
		if (!newFocusNode.isFocusable()) {
			newFocusNode = oldFocusNode;
		} else if (oldFocusNode != newFocusNode) {
			notifyFocus(oldFocusNode, newFocusNode);
			p.focus = newFocusNode;
			adjustAxis(p, axis);
		}
		return newFocusNode;
	}

	private static final Point DUMMY_POINT = new Point();

	public Point getAxis(UiNode node) {
		Asserts.requireNotNull(node);
		UiPage page = node.getPage();
		Asserts.requireNotNull(page);
		LivePage p = getLivePageOf(page);
		if (p == null) {
			return DUMMY_POINT;
		}
		return new Point(p.axis);
	}

	public void adjustAxis(UiNode node, int axis) {
		Asserts.requireNotNull(node);
		UiPage page = node.getPage();
		Asserts.requireNotNull(page);
		LivePage p = getLivePageOf(page);
		if (p != null) {
			adjustAxis(p, axis);
		}
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
		if (ds.isLoaded()) {
			DelayedExecuter.get().runLator(() -> processDataSourceUpdated(ds, 0));
		}
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

	private void notifyFocus(UiNode oldFocus, UiNode newFocus) {
		UiNode luca = (oldFocus != null && newFocus != null) ? newFocus.getLucaWith(oldFocus) : null;
		if (oldFocus != null) {
			UiNode node = oldFocus;
			do {
				node.onFocus(oldFocus, false, newFocus);
				node = node.getParent();
			} while (node != null && node != luca);
			DataRecord oldRecord = oldFocus.getDataRecord();
			if (oldRecord != null) {
				if (newFocus == null || newFocus.getDataRecord() != oldRecord) {
					oldRecord.flush();
				}
			}
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
		LOGGER.info("processInitialize(%d, %d)", screenWidth, screenHeight);
		Metrics met = Metrics.get();
		LOGGER.info("metrics em:%g ex:%g in:%g ", met.getEmSize(), met.getExSize(), met.getInSize());
		LOGGER.info("locale %s", document.getLocale());
		try {
			injectRegisteredStyles();
			this.onResize(screenWidth, screenHeight);
			root.onResize(screenWidth, screenHeight);
			refresh();
		} catch (RuntimeException|AssertionError err) {
			LOGGER.error(err);
			throw err;
		} finally {
			LOGGER.feed();
		}
	}

	public void processLoad(String tag, Map<String, String> params) {
		LOGGER.info("processLoad(%s, %s)", tag, params);
		try {
			while (!pageStack.isEmpty()) {
				LivePage p = pageStack.pop();
				root.deleteChild(p.page);
			}
			keyDowns.clear();
			Creator<UiPage> creator = pages.get(tag);
			if (creator == null) {
				LOGGER.warn("%s NOT FOUND, call default", tag);
				creator = pages.get("");
			}
			call(creator.create(params));
			refresh();
			loaded = true;
		} catch (RuntimeException|AssertionError err) {
			LOGGER.error(err);
			throw err;
		} finally {
			LOGGER.feed();
		}
	}

	public int processKeyDown(int keyCode, int charCode, int mods, int time) {
		LOGGER.info("processKeyDown(0x%x, 0x%x, 0x%x, %d)", keyCode, charCode, mods, time);
		try {
			//busyチェック
			if (busy) {
				LOGGER.warn("BUSY");
				return EVENT_CONSUMED;
			}
			LivePage page = getLivePage();
			//リピートフラグ処理
			if (keyDowns.get(keyCode)) {
				mods |= KeyCodes.MOD_REPEAT;
			}
			keyDowns.set(keyCode, true);
			//イベント配信処理
			UiNode target = page.focus;
			UiNode node = target;
			int result = node.onKeyDown(target, keyCode, charCode, mods, time);
			while ((result & EVENT_CONSUMED) == 0 && node.getParent() != null) {
				node = node.getParent();
				result |= node.onKeyDown(target, keyCode, charCode, mods, time);
			}
			if ((result & EVENT_CONSUMED) == 0) {
				//UiNode側の処理でフォーカスが変化している場合があるので、再取得
				UiNode focus = page.focus;
				result |= this.onKeyDown(focus, keyCode, charCode, mods, time);
			}
			if ((result & EVENT_AFFECTED) != 0) {
				refresh();
			}
			return result;
		} catch (RuntimeException|AssertionError err) {
			LOGGER.error(err);
			throw err;
		} finally {
			LOGGER.feed();
		}
	}

	public int processInput(String data, String content, int mods, int time) {
		LOGGER.info("processInput(%s, %s, 0x%x, %d)", data, content, mods, time);
		try {
			LivePage page = getLivePage();
			//イベント配信処理
			UiNode target = page.focus;
			UiNode node = target;
			int result = node.onInput(target, data, content, mods, time);
			while ((result & EVENT_CONSUMED) == 0 && node.getParent() != null) {
				node = node.getParent();
				result |= node.onInput(target, data, content, mods, time);
			}
			if ((result & EVENT_CONSUMED) == 0) {
				//UiNode側の処理でフォーカスが変化している場合があるので、再取得
				UiNode focus = page.focus;
				result |= this.onInput(focus, data, content, mods, time);
			}
			if ((result & EVENT_AFFECTED) != 0) {
				refresh();
			}
			return result;
		} catch (RuntimeException|AssertionError err) {
			LOGGER.error(err);
			throw err;
		} finally {
			LOGGER.feed();
		}
	}

	public int processKeyPress(int keyCode, int charCode, int mods, int time) {
		LOGGER.info("processKeyPress(0x%x, 0x%x, 0x%x, %d)", keyCode, charCode, mods, time);
		try {
			LivePage page = getLivePage();
			//イベント配信処理
			UiNode target = page.focus;
			UiNode node = target;
			int result = node.onKeyPress(target, keyCode, charCode, mods, time);
			while ((result & EVENT_CONSUMED) == 0 && node.getParent() != null) {
				node = node.getParent();
				result |= node.onKeyPress(target, keyCode, charCode, mods, time);
			}
			if ((result & EVENT_CONSUMED) == 0) {
				//UiNode側の処理でフォーカスが変化している場合があるので、再取得
				UiNode focus = page.focus;
				result |= this.onKeyPress(focus, keyCode, charCode, mods, time);
			}
			if ((result & EVENT_AFFECTED) != 0) {
				refresh();
			}
			return result;
		} catch (RuntimeException|AssertionError err) {
			LOGGER.error(err);
			throw err;
		} finally {
			LOGGER.feed();
		}
	}

	public int processKeyUp(int keyCode, int charCode, int mods, int time) {
		LOGGER.info("processKeyUp(0x%x, 0x%x, 0x%x, %d)", keyCode, charCode, mods, time);
		try {
			LivePage page = getLivePage();
			//リピートフラグ処理
			if (!keyDowns.get(keyCode)) {
				LOGGER.warn("INVALID");
				return EVENT_CONSUMED;
			}
			keyDowns.set(keyCode, false);
			//イベント配信処理
			UiNode target = page.focus;
			UiNode node = target;
			int result = node.onKeyUp(target, keyCode, charCode, mods, time);
			while ((result & EVENT_CONSUMED) == 0 && node.getParent() != null) {
				node = node.getParent();
				result |= node.onKeyUp(target, keyCode, charCode, mods, time);
			}
			if ((result & EVENT_CONSUMED) == 0) {
				//UiNode側の処理でフォーカスが変化している場合があるので、再取得
				UiNode focus = page.focus;
				result |= this.onKeyUp(focus, keyCode, charCode, mods, time);
			}
			if ((result & EVENT_AFFECTED) != 0) {
				refresh();
			}
			return result;
		} catch (RuntimeException|AssertionError err) {
			LOGGER.error(err);
			throw err;
		} finally {
			LOGGER.feed();
		}
	}

	public int processMouseMove(int x, int y, int mods, int time) {
		try {
			//busyチェック
			if (busy) {
				LOGGER.warn("BUSY");
				return EVENT_CONSUMED;
			}
			//同値チェック
			Point pt = new Point(x, y);
			UiNode target = getMouseTarget(pt);
			UiPage page = target.getPage();
			Asserts.requireNotNull(page);
			LivePage p = getLivePageOf(page);
			if (x == p.xLast && y == p.yLast) {
				return EVENT_CONSUMED;
			}
			p.xLast = x;
			p.yLast = y;
			//イベント配信処理
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
		} catch (RuntimeException|AssertionError err) {
			LOGGER.error(err);
			throw err;
		} finally {
			LOGGER.feed();
		}
	}

	public int processMouseDown(int x, int y, int mods, int time) {
		LOGGER.info("processMouseDown(%d, %d, 0x%x, %d)", x, y, mods, time);
		try {
			//busyチェック
			if (busy) {
				LOGGER.warn("BUSY");
				return EVENT_CONSUMED;
			}
			//イベント配信処理
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
		} catch (RuntimeException|AssertionError err) {
			LOGGER.error(err);
			throw err;
		} finally {
			LOGGER.feed();
		}
	}

	public int processMouseUp(int x, int y, int mods, int time) {
		LOGGER.info("processMouseUp(%d, %d, 0x%x, %d)", x, y, mods, time);
		try {
			//イベント配信処理
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
		} catch (RuntimeException|AssertionError err) {
			LOGGER.error(err);
			throw err;
		} finally {
			LOGGER.feed();
		}
	}

	public int processMouseClick(int x, int y, int mods, int time) {
		LOGGER.info("processMouseClick(%d, %d, 0x%x, %d)", x, y, mods, time);
		try {
			//イベント配信処理
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
		} catch (RuntimeException|AssertionError err) {
			LOGGER.error(err);
			throw err;
		} finally {
			LOGGER.feed();
		}
	}

	public int processMouseWheel(int x, int y, int dx, int dy, int mods, int time) {
		LOGGER.info("processMouseWheel(%d, %d, %d, %d, 0x%x, %d)", x, y, dx, dy, mods, time);
		try {
			//イベント配信処理
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
		} catch (RuntimeException|AssertionError err) {
			LOGGER.error(err);
			throw err;
		} finally {
			LOGGER.feed();
		}
	}

	public int processResize(int screenWidth, int screenHeight, int time) {
		LOGGER.info("processResize(%d, %d, %d)", screenWidth, screenHeight, time);
		try {
			if (loaded) {
				this.onResize(screenWidth, screenHeight);
				root.onResize(screenWidth, screenHeight);
				refresh();
			}
			return UiNode.EVENT_EATEN;
		} catch (RuntimeException|AssertionError err) {
			LOGGER.error(err);
			throw err;
		} finally {
			LOGGER.feed();
		}
	}

	public int processResourceChanged() {
		LOGGER.info("processResourceChanged()");
		try {
			if (loaded) {
				this.onResourceChanged();
				root.onResourceChanged();
				refresh();
			}
			return UiNode.EVENT_EATEN;
		} catch (RuntimeException|AssertionError err) {
			LOGGER.error(err);
			throw err;
		} finally {
			LOGGER.feed();
		}
	}

	public int processAnimationFrame(int time) {
		try {
			busy = false;
			return UiNode.EVENT_EATEN;
		} catch (RuntimeException|AssertionError err) {
			LOGGER.error(err);
			throw err;
		}
	}

	public int processImageLoaded(String url, int time) {
		LOGGER.info("processImageLoaded(%s, %d)", url, time);
		try {
			int result = EVENT_IGNORED;
			result |= root.onImageLoaded(url);
			result |= this.onImageLoaded(url);
			if ((result & EVENT_AFFECTED) != 0) {
				refresh();
			}
			return result;
		} catch (RuntimeException|AssertionError err) {
			LOGGER.error(err);
			throw err;
		} finally {
			LOGGER.feed();
		}
	}

	public int processDataSourceUpdated(DataSource ds, int time) {
		try {
			int result = EVENT_IGNORED;
			for (UiNode node : getAttachedNodes(ds)) {
				result |= node.onDataSourceUpdated(ds);
			}
			result |= this.onDataSourceUpdated(ds);
			if ((result & EVENT_AFFECTED) != 0) {
				refresh();
			}
			return result;
		} catch (RuntimeException|AssertionError err) {
			LOGGER.error(err);
			throw err;
		} finally {
			LOGGER.feed();
		}
	}

	private UiNode getMouseTarget(Point pt) {
		LivePage p = getLivePage(null);
		UiNode node;
		if (p == null) {
			node = root;
		} else if (capture == null) {
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
			node = capture;
			while (node != null) {
				node.translate(pt, +1);
				node = node.getParent();
			}
			node = capture;
		}
		return node;
	}

	private void refresh() {
		root.sync();
		busy = true;
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
				if ((mods & KeyCodes.MOD_ACS) == KeyCodes.MOD_CS) {
					String locale = getLocale();
					List<String> langs = Arrays.asList(Metrics.get().getLanguages());
					int index = langs.indexOf(locale);
					if (index == -1) {
						index = 0;
					} else {
						index = (index + 1) % langs.size();
					}
					setLocale(langs.get(index));
					result = EVENT_EATEN;
				}
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
			parent.scrollFor(node);
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
			//クリックエミュレーション。TODO 余計なお世話か？
			result |= target.onMouseClick(target, 0, 0, mods, time);
		}
		return result;
	}

	@Override
	public int onInput(UiNode target, String data, String content, int mods, int time) {
		return EVENT_IGNORED;
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
		Metrics.get().setScreenSize(screenWidth, screenHeight);
	}

	@Override
	public int onImageLoaded(String url) {
		return EVENT_IGNORED;
	}

	@Override
	public int onDataSourceUpdated(DataSource ds) {
		return EVENT_IGNORED;
	}

	@Override
	public void onResourceChanged() {
		//NOP
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
