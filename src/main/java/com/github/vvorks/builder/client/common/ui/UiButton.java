package com.github.vvorks.builder.client.common.ui;

import java.util.Objects;

public class UiButton extends UiNode {

	/** テキスト */
	private String text;

	public UiButton(String name) {
		super(name);
		setFocusable(true);
	}

	protected UiButton(UiButton src) {
		super(src);
		this.text = src.text;
	}

	@Override
	public UiButton copy() {
		return new UiButton(this);
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

}
