package com.github.vvorks.builder.client.common.ui;

import java.util.Objects;

public class UiText extends UiNode {

	/** ラベルテキスト */
	private String text;

	public UiText() {
		super();
	}

	public UiText(String name) {
		super(name);
	}

	public UiText(UiText src) {
		super(src);
		this.text = src.text;
	}

	@Override
	public UiText copy() {
		return new UiText(this);
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
