package com.github.vvorks.builder.client.gwt.util;

import com.github.vvorks.builder.common.util.JsonResourceBundle;
import com.google.gwt.core.client.GWT;

public interface GwtResourceBundle extends JsonResourceBundle {

	public static GwtResourceBundle INSTANCE = GWT.create(GwtResourceBundle.class);

	public static JsonResourceBundle get() {
		return INSTANCE;
	}

}
