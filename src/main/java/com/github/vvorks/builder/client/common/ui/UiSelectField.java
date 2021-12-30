package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.client.ui.BuilderUiApplication;

public class UiSelectField extends UiNode implements DataField {

	/** 選択状態 */
	private enum SelectState {
		/** 非選択中 */	NONE,
		/** 　選択中 */	PICK,
	}

	private transient DataRecord rec;

	private transient DataRecordAgent agent;

	private transient String title;

	private transient SelectState selectState;

	public UiSelectField(String name, DataRecordAgent agent) {
		super(name);
		setFocusable(true);
		this.agent = agent;
		selectState = SelectState.NONE;
	}

	protected UiSelectField(UiSelectField src) {
		super(src);
		this.agent = src.agent;
		this.title = src.title;
		selectState = SelectState.NONE;
	}

	@Override
	public UiSelectField copy() {
		return new UiSelectField(this);
	}

	@Override
	public void setRecord(DataRecord rec) {
		this.rec = rec;
		this.title = agent.getTitle(rec, getName());
		setChanged(CHANGED_CONTENT);
	}

	@Override
	protected void syncContent() {
		getDomElement().setInnerText(title);
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
