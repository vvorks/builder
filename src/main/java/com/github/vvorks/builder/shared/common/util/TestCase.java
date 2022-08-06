package com.github.vvorks.builder.shared.common.util;

public interface TestCase {
	public default void beforeTestCase() {}
	public default void beforeTest() {}
	public default void afterTest() {}
	public default void afterTestCase() {}
}
