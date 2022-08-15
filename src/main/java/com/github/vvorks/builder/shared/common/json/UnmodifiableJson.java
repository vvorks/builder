package com.github.vvorks.builder.shared.common.json;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

public class UnmodifiableJson extends Json {

	public static Json wrap(Json src) {
		if (src instanceof UnmodifiableJson) {
			return src;
		} else {
			return new UnmodifiableJson(src);
		}
	}

	private final Json json;

	public UnmodifiableJson(Json json) {
		this.json = json;
	}

	@Override
	public Object toObject(Class<?> cls) {
		return json.toObject(cls);
	}

	@Override
	public Type getType() {
		return json.getType();
	}

	@Override
	public boolean getBooleanValue() {
		return json.getBooleanValue();
	}

	@Override
	public int getIntValue() {
		return json.getIntValue();
	}

	@Override
	public long getLongValue() {
		return json.getLongValue();
	}

	@Override
	public float getFloatValue() {
		return json.getFloatValue();
	}

	@Override
	public double getDoubleValue() {
		return json.getDoubleValue();
	}

	@Override
	public BigDecimal getDecimalValue() {
		return json.getDecimalValue();
	}

	@Override
	public Date getDateValue() {
		return json.getDateValue();
	}

	@Override
	public String getStringValue() {
		return json.getStringValue();
	}

	@Override
	public boolean getBooleanValue(boolean defaultValue) {
		return json.getBooleanValue(defaultValue);
	}

	@Override
	public int getIntValue(int defaultValue) {
		return json.getIntValue(defaultValue);
	}

	@Override
	public long getLongValue(long defaultValue) {
		return json.getLongValue(defaultValue);
	}

	@Override
	public float getFloatValue(float defaultValue) {
		return json.getFloatValue(defaultValue);
	}

	@Override
	public double getDoubleValue(double defaultValue) {
		return json.getDoubleValue(defaultValue);
	}

	@Override
	public BigDecimal getDecimalValue(BigDecimal defaultValue) {
		return json.getDecimalValue(defaultValue);
	}

	@Override
	public Date getDateValue(Date defaultValue) {
		return json.getDateValue(defaultValue);
	}

	@Override
	public String getStringValue(String defaultValue) {
		return json.getStringValue(defaultValue);
	}

	@Override
	public Json get(String key) {
		return wrap(json.get(key));
	}

	@Override
	public Json computeIfAbsent(String key, Function<String, Json> creator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Json get(Iterable<String> path) {
		return wrap(json.get(path));
	}

	@Override
	public Type getType(String key) {
		return json.getType(key);
	}

	@Override
	public boolean getBoolean(String key) {
		return json.getBoolean(key);
	}

	@Override
	public int getInt(String key) {
		return json.getInt(key);
	}

	@Override
	public long getLong(String key) {
		return json.getLong(key);
	}

	@Override
	public float getFloat(String key) {
		return json.getFloat(key);
	}

	@Override
	public double getDouble(String key) {
		return json.getDouble(key);
	}

	@Override
	public BigDecimal getDecimal(String key) {
		return json.getDecimal(key);
	}

	@Override
	public Date getDate(String key) {
		return json.getDate(key);
	}

	@Override
	public String getString(String key) {
		return json.getString(key);
	}

	@Override
	public boolean getBoolean(String key, boolean defaultValue) {
		return json.getBoolean(key, defaultValue);
	}

	@Override
	public int getInt(String key, int defaultValue) {
		return json.getInt(key, defaultValue);
	}

	@Override
	public long getLong(String key, long defaultValue) {
		return json.getLong(key, defaultValue);
	}

	@Override
	public float getFloat(String key, float defaultValue) {
		return json.getFloat(key, defaultValue);
	}

	@Override
	public double getDouble(String key, double defaultValue) {
		return json.getDouble(key, defaultValue);
	}

	@Override
	public BigDecimal getDecimal(String key, BigDecimal defaultValue) {
		return json.getDecimal(key, defaultValue);
	}

	@Override
	public Date getDate(String key, Date defaultValue) {
		return json.getDate(key, defaultValue);
	}

	@Override
	public String getString(String key, String defaultValue) {
		return json.getString(key, defaultValue);
	}

	@Override
	public int size() {
		return json.size();
	}

	@Override
	public Json get(int index) {
		return wrap(json.get(index));
	}

	@Override
	public Type getType(int index) {
		return json.getType(index);
	}

	@Override
	public boolean getBoolean(int index) {
		return json.getBoolean(index);
	}

	@Override
	public int getInt(int index) {
		return json.getInt(index);
	}

	@Override
	public long getLong(int index) {
		return json.getLong(index);
	}

	@Override
	public float getFloat(int index) {
		return json.getFloat(index);
	}

	@Override
	public double getDouble(int index) {
		return json.getDouble(index);
	}

	@Override
	public BigDecimal getDecimal(int index) {
		return json.getDecimal(index);
	}

	@Override
	public Date getDate(int index) {
		return json.getDate(index);
	}

	@Override
	public String getString(int index) {
		return json.getString(index);
	}

	@Override
	public boolean getBoolean(int index, boolean defaultValue) {
		return json.getBoolean(index, defaultValue);
	}

	@Override
	public int getInt(int index, int defaultValue) {
		return json.getInt(index, defaultValue);
	}

	@Override
	public long getLong(int index, long defaultValue) {
		return json.getLong(index, defaultValue);
	}

	@Override
	public float getFloat(int index, float defaultValue) {
		return json.getFloat(index, defaultValue);
	}

	@Override
	public double getDouble(int index, double defaultValue) {
		return json.getDouble(index, defaultValue);
	}

	@Override
	public BigDecimal getDecimal(int index, BigDecimal defaultValue) {
		return json.getDecimal(index, defaultValue);
	}

	@Override
	public Date getDate(int index, Date defaultValue) {
		return json.getDate(index, defaultValue);
	}

	@Override
	public String getString(int index, String defaultValue) {
		return json.getString(index, defaultValue);
	}

	@Override
	public Collection<Json> values() {
		Collection<Json> c = json.values();
		if (c instanceof List<?>) {
			List<Json> list = (List<Json>) c;
			for (int i = 0; i < list.size(); i++) {
				list.set(i, wrap(list.get(i)));
			}
			return list;
		} else {
			List<Json> newList = new ArrayList<Json>();
			for (Json j : c) {
				newList.add(wrap(j));
			}
			return newList;
		}
	}

	@Override
	public Set<String> keySet() {
		return json.keySet();
	}

	@Override
	public Set<Entry<String, Json>> entrySet() {
		Set<Entry<String, Json>> set = json.entrySet();
		for (Entry<String, Json> e : set) {
			e.setValue(wrap(e.getValue()));
		}
		return set;
	}

	@Override
	public void remove(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setNull(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBoolean(String key, boolean value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setInt(String key, int value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLong(String key, long value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFloat(String key, float value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setDouble(String key, double value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setDecimal(String key, BigDecimal value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setDate(String key, Date value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setString(String key, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setString(String key, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setStringIfExists(String key, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void set(String key, Json value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Json setNewObject(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Json setNewArray(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setNull(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBoolean(int index, boolean value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setInt(int index, int value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLong(int index, long value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFloat(int index, float value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setDouble(int index, double value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setDecimal(int index, BigDecimal value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setDate(int index, Date value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setString(int index, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setString(int index, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void set(int index, Json value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Json setNewObject(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Json setNewArray(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addNull() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addBoolean(boolean value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addInt(int value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addLong(long value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addFloat(float value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addDouble(double value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addDecimal(BigDecimal value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addDate(Date value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addString(String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(Json value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Json addNewObject() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Json addNewArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getContent() {
		return json.getContent();
	}

	@Override
	public Json merge(Json other) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int hashCode() {
		return json.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return json.equals(obj);
	}

	@Override
	public String toString() {
		return json.toString();
	}

	@Override
	public String toJsonString() {
		return json.toJsonString();
	}

	@Override
	public String toJsonString(boolean humanReadable) {
		return json.toJsonString(humanReadable);
	}

}
