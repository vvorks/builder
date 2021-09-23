package com.github.vvorks.builder.client.common.json;

import com.github.vvorks.builder.common.json.AbstractJsonContext;
import com.github.vvorks.builder.common.json.JsonType;
import com.github.vvorks.builder.common.json.JsonValue;
import com.github.vvorks.builder.common.lang.Asserts;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class GwtJsonContext extends AbstractJsonContext<JSONValue> {

	protected GwtJsonContext() {
		enter(new JSONObject());
	}

	public GwtJsonContext(Object obj) {
		JSONValue jsonValue;
		if (obj instanceof String) {
			jsonValue = parse((String) obj);
		} else if (obj instanceof JsonValue) {
			jsonValue = fromValue((JsonValue) obj);
		} else {
			throw new IllegalArgumentException();
		}
		enter(jsonValue);
	}

	protected GwtJsonContext(String jsonString) {
		enter(parse(jsonString));
	}

	protected GwtJsonContext(JsonValue value) {
		enter(fromValue(value));
	}

	public static JsonType getJsonTypeOf(JSONValue value) {
		if (value == null) {
			return JsonType.UNDEFINED;
		} else if (value.isNull() != null) {
			return JsonType.NULL;
		} else if (value.isBoolean() != null) {
			return JsonType.BOOLEAN;
		} else if (value.isNumber() != null) {
			return JsonType.NUMBER;
		} else if (value.isString() != null) {
			return JsonType.STRING;
		} else if (value.isObject() != null) {
			return JsonType.OBJECT;
		} else if (value.isArray() != null) {
			return JsonType.ARRAY;
		} else {
			return JsonType.UNDEFINED;
		}
	}

	@Override
	public JsonType getCurrentType() {
		return getJsonTypeOf(peek());
	}

	@Override
	public void enterObject(String key) {
		JSONObject parent = getParentObject(key);
		JSONValue child = parent.get(key);
		if (child == null || child.isObject() == null) {
			child = new JSONObject();
			parent.put(key, child);
		}
		enter(child);
	}

	@Override
	public void enterObject(int index) {
		JSONArray parent = getParentArray(index, true);
		JSONValue child = parent.get(index);
		if (child == null || child.isObject() == null) {
			child = new JSONObject();
			parent.set(index, child);
		}
		enter(child);
	}

	@Override
	public void enterArray(String key) {
		JSONObject parent = getParentObject(key);
		JSONValue child = parent.get(key);
		if (child == null || child.isArray() == null) {
			child = new JSONArray();
			parent.put(key, child);
		}
		enter(child);
	}

	@Override
	public void enterArray(int index) {
		JSONArray parent = getParentArray(index, true);
		JSONValue child = parent.get(index);
		if (child == null || child.isArray() == null) {
			child = new JSONArray();
			parent.set(index, child);
		}
		enter(child);
	}

	@Override
	public int length() {
		JSONValue value = peek();
		if (getJsonTypeOf(value) != JsonType.ARRAY) {
			throw new IllegalStateException();
		}
		JSONArray parent = value.isArray();
		return parent.size();
	}

	@Override
	protected JSONValue parse(String jsonString) {
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

	@Override
	protected JSONValue getNativeValue(String key) {
		JSONObject parent = getParentObject(key);
		return parent.get(key);
	}

	@Override
	protected JSONValue getNativeValue(int index) {
		JSONArray parent = getParentArray(index, false);
		return parent.get(index);
	}

	@Override
	protected void setNativeValue(String key, JSONValue value) {
		JSONObject parent = getParentObject(key);
		parent.put(key, value);
	}

	@Override
	protected void setNativeValue(int index, JSONValue value) {
		JSONArray parent = getParentArray(index, true);
		parent.set(index, value);
	}

	@Override
	protected boolean toBoolean(JSONValue value, boolean defaultValue) {
		boolean result;
		switch (getJsonTypeOf(value)) {
		case BOOLEAN:
			result = value.isBoolean().booleanValue();
			break;
		case NUMBER:
			result = (value.isNumber().doubleValue() != 0.0);
			break;
		case STRING:
			result = Boolean.valueOf(value.isString().stringValue());
			break;
		case OBJECT:
		case ARRAY:
		case NULL:
		case UNDEFINED:
		default:
			result = defaultValue;
			break;
		}
		return result;
	}

	@Override
	protected int toInt(JSONValue value, int defaultValue) {
		int result;
		switch (getJsonTypeOf(value)) {
		case BOOLEAN:
			result = value.isBoolean().booleanValue() ? 1 : 0;
			break;
		case NUMBER:
			result = (int) value.isNumber().doubleValue();
			break;
		case STRING:
			try {
				result = Integer.decode(value.isString().stringValue());
			} catch (NumberFormatException err) {
				result = defaultValue;
			}
			break;
		case OBJECT:
		case ARRAY:
		case NULL:
		case UNDEFINED:
		default:
			result = defaultValue;
			break;
		}
		return result;
	}

	@Override
	protected double toDouble(JSONValue value, double defaultValue) {
		double result;
		switch (getJsonTypeOf(value)) {
		case BOOLEAN:
			result = value.isBoolean().booleanValue() ? 1 : 0;
			break;
		case NUMBER:
			result = value.isNumber().doubleValue();
			break;
		case STRING:
			try {
				result = Double.valueOf(value.isString().stringValue());
			} catch (NumberFormatException err) {
				result = defaultValue;
			}
			break;
		case OBJECT:
		case ARRAY:
		case NULL:
		case UNDEFINED:
		default:
			result = defaultValue;
			break;
		}
		return result;
	}

	@Override
	protected String toString(JSONValue value, String defaultValue) {
		String result;
		switch (getJsonTypeOf(value)) {
		case BOOLEAN:
			result = String.valueOf(value.isBoolean().booleanValue());
			break;
		case NUMBER:
			result = String.valueOf(value.isNumber().doubleValue());
			break;
		case STRING:
			result = value.isString().stringValue();
			break;
		case OBJECT:
		case ARRAY:
		case NULL:
		case UNDEFINED:
		default:
			result = defaultValue;
			break;
		}
		return result;
	}

	@Override
	protected JsonValue toValue(JSONValue value, JsonValue defaultValue) {
		JsonValue result;
		switch (getJsonTypeOf(value)) {
		case BOOLEAN:
		case NUMBER:
		case STRING:
		case OBJECT:
		case ARRAY:
		case NULL:
			result = new GwtJsonValue(value);
			break;
		case UNDEFINED:
		default:
			result = defaultValue;
			break;
		}
		return result;
	}

	@Override
	protected JSONValue fromNull() {
		return JSONNull.getInstance();
	}

	@Override
	protected JSONValue fromBoolean(boolean value) {
		return JSONBoolean.getInstance(value);
	}

	@Override
	protected JSONValue fromInt(int value) {
		return new JSONNumber(value);
	}

	@Override
	protected JSONValue fromDouble(double value) {
		return new JSONNumber(value);
	}

	@Override
	protected JSONValue fromString(String value) {
		return new JSONString(value);
	}

	@Override
	protected JSONValue fromValue(JsonValue value) {
		JSONValue jsonValue;
		if (value == null) {
			jsonValue = JSONNull.getInstance();
		} else if (value instanceof GwtJsonValue) {
			jsonValue = ((GwtJsonValue) value).getValue();
		} else {
			jsonValue = parse(value.toJsonString());
		}
		return jsonValue;
	}

	private JSONObject getParentObject(String key) {
		JSONValue value = peek();
		Asserts.assume(getJsonTypeOf(value) == JsonType.OBJECT);
		JSONObject parent = value.isObject();
		Asserts.requireNotEmpty(key);
		return parent;
	}

	private JSONArray getParentArray(int index, boolean isSet) {
		JSONValue value = peek();
		Asserts.assume(getJsonTypeOf(value) == JsonType.ARRAY);
		JSONArray parent = value.isArray();
		Asserts.require(0 <= index && index < parent.size() + (isSet ? 1 : 0));
		return parent;
	}

}
