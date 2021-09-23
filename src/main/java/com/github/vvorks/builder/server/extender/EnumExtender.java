package com.github.vvorks.builder.server.extender;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.server.domain.EnumDto;
import com.github.vvorks.builder.server.domain.EnumValueDto;
import com.github.vvorks.builder.server.mapper.EnumMapper;

@Component
public class EnumExtender {

	@Autowired
	private EnumMapper enumTypeMapper;

	public List<EnumValueDto> getEnumValues(EnumDto et) {
		return enumTypeMapper.listValues(et, 0, 0);
	}

}
