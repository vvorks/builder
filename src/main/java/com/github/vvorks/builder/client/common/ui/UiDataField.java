package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.logging.Logger;

public class UiDataField extends UiNode implements DataField {

	public static final Logger LOGGER = Logger.createLogger(UiDataField.class);

	/** 編集状態 */
	private enum EditState {
		/** 非編集中 */					NONE,
		/** 半編集中（矢印は使用しない） */	HALF,
		/** 全編集中（矢印を使用する） */		FULL;
	}

	private transient DataRecord rec;

	private transient String text;

	private transient EditState editState;

	private transient String saveText;

	public UiDataField(String name) {
		super(name);
		setFocusable(true);
		editState = EditState.NONE;
	}

	protected UiDataField(UiDataField src) {
		super(src);
		editState = EditState.NONE;
	}

	@Override
	public UiDataField copy() {
		return new UiDataField(this);
	}

	@Override
	public void setRecord(DataRecord rec) {
		this.rec = rec;
		setText(rec.getString(getName()));
	}

	private String getText() {
		return text;
	}

	private void setText(String text) {
		this.text = text;
		rec.setString(getName(), text); //とりあえず文字列として設定
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
			saveText = getText();
			setText("");
			doc.startEditing(this, "");
			result = EVENT_AFFECTED;
		} else if (isEditKey(keyCode, mods)) {
			editState = EditState.FULL;
			saveText = getText();
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

	/**
	 * 文字入力キーか否かを判定する
	 *
	 * @param keyCode
	 * 		キーコード
	 * @param mods
	 * 		キー修飾子
	 * @return
	 * 		文字入力を行うキーの場合、真
	 */
	private boolean isCharKey(int keyCode, int mods) {
		if ((mods & KeyCodes.MOD_AC) != 0) {
			return false;
		}
		return	(KeyCodes.KEY_A <= keyCode && keyCode <= KeyCodes.KEY_Z) ||
				(KeyCodes.KEY_0 <= keyCode && keyCode <= KeyCodes.KEY_9) ||
				(KeyCodes.NUM_0 <= keyCode && keyCode <= KeyCodes.NUM_9) ||
				(keyCode == KeyCodes.MINUS                             ) ||
				(keyCode == KeyCodes.CARET                             ) ||
				(keyCode == KeyCodes.YEN                               ) ||
				(keyCode == KeyCodes.AT                                ) ||
				(keyCode == KeyCodes.LBRACKET                          ) ||
				(keyCode == KeyCodes.SEMICOLON                         ) ||
				(keyCode == KeyCodes.COLON                             ) ||
				(keyCode == KeyCodes.RBRACKET                          ) ||
				(keyCode == KeyCodes.COMMA                             ) ||
				(keyCode == KeyCodes.PERIOD                            ) ||
				(keyCode == KeyCodes.DIVISION                          ) ||
				(keyCode == KeyCodes.BACKSLASH                         ) ||
				(keyCode == KeyCodes.NUM_MULTIPLY                      ) ||
				(keyCode == KeyCodes.NUM_PLUS                          ) ||
				(keyCode == KeyCodes.NUM_MINUS                         ) ||
				(keyCode == KeyCodes.NUM_PERIOD                        ) ||
				(keyCode == KeyCodes.NUM_DIVISION                      )  ;
	}

	/**
	 * IME入力キーか否かを判定する
	 *
	 * @param keyCode
	 * 		キーコード
	 * @param mods
	 * 		キー修飾子
	 * @return
	 * 		IME入力キーの場合、真
	 */
	private boolean isImeKey(int keyCode, int mods) {
		return keyCode == KeyCodes.WIN_IME;
	}

	/**
	 * 編集開始キーか否かを判定する
	 *
	 * @param keyCode
	 * 		キーコード
	 * @param mods
	 * 		キー修飾子
	 * @return
	 * 		全編集開始キーの場合、真
	 */
	protected boolean isEditKey(int keyCode, int mods) {
		if ((mods & KeyCodes.MOD_ACS) != 0) {
			return false;
		}
		return keyCode == KeyCodes.F2 || keyCode == KeyCodes.ENTER;
	}
	/**
	 * カーソル移動キーか否かを判定する
	 *
	 * @param keyCode
	 * 		キーコード
	 * @param mods
	 * 		キー修飾子
	 * @return
	 * 		カーソル移動キーの場合、真
	 */
	protected boolean isArrowKey(int keyCode, int mods) {
		return	(keyCode == KeyCodes.UP    ) || (keyCode == KeyCodes.DOWN    ) ||
				(keyCode == KeyCodes.LEFT  ) || (keyCode == KeyCodes.RIGHT   ) ||
				(keyCode == KeyCodes.PAGEUP) || (keyCode == KeyCodes.PAGEDOWN) ||
				(keyCode == KeyCodes.HOME  ) || (keyCode == KeyCodes.END     ) ||
				(keyCode == KeyCodes.TAB   )  ;
	}

	/**
	 * 決定キーか否かを判定する
	 *
	 * @param keyCode
	 * 		キーコード
	 * @param mods
	 * 		キー修飾子
	 * @return
	 * 		決定キーの場合、真
	 */
	protected boolean isEnterKey(int keyCode, int mods) {
		if ((mods & KeyCodes.MOD_ACS) != 0) {
			return false;
		}
		return keyCode == KeyCodes.ENTER;
	}

	/**
	 * 戻るキーか否かを判定する
	 *
	 * @param keyCode
	 * 		キーコード
	 * @param mods
	 * 		キー修飾子
	 * @return
	 * 		戻るキーの場合、真
	 */
	protected boolean isEscKey(int keyCode, int mods) {
		if ((mods & KeyCodes.MOD_ACS) != 0) {
			return false;
		}
		return keyCode == KeyCodes.ESCAPE;
	}

}
