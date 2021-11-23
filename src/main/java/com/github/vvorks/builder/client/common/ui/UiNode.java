package com.github.vvorks.builder.client.common.ui;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.json.Jsonizable;
import com.github.vvorks.builder.common.lang.Asserts;
import com.github.vvorks.builder.common.lang.Copyable;
import com.github.vvorks.builder.common.lang.Iterables;
import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.common.logging.Logger;

public class UiNode implements Copyable<UiNode>, EventHandler, Jsonizable {

	public static final Class<?> THIS = UiNode.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	public static final String NS_HTML = "http://www.w3.org/1999/xhtml";

	public static final String NS_MATHML = "http://www.w3.org/1998/Math/MathML";

	public static final String NS_SVG = "http://www.w3.org/2000/svg";

	public static final String NS_CANVAS = "http://github.com/vvorks/2021/canvas";

	public static final String HTML_DIV = "div";

	public static final String HTML_CANVAS = "canvas";

	/** イベント結果フラグ： イベントは無視された */
	public static final int EVENT_IGNORED = 0x00;

	/** イベント結果フラグ： イベントは消費された */
	public static final int EVENT_CONSUMED = 0x01;

	/** イベント結果フラグ： イベントによりUi要素の状態が変化した */
	public static final int EVENT_AFFECTED = 0x02;

	/** イベント結果フラグ： イベントは消費され、かつUi要素の状態が変化した */
	public static final int EVENT_EATEN = EVENT_CONSUMED | EVENT_AFFECTED;

	/** フォーカス可能フラグ */
	protected static final int FLAGS_FOCUSABLE		= 0x00000001;

	/** 有効フラグ */
	protected static final int FLAGS_ENABLE			= 0x00000002;

	/** 表示フラグ */
	protected static final int FLAGS_VISIBLE		= 0x00000004;

	/** 編集可能フラグ */
	protected static final int FLAGS_EDITABLE		= 0x00000008;

	/** クリック中フラグ */
	protected static final int FLAGS_CLICKING		= 0x00000010;

	/** イベントフックフラグ */
	protected static final int FLAGS_HOOK_EVENTS	= 0x00000020;

	/** 削除済みフラグ */
	protected static final int FLAGS_DELETED		= 0x00000040;

	/** 初期フラグ値 */
	protected static final int FLAGS_INITIAL		= FLAGS_ENABLE|FLAGS_VISIBLE;

	/** 内容更新フラグ */
	protected static final int CHANGED_CONTENT		= 0x00000001;

	/** 位置更新フラグ */
	protected static final int CHANGED_LOCATION		= 0x00000002;

	/** 表示属性更新フラグ */
	protected static final int CHANGED_DISPLAY		= 0x00000004;

	/** 階層更新フラグ */
	protected static final int CHANGED_HIERARCHY	= 0x00000008;

	/** 全更新フラグ */
	protected static final int CHANGED_ALL			=
			CHANGED_CONTENT|CHANGED_LOCATION|CHANGED_DISPLAY|CHANGED_HIERARCHY;

	/** 祖先イテレータ */
	private static class AncestorIterator implements Iterator<UiNode> {

		private UiNode now;

		public AncestorIterator(UiNode from) {
			this.now = from.parent;
		}

		@Override
		public boolean hasNext() {
			return now != null;
		}

		@Override
		public UiNode next() {
			if (now == null) {
				throw new NoSuchElementException();
			}
			UiNode result = now;
			now = now.parent;
			return result;
		}

	}

	/** 子孫イテレータ */
	private static class DescendantIterator implements Iterator<UiNode> {

		private UiNode top;

		private UiNode now;

		private final Predicate<UiNode> visitChild;

		public DescendantIterator(UiNode top, Predicate<UiNode> visitChild) {
			this.top = top;
			this.now = top.firstChild;
			this.visitChild = visitChild;
		}

		@Override
		public boolean hasNext() {
			return now != null;
		}

		@Override
		public UiNode next() {
			if (now == null) {
				throw new NoSuchElementException();
			}
			UiNode result = now;
			if (visitChild.test(now) && now.getFirstChild() != null) {
				now = now.getFirstChild();
			} else {
				while (now != top && now.getNextSibling() == null) {
					now = now.getParent();
				}
				now = (now != top) ? now.getNextSibling() : null;
			}
			return result;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	/** 最後に生成された生成番号 */
	private static int lastNo;

	/** 属性フラグ */
	private int flags;

	/** 変更フラグ */
	private int changed;

	/** 生成番号 */
	private final int no;

	/** 名称 */
	private final String name;

	/** 親ノード */
	private UiNode parent;

	/** 子ノード */
	private UiNode firstChild;

	/** 弟ノード */
	private UiNode nextSibling;

	/** 描画対象DOM ELEMENT */
	private DomElement domElement;

	/** スタイル管理マップ */
	private Map<String, UiStyle> registerdStyles;

	/**	親座標系における左位置 */
	private Length left;

	/**	親座標系における上位置 */
	private Length top;

	/**	親座標系における右位置 */
	private Length right;

	/**	親座標系における下位置 */
	private Length bottom;

	/** （表示上の）幅 */
	private Length width;

	/** （表示上の）高さ */
	private Length height;

	/**	論理表示開始左位置 */
	private Length scrollLeft;

	/**	論理表示開始上位置 */
	private Length scrollTop;

	/**	論理幅 */
	private Length scrollWidth;

	/**	論理高 */
	private Length scrollHeight;

	/** スタイル */
	private UiStyle style;

	private int parentWidthPxCache;

	private int parentHeightPxCache;

	public UiNode() {
		this((String)null);
	}

	public UiNode(String name) {
		this.no = takeNumber();
		this.name = name;
		this.flags = FLAGS_INITIAL;
		this.changed = CHANGED_ALL;
		this.registerdStyles = new LinkedHashMap<>();
	}

	protected UiNode(UiNode src) {
		//新規作成する値
		this.no = takeNumber();
		//immutable又はprimitiveな値
		this.name = src.name;
		this.flags = src.flags;
		this.changed = src.changed;
		this.registerdStyles = new LinkedHashMap<>(src.registerdStyles);
		this.left = src.left;
		this.top = src.top;
		this.right = src.right;
		this.bottom = src.bottom;
		this.width = src.width;
		this.height = src.height;
		this.scrollWidth = src.scrollWidth;
		this.scrollHeight = src.scrollHeight;
		this.scrollLeft = src.scrollLeft;
		this.scrollTop = src.scrollTop;
		this.style = src.style;
		//子孫をコピー
		UiNode child = src.getFirstChild();
		while (child != null) {
			this.appendChild(child.copy());
			child = child.getNextSibling();
		}
	}

	public UiNode copy() {
		return new UiNode(this);
	}

	private static synchronized int takeNumber() {
		++lastNo;
		return lastNo;
	}

	public String getName() {
		if (Strings.isEmpty(name)) {
			return getClass().getSimpleName().replace('$', '_') + no;
		}
		return name;
	}

	public String getFullName() {
		return getQualifiedName(null);
	}

	public String getQualifiedName(UiNode root) {
		List<String> list = new ArrayList<>();
		UiNode node = this;
		while (node != null && node != root) {
			list.add(node.getName());
			node = node.parent;
		}
		Collections.reverse(list);
		StringBuilder sb = new StringBuilder();
		for (String s : list) {
			sb.append("_").append(s);
		}
		return sb.substring(1);
	}

	public boolean isFocusable() {
		return getFlags(FLAGS_FOCUSABLE);
	}

	public void setFocusable(boolean focusable) {
		setFlags(FLAGS_FOCUSABLE, focusable, CHANGED_DISPLAY);
	}

	public boolean isEnable() {
		return getFlags(FLAGS_ENABLE);
	}

	public boolean isEnableAll() {
		return isEnable() && (parent == null || parent.isEnableAll());
	}

	public void setEnable(boolean enable) {
		setFlags(FLAGS_ENABLE, enable, CHANGED_DISPLAY);
	}

	public boolean isVisible() {
		return getFlags(FLAGS_VISIBLE);
	}

	public boolean isVisibleAll() {
		return isVisible() && (parent == null || parent.isVisibleAll());
	}

	public void setVisible(boolean visible) {
		setFlags(FLAGS_VISIBLE, visible, CHANGED_DISPLAY);
	}

	public boolean isClicking() {
		return getFlags(FLAGS_CLICKING);
	}

	public void setClicking(boolean clicked) {
		setFlags(FLAGS_CLICKING, clicked, CHANGED_DISPLAY);
	}

	public boolean isHookEvents() {
		return getFlags(FLAGS_HOOK_EVENTS);
	}

	public void setHookEvents(boolean uses) {
		setFlags(FLAGS_HOOK_EVENTS, uses, 0);
	}

	public boolean isEditable() {
		return getFlags(FLAGS_EDITABLE);
	}

	public void setEditable(boolean editable) {
		setFlags(FLAGS_EDITABLE, editable, CHANGED_DISPLAY);
	}

	public void setCapture() {
		getApplication().setCapture(this);
	}

	public void releaseCapture() {
		getApplication().releaseCapture();
	}

	/**
	 * 論理削除（実削除は同期時に行う）
	 *
	 * @param child
	 * 		削除対象子ノード
	 */
	public void deleteChild(UiNode child) {
		child.setFlags(FLAGS_DELETED, true, CHANGED_DISPLAY);
		setChanged(CHANGED_HIERARCHY);
	}

	public boolean isDeleted() {
		return getFlags(FLAGS_DELETED);
	}

	public boolean isDeletedAll() {
		return isDeleted() && (parent == null || parent.isDeletedAll());
	}

	protected boolean getFlags(int mask) {
		return (flags & mask) != 0;
	}

	protected void setFlags(int mask, boolean on, int changedMask) {
		if (on) {
			if ((flags & mask) != mask) {
				flags |= mask;
				setChanged(changedMask);
			}
		} else {
			if ((flags & mask) != 0) {
				flags &= ~mask;
				setChanged(changedMask);
			}
		}
	}

	public boolean isChanged() {
		return isChanged(CHANGED_ALL);
	}

	protected boolean isChanged(int mask) {
		return (changed & mask) != 0;
	}

	protected void setChanged(int mask) {
		changed |= mask;
	}

	protected void clearChanged() {
		changed = 0;
	}

	public DomElement getDomElement() {
		return domElement;
	}

	protected void setDomElement(DomElement element) {
		this.domElement = element;
	}

	protected DomElement createDomElement(String ns, String tag, UiNode owner) {
		if (parent == null) {
			return getDocument().createElement(ns, tag, owner);
		}
		return parent.createDomElement(ns, tag, owner);
	}

	protected void ensureDomElement() {
		if (domElement == null) {
			setDomElement(createDomElement(NS_HTML, HTML_DIV, this));
		}
	}

	public void registerStyle(UiStyle style) {
		registerdStyles.put(style.getName(), style);
	}

	public void unregisterStyle(UiStyle style) {
		registerdStyles.remove(style.getName());
	}

	public UiStyle getRegisteredStyle(String name) {
		return registerdStyles.get(name);
	}

	protected Iterable<UiStyle> getRegisteredStyles() {
		return registerdStyles.values();
	}

	public UiNode getParent() {
		return parent;
	}

	/** 親ノード設定： 全体の整合性を保つよう注意して使用する事 */
	protected void setParent(UiNode parent) {
		this.parent = parent;
	}

	public UiNode getFirstChild() {
		return firstChild;
	}

	/**
	 * 指定した子ノードの直前の子ノードを返す
	 *
	 * @param younger
	 * 		対象子ノード
	 * @return
	 * 		youngerの直前の子ノード。youngerが存在しなかった場合、末子。
	 */
	public UiNode getElderBrother(UiNode younger) {
		UiNode node = firstChild;
		UiNode prev = null;
		while (node != null && node != younger) {
			prev = node;
			node = node.nextSibling;
		}
		return prev;
	}

	public Iterable<UiNode> getAncestors() {
		return () -> new AncestorIterator(this);
	}

	public Iterable<UiNode> getAncestorsIf(Predicate<UiNode> condition) {
		return Iterables.filter(getAncestors(), condition);
	}

	public Iterable<UiNode> getDescendants() {
		return () -> new DescendantIterator(this, node -> true);
	}

	public Iterable<UiNode> getDescendantsIf(Predicate<UiNode> condition) {
		return Iterables.filter(getDescendants(), condition);
	}

	public Iterable<UiNode> getFocusCandidates() {
		return getFocusCandidates(null);
	}

	public Iterable<UiNode> getFocusCandidates(UiNode except) {
		if (!(!isDeletedAll() && isVisibleAll() && isEnableAll())) {
			return Collections.emptyList();
		}
		return Iterables.filter(getEnableDescendants(), d -> d != except && d.isFocusable());
	}

	protected Iterable<UiNode> getEnableDescendants() {
		return () -> new DescendantIterator(this, d -> !d.isDeleted() && d.isVisible() && d.isEnable());
	}

	public Iterable<UiNode> getChildren() {
		return () -> new DescendantIterator(this, d -> false);
	}

	public Iterable<UiNode> getChildrenIf(Predicate<UiNode> condition) {
		return Iterables.filter(getChildren(), node -> condition.test(node));
	}

	public UiNode getVisibleChildAt(int x, int y) {
		return Iterables.getLast(
				getChildrenIf(c ->
					!c.isDeleted() &&
					c.isVisible()  &&
					c.contains(x - c.getLeftPx(), y - c.getTopPx())),
				null);
	}

	private boolean contains(int x, int y) {
		return	0 <= x && x < getWidthPx() && 0 <= y && y < getHeightPx() && isHit(x, y);
	}

	public boolean isHit(int x, int y) {
		return true;
	}

	public boolean isAncestor(UiNode other) {
		if (other == null) {
			return false;
		}
		Set<UiNode> set = new HashSet<>();
		Iterables.addAll(set, other.getAncestors());
		return set.contains(this);
	}

	/**
	 * 最終共通祖先(Last Universal Common Ancestor)を取得する。
	 */
	public UiNode getLucaWith(UiNode other) {
		if (other == null) {
			return null;
		}
		//祖先集合を作成する。
		Set<UiNode> set = new HashSet<>();
		set.add(this);
		Iterables.addAll(set, getAncestors());
		//相手を祖先集合から検索
		if (set.contains(other)) {
			return other;
		}
		//相手の祖先を祖先集合から検索
		for (UiNode a : other.getAncestors()) {
			if (set.contains(a)) {
				return a;
			}
		}
		return null;
	}

	/**
	 * 指定された祖先との世代数を取得する
	 *
	 * @param ancestor
	 * 		祖先ノード
	 * @return
	 * 		祖先ノードとの世代数。自身の場合０、親は１、祖父母は２・・・。ancestorが祖先ではなかった場合、-1
	 */
	public int getDegree(UiNode ancestor) {
		List<UiNode> list = new ArrayList<>();
		list.add(this);
		Iterables.addAll(list, getAncestors());
		return list.indexOf(ancestor);
	}

	public UiNode insertChild(UiNode newChild) {
		Asserts.require(newChild != null);
		if (newChild.parent == this) {
			return newChild;
		}
		if (newChild.parent != null) {
			newChild.parent.removeChild(newChild);
		}
		UiNode younger = this.firstChild;
		this.firstChild = newChild;
		newChild.nextSibling = younger;
		newChild.parent = this;
		this.setChanged(CHANGED_HIERARCHY);
		return newChild;
	}

	public UiNode appendChild(UiNode newChild) {
		return insertBefore(newChild, null);
	}

	public UiNode insertBefore(UiNode newChild, UiNode refChild) {
		Asserts.require(newChild != null);
		if (newChild.parent == this) {
			return newChild;
		}
		if (newChild.parent != null) {
			newChild.parent.removeChild(newChild);
		}
		UiNode elder = getElderBrother(refChild);
		if (elder == null) {
			UiNode younger = this.firstChild;
			this.firstChild = newChild;
			newChild.nextSibling = younger;
		} else {
			UiNode younger = elder.nextSibling;
			elder.nextSibling = newChild;
			newChild.nextSibling = younger;
		}
		newChild.parent = this;
		this.setChanged(CHANGED_HIERARCHY);
		return newChild;
	}

	public UiNode removeChild(UiNode oldChild) {
		Asserts.require(oldChild != null);
		Asserts.require(this == oldChild.parent);
		UiNode node = firstChild;
		UiNode prev = null;
		while (node != null && node != oldChild) {
			prev = node;
			node = node.nextSibling;
		}
		Asserts.check(node != null);
		if (prev == null) {
			this.firstChild = oldChild.nextSibling;
		} else {
			prev.nextSibling = oldChild.nextSibling;
		}
		oldChild.parent = null;
		oldChild.nextSibling = null;
		this.setChanged(CHANGED_HIERARCHY);
		return oldChild;
	}

	public UiNode removeFirstChild() {
		Asserts.requireNotNull(firstChild);
		UiNode oldChild = firstChild;
		this.firstChild = oldChild.nextSibling;
		oldChild.parent = null;
		oldChild.nextSibling = null;
		this.setChanged(CHANGED_HIERARCHY);
		return oldChild;
	}

	public UiNode removeLastChild() {
		Asserts.requireNotNull(firstChild);
		UiNode prev = null;
		UiNode last = firstChild;
		for (UiNode node = last; node != null; node = node.nextSibling) {
			prev = last;
			last = node;
		}
		if (prev == null) {
			this.firstChild = null;
		} else {
			prev.nextSibling = null;
		}
		last.parent = null;
		this.setChanged(CHANGED_HIERARCHY);
		return last;
	}

	public UiNode clearChildren() {
		UiNode children = firstChild;
		children.parent = null;
		firstChild = null;
		this.setChanged(CHANGED_HIERARCHY);
		return children;
	}

	public UiNode setChildren(UiNode children) {
		Asserts.assume(firstChild == null);
		firstChild = children;
		children.parent = this;
		this.setChanged(CHANGED_HIERARCHY);
		return children;
	}

	public Iterable<UiNode> removeChildrenIf(Predicate<UiNode> condition) {
		List<UiNode> removes = new ArrayList<>();
		UiNode child = firstChild;
		UiNode prev = null;
		while (child != null) {
			if (condition.test(child)) {
				UiNode removed = child;
				if (prev == null) {
					this.firstChild = removed.nextSibling;
				} else {
					prev.nextSibling = removed.nextSibling;
				}
				child = removed.nextSibling;
				removed.parent = null;
				removed.nextSibling = null;
				removes.add(removed);
			} else {
				prev = child;
				child = child.nextSibling;
			}
		}
		return removes;
	}

	public UiNode getNextSibling() {
		return nextSibling;
	}

	public Length getLeft() {
		return left;
	}

	public int getLeftPx() {
		int result;
		if (left != null) {
			result = left.px(() -> getParentWidthPx());
		} else {
			Asserts.assume(width != null);
			final int parentWidthPx = getParentWidthPx();
			int widthPx = width.px(() -> parentWidthPx);
			int rightPx;
			if (right != null) {
				rightPx = right.px(() -> parentWidthPx);
			} else {
				rightPx = (parentWidthPx - widthPx + 1) / 2;
			}
			result = parentWidthPx - rightPx - widthPx;
		}
		return result;
	}

	public void setLeft(String value) {
		setLeft(value == null ? null : new Length(value));
	}

	public void setLeft(int newValue) {
		setLeft(new Length(newValue));
	}

	public void setLeft(Length newValue) {
		if (!Objects.equals(left, newValue)) {
			left = newValue;
			setChanged(CHANGED_LOCATION);
			if (parent != null) {
				parent.setChanged(CHANGED_CONTENT);
			}
		}
	}

	public Length getTop() {
		return top;
	}

	public int getTopPx() {
		int result;
		if (top != null) {
			result = top.px(() -> getParentHeightPx());
		} else {
			Asserts.assume(height != null);
			final int parentHeightPx = getParentHeightPx();
			int heightPx = height.px(() -> parentHeightPx);
			int bottomPx;
			if (bottom != null) {
				bottomPx = bottom.px(() -> parentHeightPx);
			} else {
				bottomPx = (parentHeightPx - heightPx + 1) / 2;
			}
			result = parentHeightPx - bottomPx - heightPx;
		}
		return result;
	}

	public void setTop(String value) {
		setTop(value == null ? null : new Length(value));
	}

	public void setTop(int newValue) {
		setTop(new Length(newValue));
	}

	public void setTop(Length newValue) {
		if (!Objects.equals(top, newValue)) {
			top = newValue;
			setChanged(CHANGED_LOCATION);
			if (parent != null) {
				parent.setChanged(CHANGED_CONTENT);
			}
		}
	}

	public Length getRight() {
		return right;
	}

	public int getRightPx() {
		int result;
		if (right != null) {
			result = right.px(() -> getParentWidthPx());
		} else {
			Asserts.assume(width != null);
			final int parentWidthPx = getParentWidthPx();
			int widthPx = width.px(() -> parentWidthPx);
			int leftPx;
			if (left != null) {
				leftPx = left.px(() -> parentWidthPx);
			} else {
				leftPx = (parentWidthPx - widthPx) / 2;
			}
			result = parentWidthPx - leftPx - widthPx;
		}
		return result;
	}

	public void setRight(String value) {
		setRight(value == null ? null : new Length(value));
	}

	public void setRight(int newValue) {
		setRight(new Length(newValue));
	}

	public void setRight(Length newValue) {
		if (!Objects.equals(right, newValue)) {
			right = newValue;
			setChanged(CHANGED_LOCATION);
			if (parent != null) {
				parent.setChanged(CHANGED_CONTENT);
			}
		}
	}

	public Length getBottom() {
		return bottom;
	}

	public int getBottomPx() {
		int result;
		if (bottom != null) {
			result = bottom.px(() -> getParentHeightPx());
		} else {
			Asserts.assume(height != null);
			final int parentHeightPx = getParentHeightPx();
			int heightPx = height.px(() -> parentHeightPx);
			int topPx;
			if (top != null) {
				topPx = top.px(() -> parentHeightPx);
			} else {
				topPx = (parentHeightPx - heightPx) / 2;
			}
			result = parentHeightPx - topPx - heightPx;
		}
		return result;
	}

	public void setBottom(String value) {
		setBottom(value == null ? null : new Length(value));
	}

	public void setBottom(int newValue) {
		setBottom(new Length(newValue));
	}

	public void setBottom(Length newValue) {
		if (!Objects.equals(bottom, newValue)) {
			bottom = newValue;
			setChanged(CHANGED_LOCATION);
			if (parent != null) {
				parent.setChanged(CHANGED_CONTENT);
			}
		}
	}

	public Length getWidth() {
		return width;
	}

	public int getWidthPx() {
		int result;
		if (width != null) {
			result = width.px(() -> getParentWidthPx());
		} else {
			final int parentWidthPx = getParentWidthPx();
			int leftPx = left.px(() -> parentWidthPx);
			int rightPx = right.px(() -> parentWidthPx);
			result = parentWidthPx - leftPx - rightPx;
		}
		return result;
	}

	public int getWidthPx(final int parentWidthPx) {
		int result;
		if (width != null) {
			result = width.px(() -> parentWidthPx);
		} else {
			int leftPx = left.px(() -> parentWidthPx);
			int rightPx = right.px(() -> parentWidthPx);
			result = parentWidthPx - leftPx - rightPx;
		}
		return result;
	}

	public void setWidth(String value) {
		setWidth(value == null ? null : new Length(value));
	}

	public void setWidth(int newValue) {
		setWidth(new Length(newValue));
	}

	public void setWidth(Length newValue) {
		if (!Objects.equals(width, newValue)) {
			width = newValue;
			setChanged(CHANGED_LOCATION);
			if (parent != null) {
				parent.setChanged(CHANGED_CONTENT);
			}
			clearDescendantsCache();
		}
	}

	public Length getHeight() {
		return height;
	}

	public int getHeightPx() {
		int result;
		if (height != null) {
			result = height.px(() -> getParentHeightPx());
		} else {
			final int parentHeightPx = getParentHeightPx();
			int topPx = top.px(() -> parentHeightPx);
			int bottomPx = bottom.px(() -> parentHeightPx);
			result = parentHeightPx - topPx - bottomPx;
		}
		return result;
	}

	public int getHeightPx(final int parentHeightPx) {
		int result;
		if (height != null) {
			result = height.px(() -> parentHeightPx);
		} else {
			int topPx = top.px(() -> parentHeightPx);
			int bottomPx = bottom.px(() -> parentHeightPx);
			result = parentHeightPx - topPx - bottomPx;
		}
		return result;
	}

	public void setHeight(String value) {
		setHeight(value == null ? null : new Length(value));
	}

	public void setHeight(int newValue) {
		setHeight(new Length(newValue));
	}

	public void setHeight(Length newValue) {
		if (!Objects.equals(height, newValue)) {
			height = newValue;
			setChanged(CHANGED_LOCATION);
			if (parent != null) {
				parent.setChanged(CHANGED_CONTENT);
			}
			clearDescendantsCache();
		}
	}

	public Length getBorderLeft() {
		return (style != null) ? style.getAtomicStyleOf(this).getBorderLeft() : Length.ZERO;
	}

	public int getBorderLeftPx() {
		return getBorderLeft().px(() -> getParentWidthPx());
	}

	public Length getBorderTop() {
		return (style != null) ? style.getAtomicStyleOf(this).getBorderTop() : Length.ZERO;
	}

	public int getBorderTopPx() {
		return getBorderTop().px(() -> getParentHeightPx());
	}

	public Length getBorderRight() {
		return (style != null) ? style.getAtomicStyleOf(this).getBorderRight() : Length.ZERO;
	}

	public int getBorderRightPx() {
		return getBorderRight().px(() -> getParentWidthPx());
	}

	public Length getBorderBottom() {
		return (style != null) ? style.getAtomicStyleOf(this).getBorderBottom() : Length.ZERO;
	}

	public int getBorderBottomPx() {
		return getBorderBottom().px(() -> getParentHeightPx());
	}

	public Length getScrollLeft() {
		return scrollLeft;
	}

	public int getScrollLeftPx() {
		int result;
		if (scrollLeft != null) {
			result = scrollLeft.px(() -> getParentWidthPx());
		} else {
			result = 0;
		}
		return result;
	}

	public void setScrollLeft(String newValue) {
		Asserts.requireNotEmpty(newValue);
		setScrollLeft(new Length(newValue));
	}

	public void setScrollLeft(int newValue) {
		setScrollLeft(new Length(newValue));
	}

	public void setScrollLeft(Length newValue) {
		Asserts.requireNotNull(newValue);
		if (!Objects.equals(scrollLeft, newValue)) {
			scrollLeft = newValue;
			setChanged(CHANGED_LOCATION);
		}
	}

	public Length getScrollTop() {
		return scrollTop;
	}

	public int getScrollTopPx() {
		int result;
		if (scrollTop != null) {
			result = scrollTop.px(() -> getParentHeightPx());
		} else {
			result = 0;
		}
		return result;
	}

	public void setScrollTop(String newValue) {
		Asserts.requireNotEmpty(newValue);
		setScrollTop(new Length(newValue));
	}

	public void setScrollTop(int newValue) {
		setScrollTop(new Length(newValue));
	}

	public void setScrollTop(Length newValue) {
		Asserts.requireNotNull(newValue);
		if (!Objects.equals(scrollTop, newValue)) {
			scrollTop = newValue;
			setChanged(CHANGED_LOCATION);
		}
	}

	public Length getScrollWidth() {
		return scrollWidth;
	}

	public int getScrollWidthPx() {
		int result = getWidthPx() - getBorderLeftPx() - getBorderRightPx();
		if (scrollWidth != null) {
			result = Math.max(result, scrollWidth.px(() -> getParentWidthPx()));
		}
		return result;
	}

	public void setScrollWidth(String newValue) {
		Asserts.requireNotEmpty(newValue);
		setScrollWidth(new Length(newValue));
	}

	public void setScrollWidth(int newValue) {
		setScrollWidth(new Length(newValue));
	}

	public void setScrollWidth(Length newValue) {
		Asserts.requireNotNull(newValue);
		if (!Objects.equals(scrollWidth, newValue)) {
			scrollWidth = newValue;
			setChanged(CHANGED_LOCATION);
			clearDescendantsCache();
		}
	}

	public Length getScrollHeight() {
		return scrollHeight;
	}

	public int getScrollHeightPx() {
		int result = getHeightPx() - getBorderTopPx() - getBorderBottomPx();
		if (scrollHeight != null) {
			result = Math.max(result, scrollHeight.px(() -> getParentHeightPx()));
		}
		return result;
	}

	public void setScrollHeight(String newValue) {
		Asserts.requireNotEmpty(newValue);
		setScrollHeight(new Length(newValue));
	}

	public void setScrollHeight(int newValue) {
		setScrollHeight(new Length(newValue));
	}

	public void setScrollHeight(Length newValue) {
		Asserts.requireNotNull(newValue);
		if (!Objects.equals(scrollHeight, newValue)) {
			scrollHeight = newValue;
			setChanged(CHANGED_LOCATION);
			clearDescendantsCache();
		}
	}

	protected int getParentWidthPx() {
		if (parentWidthPxCache <= 0) {
			parentWidthPxCache = parent.getScrollWidthPx();
		}
		return parentWidthPxCache;
	}

	protected int getParentHeightPx() {
		if (parentHeightPxCache <= 0) {
			parentHeightPxCache = parent.getScrollHeightPx();
		}
		return parentHeightPxCache;
	}

	protected void clearDescendantsCache() {
		for (UiNode d : getDescendants()) {
			d.parentWidthPxCache = 0;
			d.parentHeightPxCache = 0;
		}
	}

	public Bounds getBounds() {
		return new Bounds(left, top, right, bottom , width, height);
	}

	public void setBounds(String left, String top, String right, String bottom, String width, String height) {
		setLeft(left);
		setTop(top);
		setRight(right);
		setBottom(bottom);
		setWidth(width);
		setHeight(height);
	}

	public void setBounds(Length left, Length top, Length right, Length bottom, Length width, Length height) {
		setLeft(left);
		setTop(top);
		setRight(right);
		setBottom(bottom);
		setWidth(width);
		setHeight(height);
	}

	public void setBounds(UiNode other) {
		setLeft(other.getLeft());
		setTop(other.getTop());
		setRight(other.getRight());
		setBottom(other.getBottom());
		setWidth(other.getWidth());
		setHeight(other.getHeight());
	}

	public void setBounds(Bounds bounds) {
		setLeft(bounds.getLeft());
		setTop(bounds.getTop());
		setRight(bounds.getRight());
		setBottom(bounds.getBottom());
		setWidth(bounds.getWidth());
		setHeight(bounds.getHeight());
	}

	/** 現在の設定状態（モード、単位）を維持しつつ位置を設定する */
	public void updateBounds(Rect r) {
		if (getLeft() != null) {
			int px = r.getLeft();
			setLeft(Length.fromPx(px, getLeft().getUnit(), () -> getParentWidthPx()));
		}
		if (getTop() != null) {
			int px = r.getTop();
			setTop(Length.fromPx(px, getTop().getUnit(), () -> getParentHeightPx()));
		}
		if (getRight() != null) {
			int px = getParentWidthPx() - r.getRight();
			setRight(Length.fromPx(px, getRight().getUnit(), () -> getParentWidthPx()));
		}
		if (getBottom() != null) {
			int px = getParentHeightPx() - r.getBottom();
			setBottom(Length.fromPx(px, getBottom().getUnit(), () -> getParentHeightPx()));
		}
		if (getWidth() != null) {
			int px = r.getWidth();
			setWidth(Length.fromPx(px, getWidth().getUnit(), () -> getParentWidthPx()));
		}
		if (getHeight() != null) {
			int px = r.getHeight();
			setHeight(Length.fromPx(px, getHeight().getUnit(), () -> getParentHeightPx()));
		}
	}

	protected UiNode getFocus() {
		return getApplication().getFocus();
	}

	protected UiNode setFocus(UiNode node) {
		return getApplication().setFocus(node);
	}

	protected boolean isFocus() {
		UiNode focus = getFocus();
		return this == focus;
	}

	protected boolean hasFocus() {
		UiNode focus = getFocus();
		return this == focus || this.isAncestor(focus);
	}

	/** このノードの表示上の矩形を親座標系で返す */
	public Rect getRectangleOnParent() {
		return new Rect(getLeftPx(), getTopPx(), getWidthPx(), getHeightPx());
	}

	/** このノードの表示上の矩形を自身の座標系で返す */
	public Rect getRectangleOnThis() {
		return new Rect(
				getScrollLeftPx(),
				getScrollTopPx(),
				getWidthPx() - getBorderLeftPx() - getBorderRightPx(),
				getHeightPx() - getBorderTopPx() - getBorderBottomPx());
	}

	public UiNode getBlocker() {
		UiNode anc = parent;
		Rect r = this.getRectangleOnParent();
		while (anc != null && anc.getRectangleOnThis().contains(r)) {
			anc.translate(r, -1);
			anc = anc.parent;
		}
		return anc;
	}

	public Rect getRectangleOn(UiNode ancestor) {
		Rect r = this.getRectangleOnParent();
		translateOn(r, ancestor);
		return r;
	}

	public void translateOn(Point pt, UiNode ancestor) {
		UiNode anc = parent;
		while (anc != null && anc != ancestor) {
			anc.translate(pt, -1);
			anc = anc.parent;
		}
	}

	/**
	 * 座標系変換
	 *
	 * @param pt
	 * 		位置
	 * @param sig
	 * 		-1:上位座標系への変換 +1:下位座標系への変換
	 */
	public void translate(Point pt, int sig) {
		pt.move(-sig * getLeftPx()      , -sig * getTopPx()      );
		pt.move(-sig * getBorderLeftPx(), -sig * getBorderTopPx());
		pt.move(+sig * getScrollLeftPx(), +sig * getScrollTopPx());
	}

	public UiStyle getStyle() {
		return style;
	}

	public UiStyle findRegisteredStyle(String styleName) {
		Asserts.requireNotNull(styleName);
		UiStyle result;
		//step1: 自分自身に登録されたスタイルを検索
		result = this.getRegisteredStyle(styleName);
		if (result != null) {
			return result;
		}
		//step2: このノードが属するページに登録されたスタイルを検索
		result = getPage().getRegisteredStyle(styleName);
		if (result != null) {
			return result;
		}
		//step3: アプリケーションに登録されたスタイルを検索
		result = getApplication().getRegisteredStyle(styleName);
		if (result != null) {
			return result;
		}
		//step4: どこにもないのでnullを返却
		return null;
	}

	public void setStyle(UiStyle newValue) {
		if (!Objects.equals(this.style, newValue)) {
			this.style = newValue;
			setChanged(CHANGED_DISPLAY);
		}
	}

	public void scrollFor(UiNode child) {
		//NOP
	}

	public void sync() {
		//実DOMの準備
		ensureDomElement();
		//内容の同期
		if (isChanged(CHANGED_CONTENT)) {
			syncContent();
		}
		//位置・表示条件の同期
		if (isChanged(CHANGED_LOCATION|CHANGED_DISPLAY)) {
			syncStyle();
			syncElementStyle();
			syncScroll();
		}
		//下位層の同期
		syncChildren();
		//階層接続
		if (isChanged(CHANGED_HIERARCHY)) {
			syncDomElement(domElement);
		}
		//最終処理
		syncFinally();
		clearChanged();
	}

	protected void syncDomElement(DomElement domElement) {
		//削除ノードの分離
		for (UiNode r : removeChildrenIf(e -> e.isDeleted())) {
			//削除マークのついた子ノードを取り外し、さらにDOM要素とも切断する。
			r.getDomElement().setParent(null);
			//再利用に備えDELETEフラグは落とす
			r.setFlags(FLAGS_DELETED, false, CHANGED_ALL);
		}
		//追加ノードの接続
		for (UiNode c : getChildren()) {
			//子ノードをこのノードのDOM要素と連結
			c.getDomElement().setParent(domElement);
		}
	}

	protected void syncContent() {
		//NOP
	}

	protected void syncStyle() {
		UiStyle s = getStyle();
		if (s != null) {
			UiAtomicStyle as = s.getAtomicStyleOf(this);
			domElement.setDefinedStyle(as);
		} else {
			domElement.setDefinedStyle(null);
		}
	}

	protected void syncElementStyle() {
		CssStyle.Builder b = new CssStyle.Builder();
		syncElementStyle(b);
		domElement.setLocalStyle(b.build());
		domElement.setBounds(getLeftPx(), getTopPx(), getWidthPx(), getHeightPx());
	}

	protected void syncElementStyle(CssStyle.Builder b) {
		b.property("position", "absolute");
		b.visible(isVisible());
		b.left(left).right(right).width(width);
		b.top(top).bottom(bottom).height(height);
	}

	protected void syncScroll() {
		int x = 0;
		int y = 0;
		int w = 0;
		int h = 0;
		if (scrollLeft != null || scrollTop != null) {
			x = getScrollLeftPx();
			y = getScrollTopPx();
		}
		if (scrollWidth != null || scrollHeight != null) {
			w = getScrollWidthPx();
			h = getScrollHeightPx();
		}
		domElement.setScrollBounds(x, y, w, h);
	}

	protected void syncFinally() {
		domElement.sync();
	}

	protected void syncChildren() {
		UiNode child = firstChild;
		while (child != null) {
			child.sync();
			child = child.getNextSibling();
		}
	}

	protected UiApplication getApplication() {
		return parent != null ? parent.getApplication() : null;
	}

	protected DomDocument getDocument() {
		return parent != null ? parent.getDocument() : null;
	}

	public UiPage getPage() {
		return parent != null ? parent.getPage() : null;
	}

	@Override
	public int onKeyDown(UiNode target, int keyCode, int charCode, int mods, int time) {
		return UiNode.EVENT_IGNORED;
	}

	@Override
	public int onKeyPress(UiNode target, int keyCode, int charCode, int mods, int time) {
		return UiNode.EVENT_IGNORED;
	}

	@Override
	public int onKeyUp(UiNode target, int keyCode, int charCode, int mods, int time) {
		return UiNode.EVENT_IGNORED;
	}

	@Override
	public int onMouseMove(UiNode target, int x, int y, int mods, int time) {
		return UiNode.EVENT_IGNORED;
	}

	@Override
	public int onMouseDown(UiNode target, int x, int y, int mods, int time) {
		return UiNode.EVENT_IGNORED;
	}

	@Override
	public int onMouseUp(UiNode target, int x, int y, int mods, int time) {
		return UiNode.EVENT_IGNORED;
	}

	@Override
	public int onMouseClick(UiNode target, int x, int y, int mods, int time) {
		return UiNode.EVENT_IGNORED;
	}

	@Override
	public int onMouseWheel(UiNode target, int x, int y, int dx, int dy, int mods, int time) {
		return UiNode.EVENT_IGNORED;
	}

	@Override
	public void onResize(int screenWidth, int screenHeight) {
		parentWidthPxCache = 0;
		parentHeightPxCache = 0;
		setChanged(CHANGED_LOCATION);
		UiNode child = firstChild;
		while (child != null) {
			child.onResize(screenWidth, screenHeight);
			child = child.getNextSibling();
		}
	}

	@Override
	public int onImageLoaded(String url) {
		int result = onImageLoaded(this, url);
		UiNode child = firstChild;
		while (child != null) {
			result |= child.onImageLoaded(url);
			child = child.getNextSibling();
		}
		return result;
	}

	protected int onImageLoaded(UiNode node, String url) {
		int result = EVENT_IGNORED;
		if (style != null) {
			UiAtomicStyle as = style.getAtomicStyleOf(this);
			if (url.equals(as.getBackgroundImage()) || url.equals(as.getBorderImage())) {
				setChanged(CHANGED_DISPLAY);
				result = EVENT_AFFECTED;
			}
		}
		return result;
	}

	public void onMount() {
		UiNode child = firstChild;
		while (child != null) {
			child.onMount();
			child = child.getNextSibling();
		}
	}

	public void onUnmount() {
		UiNode child = firstChild;
		while (child != null) {
			child.onUnmount();
			child = child.getNextSibling();
		}
	}

	public void onFocus(UiNode target, boolean gained, UiNode other) {
		setChanged(CHANGED_DISPLAY);
	}

	public Json toJson() {
		Json json = Json.createObject();
		json.setString("name", getFullName());
		json.setNumber("flags", flags);
		json.setNumber("changed",  changed);
		json.setString("left", left);
		json.setString("top", top);
		json.setString("right", right);
		json.setString("bottom", bottom);
		json.setString("width", width);
		json.setString("height", height);
		json.setString("scrollLeft", scrollLeft);
		json.setString("scrollTop", scrollTop);
		json.setString("scrollWidth", scrollWidth);
		json.setString("scrollHeight", scrollHeight);
		Json children = json.setNewArray("children");
		for (UiNode child : getChildren()) {
			children.add(child.toJson());
		}
		return json;
	}

	public static class Builder {

		private final Deque<UiNode> stack;

		private final Length.Unit unit;

		public static final double NA = Double.NaN;

		public Builder(UiNode node, String defaultUnit) {
			stack = new ArrayDeque<>();
			stack.add(node);
			this.unit = Length.unitOf(defaultUnit);
		}

		public Builder enter(UiNode node) {
			stack.peek().appendChild(node);
			stack.push(node);
			return this;
		}

		public Builder leave() {
			stack.pop();
			return this;
		}

		public Builder text(String text) {
			UiNode node = stack.peek();
			if (node instanceof UiButton) {
				UiButton textNode = (UiButton) node;
				textNode.setString(text);
			}
			return this;
		}

		public Builder locate(String left, String top, String right, String bottom, String width, String height) {
			UiNode node = stack.peek();
			node.setBounds(left, top, right, bottom, width, height);
			return this;
		}

		public Builder locate(double left, double top, double right, double bottom, double width, double height) {
			UiNode node = stack.peek();
			node.setBounds(
					encode(left), encode(top), encode(right), encode(bottom),
					encode(width), encode(height));
			return this;
		}

		private Length encode(double value) {
			if (Double.isNaN(value)) {
				return null;
			} else {
				return new Length(value, unit);
			}
		}

		public Builder size(String width, String height) {
			UiNode node = stack.peek();
			node.setBounds(null, null, null, null, width, height);
			return this;
		}

		public Builder size(double width, double height) {
			UiNode node = stack.peek();
			node.setBounds(null, null, null, null, encode(width), encode(height));
			return this;
		}

		public Builder style(UiStyle s) {
			UiNode node = stack.peek();
			node.setStyle(s);
			return this;
		}

		public Builder spacing(String value) {
			UiNode node = stack.peek();
			Asserts.assume(node instanceof UiGroup);
			UiGroup group = (UiGroup) node;
			group.setSpacing(value);
			return this;
		}

		public Builder spacing(double value) {
			UiNode node = stack.peek();
			Asserts.assume(node instanceof UiGroup);
			UiGroup group = (UiGroup) node;
			group.setSpacing(encode(value));
			return this;
		}

		public Builder source(DataSource source) {
			UiNode node = stack.peek();
			Asserts.assume(node instanceof UiList);
			UiList list = (UiList) node;
			list.setDataSource(source);
			return this;
		}

		public UiNode build() {
			return stack.peekLast();
		}

	}

}

