package com.github.vvorks.builder.server.extender;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.server.domain.LayoutContent;
import com.github.vvorks.builder.server.mapper.LayoutMapper;

@Component
public class LayoutExtender {

	@Autowired
	private LayoutMapper layoutMapper;

	public List<LayoutContent> getChildren(LayoutContent layout) {
		List<LayoutContent> list = layoutMapper.listChildrenContent(layout, 0, 0);
		return list;
	}

	public Set<Map.Entry<String, String>> getLocations(LayoutContent layout) {
		Map<String, String> map = new LinkedHashMap<>();
		if (layout.getLeft() != null) {
			map.put("left", layout.getLeft());
		}
		if (layout.getTop() != null) {
			map.put("top", layout.getTop());
		}
		if (layout.getRight() != null) {
			map.put("right", layout.getRight());
		}
		if (layout.getBottom() != null) {
			map.put("bottom", layout.getBottom());
		}
		if (layout.getWidth() != null) {
			map.put("width", layout.getWidth());
		}
		if (layout.getHeight() != null) {
			map.put("height", layout.getHeight());
		}
		return map.entrySet();
	}

}
