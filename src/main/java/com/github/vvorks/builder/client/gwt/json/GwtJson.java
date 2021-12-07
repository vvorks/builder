package com.github.vvorks.builder.client.gwt.json;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.util.SimpleEntry;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class GwtJson extends Json {

	private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat(DATE_PATTERN);

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
		} else if (obj instanceof Number) {
			nativeValue = new JSONNumber(((Number)obj).doubleValue());
		} else if (obj instanceof Boolean) {
			nativeValue = JSONBoolean.getInstance(((Boolean)obj).booleanValue());
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

	private static int asInt(JSONValue value, int defaultValue) {
		switch (getTypeOf(value)) {
		case BOOLEAN:
			return value.isBoolean().booleanValue() ? 1 : 0;
		case NUMBER:
			return (int) value.isNumber().doubleValue();
		case STRING:
			try {
				return Integer.decode(value.isString().stringValue());
			} catch (NumberFormatException err) {
				return defaultValue;
			}
		default:
			return defaultValue;
		}
	}

	private static long asLong(JSONValue value, long defaultValue) {
		switch (getTypeOf(value)) {
		case BOOLEAN:
			return value.isBoolean().booleanValue() ? 1L : 0L;
		case NUMBER:
			return (long) value.isNumber().doubleValue();
		case STRING:
			try {
				return Long.decode(value.isString().stringValue());
			} catch (NumberFormatException err) {
				return defaultValue;
			}
		default:
			return defaultValue;
		}
	}

	private static float asFloat(JSONValue value, float defaultValue) {
		switch (getTypeOf(value)) {
		case BOOLEAN:
			return value.isBoolean().booleanValue() ? 1.0f : 0.0f;
		case NUMBER:
			return (float) value.isNumber().doubleValue();
		case STRING:
			try {
				return Float.valueOf(value.isString().stringValue());
			} catch (NumberFormatException err) {
				return defaultValue;
			}
		default:
			return defaultValue;
		}
	}

	private static double asDouble(JSONValue value, double defaultValue) {
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

	private static BigDecimal asDecimal(JSONValue value, BigDecimal defaultValue) {
		switch (getTypeOf(value)) {
		case BOOLEAN:
			return BigDecimal.valueOf(value.isBoolean().booleanValue() ? 1.0 : 0.0);
		case NUMBER:
			return BigDecimal.valueOf(value.isNumber().doubleValue());
		case STRING:
			try {
				return new BigDecimal(value.isString().stringValue());
			} catch (NumberFormatException err) {
				return defaultValue;
			}
		default:
			return defaultValue;
		}
	}

	private static Date asDate(JSONValue value, Date defaultValue) {
		switch (getTypeOf(value)) {
		case NUMBER:
			return new Date((long) value.isNumber().doubleValue());
		case STRING:
			try {
				return DATE_FORMAT.parse(value.isString().stringValue());
			} catch (NumberFormatException err) {
				return defaultValue;
			}
		case BOOLEAN:
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

	private static JSONValue toNativeValue(boolean value) {
		return JSONBoolean.getInstance(value);
	}

	private static JSONValue toNativeValue(int value) {
		return new JSONNumber(value);
	}

	private static JSONValue toNativeValue(long value) {
		return new JSONString(String.valueOf(value));
	}

	private static JSONValue toNativeValue(float value) {
		return new JSONNumber(value);
	}

	private static JSONValue toNativeValue(double value) {
		return new JSONNumber(value);
	}

	private static JSONValue toNativeValue(BigDecimal value) {
		return new JSONString(value.toString());
	}

	private static JSONValue toNativeValue(Date value) {
		return new JSONString(DATE_FORMAT.format(value));
	}

	private static JSONValue toNativeValue(String value) {
		return new JSONString(value);
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
	public int getIntValue(int defaultValue) {
		return asInt(nativeValue, defaultValue);
	}

	@Override
	public long getLongValue(long defaultValue) {
		return asLong(nativeValue, defaultValue);
	}

	@Override
	public float getFloatValue(float defaultValue) {
		return asFloat(nativeValue, defaultValue);
	}

	@Override
	public double getDoubleValue(double defaultValue) {
		return asDouble(nativeValue, defaultValue);
	}

	@Override
	public BigDecimal getDecimalValue(BigDecimal defaultValue) {
		return asDecimal(nativeValue, defaultValue);
	}

	@Override
	public Date getDateValue(Date defaultValue) {
		return asDate(nativeValue, defaultValue);
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
	public int getInt(String key, int defaultValue) {
		return asInt(asObject(nativeValue).get(key), defaultValue);
	}

	@Override
	public long getLong(String key, long defaultValue) {
		return asLong(asObject(nativeValue).get(key), defaultValue);
	}

	@Override
	public float getFloat(String key, float defaultValue) {
		return asFloat(asObject(nativeValue).get(key), defaultValue);
	}

	@Override
	public double getDouble(String key, double defaultValue) {
		return asDouble(asObject(nativeValue).get(key), defaultValue);
	}

	@Override
	public BigDecimal getDecimal(String key, BigDecimal defaultValue) {
		return asDecimal(asObject(nativeValue).get(key), defaultValue);
	}

	@Override
	public Date getDate(String key, Date defaultValue) {
		return asDate(asObject(nativeValue).get(key), defaultValue);
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
	public int getInt(int index, int defaultValue) {
		return asInt(asArray(nativeValue).get(index), defaultValue);
	}

	@Override
	public long getLong(int index, long defaultValue) {
		return asLong(asArray(nativeValue).get(index), defaultValue);
	}

	@Override
	public float getFloat(int index, float defaultValue) {
		return asFloat(asArray(nativeValue).get(index), defaultValue);
	}

	@Override
	public double getDouble(int index, double defaultValue) {
		return asDouble(asArray(nativeValue).get(index), defaultValue);
	}

	@Override
	public BigDecimal getDecimal(int index, BigDecimal defaultValue) {
		return asDecimal(asArray(nativeValue).get(index), defaultValue);
	}

	@Override
	public Date getDate(int index, Date defaultValue) {
		return asDate(asArray(nativeValue).get(index), defaultValue);
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
		asObject(nativeValue).put(key, toNativeValue(value));
	}

	@Override
	public void setInt(String key, int value) {
		asObject(nativeValue).put(key, toNativeValue(value));
	}

	@Override
	public void setLong(String key, long value) {
		asObject(nativeValue).put(key, toNativeValue(value));
	}

	@Override
	public void setFloat(String key, float value) {
		asObject(nativeValue).put(key, toNativeValue(value));
	}

	@Override
	public void setDouble(String key, double value) {
		asObject(nativeValue).put(key, toNativeValue(value));
	}

	@Override
	public void setDecimal(String key, BigDecimal value) {
		if (value == null) {
			setNull(key);
		} else {
			asObject(nativeValue).put(key, toNativeValue(value));
		}
	}

	@Override
	public void setDate(String key, Date value) {
		if (value == null) {
			setNull(key);
		} else {
			asObject(nativeValue).put(key, toNativeValue(value));
		}
	}

	@Override
	public void setString(String key, String value) {
		if (value == null) {
			setNull(key);
		} else {
			asObject(nativeValue).put(key, toNativeValue(value));
		}
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
		asArray(nativeValue).set(index, toNativeValue(value));
	}

	@Override
	public void setInt(int index, int value) {
		asArray(nativeValue).set(index, toNativeValue(value));
	}

	@Override
	public void setLong(int index, long value) {
		asArray(nativeValue).set(index, toNativeValue(value));
	}

	@Override
	public void setFloat(int index, float value) {
		asArray(nativeValue).set(index, toNativeValue(value));
	}

	@Override
	public void setDouble(int index, double value) {
		asArray(nativeValue).set(index, toNativeValue(value));
	}

	@Override
	public void setDecimal(int index, BigDecimal value) {
		if (value == null) {
			setNull(index);
		} else {
			asArray(nativeValue).set(index, toNativeValue(value));
		}
	}

	@Override
	public void setDate(int index, Date value) {
		if (value == null) {
			setNull(index);
		} else {
			asArray(nativeValue).set(index, toNativeValue(value));
		}
	}

	@Override
	public void setString(int index, String value) {
		if (value == null) {
			setNull(index);
		} else {
			asArray(nativeValue).set(index, toNativeValue(value));
		}
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
