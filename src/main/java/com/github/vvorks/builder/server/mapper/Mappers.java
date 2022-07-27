/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.github.vvorks.builder.common.logging.Logger;

@Component
public class Mappers {

	private static final Logger LOGGER = Logger.createLogger(Mappers.class);

	private static Mappers instance;

	public static Mappers get() {
		LOGGER.info("Mappers instance ", instance != null);
		return instance;
	}

	private static class Counter {
		private int maxProjectId;
		private int maxClassId;
		private int maxFieldId;
		private int maxQueryId;
		private int maxEnumId;
		private int maxMessageId;
		private int maxStyleId;
		private int maxWidgetId;
		private int maxPageSetId;
		private int maxPageId;
		private int maxLayoutId;
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

	private Map<String, BuilderMapper<?>> mapperMap;

	private Counter counter;

	public Mappers() {
		instance = this;
	}

	@PostConstruct
	private void initCounter() {
		Counter c = new Counter();
		c.maxProjectId = projectMapper.listSummary().getMaxProjectId();
		c.maxClassId = classMapper.listSummary().getMaxClassId();
		c.maxFieldId = fieldMapper.listSummary().getMaxFieldId();
		c.maxQueryId = queryMapper.listSummary().getMaxQueryId();
		c.maxEnumId = enumMapper.listSummary().getMaxEnumId();
		c.maxMessageId = messageMapper.listSummary().getMaxMessageId();
		c.maxStyleId = styleMapper.listSummary().getMaxStyleId();
		c.maxWidgetId = widgetMapper.listSummary().getMaxWidgetId();
		c.maxPageSetId = pageSetMapper.listSummary().getMaxPageSetId();
		c.maxPageId = pageMapper.listSummary().getMaxPageId();
		c.maxLayoutId = layoutMapper.listSummary().getMaxLayoutId();
		counter = c;
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

	/** プロジェクトのIdを新規発番する */
	public synchronized int newProjectId() {
		return ++counter.maxProjectId;
	}

	/** クラスのIdを新規発番する */
	public synchronized int newClassId() {
		return ++counter.maxClassId;
	}

	/** フィールドのIdを新規発番する */
	public synchronized int newFieldId() {
		return ++counter.maxFieldId;
	}

	/** クエリーのIdを新規発番する */
	public synchronized int newQueryId() {
		return ++counter.maxQueryId;
	}

	/** 列挙のIdを新規発番する */
	public synchronized int newEnumId() {
		return ++counter.maxEnumId;
	}

	/** メッセージのIdを新規発番する */
	public synchronized int newMessageId() {
		return ++counter.maxMessageId;
	}

	/** スタイルのIdを新規発番する */
	public synchronized int newStyleId() {
		return ++counter.maxStyleId;
	}

	/** ウィジェットのIdを新規発番する */
	public synchronized int newWidgetId() {
		return ++counter.maxWidgetId;
	}

	/** ページセットのIdを新規発番する */
	public synchronized int newPageSetId() {
		return ++counter.maxPageSetId;
	}

	/** ページのIdを新規発番する */
	public synchronized int newPageId() {
		return ++counter.maxPageId;
	}

	/** レイアウトのIdを新規発番する */
	public synchronized int newLayoutId() {
		return ++counter.maxLayoutId;
	}

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
			mapperMap.put("Widget", widgetMapper);
			mapperMap.put("PageSet", pageSetMapper);
			mapperMap.put("Page", pageMapper);
			mapperMap.put("Layout", layoutMapper);
			mapperMap.put("Locale", localeMapper);
		}
		return mapperMap.get(name);
	}

}
