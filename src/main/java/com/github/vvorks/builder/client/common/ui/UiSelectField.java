package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.client.ui.BuilderUiApplication;

public class UiSelectField extends UiNode implements DataField {

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
	public void setRecord(DataRecord rec) {
		this.rec = rec;
		title = agent.getTitle(rec, getName());
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
		if (isCharKey(keyCode, mods) || isImeKey(keyCode, mods) || isEditKey(keyCode, mods)) {
			selectState = SelectState.PICK;
			showPopup();
			result = EVENT_EATEN;
		} else {
			result = EVENT_IGNORED;
		}
		return result;
	}

	private void showPopup() {
		UiApplication app = getApplication();
		app.call(new Test(app));
	}

	private static class Test extends UiPage {
		public Test(UiApplication app) {
			super("test", app);
		}
		@Override
		protected void initialize() {
			final double NA = UiNodeBuilder.NA;
			UiNodeBuilder b = new UiNodeBuilder(this, "px");
			b.enter(new UiButton("group"));
				b.style(BuilderUiApplication.BASIC);
				b.locate(NA, NA, NA, NA, 200, 40);
				b.action(node -> {node.getApplication().back();return EVENT_EATEN;});
			b.leave();
		}
	}

}
