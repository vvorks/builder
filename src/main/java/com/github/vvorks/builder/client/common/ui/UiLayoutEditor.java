package com.github.vvorks.builder.client.common.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Iterables;
import com.github.vvorks.builder.common.logging.Logger;

/**
 * レイアウトエディタ
 * TODO なんかポイント位置が微妙にずれている気がする（Borderのずれ？）
 * TODO ハンドルクリック後？ハンドルだけが移動する不具合あり。要調査
 * TODO コピー＆ペースト操作（クリップボードが必要）
 * TODO ロード・セーブ
 * TODO テンプレートノード設定（レイアウトエディタ機能外）
 * TODO パレット（レイアウトエディタ機能外）
 * TODO プロパティリストによる操作（レイアウトエディタ機能外）
 */
public class UiLayoutEditor extends UiNode {

	public static final Class<?> THIS = UiLayoutEditor.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	/** 非選択ノード内でドラッグした場合、当該ノード内での新規作成操作をするか否か */
	private static final boolean NEW_IN_PARENT = false;

	/** ハンドル片の幅・高さ（単位：PX） */
	private static final int HANDLE_SIZE	= 8;

	/** ハンドル片の幅・高さの半分（単位：PX） */
	private static final int HANDLE_HALF	= HANDLE_SIZE / 2;

	/** ドラッグ閾値（単位：PX） */
	private static final int DRAG_THRETHOLD	= 4;

	/** デフォルトグリッドサイズ */
	private static final Length DEFAULT_GRID = new Length("1px");

	/** 矢印キーコードからインデックス値を得るための配列 */
	private static final int[] ARROW_KEYS	= {KeyCodes.LEFT, KeyCodes.RIGHT, KeyCodes.UP, KeyCodes.DOWN};
	private static final int[] ARROW_X_DIRS	= {-1,+1, 0, 0};
	private static final int[] ARROW_Y_DIRS	= { 0, 0,-1,+1};

	/** ハンドル片のスタイル */
	private static final UiAtomicStyle HANDLE_STYLE = UiStyle.newAtomicBuilder(THIS, "handle", null)
			.color(Colors.BLACK)
			.backgroundColor(Colors.WHITE)
			.font("6pt")
			.align(UiAtomicStyle.TEXT_ALIGN_CENTER, UiAtomicStyle.VERTICAL_ALIGN_MIDDLE)
			.borderColor(Colors.BLACK)
			.borderWidth("1px")
			.build();

	/** 作成中ハンドルのスタイル */
	private static final UiAtomicStyle SHADE_STYLE = UiStyle.newAtomicBuilder(THIS, "shade", null)
			.backgroundColor(0x20000000)
			.build();

	/** ボックススタイル（仮） TODO: 用が済んだら削除する事 */
	private static final UiAtomicStyle GREEN = UiStyle.newAtomicBuilder(THIS, "green", null)
			.color(Colors.BLACK)
			.backgroundColor(Colors.GREEN)
			.font("12pt")
			.align(UiAtomicStyle.TEXT_ALIGN_CENTER, UiAtomicStyle.VERTICAL_ALIGN_MIDDLE)
			.borderColor(Colors.BLACK)
			.borderWidth("2px")
			.build();

	/**
	 * ハンドル（ピース）種別
	 */
	private enum HandleType {

		NW(0,  "nwse-resize"), N(1, "ns-resize"), NE(2, "nesw-resize"),
		W (3,  "ew-resize"  ), C(4, "move"),      E (5, "ew-resize"  ),
		SW(6,  "nesw-resize"), S(7, "ns-resize"), SE(8, "nwse-resize"),
		OB(-1, "default");

		private final int index;

		private final String cursorKeyword;

		private HandleType(int index, String cursorKeyword) {
			this.index = index;
			this.cursorKeyword = cursorKeyword;
		}

		public int row() {
			return index / 3;
		}

		public int col() {
			return index % 3;
		}

		public boolean isCorner() {
			return row() != 1 && col() != 1;
		}

		public String getCursorKeyword() {
			return cursorKeyword;
		}

		public static HandleType valueOf(int index) {
			HandleType[] values = HandleType.values();
			if (0 <= index && index < 9) {
				return values[index];
			} else {
				return OB;
			}
		}

	}

	private enum MouseState {
		UP, DOWN, DRAG;
	}

	/**
	 * ハンドル片（ハンドル周囲に配置される表示部品）
	 */
	private static class HandlePiece extends UiNode {

		private final Handle owner;

		private final HandleType type;

		public HandlePiece(Handle owner, HandleType type) {
			this.owner = owner;
			this.type = type;
		}

		@Override
		public HandlePiece copy() {
			throw new UnsupportedOperationException();
		}

		public HandleType getType() {
			return type;
		}

		@Override
		protected void syncElementStyle(CssStyle.Builder b) {
			super.syncElementStyle(b);
			b.property("cursor", type.getCursorKeyword());
			UiNode target = owner.getTarget();
			boolean mark;
			switch (type) {
			case N:
				mark = target != null && (target.getTop() != null);
				break;
			case W:
				mark = target != null && (target.getLeft() != null);
				break;
			case E:
				mark = target != null && (target.getRight() != null);
				break;
			case S:
				mark = target != null && (target.getBottom() != null);
				break;
			default:
				mark = false;
				break;
			}
			b.property("background-color", mark ? "red" : "white");
		}

	}

	/**
	 * ハンドル 周辺８ヶ所にハンドル片を持つ
	 */
	private class Handle extends UiNode {

		/** 対象ノード */
		private UiNode target;

		/** ドラッグ操作前のターゲットの位置情報 */
		private Bounds oldBounds;

		/** ドラッグ操作前のターゲットの位置情報 */
		private Rect oldRect;

		/** ドラッグ操作時のx拡縮比 */
		private double xScale;

		/** ドラッグ操作時のy拡縮比 */
		private double yScale;

		public Handle() {
			final double X = UiNode.Builder.NA;
			UiNode.Builder b = new UiNode.Builder(this, "px");
			b.enter(new HandlePiece(this, HandleType.NW))
				.style(HANDLE_STYLE)
				.locate(0, 0, X, X, HANDLE_SIZE, HANDLE_SIZE)
				.leave();
			b.enter(new HandlePiece(this, HandleType.N))
				.style(HANDLE_STYLE)
				.locate(X, 0, X, X, HANDLE_SIZE, HANDLE_SIZE)
				.leave();
			b.enter(new HandlePiece(this, HandleType.NE))
				.style(HANDLE_STYLE)
				.locate(X, 0, 0, X, HANDLE_SIZE, HANDLE_SIZE)
				.leave();
			b.enter(new HandlePiece(this, HandleType.W))
				.style(HANDLE_STYLE)
				.locate(0, X, X, X, HANDLE_SIZE, HANDLE_SIZE)
				.leave();
			b.enter(new HandlePiece(this, HandleType.E))
				.style(HANDLE_STYLE)
				.locate(X, X, 0, X, HANDLE_SIZE, HANDLE_SIZE)
				.leave();
			b.enter(new HandlePiece(this, HandleType.SW))
				.style(HANDLE_STYLE)
				.locate(0, X, X, 0, HANDLE_SIZE, HANDLE_SIZE)
				.leave();
			b.enter(new HandlePiece(this, HandleType.S))
				.style(HANDLE_STYLE)
				.locate(X, X, X, 0, HANDLE_SIZE, HANDLE_SIZE)
				.leave();
			b.enter(new HandlePiece(this, HandleType.SE))
				.style(HANDLE_STYLE)
				.locate(X, X, 0, 0, HANDLE_SIZE, HANDLE_SIZE)
				.leave();
		}

		@Override
		public Handle copy() {
			throw new UnsupportedOperationException();
		}

		public void reinit() {
			target = null;
			oldBounds = null;
			oldRect = null;
			xScale = 0;
			yScale = 0;
		}

		public UiNode getTarget() {
			return target;
		}

		public Bounds getOldBounds() {
			return oldBounds;
		}

		public Bounds getNewBounds() {
			return target.getBounds();
		}

		public void setTarget(UiNode target) {
			if (this.target != target) {
				this.target = target;
				if (target != null) {
					//単位追従のため、仮設定
					this.setBounds(target);
					Rect r = target.getRectangleOn(UiLayoutEditor.this);
					r.inflate(HANDLE_HALF, HANDLE_HALF);
					//位置設定
					this.updateBounds(r);
				}
				//ハンドル片の更新フラグを立てる
				for (UiNode child : getChildren()) {
					child.setChanged(CHANGED_DISPLAY);
				}
			}
		}

		@Override
		public boolean isHit(int x, int y) {
			HandleType type = getHandleType(x, y);
			return type != HandleType.OB && type != HandleType.C;
		}

		public HandleType getHandleType(int x, int y) {
			int w = getWidthPx();
			int h = getHeightPx();
			HandleType type;
			if (between(x, HANDLE_HALF, w - HANDLE_HALF) &&
				between(y, HANDLE_HALF, h - HANDLE_HALF)  ) {
				type = HandleType.C;
			} else {
				type = HandleType.OB;
			}
			int index = 0;
			if (between(y, h - HANDLE_SIZE, h)) {
				index += HandleType.S.row() * 3;
			} else if (between(y, h/2 - HANDLE_HALF, h/2 + HANDLE_HALF)) {
				index += HandleType.C.row() * 3;
			} else if (between(y, 0, HANDLE_SIZE)) {
				index += HandleType.N.row() * 3;
			} else {
				return type;
			}
			if (between(x, w - HANDLE_SIZE, w)) {
				index += HandleType.E.col();
			} else if (between(x, w/2 - HANDLE_HALF, w/2 + HANDLE_HALF)) {
				index += HandleType.C.col();
			} else if (between(x, 0, HANDLE_SIZE)) {
				index += HandleType.W.col();
			} else {
				return type;
			}
			return HandleType.valueOf(index);
		}

		private boolean between(int v, int min, int max) {
			return min <= v && v < max;
		}

		public void preDrag(Handle masterHandle) {
			oldBounds = target.getBounds();
			oldRect = target.getRectangleOnParent();
			if (masterHandle == this) {
				xScale = yScale = 1;
			} else {
				UiNode master = masterHandle.getTarget();
				int width = master.getWidthPx();
				int height = master.getHeightPx();
				xScale = (double) oldRect.getWidth()  / width;
				yScale = (double) oldRect.getHeight() / height;
			}
		}

		public void onDrag(int dx, int dy, HandleType dragMode) {
			Rect newRect = new Rect(oldRect);
			if (dragMode == HandleType.C) {
				newRect.move(dx, dy);
			} else {
				int sx = (int) Math.round(dx * xScale);
				int sy = (int) Math.round(dy * yScale);
				int col = dragMode.col();
				int row = dragMode.row();
				newRect.resize(
						col == 0 ? sx : 0,
						row == 0 ? sy : 0,
						col == 2 ? sx : 0,
						row == 2 ? sy : 0);
			}
			target.updateBounds(newRect);
			target.translateOn(newRect, UiLayoutEditor.this);
			newRect.inflate(+HANDLE_HALF, +HANDLE_HALF);
			this.updateBounds(newRect);
		}

		public void onPieceChanged(HandleType dragMode) {
			for (UiNode child : getChildrenIf(e -> ((HandlePiece)e).getType() == dragMode)) {
				child.setChanged(CHANGED_DISPLAY);
			}
		}

		public void setHandleVisible(boolean b) {
			for (UiNode c : getChildren()) {
				c.setVisible(b);
			}
		}

	}

	/**
	 * 作成操作履歴
	 */
	private class NewHistory extends History {

		/** 対象ノード */
		private final UiNode target;

		/** 親ノード */
		private final UiNode parent;

		/** 作成後の位置情報*/
		private final Bounds newBounds;

		public NewHistory(int time, UiNode target) {
			super(time);
			this.target = target;
			this.parent = target.getParent();
			this.newBounds = target.getBounds();
		}

		@Override
		public void undo() {
			parent.deleteChild(target);
		}

		@Override
		public void redo() {
			//parentが非rootなら結果的に末尾追加となる
			parent.insertBefore(target, handleTop);
			target.setBounds(newBounds);
		}

		@Override
		public Json toJson() {
			Json json = super.toJson();
			json.setString("target", target.getName());
			json.set("newBounds", newBounds.toJson());
			return json;
		}

	}

	/**
	 * 削除操作履歴
	 */
	private class DeleteHistory extends History {

		/** 対象ノード */
		private final UiNode target;

		/** 親ノード */
		private final UiNode parent;

		/** 削除前の位置情報*/
		private final Bounds oldBounds;

		public DeleteHistory(int time, UiNode target) {
			super(time);
			this.target = target;
			this.parent = target.getParent();
			this.oldBounds = target.getBounds();
		}

		@Override
		public void undo() {
			//parentが非rootなら結果的に末尾追加となる
			parent.insertBefore(target, handleTop);
			target.setBounds(oldBounds);
		}

		@Override
		public void redo() {
			parent.deleteChild(target);
		}

		@Override
		public Json toJson() {
			Json json = super.toJson();
			json.setString("target", target.getName());
			json.set("oldBounds", oldBounds.toJson());
			return json;
		}

	}

	/**
	 * 移動（及びサイズ変更）操作履歴
	 */
	private class MoveHistory extends History {

		/** 対象ノード*/
		private final UiNode target;

		/** 操作前の位置情報 */
		private final Bounds oldBounds;

		/** 操作後の位置情報 */
		private final Bounds newBounds;

		public MoveHistory(int time, Handle handle) {
			super(time);
			this.target = handle.getTarget();
			this.oldBounds = handle.getOldBounds();
			this.newBounds = handle.getNewBounds();
		}

		@Override
		public void undo() {
			target.setBounds(oldBounds);
		}

		@Override
		public void redo() {
			target.setBounds(newBounds);
		}

		@Override
		public Json toJson() {
			Json json = super.toJson();
			json.setString("target", target.getName());
			json.set("oldBounds", oldBounds.toJson());
			json.set("newBounds", newBounds.toJson());
			return json;
		}

	}

	public class SelectionHistory extends History {

		/** 操作前の選択対象ノードリスト */
		private List<UiNode> undoTargets;

		/** 操作後の選択対象ノードリスト */
		private List<UiNode> redoTargets;

		protected SelectionHistory(int time, List<UiNode> undoTargets, List<UiNode> redoTargets) {
			super(time);
			this.undoTargets = undoTargets;
			this.redoTargets = redoTargets;
		}

		@Override
		public void undo() {
			if (undoTargets != null) {
				for (UiNode node : undoTargets) {
					addHandle(node);
				}
			}
		}

		@Override
		public void redo() {
			if (redoTargets != null) {
				for (UiNode node : redoTargets) {
					addHandle(node);
				}
			}
		}

	}

	/** 履歴バッファ */
	private final HistoryBuffer history;

	/** Xグリッド単位 */
	private Length xGrid = DEFAULT_GRID;

	/** Yグリッド単位 */
	private Length yGrid = DEFAULT_GRID;

	/** ハンドル先頭のセパレータ */
	private UiPhantom handleTop;

	/** 現在使用中のハンドルリスト */
	private List<Handle> activeHandles;

	/** 使用済みハンドルリスト */
	private List<Handle> unusedHandles;

	/** マウス状態(MOUSE_xxを設定） */
	private MouseState mouseState;

	/** 現在操作中のノード */
	private UiNode masterTarget;

	/** 現在操作中のハンドル */
	private Handle masterHandle;

	/** 現在操作中のハンドルが新規作成されているかを示すフラグ */
	private boolean masterHandleIsNew;

	/** ダウン時点のX座標 */
	private int xDown;

	/** ダウン時点のY座標 */
	private int yDown;

	/** ドラッグモード（HandleTypeを流用）*/
	private HandleType dragMode;

	/** 操作前の対象ノードリスト */
	private List<UiNode> beforeTargets;

	/** ドラッグ中のX座標 */
	private int xDrag;

	/** ドラッグ中のY座標 */
	private int yDrag;

	/** ドラッグ中のModifier */
	private int mDrag;

	public UiLayoutEditor(String name) {
		super(name);
		history = new HistoryBuffer();
		activeHandles = new ArrayList<>();
		unusedHandles = new ArrayList<>();
		handleTop = new UiPhantom();
		setFocusable(true);
		setHookEvents(true);
		registerStyle(GREEN);
		registerStyle(HANDLE_STYLE);
		registerStyle(SHADE_STYLE);
		this.appendChild(handleTop);
		resetDragState();
	}

	private void resetDragState() {
		mouseState = MouseState.UP;
		xDown = xDrag = 0;
		yDown = yDrag = 0;
		masterTarget = null;
		masterHandle = null;
		masterHandleIsNew = false;
		dragMode = null;
	}

	@Override
	public UiLayoutEditor copy() {
		throw new UnsupportedOperationException();
	}

	private UiNode getTargetOf(Handle handle) {
		return handle.getTarget();
	}

	private Handle getHandleOf(UiNode node) {
		for (Handle h : activeHandles) {
			if (h.getTarget() == node) {
				return h;
			}
		}
		return null;
	}

	private boolean hasHandles() {
		return !activeHandles.isEmpty();
	}

	private List<UiNode> getHandleTargets() {
		if (activeHandles.isEmpty()) {
			return Collections.emptyList();
		}
		List<UiNode> result = new ArrayList<>();
		for (Handle h : activeHandles) {
			result.add(h.getTarget());
		}
		return result;
	}

	private Handle addHandle(UiNode target) {
		Handle handle = obtainHandle();
		this.appendChild(handle);
		handle.setTarget(target);
		activeHandles.add(handle);
		return handle;
	}

	private Handle obtainHandle() {
		int index = indexOf(unusedHandles, h -> !h.isDeleted());
		if (index == -1) {
			return new Handle();
		}
		Handle handle = unusedHandles.remove(index);
		handle.reinit();
		return handle;
	}

	private <T> int indexOf(List<T> list, Predicate<T> condition) {
		for (int i = 0; i < list.size(); i++) {
			T e = list.get(i);
			if (condition.test(e)) {
				return i;
			}
		}
		return -1;
	}

	private void removeHandle(Handle handle) {
		this.deleteChild(handle);
		unusedHandles.add(handle);
		activeHandles.remove(handle);
	}

	private void clearHandles() {
		for (Handle h : activeHandles) {
			deleteChild(h);
		}
		unusedHandles.addAll(activeHandles);
		activeHandles.clear();
	}

	@Override
	public int onKeyDown(UiNode self, int keyCode, int charCode, int mods, int time) {
		int result;
		int keyCodeCs = keyCode|(mods & KeyCodes.MOD_CS);
		if (keyCodeCs == (KeyCodes.MOD_CTRL|KeyCodes.KEY_A)) {
			result = doSelectAll(keyCode, mods, time);
		} else if (keyCodeCs == (KeyCodes.MOD_CTRL|KeyCodes.KEY_Z)) {
			result = doUndo(keyCode, mods, time);
		} else if (keyCodeCs == (KeyCodes.MOD_CTRL|KeyCodes.KEY_Y)) {
			result = doRedo(keyCode, mods, time);
		} else if (getArrowKeyIndex(keyCode) != -1) {
			result = doResizeByArrow(keyCode, mods, time);
		} else if (keyCodeCs == KeyCodes.DELETE) {
			result = doDelete(keyCode, mods, time);
		} else if (keyCode == KeyCodes.SHIFT || keyCode == KeyCodes.CTRL) {
			//ドラッグ中のCTRL, SHIFT状態変更をマウスイベントハンドラに転送
			if ((mods & KeyCodes.MOD_LBUTTON) != 0) {
				result = onMouseMove(self, xDrag, yDrag, mods, time);
			} else {
				result = EVENT_IGNORED;
			}
		} else {
			result = EVENT_IGNORED;
		}
		return result;
	}

	private int doSelectAll(int keyCode, int mods, int time) {
		clearHandles();
		for (UiNode node : getChildrenIf(c -> !isSpecialNode(c))) {
			addHandle(node);
		}
		return EVENT_EATEN;
	}

	private int doUndo(int keyCode, int mods, int time) {
		int result = EVENT_IGNORED;
		if (history.canUndo()) {
			clearHandles();
			history.undo();
			result = EVENT_EATEN;
		}
		return result;
	}

	private int doRedo(int keyCode, int mods, int time) {
		int result = EVENT_IGNORED;
		if (history.canRedo()) {
			clearHandles();
			history.redo();
			result = EVENT_EATEN;
		}
		return result;
	}

	private int doResizeByArrow(int keyCode, int mods, int time) {
		int result = EVENT_IGNORED;
		int arrowIndex = getArrowKeyIndex(keyCode);
		//選択中ノードの（微小）移動、サイズ変更
		//TODO サイズ変更がいけてないので要再検討
		if (hasHandles()) {
			List<UiNode> targets = getHandleTargets();
			int xg = xGrid.px(() -> this.getScrollWidthPx());
			int yg = yGrid.px(() -> this.getScrollHeightPx());
			boolean ctrl = isCtrl(mods);
			int dx = ARROW_X_DIRS[arrowIndex] * (ctrl ? 1 : xg);
			int dy = ARROW_Y_DIRS[arrowIndex] * (ctrl ? 1 : yg);
			HandleType dm = isShift(mods) ? HandleType.SE : HandleType.C;
			history.done(new SelectionHistory(time, targets, null));
			for (Handle h : activeHandles) {
				h.preDrag(h);
				h.onDrag(dx, dy, dm);
				history.done(new MoveHistory(time, h));
			}
			history.done(new SelectionHistory(time, null, targets));
			result = EVENT_EATEN;
		}
		return result;
	}

	private int doDelete(int keyCode, int mods, int time) {
		int result = EVENT_IGNORED;
		if (hasHandles()) {
			List<UiNode> targets = getHandleTargets();
			history.done(new SelectionHistory(time, targets, null));
			for (UiNode child : targets) {
				deleteChild(child);
				history.done(new DeleteHistory(time, child));
			}
			clearHandles();
			result = EVENT_EATEN;
		}
		return result;
	}

	private static boolean isSpecialNode(UiNode node) {
		return node instanceof UiPhantom || node instanceof Handle;
	}

	@Override
	public int onMouseDown(UiNode self, int x, int y, int mods, int time) {
		if (!isLButton(mods)) {
			return super.onMouseDown(self, x, y, mods, time)|EVENT_CONSUMED;
		}
		int result = EVENT_CONSUMED;
		if (mouseState == MouseState.UP) {
			//マウス押下時の情報を収集
			Point pt = new Point(x, y);
			UiNode target = this.getVisibleChildAt(pt.getX(), pt.getY());
			UiNode child = target;
			while (child != null) {
				target = child;
				target.translate(pt, +1);
				child = target.getVisibleChildAt(pt.getX(), pt.getY());
			}
			Handle handle;
			HandleType type;
			if (target instanceof HandlePiece) {
				//ハンドル片が押下された→サイズ変更モードへ
				HandlePiece piece = (HandlePiece) target;
				handle = (Handle) piece.getParent();
				target = getTargetOf(handle);
				type = piece.getType();
			} else if (target != null) {
				//ノードが押下された
				handle = getHandleOf(target);
				if (handle != null) {
					//選択中ノードが押下された→移動モードへ
					type = HandleType.C;
				} else {
					//非選択ノードが押下された→ドラッグかアップまで処理保留
					type = HandleType.OB;
				}
			} else {
				//背景が押下された→ドラッグかアップまで処理保留
				handle = null;
				type = HandleType.OB;
			}
			//ハンドル準備
			List<UiNode> targets = getHandleTargets();
			//マウス押下時の情報を保持
			mouseState = MouseState.DOWN;
			xDown = xDrag = x;
			yDown = yDrag = y;
			mDrag = mods;
			masterTarget = target;
			masterHandle = handle;
			masterHandleIsNew = false;
			dragMode = type;
			beforeTargets = targets;
			result |= EVENT_AFFECTED;
		}
		//キーボードフォーカスを設定
		setFocus(this);
		return result;
	}

	@Override
	public int onMouseMove(UiNode self, int x, int y, int mods, int time) {
		if (!isLButton(mods)) {
			return super.onMouseMove(self, x, y, mods, time)|EVENT_CONSUMED;
		}
		int result = EVENT_CONSUMED;
		if (mouseState == MouseState.DOWN && isDragged(xDown, yDown, x, y)) {
			if (dragMode == HandleType.OB) {
				if (masterTarget != null && !NEW_IN_PARENT) {
					//非選択ノードでのドラッグ→移動モード
					//モードの再設定
					dragMode = HandleType.C;
					//ハンドル追加モードでなければ、既存選択をクリア
					if (!isCtrl(mods)) {
						clearHandles();
					}
					//ハンドルを作成
					masterHandle = addHandle(masterTarget);
				} else {
					//背景でのドラッグ→新規作成モード
					//既存選択をクリア
					clearHandles();
					//ダミーノード作成
					UiNode newNode = addNewNode(this, new UiPhantom(), xDown, yDown, x, y);
					//ハンドル分サイズを調整
					Rect r = newNode.getRectangleOnParent();
					r.inflate(-HANDLE_HALF, -HANDLE_HALF);
					newNode.updateBounds(r);
					//ハンドルを作成
					masterHandle = addHandle(newNode);
					//影付きスタイル設定
					masterHandle.setStyle(SHADE_STYLE);
					//ハンドル片を非表示化
					masterHandle.setHandleVisible(false);
					masterHandleIsNew = true;
				}
			}
			for (Handle h : activeHandles) {
				h.preDrag(masterHandle);
			}
			mouseState = MouseState.DRAG;
			result |= EVENT_AFFECTED;
		}
		if (mouseState == MouseState.DRAG && (x != xDrag || y != yDrag || mods != mDrag)) {
			xDrag = x;
			yDrag = y;
			mDrag = mods;
			HandleType dm = (dragMode == HandleType.OB) ? HandleType.SE : dragMode;
			Rect r = masterHandle.oldRect;
			int ox = (dm.col() != 2) ? r.getLeft() : r.getRight();
			int oy = (dm.row() != 2) ? r.getTop() : r.getBottom();
			int dx = roundX(xDrag - xDown + ox) - ox;
			int dy = roundY(yDrag - yDown + oy) - oy;
			if (dm.isCorner() && (mods & KeyCodes.MOD_SHIFT) != 0) {
				double ratio = (double) r.getHeight() / r.getWidth();
				int sign = (dm == HandleType.NW || dm == HandleType.SE) ? +1 : -1;
				dy = sign * (int) Math.round(ratio * dx);
			}
			for (Handle h : activeHandles) {
				h.onDrag(dx, dy, dm);
			}
			result |= EVENT_AFFECTED;
		}
		return result;
	}

	private UiNode addNewNode(UiNode parent, UiNode newNode, int x1, int y1, int x2, int y2) {
		parent.insertBefore(newNode, handleTop);
		newNode.setBounds(Length.ZERO, Length.ZERO, null, null, Length.ZERO, Length.ZERO);
		Rect r = new Rect();
		r.setFromTwoPoints(roundX(x1), roundY(y1), roundX(x2), roundY(y2));
		newNode.updateBounds(r);
		return newNode;
	}

	@Override
	public int onMouseUp(UiNode self, int x, int y, int mods, int time) {
		int result = EVENT_CONSUMED;
		if (mouseState != MouseState.UP) {
			result |= onMouseMove(self, x, y, mods|KeyCodes.MOD_LBUTTON, time);
		}
		if (mouseState == MouseState.DOWN) {
			result |= onMouseClicked(mods, time);
		} else if (mouseState == MouseState.DRAG) {
			result |= onMouseDragged(mods, time);
		}
		resetDragState();
		return result;
	}

	private int onMouseClicked(int mods, int time) {
		int result = EVENT_IGNORED;
		if (dragMode == HandleType.C) {
			//選択中ノードクリックの場合
			if (isCtrl(mods) && !masterHandleIsNew) {
				removeHandle(masterHandle);
				result |= EVENT_AFFECTED;
			}
		} else if (dragMode == HandleType.OB && masterTarget != null) {
			//非選択ノードクリックの場合
			if (!isCtrl(mods)) {
				clearHandles();
			}
			addHandle(masterTarget);
		} else if (dragMode == HandleType.OB) {
			//背景ノードクリックの場合
			clearHandles();
		} else {
			//ハンドルクリックの場合
			result |= onHandleClicked(masterHandle, dragMode);
			history.done(new SelectionHistory(time, beforeTargets, null));
			history.done(new MoveHistory(time, masterHandle));
			history.done(new SelectionHistory(time, null, beforeTargets));
		}
		return result;
	}

	private int onHandleClicked(Handle handle, HandleType mode) {
		int result = EVENT_IGNORED;
		UiNode target = handle.getTarget();
		Rect rect = target.getRectangleOnParent();
		boolean canChange;
		switch (mode) {
		case N:
			canChange = (target.getBottom() != null);
			if (canChange) {
				Length temp = target.getTop();
				target.setTop(target.getHeight());
				target.setHeight(temp);
			}
			break;
		case W:
			canChange = (target.getRight() != null);
			if (canChange) {
				Length temp = target.getLeft();
				target.setLeft(target.getWidth());
				target.setWidth(temp);
			}
			break;
		case E:
			canChange = (target.getLeft() != null);
			if (canChange) {
				Length temp = target.getRight();
				target.setRight(target.getWidth());
				target.setWidth(temp);
			}
			break;
		case S:
			canChange = (target.getTop() != null);
			if (canChange) {
				Length temp = target.getBottom();
				target.setBottom(target.getHeight());
				target.setHeight(temp);
			}
			break;
		default:
			canChange = false;
			break;
		}
		if (canChange) {
			target.updateBounds(rect);
			handle.setBounds(target);
			rect.inflate(HANDLE_HALF, HANDLE_HALF);
			target.translateOn(rect, this);
			handle.updateBounds(rect);
			handle.onPieceChanged(dragMode);
			result |= EVENT_AFFECTED;
		}
		return result;
	}

	private int onMouseDragged(int mods, int time) {
		int result = EVENT_IGNORED;
		if (dragMode == HandleType.OB) {
			masterHandle.setStyle(null);
			masterHandle.setHandleVisible(true);
			Rect r = masterHandle.getRectangleOnParent();
			deleteChild(masterHandle.getTarget());
			clearHandles();
			UiNode bg = (masterTarget != null) ? masterTarget : this;
			for (UiNode ans = bg; ans != this; ans = ans.getParent()) {
				ans.translate(r, +1);
			}
			Iterable<UiNode> itr = bg.getChildrenIf(c -> !isSpecialNode(c) && r.contains(c.getRectangleOnParent()));
			if (Iterables.isEmpty(itr)) {
				//指定範囲にノード作成
				UiNode newNode = new UiBox();
				newNode.setStyle(GREEN); //TODO 仮
				addNewNode(bg, newNode, r.getLeft(), r.getTop(), r.getRight(), r.getBottom());
				//作成後ハンドルを付与するためハンドル作成
				addHandle(newNode);
				//作成履歴を追記
				history.done(new SelectionHistory(time, beforeTargets, null));
				history.done(new NewHistory(time, newNode));
				history.done(new SelectionHistory(time, null, getHandleTargets()));
			} else {
				//範囲内の子ノード選択
				for (UiNode child : itr) {
					addHandle(child);
				}
			}
			result |= EVENT_AFFECTED;
		} else {
			//ドラッグ完了。ヒストリーを残す
			history.done(new SelectionHistory(time, beforeTargets, null));
			for (Handle h : activeHandles) {
				history.done(new MoveHistory(time, h));
			}
			history.done(new SelectionHistory(time, null, beforeTargets));
		}
		return result;
	}

	private boolean isDragged(int x1, int y1, int x2, int y2) {
		return	Math.abs(roundX(x2) - roundX(x1)) > DRAG_THRETHOLD ||
				Math.abs(roundY(y2) - roundY(y1)) > DRAG_THRETHOLD  ;
	}

	private int roundX(int x) {
		int xg = xGrid.px(() -> this.getScrollWidthPx());
		return (int) (Math.round((double)x / xg) * xg);
	}

	private int roundY(int y) {
		int yg = yGrid.px(() -> this.getScrollHeightPx());
		return (int) (Math.round((double)y / yg) * yg);
	}

	private static boolean isLButton(int mods) {
		return (mods & KeyCodes.MOD_LBUTTON) != 0;
	}

	private static boolean isShift(int mods) {
		return (mods & KeyCodes.MOD_SHIFT) != 0;
	}

	private static boolean isCtrl(int mods) {
		return (mods & KeyCodes.MOD_CTRL) != 0;
	}

	private static int getArrowKeyIndex(int keyCode) {
		for (int i = 0; i < ARROW_KEYS.length; i++) {
			if (keyCode == ARROW_KEYS[i]) {
				return i;
			}
		}
		return -1;
	}

}
