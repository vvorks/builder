package com.github.vvorks.builder.server.extender;

import java.lang.reflect.Field;
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
import com.github.vvorks.builder.server.domain.ClassDto;
import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.EnumDto;
import com.github.vvorks.builder.server.domain.FieldDto;
import com.github.vvorks.builder.server.mapper.ClassMapper;
import com.github.vvorks.builder.server.mapper.EnumMapper;
import com.github.vvorks.builder.server.mapper.FieldMapper;

@Component
public class FieldExtender {

	public static final String COLUMN_PREFIX = "F_";

	private static final EnumMap<DataType, Class<?>> TYPE_MAP = new EnumMap<>(DataType.class);
	static {
		TYPE_MAP.put(DataType.KEY, Integer.TYPE);
		TYPE_MAP.put(DataType.CLASS_REF, Class.class);
		TYPE_MAP.put(DataType.ENUM_REF, Enum.class);
		TYPE_MAP.put(DataType.FIELD_REF, Field.class);
		TYPE_MAP.put(DataType.INVERT_REF, Void.class);
		TYPE_MAP.put(DataType.BOOLEAN, Boolean.class);
		TYPE_MAP.put(DataType.INTEGER, Integer.class);
		TYPE_MAP.put(DataType.REAL, Double.class);
		TYPE_MAP.put(DataType.NUMERIC, BigDecimal.class);
		TYPE_MAP.put(DataType.DATE, Date.class);
		TYPE_MAP.put(DataType.STRING, String.class);
	}

	private static final Class<?>[] INTEGER_TYPES = {
		Byte.class, Short.class, Integer.class, Long.class
	};

	@Autowired
	private ClassMapper classMapper;

	@Autowired
	private FieldMapper fieldMapper;

	@Autowired
	private EnumMapper enumMapper;

	public String getTitleOrName(FieldDto fld) {
		if (!Strings.isEmpty(fld.getTitle())) {
			return fld.getTitle();
		} else {
			return fld.getFieldName();
		}
	}

	public String getJavaType(FieldDto fld) {
		String javaType;
		Class<?> cls = getJavaClass(fld);
		if (cls == Enum.class) {
			EnumDto e = enumMapper.get(fld.getErefEnumId());
			javaType = e.getEnumName();
		} else if (cls.getPackage() == Object.class.getPackage()) {
			javaType = cls.getSimpleName();
		} else {
			javaType = cls.getName();
		}
		return javaType;
	}

	public Class<?> getJavaClass(FieldDto fld) {
		Class<?> cls = TYPE_MAP.get(fld.getType());
		if (cls == null) {
			cls = Object.class;
		} else if (cls == Integer.class) {
			Integer width = fld.getWidth();
			if (width != null && (width == 1 || width == 2 || width == 4 || width == 8)) {
				cls = INTEGER_TYPES[log2(width)];
			}
		} else if (cls == Double.class) {
			Integer width = fld.getWidth();
			if (width != null && width == 4) {
				cls = Float.class;
			}
		}
		return cls;
	}

	public static int log2(int x) {
		return (int)(Math.log(x) / Math.log(2));
	}

	public String getUpperName(FieldDto fld) {
		return Strings.toFirstUpper(fld.getFieldName());
	}

	public String getGetterName(FieldDto fld) {
		if (fld.getType() == DataType.BOOLEAN) {
			return "is" + getUpperName(fld);
		} else {
			return "get" + getUpperName(fld);
		}
	}

	public String getSetterName(FieldDto fld) {
		return "set" + getUpperName(fld);
	}

	public boolean isPrimitiveOrEnum(FieldDto fld) {
		Class<?> cls = getJavaClass(fld);
		return cls.isPrimitive() || cls.isInstance(Enum.class);
	}

	public String getColumnName(FieldDto fld) {
		return COLUMN_PREFIX + Strings.toUpperSnake(fld.getFieldName());
	}

	public ClassDto getOwner(FieldDto fld) {
		return classMapper.get(fld.getOwnerClassId());
	}

	public ClassDto getCref(FieldDto fld) {
		return classMapper.get(fld.getCrefClassId());
	}

	public FieldDto getFref(FieldDto fld) {
		return fieldMapper.get(fld.getFrefFieldId());
	}

	public List<FieldDto> getRefKeyFields(FieldDto fld) {
		Asserts.require(fld.getType() == DataType.FIELD_REF);
		List<FieldDto> props = new ArrayList<>();
		Deque<FieldDto> stack = new ArrayDeque<>();
		extractKey(fld, stack, props);
		return props;
	}

	public String getRefKeyColumnName(FieldDto fld) {
		return COLUMN_PREFIX + Strings.toUpperSnake((trimLeading(fld)));
	}

	public String getRefKeyFieldName(FieldDto fld) {
		return fld.getFieldName();
	}

	private String trimLeading(FieldDto fld) {
		ClassDto owner = getOwner(fld);
		for (FieldDto leading : classMapper.listFields(owner, 0, 0)) {
			if (isLeadingField(fld, leading)) {
				return fld.getFieldName().substring(leading.getFieldName().length());
			}
		}
		return fld.getFieldName();
	}

	private boolean isLeadingField(FieldDto field, FieldDto leading) {
		String fieldName = field.getFieldName();
		String leadingName = leading.getFieldName();
		return	leading.getType() == DataType.FIELD_REF &&
				fieldName.startsWith(leadingName) &&
				Character.isUpperCase(fieldName.charAt(leadingName.length()));
	}

	public List<FieldDto> getInvKeyFields(FieldDto fld) {
		Asserts.require(fld.getType() == DataType.INVERT_REF);
		return getRefKeyFields(getFref(fld));
	}

	public String getInvKeyColumnName(FieldDto fld) {
		return getColumnName(fld);
	}

	public String getInvKeyFieldName(FieldDto fld) {
		return Strings.toFirstLower(trimLeading(fld));
	}


	public void extractKey(FieldDto fld, Deque<FieldDto> stack, List<FieldDto> into) {
		DataType type = fld.getType();
		if (type == DataType.FIELD_REF) {
			FieldDto refField = fieldMapper.get(fld.getFrefFieldId());
			ClassDto refClass = classMapper.get(refField.getOwnerClassId());
			stack.push(fld);
			for (FieldDto f : classMapper.listFields(refClass, 0, 0)) {
				Boolean pk = f.isPk();
				if (pk != null && pk) {
					extractKey(f, stack, into);
				}
			}
			stack.pop();
		} else if (type == DataType.CLASS_REF) {
			ClassDto refClass = classMapper.get(fld.getCrefClassId());
			stack.push(fld);
			for (FieldDto f : classMapper.listFields(refClass, 0, 0)) {
				Boolean pk = f.isPk();
				if (pk != null && pk) {
					extractKey(f, stack, into);
				}
			}
			stack.pop();
		} else if (type != DataType.INVERT_REF) {
			if (stack.isEmpty()) {
				into.add(fld);
			} else {
				stack.push(fld);
				StringBuilder names = new StringBuilder();
				StringBuilder titles = new StringBuilder();
				FieldDto k = new FieldDto();
				Iterator<FieldDto> itr = stack.descendingIterator();
				FieldDto e = itr.next();
				names.append(e.getFieldName());
				titles.append(e.getTitle());
				k.setOwnerClassId(e.getOwnerClassId());
				while (itr.hasNext()) {
					e = itr.next();
					names.append(Strings.toFirstUpper(e.getFieldName()));
					//TODO I18n
					titles.append("の").append(e.getTitle());
				}
				k.setFieldName(names.toString());
				k.setTitle(titles.toString());
				k.setType(e.getType());
				into.add(k);
				stack.pop();
			}
		}
	}

}
