package com.github.vvorks.builder.client.common.ui;

import java.util.HashSet;
import java.util.Set;

import com.github.vvorks.builder.client.ui.BuilderStyles;
import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Iterables;

class UiPickerPopup extends UiPage {

	private static final int DEFAULT_PER_PAGE = 1 + 10;

	//TODO いけない参照。要再定義
	private static final UiStyle FRAME_STYLE = BuilderStyles.FIELD_FOCUS;

	//TODO いけない参照。要再定義
	private static final UiStyle TRANSPARENT_STYLE = BuilderStyles.TRANSPARENT;

	//TODO いけない参照。要再定義
	private static final UiStyle NOBORDER_STYLE = BuilderStyles.GROUP;

	private final UiPickerField owner;

	private boolean isEdit;

	private int unitHeight;

	private Rect popupRect;

	private UiGroup outer;

	private UiEditText hint;

	private UiVerticalList list;

	private boolean hintUnder;

	public UiPickerPopup(UiApplication app, UiPickerField owner, boolean isEdit) {
		super("popup", app);
		this.owner = owner;
		this.isEdit = isEdit;
		Rect pivot = owner.getRectangleOnRoot();
		unitHeight = pivot.getHeight();
		Rect screen = owner.getRoot().getRectangleOnParent();
		int perPage = Math.min(DEFAULT_PER_PAGE, screen.getHeight() / unitHeight);
		int height = unitHeight * perPage;
		popupRect = new Rect(0, 0, pivot.getWidth(), height);
		if (height <= screen.getHeight() - pivot.getBottom()) {
			//pivot下に配置
			popupRect.move(pivot.getLeft(), pivot.getTop());
			hintUnder = false;
		} else if (height <= pivot.getTop()) {
			//pivot上に配置
			popupRect.move(pivot.getLeft(), pivot.getBottom() - height);
			hintUnder = true;
		} else {
			//中央に配置
			popupRect.move(pivot.getLeft(), (screen.getHeight() - height) / 2);
			hintUnder = false;
		}
		popupRect.resize(
				- FRAME_STYLE.getBorderLeft().px(0),
				- FRAME_STYLE.getBorderTop().px(0),
				+ FRAME_STYLE.getBorderRight().px(0),
				+ FRAME_STYLE.getBorderBottom().px(0));
	}

	@Override
	protected void initialize() {
		final double NA = UiNodeBuilder.NA;
		UiApplication app = getApplication();
		UiNodeBuilder b = new UiNodeBuilder(this, "px");
		b.enter(outer = new UiGroup("outer"));
			b.style(TRANSPARENT_STYLE);
			b.locate(0, 0, 0, 0, NA, NA);
			b.enter(new UiGroup("group"));
				b.style(FRAME_STYLE);
				b.locate(popupRect);
				int width = popupRect.getWidth()
						- FRAME_STYLE.getBorderLeft().px(0)
						- FRAME_STYLE.getBorderRight().px(0);
				int listHeight = popupRect.getHeight()
						- FRAME_STYLE.getBorderTop().px(0)
						- FRAME_STYLE.getBorderBottom().px(0)
						- unitHeight;
				int hintTop;
				int listTop;
				if (hintUnder) {
					hintTop = listHeight;
					listTop = 0;
				} else {
					hintTop = 0;
					listTop = unitHeight;
				}
				DataSource ds = owner.getDataSource();
				ds.setCriteria(null);
				b.enter(hint = new UiEditText("hint"));
					b.style(owner.getStyle());
					b.locate(0, hintTop, NA, NA, width, unitHeight);
				b.leave();
				b.enter(list = new UiVerticalList("list"));
					b.style(NOBORDER_STYLE);
					b.source(ds);
					b.locate(0, listTop, NA, NA, width, listHeight);
					b.loop(false);
					b.flushSoon(false);
					b.enter(new UiButtonField("_title"));
						b.style(owner.getStyle());
						b.locate(0, 0, NA, NA, width, unitHeight);
						b.action(n -> pickAndEat(n));
					b.leave();
				b.leave();
			b.leave();
		b.leave();
		app.setFocus(hint);
		if (isEdit) {
			hint.startHalfEditing();
		}
	}

	@Override
	public int onMouseClick(UiNode target, int x, int y, int mods, int time) {
		if (target != outer) {
			return super.onMouseDown(target, x, y, mods, time);
		}
		cancel();
		return EVENT_EATEN;
	}

	@Override
	public int onKeyDown(UiNode target, int keyCode, int charCode, int mods, int time) {
		int result = EVENT_IGNORED;
		int k = keyCode|mods;
		Set<UiNode> targetAns = new HashSet<>();
		Iterables.addAll(targetAns, target.getAncestors());
		if (target == hint || targetAns.contains(hint)) {
			if (k == KeyCodes.ESCAPE) {
				cancel();
				result = EVENT_EATEN;
			}
		} else if (target == list || targetAns.contains(list)) {
			if (k == KeyCodes.SPACE) {
				pick((DataField)target);
				result = EVENT_EATEN;
			} else if (k == KeyCodes.ESCAPE) {
				cancel();
				result = EVENT_EATEN;
			} else if (isCharKey(keyCode, mods) || isImeKey(keyCode, mods)) {
				result = EVENT_EATEN;
			}
		}
		return result;
	}

	@Override
	public int onInput(UiNode target, String data, String content, int mods, int time) {
		if (target == hint) {
			Json criteria = Json.createObject();
			criteria.setString("hint", content);
			list.getDataSource().setCriteria(criteria);
		}
		return EVENT_IGNORED;
	}

	protected int pickAndEat(UiNode node) {
		pick((DataField)node);
		return EVENT_EATEN;
	}

	protected void pick(DataField field) {
		owner.setValue(field.getRecord());
		getApplication().back();
	}

	protected void cancel() {
		owner.getApplication().back();
	}

}