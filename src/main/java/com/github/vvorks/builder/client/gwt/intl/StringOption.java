package com.github.vvorks.builder.client.gwt.intl;

import com.github.vvorks.builder.client.gwt.util.JsObject;

/**
 * 文字列型オプション
 */
public interface StringOption extends Option<String> {

	@Override
	public default void setInto(JsObject options) {
		options.setString(key(), value());
	}

}
