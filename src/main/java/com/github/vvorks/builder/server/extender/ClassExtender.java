package com.github.vvorks.builder.server.extender;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.server.domain.QueryContent;
import com.github.vvorks.builder.server.mapper.ClassMapper;

@Component
public class ClassExtender {

	@Autowired
	private ClassMapper classMapper;

	@Autowired
	private FieldExtender fieldExtender;

	public String getTitleOrName(ClassContent cls) {
		if (!Strings.isEmpty(cls.getTitle())) {
			return cls.getTitle();
		} else {
			return cls.getClassName();
		}
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
		return classMapper.listFieldsContent(cls, 0, 0);
	}

	public List<QueryContent> getQueries(ClassContent cls) {
		return classMapper.listQueriesContent(cls, 0, 0);
	}

}
