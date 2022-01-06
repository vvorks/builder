package com.github.vvorks.builder.client.common.ui;

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
		setFocus(this);
		showPopup(false);
		return EVENT_EATEN;
	}

	private void showPopup(boolean isEdit) {
		UiApplication app = getApplication();
		app.call(new UiSelectPopup(app, this, isEdit));
	}

}
