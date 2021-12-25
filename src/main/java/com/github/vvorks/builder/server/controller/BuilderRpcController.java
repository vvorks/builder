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
import com.github.vvorks.builder.server.domain.ClassSubject;
import com.github.vvorks.builder.server.domain.ClassSummary;
import com.github.vvorks.builder.server.domain.EnumContent;
import com.github.vvorks.builder.server.domain.EnumSubject;
import com.github.vvorks.builder.server.domain.EnumSummary;
import com.github.vvorks.builder.server.domain.EnumValueContent;
import com.github.vvorks.builder.server.domain.EnumValueSubject;
import com.github.vvorks.builder.server.domain.EnumValueSummary;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.server.domain.FieldSubject;
import com.github.vvorks.builder.server.domain.FieldSummary;
import com.github.vvorks.builder.server.domain.ProjectContent;
import com.github.vvorks.builder.server.domain.ProjectSubject;
import com.github.vvorks.builder.server.domain.ProjectSummary;
import com.github.vvorks.builder.server.domain.QueryContent;
import com.github.vvorks.builder.server.domain.QuerySubject;
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

	/** プロジェクトのMapper */
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
	 * プロジェクトを挿入する
	 *
	 * @param content 挿入するプロジェクト
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean insertProject(ProjectContent content) {
		return projectMapper.insert(content);
	}

	/**
	 * プロジェクトを更新する
	 *
	 * @param content 更新するプロジェクト
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateProject(ProjectContent content) {
		return projectMapper.update(content);
	}

	/**
	 * プロジェクトを削除する
	 *
	 * @param content 削除するプロジェクト
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteProject(ProjectContent content) {
		return projectMapper.delete(content);
	}

	/**
	 * プロジェクトを取得する
	 *
	 * @param projectId プロジェクトID
	 * @return 取得したプロジェクト
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
	 * 全てのプロジェクト情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return プロジェクト情報
	 */
	@JsonRpcMethod
	public ProjectSummary<ProjectContent> listProject(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ProjectSummary<ProjectContent> summary = projectMapper.listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectContent> contents = projectMapper.listContent(offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 名前に合致するプロジェクト情報を取得する
	 *
	 * @param name name
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return プロジェクト情報
	 */
	@JsonRpcMethod
	public ProjectSummary<ProjectContent> listProjectIfNameIs(
		@JsonRpcParam("name") String name,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ProjectSummary<ProjectContent> summary = projectMapper.listSummaryIfNameIs(
				name
				);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectContent> contents = projectMapper.listContentIfNameIs(
				name,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * クラス一覧情報を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラス一覧情報
	 */
	@JsonRpcMethod
	public ClassSummary<ClassContent> listProjectClasses(
		@JsonRpcParam("content") ProjectContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ClassSummary<ClassContent> summary = projectMapper.listClassesSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassContent> contents =
				projectMapper.listClassesContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 名前に合致するクラス一覧情報を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラス一覧情報
	 */
	@JsonRpcMethod
	public ClassSummary<ClassContent> listProjectClassesIfNameIs(
		@JsonRpcParam("content") ProjectContent content,
		@JsonRpcParam("name") String name,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ClassSummary<ClassContent> summary = projectMapper.listClassesSummaryIfNameIs(
				content,
				name
				);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassContent> contents = projectMapper.listClassesContentIfNameIs(
				content,
				name,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 列挙一覧情報を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙一覧情報
	 */
	@JsonRpcMethod
	public EnumSummary<EnumContent> listProjectEnums(
		@JsonRpcParam("content") ProjectContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		EnumSummary<EnumContent> summary = projectMapper.listEnumsSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumContent> contents =
				projectMapper.listEnumsContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 名前に合致する列挙一覧情報を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙一覧情報
	 */
	@JsonRpcMethod
	public EnumSummary<EnumContent> listProjectEnumsIfNameIs(
		@JsonRpcParam("content") ProjectContent content,
		@JsonRpcParam("name") String name,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		EnumSummary<EnumContent> summary = projectMapper.listEnumsSummaryIfNameIs(
				content,
				name
				);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumContent> contents = projectMapper.listEnumsContentIfNameIs(
				content,
				name,
				offset, limit);
		summary.setContents(contents);
		return summary;
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
	 * @return クラス情報
	 */
	@JsonRpcMethod
	public ClassSummary<ClassContent> listClass(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ClassSummary<ClassContent> summary = classMapper.listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassContent> contents = classMapper.listContent(offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 名前に合致するクラス情報を取得する
	 *
	 * @param name name
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラス情報
	 */
	@JsonRpcMethod
	public ClassSummary<ClassContent> listClassIfNameIs(
		@JsonRpcParam("name") String name,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ClassSummary<ClassContent> summary = classMapper.listSummaryIfNameIs(
				name
				);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassContent> contents = classMapper.listContentIfNameIs(
				name,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 所属プロジェクトを取得する
	 *
	 * @param content クラス
	 * @return 所属プロジェクト
	 */
	@JsonRpcMethod
	public ProjectContent getClassOwner(
		@JsonRpcParam("content") ClassContent content
	) {
		return classMapper.getOwner(content);
	}

	/**
	 * 所属プロジェクトの候補一覧を取得する
	 *
	 * @param content クラス
	 * @return 所属プロジェクトの候補一覧
	 */
	@JsonRpcMethod
	public ProjectSummary<ProjectSubject> listClassOwnerCandidate(
		@JsonRpcParam("content") ClassContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ProjectSummary<ProjectSubject> summary = classMapper.listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectSubject> contents = classMapper.listOwnerCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * フィールド一覧情報を取得する
	 *
	 * @param content クラス
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールド一覧情報
	 */
	@JsonRpcMethod
	public FieldSummary<FieldContent> listClassFields(
		@JsonRpcParam("content") ClassContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		FieldSummary<FieldContent> summary = classMapper.listFieldsSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<FieldContent> contents =
				classMapper.listFieldsContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 名前に合致するフィールド一覧情報を取得する
	 *
	 * @param content クラス
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールド一覧情報
	 */
	@JsonRpcMethod
	public FieldSummary<FieldContent> listClassFieldsIfNameIs(
		@JsonRpcParam("content") ClassContent content,
		@JsonRpcParam("name") String name,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		FieldSummary<FieldContent> summary = classMapper.listFieldsSummaryIfNameIs(
				content,
				name
				);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<FieldContent> contents = classMapper.listFieldsContentIfNameIs(
				content,
				name,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * queries情報を取得する
	 *
	 * @param content クラス
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return queries情報
	 */
	@JsonRpcMethod
	public QuerySummary<QueryContent> listClassQueries(
		@JsonRpcParam("content") ClassContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		QuerySummary<QueryContent> summary = classMapper.listQueriesSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<QueryContent> contents =
				classMapper.listQueriesContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
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
	 * @return フィールド情報
	 */
	@JsonRpcMethod
	public FieldSummary<FieldContent> listField(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		FieldSummary<FieldContent> summary = fieldMapper.listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<FieldContent> contents = fieldMapper.listContent(offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 名前に合致するフィールド情報を取得する
	 *
	 * @param name name
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールド情報
	 */
	@JsonRpcMethod
	public FieldSummary<FieldContent> listFieldIfNameIs(
		@JsonRpcParam("name") String name,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		FieldSummary<FieldContent> summary = fieldMapper.listSummaryIfNameIs(
				name
				);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<FieldContent> contents = fieldMapper.listContentIfNameIs(
				name,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 所属クラスを取得する
	 *
	 * @param content フィールド
	 * @return 所属クラス
	 */
	@JsonRpcMethod
	public ClassContent getFieldOwner(
		@JsonRpcParam("content") FieldContent content
	) {
		return fieldMapper.getOwner(content);
	}

	/**
	 * 所属クラスの候補一覧を取得する
	 *
	 * @param content フィールド
	 * @return 所属クラスの候補一覧
	 */
	@JsonRpcMethod
	public ClassSummary<ClassSubject> listFieldOwnerCandidate(
		@JsonRpcParam("content") FieldContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ClassSummary<ClassSubject> summary = fieldMapper.listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassSubject> contents = fieldMapper.listOwnerCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * クラス参照先を取得する
	 *
	 * @param content フィールド
	 * @return クラス参照先
	 */
	@JsonRpcMethod
	public ClassContent getFieldCref(
		@JsonRpcParam("content") FieldContent content
	) {
		return fieldMapper.getCref(content);
	}

	/**
	 * クラス参照先の候補一覧を取得する
	 *
	 * @param content フィールド
	 * @return クラス参照先の候補一覧
	 */
	@JsonRpcMethod
	public ClassSummary<ClassSubject> listFieldCrefCandidate(
		@JsonRpcParam("content") FieldContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ClassSummary<ClassSubject> summary = fieldMapper.listCrefCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassSubject> contents = fieldMapper.listCrefCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 列挙参照先を取得する
	 *
	 * @param content フィールド
	 * @return 列挙参照先
	 */
	@JsonRpcMethod
	public EnumContent getFieldEref(
		@JsonRpcParam("content") FieldContent content
	) {
		return fieldMapper.getEref(content);
	}

	/**
	 * 列挙参照先の候補一覧を取得する
	 *
	 * @param content フィールド
	 * @return 列挙参照先の候補一覧
	 */
	@JsonRpcMethod
	public EnumSummary<EnumSubject> listFieldErefCandidate(
		@JsonRpcParam("content") FieldContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		EnumSummary<EnumSubject> summary = fieldMapper.listErefCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumSubject> contents = fieldMapper.listErefCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * フィールド参照先を取得する
	 *
	 * @param content フィールド
	 * @return フィールド参照先
	 */
	@JsonRpcMethod
	public FieldContent getFieldFref(
		@JsonRpcParam("content") FieldContent content
	) {
		return fieldMapper.getFref(content);
	}

	/**
	 * フィールド参照先の候補一覧を取得する
	 *
	 * @param content フィールド
	 * @return フィールド参照先の候補一覧
	 */
	@JsonRpcMethod
	public FieldSummary<FieldSubject> listFieldFrefCandidate(
		@JsonRpcParam("content") FieldContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		FieldSummary<FieldSubject> summary = fieldMapper.listFrefCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<FieldSubject> contents = fieldMapper.listFrefCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
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
	 * @return クエリー情報
	 */
	@JsonRpcMethod
	public QuerySummary<QueryContent> listQuery(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		QuerySummary<QueryContent> summary = queryMapper.listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<QueryContent> contents = queryMapper.listContent(offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 所属クラスを取得する
	 *
	 * @param content クエリー
	 * @return 所属クラス
	 */
	@JsonRpcMethod
	public ClassContent getQueryOwner(
		@JsonRpcParam("content") QueryContent content
	) {
		return queryMapper.getOwner(content);
	}

	/**
	 * 所属クラスの候補一覧を取得する
	 *
	 * @param content クエリー
	 * @return 所属クラスの候補一覧
	 */
	@JsonRpcMethod
	public ClassSummary<ClassSubject> listQueryOwnerCandidate(
		@JsonRpcParam("content") QueryContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ClassSummary<ClassSubject> summary = queryMapper.listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassSubject> contents = queryMapper.listOwnerCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
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
	 * @return 列挙情報
	 */
	@JsonRpcMethod
	public EnumSummary<EnumContent> listEnum(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		EnumSummary<EnumContent> summary = enumMapper.listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumContent> contents = enumMapper.listContent(offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 名前に合致する列挙情報を取得する
	 *
	 * @param name name
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙情報
	 */
	@JsonRpcMethod
	public EnumSummary<EnumContent> listEnumIfNameIs(
		@JsonRpcParam("name") String name,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		EnumSummary<EnumContent> summary = enumMapper.listSummaryIfNameIs(
				name
				);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumContent> contents = enumMapper.listContentIfNameIs(
				name,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 所属プロジェクトを取得する
	 *
	 * @param content 列挙
	 * @return 所属プロジェクト
	 */
	@JsonRpcMethod
	public ProjectContent getEnumOwner(
		@JsonRpcParam("content") EnumContent content
	) {
		return enumMapper.getOwner(content);
	}

	/**
	 * 所属プロジェクトの候補一覧を取得する
	 *
	 * @param content 列挙
	 * @return 所属プロジェクトの候補一覧
	 */
	@JsonRpcMethod
	public ProjectSummary<ProjectSubject> listEnumOwnerCandidate(
		@JsonRpcParam("content") EnumContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ProjectSummary<ProjectSubject> summary = enumMapper.listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectSubject> contents = enumMapper.listOwnerCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 列挙値一覧情報を取得する
	 *
	 * @param content 列挙
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙値一覧情報
	 */
	@JsonRpcMethod
	public EnumValueSummary<EnumValueContent> listEnumValues(
		@JsonRpcParam("content") EnumContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		EnumValueSummary<EnumValueContent> summary = enumMapper.listValuesSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumValueContent> contents =
				enumMapper.listValuesContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
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
	 * @param ownerEnumId 所属列挙の列挙ID
	 * @param valueId 列挙名
	 * @return 取得した列挙値
	 */
	@JsonRpcMethod
	public EnumValueContent getEnumValue(
		@JsonRpcParam("ownerEnumId") int ownerEnumId, 
		@JsonRpcParam("valueId") String valueId
	) {
		return enumValueMapper.get(
				ownerEnumId, 
				valueId
				);
	}

	/**
	 * 全ての列挙値情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙値情報
	 */
	@JsonRpcMethod
	public EnumValueSummary<EnumValueContent> listEnumValue(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		EnumValueSummary<EnumValueContent> summary = enumValueMapper.listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumValueContent> contents = enumValueMapper.listContent(offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 所属列挙を取得する
	 *
	 * @param content 列挙値
	 * @return 所属列挙
	 */
	@JsonRpcMethod
	public EnumContent getEnumValueOwner(
		@JsonRpcParam("content") EnumValueContent content
	) {
		return enumValueMapper.getOwner(content);
	}

	/**
	 * 所属列挙の候補一覧を取得する
	 *
	 * @param content 列挙値
	 * @return 所属列挙の候補一覧
	 */
	@JsonRpcMethod
	public EnumSummary<EnumSubject> listEnumValueOwnerCandidate(
		@JsonRpcParam("content") EnumValueContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		EnumSummary<EnumSubject> summary = enumValueMapper.listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumSubject> contents = enumValueMapper.listOwnerCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

}
