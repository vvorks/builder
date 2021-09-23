package com.github.vvorks.builder.server.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.github.vvorks.builder.server.domain.FieldDto;

@Mapper
public interface FieldMapper {

	public boolean insert(FieldDto fieldDto);

	public boolean update(FieldDto fieldDto);

	public boolean delete(FieldDto fieldDto);

	public FieldDto get(int fieldId);

}
