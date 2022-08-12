package com.github.vvorks.builder.server.extender;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.EnumContent;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.server.domain.LayoutContent;
import com.github.vvorks.builder.server.domain.LayoutType;
import com.github.vvorks.builder.server.domain.MessageContent;
import com.github.vvorks.builder.server.domain.PageContent;
import com.github.vvorks.builder.server.mapper.FieldMapper;
import com.github.vvorks.builder.server.mapper.LayoutMapper;
import com.github.vvorks.builder.server.mapper.PageMapper;
import com.github.vvorks.builder.shared.common.json.Json;
import com.github.vvorks.builder.shared.common.lang.Asserts;

@Component
public class PageExtender {

	@Autowired
	private PageMapper pageMapper;

	@Autowired
	private FieldMapper fieldMapper;

	@Autowired
	private LayoutMapper layoutMapper;

	public String getContextName(PageContent ps) {
		if (ps.getContextClassId() == null) {
			return null;
		}
		return pageMapper.getContext(ps).getClassName();
	}

	private static class Relation {
		private String fullName;
		private Json node;
		private Integer relatedId;
	}

	public Json getLayoutTree(PageContent pg) {
		LayoutContent root = pageMapper.listLayoutsContentIfRoot(pg, 0, 0).get(0);
		Map<Integer, Relation> rels = new LinkedHashMap<>();
		Json result = toLayoutJson(root, rels, "/");
		//関連付け
		for (Relation r : rels.values()) {
			if (r.relatedId != null) {
				Relation rr = rels.get(r.relatedId);
				if (rr != null) {
					//TODO 相対パス変換（必要か？）
					r.node.setString("related", rr.fullName);
				}
			}
		}
		return result;
	}

	private Json toLayoutJson(LayoutContent content, Map<Integer, Relation> rels, String path) {
		//処理準備
		String fullName = path + content.getLayoutName();
		Json json = Json.createObject();
		//関連の設定
		Relation rel = rels.computeIfAbsent(content.getLayoutId(), x -> new Relation());
		rel.fullName = fullName;
		rel.node = json;
		rel.relatedId = content.getRelatedLayoutId();
		//Jsonへの値設定
		ClassContent cref = layoutMapper.getCref(content);
		EnumContent eref = layoutMapper.getEref(content);
		FieldContent fref = layoutMapper.getFref(content);
		json.setString("name", content.getLayoutName());
		LayoutType type = content.getLayoutType();
		json.setString("type", type.encode());
		json.setStringIfExists("param", content.getParam());
		json.setStringIfExists("layoutparam", content.getLayoutParam());
		if (type == LayoutType.FIELD || type == LayoutType.INPUT) {
			Asserts.assumeNotNull(fref);
			DataType dataType = fref.getType();
			json.setString("datatype", dataType.encode());
			if (dataType == DataType.REF) {
				ClassContent param = fieldMapper.getCref(fref);
				json.setString("datatypeparam", param.getClassName());
			} else if (dataType == DataType.ENUM) {
				EnumContent param = fieldMapper.getEref(fref);
				json.setString("datatypeparam", param.getEnumName());
			}
		}
		json.setStringIfExists("left", content.getLeft());
		json.setStringIfExists("top", content.getTop());
		json.setStringIfExists("right", content.getRight());
		json.setStringIfExists("bottom", content.getBottom());
		json.setStringIfExists("width", content.getWidth());
		json.setStringIfExists("height", content.getHeight());
		if (cref != null) {
			json.setStringIfExists("cref", cref.getClassName());
		}
		if (eref != null) {
			json.setStringIfExists("eref", eref.getEnumName());
		}
		if (fref != null) {
			ClassContent cls = fieldMapper.getOwner(fref);
			String name = cls.getClassName() + "#" + fref.getFieldName();
			json.setStringIfExists("fref", name);
		}
		MessageContent mref = layoutMapper.getMref(content);
		if (mref != null) {
			json.setStringIfExists("mref", mref.getMessageName());
		}
		List<LayoutContent> children = layoutMapper.listChildrenContent(content, 0, 0);
		if (!children.isEmpty()) {
			Json array = json.setNewArray("children");
			String cpath = fullName + "/";
			for (LayoutContent child : children) {
				array.add(toLayoutJson(child, rels, cpath));
			}
		}
		return json;
	}

}
