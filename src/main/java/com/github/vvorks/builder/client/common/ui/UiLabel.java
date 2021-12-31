package com.github.vvorks.builder.client.common.ui;

import java.util.Objects;

public class UiLabel extends UiNode {

	/** ラベルテキスト */
	private String text;

	public UiLabel() {
		super();
	}

	public UiLabel(String name) {
		super(name);
	}

	public UiLabel(UiLabel src) {
		super(src);
		this.text = src.text;
	}

	@Override
	public UiLabel copy() {
		return new UiLabel(this);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (!Objects.equals(this.text, text)) {
			this.text = text;
			setChanged(CHANGED_CONTENT);
		}
	}

	@Override
	protected void syncContent() {
		getDomElement().setInnerText(text);
	}

}
