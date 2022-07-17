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

	/** フィールド(I18n)のMapper */
	@Autowired
	private FieldI18nMapper fieldI18nMapper;

	/** クエリーのMapper */
	@Autowired
	private QueryMapper queryMapper;

	/** 列挙のMapper */
	@Autowired
	private EnumMapper enumMapper;

	/** 列挙(I18n)のMapper */
	@Autowired
	private EnumI18nMapper enumI18nMapper;

	/** 列挙値のMapper */
	@Autowired
	private EnumValueMapper enumValueMapper;

	/** 列挙値(I18n)のMapper */
	@Autowired
	private EnumValueI18nMapper enumValueI18nMapper;

	/** メッセージのMapper */
	@Autowired
	private MessageMapper messageMapper;

	/** メッセージ(I18n)のMapper */
	@Autowired
	private MessageI18nMapper messageI18nMapper;

	/** スタイルのMapper */
	@Autowired
	private StyleMapper styleMapper;

	/** フォームのMapper */
	@Autowired
	private FormMapper formMapper;

	/** フォームバリエーションのMapper */
	@Autowired
	private FormVariantMapper formVariantMapper;

	/** レイアウトのMapper */
	@Autowired
	private LayoutMapper layoutMapper;

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
			mapperMap.put("FieldI18n", fieldI18nMapper);
			mapperMap.put("Query", queryMapper);
			mapperMap.put("Enum", enumMapper);
			mapperMap.put("EnumI18n", enumI18nMapper);
			mapperMap.put("EnumValue", enumValueMapper);
			mapperMap.put("EnumValueI18n", enumValueI18nMapper);
			mapperMap.put("Message", messageMapper);
			mapperMap.put("MessageI18n", messageI18nMapper);
			mapperMap.put("Style", styleMapper);
			mapperMap.put("Form", formMapper);
			mapperMap.put("FormVariant", formVariantMapper);
			mapperMap.put("Layout", layoutMapper);
			mapperMap.put("Locale", localeMapper);
		}
		return mapperMap.get(name);
	}

}
