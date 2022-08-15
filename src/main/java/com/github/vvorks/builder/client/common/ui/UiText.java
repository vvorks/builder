package com.github.vvorks.builder.client.common.ui;

import java.util.Objects;

import com.github.vvorks.builder.client.ClientSettings;

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

	public String getDisplayText() {
		if (text != null) {
			return text;
		}
		//TODO "title"は指定可能であるべき？
		String resourceText = getResource().getString("title", null);
		if (resourceText != null) {
			//TODO テキストに値をキャッシュすべきか？
			return resourceText;
		}
		if (ClientSettings.DEBUG) {
			return getName();
		}
		return "";
	}

	public void setText(String text) {
		if (!Objects.equals(this.text, text)) {
			this.text = text;
			setChanged(CHANGED_CONTENT);
		}
	}

	@Override
	protected void syncContent() {
		getDomElement().setInnerText(getDisplayText());
	}

}
