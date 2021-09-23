package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.EnumDto;
import com.github.vvorks.builder.server.domain.EnumValueDto;

@Mapper
public interface EnumMapper {

	public boolean insert(EnumDto enumDto);

	public boolean update(EnumDto enumDto);

	public boolean delete(EnumDto enumDto);

	public EnumDto get(int enumId);

	public List<EnumValueDto> listValues(
			@Param("e") EnumDto enumDto,
			@Param("offset") int offset,
			@Param("limit") int limit);

}
