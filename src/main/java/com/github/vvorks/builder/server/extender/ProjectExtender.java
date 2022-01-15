package com.github.vvorks.builder.server.extender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.server.ServerSettings;
import com.github.vvorks.builder.server.common.util.Patterns;
import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.EnumContent;
import com.github.vvorks.builder.server.domain.MessageContent;
import com.github.vvorks.builder.server.domain.ProjectContent;
import com.github.vvorks.builder.server.mapper.ProjectMapper;

@Component
public class ProjectExtender {

    @Autowired
    private ProjectMapper projectMapper;

	public String getTitleOrName(ProjectContent prj) {
		if (!Strings.isEmpty(prj.getTitle())) {
			return prj.getTitle();
		} else {
			return getLastName(prj);
		}
	}

	public String getUpperLastName(ProjectContent prj) {
		return Strings.toFirstUpper(getLastName(prj));
	}

	public String getLastName(ProjectContent prj) {
		String name = prj.getProjectName();
		int lastDot = name.lastIndexOf('.') + 1;
		return name.substring(lastDot);
	}

	public List<String> getCopyrightLines(ProjectContent prj) {
		String copyrights = prj.getCopyrights();
		if (copyrights == null || copyrights.isEmpty()) {
			return null;
		}
		String[] lines = Patterns.EOL.split(copyrights, -1);
		return Arrays.asList(lines);
	}

	public List<ClassContent> getClasses(ProjectContent prj) {
		return projectMapper.listClassesContent(prj, 0, 0);
	}

	public List<ClassContent> getClassesByName(ProjectContent prj) {
		List<ClassContent> list = projectMapper.listClassesContent(prj, 0, 0);
		Collections.sort(list, (a, b) -> a.getClassName().compareTo(b.getClassName()));
		return list;
	}

	public List<EnumContent> getEnums(ProjectContent prj) {
		return projectMapper.listEnumsContent(prj, 0, 0);
	}

	public List<EnumContent> getEnumsByName(ProjectContent prj) {
		List<EnumContent> list = projectMapper.listEnumsContent(prj, 0, 0);
		Collections.sort(list, (a, b) -> a.getEnumName().compareTo(b.getEnumName()));
		return list;
	}

	public boolean isBuilderProject(ProjectContent prj) {
		return prj.getProjectName().equals(ServerSettings.MODULE_NAME);
	}

	public String getBuilderName(ProjectContent prj) {
		return ServerSettings.MODULE_NAME;
	}

	public Set<Map.Entry<String, List<MessageContent>>> getMessages(ProjectContent prj) {
		List<MessageContent> rawList = projectMapper.listMessagesContent(prj, 0, 0);
		Map<String, List<MessageContent>> localeMap = new LinkedHashMap<>();
		for (MessageContent c : rawList) {
			String locale = c.getLocaleId();
			String key = Strings.isEmpty(locale) ? "" : "_" + locale;
			List<MessageContent> subList = localeMap.computeIfAbsent(key, k -> new ArrayList<>());
			subList.add(c);
		}
		return localeMap.entrySet();
	}

}
