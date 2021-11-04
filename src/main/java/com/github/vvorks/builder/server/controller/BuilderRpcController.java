/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcController;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcMethod;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcParam;
import com.github.vvorks.builder.server.domain.*;
import com.github.vvorks.builder.server.mapper.ProjectMapper;
import com.github.vvorks.builder.server.mapper.ClassMapper;
import com.github.vvorks.builder.server.mapper.FieldMapper;
import com.github.vvorks.builder.server.mapper.QueryMapper;
import com.github.vvorks.builder.server.mapper.EnumMapper;
import com.github.vvorks.builder.server.mapper.EnumValueMapper;

/**
 * ビルダープロジェクトの Json-Rpc (on Websocket) API
 */
@JsonRpcController("/builder")
public class BuilderRpcController {

	/** プロジェクト型のMapper */
	@Autowired
	private ProjectMapper projectMapper;

	/** クラスのMapper */
	@Autowired
	private ClassMapper classMapper;

	/** フィールドのMapper */
	@Autowired
	private FieldMapper fieldMapper;

	/** クエリーのMapper */
	@Autowired
	private QueryMapper queryMapper;

	/** 列挙のMapper */
	@Autowired
	private EnumMapper enumMapper;

	/** 列挙値のMapper */
	@Autowired
	private EnumValueMapper enumValueMapper;

	/**
	 * プロジェクト型を挿入する
	 *
	 * @param content 挿入するプロジェクト型
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod("insertProject")
	public boolean insertProject(ProjectContent content) {
		return projectMapper.insert(content);
	}

	/**
	 * プロジェクト型を更新する
	 *
	 * @param content 更新するプロジェクト型
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod("updateProject")
	public boolean updateProject(ProjectContent content) {
		return projectMapper.update(content);
	}

	/**
	 * プロジェクト型を削除する
	 *
	 * @param content 削除するプロジェクト型
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod("deleteProject")
	public boolean deleteProject(ProjectContent content) {
		return projectMapper.delete(content);
	}

	/**
	 * プロジェクト型を取得する
	 *
	 * @param projectId プロジェクトID
	 * @return 取得したプロジェクト型
	 */
	@JsonRpcMethod("getProject")
	public ProjectContent getProject(
		@JsonRpcParam("projectId") int projectId
	) {
		return projectMapper.get(
				projectId
				);
	}

	/**
	 * 全てのプロジェクト型のサマリーを取得する
	 *
	 * @return プロジェクト型のサマリー
	 */
	@JsonRpcMethod("listProjectSummary")
	public ProjectSummary listProjectSummary() {
		return projectMapper.listSummary();
	}

	/**
	 * 全てのプロジェクト型を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return プロジェクト型のリスト
	 */
	@JsonRpcMethod("listProjectContent")
	public List<ProjectContent> listProjectContent(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		return projectMapper.listContent(offset, limit);
	}

	/**
	 * nameIsに合致するプロジェクト型のサマリーを取得する
	 *
	 * @param name name
	 * @return プロジェクト型のサマリー
	 */
	@JsonRpcMethod("listProjectSummaryIfNameIs")
	public ProjectSummary listProjectSummaryIfNameIs(
		@JsonRpcParam("name") String name
	) {
		return projectMapper.listSummaryIfNameIs(
				name
				);
	}

	/**
	 * nameIsに合致するプロジェクト型を取得する
	 *
	 * @param name name
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return プロジェクト型のリスト
	 */
	@JsonRpcMethod("listProjectContentIfNameIs")
	public List<ProjectContent> listProjectContentIfNameIs(
		@JsonRpcParam("name") String name,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		return projectMapper.listContentIfNameIs(
				name,
				offset, limit);

	}

	/**
	 * クラス一覧のサマリーを取得する
	 *
	 * @param content ビルダープロジェクト
	 * @return クラス一覧のサマリー
	 */
	@JsonRpcMethod("listProjectClassesSummary")
	public ClassSummary listProjectClassesSummary(
			@JsonRpcParam("content") ProjectContent content
	) {
		return projectMapper.listClassesSummary(content);
	}

	/**
	 * クラス一覧を取得する
	 *
	 * @param content プロジェクト型
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラス一覧
	 */
	@JsonRpcMethod("listProjectClassesContent")
	public List<ClassContent> listProjectClassesContent(
		@JsonRpcParam("content") ProjectContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		return projectMapper.listClassesContent(content, offset, limit);
	}

	/**
	 * nameIsに合致するクラス一覧のサマリーを取得する
	 *
	 * @param content プロジェクト型
	 * @return クラス一覧のサマリー
	 */
	@JsonRpcMethod("listProjectClassesSummaryIfNameIs")
	public ClassSummary listProjectClassesSummaryIfNameIs(
		@JsonRpcParam("content") ProjectContent content,
		@JsonRpcParam("name") String name
	) {
		return projectMapper.listClassesSummaryIfNameIs(content,
				name
				);
	}

	/**
	 * nameIsに合致するクラス一覧を取得する
	 *
	 * @param content プロジェクト型
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラス一覧
	 */
	@JsonRpcMethod("listProjectClassesContentIfNameIs")
	public List<ClassContent> listProjectClassesContentIfNameIs(
		@JsonRpcParam("content") ProjectContent content,
		@JsonRpcParam("name") String name,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		return projectMapper.listClassesContentIfNameIs(content,
				name,
				offset, limit);
	}

	/**
	 * 列挙一覧のサマリーを取得する
	 *
	 * @param content ビルダープロジェクト
	 * @return 列挙一覧のサマリー
	 */
	@JsonRpcMethod("listProjectEnumsSummary")
	public EnumSummary listProjectEnumsSummary(
			@JsonRpcParam("content") ProjectContent content
	) {
		return projectMapper.listEnumsSummary(content);
	}

	/**
	 * 列挙一覧を取得する
	 *
	 * @param content プロジェクト型
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙一覧
	 */
	@JsonRpcMethod("listProjectEnumsContent")
	public List<EnumContent> listProjectEnumsContent(
		@JsonRpcParam("content") ProjectContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		return projectMapper.listEnumsContent(content, offset, limit);
	}

	/**
	 * nameIsに合致する列挙一覧のサマリーを取得する
	 *
	 * @param content プロジェクト型
	 * @return 列挙一覧のサマリー
	 */
	@JsonRpcMethod("listProjectEnumsSummaryIfNameIs")
	public EnumSummary listProjectEnumsSummaryIfNameIs(
		@JsonRpcParam("content") ProjectContent content,
		@JsonRpcParam("name") String name
	) {
		return projectMapper.listEnumsSummaryIfNameIs(content,
				name
				);
	}

	/**
	 * nameIsに合致する列挙一覧を取得する
	 *
	 * @param content プロジェクト型
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙一覧
	 */
	@JsonRpcMethod("listProjectEnumsContentIfNameIs")
	public List<EnumContent> listProjectEnumsContentIfNameIs(
		@JsonRpcParam("content") ProjectContent content,
		@JsonRpcParam("name") String name,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		return projectMapper.listEnumsContentIfNameIs(content,
				name,
				offset, limit);
	}

	/**
	 * クラスを挿入する
	 *
	 * @param content 挿入するクラス
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod("insertClass")
	public boolean insertClass(ClassContent content) {
		return classMapper.insert(content);
	}

	/**
	 * クラスを更新する
	 *
	 * @param content 更新するクラス
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod("updateClass")
	public boolean updateClass(ClassContent content) {
		return classMapper.update(content);
	}

	/**
	 * クラスを削除する
	 *
	 * @param content 削除するクラス
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod("deleteClass")
	public boolean deleteClass(ClassContent content) {
		return classMapper.delete(content);
	}

	/**
	 * クラスを取得する
	 *
	 * @param classId クラスID
	 * @return 取得したクラス
	 */
	@JsonRpcMethod("getClass")
	public ClassContent getClass(
		@JsonRpcParam("classId") int classId
	) {
		return classMapper.get(
				classId
				);
	}

	/**
	 * 全てのクラスのサマリーを取得する
	 *
	 * @return クラスのサマリー
	 */
	@JsonRpcMethod("listClassSummary")
	public ClassSummary listClassSummary() {
		return classMapper.listSummary();
	}

	/**
	 * 全てのクラスを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラスのリスト
	 */
	@JsonRpcMethod("listClassContent")
	public List<ClassContent> listClassContent(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		return classMapper.listContent(offset, limit);
	}

	/**
	 * nameIsに合致するクラスのサマリーを取得する
	 *
	 * @param name name
	 * @return クラスのサマリー
	 */
	@JsonRpcMethod("listClassSummaryIfNameIs")
	public ClassSummary listClassSummaryIfNameIs(
		@JsonRpcParam("name") String name
	) {
		return classMapper.listSummaryIfNameIs(
				name
				);
	}

	/**
	 * nameIsに合致するクラスを取得する
	 *
	 * @param name name
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラスのリスト
	 */
	@JsonRpcMethod("listClassContentIfNameIs")
	public List<ClassContent> listClassContentIfNameIs(
		@JsonRpcParam("name") String name,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		return classMapper.listContentIfNameIs(
				name,
				offset, limit);

	}

	/**
	 * 所属プロジェクトを取得する
	 *
	 * @param content ビルダープロジェクト
	 * @return 所属プロジェクト
	 */
	@JsonRpcMethod("getClassOwner")
	public ProjectContent getClassOwner(
		@JsonRpcParam("content") ClassContent content
	) {
		return classMapper.getOwner(content);
	}

	/**
	 * フィールド一覧のサマリーを取得する
	 *
	 * @param content ビルダープロジェクト
	 * @return フィールド一覧のサマリー
	 */
	@JsonRpcMethod("listClassFieldsSummary")
	public FieldSummary listClassFieldsSummary(
			@JsonRpcParam("content") ClassContent content
	) {
		return classMapper.listFieldsSummary(content);
	}

	/**
	 * フィールド一覧を取得する
	 *
	 * @param content クラス
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールド一覧
	 */
	@JsonRpcMethod("listClassFieldsContent")
	public List<FieldContent> listClassFieldsContent(
		@JsonRpcParam("content") ClassContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		return classMapper.listFieldsContent(content, offset, limit);
	}

	/**
	 * nameIsに合致するフィールド一覧のサマリーを取得する
	 *
	 * @param content クラス
	 * @return フィールド一覧のサマリー
	 */
	@JsonRpcMethod("listClassFieldsSummaryIfNameIs")
	public FieldSummary listClassFieldsSummaryIfNameIs(
		@JsonRpcParam("content") ClassContent content,
		@JsonRpcParam("name") String name
	) {
		return classMapper.listFieldsSummaryIfNameIs(content,
				name
				);
	}

	/**
	 * nameIsに合致するフィールド一覧を取得する
	 *
	 * @param content クラス
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールド一覧
	 */
	@JsonRpcMethod("listClassFieldsContentIfNameIs")
	public List<FieldContent> listClassFieldsContentIfNameIs(
		@JsonRpcParam("content") ClassContent content,
		@JsonRpcParam("name") String name,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		return classMapper.listFieldsContentIfNameIs(content,
				name,
				offset, limit);
	}

	/**
	 * queriesのサマリーを取得する
	 *
	 * @param content ビルダープロジェクト
	 * @return queriesのサマリー
	 */
	@JsonRpcMethod("listClassQueriesSummary")
	public QuerySummary listClassQueriesSummary(
			@JsonRpcParam("content") ClassContent content
	) {
		return classMapper.listQueriesSummary(content);
	}

	/**
	 * queriesを取得する
	 *
	 * @param content クラス
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return queries
	 */
	@JsonRpcMethod("listClassQueriesContent")
	public List<QueryContent> listClassQueriesContent(
		@JsonRpcParam("content") ClassContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		return classMapper.listQueriesContent(content, offset, limit);
	}

	/**
	 * フィールドを挿入する
	 *
	 * @param content 挿入するフィールド
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod("insertField")
	public boolean insertField(FieldContent content) {
		return fieldMapper.insert(content);
	}

	/**
	 * フィールドを更新する
	 *
	 * @param content 更新するフィールド
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod("updateField")
	public boolean updateField(FieldContent content) {
		return fieldMapper.update(content);
	}

	/**
	 * フィールドを削除する
	 *
	 * @param content 削除するフィールド
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod("deleteField")
	public boolean deleteField(FieldContent content) {
		return fieldMapper.delete(content);
	}

	/**
	 * フィールドを取得する
	 *
	 * @param fieldId フィールドID
	 * @return 取得したフィールド
	 */
	@JsonRpcMethod("getField")
	public FieldContent getField(
		@JsonRpcParam("fieldId") int fieldId
	) {
		return fieldMapper.get(
				fieldId
				);
	}

	/**
	 * 全てのフィールドのサマリーを取得する
	 *
	 * @return フィールドのサマリー
	 */
	@JsonRpcMethod("listFieldSummary")
	public FieldSummary listFieldSummary() {
		return fieldMapper.listSummary();
	}

	/**
	 * 全てのフィールドを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールドのリスト
	 */
	@JsonRpcMethod("listFieldContent")
	public List<FieldContent> listFieldContent(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		return fieldMapper.listContent(offset, limit);
	}

	/**
	 * nameIsに合致するフィールドのサマリーを取得する
	 *
	 * @param name name
	 * @return フィールドのサマリー
	 */
	@JsonRpcMethod("listFieldSummaryIfNameIs")
	public FieldSummary listFieldSummaryIfNameIs(
		@JsonRpcParam("name") String name
	) {
		return fieldMapper.listSummaryIfNameIs(
				name
				);
	}

	/**
	 * nameIsに合致するフィールドを取得する
	 *
	 * @param name name
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールドのリスト
	 */
	@JsonRpcMethod("listFieldContentIfNameIs")
	public List<FieldContent> listFieldContentIfNameIs(
		@JsonRpcParam("name") String name,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		return fieldMapper.listContentIfNameIs(
				name,
				offset, limit);

	}

	/**
	 * 所属クラスを取得する
	 *
	 * @param content ビルダープロジェクト
	 * @return 所属クラス
	 */
	@JsonRpcMethod("getFieldOwner")
	public ClassContent getFieldOwner(
		@JsonRpcParam("content") FieldContent content
	) {
		return fieldMapper.getOwner(content);
	}

	/**
	 * クラス参照先を取得する
	 *
	 * @param content ビルダープロジェクト
	 * @return クラス参照先
	 */
	@JsonRpcMethod("getFieldCref")
	public ClassContent getFieldCref(
		@JsonRpcParam("content") FieldContent content
	) {
		return fieldMapper.getCref(content);
	}

	/**
	 * 列挙参照先を取得する
	 *
	 * @param content ビルダープロジェクト
	 * @return 列挙参照先
	 */
	@JsonRpcMethod("getFieldEref")
	public EnumContent getFieldEref(
		@JsonRpcParam("content") FieldContent content
	) {
		return fieldMapper.getEref(content);
	}

	/**
	 * フィールド参照先を取得する
	 *
	 * @param content ビルダープロジェクト
	 * @return フィールド参照先
	 */
	@JsonRpcMethod("getFieldFref")
	public FieldContent getFieldFref(
		@JsonRpcParam("content") FieldContent content
	) {
		return fieldMapper.getFref(content);
	}

	/**
	 * クエリーを挿入する
	 *
	 * @param content 挿入するクエリー
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod("insertQuery")
	public boolean insertQuery(QueryContent content) {
		return queryMapper.insert(content);
	}

	/**
	 * クエリーを更新する
	 *
	 * @param content 更新するクエリー
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod("updateQuery")
	public boolean updateQuery(QueryContent content) {
		return queryMapper.update(content);
	}

	/**
	 * クエリーを削除する
	 *
	 * @param content 削除するクエリー
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod("deleteQuery")
	public boolean deleteQuery(QueryContent content) {
		return queryMapper.delete(content);
	}

	/**
	 * クエリーを取得する
	 *
	 * @param queryId クエリーID
	 * @return 取得したクエリー
	 */
	@JsonRpcMethod("getQuery")
	public QueryContent getQuery(
		@JsonRpcParam("queryId") int queryId
	) {
		return queryMapper.get(
				queryId
				);
	}

	/**
	 * 全てのクエリーのサマリーを取得する
	 *
	 * @return クエリーのサマリー
	 */
	@JsonRpcMethod("listQuerySummary")
	public QuerySummary listQuerySummary() {
		return queryMapper.listSummary();
	}

	/**
	 * 全てのクエリーを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クエリーのリスト
	 */
	@JsonRpcMethod("listQueryContent")
	public List<QueryContent> listQueryContent(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		return queryMapper.listContent(offset, limit);
	}

	/**
	 * 所属クラスを取得する
	 *
	 * @param content ビルダープロジェクト
	 * @return 所属クラス
	 */
	@JsonRpcMethod("getQueryOwner")
	public ClassContent getQueryOwner(
		@JsonRpcParam("content") QueryContent content
	) {
		return queryMapper.getOwner(content);
	}

	/**
	 * 列挙を挿入する
	 *
	 * @param content 挿入する列挙
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod("insertEnum")
	public boolean insertEnum(EnumContent content) {
		return enumMapper.insert(content);
	}

	/**
	 * 列挙を更新する
	 *
	 * @param content 更新する列挙
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod("updateEnum")
	public boolean updateEnum(EnumContent content) {
		return enumMapper.update(content);
	}

	/**
	 * 列挙を削除する
	 *
	 * @param content 削除する列挙
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod("deleteEnum")
	public boolean deleteEnum(EnumContent content) {
		return enumMapper.delete(content);
	}

	/**
	 * 列挙を取得する
	 *
	 * @param enumId 列挙ID
	 * @return 取得した列挙
	 */
	@JsonRpcMethod("getEnum")
	public EnumContent getEnum(
		@JsonRpcParam("enumId") int enumId
	) {
		return enumMapper.get(
				enumId
				);
	}

	/**
	 * 全ての列挙のサマリーを取得する
	 *
	 * @return 列挙のサマリー
	 */
	@JsonRpcMethod("listEnumSummary")
	public EnumSummary listEnumSummary() {
		return enumMapper.listSummary();
	}

	/**
	 * 全ての列挙を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙のリスト
	 */
	@JsonRpcMethod("listEnumContent")
	public List<EnumContent> listEnumContent(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		return enumMapper.listContent(offset, limit);
	}

	/**
	 * nameIsに合致する列挙のサマリーを取得する
	 *
	 * @param name name
	 * @return 列挙のサマリー
	 */
	@JsonRpcMethod("listEnumSummaryIfNameIs")
	public EnumSummary listEnumSummaryIfNameIs(
		@JsonRpcParam("name") String name
	) {
		return enumMapper.listSummaryIfNameIs(
				name
				);
	}

	/**
	 * nameIsに合致する列挙を取得する
	 *
	 * @param name name
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙のリスト
	 */
	@JsonRpcMethod("listEnumContentIfNameIs")
	public List<EnumContent> listEnumContentIfNameIs(
		@JsonRpcParam("name") String name,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		return enumMapper.listContentIfNameIs(
				name,
				offset, limit);

	}

	/**
	 * 所属プロジェクトを取得する
	 *
	 * @param content ビルダープロジェクト
	 * @return 所属プロジェクト
	 */
	@JsonRpcMethod("getEnumOwner")
	public ProjectContent getEnumOwner(
		@JsonRpcParam("content") EnumContent content
	) {
		return enumMapper.getOwner(content);
	}

	/**
	 * 列挙値一覧のサマリーを取得する
	 *
	 * @param content ビルダープロジェクト
	 * @return 列挙値一覧のサマリー
	 */
	@JsonRpcMethod("listEnumValuesSummary")
	public EnumValueSummary listEnumValuesSummary(
			@JsonRpcParam("content") EnumContent content
	) {
		return enumMapper.listValuesSummary(content);
	}

	/**
	 * 列挙値一覧を取得する
	 *
	 * @param content 列挙
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙値一覧
	 */
	@JsonRpcMethod("listEnumValuesContent")
	public List<EnumValueContent> listEnumValuesContent(
		@JsonRpcParam("content") EnumContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		return enumMapper.listValuesContent(content, offset, limit);
	}

	/**
	 * 列挙値を挿入する
	 *
	 * @param content 挿入する列挙値
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod("insertEnumValue")
	public boolean insertEnumValue(EnumValueContent content) {
		return enumValueMapper.insert(content);
	}

	/**
	 * 列挙値を更新する
	 *
	 * @param content 更新する列挙値
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod("updateEnumValue")
	public boolean updateEnumValue(EnumValueContent content) {
		return enumValueMapper.update(content);
	}

	/**
	 * 列挙値を削除する
	 *
	 * @param content 削除する列挙値
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod("deleteEnumValue")
	public boolean deleteEnumValue(EnumValueContent content) {
		return enumValueMapper.delete(content);
	}

	/**
	 * 列挙値を取得する
	 *
	 * @param valueId 列挙名
	 * @param ownerEnumId 所属列挙の列挙ID
	 * @return 取得した列挙値
	 */
	@JsonRpcMethod("getEnumValue")
	public EnumValueContent getEnumValue(
		@JsonRpcParam("valueId") String valueId, 
		@JsonRpcParam("ownerEnumId") int ownerEnumId
	) {
		return enumValueMapper.get(
				valueId, 
				ownerEnumId
				);
	}

	/**
	 * 全ての列挙値のサマリーを取得する
	 *
	 * @return 列挙値のサマリー
	 */
	@JsonRpcMethod("listEnumValueSummary")
	public EnumValueSummary listEnumValueSummary() {
		return enumValueMapper.listSummary();
	}

	/**
	 * 全ての列挙値を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙値のリスト
	 */
	@JsonRpcMethod("listEnumValueContent")
	public List<EnumValueContent> listEnumValueContent(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		return enumValueMapper.listContent(offset, limit);
	}

	/**
	 * 所属列挙を取得する
	 *
	 * @param content ビルダープロジェクト
	 * @return 所属列挙
	 */
	@JsonRpcMethod("getEnumValueOwner")
	public EnumContent getEnumValueOwner(
		@JsonRpcParam("content") EnumValueContent content
	) {
		return enumValueMapper.getOwner(content);
	}

}
