package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.json.Jsonizable;

public class Bounds implements Jsonizable {

	private final Length left;
	private final Length top;
	private final Length right;
	private final Length bottom;
	private final Length width;
	private final Length height;

	public Bounds(Length left, Length top, Length right, Length bottom, Length width, Length height) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.width = width;
		this.height = height;
	}

	public Length getLeft() {
		return left;
	}

	public Length getTop() {
		return top;
	}

	public Length getRight() {
		return right;
	}

	public Length getBottom() {
		return bottom;
	}

	public Length getWidth() {
		return width;
	}

	public Length getHeight() {
		return height;
	}

	@Override
	public Json toJson() {
		Json json = Json.createObject();
		json.set("left", Json.valueOf(left));
		json.set("top", Json.valueOf(top));
		json.set("right", Json.valueOf(right));
		json.set("bottom", Json.valueOf(bottom));
		json.set("width", Json.valueOf(width));
		json.set("height", Json.valueOf(height));
		return json;
	}

}
