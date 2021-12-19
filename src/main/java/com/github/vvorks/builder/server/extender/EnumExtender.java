package com.github.vvorks.builder.server.extender;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.server.domain.EnumContent;
import com.github.vvorks.builder.server.domain.EnumValueContent;
import com.github.vvorks.builder.server.mapper.EnumMapper;

@Component
public class EnumExtender {

	@Autowired
	private EnumMapper enumTypeMapper;

	/**
	 * （再）初期化
	 */
	public EnumExtender init() {
		return this;
	}

	public String getTitleOrName(EnumContent e) {
		if (!Strings.isEmpty(e.getTitle())) {
			return e.getTitle();
		} else {
			return e.getEnumName();
		}
	}

	public List<EnumValueContent> getEnumValues(EnumContent e) {
		return enumTypeMapper.listValuesContent(e, 0, 0);
	}

	public String getEncodeType(EnumContent e) {
		return e.isEncodeString() ? "String" : "Integer";
	}

}
