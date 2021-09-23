package com.github.vvorks.builder.server.extender;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.server.domain.ClassDto;
import com.github.vvorks.builder.server.domain.FieldDto;
import com.github.vvorks.builder.server.mapper.ClassMapper;

@Component
public class ClassExtender {

	@Autowired
	private ClassMapper classMapper;

	@Autowired
	private FieldExtender fieldExtender;

	public List<FieldDto> getFields(ClassDto cls) {
		return classMapper.listFields(cls, 0, 0);
	}

	public List<FieldDto> getProperties(ClassDto cls) {
		List<FieldDto> props = new ArrayList<>();
		Deque<FieldDto> stack = new ArrayDeque<>();
		for (FieldDto field : getFields(cls)) {
			fieldExtender.extractKey(field, stack, props);
		}
		return props;
	}

}
