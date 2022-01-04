package com.github.vvorks.builder.client.common.ui;

public class UiField extends UiNode {

	public UiField(String name) {
		super(name);
	}

	public UiField(UiField src) {
		super(src);
	}

	@Override
	public UiField copy() {
		return new UiField(this);
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
	public static boolean isCharKey(int keyCode, int mods) {
		if ((mods & KeyCodes.MOD_AC) != 0) {
			return false;
		}
		return	(KeyCodes.KEY_A <= keyCode && keyCode <= KeyCodes.KEY_Z) ||
				(KeyCodes.KEY_0 <= keyCode && keyCode <= KeyCodes.KEY_9) ||
				(KeyCodes.NUM_0 <= keyCode && keyCode <= KeyCodes.NUM_9) ||
				(keyCode == KeyCodes.SPACE                             ) ||
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
	 * BackSpaceキーか否かを判定する
	 */
	public static boolean isBsKey(int keyCode, int mods) {
		if ((mods & KeyCodes.MOD_AC) != 0) {
			return false;
		}
		return keyCode == KeyCodes.BACKSPACE;
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
	public static boolean isImeKey(int keyCode, int mods) {
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
	public static boolean isEditKey(int keyCode, int mods) {
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
	public static boolean isArrowKey(int keyCode, int mods) {
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
	public static boolean isEnterKey(int keyCode, int mods) {
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
	public static boolean isEscKey(int keyCode, int mods) {
		if ((mods & KeyCodes.MOD_ACS) != 0) {
			return false;
		}
		return keyCode == KeyCodes.ESCAPE;
	}

}
