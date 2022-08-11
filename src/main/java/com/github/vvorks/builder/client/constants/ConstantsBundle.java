package com.github.vvorks.builder.client.constants;

import com.github.vvorks.builder.shared.common.lang.Factory;
import com.github.vvorks.builder.shared.common.util.JsonResourceBundle;

public interface ConstantsBundle extends JsonResourceBundle {

	public static ConstantsBundle get() {
		return Factory.getInstance(ConstantsBundle.class);
	}

}
