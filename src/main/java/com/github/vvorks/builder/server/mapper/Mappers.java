/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntSupplier;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import com.github.vvorks.builder.common.logging.Logger;
import com.github.vvorks.builder.server.domain.BuilderContent;

@Component
public class Mappers implements BeanPostProcessor {

	private static final Logger LOGGER = Logger.createLogger(Mappers.class);

	private static Mappers instance;

	public static Mappers get() {
		LOGGER.info("Mappers instance ", instance != null);
		return instance;
	}

	private static final String NAME_PROJECT = "Project";
	private static final String NAME_PROJECT_I18N = "ProjectI18n";
	private static final String NAME_CLASS = "Class";
	private static final String NAME_CLASS_I18N = "ClassI18n";
	private static final String NAME_FIELD = "Field";
	private static final String NAME_FIELD_I18N = "FieldI18n";
	private static final String NAME_QUERY = "Query";
	private static final String NAME_ENUM = "Enum";
	private static final String NAME_ENUM_I18N = "EnumI18n";
	private static final String NAME_ENUM_VALUE = "EnumValue";
	private static final String NAME_ENUM_VALUE_I18N = "EnumValueI18n";
	private static final String NAME_MESSAGE = "Message";
	private static final String NAME_MESSAGE_I18N = "MessageI18n";
	private static final String NAME_STYLE = "Style";
	private static final String NAME_WIDGET = "Widget";
	private static final String NAME_PAGE_SET = "PageSet";
	private static final String NAME_PAGE = "Page";
	private static final String NAME_LAYOUT = "Layout";
	private static final String NAME_LOCALE = "Locale";
	private static final String NAME_BUILDER = "Builder";

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

	/** ウィジェットのMapper */
	@Autowired
	private WidgetMapper widgetMapper;

	/** ページセットのMapper */
	@Autowired
	private PageSetMapper pageSetMapper;

	/** ページのMapper */
	@Autowired
	private PageMapper pageMapper;

	/** レイアウトのMapper */
	@Autowired
	private LayoutMapper layoutMapper;

	/** ロケールのMapper */
	@Autowired
	private LocaleMapper localeMapper;

	/** BuilderのMapper */
	@Autowired
	private BuilderMapper builderMapper;

	private Map<String, MapperInterface<?>> mapperMap;

	public Mappers() {
		instance = this;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (beanName.equals("dataSourceScriptDatabaseInitializer")) {
			resetIds();
		}
		return bean;
	}

	private void resetIds() {
		resetId(NAME_PROJECT, () -> projectMapper.listSummary().getMaxProjectId());
		resetId(NAME_CLASS, () -> classMapper.listSummary().getMaxClassId());
		resetId(NAME_FIELD, () -> fieldMapper.listSummary().getMaxFieldId());
		resetId(NAME_QUERY, () -> queryMapper.listSummary().getMaxQueryId());
		resetId(NAME_ENUM, () -> enumMapper.listSummary().getMaxEnumId());
		resetId(NAME_MESSAGE, () -> messageMapper.listSummary().getMaxMessageId());
		resetId(NAME_STYLE, () -> styleMapper.listSummary().getMaxStyleId());
		resetId(NAME_WIDGET, () -> widgetMapper.listSummary().getMaxWidgetId());
		resetId(NAME_PAGE_SET, () -> pageSetMapper.listSummary().getMaxPageSetId());
		resetId(NAME_PAGE, () -> pageMapper.listSummary().getMaxPageId());
		resetId(NAME_LAYOUT, () -> layoutMapper.listSummary().getMaxLayoutId());
	}

	/** プロジェクトのMapperを取得する */
	public ProjectMapper getProjectMapper() {
		return projectMapper;
	}

	/** プロジェクト(I18n)のMapperを取得する */
	public ProjectI18nMapper getProjectI18nMapper() {
		return projectI18nMapper;
	}

	/** クラスのMapperを取得する */
	public ClassMapper getClassMapper() {
		return classMapper;
	}

	/** クラス(I18n)のMapperを取得する */
	public ClassI18nMapper getClassI18nMapper() {
		return classI18nMapper;
	}

	/** フィールドのMapperを取得する */
	public FieldMapper getFieldMapper() {
		return fieldMapper;
	}

	/** フィールド(I18n)のMapperを取得する */
	public FieldI18nMapper getFieldI18nMapper() {
		return fieldI18nMapper;
	}

	/** クエリーのMapperを取得する */
	public QueryMapper getQueryMapper() {
		return queryMapper;
	}

	/** 列挙のMapperを取得する */
	public EnumMapper getEnumMapper() {
		return enumMapper;
	}

	/** 列挙(I18n)のMapperを取得する */
	public EnumI18nMapper getEnumI18nMapper() {
		return enumI18nMapper;
	}

	/** 列挙値のMapperを取得する */
	public EnumValueMapper getEnumValueMapper() {
		return enumValueMapper;
	}

	/** 列挙値(I18n)のMapperを取得する */
	public EnumValueI18nMapper getEnumValueI18nMapper() {
		return enumValueI18nMapper;
	}

	/** メッセージのMapperを取得する */
	public MessageMapper getMessageMapper() {
		return messageMapper;
	}

	/** メッセージ(I18n)のMapperを取得する */
	public MessageI18nMapper getMessageI18nMapper() {
		return messageI18nMapper;
	}

	/** スタイルのMapperを取得する */
	public StyleMapper getStyleMapper() {
		return styleMapper;
	}

	/** ウィジェットのMapperを取得する */
	public WidgetMapper getWidgetMapper() {
		return widgetMapper;
	}

	/** ページセットのMapperを取得する */
	public PageSetMapper getPageSetMapper() {
		return pageSetMapper;
	}

	/** ページのMapperを取得する */
	public PageMapper getPageMapper() {
		return pageMapper;
	}

	/** レイアウトのMapperを取得する */
	public LayoutMapper getLayoutMapper() {
		return layoutMapper;
	}

	/** ロケールのMapperを取得する */
	public LocaleMapper getLocaleMapper() {
		return localeMapper;
	}

	/** BuilderのMapperを取得する */
	public BuilderMapper getBuilderMapper() {
		return builderMapper;
	}

	/** プロジェクトのIdを新規発番する */
	public int newProjectId() {
		return generateId(NAME_PROJECT);
	}

	/** クラスのIdを新規発番する */
	public int newClassId() {
		return generateId(NAME_CLASS);
	}

	/** フィールドのIdを新規発番する */
	public int newFieldId() {
		return generateId(NAME_FIELD);
	}

	/** クエリーのIdを新規発番する */
	public int newQueryId() {
		return generateId(NAME_QUERY);
	}

	/** 列挙のIdを新規発番する */
	public int newEnumId() {
		return generateId(NAME_ENUM);
	}

	/** メッセージのIdを新規発番する */
	public int newMessageId() {
		return generateId(NAME_MESSAGE);
	}

	/** スタイルのIdを新規発番する */
	public int newStyleId() {
		return generateId(NAME_STYLE);
	}

	/** ウィジェットのIdを新規発番する */
	public int newWidgetId() {
		return generateId(NAME_WIDGET);
	}

	/** ページセットのIdを新規発番する */
	public int newPageSetId() {
		return generateId(NAME_PAGE_SET);
	}

	/** ページのIdを新規発番する */
	public int newPageId() {
		return generateId(NAME_PAGE);
	}

	/** レイアウトのIdを新規発番する */
	public int newLayoutId() {
		return generateId(NAME_LAYOUT);
	}

	private synchronized int resetId(String name, IntSupplier func) {
		BuilderContent obj;
		do {
			obj = builderMapper.get(name);
			obj.setSurrogateCount(Math.max(obj.getSurrogateCount(), func.getAsInt()));
		} while(!builderMapper.update(obj));
		return obj.getSurrogateCount();
	}

	private synchronized int generateId(String name) {
		BuilderContent obj;
		do {
			obj = builderMapper.get(name);
			obj.setSurrogateCount(obj.getSurrogateCount() + 1);
		} while(!builderMapper.update(obj));
		return obj.getSurrogateCount();
	}

	public MapperInterface<?> getMapperOf(String name) {
		if (mapperMap == null) {
			mapperMap = new HashMap<>();
			mapperMap.put(NAME_PROJECT, projectMapper);
			mapperMap.put(NAME_PROJECT_I18N, projectI18nMapper);
			mapperMap.put(NAME_CLASS, classMapper);
			mapperMap.put(NAME_CLASS_I18N, classI18nMapper);
			mapperMap.put(NAME_FIELD, fieldMapper);
			mapperMap.put(NAME_FIELD_I18N, fieldI18nMapper);
			mapperMap.put(NAME_QUERY, queryMapper);
			mapperMap.put(NAME_ENUM, enumMapper);
			mapperMap.put(NAME_ENUM_I18N, enumI18nMapper);
			mapperMap.put(NAME_ENUM_VALUE, enumValueMapper);
			mapperMap.put(NAME_ENUM_VALUE_I18N, enumValueI18nMapper);
			mapperMap.put(NAME_MESSAGE, messageMapper);
			mapperMap.put(NAME_MESSAGE_I18N, messageI18nMapper);
			mapperMap.put(NAME_STYLE, styleMapper);
			mapperMap.put(NAME_WIDGET, widgetMapper);
			mapperMap.put(NAME_PAGE_SET, pageSetMapper);
			mapperMap.put(NAME_PAGE, pageMapper);
			mapperMap.put(NAME_LAYOUT, layoutMapper);
			mapperMap.put(NAME_LOCALE, localeMapper);
			mapperMap.put(NAME_BUILDER, builderMapper);
		}
		return mapperMap.get(name);
	}

}
