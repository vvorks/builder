package com.github.vvorks.builder.client.common.ui;

import java.util.Objects;

import com.github.vvorks.builder.shared.common.json.Json;
import com.github.vvorks.builder.shared.common.json.Jsonizable;
import com.github.vvorks.builder.shared.common.lang.Strings;

public class Point implements Jsonizable {

	protected int x;

	protected int y;

	protected Point() {
		this.x = 0;
		this.y = 0;
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point other) {
		this.x = other.x;
		this.y = other.y;
	}

	public int getX() {
		return x;
	}

	public Point setX(int x) {
		this.x = x;
		return this;
	}

	public int getY() {
		return y;
	}

	public Point setY(int y) {
		this.y = y;
		return this;
	}

	public Point set(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Point move(int dx, int dy) {
		x += dx;
		y += dy;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
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
			return equals((Point) obj);
		}
	}

	private boolean equals(Point other) {
		return	x == other.x &&
				y == other.y;
	}

	@Override
	public Json toJson() {
		Json json = Json.createObject();
		json.setInt("x", x);
		json.setInt("y", x);
		return json;
	}

	@Override
	public String toString() {
		return Strings.sprintf("%d, %d", x, y);
	}

}
