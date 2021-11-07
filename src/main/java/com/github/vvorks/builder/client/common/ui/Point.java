package com.github.vvorks.builder.client.common.ui;

import java.util.Objects;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.json.Jsonizable;
import com.github.vvorks.builder.common.logging.Logger;

public class Point implements Jsonizable {

	public static final Class<?> THIS = Point.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	protected int x;

	protected int y;

	protected Point() {
		set(0, 0);
	}

	public Point(int x, int y) {
		set(x, y);
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
		json.setNumber("x", x);
		json.setNumber("y", x);
		return json;
	}

}
