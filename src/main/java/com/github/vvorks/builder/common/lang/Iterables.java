package com.github.vvorks.builder.common.lang;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class Iterables {

	private Iterables() {
	}

	public static <T> void addAll(Collection<T> list, Iterable<T> itb) {
		for (T e : itb) {
			list.add(e);
		}
	}

	public static <T> T getFirst(Iterable<T> itb, T defaultValue) {
		Iterator<T> itr = itb.iterator();
		return itr.hasNext() ? itr.next() : defaultValue;
	}

	public static <T> T getLast(Iterable<T> itb, T defaultValue) {
		if (itb instanceof List) {
			List<T> list = (List<T>) itb;
			int n = list.size();
			return n > 0 ? list.get(n - 1) : defaultValue;
		} else {
			T last = defaultValue;
			for (T e : itb) {
				last = e;
			}
			return last;
		}
	}

	public static <T> boolean exists(Iterable<T> itb, Predicate<T> func) {
		for (T t : itb) {
			if (func.test(t)) {
				return true;
			}
		}
		return false;
	}

	public static <T> boolean isEmpty(Iterable<T> itr) {
		return !itr.iterator().hasNext();
	}

	public static <T> Iterable<T> filter(Iterable<T> itb, Predicate<T> cond) {
		return () -> new FilterIterator<T>(itb.iterator(), cond);
	}

	public static <T> Iterable<T> concat(List<Iterable<T>> list) {
		return () -> new ConcatIterator<T>(list);
	}

	private static class FilterIterator<T> implements Iterator<T> {

		private final Iterator<T> source;

		private final Predicate<T> cond;

		private T next;

		private boolean hasNext;

		public FilterIterator(Iterator<T> source, Predicate<T> cond) {
			this.source = source;
			this.cond = cond;
			hasNext = true;
			foreward();
		}

		@Override
		public boolean hasNext() {
			return hasNext;
		}

		@Override
		public T next() {
			if (!hasNext) {
				throw new NoSuchElementException();
			}
			T result = next;
			foreward();
			return result;
		}

		private void foreward() {
			if (hasNext) {
				while (source.hasNext()) {
					T x = source.next();
					if (cond.test(x)) {
						next = x;
						return;
					}
				}
				hasNext = false;
			}
		}

	}

	private static class ConcatIterator<T> implements Iterator<T> {

		private List<Iterable<T>> list;

		private int index;

		private Iterator<T> itr;

		public ConcatIterator(List<Iterable<T>> list) {
			Asserts.requireNotNull(list);
			this.list = list;
			this.index = 0;
			this.itr = getNextIterator();
		}

		@Override
		public boolean hasNext() {
			while (itr != null && !itr.hasNext()) {
				itr = getNextIterator();
			}
			return itr != null;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return itr.next();
		}

		private Iterator<T> getNextIterator() {
			return index < list.size() ? list.get(index++).iterator() : null;
		}

	}

	public static <T> Iterable<T> from(T[] array) {
		return array == null ? Collections.emptyList() : Arrays.asList(array);
	}

	public static <T> Iterable<T> from(Collection<T> collection) {
		return collection == null ? Collections.emptyList() : collection;
	}

}
