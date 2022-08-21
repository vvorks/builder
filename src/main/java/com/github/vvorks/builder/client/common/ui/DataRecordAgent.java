package com.github.vvorks.builder.client.common.ui;

import java.util.Map;

import com.github.vvorks.builder.shared.common.json.Json;

public abstract class DataRecordAgent {

	public abstract String getTypeName();

	public String getTitle(DataRecord rec, String name) {
		return rec.getString(name + "_title");
	}

	public void setTitle(DataRecord rec, String name, Json from) {
		rec.setString(name + "_title", from.getString("_title"));
	}

	public abstract Json getValue(DataRecord rec, String name);

	public abstract void setValue(DataRecord rec, String name, Json from);

	public abstract Json fromParam(Map<String, String> param);

	public abstract Map<String, String> toParam(Json data);

	protected boolean asBoolean(String value) {
		return Boolean.valueOf(value);
	}

	protected int asInt(String value) {
		return Integer.decode(value);
	}

	protected long asLong(String value) {
		return Long.decode(value);
	}

	protected float asFloat(String value) {
		return Float.parseFloat(value);
	}

	protected double asDouble(String value) {
		return Double.parseDouble(value);
	}

	protected String asString(String value) {
		return value;
	}

	protected String toString(boolean value) {
		return String.valueOf(value);
	}

	protected String toString(int value) {
		return String.valueOf(value);
	}

	protected String toString(long value) {
		return String.valueOf(value);
	}

	protected String toString(float value) {
		return String.valueOf(value);
	}

	protected String toString(double value) {
		return String.valueOf(value);
	}

	protected String toString(String value) {
		return value;
	}

}
