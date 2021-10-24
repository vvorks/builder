package com.github.vvorks.builder.server.extender;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.server.domain.ClassDto;
import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.FieldDto;
import com.github.vvorks.builder.server.mapper.ClassMapper;

@Component
public class ClassExtender {

	public static final String TABLE_PREFIX = "T_";

	@Autowired
	private ClassMapper classMapper;

	@Autowired
	private FieldExtender fieldExtender;

	public String getTitleOrName(ClassDto cls) {
		if (!Strings.isEmpty(cls.getTitle())) {
			return cls.getTitle();
		} else {
			return cls.getClassName();
		}
	}

	public String getTableName(ClassDto cls) {
		return TABLE_PREFIX + Strings.toUpperSnake(cls.getClassName());
	}

	public List<FieldDto> getProperties(ClassDto cls) {
		return getProperties(cls, (fld) -> true);
	}

	public List<FieldDto> getKeys(ClassDto cls) {
		return getProperties(cls, (fld) ->  fld.isPk());
	}

	public List<FieldDto> getNotKeys(ClassDto cls) {
		return getProperties(cls, (fld) -> !fld.isPk());
	}

	private List<FieldDto> getProperties(ClassDto cls, Predicate<FieldDto> filter) {
		List<FieldDto> props = new ArrayList<>();
		Deque<FieldDto> stack = new ArrayDeque<>();
		for (FieldDto field : getFields(cls)) {
			if (filter.test(field)) {
				fieldExtender.extractKey(field, stack, props);
			}
		}
		return props;
	}

	public List<FieldDto> getRefs(ClassDto cls) {
		return getFields(cls, fld -> fld.getType() == DataType.REF);
	}

	public List<FieldDto> getSets(ClassDto cls) {
		return getFields(cls, fld -> fld.getType() == DataType.SET);
	}

	public List<FieldDto> getFindConditions(ClassDto cls) {
		return getProperties(cls, (fld) -> !fld.isPk() && !fld.isNullable());
	}

	public List<FieldDto> getFields(ClassDto cls, Predicate<FieldDto> filter) {
		List<FieldDto> result = new ArrayList<>();
		for (FieldDto field : getFields(cls)) {
			if (filter.test(field)) {
				result.add(field);
			}
		}
		return result;
	}

	public List<FieldDto> getFields(ClassDto cls) {
		return classMapper.listFields(cls, 0, 0);
	}

}
