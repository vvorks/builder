package com.github.vvorks.builder.server.extender;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.common.lang.Asserts;
import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.server.common.sql.SqlHelper;
import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.EnumContent;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.server.mapper.ClassMapper;
import com.github.vvorks.builder.server.mapper.EnumMapper;
import com.github.vvorks.builder.server.mapper.FieldMapper;

@Component
public class FieldExtender {

	private static final EnumMap<DataType, Class<?>> TYPE_MAP = new EnumMap<>(DataType.class);
	static {
		TYPE_MAP.put(DataType.KEY, Integer.TYPE);
		TYPE_MAP.put(DataType.REF, Class.class);
		TYPE_MAP.put(DataType.ENUM, Enum.class);
		TYPE_MAP.put(DataType.SET, Void.class);
		TYPE_MAP.put(DataType.BOOLEAN, Boolean.class);
		TYPE_MAP.put(DataType.INTEGER, Integer.class);
		TYPE_MAP.put(DataType.REAL, Double.class);
		TYPE_MAP.put(DataType.NUMERIC, BigDecimal.class);
		TYPE_MAP.put(DataType.DATE, Date.class);
		TYPE_MAP.put(DataType.STRING, String.class);
	}

	private static final EnumMap<DataType, String> SQLITE_TYPE_MAP = new EnumMap<>(DataType.class);
	static {
		String i = "INTEGER";
		String r = "REAL";
		String t = "TEXT";
		String n = "NUMERIC";
		SQLITE_TYPE_MAP.put(DataType.KEY, i);
		SQLITE_TYPE_MAP.put(DataType.REF, i);
		SQLITE_TYPE_MAP.put(DataType.ENUM, i);
		SQLITE_TYPE_MAP.put(DataType.BOOLEAN, i);
		SQLITE_TYPE_MAP.put(DataType.INTEGER, i);
		SQLITE_TYPE_MAP.put(DataType.REAL, r);
		SQLITE_TYPE_MAP.put(DataType.NUMERIC, n);
		SQLITE_TYPE_MAP.put(DataType.DATE, i);
		SQLITE_TYPE_MAP.put(DataType.STRING, t);
	}

	@Autowired
	private ClassMapper classMapper;

	@Autowired
	private FieldMapper fieldMapper;

	@Autowired
	private EnumMapper enumMapper;

	private SqlHelper sqlHelper = SqlHelper.getHelper();

	/**
	 * （再）初期化
	 */
	public FieldExtender init() {
		return this;
	}

	public String getTitleOrName(FieldContent fld) {
		if (!Strings.isEmpty(fld.getTitle())) {
			return fld.getTitle();
		} else {
			return fld.getFieldName();
		}
	}

	public String getWrapperType(FieldContent fld) {
		return getJavaType(fld, true);
	}

	public String getJavaType(FieldContent fld) {
		return getJavaType(fld, fld.isNullable());
	}

	private String getJavaType(FieldContent fld, boolean nullable) {
		String javaType;
		Class<?> cls = getJavaClass(fld, nullable);
		if (cls == Enum.class) {
			EnumContent e = enumMapper.get(fld.getErefEnumId());
			javaType = e.getEnumName();
		} else if (cls.getPackage() == Object.class.getPackage()) {
			javaType = cls.getSimpleName();
		} else {
			javaType = cls.getName();
		}
		return javaType;
	}

	private Class<?> getJavaClass(FieldContent fld, boolean nullable) {
		Class<?> cls = TYPE_MAP.get(fld.getType());
		if (cls == null) {
			cls = Object.class;
		} else if (cls == Boolean.class || cls == Boolean.TYPE) {
			cls = nullable ? Boolean.class : Boolean.TYPE;
		} else if (cls == Integer.class) {
			cls = getIntegerClass(fld, nullable);
		} else if (cls == Integer.TYPE) {
			cls = nullable ? Integer.class : Integer.TYPE;
		} else if (cls == Double.class) {
			cls = getRealClass(fld, nullable);
		}
		return cls;
	}

	private Class<?> getIntegerClass(FieldContent fld, boolean nullable) {
		Class<?> cls;
		int width = fld.getWidth();
		if (width == 0) {
			cls = nullable ? Integer.class : Integer.TYPE;
		} else if (width <= 8) {
			cls = nullable ? Byte.class : Byte.TYPE;
		} else if (width <= 16) {
			cls = nullable ? Short.class : Short.TYPE;
		} else if (width <= 32) {
			cls = nullable ? Integer.class : Integer.TYPE;
		} else {
			cls = nullable ? Long.class : Long.TYPE;
		}
		return cls;
	}

	private Class<?> getRealClass(FieldContent fld, boolean nullable) {
		Class<?> cls;
		int width = fld.getWidth();
		if (width == 0) {
			cls = nullable ? Double.class : Double.TYPE;
		} else if (width <= 32) {
			cls = nullable ? Float.class : Float.TYPE;
		} else {
			cls = nullable ? Double.class : Double.TYPE;
		}
		return cls;
	}

	public String getCalcuratedType(FieldContent fld) {
		Class<?> cls = getJavaClass(fld, false);
		if (cls == Byte.TYPE || cls == Short.TYPE) {
			cls = Integer.TYPE;
		}
		String javaType;
		if (cls.getPackage() == Object.class.getPackage()) {
			javaType = cls.getSimpleName();
		} else {
			javaType = cls.getName();
		}
		return javaType;
	}

	public String getUpperName(FieldContent fld) {
		return Strings.toFirstUpper(fld.getFieldName());
	}

	public String getGetterName(FieldContent fld) {
		if (fld.getType() == DataType.BOOLEAN) {
			return "is" + getUpperName(fld);
		} else {
			return "get" + getUpperName(fld);
		}
	}

	public String getSetterName(FieldContent fld) {
		return "set" + getUpperName(fld);
	}

	public boolean isPrimitiveOrEnum(FieldContent fld) {
		Class<?> cls = getJavaClass(fld, fld.isNullable());
		return cls.isPrimitive() || cls.isInstance(Enum.class);
	}

	public boolean isNotNullObject(FieldContent fld) {
		return !fld.isNullable() && !getJavaClass(fld, false).isPrimitive();
	}

	public String getColumnName(FieldContent fld) {
		return SqlWriter.COLUMN_PREFIX + Strings.toUpperSnake(fld.getFieldName());
	}

	public String getColumnType(FieldContent fld) {
		return SQLITE_TYPE_MAP.get(fld.getType());
	}

	public boolean isNotNull(FieldContent fld) {
		return !fld.isNullable();
	}

	public String getTimestamp(FieldContent fld) {
		if (ClassExtender.LAST_UPDATED_AT.equals(fld.getFieldName())) {
			return sqlHelper.getNow();
		} else {
			return null;
		}
	}

	public ClassContent getOwner(FieldContent fld) {
		return classMapper.get(fld.getOwnerClassId());
	}

	public ClassContent getCref(FieldContent fld) {
		return fieldMapper.getCref(fld);
	}

	public FieldContent getFref(FieldContent fld) {
		return fieldMapper.getFref(fld);
	}

	public List<FieldContent> getRefKeyFields(FieldContent fld) {
		Asserts.require(fld.getType() == DataType.REF);
		List<FieldContent> props = new ArrayList<>();
		Deque<FieldContent> stack = new ArrayDeque<>();
		extractKey(fld, stack, props);
		return props;
	}

	public String getRefKeyFieldName(FieldContent fld) {
		return fld.getFieldName();
	}

	public String getRefKeyColumnName(FieldContent fld) {
		return SqlWriter.COLUMN_PREFIX + Strings.toUpperSnake((trimLeading(fld)));
	}

	private String trimLeading(FieldContent fld) {
		ClassContent cls = getOwner(fld);
		for (FieldContent leading : classMapper.listFieldsContent(cls, 0, 0)) {
			if (isLeadingField(fld, leading)) {
				return fld.getFieldName().substring(leading.getFieldName().length());
			}
		}
		return fld.getFieldName();
	}

	private boolean isLeadingField(FieldContent field, FieldContent leading) {
		String fieldName = field.getFieldName();
		String leadingName = leading.getFieldName();
		return	fieldName.startsWith(leadingName) &&
				Character.isUpperCase(fieldName.charAt(leadingName.length()));
	}

	public List<FieldContent> getInvKeyFields(FieldContent fld) {
		Asserts.require(fld.getType() == DataType.SET);
		return getRefKeyFields(getFref(fld));
	}

	public String getInvKeyColumnName(FieldContent fld) {
		return getColumnName(fld);
	}

	public String getInvKeyFieldName(FieldContent fld) {
		return Strings.toFirstLower(trimLeading(fld));
	}

	public void extractKey(FieldContent fld, Deque<FieldContent> stack, List<FieldContent> into) {
		extractKey(fld, false, stack, into);
	}

	public void extractKey(FieldContent fld, boolean addTitle, Deque<FieldContent> stack, List<FieldContent> into) {
		DataType type = fld.getType();
		switch (type) {
		case SET:
			break;
		case REF:
			ClassContent refClass = classMapper.get(fld.getCrefClassId());
			stack.push(fld);
			for (FieldContent f : classMapper.listFieldsContent(refClass, 0, 0)) {
				if (f.isPk()) {
					extractKey(f, stack, into);
				}
			}
			stack.pop();
			//タイトルプロパティの追加
			if (addTitle) {
				FieldContent title = new FieldContent();
				title.setFieldName(fld.getFieldName() + "_title");
				title.setTitle(getTitleOrName(fld) + "の見出し");
				title.setType(DataType.STRING);
				title.setWidth(0);
				title.setScale(0);
				into.add(title);
			}
			break;
		default:
			if (stack.isEmpty()) {
				into.add(fld);
			} else {
				stack.push(fld);
				StringBuilder names = new StringBuilder();
				StringBuilder titles = new StringBuilder();
				FieldContent k = new FieldContent();
				Iterator<FieldContent> itr = stack.descendingIterator();
				FieldContent e = itr.next();
				names.append(e.getFieldName());
				titles.append(getTitleOrName(e));
				k.setOwnerClassId(e.getOwnerClassId());
				while (itr.hasNext()) {
					e = itr.next();
					names.append(Strings.toFirstUpper(e.getFieldName()));
					titles.append("の").append(getTitleOrName(e)); //TODO I18n
				}
				k.setFieldName(names.toString());
				k.setTitle(titles.toString());
				k.setType(e.getType());
				k.setWidth(e.getWidth());
				k.setScale(e.getScale());
				into.add(k);
				stack.pop();
			}
			break;
		}
	}

}
