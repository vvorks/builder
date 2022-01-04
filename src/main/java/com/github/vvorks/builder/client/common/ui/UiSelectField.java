package com.github.vvorks.builder.client.common.ui;

import java.util.HashSet;
import java.util.Set;

import com.github.vvorks.builder.client.ui.BuilderUiApplication;
import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Iterables;

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

	private transient DataRecord rec;

	private transient DataRecordAgent agent;

	private transient String title;

	private transient UiInnerText label;

	private transient UiImage image;

	public UiSelectField(String name, DataRecordAgent agent) {
		super(name);
		setFocusable(true);
		this.agent = agent;
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
	public int onKeyDown(UiNode target, int keyCode, int charCode, int mods, int time) {
		int result;
		int k = keyCode|mods;
		if (isCharKey(keyCode, mods) || k == KeyCodes.BACKSPACE || isImeKey(keyCode, mods)) {
			showPopup(true);
			result = EVENT_AFFECTED;
		} else if (isEditKey(keyCode, mods) || k == (KeyCodes.MOD_ALT|KeyCodes.DOWN)) {
			showPopup(false);
			result = EVENT_AFFECTED;
		} else {
			result = EVENT_IGNORED;
		}
		if ((result & EVENT_AFFECTED) != 0 && keyCode == KeyCodes.ENTER) {
			result |= EVENT_CONSUMED;
		}
		return result;
	}

	@Override
	public int onMouseClick(UiNode target, int x, int y, int mods, int time) {
		showPopup(false);
		return EVENT_EATEN;
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
					pickup((DataField)target);
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

		protected int onSelected(UiNode node) {
			pickup((DataField)node);
			return EVENT_CONSUMED;
		}

		protected void pickup(DataField field) {
			owner.setValue(field.getRecord());
			getApplication().back();
		}

		protected void cancel() {
			owner.getApplication().back();
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
