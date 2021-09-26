package com.github.vvorks.builder.server.extender;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.server.domain.EnumDto;
import com.github.vvorks.builder.server.domain.EnumValueDto;
import com.github.vvorks.builder.server.mapper.EnumMapper;

@Component
public class EnumExtender {

	@Autowired
	private EnumMapper enumTypeMapper;

	public String getTitleOrName(EnumDto e) {
		if (!Strings.isEmpty(e.getTitle())) {
			return e.getTitle();
		} else {
			return e.getEnumName();
		}
	}

	public List<EnumValueDto> getEnumValues(EnumDto e) {
		return enumTypeMapper.listValues(e, 0, 0);
	}

}
