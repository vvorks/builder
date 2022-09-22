package com.github.vvorks.builder.server.extender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vvorks.builder.server.ServerSettings;
import com.github.vvorks.builder.server.common.util.Patterns;
import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.ClassI18nContent;
import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.EnumContent;
import com.github.vvorks.builder.server.domain.EnumI18nContent;
import com.github.vvorks.builder.server.domain.EnumValueContent;
import com.github.vvorks.builder.server.domain.EnumValueI18nContent;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.server.domain.FieldI18nContent;
import com.github.vvorks.builder.server.domain.MessageContent;
import com.github.vvorks.builder.server.domain.MessageI18nContent;
import com.github.vvorks.builder.server.domain.PageSetContent;
import com.github.vvorks.builder.server.domain.ProjectContent;
import com.github.vvorks.builder.server.domain.ProjectI18nContent;
import com.github.vvorks.builder.server.domain.StyleContent;
import com.github.vvorks.builder.server.mapper.Mappers;
import com.github.vvorks.builder.shared.common.json.Json;
import com.github.vvorks.builder.shared.common.lang.Strings;
import com.google.gwt.thirdparty.guava.common.collect.Iterables;

@Component
public class ProjectExtender {

	private static final String JSONKEY_FORMAT = "format";

	private static final String JSONKEY_TITLE = "title";

    private static final String JSONKEY_DESCRIPTION = "description";

	private static final String JSONKEY_MESSAGE = "message";

	private static final Function<String, Json> MAKE_NODE = k -> Json.createObject();

	private static final Set<String> LAYOUT_STAFF = new HashSet<>(Arrays.asList(
			"PageSet", "Page", "Layout"));

	@Autowired
    private Mappers mappers;

    @Autowired
    private Extenders extenders;

    @Autowired
    private AdditionalInformation adder;

	public String getTitleOrName(ProjectContent prj) {
		if (!Strings.isEmpty(prj.getTitle())) {
			return prj.getTitle();
		} else {
			return getLastName(prj);
		}
	}

	public String getUpperLastName(ProjectContent prj) {
		return Strings.toFirstUpper(getLastName(prj));
	}

	public String getLastName(ProjectContent prj) {
		String name = prj.getProjectName();
		int lastDot = name.lastIndexOf('.') + 1;
		return name.substring(lastDot);
	}

	public List<String> getCopyrightLines(ProjectContent prj) {
		String copyrights = prj.getCopyrights();
		if (copyrights == null || copyrights.isEmpty()) {
			return Collections.emptyList();
		}
		String[] lines = Patterns.EOL.split(copyrights, -1);
		return Arrays.asList(lines);
	}

	public List<ClassContent> getClasses(ProjectContent prj) {
		List<ClassContent> list = mappers.getProjectMapper().listClassesContent(prj, 0, 0);
		list.addAll(adder.getAdditionalClasses(prj));
		return list;
	}

	public List<ClassContent> getClassesByName(ProjectContent prj) {
		List<ClassContent> list = getClasses(prj);
		Collections.sort(list, (a, b) -> a.getClassName().compareTo(b.getClassName()));
		return list;
	}

	public Iterable<ClassContent> getSurrogateClasses(ProjectContent prj) {
		List<ClassContent> list = getClasses(prj);
		return Iterables.filter(list, c -> extenders.getClassExtender().isSurrogateClass(c));
	}

	public Iterable<ClassContent> getBuilderClasses(ProjectContent prj) {
		List<ClassContent> list = getClasses(prj);
		return Iterables.filter(list, c -> !LAYOUT_STAFF.contains(c.getClassName()));
	}

	public Iterable<ClassContent> getPublicClasses(ProjectContent prj) {
		List<ClassContent> list = getClasses(prj);
		String name = getUpperLastName(prj);
		return Iterables.filter(list, c -> !name.equals(c.getClassName()));
	}

	public List<EnumContent> getEnums(ProjectContent prj) {
		List<EnumContent> list = mappers.getProjectMapper().listEnumsContent(prj, 0, 0);
		return list;
	}

	public List<EnumContent> getEnumsByName(ProjectContent prj) {
		List<EnumContent> list = getEnums(prj);
		Collections.sort(list, (a, b) -> a.getEnumName().compareTo(b.getEnumName()));
		return list;
	}

	public boolean isBuilderProject(ProjectContent prj) {
		return prj.getProjectName().equals(ServerSettings.MODULE_NAME);
	}

	public String getBuilderName(ProjectContent prj) {
		return ServerSettings.MODULE_NAME;
	}

	public List<StyleContent> getStyles(ProjectContent prj) {
		List<StyleContent> list = mappers.getProjectMapper().listStylesContent(prj, 0, 0);
		List<StyleContent> result = new ArrayList<>();
		StyleContent root = new StyleContent();
		root.setStyleId(0);
		sortStyles(list, root, result);
		return result;
	}

	private void sortStyles(List<StyleContent> list, StyleContent owner, List<StyleContent> into) {
		for (StyleContent c : list) {
			Integer psid = c.getParentStyleId();
			int parentStyleId = (psid == null) ? 0 : psid;
			if (parentStyleId == owner.getStyleId()) {
				into.add(c);
				sortStyles(list, c, into);
			}
		}
	}

	public List<PageSetContent> getPageSets(ProjectContent prj) {
		List<PageSetContent> list = mappers.getProjectMapper().listPageSetsContent(prj, 0, 0);
		return list;
	}

	/**
	 * プロジェクト定数リソースを出力する
	 *
	 * @param prj プロジェクト情報
	 * @return ロケール別リソースJSONファイル
	 */
	public Set<Map.Entry<String, Json>> getProjectConstants(ProjectContent prj) {
		Map<String, Json> jsonMap = new LinkedHashMap<>();
		Json pjson = jsonMap.computeIfAbsent("", MAKE_NODE);
		pjson.setStringIfExists(JSONKEY_TITLE, prj.getTitle());
		pjson.setStringIfExists(JSONKEY_DESCRIPTION, prj.getDescription());
		for (ProjectI18nContent i18n : mappers.getProjectMapper().listI18nsContent(prj, 0, 0)) {
			String suffix = "_" + i18n.getTargetLocaleId();
			pjson = jsonMap.computeIfAbsent(suffix, MAKE_NODE);
			pjson.setStringIfExists(JSONKEY_TITLE, i18n.getTitle());
			pjson.setStringIfExists(JSONKEY_DESCRIPTION, i18n.getDescription());
		}
		return jsonMap.entrySet();
	}
	/**
	 * クラス定数リソースを出力する
	 *
	 * @param prj プロジェクト情報
	 * @return ロケール別リソースJSONファイル
	 */
	public Set<Map.Entry<String, Json>> getClassConstants(ProjectContent prj) {
		Map<String, Json> jsonMap = new LinkedHashMap<>();
		Json pjson = jsonMap.computeIfAbsent("", MAKE_NODE);
		for (ClassContent cls : mappers.getProjectMapper().listClassesContent(prj, 0, 0)) {
			Json cjson = pjson.computeIfAbsent(cls.getClassName(), MAKE_NODE);
			cjson.setStringIfExists(JSONKEY_TITLE, cls.getTitle());
			cjson.setStringIfExists(JSONKEY_DESCRIPTION, cls.getDescription());
			for (ClassI18nContent i18n : mappers.getClassMapper().listI18nsContent(cls, 0, 0)) {
				String suffix = "_" + i18n.getTargetLocaleId();
				Json pjsonI18n = jsonMap.computeIfAbsent(suffix, MAKE_NODE);
				Json cjsonI18n = pjsonI18n.computeIfAbsent(cls.getClassName(), MAKE_NODE);
				cjsonI18n.setStringIfExists(JSONKEY_TITLE, i18n.getTitle());
				cjsonI18n.setStringIfExists(JSONKEY_DESCRIPTION, i18n.getDescription());
			}
		}
		return jsonMap.entrySet();
	}

	/**
	 * フィールドラベルリソースを出力する
	 *
	 * @param prj プロジェクト情報
	 * @return フィールドラベルのロケール別リソースJSONファイル
	 */
	public Set<Map.Entry<String, Json>> getFieldConstants(ProjectContent prj) {
		Map<String, Json> jsonMap = new LinkedHashMap<>();
		Json pjson = jsonMap.computeIfAbsent("", MAKE_NODE);
		for (ClassContent cls : mappers.getProjectMapper().listClassesContent(prj, 0, 0)) {
			Json cjson = pjson.computeIfAbsent(cls.getClassName(), MAKE_NODE);
			for (FieldContent fld : mappers.getClassMapper().listFieldsContent(cls, 0, 0)) {
				Json fjson = cjson.computeIfAbsent(fld.getFieldName(), MAKE_NODE);
				fjson.setStringIfExists(JSONKEY_FORMAT, fld.getFormat());
				fjson.setStringIfExists(JSONKEY_TITLE, fld.getTitle());
				fjson.setStringIfExists(JSONKEY_DESCRIPTION, fld.getDescription());
				for (FieldI18nContent i18n : mappers.getFieldMapper().listI18nsContent(fld, 0, 0)) {
					String suffix = "_" + i18n.getTargetLocaleId();
					Json pjsonI18n = jsonMap.computeIfAbsent(suffix, MAKE_NODE);
					Json cjsonI18n = pjsonI18n.computeIfAbsent(cls.getClassName(), MAKE_NODE);
					Json fjsonI18n = cjsonI18n.computeIfAbsent(fld.getFieldName(), MAKE_NODE);
					fjsonI18n.setStringIfExists(JSONKEY_FORMAT, i18n.getFormat());
					fjsonI18n.setStringIfExists(JSONKEY_TITLE, i18n.getTitle());
					fjsonI18n.setStringIfExists(JSONKEY_DESCRIPTION, i18n.getDescription());
				}
			}
		}
		return jsonMap.entrySet();
	}

	/**
	 * 列挙定数リソースを出力する
	 *
	 * @param prj プロジェクト情報
	 * @return フィールドラベルのロケール別リソースJSONファイル
	 */
	public Set<Map.Entry<String, Json>> getEnumConstants(ProjectContent prj) {
		Map<String, Json> jsonMap = new LinkedHashMap<>();
		Json pjson = jsonMap.computeIfAbsent("", MAKE_NODE);
		for (EnumContent enm : mappers.getProjectMapper().listEnumsContent(prj, 0, 0)) {
			Json ejson = pjson.computeIfAbsent(enm.getEnumName(), MAKE_NODE);
			ejson.setStringIfExists(JSONKEY_TITLE, enm.getTitle());
			ejson.setStringIfExists(JSONKEY_DESCRIPTION, enm.getDescription());
			for (EnumI18nContent i18n : mappers.getEnumMapper().listI18nsContent(enm, 0, 0)) {
				String suffix = "_" + i18n.getTargetLocaleId();
				Json pjsonI18n = jsonMap.computeIfAbsent(suffix, MAKE_NODE);
				Json ejsonI18n = pjsonI18n.computeIfAbsent(enm.getEnumName(), MAKE_NODE);
				ejsonI18n.setStringIfExists(JSONKEY_TITLE, i18n.getTitle());
				ejsonI18n.setStringIfExists(JSONKEY_DESCRIPTION, i18n.getDescription());
			}
		}
		return jsonMap.entrySet();
	}

	/**
	 * 列挙値定数リソースを出力する
	 *
	 * @param prj プロジェクト情報
	 * @return フィールドラベルのロケール別リソースJSONファイル
	 */
	public Set<Map.Entry<String, Json>> getEnumValueConstants(ProjectContent prj) {
		Map<String, Json> jsonMap = new LinkedHashMap<>();
		Json pjson = jsonMap.computeIfAbsent("", MAKE_NODE);
		for (EnumContent enm : mappers.getProjectMapper().listEnumsContent(prj, 0, 0)) {
			Json ejson = pjson.computeIfAbsent(enm.getEnumName(), MAKE_NODE);
			for (EnumValueContent eva : mappers.getEnumMapper().listValuesContent(enm, 0, 0)) {
				Json vjson = ejson.computeIfAbsent(eva.getValueId(), MAKE_NODE);
				vjson.setStringIfExists(JSONKEY_TITLE, eva.getTitle());
				vjson.setStringIfExists(JSONKEY_DESCRIPTION, eva.getDescription());
				for (EnumValueI18nContent i18n : mappers.getEnumValueMapper().listI18nsContent(eva, 0, 0)) {
					String suffix = "_" + i18n.getTargetLocaleId();
					Json pjsonI18n = jsonMap.computeIfAbsent(suffix, MAKE_NODE);
					Json ejsonI18n = pjsonI18n.computeIfAbsent(enm.getEnumName(), MAKE_NODE);
					Json vjsonI18n = ejsonI18n.computeIfAbsent(eva.getValueId(), MAKE_NODE);
					vjsonI18n.setStringIfExists(JSONKEY_TITLE, i18n.getTitle());
					vjsonI18n.setStringIfExists(JSONKEY_DESCRIPTION, i18n.getDescription());
				}
			}
		}
		return jsonMap.entrySet();
	}

	/**
	 * メッセージリソースを出力する
	 *
	 * @param prj プロジェクト情報
	 * @return メッセージのロケール別リソースJSONファイル
	 */
	public Set<Map.Entry<String, Json>> getMessages(ProjectContent prj) {
		Map<String, Json> jsonMap = new LinkedHashMap<>();
		Json pjson = jsonMap.computeIfAbsent("", MAKE_NODE);
		for (MessageContent msg : mappers.getProjectMapper().listMessagesContent(prj, 0, 0)) {
			Json mjson = pjson.computeIfAbsent(msg.getMessageName(), MAKE_NODE);
			mjson.setStringIfExists(JSONKEY_MESSAGE, msg.getMessage());
			for (MessageI18nContent i18n : mappers.getMessageMapper().listI18nsContent(msg, 0, 0)) {
				String suffix = "_" + i18n.getTargetLocaleId();
				Json pjsonI18n = jsonMap.computeIfAbsent(suffix, MAKE_NODE);
				Json mjsonI18n = pjsonI18n.computeIfAbsent(msg.getMessageName(), MAKE_NODE);
				mjsonI18n.setStringIfExists(JSONKEY_MESSAGE, i18n.getMessage());
			}
		}
		return jsonMap.entrySet();
	}

	public ClassRelation getRelation(ProjectContent prj) {
		Map<Integer, ClassRelation> relations = getRelationMap(prj);
		//誰からも保有されていないクラスをトップレベルクラスとし、rootに追加
		ClassRelation root = new ClassRelation();
		for (ClassRelation r : relations.values()) {
			if (!r.isContained()) {
				root.addSet(r);
			}
		}
		//ルートを返却
		return root;
	}

	public Collection<ClassRelation> getRelationList(ProjectContent prj) {
		return getRelationMap(prj).values();
	}

	public Map<Integer, ClassRelation> getRelationMap(ProjectContent prj) {
		Map<Integer, ClassRelation> relations = new LinkedHashMap<>();
		//クラス一覧からリレーションのリストを作成
		for (ClassContent cls : mappers.getProjectMapper().listClassesContent(prj, 0, 0)) {
			relations.put(cls.getClassId(), new ClassRelation(cls));
		}
		//各クラス中のSETフィールドを元に関連付け
		for (ClassRelation owner : relations.values()) {
			ClassContent cls = owner.getContent();
			for (FieldContent fld : mappers.getClassMapper().listFieldsContent(cls, 0, 0)) {
				if (fld.getType() == DataType.SET) {
					FieldContent ref = mappers.getFieldMapper().getFref(fld);
					ClassRelation set = relations.get(ref.getOwnerClassId());
					owner.addSet(set, fld, ref);
				}
			}
		}
		return relations;
	}

}
