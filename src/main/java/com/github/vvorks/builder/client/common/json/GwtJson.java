package com.github.vvorks.builder.client.common.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.util.SimpleEntry;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class GwtJson extends Json {

	private final JSONValue nativeValue;

	public GwtJson(Object obj) {
		if (obj == null) {
			nativeValue = JSONNull.getInstance();
		} else if (obj instanceof JSONValue) {
			nativeValue = (JSONValue) obj;
		} else if (obj instanceof GwtJson) {
			nativeValue = ((GwtJson) obj).getNativeValue();
		} else if (obj instanceof Json) {
			nativeValue = parse(((Json) obj).toJsonString());
		} else if (obj instanceof String) {
			nativeValue = parse((String) obj);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	private static JSONValue parse(String jsonString) {
		JSONValue value;
		if ("{}".equals(jsonString)) {
			value = new JSONObject();
		} else if ("[]".equals(jsonString)) {
			value = new JSONArray();
		} else {
			value = JSONParser.parseStrict(jsonString);
		}
		return value;
	}

	private static Type getTypeOf(JSONValue value) {
		if (value == null) {
			return Type.UNDEFINED;
		} else if (value.isNull() != null) {
			return Type.NULL;
		} else if (value.isBoolean() != null) {
			return Type.BOOLEAN;
		} else if (value.isNumber() != null) {
			return Type.NUMBER;
		} else if (value.isString() != null) {
			return Type.STRING;
		} else if (value.isObject() != null) {
			return Type.OBJECT;
		} else if (value.isArray() != null) {
			return Type.ARRAY;
		} else {
			return Type.UNDEFINED;
		}
	}

	private static boolean asBoolean(JSONValue value, boolean defaultValue) {
		switch (getTypeOf(value)) {
		case BOOLEAN:
			return value.isBoolean().booleanValue();
		case NUMBER:
			return value.isNumber().doubleValue() != 0.0;
		case STRING:
			return Boolean.valueOf(value.isString().stringValue());
		default:
			return defaultValue;
		}
	}

	private static double asNumber(JSONValue value, double defaultValue) {
		switch (getTypeOf(value)) {
		case BOOLEAN:
			return value.isBoolean().booleanValue() ? 1.0 : 0.0;
		case NUMBER:
			return value.isNumber().doubleValue();
		case STRING:
			try {
				return Double.valueOf(value.isString().stringValue());
			} catch (NumberFormatException err) {
				return defaultValue;
			}
		default:
			return defaultValue;
		}
	}

	private static String asString(JSONValue value, String defaultValue) {
		switch (getTypeOf(value)) {
		case BOOLEAN:
			return String.valueOf(value.isBoolean().booleanValue());
		case NUMBER:
			return String.valueOf(value.isNumber().doubleValue());
		case STRING:
			return value.isString().stringValue();
		case NULL:
			return null;
		default:
			return defaultValue;
		}
	}

	private static JSONObject asObject(JSONValue value) {
		if (getTypeOf(value) != Type.OBJECT) {
			throw new IllegalStateException();
		}
		return value.isObject();
	}

	private static JSONArray asArray(JSONValue value) {
		if (getTypeOf(value) != Type.ARRAY) {
			throw new IllegalStateException();
		}
		return value.isArray();
	}

	private static Json wrap(JSONValue value) {
		return new GwtJson(value);
	}

	private JSONValue toNativeValue(Json value) {
		if (value == null) {
			return null;
		} else if (value instanceof GwtJson) {
			return ((GwtJson)value).getNativeValue();
		} else {
			return parse(value.toJsonString());
		}
	}

	private JSONValue getNativeValue() {
		return nativeValue;
	}

	@Override
	public Object toObject(Class<?> cls) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Type getType() {
		return getTypeOf(nativeValue);
	}

	@Override
	public boolean getBooleanValue(boolean defaultValue) {
		return asBoolean(nativeValue, defaultValue);
	}

	@Override
	public double getNumberValue(double defaultValue) {
		return asNumber(nativeValue, defaultValue);
	}

	@Override
	public String getStringValue(String defaultValue) {
		return asString(nativeValue, defaultValue);
	}

	@Override
	public Json get(String key) {
		return wrap(asObject(nativeValue).get(key));
	}

	@Override
	public Type getType(String key) {
		return getTypeOf(asObject(nativeValue).get(key));
	}

	@Override
	public boolean getBoolean(String key, boolean defaultValue) {
		return asBoolean(asObject(nativeValue).get(key), defaultValue);
	}

	@Override
	public double getNumber(String key, double defaultValue) {
		return asNumber(asObject(nativeValue).get(key), defaultValue);
	}

	@Override
	public String getString(String key, String defaultValue) {
		return asString(asObject(nativeValue).get(key), defaultValue);
	}

	@Override
	public int size() {
		return asArray(nativeValue).size();
	}

	@Override
	public Json get(int index) {
		return wrap(asArray(nativeValue).get(index));
	}

	@Override
	public Type getType(int index) {
		return getTypeOf(asArray(nativeValue).get(index));
	}

	@Override
	public boolean getBoolean(int index, boolean defaultValue) {
		return asBoolean(asArray(nativeValue).get(index), defaultValue);
	}

	@Override
	public double getNumber(int index, double defaultValue) {
		return asNumber(asArray(nativeValue).get(index), defaultValue);
	}

	@Override
	public String getString(int index, String defaultValue) {
		return asString(asArray(nativeValue).get(index), defaultValue);
	}

	@Override
	public String toJsonString() {
		return nativeValue.toString();
	}

	@Override
	public Collection<Json> values() {
		Type type = getTypeOf(nativeValue);
		List<Json> result;
		if (type == Type.OBJECT) {
			result = new ArrayList<>();
			JSONObject obj = asObject(nativeValue);
			for (String key : obj.keySet()) {
				result.add(wrap(obj.get(key)));
			}
		} else if (type == Type.ARRAY) {
			result = new ArrayList<>();
			JSONArray array = asArray(nativeValue);
			for (int i = 0; i < array.size(); i++) {
				result.add(wrap(array.get(i)));
			}
		} else {
			result = Collections.emptyList();
		}
		return result;
	}

	@Override
	public Set<String> keySet() {
		Type type = getTypeOf(nativeValue);
		Set<String> result;
		if (type == Type.OBJECT) {
			result = asObject(nativeValue).keySet();
		} else {
			result = Collections.emptySet();
		}
		return result;
	}

	@Override
	public Set<Map.Entry<String,Json>> entrySet() {
		Type type = getTypeOf(nativeValue);
		Set<Map.Entry<String,Json>> result;
		if (type == Type.OBJECT) {
			result = new LinkedHashSet<>();
			JSONObject obj = asObject(nativeValue);
			for (String key : obj.keySet()) {
				result.add(new SimpleEntry<>(key, wrap(obj.get(key))));
			}
		} else {
			result = Collections.emptySet();
		}
		return result;
	}

	@Override
	public void remove(String key) {
		asObject(nativeValue).put(key, null);
	}

	@Override
	public void setNull(String key) {
		asObject(nativeValue).put(key, JSONNull.getInstance());
	}

	@Override
	public void setBoolean(String key, boolean value) {
		asObject(nativeValue).put(key, JSONBoolean.getInstance(value));
	}

	@Override
	public void setNumber(String key, double value) {
		asObject(nativeValue).put(key, new JSONNumber(value));
	}

	@Override
	public void setString(String key, String value) {
		asObject(nativeValue).put(key, new JSONString(value));
	}

	@Override
	public void set(String key, Json value) {
		asObject(nativeValue).put(key, toNativeValue(value));
	}

	@Override
	public Json setNewObject(String key) {
		JSONValue value = new JSONObject();
		asObject(nativeValue).put(key, value);
		return wrap(value);
	}

	@Override
	public Json setNewArray(String key) {
		JSONValue value = new JSONArray();
		asObject(nativeValue).put(key, value);
		return wrap(value);
	}

	@Override
	public void setNull(int index) {
		asArray(nativeValue).set(index, JSONNull.getInstance());
	}

	@Override
	public void setBoolean(int index, boolean value) {
		asArray(nativeValue).set(index, JSONBoolean.getInstance(value));
	}

	@Override
	public void setNumber(int index, double value) {
		asArray(nativeValue).set(index, new JSONNumber(value));
	}

	@Override
	public void setString(int index, String value) {
		asArray(nativeValue).set(index, new JSONString(value));
	}

	@Override
	public void set(int index, Json value) {
		asArray(nativeValue).set(index, toNativeValue(value));
	}

	@Override
	public Json setNewObject(int index) {
		JSONValue value = new JSONObject();
		asArray(nativeValue).set(index, value);
		return wrap(value);
	}

	@Override
	public Json setNewArray(int index) {
		JSONValue value = new JSONArray();
		asArray(nativeValue).set(index, value);
		return wrap(value);
	}

}
