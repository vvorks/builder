package com.github.vvorks.builder.client.gwt.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.vvorks.builder.rebind.GlobalizeResourceGenerator;
import com.google.gwt.resources.client.ResourcePrototype;
import com.google.gwt.resources.ext.DefaultExtensions;
import com.google.gwt.resources.ext.ResourceGeneratorType;

@DefaultExtensions(value = {".json"})
@ResourceGeneratorType(GlobalizeResourceGenerator.class)
public interface GlobalizeResource extends ResourcePrototype {
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface Require {
		String[] value();
	}
	String getLocale();
	String[] getJsContents();
	String[] getJsonContents();
}
