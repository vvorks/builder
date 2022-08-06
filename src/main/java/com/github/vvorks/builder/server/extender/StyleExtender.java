package com.github.vvorks.builder.server.extender;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.server.domain.StyleContent;
import com.github.vvorks.builder.server.mapper.StyleMapper;
import com.github.vvorks.builder.shared.common.lang.Strings;

@Component
public class StyleExtender {

	/** スタイルのMapper */
	@Autowired
	private StyleMapper styleMapper;

	public String getConstName(StyleContent sty) {
		return Strings.toUpperSnake(sty.getStyleName());
	}

	public StyleContent getParent(StyleContent sty) {
		return styleMapper.getParent(sty);
	}

	public List<StyleContent> getChildren(StyleContent sty) {
		return styleMapper.listChildrenContent(sty, 0, 0);
	}

}
