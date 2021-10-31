package com.github.vvorks.builder.server.extender;

import org.springframework.stereotype.Component;

import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.server.domain.EnumValueContent;

@Component
public class EnumValueExtender {

	public String getTitleOrName(EnumValueContent v) {
		if (!Strings.isEmpty(v.getTitle())) {
			return v.getTitle();
		} else {
			return v.getValueId();
		}
	}

}
