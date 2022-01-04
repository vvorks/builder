package com.github.vvorks.builder.client.common.ui;

import java.util.Objects;
import java.util.function.Function;

public class UiButton extends UiNode {

	/** テキスト */
	private String text;

	/** アクション */
	private Function<UiNode, Integer> action;

	public UiButton(String name) {
		super(name);
		setFocusable(true);
	}

	protected UiButton(UiButton src) {
		super(src);
		this.text = src.text;
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

	public String getText() {
		return text;
	}

	public void setText(String newText) {
		if (!Objects.equals(this.text, newText)) {
			this.text = newText;
			setChanged(CHANGED_CONTENT);
		}
	}

	@Override
	protected void syncContent() {
		getDomElement().setInnerText(text);
	}

	@Override
	public int onMouseClick(UiNode target, int x, int y, int mods, int time) {
		if (action == null) {
			return super.onMouseClick(target, x, y, mods, time);
		}
		return action.apply(this);
	}

}
