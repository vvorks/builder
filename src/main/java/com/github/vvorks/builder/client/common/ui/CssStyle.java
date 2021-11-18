package com.github.vvorks.builder.client.common.ui;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.json.Jsonizable;
import com.github.vvorks.builder.common.logging.Logger;

public class CssStyle implements Jsonizable {

	public static final Class<?> THIS = CssStyle.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	private final Map<String, Object> map;

	public Length getBorderWidth() {
		return (Length) map.get("border-width");
	}

	public Object getProperty(String name) {
		return map.get(name);
	}

	public Set<Map.Entry<String, Object>> getProperties() {
		return Collections.unmodifiableSet(map.entrySet());
	}

	@Override
	public Json toJson() {
		Json json = Json.createObject();
		for (Map.Entry<String, Object> e : map.entrySet()) {
			json.setString(e.getKey(), String.valueOf(e.getValue()));
		}
		return json;
	}

	public CssStyle() {
		map = new LinkedHashMap<>();
	}

	public static class Builder {

		private final CssStyle style;

		public Builder() {
			style = new CssStyle();
		}

		public Builder visible(boolean b) {
			return property("visibility", b ? "visible" : "hidden");
		}

		public Builder left(Length length) {
			return property("left", length);
		}

		public Builder top(Length length) {
			return property("top", length);
		}

		public Builder right(Length length) {
			return property("right", length);
		}

		public Builder bottom(Length length) {
			return property("bottom", length);
		}

		public Builder width(Length length) {
			return property("width", length);
		}

		public Builder height(Length length) {
			return property("height", length);
		}

		public Builder borderWidth(Length length) {
			return property("border-width", length);
		}

		public Builder property(String name, Object value) {
			if (value != null) {
				style.map.put(name, value);
			} else {
				style.map.remove(name);
			}
			return this;
		}

		public CssStyle build() {
			return style;
		}

	}

}
