package com.github.vvorks.builder.client.common.ui;

import java.math.BigDecimal;
import java.util.Date;

import com.github.vvorks.builder.client.ClientSettings;
import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Asserts;

public class UiVerticalList extends UiGroup {

	/** 疑似スクロール用のマージン行数 */
	protected static final int VIEW_MARGIN	= 3;

	/** 末端でのカーソル移動実行時のふるまい */
	protected static final int FLAGS_EDGE_LOOP = 0x00010000;

	/** データ更新を直ちにコミットするか否か */
	protected static final int FLAGS_COMMIT_SOON = 0x00020000;

	/** JSONデータ変更済みフラグ（UILine用） */
	private static final int FLAGS_UPDATED = 0x00010000;

	/**
	 * 行クラス
	 */
	protected static class UiLine extends UiNode implements DataRecord {

		private final UiVerticalList list;

		private int index;

		private Json json;

		public UiLine(UiVerticalList list, String name) {
			super(name);
			this.list = list;
			this.index = -1;
			this.json = null;
		}

		protected UiLine(UiLine src) {
			super(src);
			this.list = src.list;
			this.index = src.index;
			this.json = src.json;
		}

		@Override
		public UiLine copy() {
			return new UiLine(this);
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			flush();
			DataSource ds = list.getDataSource();
			this.index = index;
			this.json = ds.getData(index);
			for (UiNode d : this.getDescendantsIf(c -> c instanceof DataField)) {
				DataField field = (DataField) d;
				field.setRecord(this);
			}
		}

		@Override
		public String getName() {
			return index < 0 ? super.getName() : "" + index;
		}

		@Override
		public void onFocus(UiNode target, boolean gained, UiNode other) {
			if (!gained) {
				flush();
			}
			super.onFocus(target, gained, other);
		}

		@Override
		public DataRecord getDataRecord() {
			return this;
		}

		@Override
		public boolean exists() {
			return json != null;
		}

		@Override
		public Class<?> getType(String column) {
			Asserts.assume(exists());
			return json.getType().asClass();
		}

		@Override
		public boolean getBoolean(String column, boolean defaultValue) {
			Asserts.assume(exists());
			return json.getBoolean(column, defaultValue);
		}

		@Override
		public int getInt(String column, int defaultValue) {
			Asserts.assume(exists());
			return json.getInt(column, defaultValue);
		}

		@Override
		public long getLong(String column, long defaultValue) {
			Asserts.assume(exists());
			return json.getLong(column, defaultValue);
		}

		@Override
		public float getFloat(String column, float defaultValue) {
			Asserts.assume(exists());
			return json.getFloat(column, defaultValue);
		}

		@Override
		public double getDouble(String column, double defaultValue) {
			Asserts.assume(exists());
			return json.getDouble(column, defaultValue);
		}

		@Override
		public BigDecimal getDecimal(String column, BigDecimal defaultValue) {
			Asserts.assume(exists());
			return json.getDecimal(column, defaultValue);
		}

		@Override
		public Date getDate(String column, Date defaultValue) {
			Asserts.assume(exists());
			return json.getDate(column, defaultValue);
		}

		@Override
		public String getString(String column, String defaultValue) {
			Asserts.assume(exists());
			return json.getString(column, defaultValue);
		}

		@Override
		public void setNull(String column) {
			Asserts.assume(exists());
			json.setNull(column);
			onFieldUpdated();
		}

		@Override
		public void setBoolean(String column, boolean value) {
			Asserts.assume(exists());
			json.setBoolean(column, value);
			onFieldUpdated();
		}

		@Override
		public void setInt(String column, int value) {
			Asserts.assume(exists());
			json.setInt(column, value);
			onFieldUpdated();
		}

		@Override
		public void setLong(String column, long value) {
			Asserts.assume(exists());
			json.setLong(column, value);
			onFieldUpdated();
		}

		@Override
		public void setFloat(String column, float value) {
			Asserts.assume(exists());
			json.setFloat(column, value);
			onFieldUpdated();
		}

		@Override
		public void setDouble(String column, double value) {
			Asserts.assume(exists());
			json.setDouble(column, value);
			onFieldUpdated();
		}

		@Override
		public void setDecimal(String column, BigDecimal value) {
			Asserts.assume(exists());
			json.setDecimal(column, value);
			onFieldUpdated();
		}

		@Override
		public void setDate(String column, Date value) {
			Asserts.assume(exists());
			json.setDate(column, value);
			onFieldUpdated();
		}

		@Override
		public void setString(String column, String value) {
			Asserts.assume(exists());
			json.setString(column, value);
			onFieldUpdated();
		}

		private void onFieldUpdated() {
			setFlags(FLAGS_UPDATED, true, 0);
			if (list.isFlushSoon()) {
				flush();
			}
		}

		private void flush() {
			if (exists() && isFlagsOn(FLAGS_UPDATED)) {
				DataSource ds = list.getDataSource();
				ds.update(json);
				setFlags(FLAGS_UPDATED, false, 0);
			}
		}

	}

	/** データソース */
	private DataSource dataSource;

	/** テンプレートノード */
	private UiLine template;

	/** ページ高さ（ピクセル単位） */
	private int pageHeight;

	/** 行高さ（ピクセル単位） */
	private int lineHeight;

	/** １画面に表示可能な行数 */
	private int linesPerView;

	/** Wheelにより隠れてしまったフォーカス位置の方向 */
	private int hiddenDir;

	/** Wheelにより隠れてしまったフォーカス位置のインデックス */
	private int hiddenIndex;

	/** Wheelにより隠れてしまったフォーカス位置のカラム番号 */
	private int hiddenColumn;

	/**
	 * UiListを作成する
	 *
	 * @param name ノード名
	 */
	public UiVerticalList(String name) {
		super(name);
		hiddenIndex = -1;
		hiddenColumn = -1;
	}

	/**
	 * コピーコンストラクタ
	 *
	 * @param src コピー元
	 */
	public UiVerticalList(UiVerticalList src) {
		super(src);
		dataSource = src.dataSource;
		template = src.template;
		pageHeight = src.pageHeight;
		lineHeight = src.lineHeight;
		linesPerView = src.linesPerView;
		hiddenIndex = src.hiddenIndex;
		hiddenColumn = src.hiddenColumn;
	}

	@Override
	public UiVerticalList copy() {
		return new UiVerticalList(this);
	}

	/**
	 * データソースを取得する
	 *
	 * @return データソース
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * データソースを設定する
	 *
	 * @param ds データソース
	 */
	public void setDataSource(DataSource ds) {
		UiApplication app = getApplication();
		if (this.dataSource != null) {
			app.detachDataSource(this, this.dataSource);
		}
		this.dataSource = ds;
		if (this.dataSource != null) {
			app.attachDataSource(this, this.dataSource);
		}
	}

	public boolean isLoopMode() {
		return isFlagsOn(FLAGS_EDGE_LOOP);
	}

	public void setLoopMode(boolean loopMode) {
		setFlags(FLAGS_EDGE_LOOP, loopMode, 0);
	}

	public boolean isFlushSoon() {
		return isFlagsOn(FLAGS_COMMIT_SOON);
	}

	public void setFlushSoon(boolean soon) {
		setFlags(FLAGS_COMMIT_SOON, soon, 0);
	}

	@Override
	public boolean isFocusable() {
		return template == null || getFirstChild() == null;
	}

	private boolean isScrollable() {
		return size() >= linesPerView;
	}

	@Override
	protected void notifyHorizontalScroll(int offset, int limit, int count) {
		//NOP
	}

	@Override
	protected void notifyVerticalScroll(int _offset, int _limit, int _count) {
		if (size() >= linesPerView) {
			UiLine first = (UiLine) getFirstChild();
			int count = dataSource.getCount() * lineHeight;
			int offset = (first.getIndex() * lineHeight + getScrollTopPx()) % count;
			super.notifyVerticalScroll(offset, pageHeight, count);
		} else {
			super.notifyVerticalScroll(0, pageHeight, pageHeight);
		}
	}

	@Override
	public int setHorizontalScroll(int offset) {
		return EVENT_IGNORED;
	}

	@Override
	public int setVerticalScroll(int offset) {
		int count = dataSource.getCount() * lineHeight;
		int len = count - pageHeight;
		int index = ((UiLine) getFirstChild()).getIndex();
		int oldOffset = (index * lineHeight + getScrollTopPx()) % count;
		if (!isLoopMode() || !(0 == oldOffset || oldOffset == len)) {
			offset = Math.min(Math.max(0, offset), len);
		}
		int newTop = offset - index * lineHeight;
		scrollVirtual(newTop);
		return EVENT_EATEN;
	}

	@Override
	public void onMount() {
		if (template == null) {
			template = new UiLine(this, "template");
			template.setBounds(Length.ZERO, Length.ZERO, Length.ZERO, null, null, getMaxHeight());
			template.setChildren(clearChildren());
			template.setParent(this);
		}
		updateMetrics();
		super.onMount();
	}

	private void updateMetrics() {
		pageHeight = getHeightPx() - getBorderTopPx() - getBorderBottomPx();
		lineHeight = template.getHeightPx(pageHeight);
		linesPerView = (int) Math.ceil((double)pageHeight / (double)lineHeight);
	}

	private Length getMaxHeight() {
		Length maxHeight = null;
		int maxHeightPx = 0;
		for (UiNode c : getChildren()) {
			Length height = c.getHeight();
			int heightPx = c.getHeightPx();
			if (maxHeight == null || maxHeightPx < heightPx) {
				maxHeight = height;
				maxHeightPx = heightPx;
			}
		}
		return maxHeight;
	}

	@Override
	public void onUnmount() {
		super.onUnmount();
		clearChildren();
	}

	@Override
	public void onResize(int screenWidth, int screenHeight) {
		updateMetrics();
		DataSource ds = dataSource;
		if (ds.isLoaded()) {
			prepareLines(ds.getCount(), ds.getOffset());
		} else {
			prepareLines(0, 0);
		}
	}

	@Override
	public int onDataSourceUpdated(DataSource ds) {
		int result = super.onDataSourceUpdated(ds);
		if (ds.isLoaded()) {
			prepareLines(ds.getCount(), ds.getOffset());
		} else {
			prepareLines(0, 0);
		}
		result |= EVENT_AFFECTED;
		return result;
	}

	@Override
	public int onKeyDown(UiNode target, int keyCode, int charCode, int mods, int time) {
		int result;
		if (isFocusHidden(target)) {
			recoverFocus(target);
		}
		if (isScrollable() && !isLoopMode()) {
			result = onKeyDownScroll(target, keyCode, charCode, mods, time);
		} else if (!isScrollable() && isLoopMode()) {
			result = onKeyDownSingle(target, keyCode, charCode, mods, time);
		} else {
			result = super.onKeyDown(target, keyCode, charCode, mods, time);
		}
		return result;
	}

	private boolean isFocusHidden(UiNode target) {
		return (target == this && hiddenIndex != -1);
	}

	private void recoverFocus(UiNode target) {
		int count = getDataSource().getCount();
		int relative = hiddenDir == 1 ? linesPerView : 0;
		int index = lap(hiddenIndex - relative - VIEW_MARGIN, count);
		renumberChildren(count, index);
		setScrollTop(lineHeight * VIEW_MARGIN);
		UiLine child = (UiLine) getChild(VIEW_MARGIN + relative);
		Asserts.checkNotNull(child);
		restoreFocus(child);
	}

	public int onKeyDownScroll(UiNode target, int keyCode, int charCode, int mods, int time) {
		UiApplication app = getApplication();
		int result = EVENT_IGNORED;
		UiNode root = getRoot();
		int tIndex = getOwnerIndex(target);
		Rect tRect = target.getRectangleOn(root);
		UiNode next = null;
		int axis = AXIS_NO;
		switch (keyCode) {
		case KeyCodes.UP:
			next = app.getNearestNode(target, c -> getExceptRectUp(c, root, tIndex, tRect));
			axis = AXIS_Y;
			break;
		case KeyCodes.DOWN:
			next = app.getNearestNode(target, c -> getExceptRectDown(c, root, tIndex, tRect));
			axis = AXIS_Y;
			break;
		case KeyCodes.TAB:
			int dir = (mods & KeyCodes.MOD_SHIFT) != 0 ? -1 : +1;
			next = app.getAdjacentNode(target, dir, c -> {
				int i = getOwnerIndex(c);
				return i == -1 || dir == -1 ? i <= tIndex : i >= tIndex;
			});
			axis = AXIS_XY;
			result |= EVENT_CONSUMED;
			break;
		default:
			break;
		}
		if (next != null) {
			app.scrollFor(next);
			app.setFocus(next, axis);
			result |= EVENT_EATEN;
		}
		return result;
	}

	private int getOwnerIndex(UiNode node) {
		while (node != null && !(node instanceof UiLine)) {
			node = node.getParent();
		}
		return node != null ? ((UiLine) node).getIndex() : -1;
	}

	/**
	 * 末尾から先頭への切れ目を超えずに移動するための判定式
	 *
	 * @param child 対象子ノード
	 * @param root ルートノード（矩形取得に必要）
	 * @param tIndex 現在の行インデックス
	 * @param tRect 現在のノード矩形
	 * @return childの矩形。またはnull（切れ目を超えている場合）
	 */
	private Rect getExceptRectUp(UiNode child, UiNode root,int tIndex, Rect tRect) {
		Rect r = child.getRectangleOn(root);
		int i = getOwnerIndex(child);
		return (r.getBottom() <= tRect.getTop()) && (i == -1 || i <= tIndex) ? r : null;
	}

	/**
	 * 先頭から末尾への切れ目を超えずに移動するための判定式
	 *
	 * @param child 対象子ノード
	 * @param root ルートノード（矩形取得に必要）
	 * @param tIndex 現在の行インデックス
	 * @param tRect 現在のノード矩形
	 * @return childの矩形。またはnull（切れ目を超えている場合）
	 */
	private Rect getExceptRectDown(UiNode child, UiNode root,int tIndex, Rect tRect) {
		Rect r = child.getRectangleOn(root);
		int i = getOwnerIndex(child);
		return (r.getTop() >= tRect.getBottom()) && (i == -1 || i >= tIndex) ? r : null;
	}

	public int onKeyDownSingle(UiNode target, int keyCode, int charCode, int mods, int time) {
		UiApplication app = getApplication();
		int result = EVENT_IGNORED;
		UiNode root = getRoot();
		int tIndex = getOwnerIndex(target);
		Rect tRect = target.getRectangleOn(root);
		UiNode next = null;
		int axis = AXIS_NO;
		int offset = size() * lineHeight;
		switch (keyCode) {
		case KeyCodes.UP:
			next = app.getNearestNode(target, c -> getXlatRectUp(c, root, tIndex, tRect, offset));
			axis = AXIS_Y;
			break;
		case KeyCodes.DOWN:
			next = app.getNearestNode(target, c -> getXlatRectDown(c, root, tIndex, tRect, offset));
			axis = AXIS_Y;
			break;
		case KeyCodes.TAB:
			int dir = (mods & KeyCodes.MOD_SHIFT) != 0 ? -1 : +1;
			next = app.getAdjacentNode(target, dir, c -> this.isAncestor(c));
			axis = AXIS_XY;
			result |= EVENT_CONSUMED;
			break;
		default:
			break;
		}
		if (next != null) {
			app.scrollFor(next);
			app.setFocus(next, axis);
			result |= EVENT_EATEN;
		}
		return result;
	}

	private Rect getXlatRectUp(UiNode child, UiNode root, int tIndex, Rect tRect, int offset) {
		if (!this.isAncestor(child)) {
			return null;
		}
		Rect r = child.getRectangleOn(root);
		int newIndex = getOwnerIndex(child);
		//カーソル上移動の場合
		if (r.getBottom() <= tRect.getTop()) {
			//上にあるノードはそのまま対象
		} else if (r.getTop() >= tRect.getBottom()) {
			//下にあるノードの場合
			if (tIndex != newIndex) {
				//別レコード内ノードは位置を上に移動して対象化
				r.move(0, -offset);
			} else {
				//同レコード内ノードは対象外
				r = null;
			}
		} else {
			//横並びの子ノードは対象外
			r = null;
		}
		return r;
	}

	private Rect getXlatRectDown(UiNode child, UiNode root, int tIndex, Rect tRect, int offset) {
		if (!this.isAncestor(child)) {
			return null;
		}
		Rect r = child.getRectangleOn(root);
		int newIndex = getOwnerIndex(child);
		//カーソル下移動の場合
		if (r.getTop() >= tRect.getBottom()) {
			//下にあるノードはそのまま対象
		} else if (r.getBottom() <= tRect.getTop()) {
			//上にあるノードの場合
			if (tIndex != newIndex) {
				//別レコード内ノードは位置を下に移動して対象化
				r.move(0, +offset);
			} else {
				//同レコード内ノードは対象外
				r = null;
			}
		} else {
			//横並びの子ノードは対象外
			r = null;
		}
		return r;
	}

	private void prepareLines(int count, int offset) {
		boolean hasFocus = hasFocus();
		if (count <= 0 || linesPerView <= 0) {
			clearChildren();
			if (hasFocus) {
				setFocus(this);
			}
			return;
		}
		UiApplication app = getApplication();
		boolean hasMargin = (count >= linesPerView);
		int oldLines = size();
		int newLines = hasMargin ? linesPerView + VIEW_MARGIN * 2 : count;
		if (oldLines != newLines) {
			int scrollTop = -1;
			if (newLines > oldLines) {
				for (int i = 0; i < newLines - oldLines; i++) {
					insertChild(template.copy());
				}
				if (oldLines < linesPerView && newLines >= linesPerView) {
					scrollTop = lineHeight * VIEW_MARGIN;
				}
			} else {
				for (int i = 0; i < oldLines - newLines; i++) {
					removeFirstChild();
				}
				if (oldLines >= linesPerView && newLines < linesPerView) {
					scrollTop = 0;
				}
			}
			int index = hasMargin ? lap(offset - VIEW_MARGIN, count) : offset;
			renumberChildren(count, index);
			relocateChildren();
			if (scrollTop >= 0) {
				setScrollTop(scrollTop);
			}
			if (hasFocus) {
				setFocus(app.getFirstFocus(this));
			}
		}
	}

	private void renumberChildren(int count, int index) {
		for (UiNode c = getFirstChild(); c != null; c = c.getNextSibling()) {
			((UiLine)c).setIndex(index);
			index = lap(index + 1, count);
		}
	}

	private void relocateChildren() {
		Length spacingWidth = getSpacingWidth();
		int spacingHeightPx = getSpacingHeightPx();
		int total = spacingHeightPx;
		for (UiNode child : this.getChildrenIf(c -> !c.isDeleted() && c.isVisible())) {
			Length top = new Length(total);
			Length height = child.getHeight();
			Length left = child.getLeft();
			Length right = child.getRight();
			Length width = child.getWidth();
			if (left == null && right == null && width == null) {
				left = right = spacingWidth;
			}
			child.setBounds(left, top, right, null, width, height);
			total += child.getHeightPx() + spacingHeightPx;
		}
		setScrollHeight(total);
	}

	@Override
	public int onMouseWheel(UiNode target, int x, int y, int dx, int dy, int mods, int time) {
		int result = EVENT_CONSUMED;
		if (size() >= linesPerView) {
			int count = getDataSource().getCount();
			int scrollTop = getScrollTopPx();
			int newTop;
			if (isLoopMode()) {
				newTop = scrollTop + dy * ClientSettings.WHEEL_SCALE;
			} else {
				int index = ((UiLine) getFirstChild()).getIndex();
				int diff = (lap(index + VIEW_MARGIN, count) - VIEW_MARGIN) * lineHeight;
				int top = scrollTop + diff;
				int len = count * lineHeight - pageHeight;
				top = Math.min(Math.max(0, top + dy * ClientSettings.WHEEL_SCALE), len);
				newTop = top - diff;
			}
			if (scrollTop != newTop) {
				scrollVirtual(newTop);
				result |= EVENT_AFFECTED;
				getApplication().adjustAxis(this, AXIS_Y);
			}
		}
		return result;
	}

	@Override
	public void scrollFor(UiNode child) {
		super.scrollFor(child);
		if (size() >= linesPerView) {
			int scrollTop = getScrollTopPx();
			int scrollHeight = getScrollHeightPx();
			int margin = lineHeight * VIEW_MARGIN;
			if (scrollTop < margin || scrollHeight - (scrollTop + pageHeight) < margin) {
				scrollVirtual(scrollTop);
			}
		}
	}

	private void scrollVirtual(int scrollTop) {
		int scrollHeight = getScrollHeightPx();
		int margin = lineHeight * VIEW_MARGIN;
		if (scrollTop < margin) {
			while (scrollTop < margin) {
				rollUp();
				scrollTop += lineHeight;
			}
			setScrollTop(scrollTop);
			relocateChildren();
		} else if (scrollHeight - (scrollTop + pageHeight) < margin) {
			while (scrollHeight - (scrollTop + pageHeight) < margin) {
				rollDown();
				scrollTop -= lineHeight;
			}
			setScrollTop(scrollTop);
			relocateChildren();
		}
	}

	private void rollUp() {
		int count = dataSource.getCount();
		UiLine edgeLine = (UiLine) getFirstChild();
		int index = lap(edgeLine.getIndex() - 1, count);
		UiLine rollLine = (UiLine) removeLastChild();
		UiNode focus = getFocus();
		if (rollLine.isAncestor(focus)) {
			hiddenDir = +1;
			saveFocus(rollLine, focus);
		}
		rollLine.setIndex(index);
		insertChild(rollLine);
		if (index == hiddenIndex) {
			restoreFocus(rollLine);
		}
	}

	private void rollDown() {
		int count = dataSource.getCount();
		UiLine edgeLine = (UiLine) getLastChild();
		int index = lap(edgeLine.getIndex() + 1, count);
		UiLine rollLine = (UiLine) removeFirstChild();
		UiNode focus = getFocus();
		if (rollLine.isAncestor(focus)) {
			hiddenDir = -1;
			saveFocus(rollLine, focus);
		}
		rollLine.setIndex(index);
		appendChild(rollLine);
		if (index == hiddenIndex) {
			restoreFocus(rollLine);
		}
	}

	private void saveFocus(UiLine rollLine, UiNode focus) {
		hiddenIndex = rollLine.getIndex();
		hiddenColumn = rollLine.getDescendantIndex(focus);
		setFocus(this, AXIS_Y);
	}

	private void restoreFocus(UiLine rollLine) {
		UiNode restore = rollLine.getDescendantAt(hiddenColumn);
		setFocus(restore, AXIS_Y);
		hiddenDir = 0;
		hiddenIndex = -1;
		hiddenColumn = -1;
	}

	private static int lap(int index, int count) {
		return (index + count) % count;
	}

}