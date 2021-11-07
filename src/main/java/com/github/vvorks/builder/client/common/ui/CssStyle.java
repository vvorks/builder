package com.github.vvorks.builder.client.common.ui;

import java.util.LinkedHashMap;
import java.util.Map;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.json.Jsonizable;
import com.github.vvorks.builder.common.logging.Logger;

public class CssStyle implements Jsonizable {

	public static final Class<?> THIS = CssStyle.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	private final Map<String, String> map;

	@Override
	public String toString() {
		if (map.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> e : map.entrySet()) {
			sb.append(";");
			sb.append(e.getKey());
			sb.append(":");
			sb.append(e.getValue());
		}
		return sb.substring(1);
	}

	@Override
	public Json toJson() {
		Json json = Json.createObject();
		for (Map.Entry<String, String> e : map.entrySet()) {
			json.setString(e.getKey(), e.getValue());
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
			property("visibility", b ? "visible" : "hidden");
			return this;
		}

		public Builder left(Length length) {
			property("left", length);
			return this;
		}

		public Builder top(Length length) {
			property("top", length);
			return this;
		}

		public Builder right(Length length) {
			property("right", length);
			return this;
		}

		public Builder bottom(Length length) {
			property("bottom", length);
			return this;
		}

		public Builder width(Length length) {
			property("width", length);
			return this;
		}

		public Builder height(Length length) {
			property("height", length);
			return this;
		}

		public Builder borderWidth(Length length) {
			property("border-width", length);
			return this;
		}

		public void transform(String value) {
			if (value != null) {
				add("transform", value);
			}
		}

		public Builder property(String name, Object value) {
			if (value != null) {
				add(name, value.toString());
			}
			return this;
		}

		private void add(String key, String value) {
			style.map.put(key, value);
		}

		public CssStyle build() {
			return style;
		}

	}

}
