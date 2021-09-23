package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.ClassDto;
import com.github.vvorks.builder.server.domain.FieldDto;

@Mapper
public interface ClassMapper {

	public boolean insert(ClassDto classDto);

	public boolean update(ClassDto classDto);

	public boolean delete(ClassDto classDto);

	public ClassDto get(int classId);

	public List<FieldDto> listFields(
			@Param("c") ClassDto classDto,
			@Param("offset") int offset,
			@Param("limit") int limit);

	public List<FieldDto> listPkFields(@Param("c") ClassDto classDto);

}
