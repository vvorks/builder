/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.HashMap;
import java.util.Map;
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
			initCounter();
		}
		return bean;
	}

	private void initCounter() {
		BuilderContent obj;
		do {
			obj = builderMapper.get("Project");
			obj.setSurrogateCount(projectMapper.listSummary().getMaxProjectId());
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
		do {
			obj = builderMapper.get("Class");
			obj.setSurrogateCount(classMapper.listSummary().getMaxClassId());
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
		do {
			obj = builderMapper.get("Field");
			obj.setSurrogateCount(fieldMapper.listSummary().getMaxFieldId());
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
		do {
			obj = builderMapper.get("Query");
			obj.setSurrogateCount(queryMapper.listSummary().getMaxQueryId());
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
		do {
			obj = builderMapper.get("Enum");
			obj.setSurrogateCount(enumMapper.listSummary().getMaxEnumId());
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
		do {
			obj = builderMapper.get("Message");
			obj.setSurrogateCount(messageMapper.listSummary().getMaxMessageId());
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
		do {
			obj = builderMapper.get("Style");
			obj.setSurrogateCount(styleMapper.listSummary().getMaxStyleId());
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
		do {
			obj = builderMapper.get("Widget");
			obj.setSurrogateCount(widgetMapper.listSummary().getMaxWidgetId());
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
		do {
			obj = builderMapper.get("PageSet");
			obj.setSurrogateCount(pageSetMapper.listSummary().getMaxPageSetId());
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
		do {
			obj = builderMapper.get("Page");
			obj.setSurrogateCount(pageMapper.listSummary().getMaxPageId());
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
		do {
			obj = builderMapper.get("Layout");
			obj.setSurrogateCount(layoutMapper.listSummary().getMaxLayoutId());
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
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
	public synchronized int newProjectId() {
		BuilderContent obj;
		do {
			obj = builderMapper.get("Project");
			obj.setSurrogateCount(obj.getSurrogateCount() + 1);
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
		return obj.getSurrogateCount();
	}

	/** クラスのIdを新規発番する */
	public synchronized int newClassId() {
		BuilderContent obj;
		do {
			obj = builderMapper.get("Class");
			obj.setSurrogateCount(obj.getSurrogateCount() + 1);
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
		return obj.getSurrogateCount();
	}

	/** フィールドのIdを新規発番する */
	public synchronized int newFieldId() {
		BuilderContent obj;
		do {
			obj = builderMapper.get("Field");
			obj.setSurrogateCount(obj.getSurrogateCount() + 1);
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
		return obj.getSurrogateCount();
	}

	/** クエリーのIdを新規発番する */
	public synchronized int newQueryId() {
		BuilderContent obj;
		do {
			obj = builderMapper.get("Query");
			obj.setSurrogateCount(obj.getSurrogateCount() + 1);
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
		return obj.getSurrogateCount();
	}

	/** 列挙のIdを新規発番する */
	public synchronized int newEnumId() {
		BuilderContent obj;
		do {
			obj = builderMapper.get("Enum");
			obj.setSurrogateCount(obj.getSurrogateCount() + 1);
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
		return obj.getSurrogateCount();
	}

	/** メッセージのIdを新規発番する */
	public synchronized int newMessageId() {
		BuilderContent obj;
		do {
			obj = builderMapper.get("Message");
			obj.setSurrogateCount(obj.getSurrogateCount() + 1);
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
		return obj.getSurrogateCount();
	}

	/** スタイルのIdを新規発番する */
	public synchronized int newStyleId() {
		BuilderContent obj;
		do {
			obj = builderMapper.get("Style");
			obj.setSurrogateCount(obj.getSurrogateCount() + 1);
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
		return obj.getSurrogateCount();
	}

	/** ウィジェットのIdを新規発番する */
	public synchronized int newWidgetId() {
		BuilderContent obj;
		do {
			obj = builderMapper.get("Widget");
			obj.setSurrogateCount(obj.getSurrogateCount() + 1);
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
		return obj.getSurrogateCount();
	}

	/** ページセットのIdを新規発番する */
	public synchronized int newPageSetId() {
		BuilderContent obj;
		do {
			obj = builderMapper.get("PageSet");
			obj.setSurrogateCount(obj.getSurrogateCount() + 1);
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
		return obj.getSurrogateCount();
	}

	/** ページのIdを新規発番する */
	public synchronized int newPageId() {
		BuilderContent obj;
		do {
			obj = builderMapper.get("Page");
			obj.setSurrogateCount(obj.getSurrogateCount() + 1);
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
		return obj.getSurrogateCount();
	}

	/** レイアウトのIdを新規発番する */
	public synchronized int newLayoutId() {
		BuilderContent obj;
		do {
			obj = builderMapper.get("Layout");
			obj.setSurrogateCount(obj.getSurrogateCount() + 1);
			obj = builderMapper.update(obj) ? obj : null;
		} while (obj == null);
		return obj.getSurrogateCount();
	}

	public MapperInterface<?> getMapperOf(String name) {
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
			mapperMap.put("Widget", widgetMapper);
			mapperMap.put("PageSet", pageSetMapper);
			mapperMap.put("Page", pageMapper);
			mapperMap.put("Layout", layoutMapper);
			mapperMap.put("Locale", localeMapper);
			mapperMap.put("Builder", builderMapper);
		}
		return mapperMap.get(name);
	}

}
