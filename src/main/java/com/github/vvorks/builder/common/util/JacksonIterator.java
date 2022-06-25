package com.github.vvorks.builder.common.util;

import java.util.Collection;
import java.util.Iterator;

public class JacksonIterator<E> implements Iterator<E> {

	private final Iterator<E> itr;

	public JacksonIterator(Collection<E> c) {
		itr = c.iterator();
	}

	@Override
	public boolean hasNext() {
		return itr.hasNext();
	}

	@Override
	public E next() {
		return itr.hasNext() ? itr.next() : null;
	}

}
