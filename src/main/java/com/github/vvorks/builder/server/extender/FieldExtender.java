package com.github.vvorks.builder.server.extender;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	private EnumMapper enumMapper;

	@Autowired
	private FieldMapper fieldMapper;


	public void extractKey(FieldDto fld, String prefix, List<FieldDto> into) {
		DataType type = fld.getType();
		if (type == DataType.FIELD_REF) {
			FieldDto refField = fieldMapper.get(fld.getFrefFieldId());
			ClassDto refClass = classMapper.get(refField.getOwnerClassId());
			String newPrefix = concatCamel(prefix, fld.getFieldName());
			for (FieldDto f : classMapper.listPkFields(refClass)) {
				extractKey(f, newPrefix, into);
			}
		} else if (type == DataType.CLASS_REF) {
			ClassDto refClass = classMapper.get(fld.getCrefClassId());
			String newPrefix = concatCamel(prefix, fld.getFieldName());
			for (FieldDto f : classMapper.listPkFields(refClass)) {
				extractKey(f, newPrefix, into);
			}
		} else if (type != DataType.INVERT_REF) {
			if (prefix.isEmpty()) {
				into.add(fld);
			} else {
				FieldDto k = new FieldDto();
				k.setFieldName(concatCamel(prefix, fld.getFieldName()));
				k.setType(fld.getType());
				into.add(k);
			}
		}
	}

	private String concatCamel(String a, String b) {
		if (a.isEmpty()) {
			return b;
		}
		return a + Strings.toFirstUpper(b);
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
			int width = fld.getWidth();
			if (width == 1 || width == 2 || width == 4 || width == 8) {
				cls = INTEGER_TYPES[log2(width)];
			}
		} else if (cls == Double.class) {
			if (fld.getWidth() == 4) {
				cls = Float.class;
			}
		}
		return cls;
	}

	public static int log2(int x) {
		return (int)(Math.log(x) / Math.log(2));
	}

	public String getGetterName(FieldDto fld) {
		if (fld.getType() == DataType.BOOLEAN) {
			return "is" + Strings.toFirstUpper(fld.getFieldName());
		} else {
			return "get" + Strings.toFirstUpper(fld.getFieldName());
		}
	}

	public String getSetterName(FieldDto fld) {
		return "set" + Strings.toFirstUpper(fld.getFieldName());
	}

	public boolean isPrimitiveOrEnum(FieldDto fld) {
		Class<?> cls = getJavaClass(fld);
		return cls.isPrimitive() || cls.isInstance(Enum.class);
	}

}
