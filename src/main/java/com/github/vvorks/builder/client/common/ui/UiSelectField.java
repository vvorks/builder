package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.client.ui.BuilderUiApplication;
import com.github.vvorks.builder.common.json.Json;

public class UiSelectField extends UiField implements DataField {

	private static class UiInnerText extends UiLabel {

		public UiInnerText(String name) {
			super(name);
		}

		public UiInnerText(UiInnerText src) {
			super(src);
		}

		@Override
		public UiInnerText copy() {
			return new UiInnerText(this);
		}

		@Override
		protected void syncElementStyle(CssStyle.Builder b) {
			super.syncElementStyle(b);
			b.property("border", "0");
		}

	}

	/** 選択状態 */
	private enum SelectState {
		/** 非選択中 */	NONE,
		/** 　選択中 */	PICK,
	}

	private transient DataRecord rec;

	private transient DataRecordAgent agent;

	private transient String title;

	private transient SelectState selectState;

	private transient UiInnerText label;

	private transient UiImage image;

	public UiSelectField(String name, DataRecordAgent agent) {
		super(name);
		setFocusable(true);
		this.agent = agent;
		selectState = SelectState.NONE;
		label = (UiInnerText) appendChild(new UiInnerText("inner"));
		image = (UiImage) appendChild(new UiImage());
		Length arrowSize = Length.pxOf(16);
		label.setBounds(Length.ZERO, Length.ZERO, arrowSize, Length.ZERO, null, null);
		image.setBounds(null, null, Length.ZERO, null, arrowSize, arrowSize);
		image.setImageUrl("image/arrow-down.png");
	}

	protected UiSelectField(UiSelectField src) {
		super(src);
		this.rec = src.rec;
		this.agent = src.agent;
		this.title = src.title;
		this.selectState = SelectState.NONE;
		this.label = (UiInnerText) getFirstChild();
		this.image = (UiImage) this.label.getNextSibling();
	}

	@Override
	public UiSelectField copy() {
		return new UiSelectField(this);
	}

	@Override
	public void setStyle(UiStyle style) {
		super.setStyle(style);
		label.setStyle(style);
		image.setStyle(style);
	}

	@Override
	public DataRecord getRecord() {
		return rec;
	}

	@Override
	public void setRecord(DataRecord rec) {
		this.rec = rec;
		String name = getName();
		title = agent.getTitle(rec, name);
		label.setText(title);
	}

	public void setValue(DataRecord candidate) {
		String name = getName();
		agent.setValue(rec, name, candidate);
		agent.setTitle(rec, name, candidate);
		title = agent.getTitle(rec, name);
		label.setText(title);
	}

	@Override
	public void onFocus(UiNode target, boolean gained, UiNode other) {
		if (this == target) {
			if (selectState != SelectState.NONE && !gained) {
				//TODO PICKキャンセル
				selectState = SelectState.NONE;
			}
		}
		super.onFocus(target, gained, other);
	}

	@Override
	public int onKeyDown(UiNode target, int keyCode, int charCode, int mods, int time) {
		int result;
		if (isCharKey(keyCode, mods) || isBsKey(keyCode, mods) || isImeKey(keyCode, mods)) {
			selectState = SelectState.PICK;
			showPopup(true);
			result = EVENT_AFFECTED;
		} else if (isEditKey(keyCode, mods)) {
			selectState = SelectState.PICK;
			showPopup(false);
			result = EVENT_AFFECTED;
		} else {
			result = EVENT_IGNORED;
		}
		return result;
	}

	private void showPopup(boolean isEdit) {
		UiApplication app = getApplication();
		app.call(new Popup(app, this, isEdit));
	}

	private static class Popup extends UiPage {

		private static final int DEFAULT_PER_PAGE = 1 + 10;

		private final UiSelectField owner;

		private boolean isEdit;

		private int unitHeight;

		private Rect popupRect;

		private UiTextField hint;

		private UiVerticalList list;

		private boolean hintUnder;

		public Popup(UiApplication app, UiSelectField owner, boolean isEdit) {
			super("test", app);
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
			popupRect.move(32, 0); //仮
		}

		@Override
		protected void initialize() {
			final double NA = UiNodeBuilder.NA;
			UiApplication app = getApplication();
			UiNodeBuilder b = new UiNodeBuilder(this, "px");
			b.enter(new UiGroup("group"));
				b.style(BuilderUiApplication.NOBORDER);
				b.locate(popupRect);
				int width = popupRect.getWidth();
				int listHeight = popupRect.getHeight() - unitHeight;
				int hintTop;
				int listTop;
				if (hintUnder) {
					hintTop = listHeight;
					listTop = 0;
				} else {
					hintTop = 0;
					listTop = unitHeight;
				}
				b.enter(hint = new HintField("hint"));
					b.style(BuilderUiApplication.BASIC);
					b.locate(0, hintTop, NA, NA, width, unitHeight);
				b.leave();
				b.enter(list = new UiVerticalList("list"));
					b.style(BuilderUiApplication.NOBORDER);
					b.source(owner.getDataSource());
					b.locate(0, listTop, NA, NA, width, listHeight);
					b.loop(false);
					b.flushSoon(false);
					b.enter(new UiButtonField("_title"));
						b.style(BuilderUiApplication.BASIC);
						b.locate(0, 0, NA, NA, width, unitHeight);
						b.action((n) -> onSelected(n));
					b.leave();
				b.leave();
			b.leave();
			app.setFocus(hint);
			if (isEdit) {
				hint.startHalfEditing();
			}
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

		protected int onSelected(UiNode node) {
			DataRecord candidate = ((DataField)node).getRecord();
			owner.setValue(candidate);
			owner.getApplication().back();
			return EVENT_CONSUMED;
		}

	}

	private static class HintField extends UiTextField {

		public HintField(String name) {
			super(name);
		}

		public HintField(HintField src) {
			super(src);
		}

		public HintField copy() {
			return new HintField(this);
		}

	}

}
