package com.github.vvorks.builder.server.common.handlebars;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.github.jknack.handlebars.ValueResolver;
import com.github.vvorks.builder.common.util.SimpleEntry;

public class GlobalResolver implements ValueResolver {

	private final SimpleEntry<String, Object> entry;

	public GlobalResolver(String name, Object value) {
		entry = new SimpleEntry<>(name, value);
	}

	@Override
	public Object resolve(Object context, String name) {
		return entry.getKey().equals(name) ? entry.getValue() : UNRESOLVED;
	}

	@Override
	public Object resolve(Object context) {
		return UNRESOLVED;
	}

	@Override
	public Set<Map.Entry<String, Object>> propertySet(Object context) {
		return Collections.singleton(entry);
	}

}
