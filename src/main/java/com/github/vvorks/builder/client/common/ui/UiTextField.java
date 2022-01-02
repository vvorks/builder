package com.github.vvorks.builder.client.common.ui;

public class UiTextField extends UiField {

	/** 編集状態 */
	private enum EditState {
		/** 非編集中 */					NONE,
		/** 半編集中（矢印は使用しない） */	HALF,
		/** 全編集中（矢印を使用する） */		FULL;
	}

	private transient String text;

	private transient EditState editState;

	private transient String saveText;

	public UiTextField(String name) {
		super(name);
		setFocusable(true);
		editState = EditState.NONE;
	}

	protected UiTextField(UiTextField src) {
		super(src);
		editState = EditState.NONE;
	}

	@Override
	public UiTextField copy() {
		return new UiTextField(this);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		setChanged(CHANGED_CONTENT);
	}

	@Override
	protected void syncContent() {
		getDomElement().setInnerText(text);
	}

	@Override
	public void onFocus(UiNode target, boolean gained, UiNode other) {
		if (this == target) {
			if (editState != EditState.NONE && !gained) {
				DomDocument doc = getDocument();
				setText(doc.endEditing(this));
				editState = EditState.NONE;
			}
		}
		super.onFocus(target, gained, other);
	}

	@Override
	public int onKeyDown(UiNode target, int keyCode, int charCode, int mods, int time) {
		int result;
		if (editState == EditState.FULL || editState == EditState.HALF) {
			result = onKeyDownInEdit(target, keyCode, charCode, mods, time);
		} else {
			result = onKeyDownInNone(target, keyCode, charCode, mods, time);
		}
		return result;
	}

	private int onKeyDownInNone(UiNode target, int keyCode, int charCode, int mods, int time) {
		DomDocument doc = getDocument();
		int result;
		if (isCharKey(keyCode, mods) || isImeKey(keyCode, mods)) {
			editState = EditState.HALF;
			saveText = text;
			setText("");
			doc.startEditing(this, "");
			result = EVENT_AFFECTED;
		} else if (isEditKey(keyCode, mods)) {
			editState = EditState.FULL;
			saveText = text;
			setText("");
			doc.startEditing(this, saveText);
			result = EVENT_EATEN;
		} else {
			result = EVENT_IGNORED;
		}
		return result;
	}

	private int onKeyDownInEdit(UiNode target, int keyCode, int charCode, int mods, int time) {
		DomDocument doc = getDocument();
		int result;
		if (isArrowKey(keyCode, mods)) {
			if (editState == EditState.HALF) {
				setText(doc.endEditing(this));
				editState = EditState.NONE;
				result = EVENT_AFFECTED;
			} else {
				result = EVENT_THROUGH;
			}
		} else if (isEnterKey(keyCode, mods)) {
			setText(doc.endEditing(this));
			editState = EditState.NONE;
			result = EVENT_EATEN;
			//ENTER後に下移動
			getApplication().onKeyDown(target, KeyCodes.DOWN, 0, 0, time);
		} else if (isEscKey(keyCode, mods)) {
			doc.endEditing(this);
			setText(saveText);
			editState = EditState.NONE;
			result = EVENT_EATEN;
		} else {
			result = EVENT_IGNORED;
		}
		return result;
	}

}
