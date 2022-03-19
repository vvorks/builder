package com.github.vvorks.builder.client.gwt.intl;

import com.github.vvorks.builder.client.gwt.util.JsObject;

/**
 * 数値型オプション
 */
public interface NumberOption extends Option<Double> {

	@Override
	public default void setInto(JsObject options) {
		options.setNumber(key(), value());
	}

}
