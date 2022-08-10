package com.github.vvorks.builder.server.extender;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.server.domain.PageContent;
import com.github.vvorks.builder.server.domain.PageSetContent;
import com.github.vvorks.builder.server.mapper.PageSetMapper;
import com.github.vvorks.builder.shared.common.lang.Strings;

@Component
public class PageSetExtender {

	@Autowired
	private PageSetMapper pageSetMapper;

	public List<PageContent> getPages(PageSetContent ps) {
		List<PageContent> list = pageSetMapper.listPagesContent(ps, 0, 0);
		return list;
	}

	public String getUpperName(PageSetContent ps) {
		return Strings.toFirstUpper(ps.getPageSetName());
	}

}
