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

	/** リソースのMapper */
	@Autowired
	private ResourceMapper resourceMapper;

	/** ローカライズドリソースのMapper */
	@Autowired
	private LocalizedResourceMapper localizedResourceMapper;

	private final Map<String, BuilderMapper<?>> mappers = new HashMap<>();

	public Mappers() {
		mappers.put("Project", projectMapper);
		mappers.put("ProjectI18n", projectI18nMapper);
		mappers.put("Class", classMapper);
		mappers.put("Field", fieldMapper);
		mappers.put("Query", queryMapper);
		mappers.put("Enum", enumMapper);
		mappers.put("EnumValue", enumValueMapper);
		mappers.put("Message", messageMapper);
		mappers.put("Locale", localeMapper);
		mappers.put("Resource", resourceMapper);
		mappers.put("LocalizedResource", localizedResourceMapper);
	}

	public Map<String, BuilderMapper<?>> getMappers() {
		return mappers;
	}

}
