package com.github.vvorks.builder.server.extender;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.EnumValueContent;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.server.mapper.ClassMapper;

@Component
public class EnumValueExtender {

	private static final String CLASS_NAME = "EnumValue";

	private static final String OWNER_KEY_NAME = "owner";

	private static final String KEY_NAME = "valueId";

	@Autowired
	private ClassMapper classMapper;

	@Autowired
	private FieldExtender fieldExtender;

	public String getTitleOrName(EnumValueContent v) {
		if (!Strings.isEmpty(v.getTitle())) {
			return v.getTitle();
		} else {
			return v.getValueId();
		}
	}

	public ClassContent getThisContent() {
		return classMapper.listContentIfNameIs(CLASS_NAME, 0, 0).get(0);
	}

	public FieldContent getThisKeyField() {
		ClassContent ref = getThisContent();
		return classMapper.listFieldsContentIfNameIs(ref, KEY_NAME, 0, 0).get(0);
	}

	public FieldContent getOwnerKeyField() {
		ClassContent ref = getThisContent();
		FieldContent fld = classMapper.listFieldsContentIfNameIs(ref, OWNER_KEY_NAME, 0, 0).get(0);
		List<FieldContent> props = new ArrayList<>();
		fieldExtender.extractKey(fld, new ArrayDeque<>(), props);
		return props.get(0);
	}

}
