package com.github.vvorks.builder.client.common.ui;

import java.util.Objects;

public class UiImage extends UiNode {

	/** イメージURL */
	private String url;

	public UiImage() {
		super();
	}

	public UiImage(String name) {
		super(name);
	}

	protected UiImage(UiImage src) {
		super(src);
		this.url = src.url;
	}

	@Override
	public UiImage copy() {
		return new UiImage(this);
	}

	@Override
	protected DomElement createDomElement(String ns, String tag, UiNode owner) {
		return super.createDomElement(ns, HTML_IMG, owner);
	}

	public String getImageUrl() {
		return this.url;
	}

	public void setImageUrl(String url) {
		if (!Objects.equals(this.url, url)) {
			this.url = url;
			setChanged(CHANGED_CONTENT);
		}
	}

	@Override
	protected void syncContent() {
		getDomElement().setImageUrl(url);
	}

}
