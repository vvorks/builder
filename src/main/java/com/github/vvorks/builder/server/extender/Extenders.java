package com.github.vvorks.builder.server.extender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Extenders {

	@Autowired
	private ProjectExtender projectExtender;

	@Autowired
	private ClassExtender classExtender;

	@Autowired
	private FieldExtender fieldExtender;

	@Autowired
	private QueryExtender queryExtender;

	@Autowired
	private EnumExtender enumExtender;

	@Autowired
	private EnumValueExtender enumValueExtender;

	@Autowired
	private StyleExtender styleExtender;

	@Autowired
	private PageSetExtender pageSetExtender;

	@Autowired
	private PageExtender pageExtender;

	@Autowired
	private LayoutExtender layoutExtender;

	public ProjectExtender getProjectExtender() {
		return projectExtender;
	}

	public ClassExtender getClassExtender() {
		return classExtender;
	}

	public FieldExtender getFieldExtender() {
		return fieldExtender;
	}

	public QueryExtender getQueryExtender() {
		return queryExtender;
	}

	public EnumExtender getEnumExtender() {
		return enumExtender;
	}

	public EnumValueExtender getEnumValueExtender() {
		return enumValueExtender;
	}

	public StyleExtender getStyleExtender() {
		return styleExtender;
	}

	public PageSetExtender getPageSetExtender() {
		return pageSetExtender;
	}

	public PageExtender getPageExtender() {
		return pageExtender;
	}

	public LayoutExtender getLayoutExtender() {
		return layoutExtender;
	}

	public Object[] getExtenders() {
		return new Object[] {
				projectExtender,
				classExtender,
				fieldExtender,
				queryExtender,
				enumExtender,
				enumValueExtender,
				styleExtender,
				pageSetExtender,
				pageExtender,
				layoutExtender
		};
	}
}
