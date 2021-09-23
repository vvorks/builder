package com.github.vvorks.builder.server.common.handlebars;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.github.jknack.handlebars.ValueResolver;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.common.util.Logger;
import com.github.vvorks.builder.server.common.util.Invoker;

public class ExtenderResolver implements ValueResolver {

	private static final Class<?> THIS = ExtenderResolver.class;

	private static final Logger LOGGER = Factory.newInstance(Logger.class, THIS);

	private final Map<Class<?>, Map<String, Invoker>> classMap;

	public ExtenderResolver(Object... extenders) {
		classMap = new LinkedHashMap<>();
		for (Object ext : extenders) {
			for (Method m : ext.getClass().getMethods()) {
				Class<?> target = getExtensionTarget(m);
				if (target != null) {
					Map<String, Invoker> methodMap =
							classMap.computeIfAbsent(target, k -> new LinkedHashMap<>());
					String name = getPropertyName(m);
					if (!methodMap.containsKey(name)) {
						methodMap.put(name, new Invoker(ext, m));
					} else {
						LOGGER.warn("%s#%s() is IGNORED", m.getDeclaringClass().getName(), m.getName());
					}
				}
			}
		}
	}

	private Class<?> getExtensionTarget(Method method) {
		if (!(isBooleanGetter(method) || isGetter(method))) {
			return null;
		}
		Class<?>[] params = method.getParameterTypes();
		if (params.length != 1) {
			return null;
		}
		return params[0];
	}

	private boolean isBooleanGetter(Method method) {
		String methodName = method.getName();
		Class<?> returnType = method.getReturnType();
		return	methodName.startsWith("is") &&
				(returnType == Boolean.TYPE || returnType == Boolean.class);
	}

	private boolean isGetter(Method method) {
		String methodName = method.getName();
		return methodName.startsWith("get");
	}

	public String getPropertyName(Method method) {
		String name = method.getName();
		if (name.startsWith("get") || name.startsWith("set")) {
			name = Strings.toFirstLower(name.substring(3));
		} else if (name.startsWith("is")) {
			name = Strings.toFirstLower(name.substring(2));
		}
		return name;
	}

	@Override
	public Object resolve(Object context, String name) {
		if (context == null || name == null || name.isEmpty()) {
			return UNRESOLVED;
		}
		Invoker invoker = findInvoker(context, name);
		if (invoker == null) {
			return UNRESOLVED;
		}
		return invoker.invoke(context);
	}

	private Invoker findInvoker(Object context, String name) {
		Map<String, Invoker> methodMap = classMap.get(context.getClass());
		if (methodMap == null) {
			return null;
		}
		return methodMap.get(name);
	}

	@Override
	public Object resolve(Object context) {
	    return UNRESOLVED;
	}

	@Override
	public Set<Map.Entry<String, Object>> propertySet(Object context) {
		if (context == null) {
			return Collections.emptySet();
		}
		Map<String, Invoker> methodMap = classMap.get(context.getClass());
		Map<String, Object> propertyMap = new LinkedHashMap<>();
		for (Map.Entry<String, Invoker> e : methodMap.entrySet()) {
			Object value = e.getValue().invoke(context);
			propertyMap.put(e.getKey(), value);
	    }
		return propertyMap.entrySet();
	}

}
