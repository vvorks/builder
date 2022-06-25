package com.github.vvorks.builder.common.util;

import java.util.Map;
import java.util.function.Consumer;

import com.github.vvorks.builder.common.lang.Factory;

public interface TestRunner {

	public static TestRunner getRunnerIfExists() {
		if (Factory.isRegistered(TestRunner.class)) {
			return Factory.getInstance(TestRunner.class);
		}
		return null;
	}

	public default boolean doBeforeAfter(
			TestCase t,
			String typeName, String methodName,
			Consumer<TestCase> func,
			Map<String, Throwable> resultMap) {
		try {
			func.accept(t);
			return true;
		} catch (Exception|AssertionError err) {
			resultMap.put(typeName + methodName, err);
			return false;
		}
	}

	public default void doTest(
			TestCase t,
			String typeName, String methodName,
			Consumer<TestCase> func,
			Map<String, Throwable> resultMap) {
		try {
			t.beforeTest();
		} catch (Exception|AssertionError err) {
			resultMap.put(typeName + "#beforeTest", err);
			return;
		}
		try {
			func.accept(t);
			resultMap.put(typeName + methodName, null);
		} catch (Exception|AssertionError err) {
			resultMap.put(typeName + methodName, err);
		} finally {
			try {
				t.afterTest();
			} catch (Exception|AssertionError err) {
				resultMap.put(typeName + "#afterTest", err);
			}
		}
	}

	public Map<String, Throwable> run();

}
