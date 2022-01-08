package com.github.vvorks.builder.client.common.ui;

import java.util.function.Function;

public class UiButton extends UiText {

	/** アクション */
	private Function<UiNode, Integer> action;

	public UiButton(String name) {
		super(name);
		setFocusable(true);
	}

	protected UiButton(UiButton src) {
		super(src);
		this.action = src.action;
	}

	@Override
	public UiButton copy() {
		return new UiButton(this);
	}

	/**
	 * ボタン押下時のアクションを設定する
	 *
	 * @param action アクション
	 */
	public void setAction(Function<UiNode, Integer> action) {
		this.action = action;
	}

	@Override
	public int onMouseClick(UiNode target, int x, int y, int mods, int time) {
		if (action == null) {
			return super.onMouseClick(target, x, y, mods, time);
		}
		return action.apply(this);
	}

}
