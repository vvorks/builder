package com.github.vvorks.builder.shared.common.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class CacheMap<K, V> extends LinkedHashMap<K, V> {

	private int limit;

	public CacheMap() {
		this(256, true);
	}

	public CacheMap(int limit, boolean accessOrder) {
		super(16, 0.75F, accessOrder);
		this.limit = limit;
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return size() > limit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(limit);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!super.equals(obj)) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		} else {
			@SuppressWarnings("unchecked")
			CacheMap<K, V> other = (CacheMap<K, V>) obj;
			return limit == other.limit;
		}
	}

}
