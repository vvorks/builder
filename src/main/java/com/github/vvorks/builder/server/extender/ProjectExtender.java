package com.github.vvorks.builder.server.extender;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.server.common.util.Patterns;
import com.github.vvorks.builder.server.domain.ClassDto;
import com.github.vvorks.builder.server.domain.EnumDto;
import com.github.vvorks.builder.server.domain.ProjectDto;
import com.github.vvorks.builder.server.mapper.ProjectMapper;

@Component
public class ProjectExtender {

    @Autowired
    private ProjectMapper projectMapper;

	public List<String> getCopyrightLines(ProjectDto prj) {
		String copyrights = prj.getCopyrights();
		if (copyrights == null || copyrights.isEmpty()) {
			return null;
		}
		String[] lines = Patterns.EOL.split(copyrights, -1);
		return Arrays.asList(lines);
	}

	public List<ClassDto> getClasses(ProjectDto prj) {
		return projectMapper.listClasses(prj, 0, 10000);
	}

	public List<EnumDto> getEnums(ProjectDto prj) {
		return projectMapper.listEnums(prj, 0, 10000);
	}

}
