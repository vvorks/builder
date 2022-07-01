/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mappers {

	/** プロジェクトのMapper */
	@Autowired
	private ProjectMapper projectMapper;

	/** プロジェクト(I18n)のMapper */
	@Autowired
	private ProjectI18nMapper projectI18nMapper;

	/** クラスのMapper */
	@Autowired
	private ClassMapper classMapper;

	/** クラス(I18n)のMapper */
	@Autowired
	private ClassI18nMapper classI18nMapper;

	/** フィールドのMapper */
	@Autowired
	private FieldMapper fieldMapper;

	/** クエリーのMapper */
	@Autowired
	private QueryMapper queryMapper;

	/** 列挙のMapper */
	@Autowired
	private EnumMapper enumMapper;

	/** 列挙値のMapper */
	@Autowired
	private EnumValueMapper enumValueMapper;

	/** メッセージのMapper */
	@Autowired
	private MessageMapper messageMapper;

	/** ロケールのMapper */
	@Autowired
	private LocaleMapper localeMapper;

	private Map<String, BuilderMapper<?>> mapperMap;

	public BuilderMapper<?> getMapperOf(String name) {
		if (mapperMap == null) {
			mapperMap = new HashMap<>();
			mapperMap.put("Project", projectMapper);
			mapperMap.put("ProjectI18n", projectI18nMapper);
			mapperMap.put("Class", classMapper);
			mapperMap.put("ClassI18n", classI18nMapper);
			mapperMap.put("Field", fieldMapper);
			mapperMap.put("Query", queryMapper);
			mapperMap.put("Enum", enumMapper);
			mapperMap.put("EnumValue", enumValueMapper);
			mapperMap.put("Message", messageMapper);
			mapperMap.put("Locale", localeMapper);
		}
		return mapperMap.get(name);
	}

}
