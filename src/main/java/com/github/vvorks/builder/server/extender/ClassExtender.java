package com.github.vvorks.builder.server.extender;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.common.lang.Encodable;
import com.github.vvorks.builder.common.lang.Iterables;
import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.common.logging.Logger;
import com.github.vvorks.builder.server.common.sql.SqlHelper;
import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.EnumContent;
import com.github.vvorks.builder.server.domain.EnumValueContent;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.server.domain.ProjectContent;
import com.github.vvorks.builder.server.domain.QueryContent;
import com.github.vvorks.builder.server.mapper.ClassMapper;
import com.github.vvorks.builder.server.mapper.EnumMapper;
import com.github.vvorks.builder.server.mapper.EnumValueMapper;
import com.github.vvorks.builder.server.mapper.FieldMapper;
import com.github.vvorks.builder.server.mapper.ProjectMapper;
import com.github.vvorks.builder.server.mapper.QueryMapper;

@Component
public class ClassExtender {

	public static final Logger LOGGER = Logger.createLogger(ClassExtender.class);

	@Autowired
	private ProjectMapper projectMapper;

	@Autowired
	private ClassMapper classMapper;

	@Autowired
	private FieldMapper fieldMapper;

	@Autowired
	private QueryMapper queryMapper;

	@Autowired
	private EnumMapper enumMapper;

	@Autowired
	private EnumValueMapper enumValueMapper;

	@Autowired
	private FieldExtender fieldExtender;

	private static final String LAST_UPDATED_AT = "_lastUpdatedAt";

	private SqlHelper sqlHelper = SqlHelper.getHelper();

	public String getTitleOrName(ClassContent cls) {
		if (!Strings.isEmpty(cls.getTitle())) {
			return cls.getTitle();
		} else {
			return cls.getClassName();
		}
	}

	public String getLowerName(ClassContent cls) {
		return Strings.toFirstLower(cls.getClassName());
	}

	public String getTableName(ClassContent cls) {
		return SqlWriter.TABLE_PREFIX + Strings.toUpperSnake(cls.getClassName());
	}

	public List<FieldContent> getProperties(ClassContent cls) {
		return getProperties(cls, fld -> true);
	}

	public List<FieldContent> getKeys(ClassContent cls) {
		return getProperties(cls, fld ->  fld.isPk());
	}

	public List<FieldContent> getNotKeys(ClassContent cls) {
		return getProperties(cls, fld -> !fld.isPk());
	}

	private List<FieldContent> getProperties(ClassContent cls, Predicate<FieldContent> filter) {
		List<FieldContent> props = new ArrayList<>();
		Deque<FieldContent> stack = new ArrayDeque<>();
		for (FieldContent field : getFields(cls)) {
			if (filter.test(field)) {
				fieldExtender.extractKey(field, stack, props);
			}
		}
		return props;
	}

	public List<FieldContent> getRefs(ClassContent cls) {
		return getFields(cls, fld -> fld.getType() == DataType.REF);
	}

	public List<FieldContent> getSets(ClassContent cls) {
		return getFields(cls, fld -> fld.getType() == DataType.SET);
	}

	public List<FieldContent> getFields(ClassContent cls, Predicate<FieldContent> filter) {
		List<FieldContent> result = new ArrayList<>();
		for (FieldContent field : getFields(cls)) {
			if (filter.test(field)) {
				result.add(field);
			}
		}
		return result;
	}

	public List<FieldContent> getFields(ClassContent cls) {
		List<FieldContent> fields = classMapper.listFieldsContent(cls, 0, 0);
		//追加フィールド挿入
		FieldContent lastUpdated = new FieldContent();
		lastUpdated.setFieldName(LAST_UPDATED_AT);
		lastUpdated.setTitle("最終更新時刻");
		lastUpdated.setType(DataType.DATE);
		lastUpdated.setPk(false);
		lastUpdated.setNeedsMax(true);
		fields.add(lastUpdated);
		return fields;
	}

	public List<QueryContent> getQueries(ClassContent cls) {
		return classMapper.listQueriesContent(cls, 0, 0);
	}

	private static class Joint {
		private final Class<?> type;
		private final Function<ClassExtender, List<?>> func;
		private FieldContent[] fields;
		private Method[] getters;
		private Joint(Class<?> type, Function<ClassExtender, List<?>> func) {
			this.type = type;
			this.func = func;
			this.fields = null;
			this.getters = null;
		}
		private boolean hasGetters() {
			return getters != null;
		}
	}

	private static final Map<String, Joint> JOINT_MAP = new HashMap<>();
	static {
		JOINT_MAP.put("Project", new Joint(ProjectContent.class, me -> me.projectMapper.listContent(0, 0)));
		JOINT_MAP.put("Class", new Joint(ClassContent.class, me -> me.classMapper.listContent(0, 0)));
		JOINT_MAP.put("Field", new Joint(FieldContent.class, me -> me.fieldMapper.listContent(0, 0)));
		JOINT_MAP.put("Query", new Joint(QueryContent.class, me -> me.queryMapper.listContent(0, 0)));
		JOINT_MAP.put("Enum", new Joint(EnumContent.class, me -> me.enumMapper.listContent(0, 0)));
		JOINT_MAP.put("EnumValue", new Joint(EnumValueContent.class, me -> me.enumValueMapper.listContent(0, 0)));
	}

	public List<String[]> getValues(ClassContent cls) {
		String name = cls.getClassName();
		Joint joint = JOINT_MAP.get(name);
		if (joint == null) {
			return Collections.emptyList();
		}
		if (!joint.hasGetters()) {
			setupJoint(joint, cls);
		}
		List<String[]> recs = new ArrayList<>();
		List<?> list = joint.func.apply(this);
		int n = joint.getters.length;
		for (Object rec : list) {
			String[] flds = new String[n];
			for (int i = 0; i < n; i++) {
				if (joint.fields[i].getFieldName().equals(LAST_UPDATED_AT)) {
					flds[i] = sqlHelper.getNow();
				} else {
					flds[i] = getConstant(rec, joint, i);
				}
			}
			recs.add(flds);
		}
		return recs;
	}

	private void setupJoint(Joint joint, ClassContent cls) {
		List<FieldContent> list = new ArrayList<>();
		Iterables.addAll(list, getProperties(cls));
		FieldContent[] fields = list.toArray(new FieldContent[list.size()]);
		int n = fields.length;
		Method[] getters = new Method[n];
		for (int i = 0; i < n; i++) {
			String getterName = fieldExtender.getGetterName(fields[i]);
			try {
				getters[i] = joint.type.getMethod(getterName);
			} catch (NoSuchMethodException|SecurityException err) {
				LOGGER.error(err, "getter %s not found.", getterName);
				throw new RuntimeException(err);
			}
		}
		joint.fields = fields;
		joint.getters = getters;
	}

	private String getConstant(Object rec, Joint joint, int index) {
		Object value;
		Method m = joint.getters[index];
		try {
			value = m.invoke(rec);
		} catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException err) {
			LOGGER.error(err, "getter %s invoke error.", m.getName());
			throw new RuntimeException(err);
		}
		return toSqlConstants(value, joint.fields[index]);
	}

	private String toSqlConstants(Object value, FieldContent fld) {
		String result;
		switch (fld.getType()) {
		case BOOLEAN:
			if (value != null) {
				result = sqlHelper.getBooleanValue((Boolean) value);
			} else if (!fld.isNullable()) {
				result = sqlHelper.getBooleanValue(false);
			} else {
				result = sqlHelper.getNullValue();
			}
			break;
		case KEY:
		case INTEGER:
		case REAL:
		case NUMERIC:
			if (value != null) {
				result = String.valueOf(value);
			} else if (!fld.isNullable()) {
				result = "0";
			} else {
				result = sqlHelper.getNullValue();
			}
			break;
		case DATE:
			if (value != null) {
				result = sqlHelper.getDateValue((Date)value);
			} else if (!fld.isNullable()) {
				result = "0";
			} else {
				result = sqlHelper.getNullValue();
			}
			break;
		case STRING:
			if (value != null) {
				result = sqlHelper.quote((String) value);
			} else if (!fld.isNullable()) {
				result = sqlHelper.quote("");
			} else {
				result = sqlHelper.getNullValue();
			}
			break;
		case ENUM:
			EnumContent enc = enumMapper.get(fld.getErefEnumId());
			if (value != null) {
				if (enc.isEncodeString()) {
					@SuppressWarnings("unchecked")
					Encodable<String> s = (Encodable<String>) value;
					result = sqlHelper.quote(s.encode());
				} else {
					@SuppressWarnings("unchecked")
					Encodable<Integer> i = (Encodable<Integer>) value;
					result = String.valueOf(i.encode());
				}
			} else if (!fld.isNullable()) {
				if (enc.isEncodeString()) {
					result = sqlHelper.quote("");
				} else {
					result = "0";
				}
			} else {
				result = sqlHelper.getNullValue();
			}
			break;
		default:
			result = "0";
			break;
		}
		return result;
	}

}
