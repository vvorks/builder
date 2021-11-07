package com.github.vvorks.builder.client.common.ui;

import java.util.Objects;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.logging.Logger;

public class Rect extends Point {

	public static final Class<?> THIS = Rect.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	protected int width;

	protected int height;

	public Rect() {
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
	}

	public Rect(int x, int y, int width, int height) {
		set(x, y, width, height);
	}

	public Rect(Rect other) {
		set(other);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getLeft() {
		return x;
	}

	public int getRight() {
		return x + width;
	}

	public int getTop() {
		return y;
	}

	public int getBottom() {
		return y + height;
	}

	public int getCenterX() {
		return x + width / 2;
	}

	public int getCenterY() {
		return y + height / 2;
	}

	public Rect set(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width  = width;
		this.height = height;
		return this;
	}

	public Rect set(Rect other) {
		this.x = other.x;
		this.y = other.y;
		this.width  = other.width;
		this.height = other.height;
		return this;
	}

	public Rect setFromTwoPoints(int x1, int y1, int x2, int y2) {
		this.x = Math.min(x1, x2);
		this.y = Math.min(y1, y2);
		this.width = Math.abs(x2 - x1);
		this.height = Math.abs(y2 - y1);
		return this;
	}

	public Rect setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Rect setSize(int width, int height) {
		this.width  = width;
		this.height = height;
		return this;
	}

	@Override
	public Rect move(int dx, int dy) {
		super.move(dx, dy);
		return this;
	}

	public Rect inflate(int ix, int iy) {
		x -= ix;
		y -= iy;
		width += ix * 2;
		height += iy * 2;
		return this;
	}

	public Rect resize(int dLeft, int dTop, int dRight, int dBottom) {
		int x1 = x + dLeft;
		int y1 = y + dTop;
		int x2 = x + width + dRight;
		int y2 = y + height + dBottom;
		return setFromTwoPoints(x1, y1, x2, y2);
	}

	public boolean contains(int x, int y) {
		return	getLeft() <= x && x < getRight()
		&&		getTop()  <= y && y < getBottom();

	}

	public boolean contains(Rect other) {
		return	getLeft() <= other.getLeft() && other.getRight()  <= getRight()
		&&		getTop()  <= other.getTop()  && other.getBottom() <= getBottom();
	}

	public boolean separates(Rect other) {
		return	other.getRight()  <= getLeft() || getRight()  <= other.getLeft() ||
				other.getBottom() <= getTop()  || getBottom() <= other.getTop()   ;
	}

	public boolean intersects(Rect other) {
		return !separates(other);
	}

	public double distance(Point pt) {
		int dx;
		if (pt.x < getLeft()) {
			dx = pt.x - getLeft();
		} else if (pt.x > getRight()) {
			dx = pt.x - getRight();
		} else {
			dx = 0;
		}
		int dy;
		if (pt.y < getTop()) {
			dy = pt.y - getTop();
		} else if (pt.y > getBottom()) {
			dy = pt.y - getBottom();
		} else {
			dy = 0;
		}
		return sqrt(square(dx) + square(dy));
	}

	private static double square(double x) {
		return x * x;
	}

	private static double sqrt(double x) {
		return Math.sqrt(x);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, width, height);
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
			return equals((Rect) obj);
		}
	}

	private boolean equals(Rect other) {
		return	x == other.x &&
				y == other.y &&
				width == other.width &&
				height == other.height;
	}

	@Override
	public Json toJson() {
		Json json = super.toJson();
		json.setNumber("width", width);
		json.setNumber("height", height);
		return json;
	}


}
