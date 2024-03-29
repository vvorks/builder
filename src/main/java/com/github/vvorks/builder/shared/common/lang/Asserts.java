package com.github.vvorks.builder.shared.common.lang;

import java.util.Objects;

public class Asserts {

	private Asserts() {
	}

	public static void require(boolean cond) {
		if (!cond) {
			throw new IllegalArgumentException();
		}
	}

	public static void require(boolean cond, String errorMessage) {
		if (!cond) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	public static void requireNotNull(Object obj) {
		if (obj == null) {
			throw new IllegalArgumentException();
		}
	}

	public static void requireNotNull(Object obj, String errorMessage) {
		if (obj == null) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	public static void requireNotEmpty(String str) {
		if (str == null || str.isEmpty()) {
			throw new IllegalArgumentException();
		}
	}

	public static void requireNotEmpty(String str, String errorMessage) {
		if (str == null || str.isEmpty()) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	public static void assume(boolean cond) {
		if (!cond) {
			throw new IllegalStateException();
		}
	}

	public static void assume(boolean cond, String errorMessage) {
		if (!cond) {
			throw new IllegalStateException(errorMessage);
		}
	}

	public static void assumeNotNull(Object obj) {
		if (obj == null) {
			throw new IllegalStateException();
		}
	}

	public static void assumeNotNull(Object obj, String errorMessage) {
		if (obj == null) {
			throw new IllegalStateException(errorMessage);
		}
	}

	public static void check(boolean cond) {
		if (!cond) {
			throw new AssertionError();
		}
	}

	public static void check(boolean cond, String errorMessage) {
		if (!cond) {
			throw new AssertionError(errorMessage);
		}
	}

	public static void checkNotNull(Object obj) {
		if (obj == null) {
			throw new AssertionError();
		}
	}

	public static void checkNotNull(Object obj, String errorMessage) {
		if (obj == null) {
			throw new AssertionError(errorMessage);
		}
	}

	public static void checkEquals(Object a, Object b) {
		if (!Objects.equals(a, b)) {
			throw new AssertionError("[" + a + "] != [" + b + "]");
		}
	}

	public static void checkEquals(Object a, Object b, String errorMessage) {
		if (!Objects.equals(a, b)) {
			throw new AssertionError(errorMessage);
		}
	}

}
