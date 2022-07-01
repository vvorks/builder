package com.github.vvorks.builder.server.extender;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.server.ServerSettings;
import com.github.vvorks.builder.server.common.util.Patterns;
import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.EnumContent;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.server.domain.MessageContent;
import com.github.vvorks.builder.server.domain.ProjectContent;
import com.github.vvorks.builder.server.mapper.ClassMapper;
import com.github.vvorks.builder.server.mapper.FieldMapper;
import com.github.vvorks.builder.server.mapper.MessageMapper;
import com.github.vvorks.builder.server.mapper.ProjectMapper;

@Component
public class ProjectExtender {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private FieldMapper fieldMapper;

    @Autowired
    private MessageMapper messageMapper;

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
			return Collections.emptyList();
		}
		String[] lines = Patterns.EOL.split(copyrights, -1);
		return Arrays.asList(lines);
	}

	public List<ClassContent> getClasses(ProjectContent prj) {
		List<ClassContent> result = projectMapper.listClassesContent(prj, 0, 0);
		return result;
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

	/**
	 * クラスラベルリソースを出力する
	 *
	 * @param prj プロジェクト情報
	 * @return クラスラベルのロケール別リソースJSONファイル
	 */
	public Set<Map.Entry<String, Json>> getClassLabels(ProjectContent prj) {
		List<ClassContent> classes = projectMapper.listClassesContent(prj, 0, 0);
		Map<String, Json> jsonMap = new LinkedHashMap<>();
		for (ClassContent cls : classes) {
			String key = cls.getClassName();
			Json json = jsonMap.computeIfAbsent("", k -> Json.createObject());
			json.setString(key, cls.getLabel());
//			classMapper.
//			if (lab != null) {
//				List<LocalizedResourceContent> locs = resourceMapper.listVariationsContent(lab, 0, 0);
//				for (LocalizedResourceContent loc : locs) {
//					String suffix = "_" + loc.getLocale();
//					Json locJson = jsonMap.computeIfAbsent(suffix, k -> Json.createObject());
//					String locValue = loc.getValue();
//					locJson.setString(key, locValue);
//				}
//			}
		}
		return jsonMap.entrySet();
	}

	/**
	 * フィールドラベルリソースを出力する
	 *
	 * @param prj プロジェクト情報
	 * @return フィールドラベルのロケール別リソースJSONファイル
	 */
	public Set<Map.Entry<String, Json>> getFieldLabels(ProjectContent prj) {
		List<ClassContent> classes = projectMapper.listClassesContent(prj, 0, 0);
		Map<String, Json> jsonMap = new LinkedHashMap<>();
		for (ClassContent cls : classes) {
			String clsName = cls.getClassName();
			Json clsJson = jsonMap.computeIfAbsent("", k -> Json.createObject());
			Json fldJson = Json.createObject();
			clsJson.set(clsName, fldJson);
			List<FieldContent> fields = classMapper.listFieldsContent(cls, 0, 0);
			for (FieldContent fld : fields) {
				String fldName = fld.getFieldName();
				fldJson.setString(fldName, fld.getLabel());
//				if (lab != null) {
//					List<LocalizedResourceContent> locs = resourceMapper.listVariationsContent(lab, 0, 0);
//					for (LocalizedResourceContent loc : locs) {
//						String suffix = "_" + loc.getLocale();
//						Json locJson = jsonMap.computeIfAbsent(suffix, k -> Json.createObject());
//						Json locFldJson = locJson.computeIfAbsent(clsName, k -> Json.createObject());
//						locFldJson.setString(fldName, loc.getValue());
//					}
//				}
			}
		}
		return jsonMap.entrySet();
	}

	/**
	 * メッセージリソースを出力する
	 *
	 * @param prj プロジェクト情報
	 * @return メッセージのロケール別リソースJSONファイル
	 */
	public Set<Map.Entry<String, Json>> getMessages(ProjectContent prj) {
		List<MessageContent> messages = projectMapper.listMessagesContent(prj, 0, 0);
		Map<String, Json> jsonMap = new LinkedHashMap<>();
		for (MessageContent msg : messages) {
			String key = msg.getMessageName();
			Json json = jsonMap.computeIfAbsent("", k -> Json.createObject());
			json.setString(key, msg.getMessage());
//			if (res != null) {
//				List<LocalizedResourceContent> locs = resourceMapper.listVariationsContent(res, 0, 0);
//				for (LocalizedResourceContent loc : locs) {
//					String suffix = "_" + loc.getLocale();
//					Json locJson = jsonMap.computeIfAbsent(suffix, k -> Json.createObject());
//					String locValue = loc.getValue();
//					locJson.setString(key, locValue);
//				}
//			}
		}
		return jsonMap.entrySet();
	}

}
