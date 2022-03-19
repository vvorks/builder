package com.github.vvorks.builder.client.gwt.intl;

import com.github.vvorks.builder.client.gwt.util.JsObject;

/**
 * Boolean型オプションインターフェース
 */
public interface BooleanOption extends Option<Boolean> {

	@Override
	public default void setInto(JsObject options) {
		options.setBoolean(key(), value());
	}

}
