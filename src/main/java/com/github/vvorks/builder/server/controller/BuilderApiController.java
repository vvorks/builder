/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.vvorks.builder.server.domain.BuilderContent;
import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.ClassI18nContent;
import com.github.vvorks.builder.server.domain.EnumContent;
import com.github.vvorks.builder.server.domain.EnumI18nContent;
import com.github.vvorks.builder.server.domain.EnumValueContent;
import com.github.vvorks.builder.server.domain.EnumValueI18nContent;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.server.domain.FieldI18nContent;
import com.github.vvorks.builder.server.domain.LayoutContent;
import com.github.vvorks.builder.server.domain.LocaleContent;
import com.github.vvorks.builder.server.domain.MessageContent;
import com.github.vvorks.builder.server.domain.MessageI18nContent;
import com.github.vvorks.builder.server.domain.PageContent;
import com.github.vvorks.builder.server.domain.PageSetContent;
import com.github.vvorks.builder.server.domain.ProjectContent;
import com.github.vvorks.builder.server.domain.ProjectI18nContent;
import com.github.vvorks.builder.server.domain.QueryContent;
import com.github.vvorks.builder.server.domain.StyleContent;
import com.github.vvorks.builder.server.mapper.Mappers;
import com.github.vvorks.builder.shared.common.lang.Strings;

/**
 * ビルダープロジェクトの REST API
 */
@RestController
public class BuilderApiController {

	@Autowired
	private Mappers mappers;

	/**
	 * プロジェクトを取得する
	 *
	 * @param projectId プロジェクトID
	 * @return 取得したプロジェクト
	 */
	@GetMapping("/getProject")
	public ProjectContent getProject(
		@RequestParam(value = "projectId") int projectId
	) {
		return mappers.getProjectMapper().get(
				projectId
				);
	}

	/**
	 * 全てのプロジェクト情報を取得する
	 *
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return プロジェクト情報
	 */
	@GetMapping("/listProject")
	public List<ProjectContent> listProject(
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getProjectMapper().listContent(offset, limit);
	}

	/**
	 * 名前に合致するプロジェクト情報を取得する
	 *
	 * @param name name
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return プロジェクト情報
	 */
	@GetMapping("/listProjectIfNameIs")
	public List<ProjectContent> listProjectIfNameIs(
		@RequestParam(value = "name") String name,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getProjectMapper().listContentIfNameIs(
				name,
				offset, limit);
	}

	/**
	 * クラス一覧情報を取得する
	 *
	 * @param projectId プロジェクトID
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return クラス一覧情報
	 */
	@GetMapping("/listProjectClasses")
	public List<ClassContent> listProjectClasses(
		@RequestParam(value = "projectId") int projectId,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		ProjectContent content = mappers.getProjectMapper().get(
				projectId
				);
		return mappers.getProjectMapper().listClassesContent(content, offset, limit);
	}

	/**
	 * 名前に合致するクラス一覧情報を取得する
	 *
	 * @param projectId プロジェクトID
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return クラス一覧情報
	 */
	@GetMapping("listProjectClassesIfNameIs")
	public List<ClassContent> listProjectClassesIfNameIs(
		@RequestParam(value = "projectId") int projectId,
		@RequestParam(value = "name") String name,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		ProjectContent content = mappers.getProjectMapper().get(
				projectId
				);
		return mappers.getProjectMapper().listClassesContentIfNameIs(
				content,
				name,
				offset, limit);
	}

	/**
	 * 列挙一覧情報を取得する
	 *
	 * @param projectId プロジェクトID
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return 列挙一覧情報
	 */
	@GetMapping("/listProjectEnums")
	public List<EnumContent> listProjectEnums(
		@RequestParam(value = "projectId") int projectId,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		ProjectContent content = mappers.getProjectMapper().get(
				projectId
				);
		return mappers.getProjectMapper().listEnumsContent(content, offset, limit);
	}

	/**
	 * 名前に合致する列挙一覧情報を取得する
	 *
	 * @param projectId プロジェクトID
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return 列挙一覧情報
	 */
	@GetMapping("listProjectEnumsIfNameIs")
	public List<EnumContent> listProjectEnumsIfNameIs(
		@RequestParam(value = "projectId") int projectId,
		@RequestParam(value = "name") String name,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		ProjectContent content = mappers.getProjectMapper().get(
				projectId
				);
		return mappers.getProjectMapper().listEnumsContentIfNameIs(
				content,
				name,
				offset, limit);
	}

	/**
	 * メッセージ一覧情報を取得する
	 *
	 * @param projectId プロジェクトID
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return メッセージ一覧情報
	 */
	@GetMapping("/listProjectMessages")
	public List<MessageContent> listProjectMessages(
		@RequestParam(value = "projectId") int projectId,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		ProjectContent content = mappers.getProjectMapper().get(
				projectId
				);
		return mappers.getProjectMapper().listMessagesContent(content, offset, limit);
	}

	/**
	 * スタイル一覧情報を取得する
	 *
	 * @param projectId プロジェクトID
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return スタイル一覧情報
	 */
	@GetMapping("/listProjectStyles")
	public List<StyleContent> listProjectStyles(
		@RequestParam(value = "projectId") int projectId,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		ProjectContent content = mappers.getProjectMapper().get(
				projectId
				);
		return mappers.getProjectMapper().listStylesContent(content, offset, limit);
	}

	/**
	 * ページセット一覧情報を取得する
	 *
	 * @param projectId プロジェクトID
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return ページセット一覧情報
	 */
	@GetMapping("/listProjectPageSets")
	public List<PageSetContent> listProjectPageSets(
		@RequestParam(value = "projectId") int projectId,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		ProjectContent content = mappers.getProjectMapper().get(
				projectId
				);
		return mappers.getProjectMapper().listPageSetsContent(content, offset, limit);
	}

	/**
	 * ロケール一覧情報を取得する
	 *
	 * @param projectId プロジェクトID
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return ロケール一覧情報
	 */
	@GetMapping("/listProjectLocales")
	public List<LocaleContent> listProjectLocales(
		@RequestParam(value = "projectId") int projectId,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		ProjectContent content = mappers.getProjectMapper().get(
				projectId
				);
		return mappers.getProjectMapper().listLocalesContent(content, offset, limit);
	}

	/**
	 * I18n一覧情報を取得する
	 *
	 * @param projectId プロジェクトID
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return I18n一覧情報
	 */
	@GetMapping("/listProjectI18ns")
	public List<ProjectI18nContent> listProjectI18ns(
		@RequestParam(value = "projectId") int projectId,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		ProjectContent content = mappers.getProjectMapper().get(
				projectId
				);
		return mappers.getProjectMapper().listI18nsContent(content, offset, limit);
	}

	/**
	 * プロジェクト(I18n)を取得する
	 *
	 * @param ownerProjectId 所属プロジェクトのプロジェクトID
	 * @param targetLocaleId 対象ロケールのロケールID
	 * @return 取得したプロジェクト(I18n)
	 */
	@GetMapping("/getProjectI18n")
	public ProjectI18nContent getProjectI18n(
		@RequestParam(value = "ownerProjectId") int ownerProjectId, 
		@RequestParam(value = "targetLocaleId") String targetLocaleId
	) {
		return mappers.getProjectI18nMapper().get(
				ownerProjectId, 
				targetLocaleId
				);
	}

	/**
	 * 全てのプロジェクト(I18n)情報を取得する
	 *
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return プロジェクト(I18n)情報
	 */
	@GetMapping("/listProjectI18n")
	public List<ProjectI18nContent> listProjectI18n(
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getProjectI18nMapper().listContent(offset, limit);
	}

	/**
	 * クラスを取得する
	 *
	 * @param classId クラスID
	 * @return 取得したクラス
	 */
	@GetMapping("/getClass")
	public ClassContent getClass(
		@RequestParam(value = "classId") int classId
	) {
		return mappers.getClassMapper().get(
				classId
				);
	}

	/**
	 * 全てのクラス情報を取得する
	 *
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return クラス情報
	 */
	@GetMapping("/listClass")
	public List<ClassContent> listClass(
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getClassMapper().listContent(offset, limit);
	}

	/**
	 * 名前に合致するクラス情報を取得する
	 *
	 * @param name name
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return クラス情報
	 */
	@GetMapping("/listClassIfNameIs")
	public List<ClassContent> listClassIfNameIs(
		@RequestParam(value = "name") String name,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getClassMapper().listContentIfNameIs(
				name,
				offset, limit);
	}

	/**
	 * フィールド一覧情報を取得する
	 *
	 * @param classId クラスID
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return フィールド一覧情報
	 */
	@GetMapping("/listClassFields")
	public List<FieldContent> listClassFields(
		@RequestParam(value = "classId") int classId,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		ClassContent content = mappers.getClassMapper().get(
				classId
				);
		return mappers.getClassMapper().listFieldsContent(content, offset, limit);
	}

	/**
	 * 名前に合致するフィールド一覧情報を取得する
	 *
	 * @param classId クラスID
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return フィールド一覧情報
	 */
	@GetMapping("listClassFieldsIfNameIs")
	public List<FieldContent> listClassFieldsIfNameIs(
		@RequestParam(value = "classId") int classId,
		@RequestParam(value = "name") String name,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		ClassContent content = mappers.getClassMapper().get(
				classId
				);
		return mappers.getClassMapper().listFieldsContentIfNameIs(
				content,
				name,
				offset, limit);
	}

	/**
	 * クエリー一覧情報を取得する
	 *
	 * @param classId クラスID
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return クエリー一覧情報
	 */
	@GetMapping("/listClassQueries")
	public List<QueryContent> listClassQueries(
		@RequestParam(value = "classId") int classId,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		ClassContent content = mappers.getClassMapper().get(
				classId
				);
		return mappers.getClassMapper().listQueriesContent(content, offset, limit);
	}

	/**
	 * I18n一覧情報を取得する
	 *
	 * @param classId クラスID
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return I18n一覧情報
	 */
	@GetMapping("/listClassI18ns")
	public List<ClassI18nContent> listClassI18ns(
		@RequestParam(value = "classId") int classId,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		ClassContent content = mappers.getClassMapper().get(
				classId
				);
		return mappers.getClassMapper().listI18nsContent(content, offset, limit);
	}

	/**
	 * クラス(I18n)を取得する
	 *
	 * @param ownerClassId 所属クラスのクラスID
	 * @param targetLocaleId 対象ロケールのロケールID
	 * @return 取得したクラス(I18n)
	 */
	@GetMapping("/getClassI18n")
	public ClassI18nContent getClassI18n(
		@RequestParam(value = "ownerClassId") int ownerClassId, 
		@RequestParam(value = "targetLocaleId") String targetLocaleId
	) {
		return mappers.getClassI18nMapper().get(
				ownerClassId, 
				targetLocaleId
				);
	}

	/**
	 * 全てのクラス(I18n)情報を取得する
	 *
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return クラス(I18n)情報
	 */
	@GetMapping("/listClassI18n")
	public List<ClassI18nContent> listClassI18n(
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getClassI18nMapper().listContent(offset, limit);
	}

	/**
	 * フィールドを取得する
	 *
	 * @param fieldId フィールドID
	 * @return 取得したフィールド
	 */
	@GetMapping("/getField")
	public FieldContent getField(
		@RequestParam(value = "fieldId") int fieldId
	) {
		return mappers.getFieldMapper().get(
				fieldId
				);
	}

	/**
	 * 全てのフィールド情報を取得する
	 *
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return フィールド情報
	 */
	@GetMapping("/listField")
	public List<FieldContent> listField(
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getFieldMapper().listContent(offset, limit);
	}

	/**
	 * 名前に合致するフィールド情報を取得する
	 *
	 * @param name name
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return フィールド情報
	 */
	@GetMapping("/listFieldIfNameIs")
	public List<FieldContent> listFieldIfNameIs(
		@RequestParam(value = "name") String name,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getFieldMapper().listContentIfNameIs(
				name,
				offset, limit);
	}

	/**
	 * I18n一覧情報を取得する
	 *
	 * @param fieldId フィールドID
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return I18n一覧情報
	 */
	@GetMapping("/listFieldI18ns")
	public List<FieldI18nContent> listFieldI18ns(
		@RequestParam(value = "fieldId") int fieldId,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		FieldContent content = mappers.getFieldMapper().get(
				fieldId
				);
		return mappers.getFieldMapper().listI18nsContent(content, offset, limit);
	}

	/**
	 * フィールド(I18n)を取得する
	 *
	 * @param ownerFieldId 所属フィールドのフィールドID
	 * @param targetLocaleId 対象ロケールのロケールID
	 * @return 取得したフィールド(I18n)
	 */
	@GetMapping("/getFieldI18n")
	public FieldI18nContent getFieldI18n(
		@RequestParam(value = "ownerFieldId") int ownerFieldId, 
		@RequestParam(value = "targetLocaleId") String targetLocaleId
	) {
		return mappers.getFieldI18nMapper().get(
				ownerFieldId, 
				targetLocaleId
				);
	}

	/**
	 * 全てのフィールド(I18n)情報を取得する
	 *
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return フィールド(I18n)情報
	 */
	@GetMapping("/listFieldI18n")
	public List<FieldI18nContent> listFieldI18n(
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getFieldI18nMapper().listContent(offset, limit);
	}

	/**
	 * クエリーを取得する
	 *
	 * @param queryId クエリーID
	 * @return 取得したクエリー
	 */
	@GetMapping("/getQuery")
	public QueryContent getQuery(
		@RequestParam(value = "queryId") int queryId
	) {
		return mappers.getQueryMapper().get(
				queryId
				);
	}

	/**
	 * 全てのクエリー情報を取得する
	 *
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return クエリー情報
	 */
	@GetMapping("/listQuery")
	public List<QueryContent> listQuery(
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getQueryMapper().listContent(offset, limit);
	}

	/**
	 * 列挙を取得する
	 *
	 * @param enumId 列挙ID
	 * @return 取得した列挙
	 */
	@GetMapping("/getEnum")
	public EnumContent getEnum(
		@RequestParam(value = "enumId") int enumId
	) {
		return mappers.getEnumMapper().get(
				enumId
				);
	}

	/**
	 * 全ての列挙情報を取得する
	 *
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return 列挙情報
	 */
	@GetMapping("/listEnum")
	public List<EnumContent> listEnum(
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getEnumMapper().listContent(offset, limit);
	}

	/**
	 * 名前に合致する列挙情報を取得する
	 *
	 * @param name name
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return 列挙情報
	 */
	@GetMapping("/listEnumIfNameIs")
	public List<EnumContent> listEnumIfNameIs(
		@RequestParam(value = "name") String name,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getEnumMapper().listContentIfNameIs(
				name,
				offset, limit);
	}

	/**
	 * 列挙値一覧情報を取得する
	 *
	 * @param enumId 列挙ID
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return 列挙値一覧情報
	 */
	@GetMapping("/listEnumValues")
	public List<EnumValueContent> listEnumValues(
		@RequestParam(value = "enumId") int enumId,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		EnumContent content = mappers.getEnumMapper().get(
				enumId
				);
		return mappers.getEnumMapper().listValuesContent(content, offset, limit);
	}

	/**
	 * I18n一覧情報を取得する
	 *
	 * @param enumId 列挙ID
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return I18n一覧情報
	 */
	@GetMapping("/listEnumI18ns")
	public List<EnumI18nContent> listEnumI18ns(
		@RequestParam(value = "enumId") int enumId,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		EnumContent content = mappers.getEnumMapper().get(
				enumId
				);
		return mappers.getEnumMapper().listI18nsContent(content, offset, limit);
	}

	/**
	 * 列挙(I18n)を取得する
	 *
	 * @param ownerEnumId 所属列挙の列挙ID
	 * @param targetLocaleId 対象ロケールのロケールID
	 * @return 取得した列挙(I18n)
	 */
	@GetMapping("/getEnumI18n")
	public EnumI18nContent getEnumI18n(
		@RequestParam(value = "ownerEnumId") int ownerEnumId, 
		@RequestParam(value = "targetLocaleId") String targetLocaleId
	) {
		return mappers.getEnumI18nMapper().get(
				ownerEnumId, 
				targetLocaleId
				);
	}

	/**
	 * 全ての列挙(I18n)情報を取得する
	 *
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return 列挙(I18n)情報
	 */
	@GetMapping("/listEnumI18n")
	public List<EnumI18nContent> listEnumI18n(
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getEnumI18nMapper().listContent(offset, limit);
	}

	/**
	 * 列挙値を取得する
	 *
	 * @param ownerEnumId 所属列挙の列挙ID
	 * @param valueId 列挙名
	 * @return 取得した列挙値
	 */
	@GetMapping("/getEnumValue")
	public EnumValueContent getEnumValue(
		@RequestParam(value = "ownerEnumId") int ownerEnumId, 
		@RequestParam(value = "valueId") String valueId
	) {
		return mappers.getEnumValueMapper().get(
				ownerEnumId, 
				valueId
				);
	}

	/**
	 * 全ての列挙値情報を取得する
	 *
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return 列挙値情報
	 */
	@GetMapping("/listEnumValue")
	public List<EnumValueContent> listEnumValue(
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getEnumValueMapper().listContent(offset, limit);
	}

	/**
	 * I18n一覧情報を取得する
	 *
	 * @param ownerEnumId 所属列挙の列挙ID
	 * @param valueId 列挙名
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return I18n一覧情報
	 */
	@GetMapping("/listEnumValueI18ns")
	public List<EnumValueI18nContent> listEnumValueI18ns(
		@RequestParam(value = "ownerEnumId") int ownerEnumId,
		@RequestParam(value = "valueId") String valueId,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		EnumValueContent content = mappers.getEnumValueMapper().get(
				ownerEnumId, 
				valueId
				);
		return mappers.getEnumValueMapper().listI18nsContent(content, offset, limit);
	}

	/**
	 * 列挙値(I18n)を取得する
	 *
	 * @param ownerOwnerEnumId 所属列挙値の所属列挙の列挙ID
	 * @param ownerValueId 所属列挙値の列挙名
	 * @param targetLocaleId 対象ロケールのロケールID
	 * @return 取得した列挙値(I18n)
	 */
	@GetMapping("/getEnumValueI18n")
	public EnumValueI18nContent getEnumValueI18n(
		@RequestParam(value = "ownerOwnerEnumId") int ownerOwnerEnumId, 
		@RequestParam(value = "ownerValueId") String ownerValueId, 
		@RequestParam(value = "targetLocaleId") String targetLocaleId
	) {
		return mappers.getEnumValueI18nMapper().get(
				ownerOwnerEnumId, 
				ownerValueId, 
				targetLocaleId
				);
	}

	/**
	 * 全ての列挙値(I18n)情報を取得する
	 *
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return 列挙値(I18n)情報
	 */
	@GetMapping("/listEnumValueI18n")
	public List<EnumValueI18nContent> listEnumValueI18n(
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getEnumValueI18nMapper().listContent(offset, limit);
	}

	/**
	 * メッセージを取得する
	 *
	 * @param messageId メッセージID
	 * @return 取得したメッセージ
	 */
	@GetMapping("/getMessage")
	public MessageContent getMessage(
		@RequestParam(value = "messageId") int messageId
	) {
		return mappers.getMessageMapper().get(
				messageId
				);
	}

	/**
	 * 全てのメッセージ情報を取得する
	 *
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return メッセージ情報
	 */
	@GetMapping("/listMessage")
	public List<MessageContent> listMessage(
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getMessageMapper().listContent(offset, limit);
	}

	/**
	 * I18n一覧情報を取得する
	 *
	 * @param messageId メッセージID
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return I18n一覧情報
	 */
	@GetMapping("/listMessageI18ns")
	public List<MessageI18nContent> listMessageI18ns(
		@RequestParam(value = "messageId") int messageId,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		MessageContent content = mappers.getMessageMapper().get(
				messageId
				);
		return mappers.getMessageMapper().listI18nsContent(content, offset, limit);
	}

	/**
	 * メッセージ(I18n)を取得する
	 *
	 * @param ownerMessageId 所属列挙値のメッセージID
	 * @param targetLocaleId 対象ロケールのロケールID
	 * @return 取得したメッセージ(I18n)
	 */
	@GetMapping("/getMessageI18n")
	public MessageI18nContent getMessageI18n(
		@RequestParam(value = "ownerMessageId") int ownerMessageId, 
		@RequestParam(value = "targetLocaleId") String targetLocaleId
	) {
		return mappers.getMessageI18nMapper().get(
				ownerMessageId, 
				targetLocaleId
				);
	}

	/**
	 * 全てのメッセージ(I18n)情報を取得する
	 *
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return メッセージ(I18n)情報
	 */
	@GetMapping("/listMessageI18n")
	public List<MessageI18nContent> listMessageI18n(
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getMessageI18nMapper().listContent(offset, limit);
	}

	/**
	 * スタイルを取得する
	 *
	 * @param styleId スタイルID
	 * @return 取得したスタイル
	 */
	@GetMapping("/getStyle")
	public StyleContent getStyle(
		@RequestParam(value = "styleId") int styleId
	) {
		return mappers.getStyleMapper().get(
				styleId
				);
	}

	/**
	 * 全てのスタイル情報を取得する
	 *
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return スタイル情報
	 */
	@GetMapping("/listStyle")
	public List<StyleContent> listStyle(
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getStyleMapper().listContent(offset, limit);
	}

	/**
	 * 派生スタイル情報を取得する
	 *
	 * @param styleId スタイルID
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return 派生スタイル情報
	 */
	@GetMapping("/listStyleChildren")
	public List<StyleContent> listStyleChildren(
		@RequestParam(value = "styleId") int styleId,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		StyleContent content = mappers.getStyleMapper().get(
				styleId
				);
		return mappers.getStyleMapper().listChildrenContent(content, offset, limit);
	}

	/**
	 * ページセットを取得する
	 *
	 * @param pageSetId ページセットId
	 * @return 取得したページセット
	 */
	@GetMapping("/getPageSet")
	public PageSetContent getPageSet(
		@RequestParam(value = "pageSetId") int pageSetId
	) {
		return mappers.getPageSetMapper().get(
				pageSetId
				);
	}

	/**
	 * 全てのページセット情報を取得する
	 *
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return ページセット情報
	 */
	@GetMapping("/listPageSet")
	public List<PageSetContent> listPageSet(
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getPageSetMapper().listContent(offset, limit);
	}

	/**
	 * ページ一覧情報を取得する
	 *
	 * @param pageSetId ページセットId
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return ページ一覧情報
	 */
	@GetMapping("/listPageSetPages")
	public List<PageContent> listPageSetPages(
		@RequestParam(value = "pageSetId") int pageSetId,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		PageSetContent content = mappers.getPageSetMapper().get(
				pageSetId
				);
		return mappers.getPageSetMapper().listPagesContent(content, offset, limit);
	}

	/**
	 * ページを取得する
	 *
	 * @param pageId ページId
	 * @return 取得したページ
	 */
	@GetMapping("/getPage")
	public PageContent getPage(
		@RequestParam(value = "pageId") int pageId
	) {
		return mappers.getPageMapper().get(
				pageId
				);
	}

	/**
	 * 全てのページ情報を取得する
	 *
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return ページ情報
	 */
	@GetMapping("/listPage")
	public List<PageContent> listPage(
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getPageMapper().listContent(offset, limit);
	}

	/**
	 * レイアウト一覧情報を取得する
	 *
	 * @param pageId ページId
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return レイアウト一覧情報
	 */
	@GetMapping("/listPageLayouts")
	public List<LayoutContent> listPageLayouts(
		@RequestParam(value = "pageId") int pageId,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		PageContent content = mappers.getPageMapper().get(
				pageId
				);
		return mappers.getPageMapper().listLayoutsContent(content, offset, limit);
	}

	/**
	 * rootに合致するレイアウト一覧情報を取得する
	 *
	 * @param pageId ページId
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return レイアウト一覧情報
	 */
	@GetMapping("listPageLayoutsIfRoot")
	public List<LayoutContent> listPageLayoutsIfRoot(
		@RequestParam(value = "pageId") int pageId,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		PageContent content = mappers.getPageMapper().get(
				pageId
				);
		return mappers.getPageMapper().listLayoutsContentIfRoot(
				content,
				offset, limit);
	}

	/**
	 * レイアウトを取得する
	 *
	 * @param layoutId レイアウトId
	 * @return 取得したレイアウト
	 */
	@GetMapping("/getLayout")
	public LayoutContent getLayout(
		@RequestParam(value = "layoutId") int layoutId
	) {
		return mappers.getLayoutMapper().get(
				layoutId
				);
	}

	/**
	 * 全てのレイアウト情報を取得する
	 *
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return レイアウト情報
	 */
	@GetMapping("/listLayout")
	public List<LayoutContent> listLayout(
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getLayoutMapper().listContent(offset, limit);
	}

	/**
	 * rootに合致するレイアウト情報を取得する
	 *
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return レイアウト情報
	 */
	@GetMapping("/listLayoutIfRoot")
	public List<LayoutContent> listLayoutIfRoot(
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getLayoutMapper().listContentIfRoot(
				offset, limit);
	}

	/**
	 * 子レイアウト情報を取得する
	 *
	 * @param layoutId レイアウトId
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return 子レイアウト情報
	 */
	@GetMapping("/listLayoutChildren")
	public List<LayoutContent> listLayoutChildren(
		@RequestParam(value = "layoutId") int layoutId,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		LayoutContent content = mappers.getLayoutMapper().get(
				layoutId
				);
		return mappers.getLayoutMapper().listChildrenContent(content, offset, limit);
	}

	/**
	 * rootに合致する子レイアウト情報を取得する
	 *
	 * @param layoutId レイアウトId
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return 子レイアウト情報
	 */
	@GetMapping("listLayoutChildrenIfRoot")
	public List<LayoutContent> listLayoutChildrenIfRoot(
		@RequestParam(value = "layoutId") int layoutId,
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		LayoutContent content = mappers.getLayoutMapper().get(
				layoutId
				);
		return mappers.getLayoutMapper().listChildrenContentIfRoot(
				content,
				offset, limit);
	}

	/**
	 * ロケールを取得する
	 *
	 * @param localeId ロケールID
	 * @return 取得したロケール
	 */
	@GetMapping("/getLocale")
	public LocaleContent getLocale(
		@RequestParam(value = "localeId") String localeId
	) {
		return mappers.getLocaleMapper().get(
				localeId
				);
	}

	/**
	 * 全てのロケール情報を取得する
	 *
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return ロケール情報
	 */
	@GetMapping("/listLocale")
	public List<LocaleContent> listLocale(
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getLocaleMapper().listContent(offset, limit);
	}

	/**
	 * Builderを取得する
	 *
	 * @param className className
	 * @return 取得したBuilder
	 */
	@GetMapping("/getBuilder")
	public BuilderContent getBuilder(
		@RequestParam(value = "className") String className
	) {
		return mappers.getBuilderMapper().get(
				className
				);
	}

	/**
	 * 全てのBuilder情報を取得する
	 *
	 * @param offset 取得開始位置（既定値０、全件取得の場合は無効）
	 * @param limit 件数（既定値０、０または負値を指定した場合には全件）
	 * @return Builder情報
	 */
	@GetMapping("/listBuilder")
	public List<BuilderContent> listBuilder(
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "0") int limit
	) {
		return mappers.getBuilderMapper().listContent(offset, limit);
	}

}
