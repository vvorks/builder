/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME,
		  include = JsonTypeInfo.As.PROPERTY,
		  property = "_type")
@JsonSubTypes({
		@Type(value = ProjectSubject.class, name = "Project"),
		@Type(value = ProjectI18nSubject.class, name = "ProjectI18n"),
		@Type(value = ClassSubject.class, name = "Class"),
		@Type(value = ClassI18nSubject.class, name = "ClassI18n"),
		@Type(value = FieldSubject.class, name = "Field"),
		@Type(value = FieldI18nSubject.class, name = "FieldI18n"),
		@Type(value = QuerySubject.class, name = "Query"),
		@Type(value = EnumSubject.class, name = "Enum"),
		@Type(value = EnumI18nSubject.class, name = "EnumI18n"),
		@Type(value = EnumValueSubject.class, name = "EnumValue"),
		@Type(value = EnumValueI18nSubject.class, name = "EnumValueI18n"),
		@Type(value = MessageSubject.class, name = "Message"),
		@Type(value = MessageI18nSubject.class, name = "MessageI18n"),
		@Type(value = StyleSubject.class, name = "Style"),
		@Type(value = PageSetSubject.class, name = "PageSet"),
		@Type(value = PageSubject.class, name = "Page"),
		@Type(value = LayoutSubject.class, name = "Layout"),
		@Type(value = LocaleSubject.class, name = "Locale"),
		@Type(value = BuilderSubject.class, name = "Builder")
		})
public abstract class Subject {

	private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSSZ";

	private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT =
			ThreadLocal.withInitial(() -> new SimpleDateFormat(DATE_PATTERN));

	private static final Map<String, Function<Map<String, Object>, Subject>> CREATORS = new HashMap<>();
	static {
		CREATORS.put("Project", map -> ProjectSubject.createFrom(map));
		CREATORS.put("ProjectI18n", map -> ProjectI18nSubject.createFrom(map));
		CREATORS.put("Class", map -> ClassSubject.createFrom(map));
		CREATORS.put("ClassI18n", map -> ClassI18nSubject.createFrom(map));
		CREATORS.put("Field", map -> FieldSubject.createFrom(map));
		CREATORS.put("FieldI18n", map -> FieldI18nSubject.createFrom(map));
		CREATORS.put("Query", map -> QuerySubject.createFrom(map));
		CREATORS.put("Enum", map -> EnumSubject.createFrom(map));
		CREATORS.put("EnumI18n", map -> EnumI18nSubject.createFrom(map));
		CREATORS.put("EnumValue", map -> EnumValueSubject.createFrom(map));
		CREATORS.put("EnumValueI18n", map -> EnumValueI18nSubject.createFrom(map));
		CREATORS.put("Message", map -> MessageSubject.createFrom(map));
		CREATORS.put("MessageI18n", map -> MessageI18nSubject.createFrom(map));
		CREATORS.put("Style", map -> StyleSubject.createFrom(map));
		CREATORS.put("PageSet", map -> PageSetSubject.createFrom(map));
		CREATORS.put("Page", map -> PageSubject.createFrom(map));
		CREATORS.put("Layout", map -> LayoutSubject.createFrom(map));
		CREATORS.put("Locale", map -> LocaleSubject.createFrom(map));
		CREATORS.put("Builder", map -> BuilderSubject.createFrom(map));
	}

	public static Subject createFrom(Map<String, Object> args) {
		String type = args.get("_type").toString();
		return CREATORS.get(type).apply(args);
	}

	protected static Boolean asBoolean(Object obj) {
		if (obj == null) {
			return null;
		} else if (obj instanceof Boolean) {
			return (Boolean) obj;
		}
		return Boolean.parseBoolean(obj.toString());
	}

	protected static Integer asInt(Object obj) {
		if (obj == null) {
			return null;
		} else if (obj instanceof Number) {
			return ((Number)obj).intValue();
		}
		return Integer.decode(obj.toString());
	}

	protected static Long asLong(Object obj) {
		if (obj == null) {
			return null;
		} else if (obj instanceof Number) {
			return ((Number)obj).longValue();
		}
		return Long.decode(obj.toString());
	}

	protected static Float asFloat(Object obj) {
		if (obj == null) {
			return null;
		} else if (obj instanceof Number) {
			return ((Number)obj).floatValue();
		}
		return Float.parseFloat(obj.toString());
	}

	protected static Double asDouble(Object obj) {
		if (obj == null) {
			return null;
		} else if (obj instanceof Number) {
			return ((Number)obj).doubleValue();
		}
		return Double.parseDouble(obj.toString());
	}

	protected static BigDecimal asDecimal(Object obj) {
		if (obj == null) {
			return null;
		} else if (obj instanceof BigDecimal) {
			return (BigDecimal) obj;
		} else if (obj instanceof Number) {
			return BigDecimal.valueOf(((Number)obj).doubleValue());
		}
		return new BigDecimal(obj.toString());
	}

	protected static Date asDate(Object obj) {
		if (obj == null) {
			return null;
		} else if (obj instanceof Date) {
			return (Date) obj;
		} else if (obj instanceof Number) {
			return new Date(((Number)obj).longValue());
		}
		try {
			return DATE_FORMAT.get().parse(obj.toString());
		} catch (ParseException err) {
			throw new RuntimeException(err);
		}
	}

	protected static String asString(Object obj) {
		if (obj == null) {
			return null;
		}
		return obj.toString();
	}

	public abstract String get_title();

}
