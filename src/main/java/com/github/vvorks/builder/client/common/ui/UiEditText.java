package com.github.vvorks.builder.client.common.ui;

public class UiEditText extends UiText {

	/** 編集状態 */
	private enum EditState {
		/** 非編集中 */					NONE,
		/** 半編集中（矢印は使用しない） */	HALF,
		/** 全編集中（矢印を使用する） */		FULL;
	}

	private transient EditState editState;

	private transient String saveText;

	public UiEditText(String name) {
		super(name);
		setFocusable(true);
		editState = EditState.NONE;
	}

	protected UiEditText(UiEditText src) {
		super(src);
		editState = EditState.NONE;
	}

	@Override
	public UiEditText copy() {
		return new UiEditText(this);
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
		int k = keyCode|mods;
		if (isCharKey(keyCode, mods) || k == KeyCodes.BACKSPACE || isImeKey(keyCode, mods)) {
			editState = EditState.HALF;
			saveText = getText();
			setText("");
			doc.startEditing(this, "", k == KeyCodes.BACKSPACE);
			result = EVENT_AFFECTED;
		} else if (isEditKey(keyCode, mods)) {
			editState = EditState.FULL;
			saveText = getText();
			setText("");
			doc.startEditing(this, saveText, false);
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

	public void startHalfEditing() {
		DomDocument doc = getDocument();
		editState = EditState.HALF;
		saveText = getText();
		setText("");
		doc.startEditing(this, "", false);
	}

}
