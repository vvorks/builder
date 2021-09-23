package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.ClassDto;
import com.github.vvorks.builder.server.domain.EnumDto;
import com.github.vvorks.builder.server.domain.ProjectDto;

@Mapper
public interface ProjectMapper {

	public boolean insert(ProjectDto projectDto);

	public boolean update(ProjectDto projectDto);

	public boolean delete(ProjectDto projectDto);

	public ProjectDto get(int projectId);

	public List<ProjectDto> list(
			@Param("offset") int offset,
			@Param("limit") int limit);

	public List<ClassDto> listClasses(
			@Param("project") ProjectDto projectDto,
			@Param("offset") int offset,
			@Param("limit") int limit);

	public List<EnumDto> listEnums(
			@Param("project") ProjectDto projectDto,
			@Param("offset") int offset,
			@Param("limit") int limit);

}
