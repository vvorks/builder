/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcController;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcMethod;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcParam;
import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.ClassInfo;
import com.github.vvorks.builder.server.domain.ClassSummary;
import com.github.vvorks.builder.server.domain.EnumContent;
import com.github.vvorks.builder.server.domain.EnumInfo;
import com.github.vvorks.builder.server.domain.EnumSummary;
import com.github.vvorks.builder.server.domain.EnumValueContent;
import com.github.vvorks.builder.server.domain.EnumValueInfo;
import com.github.vvorks.builder.server.domain.EnumValueSummary;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.server.domain.FieldInfo;
import com.github.vvorks.builder.server.domain.FieldSummary;
import com.github.vvorks.builder.server.domain.ProjectContent;
import com.github.vvorks.builder.server.domain.ProjectInfo;
import com.github.vvorks.builder.server.domain.ProjectSummary;
import com.github.vvorks.builder.server.domain.QueryContent;
import com.github.vvorks.builder.server.domain.QueryInfo;
import com.github.vvorks.builder.server.domain.QuerySummary;
import com.github.vvorks.builder.server.mapper.ClassMapper;
import com.github.vvorks.builder.server.mapper.EnumMapper;
import com.github.vvorks.builder.server.mapper.EnumValueMapper;
import com.github.vvorks.builder.server.mapper.FieldMapper;
import com.github.vvorks.builder.server.mapper.ProjectMapper;
import com.github.vvorks.builder.server.mapper.QueryMapper;

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
	@JsonRpcMethod
	public boolean insertProject(ProjectContent content) {
		return projectMapper.insert(content);
	}

	/**
	 * プロジェクト型を更新する
	 *
	 * @param content 更新するプロジェクト型
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateProject(ProjectContent content) {
		return projectMapper.update(content);
	}

	/**
	 * プロジェクト型を削除する
	 *
	 * @param content 削除するプロジェクト型
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteProject(ProjectContent content) {
		return projectMapper.delete(content);
	}

	/**
	 * プロジェクト型を取得する
	 *
	 * @param projectId プロジェクトID
	 * @return 取得したプロジェクト型
	 */
	@JsonRpcMethod
	public ProjectContent getProject(
		@JsonRpcParam("projectId") int projectId
	) {
		return projectMapper.get(
				projectId
				);
	}

	/**
	 * 全てのプロジェクト型情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @param lastCount 前回検索時に取得した件数（データ更新チェックに使用する）
	 * @param lastUpdatedAt 前回検索時のデータ更新時刻（データ更新チェックに使用する）
	 * @return プロジェクト型情報
	 */
	@JsonRpcMethod
	public ProjectInfo listProject(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit,
		@JsonRpcParam("lastCount") int lastCount,
		@JsonRpcParam("lastUpdatedAt") java.util.Date lastUpdatedAt
	) {
		ProjectInfo info = new ProjectInfo();
		ProjectSummary summary = projectMapper.listSummary();
		info.setSummary(summary);
		info.setUpdated(summary.isUpdated(lastCount, lastUpdatedAt));
		if (info.isUpdated()) {
			offset = summary.getFocus();
		}
		info.setOffset(offset);
		List<ProjectContent> contents = projectMapper.listContent(offset, limit);
		info.setContents(contents);
		return info;
	}

	/**
	 * 名前に合致するプロジェクト型情報を取得する
	 *
	 * @param name name
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @param lastCount 前回検索時に取得した件数（データ更新チェックに使用する）
	 * @param lastUpdatedAt 前回検索時のデータ更新時刻（データ更新チェックに使用する）
	 * @return プロジェクト型情報
	 */
	@JsonRpcMethod
	public ProjectInfo listProjectIfNameIs(
		@JsonRpcParam("name") String name,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit,
		@JsonRpcParam("lastCount") int lastCount,
		@JsonRpcParam("lastUpdatedAt") java.util.Date lastUpdatedAt
	) {
		ProjectInfo info = new ProjectInfo();
		ProjectSummary summary = projectMapper.listSummaryIfNameIs(
				name
				);
		info.setSummary(summary);
		info.setUpdated(summary.isUpdated(lastCount, lastUpdatedAt));
		if (info.isUpdated()) {
			offset = summary.getFocus();
		}
		info.setOffset(offset);
		List<ProjectContent> contents = projectMapper.listContentIfNameIs(
				name,
				offset, limit);
		info.setContents(contents);
		return info;
	}

	/**
	 * クラス一覧情報を取得する
	 *
	 * @param content プロジェクト型
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @param lastCount 前回検索時に取得した件数（データ更新チェックに使用する）
	 * @param lastUpdatedAt 前回検索時のデータ更新時刻（データ更新チェックに使用する）
	 * @return クラス一覧情報
	 */
	@JsonRpcMethod
	public ClassInfo listProjectClasses(
		@JsonRpcParam("content") ProjectContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit,
		@JsonRpcParam("lastCount") int lastCount,
		@JsonRpcParam("lastUpdatedAt") java.util.Date lastUpdatedAt
	) {
		ClassInfo info = new ClassInfo();
		ClassSummary summary = projectMapper.listClassesSummary(content);
		info.setSummary(summary);
		info.setUpdated(summary.isUpdated(lastCount, lastUpdatedAt));
		if (info.isUpdated()) {
			offset = summary.getFocus();
		}
		info.setOffset(offset);
		List<ClassContent> contents =
				projectMapper.listClassesContent(content, offset, limit);
		info.setContents(contents);
		return info;
	}

	/**
	 * 名前に合致するクラス一覧情報を取得する
	 *
	 * @param content プロジェクト型
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @param lastCount 前回検索時に取得した件数（データ更新チェックに使用する）
	 * @param lastUpdatedAt 前回検索時のデータ更新時刻（データ更新チェックに使用する）
	 * @return クラス一覧情報
	 */
	@JsonRpcMethod
	public ClassInfo listProjectClassesIfNameIs(
		@JsonRpcParam("content") ProjectContent content,
		@JsonRpcParam("name") String name,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit,
		@JsonRpcParam("lastCount") int lastCount,
		@JsonRpcParam("lastUpdatedAt") java.util.Date lastUpdatedAt
	) {
		ClassInfo info = new ClassInfo();
		ClassSummary summary = projectMapper.listClassesSummaryIfNameIs(
				content,
				name
				);
		info.setSummary(summary);
		info.setUpdated(summary.isUpdated(lastCount, lastUpdatedAt));
		if (info.isUpdated()) {
			offset = summary.getFocus();
		}
		info.setOffset(offset);
		List<ClassContent> contents = projectMapper.listClassesContentIfNameIs(
				content,
				name,
				offset, limit);
		info.setContents(contents);
		return info;
	}

	/**
	 * 列挙一覧情報を取得する
	 *
	 * @param content プロジェクト型
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @param lastCount 前回検索時に取得した件数（データ更新チェックに使用する）
	 * @param lastUpdatedAt 前回検索時のデータ更新時刻（データ更新チェックに使用する）
	 * @return 列挙一覧情報
	 */
	@JsonRpcMethod
	public EnumInfo listProjectEnums(
		@JsonRpcParam("content") ProjectContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit,
		@JsonRpcParam("lastCount") int lastCount,
		@JsonRpcParam("lastUpdatedAt") java.util.Date lastUpdatedAt
	) {
		EnumInfo info = new EnumInfo();
		EnumSummary summary = projectMapper.listEnumsSummary(content);
		info.setSummary(summary);
		info.setUpdated(summary.isUpdated(lastCount, lastUpdatedAt));
		if (info.isUpdated()) {
			offset = summary.getFocus();
		}
		info.setOffset(offset);
		List<EnumContent> contents =
				projectMapper.listEnumsContent(content, offset, limit);
		info.setContents(contents);
		return info;
	}

	/**
	 * 名前に合致する列挙一覧情報を取得する
	 *
	 * @param content プロジェクト型
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @param lastCount 前回検索時に取得した件数（データ更新チェックに使用する）
	 * @param lastUpdatedAt 前回検索時のデータ更新時刻（データ更新チェックに使用する）
	 * @return 列挙一覧情報
	 */
	@JsonRpcMethod
	public EnumInfo listProjectEnumsIfNameIs(
		@JsonRpcParam("content") ProjectContent content,
		@JsonRpcParam("name") String name,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit,
		@JsonRpcParam("lastCount") int lastCount,
		@JsonRpcParam("lastUpdatedAt") java.util.Date lastUpdatedAt
	) {
		EnumInfo info = new EnumInfo();
		EnumSummary summary = projectMapper.listEnumsSummaryIfNameIs(
				content,
				name
				);
		info.setSummary(summary);
		info.setUpdated(summary.isUpdated(lastCount, lastUpdatedAt));
		if (info.isUpdated()) {
			offset = summary.getFocus();
		}
		info.setOffset(offset);
		List<EnumContent> contents = projectMapper.listEnumsContentIfNameIs(
				content,
				name,
				offset, limit);
		info.setContents(contents);
		return info;
	}

	/**
	 * クラスを挿入する
	 *
	 * @param content 挿入するクラス
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean insertClass(ClassContent content) {
		return classMapper.insert(content);
	}

	/**
	 * クラスを更新する
	 *
	 * @param content 更新するクラス
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateClass(ClassContent content) {
		return classMapper.update(content);
	}

	/**
	 * クラスを削除する
	 *
	 * @param content 削除するクラス
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteClass(ClassContent content) {
		return classMapper.delete(content);
	}

	/**
	 * クラスを取得する
	 *
	 * @param classId クラスID
	 * @return 取得したクラス
	 */
	@JsonRpcMethod
	public ClassContent getClass(
		@JsonRpcParam("classId") int classId
	) {
		return classMapper.get(
				classId
				);
	}

	/**
	 * 全てのクラス情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @param lastCount 前回検索時に取得した件数（データ更新チェックに使用する）
	 * @param lastUpdatedAt 前回検索時のデータ更新時刻（データ更新チェックに使用する）
	 * @return クラス情報
	 */
	@JsonRpcMethod
	public ClassInfo listClass(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit,
		@JsonRpcParam("lastCount") int lastCount,
		@JsonRpcParam("lastUpdatedAt") java.util.Date lastUpdatedAt
	) {
		ClassInfo info = new ClassInfo();
		ClassSummary summary = classMapper.listSummary();
		info.setSummary(summary);
		info.setUpdated(summary.isUpdated(lastCount, lastUpdatedAt));
		if (info.isUpdated()) {
			offset = summary.getFocus();
		}
		info.setOffset(offset);
		List<ClassContent> contents = classMapper.listContent(offset, limit);
		info.setContents(contents);
		return info;
	}

	/**
	 * 名前に合致するクラス情報を取得する
	 *
	 * @param name name
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @param lastCount 前回検索時に取得した件数（データ更新チェックに使用する）
	 * @param lastUpdatedAt 前回検索時のデータ更新時刻（データ更新チェックに使用する）
	 * @return クラス情報
	 */
	@JsonRpcMethod
	public ClassInfo listClassIfNameIs(
		@JsonRpcParam("name") String name,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit,
		@JsonRpcParam("lastCount") int lastCount,
		@JsonRpcParam("lastUpdatedAt") java.util.Date lastUpdatedAt
	) {
		ClassInfo info = new ClassInfo();
		ClassSummary summary = classMapper.listSummaryIfNameIs(
				name
				);
		info.setSummary(summary);
		info.setUpdated(summary.isUpdated(lastCount, lastUpdatedAt));
		if (info.isUpdated()) {
			offset = summary.getFocus();
		}
		info.setOffset(offset);
		List<ClassContent> contents = classMapper.listContentIfNameIs(
				name,
				offset, limit);
		info.setContents(contents);
		return info;
	}

	/**
	 * 所属プロジェクトを取得する
	 *
	 * @param content ビルダープロジェクト
	 * @return 所属プロジェクト
	 */
	@JsonRpcMethod
	public ProjectContent getClassOwner(
		@JsonRpcParam("content") ClassContent content
	) {
		return classMapper.getOwner(content);
	}

	/**
	 * フィールド一覧情報を取得する
	 *
	 * @param content クラス
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @param lastCount 前回検索時に取得した件数（データ更新チェックに使用する）
	 * @param lastUpdatedAt 前回検索時のデータ更新時刻（データ更新チェックに使用する）
	 * @return フィールド一覧情報
	 */
	@JsonRpcMethod
	public FieldInfo listClassFields(
		@JsonRpcParam("content") ClassContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit,
		@JsonRpcParam("lastCount") int lastCount,
		@JsonRpcParam("lastUpdatedAt") java.util.Date lastUpdatedAt
	) {
		FieldInfo info = new FieldInfo();
		FieldSummary summary = classMapper.listFieldsSummary(content);
		info.setSummary(summary);
		info.setUpdated(summary.isUpdated(lastCount, lastUpdatedAt));
		if (info.isUpdated()) {
			offset = summary.getFocus();
		}
		info.setOffset(offset);
		List<FieldContent> contents =
				classMapper.listFieldsContent(content, offset, limit);
		info.setContents(contents);
		return info;
	}

	/**
	 * 名前に合致するフィールド一覧情報を取得する
	 *
	 * @param content クラス
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @param lastCount 前回検索時に取得した件数（データ更新チェックに使用する）
	 * @param lastUpdatedAt 前回検索時のデータ更新時刻（データ更新チェックに使用する）
	 * @return フィールド一覧情報
	 */
	@JsonRpcMethod
	public FieldInfo listClassFieldsIfNameIs(
		@JsonRpcParam("content") ClassContent content,
		@JsonRpcParam("name") String name,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit,
		@JsonRpcParam("lastCount") int lastCount,
		@JsonRpcParam("lastUpdatedAt") java.util.Date lastUpdatedAt
	) {
		FieldInfo info = new FieldInfo();
		FieldSummary summary = classMapper.listFieldsSummaryIfNameIs(
				content,
				name
				);
		info.setSummary(summary);
		info.setUpdated(summary.isUpdated(lastCount, lastUpdatedAt));
		if (info.isUpdated()) {
			offset = summary.getFocus();
		}
		info.setOffset(offset);
		List<FieldContent> contents = classMapper.listFieldsContentIfNameIs(
				content,
				name,
				offset, limit);
		info.setContents(contents);
		return info;
	}

	/**
	 * queries情報を取得する
	 *
	 * @param content クラス
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @param lastCount 前回検索時に取得した件数（データ更新チェックに使用する）
	 * @param lastUpdatedAt 前回検索時のデータ更新時刻（データ更新チェックに使用する）
	 * @return queries情報
	 */
	@JsonRpcMethod
	public QueryInfo listClassQueries(
		@JsonRpcParam("content") ClassContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit,
		@JsonRpcParam("lastCount") int lastCount,
		@JsonRpcParam("lastUpdatedAt") java.util.Date lastUpdatedAt
	) {
		QueryInfo info = new QueryInfo();
		QuerySummary summary = classMapper.listQueriesSummary(content);
		info.setSummary(summary);
		info.setUpdated(summary.isUpdated(lastCount, lastUpdatedAt));
		if (info.isUpdated()) {
			offset = summary.getFocus();
		}
		info.setOffset(offset);
		List<QueryContent> contents =
				classMapper.listQueriesContent(content, offset, limit);
		info.setContents(contents);
		return info;
	}

	/**
	 * フィールドを挿入する
	 *
	 * @param content 挿入するフィールド
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean insertField(FieldContent content) {
		return fieldMapper.insert(content);
	}

	/**
	 * フィールドを更新する
	 *
	 * @param content 更新するフィールド
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateField(FieldContent content) {
		return fieldMapper.update(content);
	}

	/**
	 * フィールドを削除する
	 *
	 * @param content 削除するフィールド
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteField(FieldContent content) {
		return fieldMapper.delete(content);
	}

	/**
	 * フィールドを取得する
	 *
	 * @param fieldId フィールドID
	 * @return 取得したフィールド
	 */
	@JsonRpcMethod
	public FieldContent getField(
		@JsonRpcParam("fieldId") int fieldId
	) {
		return fieldMapper.get(
				fieldId
				);
	}

	/**
	 * 全てのフィールド情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @param lastCount 前回検索時に取得した件数（データ更新チェックに使用する）
	 * @param lastUpdatedAt 前回検索時のデータ更新時刻（データ更新チェックに使用する）
	 * @return フィールド情報
	 */
	@JsonRpcMethod
	public FieldInfo listField(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit,
		@JsonRpcParam("lastCount") int lastCount,
		@JsonRpcParam("lastUpdatedAt") java.util.Date lastUpdatedAt
	) {
		FieldInfo info = new FieldInfo();
		FieldSummary summary = fieldMapper.listSummary();
		info.setSummary(summary);
		info.setUpdated(summary.isUpdated(lastCount, lastUpdatedAt));
		if (info.isUpdated()) {
			offset = summary.getFocus();
		}
		info.setOffset(offset);
		List<FieldContent> contents = fieldMapper.listContent(offset, limit);
		info.setContents(contents);
		return info;
	}

	/**
	 * 名前に合致するフィールド情報を取得する
	 *
	 * @param name name
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @param lastCount 前回検索時に取得した件数（データ更新チェックに使用する）
	 * @param lastUpdatedAt 前回検索時のデータ更新時刻（データ更新チェックに使用する）
	 * @return フィールド情報
	 */
	@JsonRpcMethod
	public FieldInfo listFieldIfNameIs(
		@JsonRpcParam("name") String name,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit,
		@JsonRpcParam("lastCount") int lastCount,
		@JsonRpcParam("lastUpdatedAt") java.util.Date lastUpdatedAt
	) {
		FieldInfo info = new FieldInfo();
		FieldSummary summary = fieldMapper.listSummaryIfNameIs(
				name
				);
		info.setSummary(summary);
		info.setUpdated(summary.isUpdated(lastCount, lastUpdatedAt));
		if (info.isUpdated()) {
			offset = summary.getFocus();
		}
		info.setOffset(offset);
		List<FieldContent> contents = fieldMapper.listContentIfNameIs(
				name,
				offset, limit);
		info.setContents(contents);
		return info;
	}

	/**
	 * 所属クラスを取得する
	 *
	 * @param content ビルダープロジェクト
	 * @return 所属クラス
	 */
	@JsonRpcMethod
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
	@JsonRpcMethod
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
	@JsonRpcMethod
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
	@JsonRpcMethod
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
	@JsonRpcMethod
	public boolean insertQuery(QueryContent content) {
		return queryMapper.insert(content);
	}

	/**
	 * クエリーを更新する
	 *
	 * @param content 更新するクエリー
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateQuery(QueryContent content) {
		return queryMapper.update(content);
	}

	/**
	 * クエリーを削除する
	 *
	 * @param content 削除するクエリー
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteQuery(QueryContent content) {
		return queryMapper.delete(content);
	}

	/**
	 * クエリーを取得する
	 *
	 * @param queryId クエリーID
	 * @return 取得したクエリー
	 */
	@JsonRpcMethod
	public QueryContent getQuery(
		@JsonRpcParam("queryId") int queryId
	) {
		return queryMapper.get(
				queryId
				);
	}

	/**
	 * 全てのクエリー情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @param lastCount 前回検索時に取得した件数（データ更新チェックに使用する）
	 * @param lastUpdatedAt 前回検索時のデータ更新時刻（データ更新チェックに使用する）
	 * @return クエリー情報
	 */
	@JsonRpcMethod
	public QueryInfo listQuery(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit,
		@JsonRpcParam("lastCount") int lastCount,
		@JsonRpcParam("lastUpdatedAt") java.util.Date lastUpdatedAt
	) {
		QueryInfo info = new QueryInfo();
		QuerySummary summary = queryMapper.listSummary();
		info.setSummary(summary);
		info.setUpdated(summary.isUpdated(lastCount, lastUpdatedAt));
		if (info.isUpdated()) {
			offset = summary.getFocus();
		}
		info.setOffset(offset);
		List<QueryContent> contents = queryMapper.listContent(offset, limit);
		info.setContents(contents);
		return info;
	}

	/**
	 * 所属クラスを取得する
	 *
	 * @param content ビルダープロジェクト
	 * @return 所属クラス
	 */
	@JsonRpcMethod
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
	@JsonRpcMethod
	public boolean insertEnum(EnumContent content) {
		return enumMapper.insert(content);
	}

	/**
	 * 列挙を更新する
	 *
	 * @param content 更新する列挙
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateEnum(EnumContent content) {
		return enumMapper.update(content);
	}

	/**
	 * 列挙を削除する
	 *
	 * @param content 削除する列挙
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteEnum(EnumContent content) {
		return enumMapper.delete(content);
	}

	/**
	 * 列挙を取得する
	 *
	 * @param enumId 列挙ID
	 * @return 取得した列挙
	 */
	@JsonRpcMethod
	public EnumContent getEnum(
		@JsonRpcParam("enumId") int enumId
	) {
		return enumMapper.get(
				enumId
				);
	}

	/**
	 * 全ての列挙情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @param lastCount 前回検索時に取得した件数（データ更新チェックに使用する）
	 * @param lastUpdatedAt 前回検索時のデータ更新時刻（データ更新チェックに使用する）
	 * @return 列挙情報
	 */
	@JsonRpcMethod
	public EnumInfo listEnum(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit,
		@JsonRpcParam("lastCount") int lastCount,
		@JsonRpcParam("lastUpdatedAt") java.util.Date lastUpdatedAt
	) {
		EnumInfo info = new EnumInfo();
		EnumSummary summary = enumMapper.listSummary();
		info.setSummary(summary);
		info.setUpdated(summary.isUpdated(lastCount, lastUpdatedAt));
		if (info.isUpdated()) {
			offset = summary.getFocus();
		}
		info.setOffset(offset);
		List<EnumContent> contents = enumMapper.listContent(offset, limit);
		info.setContents(contents);
		return info;
	}

	/**
	 * 名前に合致する列挙情報を取得する
	 *
	 * @param name name
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @param lastCount 前回検索時に取得した件数（データ更新チェックに使用する）
	 * @param lastUpdatedAt 前回検索時のデータ更新時刻（データ更新チェックに使用する）
	 * @return 列挙情報
	 */
	@JsonRpcMethod
	public EnumInfo listEnumIfNameIs(
		@JsonRpcParam("name") String name,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit,
		@JsonRpcParam("lastCount") int lastCount,
		@JsonRpcParam("lastUpdatedAt") java.util.Date lastUpdatedAt
	) {
		EnumInfo info = new EnumInfo();
		EnumSummary summary = enumMapper.listSummaryIfNameIs(
				name
				);
		info.setSummary(summary);
		info.setUpdated(summary.isUpdated(lastCount, lastUpdatedAt));
		if (info.isUpdated()) {
			offset = summary.getFocus();
		}
		info.setOffset(offset);
		List<EnumContent> contents = enumMapper.listContentIfNameIs(
				name,
				offset, limit);
		info.setContents(contents);
		return info;
	}

	/**
	 * 所属プロジェクトを取得する
	 *
	 * @param content ビルダープロジェクト
	 * @return 所属プロジェクト
	 */
	@JsonRpcMethod
	public ProjectContent getEnumOwner(
		@JsonRpcParam("content") EnumContent content
	) {
		return enumMapper.getOwner(content);
	}

	/**
	 * 列挙値一覧情報を取得する
	 *
	 * @param content 列挙
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @param lastCount 前回検索時に取得した件数（データ更新チェックに使用する）
	 * @param lastUpdatedAt 前回検索時のデータ更新時刻（データ更新チェックに使用する）
	 * @return 列挙値一覧情報
	 */
	@JsonRpcMethod
	public EnumValueInfo listEnumValues(
		@JsonRpcParam("content") EnumContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit,
		@JsonRpcParam("lastCount") int lastCount,
		@JsonRpcParam("lastUpdatedAt") java.util.Date lastUpdatedAt
	) {
		EnumValueInfo info = new EnumValueInfo();
		EnumValueSummary summary = enumMapper.listValuesSummary(content);
		info.setSummary(summary);
		info.setUpdated(summary.isUpdated(lastCount, lastUpdatedAt));
		if (info.isUpdated()) {
			offset = summary.getFocus();
		}
		info.setOffset(offset);
		List<EnumValueContent> contents =
				enumMapper.listValuesContent(content, offset, limit);
		info.setContents(contents);
		return info;
	}

	/**
	 * 列挙値を挿入する
	 *
	 * @param content 挿入する列挙値
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean insertEnumValue(EnumValueContent content) {
		return enumValueMapper.insert(content);
	}

	/**
	 * 列挙値を更新する
	 *
	 * @param content 更新する列挙値
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateEnumValue(EnumValueContent content) {
		return enumValueMapper.update(content);
	}

	/**
	 * 列挙値を削除する
	 *
	 * @param content 削除する列挙値
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
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
	@JsonRpcMethod
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
	 * 全ての列挙値情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @param lastCount 前回検索時に取得した件数（データ更新チェックに使用する）
	 * @param lastUpdatedAt 前回検索時のデータ更新時刻（データ更新チェックに使用する）
	 * @return 列挙値情報
	 */
	@JsonRpcMethod
	public EnumValueInfo listEnumValue(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit,
		@JsonRpcParam("lastCount") int lastCount,
		@JsonRpcParam("lastUpdatedAt") java.util.Date lastUpdatedAt
	) {
		EnumValueInfo info = new EnumValueInfo();
		EnumValueSummary summary = enumValueMapper.listSummary();
		info.setSummary(summary);
		info.setUpdated(summary.isUpdated(lastCount, lastUpdatedAt));
		if (info.isUpdated()) {
			offset = summary.getFocus();
		}
		info.setOffset(offset);
		List<EnumValueContent> contents = enumValueMapper.listContent(offset, limit);
		info.setContents(contents);
		return info;
	}

	/**
	 * 所属列挙を取得する
	 *
	 * @param content ビルダープロジェクト
	 * @return 所属列挙
	 */
	@JsonRpcMethod
	public EnumContent getEnumValueOwner(
		@JsonRpcParam("content") EnumValueContent content
	) {
		return enumValueMapper.getOwner(content);
	}

}
