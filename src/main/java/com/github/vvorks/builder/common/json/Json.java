package com.github.vvorks.builder.common.json;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.github.vvorks.builder.common.lang.Factory;

public abstract class Json {

	/** JSONデータ種別を示す列挙 */
	public enum Type {
		UNDEFINED, NULL, BOOLEAN, NUMBER, STRING, OBJECT, ARRAY;
	}

	private static final Json NULL_VALUE = createJson(null);

	protected static final boolean DEFAULT_BOOLEAN = false;

	protected static final double DEFAULT_NUMBER = 0.0d;

	protected static final String DEFAULT_STRING = "";

	public static Json createJson(Object arg) {
		return Factory.newInstance(Json.class, arg);
	}

	public static Json createObject() {
		return createJson("{}");
	}

	public static Json createArray() {
		return createJson("[]");
	}

	public static Json valueOf(Jsonizable value) {
		return value != null ? value.toJson() : NULL_VALUE;
	}

	/** POJOへの変換（サポートされない場合あり） */
	public abstract Object toObject(Class<?> cls);

	/** このノードのデータ種別を返す */
	public abstract Type getType();

	/** このノードのBoolean値を返す */
	public boolean getBooleanValue() {
		return getBooleanValue(DEFAULT_BOOLEAN);
	}

	/** このノードのNumber値を返す */
	public double getNumberValue() {
		return getNumberValue(DEFAULT_NUMBER);
	}

	/** このノードのString値を返す */
	public String getStringValue() {
		return getStringValue(DEFAULT_STRING);
	}

	/** このノードのBoolean値を返す */
	public abstract boolean getBooleanValue(boolean defaultValue);

	/** このノードのNumber値を返す */
	public abstract double getNumberValue(double defaultValue);

	/** このノードのString値を返す */
	public abstract String getStringValue(String defaultValue);

	/** 指定したキー値で得られる子ノードを返す */
	public abstract Json get(String key);

	/** 指定したキー値で得られる子ノードのデータ種別を返す */
	public abstract Type getType(String key);

	/** 指定したキー値で得られる子ノードのBoolean値を返す */
	public boolean getBoolean(String key) {
		return getBoolean(key, DEFAULT_BOOLEAN);
	}

	/** 指定したキー値で得られる子ノードのNumber値を返す */
	public double getNumber(String key) {
		return getNumber(key, DEFAULT_NUMBER);
	}

	/** 指定したキー値で得られる子ノードのString値を返す */
	public String getString(String key) {
		return getString(key, DEFAULT_STRING);
	}

	/** 指定したキー値で得られる子ノードのBoolean値を返す */
	public abstract boolean getBoolean(String key, boolean defaultValue);

	/** 指定したキー値で得られる子ノードのNumber値を返す */
	public abstract double getNumber(String key, double defaultValue);

	/** 指定したキー値で得られる子ノードのString値を返す */
	public abstract String getString(String key, String defaultValue);

	/** 配列ノードの子ノード数を返す */
	public abstract int size();

	/** 指定したindex値で得られる子ノードを返す */
	public abstract Json get(int index);

	/** 指定したindex値で得られる子ノードのノード種別を返す */
	public abstract Type getType(int index);

	/** 指定したindex値で得られる子ノードのBoolean値を返す */
	public boolean getBoolean(int index) {
		return getBoolean(index, DEFAULT_BOOLEAN);
	}

	/** 指定したindex値で得られる子ノードのNumber値を返す */
	public double getNumber(int index) {
		return getNumber(index, DEFAULT_NUMBER);
	}

	/** 指定したindex値で得られる子ノードのNumber値を返す */
	public String getString(int index) {
		return getString(index, DEFAULT_STRING);
	}

	/** 指定したindex値で得られる子ノードのBoolean値を返す */
	public abstract boolean getBoolean(int index, boolean defaultValue);

	/** 指定したindex値で得られる子ノードのNumber値を返す */
	public abstract double getNumber(int index, double defaultValue);

	/** 指定したindex値で得られる子ノードのNumber値を返す */
	public abstract String getString(int index, String defaultValue);

	/** Json文字列を返す */
	public abstract String toJsonString();

	/** 子ノードの値一式を返す */
	public abstract Collection<Json> values();

	/** 子ノードのキー一式を返す */
	public abstract Set<String> keySet();

	/** 子ノードのエントリー一式を返す */
	public abstract Set<Map.Entry<String,Json>> entrySet();

	public abstract void remove(String key);

	public abstract void setNull(String key);

	public abstract void setBoolean(String key, boolean value);

	public abstract void setNumber(String key, double value);

	public abstract void setString(String key, String value);

	public void setString(String key, Object value) {
		if (value == null) {
			setNull(key);
		} else {
			setString(key, value.toString());
		}
	}

	public abstract void set(String key, Json value);

	public abstract Json setNewObject(String key);

	public abstract Json setNewArray(String key);

	public abstract void setNull(int index);

	public abstract void setBoolean(int index, boolean value);

	public abstract void setNumber(int index, double value);

	public abstract void setString(int index, String value);

	public void setString(int index, Object value) {
		if (value == null) {
			setNull(index);
		} else {
			setString(index, value.toString());
		}
	}

	public abstract void set(int index, Json value);

	public abstract Json setNewObject(int index);

	public abstract Json setNewArray(int index);

	public void addNull() {
		setNull(size());
	}

	public void addBoolean(boolean value) {
		setBoolean(size(), value);
	}

	public void addNumber(double value) {
		setNumber(size(), value);
	}

	public void addString(String value) {
		setString(size(), value);
	}

	public void add(Json value) {
		set(size(), value);
	}

	public Json addNewObject() {
		return setNewObject(size());
	}

	public Json addNewArray() {
		return setNewArray(size());
	}

	public String toString() {
		return toJsonString();
	}

}
