package com.github.vvorks.builder.common.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class CacheMap<K, V> extends LinkedHashMap<K, V> {

	private int limit;

	public CacheMap() {
		this(256);
	}

	public CacheMap(int limit) {
		super(16, 0.75F, true);
		this.limit = limit;
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return size() > limit;
	}

	@Override
	public boolean equals(Object other) {
		return super.equals(other);
	}

}
