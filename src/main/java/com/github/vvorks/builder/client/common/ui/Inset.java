package com.github.vvorks.builder.client.common.ui;

import java.util.Objects;

import com.github.vvorks.builder.shared.common.json.Json;
import com.github.vvorks.builder.shared.common.json.Jsonizable;
import com.github.vvorks.builder.shared.common.lang.Strings;

public class Inset implements Jsonizable {

	protected int left;

	protected int top;

	protected int right;

	protected int bottom;

	public Inset() {
		this.left = 0;
		this.top = 0;
		this.right = 0;
		this.bottom = 0;
	}

	public Inset(int left, int top, int right, int bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	public Inset(Inset other) {
		this.left = other.left;
		this.top = other.top;
		this.right = other.right;
		this.bottom = other.bottom;
	}

	public int getLeft() {
		return left;
	}

	public int getTop() {
		return top;
	}

	public int getRight() {
		return right;
	}

	public int getBottom() {
		return bottom;
	}

	@Override
	public int hashCode() {
		return Objects.hash(left, top, right, bottom);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		} else {
			return equals((Inset) obj);
		}
	}

	private boolean equals(Inset other) {
		return	left == other.left &&
				top == other.top &&
				right == other.right &&
				bottom == other.bottom;
	}

	@Override
	public Json toJson() {
		Json json = Json.createObject();
		json.setInt("left", left);
		json.setInt("top", top);
		json.setInt("right", right);
		json.setInt("bottom", bottom);
		return json;
	}

	@Override
	public String toString() {
		return Strings.sprintf("%d, %d, %d, %d", left, top, right, bottom);
	}

}
