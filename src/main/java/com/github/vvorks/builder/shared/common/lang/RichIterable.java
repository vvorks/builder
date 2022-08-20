package com.github.vvorks.builder.shared.common.lang;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

public class RichIterable<T> implements Iterable<T> {

	/**
	 * filter iterator
	 *
	 * @param <T>
	 */
	private static class FilterIterator<T> implements Iterator<T> {

		private final Iterator<T> source;

		private final Predicate<T> cond;

		private T next;

		private boolean hasNext;

		private FilterIterator(Iterator<T> source, Predicate<T> cond) {
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

	/**
	 * concat iterator
	 *
	 * @param <T>
	 */
	private static class ConcatIterator<T> implements Iterator<T> {

		private List<Iterable<T>> list;

		private int index;

		private Iterator<T> itr;

		private ConcatIterator(List<Iterable<T>> list) {
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

	/**
	 * convert iterator
	 *
	 * @param <T>
	 * @param <R>
	 */
	private static class ConvertIterator<T, R> implements Iterator<R> {

		private final Iterator<T> source;

		private final Function<T, R> func;

		private ConvertIterator(Iterator<T> source, Function<T, R> func) {
			this.source = source;
			this.func = func;
		}

		@Override
		public boolean hasNext() {
			return source.hasNext();
		}

		@Override
		public R next() {
			return func.apply(source.next());
		}

	}

	public static <T> RichIterable<T> from(Iterable<T> iterable) {
		if (iterable instanceof RichIterable<?>) {
			return (RichIterable<T>) iterable;
		}
		return new RichIterable<>(iterable);
	}

	public static <T> RichIterable<T> from(List<Iterable<T>> list) {
		return new RichIterable<>(
				() -> new ConcatIterator<T>(list));
	}

	public static <T> RichIterable<T> from(Collection<T> collection) {
		return new RichIterable<>(
				collection == null ? Collections.emptyList() : collection);
	}

	public static <T> RichIterable<T> from(T[] array) {
		return new RichIterable<>(
				array == null ? Collections.emptyList() : Arrays.asList(array));
	}

	/**
	 * sourcec iterable
	 */
	private Iterable<T> source;

	/**
	 * create new rich iterable from source.
	 *
	 * @param iterable source iterable.
	 */
	public RichIterable(Iterable<T> iterable) {
		this.source = iterable;
	}

	@Override
	public Iterator<T> iterator() {
		return source.iterator();
	}

	public boolean isEmpty() {
		if (source instanceof Collection<?>) {
			return ((Collection<?>) source).isEmpty();
		} else {
			return !source.iterator().hasNext();
		}
	}

	public T first(T defaultValue) {
		if (source instanceof List) {
			List<T> list = (List<T>) source;
			return !list.isEmpty() ? list.get(0) : defaultValue;
		} else {
			Iterator<T> itr = source.iterator();
			return itr.hasNext() ? itr.next() : defaultValue;
		}
	}

	public T last(T defaultValue) {
		if (source instanceof List) {
			List<T> list = (List<T>) source;
			int n = list.size();
			return n > 0 ? list.get(n - 1) : defaultValue;
		} else {
			T last = defaultValue;
			for (T e : source) {
				last = e;
			}
			return last;
		}
	}

	public boolean exists(Predicate<T> func) {
		for (T t : source) {
			if (func.test(t)) {
				return true;
			}
		}
		return false;
	}

	public RichIterable<T> filter(Predicate<T> cond) {
		return new RichIterable<>(
				() -> new FilterIterator<T>(source.iterator(), cond));
	}

	public <R> RichIterable<R> map(Function<T, R> func) {
		return new RichIterable<>(
				() -> new ConvertIterator<T, R>(source.iterator(), func));
	}

	public void into(Collection<T> correction) {
		forEach(c -> correction.add(c));
	}

}