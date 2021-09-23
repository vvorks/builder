package com.github.vvorks.builder.common.json;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class AbstractJsonContext<T> implements JsonContext {

	private Deque<T> stack = new ArrayDeque<>();

	@Override
	public boolean getBoolean(String key) {
		return toBoolean(getNativeValue(key), false);
	}

	@Override
	public boolean getBoolean(String key, boolean defaultValue) {
		return toBoolean(getNativeValue(key), defaultValue);
	}

	@Override
	public int getInt(String key) {
		return toInt(getNativeValue(key), 0);
	}

	@Override
	public int getInt(String key, int defaultValue) {
		return toInt(getNativeValue(key), defaultValue);
	}

	@Override
	public double getDouble(String key) {
		return toDouble(getNativeValue(key), 0.0d);
	}

	@Override
	public double getDouble(String key, double defaultValue) {
		return toDouble(getNativeValue(key), defaultValue);
	}

	@Override
	public String getString(String key) {
		return toString(getNativeValue(key), "");
	}

	@Override
	public String getString(String key, String defaultValue) {
		return toString(getNativeValue(key), defaultValue);
	}

	@Override
	public JsonValue getValue(String key) {
		return toValue(getNativeValue(key), null);
	}

	@Override
	public JsonValue getValue(String key, JsonValue defaultValue) {
		return toValue(getNativeValue(key), defaultValue);
	}

	@Override
	public boolean getBoolean(int index) {
		return toBoolean(getNativeValue(index), false);
	}

	@Override
	public boolean getBoolean(int index, boolean defaultValue) {
		return toBoolean(getNativeValue(index), defaultValue);
	}

	@Override
	public int getInt(int index) {
		return toInt(getNativeValue(index), 0);
	}

	@Override
	public int getInt(int index, int defaultValue) {
		return toInt(getNativeValue(index), defaultValue);
	}

	@Override
	public double getDouble(int index) {
		return toDouble(getNativeValue(index), 0.0d);
	}

	@Override
	public double getDouble(int index, double defaultValue) {
		return toDouble(getNativeValue(index), defaultValue);
	}

	@Override
	public String getString(int index) {
		return toString(getNativeValue(index), "");
	}

	@Override
	public String getString(int index, String defaultValue) {
		return toString(getNativeValue(index), defaultValue);
	}

	@Override
	public JsonValue getValue(int index) {
		return toValue(getNativeValue(index), null);
	}

	@Override
	public JsonValue getValue(int index, JsonValue defaultValue) {
		return toValue(getNativeValue(index), defaultValue);
	}

	@Override
	public void setNull(String key) {
		setNativeValue(key, fromNull());
	}

	@Override
	public void setBoolean(String key, boolean value) {
		setNativeValue(key, fromBoolean(value));
	}

	@Override
	public void setInt(String key, int value) {
		setNativeValue(key, fromInt(value));
	}

	@Override
	public void setDouble(String key, double value) {
		setNativeValue(key, fromDouble(value));
	}

	@Override
	public void setString(String key, String value) {
		setNativeValue(key, fromString(value));
	}

	@Override
	public void setString(String key, Object obj) {
		if (obj == null) {
			setNativeValue(key, fromNull());
		} else {
			setNativeValue(key, fromString(obj.toString()));
		}
	}

	@Override
	public void setValue(String key, JsonValue value) {
		setNativeValue(key, fromValue(value));
	}

	@Override
	public void setValue(String key, String jsonString) {
		setNativeValue(key, parse(jsonString));
	}

	@Override
	public void setNull(int index) {
		setNativeValue(index, fromNull());
	}

	@Override
	public void setBoolean(int index, boolean value) {
		setNativeValue(index, fromBoolean(value));
	}

	@Override
	public void setInt(int index, int value) {
		setNativeValue(index, fromInt(value));
	}

	@Override
	public void setDouble(int index, double value) {
		setNativeValue(index, fromDouble(value));
	}

	@Override
	public void setString(int index, String value) {
		setNativeValue(index, fromString(value));
	}

	@Override
	public void setString(int index, Object obj) {
		if (obj == null) {
			setNativeValue(index, fromNull());
		} else {
			setNativeValue(index, fromString(obj.toString()));
		}
	}

	@Override
	public void setValue(int index, JsonValue value) {
		setNativeValue(index, fromValue(value));
	}

	@Override
	public void setValue(int index, String jsonString) {
		setNativeValue(index, parse(jsonString));
	}

	protected void enter(T newFrame) {
		stack.push(newFrame);
	}

	@Override
	public void leave() {
		if (stack.isEmpty()) {
			throw new IllegalStateException();
		}
		stack.pop();
	}

	protected T peek() {
		if (stack.isEmpty()) {
			throw new IllegalStateException();
		}
		return stack.peek();
	}

	@Override
	public JsonValue toJsonValue() {
		return toValue(stack.getLast(), null);
	}

	@Override
	public String toJsonString() {
		return toJsonValue().toJsonString();
	}

	@Override
	public String toString() {
		return toJsonString();
	}

	protected abstract T parse(String jsonString);

	protected abstract T getNativeValue(String key);

	protected abstract T getNativeValue(int index);

	protected abstract void setNativeValue(String key, T value);

	protected abstract void setNativeValue(int index, T value);

	protected abstract boolean toBoolean(T value, boolean defaultValue);

	protected abstract int toInt(T value, int defaultValue);

	protected abstract double toDouble(T value, double defaultValue);

	protected abstract String toString(T value, String defaultValue);

	protected abstract JsonValue toValue(T value, JsonValue defaultValue);

	protected abstract T fromNull();

	protected abstract T fromBoolean(boolean value);

	protected abstract T fromInt(int value);

	protected abstract T fromDouble(double value);

	protected abstract T fromString(String value);

	protected abstract T fromValue(JsonValue value);

}
