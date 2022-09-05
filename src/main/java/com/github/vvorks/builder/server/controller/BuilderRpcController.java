/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcController;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcMethod;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcParam;
import com.github.vvorks.builder.server.domain.BuilderContent;
import com.github.vvorks.builder.server.domain.BuilderSubject;
import com.github.vvorks.builder.server.domain.BuilderSummary;
import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.ClassSubject;
import com.github.vvorks.builder.server.domain.ClassSummary;
import com.github.vvorks.builder.server.domain.ClassI18nContent;
import com.github.vvorks.builder.server.domain.ClassI18nSubject;
import com.github.vvorks.builder.server.domain.ClassI18nSummary;
import com.github.vvorks.builder.server.domain.EnumContent;
import com.github.vvorks.builder.server.domain.EnumSubject;
import com.github.vvorks.builder.server.domain.EnumSummary;
import com.github.vvorks.builder.server.domain.EnumI18nContent;
import com.github.vvorks.builder.server.domain.EnumI18nSubject;
import com.github.vvorks.builder.server.domain.EnumI18nSummary;
import com.github.vvorks.builder.server.domain.EnumValueContent;
import com.github.vvorks.builder.server.domain.EnumValueSubject;
import com.github.vvorks.builder.server.domain.EnumValueSummary;
import com.github.vvorks.builder.server.domain.EnumValueI18nContent;
import com.github.vvorks.builder.server.domain.EnumValueI18nSubject;
import com.github.vvorks.builder.server.domain.EnumValueI18nSummary;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.server.domain.FieldSubject;
import com.github.vvorks.builder.server.domain.FieldSummary;
import com.github.vvorks.builder.server.domain.FieldI18nContent;
import com.github.vvorks.builder.server.domain.FieldI18nSubject;
import com.github.vvorks.builder.server.domain.FieldI18nSummary;
import com.github.vvorks.builder.server.domain.LayoutContent;
import com.github.vvorks.builder.server.domain.LayoutSubject;
import com.github.vvorks.builder.server.domain.LayoutSummary;
import com.github.vvorks.builder.server.domain.LocaleContent;
import com.github.vvorks.builder.server.domain.LocaleSubject;
import com.github.vvorks.builder.server.domain.LocaleSummary;
import com.github.vvorks.builder.server.domain.MessageContent;
import com.github.vvorks.builder.server.domain.MessageSubject;
import com.github.vvorks.builder.server.domain.MessageSummary;
import com.github.vvorks.builder.server.domain.MessageI18nContent;
import com.github.vvorks.builder.server.domain.MessageI18nSubject;
import com.github.vvorks.builder.server.domain.MessageI18nSummary;
import com.github.vvorks.builder.server.domain.PageContent;
import com.github.vvorks.builder.server.domain.PageSubject;
import com.github.vvorks.builder.server.domain.PageSummary;
import com.github.vvorks.builder.server.domain.PageSetContent;
import com.github.vvorks.builder.server.domain.PageSetSubject;
import com.github.vvorks.builder.server.domain.PageSetSummary;
import com.github.vvorks.builder.server.domain.ProjectContent;
import com.github.vvorks.builder.server.domain.ProjectSubject;
import com.github.vvorks.builder.server.domain.ProjectSummary;
import com.github.vvorks.builder.server.domain.ProjectI18nContent;
import com.github.vvorks.builder.server.domain.ProjectI18nSubject;
import com.github.vvorks.builder.server.domain.ProjectI18nSummary;
import com.github.vvorks.builder.server.domain.QueryContent;
import com.github.vvorks.builder.server.domain.QuerySubject;
import com.github.vvorks.builder.server.domain.QuerySummary;
import com.github.vvorks.builder.server.domain.StyleContent;
import com.github.vvorks.builder.server.domain.StyleSubject;
import com.github.vvorks.builder.server.domain.StyleSummary;
import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.DataTypeSubject;
import com.github.vvorks.builder.server.domain.DataTypeSummary;
import com.github.vvorks.builder.server.domain.LayoutType;
import com.github.vvorks.builder.server.domain.LayoutTypeSubject;
import com.github.vvorks.builder.server.domain.LayoutTypeSummary;
import com.github.vvorks.builder.server.domain.StyleCondition;
import com.github.vvorks.builder.server.domain.StyleConditionSubject;
import com.github.vvorks.builder.server.domain.StyleConditionSummary;
import com.github.vvorks.builder.server.domain.Subject;
import com.github.vvorks.builder.server.mapper.Mappers;
import com.github.vvorks.builder.shared.common.lang.Strings;

/**
 * ビルダープロジェクトの Json-Rpc (on Websocket) API
 */
@JsonRpcController("/builder")
public class BuilderRpcController {

	@Autowired
	private Mappers mappers;

	/**
	 * プロジェクトを挿入する
	 *
	 * @param content 挿入するプロジェクト
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean insertProject(ProjectContent content) {
		return mappers.getProjectMapper().insert(content);
	}

	/**
	 * プロジェクトを更新する
	 *
	 * @param content 更新するプロジェクト
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateProject(ProjectContent content) {
		return mappers.getProjectMapper().update(content);
	}

	/**
	 * プロジェクトを削除する
	 *
	 * @param content 削除するプロジェクト
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteProject(ProjectContent content) {
		return mappers.getProjectMapper().delete(content);
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
		return mappers.getProjectMapper().get(
				projectId
				);
	}

	/**
	 * プロジェクトのトピックパスを取得する
	 *
	 * @param content 取得対象プロジェクト
	 * @return Subjectリスト
	 */
	@JsonRpcMethod
	public List<Subject> listProjectTopicPath(ProjectContent content) {
		return mappers.getProjectMapper().listTopicPath(content);
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
		ProjectSummary<ProjectContent> summary = mappers.getProjectMapper().listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectContent> contents = mappers.getProjectMapper().listContent(offset, limit);
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
		ProjectSummary<ProjectContent> summary = mappers.getProjectMapper().listSummaryIfNameIs(
				name
				);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectContent> contents = mappers.getProjectMapper().listContentIfNameIs(
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
		ClassSummary<ClassContent> summary = mappers.getProjectMapper().listClassesSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassContent> contents =
				mappers.getProjectMapper().listClassesContent(content, offset, limit);
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
		ClassSummary<ClassContent> summary = mappers.getProjectMapper().listClassesSummaryIfNameIs(
				content,
				name
				);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassContent> contents = mappers.getProjectMapper().listClassesContentIfNameIs(
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
		EnumSummary<EnumContent> summary = mappers.getProjectMapper().listEnumsSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumContent> contents =
				mappers.getProjectMapper().listEnumsContent(content, offset, limit);
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
		EnumSummary<EnumContent> summary = mappers.getProjectMapper().listEnumsSummaryIfNameIs(
				content,
				name
				);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumContent> contents = mappers.getProjectMapper().listEnumsContentIfNameIs(
				content,
				name,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * メッセージ一覧情報を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return メッセージ一覧情報
	 */
	@JsonRpcMethod
	public MessageSummary<MessageContent> listProjectMessages(
		@JsonRpcParam("content") ProjectContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		MessageSummary<MessageContent> summary = mappers.getProjectMapper().listMessagesSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<MessageContent> contents =
				mappers.getProjectMapper().listMessagesContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * スタイル一覧情報を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return スタイル一覧情報
	 */
	@JsonRpcMethod
	public StyleSummary<StyleContent> listProjectStyles(
		@JsonRpcParam("content") ProjectContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		StyleSummary<StyleContent> summary = mappers.getProjectMapper().listStylesSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<StyleContent> contents =
				mappers.getProjectMapper().listStylesContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * ページセット一覧情報を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return ページセット一覧情報
	 */
	@JsonRpcMethod
	public PageSetSummary<PageSetContent> listProjectPageSets(
		@JsonRpcParam("content") ProjectContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		PageSetSummary<PageSetContent> summary = mappers.getProjectMapper().listPageSetsSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<PageSetContent> contents =
				mappers.getProjectMapper().listPageSetsContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * ロケール一覧情報を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return ロケール一覧情報
	 */
	@JsonRpcMethod
	public LocaleSummary<LocaleContent> listProjectLocales(
		@JsonRpcParam("content") ProjectContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		LocaleSummary<LocaleContent> summary = mappers.getProjectMapper().listLocalesSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LocaleContent> contents =
				mappers.getProjectMapper().listLocalesContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * I18n一覧情報を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return I18n一覧情報
	 */
	@JsonRpcMethod
	public ProjectI18nSummary<ProjectI18nContent> listProjectI18ns(
		@JsonRpcParam("content") ProjectContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ProjectI18nSummary<ProjectI18nContent> summary = mappers.getProjectMapper().listI18nsSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectI18nContent> contents =
				mappers.getProjectMapper().listI18nsContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * プロジェクト(I18n)を挿入する
	 *
	 * @param content 挿入するプロジェクト(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean insertProjectI18n(ProjectI18nContent content) {
		return mappers.getProjectI18nMapper().insert(content);
	}

	/**
	 * プロジェクト(I18n)を更新する
	 *
	 * @param content 更新するプロジェクト(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateProjectI18n(ProjectI18nContent content) {
		return mappers.getProjectI18nMapper().update(content);
	}

	/**
	 * プロジェクト(I18n)を削除する
	 *
	 * @param content 削除するプロジェクト(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteProjectI18n(ProjectI18nContent content) {
		return mappers.getProjectI18nMapper().delete(content);
	}

	/**
	 * プロジェクト(I18n)を取得する
	 *
	 * @param ownerProjectId 所属プロジェクトのプロジェクトID
	 * @param targetLocaleId 対象ロケールのロケールID
	 * @return 取得したプロジェクト(I18n)
	 */
	@JsonRpcMethod
	public ProjectI18nContent getProjectI18n(
		@JsonRpcParam("ownerProjectId") int ownerProjectId, 
		@JsonRpcParam("targetLocaleId") String targetLocaleId
	) {
		return mappers.getProjectI18nMapper().get(
				ownerProjectId, 
				targetLocaleId
				);
	}

	/**
	 * プロジェクト(I18n)のトピックパスを取得する
	 *
	 * @param content 取得対象プロジェクト(I18n)
	 * @return Subjectリスト
	 */
	@JsonRpcMethod
	public List<Subject> listProjectI18nTopicPath(ProjectI18nContent content) {
		return mappers.getProjectI18nMapper().listTopicPath(content);
	}

	/**
	 * 全てのプロジェクト(I18n)情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return プロジェクト(I18n)情報
	 */
	@JsonRpcMethod
	public ProjectI18nSummary<ProjectI18nContent> listProjectI18n(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ProjectI18nSummary<ProjectI18nContent> summary = mappers.getProjectI18nMapper().listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectI18nContent> contents = mappers.getProjectI18nMapper().listContent(offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 所属プロジェクトを取得する
	 *
	 * @param content プロジェクト(I18n)
	 * @return 所属プロジェクト
	 */
	@JsonRpcMethod
	public ProjectContent getProjectI18nOwner(
		@JsonRpcParam("content") ProjectI18nContent content
	) {
		return mappers.getProjectI18nMapper().getOwner(content);
	}

	/**
	 * 所属プロジェクトの候補一覧を取得する
	 *
	 * @param content プロジェクト(I18n)
	 * @return 所属プロジェクトの候補一覧
	 */
	@JsonRpcMethod
	public ProjectSummary<ProjectSubject> listProjectI18nOwnerCandidate(
		@JsonRpcParam("content") ProjectI18nContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ProjectSummary<ProjectSubject> summary = mappers.getProjectI18nMapper().listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectSubject> contents = mappers.getProjectI18nMapper().listOwnerCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 対象ロケールを取得する
	 *
	 * @param content プロジェクト(I18n)
	 * @return 対象ロケール
	 */
	@JsonRpcMethod
	public LocaleContent getProjectI18nTarget(
		@JsonRpcParam("content") ProjectI18nContent content
	) {
		return mappers.getProjectI18nMapper().getTarget(content);
	}

	/**
	 * 対象ロケールの候補一覧を取得する
	 *
	 * @param content プロジェクト(I18n)
	 * @return 対象ロケールの候補一覧
	 */
	@JsonRpcMethod
	public LocaleSummary<LocaleSubject> listProjectI18nTargetCandidate(
		@JsonRpcParam("content") ProjectI18nContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		LocaleSummary<LocaleSubject> summary = mappers.getProjectI18nMapper().listTargetCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LocaleSubject> contents = mappers.getProjectI18nMapper().listTargetCandidateSubject(
				content, hint,
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
		return mappers.getClassMapper().insert(content);
	}

	/**
	 * クラスを更新する
	 *
	 * @param content 更新するクラス
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateClass(ClassContent content) {
		return mappers.getClassMapper().update(content);
	}

	/**
	 * クラスを削除する
	 *
	 * @param content 削除するクラス
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteClass(ClassContent content) {
		return mappers.getClassMapper().delete(content);
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
		return mappers.getClassMapper().get(
				classId
				);
	}

	/**
	 * クラスのトピックパスを取得する
	 *
	 * @param content 取得対象クラス
	 * @return Subjectリスト
	 */
	@JsonRpcMethod
	public List<Subject> listClassTopicPath(ClassContent content) {
		return mappers.getClassMapper().listTopicPath(content);
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
		ClassSummary<ClassContent> summary = mappers.getClassMapper().listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassContent> contents = mappers.getClassMapper().listContent(offset, limit);
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
		ClassSummary<ClassContent> summary = mappers.getClassMapper().listSummaryIfNameIs(
				name
				);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassContent> contents = mappers.getClassMapper().listContentIfNameIs(
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
		return mappers.getClassMapper().getOwner(content);
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
		ProjectSummary<ProjectSubject> summary = mappers.getClassMapper().listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectSubject> contents = mappers.getClassMapper().listOwnerCandidateSubject(
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
		FieldSummary<FieldContent> summary = mappers.getClassMapper().listFieldsSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<FieldContent> contents =
				mappers.getClassMapper().listFieldsContent(content, offset, limit);
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
		FieldSummary<FieldContent> summary = mappers.getClassMapper().listFieldsSummaryIfNameIs(
				content,
				name
				);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<FieldContent> contents = mappers.getClassMapper().listFieldsContentIfNameIs(
				content,
				name,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * クエリー一覧情報を取得する
	 *
	 * @param content クラス
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クエリー一覧情報
	 */
	@JsonRpcMethod
	public QuerySummary<QueryContent> listClassQueries(
		@JsonRpcParam("content") ClassContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		QuerySummary<QueryContent> summary = mappers.getClassMapper().listQueriesSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<QueryContent> contents =
				mappers.getClassMapper().listQueriesContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * I18n一覧情報を取得する
	 *
	 * @param content クラス
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return I18n一覧情報
	 */
	@JsonRpcMethod
	public ClassI18nSummary<ClassI18nContent> listClassI18ns(
		@JsonRpcParam("content") ClassContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ClassI18nSummary<ClassI18nContent> summary = mappers.getClassMapper().listI18nsSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassI18nContent> contents =
				mappers.getClassMapper().listI18nsContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * クラス(I18n)を挿入する
	 *
	 * @param content 挿入するクラス(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean insertClassI18n(ClassI18nContent content) {
		return mappers.getClassI18nMapper().insert(content);
	}

	/**
	 * クラス(I18n)を更新する
	 *
	 * @param content 更新するクラス(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateClassI18n(ClassI18nContent content) {
		return mappers.getClassI18nMapper().update(content);
	}

	/**
	 * クラス(I18n)を削除する
	 *
	 * @param content 削除するクラス(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteClassI18n(ClassI18nContent content) {
		return mappers.getClassI18nMapper().delete(content);
	}

	/**
	 * クラス(I18n)を取得する
	 *
	 * @param ownerClassId 所属クラスのクラスID
	 * @param targetLocaleId 対象ロケールのロケールID
	 * @return 取得したクラス(I18n)
	 */
	@JsonRpcMethod
	public ClassI18nContent getClassI18n(
		@JsonRpcParam("ownerClassId") int ownerClassId, 
		@JsonRpcParam("targetLocaleId") String targetLocaleId
	) {
		return mappers.getClassI18nMapper().get(
				ownerClassId, 
				targetLocaleId
				);
	}

	/**
	 * クラス(I18n)のトピックパスを取得する
	 *
	 * @param content 取得対象クラス(I18n)
	 * @return Subjectリスト
	 */
	@JsonRpcMethod
	public List<Subject> listClassI18nTopicPath(ClassI18nContent content) {
		return mappers.getClassI18nMapper().listTopicPath(content);
	}

	/**
	 * 全てのクラス(I18n)情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラス(I18n)情報
	 */
	@JsonRpcMethod
	public ClassI18nSummary<ClassI18nContent> listClassI18n(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ClassI18nSummary<ClassI18nContent> summary = mappers.getClassI18nMapper().listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassI18nContent> contents = mappers.getClassI18nMapper().listContent(offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 所属クラスを取得する
	 *
	 * @param content クラス(I18n)
	 * @return 所属クラス
	 */
	@JsonRpcMethod
	public ClassContent getClassI18nOwner(
		@JsonRpcParam("content") ClassI18nContent content
	) {
		return mappers.getClassI18nMapper().getOwner(content);
	}

	/**
	 * 所属クラスの候補一覧を取得する
	 *
	 * @param content クラス(I18n)
	 * @return 所属クラスの候補一覧
	 */
	@JsonRpcMethod
	public ClassSummary<ClassSubject> listClassI18nOwnerCandidate(
		@JsonRpcParam("content") ClassI18nContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ClassSummary<ClassSubject> summary = mappers.getClassI18nMapper().listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassSubject> contents = mappers.getClassI18nMapper().listOwnerCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 対象ロケールを取得する
	 *
	 * @param content クラス(I18n)
	 * @return 対象ロケール
	 */
	@JsonRpcMethod
	public LocaleContent getClassI18nTarget(
		@JsonRpcParam("content") ClassI18nContent content
	) {
		return mappers.getClassI18nMapper().getTarget(content);
	}

	/**
	 * 対象ロケールの候補一覧を取得する
	 *
	 * @param content クラス(I18n)
	 * @return 対象ロケールの候補一覧
	 */
	@JsonRpcMethod
	public LocaleSummary<LocaleSubject> listClassI18nTargetCandidate(
		@JsonRpcParam("content") ClassI18nContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		LocaleSummary<LocaleSubject> summary = mappers.getClassI18nMapper().listTargetCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LocaleSubject> contents = mappers.getClassI18nMapper().listTargetCandidateSubject(
				content, hint,
				offset, limit);
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
		return mappers.getFieldMapper().insert(content);
	}

	/**
	 * フィールドを更新する
	 *
	 * @param content 更新するフィールド
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateField(FieldContent content) {
		return mappers.getFieldMapper().update(content);
	}

	/**
	 * フィールドを削除する
	 *
	 * @param content 削除するフィールド
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteField(FieldContent content) {
		return mappers.getFieldMapper().delete(content);
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
		return mappers.getFieldMapper().get(
				fieldId
				);
	}

	/**
	 * フィールドのトピックパスを取得する
	 *
	 * @param content 取得対象フィールド
	 * @return Subjectリスト
	 */
	@JsonRpcMethod
	public List<Subject> listFieldTopicPath(FieldContent content) {
		return mappers.getFieldMapper().listTopicPath(content);
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
		FieldSummary<FieldContent> summary = mappers.getFieldMapper().listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<FieldContent> contents = mappers.getFieldMapper().listContent(offset, limit);
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
		FieldSummary<FieldContent> summary = mappers.getFieldMapper().listSummaryIfNameIs(
				name
				);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<FieldContent> contents = mappers.getFieldMapper().listContentIfNameIs(
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
		return mappers.getFieldMapper().getOwner(content);
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
		ClassSummary<ClassSubject> summary = mappers.getFieldMapper().listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassSubject> contents = mappers.getFieldMapper().listOwnerCandidateSubject(
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
		return mappers.getFieldMapper().getCref(content);
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
		ClassSummary<ClassSubject> summary = mappers.getFieldMapper().listCrefCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassSubject> contents = mappers.getFieldMapper().listCrefCandidateSubject(
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
		return mappers.getFieldMapper().getEref(content);
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
		EnumSummary<EnumSubject> summary = mappers.getFieldMapper().listErefCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumSubject> contents = mappers.getFieldMapper().listErefCandidateSubject(
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
		return mappers.getFieldMapper().getFref(content);
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
		FieldSummary<FieldSubject> summary = mappers.getFieldMapper().listFrefCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<FieldSubject> contents = mappers.getFieldMapper().listFrefCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * フィールド型の候補一覧を取得する
	 *
	 * @param content フィールド
	 * @return フィールド型の候補一覧
	 */
	@JsonRpcMethod
	public DataTypeSummary<DataTypeSubject> listFieldTypeCandidate(
		@JsonRpcParam("content") FieldContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		int count;
		if (Strings.isEmpty(hint)) {
			count = DataType.values().length;
		} else {
			count = 0;
			for (DataType e : DataType.values()) {
				if (fuzzyMatch(e.get_title(), hint)) {
					count++;
				}
			}
		}
		offset = Math.min(Math.max(0, offset), count);
		DataTypeSummary<DataTypeSubject> summary = new DataTypeSummary<>();
		summary.setCount(count);
		summary.setFocus(0);
		summary.setOffset(offset);
		if (count == 0) {
			summary.setContents(Collections.emptyList());
		} else {
			List<DataTypeSubject> contents = new ArrayList<>();
			for (DataType e : DataType.values()) {
				if (Strings.isEmpty(hint) || fuzzyMatch(e.get_title(), hint)) {
					if (offset > 0) {
						offset--;
					} else if (limit > 0) {
						contents.add(new DataTypeSubject(e));
						limit--;
						if (limit == 0) {
							break;
						}
					}
				}
			}
			summary.setContents(contents);
		}
		return summary;
	}

	/**
	 * I18n一覧情報を取得する
	 *
	 * @param content フィールド
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return I18n一覧情報
	 */
	@JsonRpcMethod
	public FieldI18nSummary<FieldI18nContent> listFieldI18ns(
		@JsonRpcParam("content") FieldContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		FieldI18nSummary<FieldI18nContent> summary = mappers.getFieldMapper().listI18nsSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<FieldI18nContent> contents =
				mappers.getFieldMapper().listI18nsContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * フィールド(I18n)を挿入する
	 *
	 * @param content 挿入するフィールド(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean insertFieldI18n(FieldI18nContent content) {
		return mappers.getFieldI18nMapper().insert(content);
	}

	/**
	 * フィールド(I18n)を更新する
	 *
	 * @param content 更新するフィールド(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateFieldI18n(FieldI18nContent content) {
		return mappers.getFieldI18nMapper().update(content);
	}

	/**
	 * フィールド(I18n)を削除する
	 *
	 * @param content 削除するフィールド(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteFieldI18n(FieldI18nContent content) {
		return mappers.getFieldI18nMapper().delete(content);
	}

	/**
	 * フィールド(I18n)を取得する
	 *
	 * @param ownerFieldId 所属フィールドのフィールドID
	 * @param targetLocaleId 対象ロケールのロケールID
	 * @return 取得したフィールド(I18n)
	 */
	@JsonRpcMethod
	public FieldI18nContent getFieldI18n(
		@JsonRpcParam("ownerFieldId") int ownerFieldId, 
		@JsonRpcParam("targetLocaleId") String targetLocaleId
	) {
		return mappers.getFieldI18nMapper().get(
				ownerFieldId, 
				targetLocaleId
				);
	}

	/**
	 * フィールド(I18n)のトピックパスを取得する
	 *
	 * @param content 取得対象フィールド(I18n)
	 * @return Subjectリスト
	 */
	@JsonRpcMethod
	public List<Subject> listFieldI18nTopicPath(FieldI18nContent content) {
		return mappers.getFieldI18nMapper().listTopicPath(content);
	}

	/**
	 * 全てのフィールド(I18n)情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールド(I18n)情報
	 */
	@JsonRpcMethod
	public FieldI18nSummary<FieldI18nContent> listFieldI18n(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		FieldI18nSummary<FieldI18nContent> summary = mappers.getFieldI18nMapper().listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<FieldI18nContent> contents = mappers.getFieldI18nMapper().listContent(offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 所属フィールドを取得する
	 *
	 * @param content フィールド(I18n)
	 * @return 所属フィールド
	 */
	@JsonRpcMethod
	public FieldContent getFieldI18nOwner(
		@JsonRpcParam("content") FieldI18nContent content
	) {
		return mappers.getFieldI18nMapper().getOwner(content);
	}

	/**
	 * 所属フィールドの候補一覧を取得する
	 *
	 * @param content フィールド(I18n)
	 * @return 所属フィールドの候補一覧
	 */
	@JsonRpcMethod
	public FieldSummary<FieldSubject> listFieldI18nOwnerCandidate(
		@JsonRpcParam("content") FieldI18nContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		FieldSummary<FieldSubject> summary = mappers.getFieldI18nMapper().listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<FieldSubject> contents = mappers.getFieldI18nMapper().listOwnerCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 対象ロケールを取得する
	 *
	 * @param content フィールド(I18n)
	 * @return 対象ロケール
	 */
	@JsonRpcMethod
	public LocaleContent getFieldI18nTarget(
		@JsonRpcParam("content") FieldI18nContent content
	) {
		return mappers.getFieldI18nMapper().getTarget(content);
	}

	/**
	 * 対象ロケールの候補一覧を取得する
	 *
	 * @param content フィールド(I18n)
	 * @return 対象ロケールの候補一覧
	 */
	@JsonRpcMethod
	public LocaleSummary<LocaleSubject> listFieldI18nTargetCandidate(
		@JsonRpcParam("content") FieldI18nContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		LocaleSummary<LocaleSubject> summary = mappers.getFieldI18nMapper().listTargetCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LocaleSubject> contents = mappers.getFieldI18nMapper().listTargetCandidateSubject(
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
		return mappers.getQueryMapper().insert(content);
	}

	/**
	 * クエリーを更新する
	 *
	 * @param content 更新するクエリー
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateQuery(QueryContent content) {
		return mappers.getQueryMapper().update(content);
	}

	/**
	 * クエリーを削除する
	 *
	 * @param content 削除するクエリー
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteQuery(QueryContent content) {
		return mappers.getQueryMapper().delete(content);
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
		return mappers.getQueryMapper().get(
				queryId
				);
	}

	/**
	 * クエリーのトピックパスを取得する
	 *
	 * @param content 取得対象クエリー
	 * @return Subjectリスト
	 */
	@JsonRpcMethod
	public List<Subject> listQueryTopicPath(QueryContent content) {
		return mappers.getQueryMapper().listTopicPath(content);
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
		QuerySummary<QueryContent> summary = mappers.getQueryMapper().listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<QueryContent> contents = mappers.getQueryMapper().listContent(offset, limit);
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
		return mappers.getQueryMapper().getOwner(content);
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
		ClassSummary<ClassSubject> summary = mappers.getQueryMapper().listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassSubject> contents = mappers.getQueryMapper().listOwnerCandidateSubject(
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
		return mappers.getEnumMapper().insert(content);
	}

	/**
	 * 列挙を更新する
	 *
	 * @param content 更新する列挙
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateEnum(EnumContent content) {
		return mappers.getEnumMapper().update(content);
	}

	/**
	 * 列挙を削除する
	 *
	 * @param content 削除する列挙
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteEnum(EnumContent content) {
		return mappers.getEnumMapper().delete(content);
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
		return mappers.getEnumMapper().get(
				enumId
				);
	}

	/**
	 * 列挙のトピックパスを取得する
	 *
	 * @param content 取得対象列挙
	 * @return Subjectリスト
	 */
	@JsonRpcMethod
	public List<Subject> listEnumTopicPath(EnumContent content) {
		return mappers.getEnumMapper().listTopicPath(content);
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
		EnumSummary<EnumContent> summary = mappers.getEnumMapper().listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumContent> contents = mappers.getEnumMapper().listContent(offset, limit);
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
		EnumSummary<EnumContent> summary = mappers.getEnumMapper().listSummaryIfNameIs(
				name
				);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumContent> contents = mappers.getEnumMapper().listContentIfNameIs(
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
		return mappers.getEnumMapper().getOwner(content);
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
		ProjectSummary<ProjectSubject> summary = mappers.getEnumMapper().listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectSubject> contents = mappers.getEnumMapper().listOwnerCandidateSubject(
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
		EnumValueSummary<EnumValueContent> summary = mappers.getEnumMapper().listValuesSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumValueContent> contents =
				mappers.getEnumMapper().listValuesContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * I18n一覧情報を取得する
	 *
	 * @param content 列挙
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return I18n一覧情報
	 */
	@JsonRpcMethod
	public EnumI18nSummary<EnumI18nContent> listEnumI18ns(
		@JsonRpcParam("content") EnumContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		EnumI18nSummary<EnumI18nContent> summary = mappers.getEnumMapper().listI18nsSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumI18nContent> contents =
				mappers.getEnumMapper().listI18nsContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 列挙(I18n)を挿入する
	 *
	 * @param content 挿入する列挙(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean insertEnumI18n(EnumI18nContent content) {
		return mappers.getEnumI18nMapper().insert(content);
	}

	/**
	 * 列挙(I18n)を更新する
	 *
	 * @param content 更新する列挙(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateEnumI18n(EnumI18nContent content) {
		return mappers.getEnumI18nMapper().update(content);
	}

	/**
	 * 列挙(I18n)を削除する
	 *
	 * @param content 削除する列挙(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteEnumI18n(EnumI18nContent content) {
		return mappers.getEnumI18nMapper().delete(content);
	}

	/**
	 * 列挙(I18n)を取得する
	 *
	 * @param ownerEnumId 所属列挙の列挙ID
	 * @param targetLocaleId 対象ロケールのロケールID
	 * @return 取得した列挙(I18n)
	 */
	@JsonRpcMethod
	public EnumI18nContent getEnumI18n(
		@JsonRpcParam("ownerEnumId") int ownerEnumId, 
		@JsonRpcParam("targetLocaleId") String targetLocaleId
	) {
		return mappers.getEnumI18nMapper().get(
				ownerEnumId, 
				targetLocaleId
				);
	}

	/**
	 * 列挙(I18n)のトピックパスを取得する
	 *
	 * @param content 取得対象列挙(I18n)
	 * @return Subjectリスト
	 */
	@JsonRpcMethod
	public List<Subject> listEnumI18nTopicPath(EnumI18nContent content) {
		return mappers.getEnumI18nMapper().listTopicPath(content);
	}

	/**
	 * 全ての列挙(I18n)情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙(I18n)情報
	 */
	@JsonRpcMethod
	public EnumI18nSummary<EnumI18nContent> listEnumI18n(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		EnumI18nSummary<EnumI18nContent> summary = mappers.getEnumI18nMapper().listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumI18nContent> contents = mappers.getEnumI18nMapper().listContent(offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 所属列挙を取得する
	 *
	 * @param content 列挙(I18n)
	 * @return 所属列挙
	 */
	@JsonRpcMethod
	public EnumContent getEnumI18nOwner(
		@JsonRpcParam("content") EnumI18nContent content
	) {
		return mappers.getEnumI18nMapper().getOwner(content);
	}

	/**
	 * 所属列挙の候補一覧を取得する
	 *
	 * @param content 列挙(I18n)
	 * @return 所属列挙の候補一覧
	 */
	@JsonRpcMethod
	public EnumSummary<EnumSubject> listEnumI18nOwnerCandidate(
		@JsonRpcParam("content") EnumI18nContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		EnumSummary<EnumSubject> summary = mappers.getEnumI18nMapper().listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumSubject> contents = mappers.getEnumI18nMapper().listOwnerCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 対象ロケールを取得する
	 *
	 * @param content 列挙(I18n)
	 * @return 対象ロケール
	 */
	@JsonRpcMethod
	public LocaleContent getEnumI18nTarget(
		@JsonRpcParam("content") EnumI18nContent content
	) {
		return mappers.getEnumI18nMapper().getTarget(content);
	}

	/**
	 * 対象ロケールの候補一覧を取得する
	 *
	 * @param content 列挙(I18n)
	 * @return 対象ロケールの候補一覧
	 */
	@JsonRpcMethod
	public LocaleSummary<LocaleSubject> listEnumI18nTargetCandidate(
		@JsonRpcParam("content") EnumI18nContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		LocaleSummary<LocaleSubject> summary = mappers.getEnumI18nMapper().listTargetCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LocaleSubject> contents = mappers.getEnumI18nMapper().listTargetCandidateSubject(
				content, hint,
				offset, limit);
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
		return mappers.getEnumValueMapper().insert(content);
	}

	/**
	 * 列挙値を更新する
	 *
	 * @param content 更新する列挙値
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateEnumValue(EnumValueContent content) {
		return mappers.getEnumValueMapper().update(content);
	}

	/**
	 * 列挙値を削除する
	 *
	 * @param content 削除する列挙値
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteEnumValue(EnumValueContent content) {
		return mappers.getEnumValueMapper().delete(content);
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
		return mappers.getEnumValueMapper().get(
				ownerEnumId, 
				valueId
				);
	}

	/**
	 * 列挙値のトピックパスを取得する
	 *
	 * @param content 取得対象列挙値
	 * @return Subjectリスト
	 */
	@JsonRpcMethod
	public List<Subject> listEnumValueTopicPath(EnumValueContent content) {
		return mappers.getEnumValueMapper().listTopicPath(content);
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
		EnumValueSummary<EnumValueContent> summary = mappers.getEnumValueMapper().listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumValueContent> contents = mappers.getEnumValueMapper().listContent(offset, limit);
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
		return mappers.getEnumValueMapper().getOwner(content);
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
		EnumSummary<EnumSubject> summary = mappers.getEnumValueMapper().listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumSubject> contents = mappers.getEnumValueMapper().listOwnerCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * I18n一覧情報を取得する
	 *
	 * @param content 列挙値
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return I18n一覧情報
	 */
	@JsonRpcMethod
	public EnumValueI18nSummary<EnumValueI18nContent> listEnumValueI18ns(
		@JsonRpcParam("content") EnumValueContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		EnumValueI18nSummary<EnumValueI18nContent> summary = mappers.getEnumValueMapper().listI18nsSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumValueI18nContent> contents =
				mappers.getEnumValueMapper().listI18nsContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 列挙値(I18n)を挿入する
	 *
	 * @param content 挿入する列挙値(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean insertEnumValueI18n(EnumValueI18nContent content) {
		return mappers.getEnumValueI18nMapper().insert(content);
	}

	/**
	 * 列挙値(I18n)を更新する
	 *
	 * @param content 更新する列挙値(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateEnumValueI18n(EnumValueI18nContent content) {
		return mappers.getEnumValueI18nMapper().update(content);
	}

	/**
	 * 列挙値(I18n)を削除する
	 *
	 * @param content 削除する列挙値(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteEnumValueI18n(EnumValueI18nContent content) {
		return mappers.getEnumValueI18nMapper().delete(content);
	}

	/**
	 * 列挙値(I18n)を取得する
	 *
	 * @param ownerOwnerEnumId 所属列挙値の所属列挙の列挙ID
	 * @param ownerValueId 所属列挙値の列挙名
	 * @param targetLocaleId 対象ロケールのロケールID
	 * @return 取得した列挙値(I18n)
	 */
	@JsonRpcMethod
	public EnumValueI18nContent getEnumValueI18n(
		@JsonRpcParam("ownerOwnerEnumId") int ownerOwnerEnumId, 
		@JsonRpcParam("ownerValueId") String ownerValueId, 
		@JsonRpcParam("targetLocaleId") String targetLocaleId
	) {
		return mappers.getEnumValueI18nMapper().get(
				ownerOwnerEnumId, 
				ownerValueId, 
				targetLocaleId
				);
	}

	/**
	 * 列挙値(I18n)のトピックパスを取得する
	 *
	 * @param content 取得対象列挙値(I18n)
	 * @return Subjectリスト
	 */
	@JsonRpcMethod
	public List<Subject> listEnumValueI18nTopicPath(EnumValueI18nContent content) {
		return mappers.getEnumValueI18nMapper().listTopicPath(content);
	}

	/**
	 * 全ての列挙値(I18n)情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙値(I18n)情報
	 */
	@JsonRpcMethod
	public EnumValueI18nSummary<EnumValueI18nContent> listEnumValueI18n(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		EnumValueI18nSummary<EnumValueI18nContent> summary = mappers.getEnumValueI18nMapper().listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumValueI18nContent> contents = mappers.getEnumValueI18nMapper().listContent(offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 所属列挙値を取得する
	 *
	 * @param content 列挙値(I18n)
	 * @return 所属列挙値
	 */
	@JsonRpcMethod
	public EnumValueContent getEnumValueI18nOwner(
		@JsonRpcParam("content") EnumValueI18nContent content
	) {
		return mappers.getEnumValueI18nMapper().getOwner(content);
	}

	/**
	 * 所属列挙値の候補一覧を取得する
	 *
	 * @param content 列挙値(I18n)
	 * @return 所属列挙値の候補一覧
	 */
	@JsonRpcMethod
	public EnumValueSummary<EnumValueSubject> listEnumValueI18nOwnerCandidate(
		@JsonRpcParam("content") EnumValueI18nContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		EnumValueSummary<EnumValueSubject> summary = mappers.getEnumValueI18nMapper().listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumValueSubject> contents = mappers.getEnumValueI18nMapper().listOwnerCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 対象ロケールを取得する
	 *
	 * @param content 列挙値(I18n)
	 * @return 対象ロケール
	 */
	@JsonRpcMethod
	public LocaleContent getEnumValueI18nTarget(
		@JsonRpcParam("content") EnumValueI18nContent content
	) {
		return mappers.getEnumValueI18nMapper().getTarget(content);
	}

	/**
	 * 対象ロケールの候補一覧を取得する
	 *
	 * @param content 列挙値(I18n)
	 * @return 対象ロケールの候補一覧
	 */
	@JsonRpcMethod
	public LocaleSummary<LocaleSubject> listEnumValueI18nTargetCandidate(
		@JsonRpcParam("content") EnumValueI18nContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		LocaleSummary<LocaleSubject> summary = mappers.getEnumValueI18nMapper().listTargetCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LocaleSubject> contents = mappers.getEnumValueI18nMapper().listTargetCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * メッセージを挿入する
	 *
	 * @param content 挿入するメッセージ
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean insertMessage(MessageContent content) {
		return mappers.getMessageMapper().insert(content);
	}

	/**
	 * メッセージを更新する
	 *
	 * @param content 更新するメッセージ
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateMessage(MessageContent content) {
		return mappers.getMessageMapper().update(content);
	}

	/**
	 * メッセージを削除する
	 *
	 * @param content 削除するメッセージ
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteMessage(MessageContent content) {
		return mappers.getMessageMapper().delete(content);
	}

	/**
	 * メッセージを取得する
	 *
	 * @param messageId メッセージID
	 * @return 取得したメッセージ
	 */
	@JsonRpcMethod
	public MessageContent getMessage(
		@JsonRpcParam("messageId") int messageId
	) {
		return mappers.getMessageMapper().get(
				messageId
				);
	}

	/**
	 * メッセージのトピックパスを取得する
	 *
	 * @param content 取得対象メッセージ
	 * @return Subjectリスト
	 */
	@JsonRpcMethod
	public List<Subject> listMessageTopicPath(MessageContent content) {
		return mappers.getMessageMapper().listTopicPath(content);
	}

	/**
	 * 全てのメッセージ情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return メッセージ情報
	 */
	@JsonRpcMethod
	public MessageSummary<MessageContent> listMessage(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		MessageSummary<MessageContent> summary = mappers.getMessageMapper().listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<MessageContent> contents = mappers.getMessageMapper().listContent(offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 所属プロジェクトを取得する
	 *
	 * @param content メッセージ
	 * @return 所属プロジェクト
	 */
	@JsonRpcMethod
	public ProjectContent getMessageOwner(
		@JsonRpcParam("content") MessageContent content
	) {
		return mappers.getMessageMapper().getOwner(content);
	}

	/**
	 * 所属プロジェクトの候補一覧を取得する
	 *
	 * @param content メッセージ
	 * @return 所属プロジェクトの候補一覧
	 */
	@JsonRpcMethod
	public ProjectSummary<ProjectSubject> listMessageOwnerCandidate(
		@JsonRpcParam("content") MessageContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ProjectSummary<ProjectSubject> summary = mappers.getMessageMapper().listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectSubject> contents = mappers.getMessageMapper().listOwnerCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * I18n一覧情報を取得する
	 *
	 * @param content メッセージ
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return I18n一覧情報
	 */
	@JsonRpcMethod
	public MessageI18nSummary<MessageI18nContent> listMessageI18ns(
		@JsonRpcParam("content") MessageContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		MessageI18nSummary<MessageI18nContent> summary = mappers.getMessageMapper().listI18nsSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<MessageI18nContent> contents =
				mappers.getMessageMapper().listI18nsContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * メッセージ(I18n)を挿入する
	 *
	 * @param content 挿入するメッセージ(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean insertMessageI18n(MessageI18nContent content) {
		return mappers.getMessageI18nMapper().insert(content);
	}

	/**
	 * メッセージ(I18n)を更新する
	 *
	 * @param content 更新するメッセージ(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateMessageI18n(MessageI18nContent content) {
		return mappers.getMessageI18nMapper().update(content);
	}

	/**
	 * メッセージ(I18n)を削除する
	 *
	 * @param content 削除するメッセージ(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteMessageI18n(MessageI18nContent content) {
		return mappers.getMessageI18nMapper().delete(content);
	}

	/**
	 * メッセージ(I18n)を取得する
	 *
	 * @param ownerMessageId 所属列挙値のメッセージID
	 * @param targetLocaleId 対象ロケールのロケールID
	 * @return 取得したメッセージ(I18n)
	 */
	@JsonRpcMethod
	public MessageI18nContent getMessageI18n(
		@JsonRpcParam("ownerMessageId") int ownerMessageId, 
		@JsonRpcParam("targetLocaleId") String targetLocaleId
	) {
		return mappers.getMessageI18nMapper().get(
				ownerMessageId, 
				targetLocaleId
				);
	}

	/**
	 * メッセージ(I18n)のトピックパスを取得する
	 *
	 * @param content 取得対象メッセージ(I18n)
	 * @return Subjectリスト
	 */
	@JsonRpcMethod
	public List<Subject> listMessageI18nTopicPath(MessageI18nContent content) {
		return mappers.getMessageI18nMapper().listTopicPath(content);
	}

	/**
	 * 全てのメッセージ(I18n)情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return メッセージ(I18n)情報
	 */
	@JsonRpcMethod
	public MessageI18nSummary<MessageI18nContent> listMessageI18n(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		MessageI18nSummary<MessageI18nContent> summary = mappers.getMessageI18nMapper().listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<MessageI18nContent> contents = mappers.getMessageI18nMapper().listContent(offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 所属列挙値を取得する
	 *
	 * @param content メッセージ(I18n)
	 * @return 所属列挙値
	 */
	@JsonRpcMethod
	public MessageContent getMessageI18nOwner(
		@JsonRpcParam("content") MessageI18nContent content
	) {
		return mappers.getMessageI18nMapper().getOwner(content);
	}

	/**
	 * 所属列挙値の候補一覧を取得する
	 *
	 * @param content メッセージ(I18n)
	 * @return 所属列挙値の候補一覧
	 */
	@JsonRpcMethod
	public MessageSummary<MessageSubject> listMessageI18nOwnerCandidate(
		@JsonRpcParam("content") MessageI18nContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		MessageSummary<MessageSubject> summary = mappers.getMessageI18nMapper().listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<MessageSubject> contents = mappers.getMessageI18nMapper().listOwnerCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 対象ロケールを取得する
	 *
	 * @param content メッセージ(I18n)
	 * @return 対象ロケール
	 */
	@JsonRpcMethod
	public LocaleContent getMessageI18nTarget(
		@JsonRpcParam("content") MessageI18nContent content
	) {
		return mappers.getMessageI18nMapper().getTarget(content);
	}

	/**
	 * 対象ロケールの候補一覧を取得する
	 *
	 * @param content メッセージ(I18n)
	 * @return 対象ロケールの候補一覧
	 */
	@JsonRpcMethod
	public LocaleSummary<LocaleSubject> listMessageI18nTargetCandidate(
		@JsonRpcParam("content") MessageI18nContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		LocaleSummary<LocaleSubject> summary = mappers.getMessageI18nMapper().listTargetCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LocaleSubject> contents = mappers.getMessageI18nMapper().listTargetCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * スタイルを挿入する
	 *
	 * @param content 挿入するスタイル
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean insertStyle(StyleContent content) {
		return mappers.getStyleMapper().insert(content);
	}

	/**
	 * スタイルを更新する
	 *
	 * @param content 更新するスタイル
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateStyle(StyleContent content) {
		return mappers.getStyleMapper().update(content);
	}

	/**
	 * スタイルを削除する
	 *
	 * @param content 削除するスタイル
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteStyle(StyleContent content) {
		return mappers.getStyleMapper().delete(content);
	}

	/**
	 * スタイルを取得する
	 *
	 * @param styleId スタイルID
	 * @return 取得したスタイル
	 */
	@JsonRpcMethod
	public StyleContent getStyle(
		@JsonRpcParam("styleId") int styleId
	) {
		return mappers.getStyleMapper().get(
				styleId
				);
	}

	/**
	 * スタイルのトピックパスを取得する
	 *
	 * @param content 取得対象スタイル
	 * @return Subjectリスト
	 */
	@JsonRpcMethod
	public List<Subject> listStyleTopicPath(StyleContent content) {
		return mappers.getStyleMapper().listTopicPath(content);
	}

	/**
	 * 全てのスタイル情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return スタイル情報
	 */
	@JsonRpcMethod
	public StyleSummary<StyleContent> listStyle(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		StyleSummary<StyleContent> summary = mappers.getStyleMapper().listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<StyleContent> contents = mappers.getStyleMapper().listContent(offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 所属プロジェクトを取得する
	 *
	 * @param content スタイル
	 * @return 所属プロジェクト
	 */
	@JsonRpcMethod
	public ProjectContent getStyleOwner(
		@JsonRpcParam("content") StyleContent content
	) {
		return mappers.getStyleMapper().getOwner(content);
	}

	/**
	 * 所属プロジェクトの候補一覧を取得する
	 *
	 * @param content スタイル
	 * @return 所属プロジェクトの候補一覧
	 */
	@JsonRpcMethod
	public ProjectSummary<ProjectSubject> listStyleOwnerCandidate(
		@JsonRpcParam("content") StyleContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ProjectSummary<ProjectSubject> summary = mappers.getStyleMapper().listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectSubject> contents = mappers.getStyleMapper().listOwnerCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 基底スタイルを取得する
	 *
	 * @param content スタイル
	 * @return 基底スタイル
	 */
	@JsonRpcMethod
	public StyleContent getStyleParent(
		@JsonRpcParam("content") StyleContent content
	) {
		return mappers.getStyleMapper().getParent(content);
	}

	/**
	 * 基底スタイルの候補一覧を取得する
	 *
	 * @param content スタイル
	 * @return 基底スタイルの候補一覧
	 */
	@JsonRpcMethod
	public StyleSummary<StyleSubject> listStyleParentCandidate(
		@JsonRpcParam("content") StyleContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		StyleSummary<StyleSubject> summary = mappers.getStyleMapper().listParentCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<StyleSubject> contents = mappers.getStyleMapper().listParentCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 適用条件の候補一覧を取得する
	 *
	 * @param content スタイル
	 * @return 適用条件の候補一覧
	 */
	@JsonRpcMethod
	public StyleConditionSummary<StyleConditionSubject> listStyleCondCandidate(
		@JsonRpcParam("content") StyleContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		int count;
		if (Strings.isEmpty(hint)) {
			count = StyleCondition.values().length;
		} else {
			count = 0;
			for (StyleCondition e : StyleCondition.values()) {
				if (fuzzyMatch(e.get_title(), hint)) {
					count++;
				}
			}
		}
		offset = Math.min(Math.max(0, offset), count);
		StyleConditionSummary<StyleConditionSubject> summary = new StyleConditionSummary<>();
		summary.setCount(count);
		summary.setFocus(0);
		summary.setOffset(offset);
		if (count == 0) {
			summary.setContents(Collections.emptyList());
		} else {
			List<StyleConditionSubject> contents = new ArrayList<>();
			for (StyleCondition e : StyleCondition.values()) {
				if (Strings.isEmpty(hint) || fuzzyMatch(e.get_title(), hint)) {
					if (offset > 0) {
						offset--;
					} else if (limit > 0) {
						contents.add(new StyleConditionSubject(e));
						limit--;
						if (limit == 0) {
							break;
						}
					}
				}
			}
			summary.setContents(contents);
		}
		return summary;
	}

	/**
	 * 派生スタイル情報を取得する
	 *
	 * @param content スタイル
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 派生スタイル情報
	 */
	@JsonRpcMethod
	public StyleSummary<StyleContent> listStyleChildren(
		@JsonRpcParam("content") StyleContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		StyleSummary<StyleContent> summary = mappers.getStyleMapper().listChildrenSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<StyleContent> contents =
				mappers.getStyleMapper().listChildrenContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * ページセットを挿入する
	 *
	 * @param content 挿入するページセット
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean insertPageSet(PageSetContent content) {
		return mappers.getPageSetMapper().insert(content);
	}

	/**
	 * ページセットを更新する
	 *
	 * @param content 更新するページセット
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updatePageSet(PageSetContent content) {
		return mappers.getPageSetMapper().update(content);
	}

	/**
	 * ページセットを削除する
	 *
	 * @param content 削除するページセット
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deletePageSet(PageSetContent content) {
		return mappers.getPageSetMapper().delete(content);
	}

	/**
	 * ページセットを取得する
	 *
	 * @param pageSetId ページセットId
	 * @return 取得したページセット
	 */
	@JsonRpcMethod
	public PageSetContent getPageSet(
		@JsonRpcParam("pageSetId") int pageSetId
	) {
		return mappers.getPageSetMapper().get(
				pageSetId
				);
	}

	/**
	 * ページセットのトピックパスを取得する
	 *
	 * @param content 取得対象ページセット
	 * @return Subjectリスト
	 */
	@JsonRpcMethod
	public List<Subject> listPageSetTopicPath(PageSetContent content) {
		return mappers.getPageSetMapper().listTopicPath(content);
	}

	/**
	 * 全てのページセット情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return ページセット情報
	 */
	@JsonRpcMethod
	public PageSetSummary<PageSetContent> listPageSet(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		PageSetSummary<PageSetContent> summary = mappers.getPageSetMapper().listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<PageSetContent> contents = mappers.getPageSetMapper().listContent(offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 所属プロジェクトを取得する
	 *
	 * @param content ページセット
	 * @return 所属プロジェクト
	 */
	@JsonRpcMethod
	public ProjectContent getPageSetOwner(
		@JsonRpcParam("content") PageSetContent content
	) {
		return mappers.getPageSetMapper().getOwner(content);
	}

	/**
	 * 所属プロジェクトの候補一覧を取得する
	 *
	 * @param content ページセット
	 * @return 所属プロジェクトの候補一覧
	 */
	@JsonRpcMethod
	public ProjectSummary<ProjectSubject> listPageSetOwnerCandidate(
		@JsonRpcParam("content") PageSetContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ProjectSummary<ProjectSubject> summary = mappers.getPageSetMapper().listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectSubject> contents = mappers.getPageSetMapper().listOwnerCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * ページ一覧情報を取得する
	 *
	 * @param content ページセット
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return ページ一覧情報
	 */
	@JsonRpcMethod
	public PageSummary<PageContent> listPageSetPages(
		@JsonRpcParam("content") PageSetContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		PageSummary<PageContent> summary = mappers.getPageSetMapper().listPagesSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<PageContent> contents =
				mappers.getPageSetMapper().listPagesContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * ページを挿入する
	 *
	 * @param content 挿入するページ
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean insertPage(PageContent content) {
		return mappers.getPageMapper().insert(content);
	}

	/**
	 * ページを更新する
	 *
	 * @param content 更新するページ
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updatePage(PageContent content) {
		return mappers.getPageMapper().update(content);
	}

	/**
	 * ページを削除する
	 *
	 * @param content 削除するページ
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deletePage(PageContent content) {
		return mappers.getPageMapper().delete(content);
	}

	/**
	 * ページを取得する
	 *
	 * @param pageId ページId
	 * @return 取得したページ
	 */
	@JsonRpcMethod
	public PageContent getPage(
		@JsonRpcParam("pageId") int pageId
	) {
		return mappers.getPageMapper().get(
				pageId
				);
	}

	/**
	 * ページのトピックパスを取得する
	 *
	 * @param content 取得対象ページ
	 * @return Subjectリスト
	 */
	@JsonRpcMethod
	public List<Subject> listPageTopicPath(PageContent content) {
		return mappers.getPageMapper().listTopicPath(content);
	}

	/**
	 * 全てのページ情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return ページ情報
	 */
	@JsonRpcMethod
	public PageSummary<PageContent> listPage(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		PageSummary<PageContent> summary = mappers.getPageMapper().listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<PageContent> contents = mappers.getPageMapper().listContent(offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 所属ページセットを取得する
	 *
	 * @param content ページ
	 * @return 所属ページセット
	 */
	@JsonRpcMethod
	public PageSetContent getPageOwner(
		@JsonRpcParam("content") PageContent content
	) {
		return mappers.getPageMapper().getOwner(content);
	}

	/**
	 * 所属ページセットの候補一覧を取得する
	 *
	 * @param content ページ
	 * @return 所属ページセットの候補一覧
	 */
	@JsonRpcMethod
	public PageSetSummary<PageSetSubject> listPageOwnerCandidate(
		@JsonRpcParam("content") PageContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		PageSetSummary<PageSetSubject> summary = mappers.getPageMapper().listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<PageSetSubject> contents = mappers.getPageMapper().listOwnerCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * コンテキストクラスを取得する
	 *
	 * @param content ページ
	 * @return コンテキストクラス
	 */
	@JsonRpcMethod
	public ClassContent getPageContext(
		@JsonRpcParam("content") PageContent content
	) {
		return mappers.getPageMapper().getContext(content);
	}

	/**
	 * コンテキストクラスの候補一覧を取得する
	 *
	 * @param content ページ
	 * @return コンテキストクラスの候補一覧
	 */
	@JsonRpcMethod
	public ClassSummary<ClassSubject> listPageContextCandidate(
		@JsonRpcParam("content") PageContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ClassSummary<ClassSubject> summary = mappers.getPageMapper().listContextCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassSubject> contents = mappers.getPageMapper().listContextCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * レイアウト一覧情報を取得する
	 *
	 * @param content ページ
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return レイアウト一覧情報
	 */
	@JsonRpcMethod
	public LayoutSummary<LayoutContent> listPageLayouts(
		@JsonRpcParam("content") PageContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		LayoutSummary<LayoutContent> summary = mappers.getPageMapper().listLayoutsSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LayoutContent> contents =
				mappers.getPageMapper().listLayoutsContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * rootに合致するレイアウト一覧情報を取得する
	 *
	 * @param content ページ
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return レイアウト一覧情報
	 */
	@JsonRpcMethod
	public LayoutSummary<LayoutContent> listPageLayoutsIfRoot(
		@JsonRpcParam("content") PageContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		LayoutSummary<LayoutContent> summary = mappers.getPageMapper().listLayoutsSummaryIfRoot(
				content
				);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LayoutContent> contents = mappers.getPageMapper().listLayoutsContentIfRoot(
				content,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * レイアウトを挿入する
	 *
	 * @param content 挿入するレイアウト
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean insertLayout(LayoutContent content) {
		return mappers.getLayoutMapper().insert(content);
	}

	/**
	 * レイアウトを更新する
	 *
	 * @param content 更新するレイアウト
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateLayout(LayoutContent content) {
		return mappers.getLayoutMapper().update(content);
	}

	/**
	 * レイアウトを削除する
	 *
	 * @param content 削除するレイアウト
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteLayout(LayoutContent content) {
		return mappers.getLayoutMapper().delete(content);
	}

	/**
	 * レイアウトを取得する
	 *
	 * @param layoutId レイアウトId
	 * @return 取得したレイアウト
	 */
	@JsonRpcMethod
	public LayoutContent getLayout(
		@JsonRpcParam("layoutId") int layoutId
	) {
		return mappers.getLayoutMapper().get(
				layoutId
				);
	}

	/**
	 * レイアウトのトピックパスを取得する
	 *
	 * @param content 取得対象レイアウト
	 * @return Subjectリスト
	 */
	@JsonRpcMethod
	public List<Subject> listLayoutTopicPath(LayoutContent content) {
		return mappers.getLayoutMapper().listTopicPath(content);
	}

	/**
	 * 全てのレイアウト情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return レイアウト情報
	 */
	@JsonRpcMethod
	public LayoutSummary<LayoutContent> listLayout(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		LayoutSummary<LayoutContent> summary = mappers.getLayoutMapper().listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LayoutContent> contents = mappers.getLayoutMapper().listContent(offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * rootに合致するレイアウト情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return レイアウト情報
	 */
	@JsonRpcMethod
	public LayoutSummary<LayoutContent> listLayoutIfRoot(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		LayoutSummary<LayoutContent> summary = mappers.getLayoutMapper().listSummaryIfRoot(
				);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LayoutContent> contents = mappers.getLayoutMapper().listContentIfRoot(
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 所属ページを取得する
	 *
	 * @param content レイアウト
	 * @return 所属ページ
	 */
	@JsonRpcMethod
	public PageContent getLayoutOwner(
		@JsonRpcParam("content") LayoutContent content
	) {
		return mappers.getLayoutMapper().getOwner(content);
	}

	/**
	 * 所属ページの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @return 所属ページの候補一覧
	 */
	@JsonRpcMethod
	public PageSummary<PageSubject> listLayoutOwnerCandidate(
		@JsonRpcParam("content") LayoutContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		PageSummary<PageSubject> summary = mappers.getLayoutMapper().listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<PageSubject> contents = mappers.getLayoutMapper().listOwnerCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 親レイアウトを取得する
	 *
	 * @param content レイアウト
	 * @return 親レイアウト
	 */
	@JsonRpcMethod
	public LayoutContent getLayoutParent(
		@JsonRpcParam("content") LayoutContent content
	) {
		return mappers.getLayoutMapper().getParent(content);
	}

	/**
	 * 親レイアウトの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @return 親レイアウトの候補一覧
	 */
	@JsonRpcMethod
	public LayoutSummary<LayoutSubject> listLayoutParentCandidate(
		@JsonRpcParam("content") LayoutContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		LayoutSummary<LayoutSubject> summary = mappers.getLayoutMapper().listParentCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LayoutSubject> contents = mappers.getLayoutMapper().listParentCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 対象クラスを取得する
	 *
	 * @param content レイアウト
	 * @return 対象クラス
	 */
	@JsonRpcMethod
	public ClassContent getLayoutCref(
		@JsonRpcParam("content") LayoutContent content
	) {
		return mappers.getLayoutMapper().getCref(content);
	}

	/**
	 * 対象クラスの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @return 対象クラスの候補一覧
	 */
	@JsonRpcMethod
	public ClassSummary<ClassSubject> listLayoutCrefCandidate(
		@JsonRpcParam("content") LayoutContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ClassSummary<ClassSubject> summary = mappers.getLayoutMapper().listCrefCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassSubject> contents = mappers.getLayoutMapper().listCrefCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 対象列挙を取得する
	 *
	 * @param content レイアウト
	 * @return 対象列挙
	 */
	@JsonRpcMethod
	public EnumContent getLayoutEref(
		@JsonRpcParam("content") LayoutContent content
	) {
		return mappers.getLayoutMapper().getEref(content);
	}

	/**
	 * 対象列挙の候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @return 対象列挙の候補一覧
	 */
	@JsonRpcMethod
	public EnumSummary<EnumSubject> listLayoutErefCandidate(
		@JsonRpcParam("content") LayoutContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		EnumSummary<EnumSubject> summary = mappers.getLayoutMapper().listErefCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumSubject> contents = mappers.getLayoutMapper().listErefCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 対象フィールドを取得する
	 *
	 * @param content レイアウト
	 * @return 対象フィールド
	 */
	@JsonRpcMethod
	public FieldContent getLayoutFref(
		@JsonRpcParam("content") LayoutContent content
	) {
		return mappers.getLayoutMapper().getFref(content);
	}

	/**
	 * 対象フィールドの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @return 対象フィールドの候補一覧
	 */
	@JsonRpcMethod
	public FieldSummary<FieldSubject> listLayoutFrefCandidate(
		@JsonRpcParam("content") LayoutContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		FieldSummary<FieldSubject> summary = mappers.getLayoutMapper().listFrefCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<FieldSubject> contents = mappers.getLayoutMapper().listFrefCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 対象メッセージを取得する
	 *
	 * @param content レイアウト
	 * @return 対象メッセージ
	 */
	@JsonRpcMethod
	public MessageContent getLayoutMref(
		@JsonRpcParam("content") LayoutContent content
	) {
		return mappers.getLayoutMapper().getMref(content);
	}

	/**
	 * 対象メッセージの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @return 対象メッセージの候補一覧
	 */
	@JsonRpcMethod
	public MessageSummary<MessageSubject> listLayoutMrefCandidate(
		@JsonRpcParam("content") LayoutContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		MessageSummary<MessageSubject> summary = mappers.getLayoutMapper().listMrefCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<MessageSubject> contents = mappers.getLayoutMapper().listMrefCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 関連レイアウトを取得する
	 *
	 * @param content レイアウト
	 * @return 関連レイアウト
	 */
	@JsonRpcMethod
	public LayoutContent getLayoutRelated(
		@JsonRpcParam("content") LayoutContent content
	) {
		return mappers.getLayoutMapper().getRelated(content);
	}

	/**
	 * 関連レイアウトの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @return 関連レイアウトの候補一覧
	 */
	@JsonRpcMethod
	public LayoutSummary<LayoutSubject> listLayoutRelatedCandidate(
		@JsonRpcParam("content") LayoutContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		LayoutSummary<LayoutSubject> summary = mappers.getLayoutMapper().listRelatedCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LayoutSubject> contents = mappers.getLayoutMapper().listRelatedCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 使用スタイルを取得する
	 *
	 * @param content レイアウト
	 * @return 使用スタイル
	 */
	@JsonRpcMethod
	public StyleContent getLayoutStyle(
		@JsonRpcParam("content") LayoutContent content
	) {
		return mappers.getLayoutMapper().getStyle(content);
	}

	/**
	 * 使用スタイルの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @return 使用スタイルの候補一覧
	 */
	@JsonRpcMethod
	public StyleSummary<StyleSubject> listLayoutStyleCandidate(
		@JsonRpcParam("content") LayoutContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		StyleSummary<StyleSubject> summary = mappers.getLayoutMapper().listStyleCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<StyleSubject> contents = mappers.getLayoutMapper().listStyleCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * レイアウト種別の候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @return レイアウト種別の候補一覧
	 */
	@JsonRpcMethod
	public LayoutTypeSummary<LayoutTypeSubject> listLayoutLayoutTypeCandidate(
		@JsonRpcParam("content") LayoutContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		int count;
		if (Strings.isEmpty(hint)) {
			count = LayoutType.values().length;
		} else {
			count = 0;
			for (LayoutType e : LayoutType.values()) {
				if (fuzzyMatch(e.get_title(), hint)) {
					count++;
				}
			}
		}
		offset = Math.min(Math.max(0, offset), count);
		LayoutTypeSummary<LayoutTypeSubject> summary = new LayoutTypeSummary<>();
		summary.setCount(count);
		summary.setFocus(0);
		summary.setOffset(offset);
		if (count == 0) {
			summary.setContents(Collections.emptyList());
		} else {
			List<LayoutTypeSubject> contents = new ArrayList<>();
			for (LayoutType e : LayoutType.values()) {
				if (Strings.isEmpty(hint) || fuzzyMatch(e.get_title(), hint)) {
					if (offset > 0) {
						offset--;
					} else if (limit > 0) {
						contents.add(new LayoutTypeSubject(e));
						limit--;
						if (limit == 0) {
							break;
						}
					}
				}
			}
			summary.setContents(contents);
		}
		return summary;
	}

	/**
	 * 子レイアウト情報を取得する
	 *
	 * @param content レイアウト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 子レイアウト情報
	 */
	@JsonRpcMethod
	public LayoutSummary<LayoutContent> listLayoutChildren(
		@JsonRpcParam("content") LayoutContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		LayoutSummary<LayoutContent> summary = mappers.getLayoutMapper().listChildrenSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LayoutContent> contents =
				mappers.getLayoutMapper().listChildrenContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * rootに合致する子レイアウト情報を取得する
	 *
	 * @param content レイアウト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 子レイアウト情報
	 */
	@JsonRpcMethod
	public LayoutSummary<LayoutContent> listLayoutChildrenIfRoot(
		@JsonRpcParam("content") LayoutContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		LayoutSummary<LayoutContent> summary = mappers.getLayoutMapper().listChildrenSummaryIfRoot(
				content
				);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LayoutContent> contents = mappers.getLayoutMapper().listChildrenContentIfRoot(
				content,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * ロケールを挿入する
	 *
	 * @param content 挿入するロケール
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean insertLocale(LocaleContent content) {
		return mappers.getLocaleMapper().insert(content);
	}

	/**
	 * ロケールを更新する
	 *
	 * @param content 更新するロケール
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateLocale(LocaleContent content) {
		return mappers.getLocaleMapper().update(content);
	}

	/**
	 * ロケールを削除する
	 *
	 * @param content 削除するロケール
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteLocale(LocaleContent content) {
		return mappers.getLocaleMapper().delete(content);
	}

	/**
	 * ロケールを取得する
	 *
	 * @param localeId ロケールID
	 * @return 取得したロケール
	 */
	@JsonRpcMethod
	public LocaleContent getLocale(
		@JsonRpcParam("localeId") String localeId
	) {
		return mappers.getLocaleMapper().get(
				localeId
				);
	}

	/**
	 * ロケールのトピックパスを取得する
	 *
	 * @param content 取得対象ロケール
	 * @return Subjectリスト
	 */
	@JsonRpcMethod
	public List<Subject> listLocaleTopicPath(LocaleContent content) {
		return mappers.getLocaleMapper().listTopicPath(content);
	}

	/**
	 * 全てのロケール情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return ロケール情報
	 */
	@JsonRpcMethod
	public LocaleSummary<LocaleContent> listLocale(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		LocaleSummary<LocaleContent> summary = mappers.getLocaleMapper().listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LocaleContent> contents = mappers.getLocaleMapper().listContent(offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 所属プロジェクトを取得する
	 *
	 * @param content ロケール
	 * @return 所属プロジェクト
	 */
	@JsonRpcMethod
	public ProjectContent getLocaleOwner(
		@JsonRpcParam("content") LocaleContent content
	) {
		return mappers.getLocaleMapper().getOwner(content);
	}

	/**
	 * 所属プロジェクトの候補一覧を取得する
	 *
	 * @param content ロケール
	 * @return 所属プロジェクトの候補一覧
	 */
	@JsonRpcMethod
	public ProjectSummary<ProjectSubject> listLocaleOwnerCandidate(
		@JsonRpcParam("content") LocaleContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ProjectSummary<ProjectSubject> summary = mappers.getLocaleMapper().listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectSubject> contents = mappers.getLocaleMapper().listOwnerCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * Builderを挿入する
	 *
	 * @param content 挿入するBuilder
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean insertBuilder(BuilderContent content) {
		return mappers.getBuilderMapper().insert(content);
	}

	/**
	 * Builderを更新する
	 *
	 * @param content 更新するBuilder
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateBuilder(BuilderContent content) {
		return mappers.getBuilderMapper().update(content);
	}

	/**
	 * Builderを削除する
	 *
	 * @param content 削除するBuilder
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteBuilder(BuilderContent content) {
		return mappers.getBuilderMapper().delete(content);
	}

	/**
	 * Builderを取得する
	 *
	 * @param className className
	 * @return 取得したBuilder
	 */
	@JsonRpcMethod
	public BuilderContent getBuilder(
		@JsonRpcParam("className") String className
	) {
		return mappers.getBuilderMapper().get(
				className
				);
	}

	/**
	 * Builderのトピックパスを取得する
	 *
	 * @param content 取得対象Builder
	 * @return Subjectリスト
	 */
	@JsonRpcMethod
	public List<Subject> listBuilderTopicPath(BuilderContent content) {
		return mappers.getBuilderMapper().listTopicPath(content);
	}

	/**
	 * 全てのBuilder情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return Builder情報
	 */
	@JsonRpcMethod
	public BuilderSummary<BuilderContent> listBuilder(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		BuilderSummary<BuilderContent> summary = mappers.getBuilderMapper().listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<BuilderContent> contents = mappers.getBuilderMapper().listContent(offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 文字列部分比較関数
	 */
	protected boolean fuzzyMatch(String text, String part) {
		//TODO 全角半角同一視、（ひらがな、カタカナも）。但し、SQL側ロジックと合わせる事
		return text.toLowerCase().indexOf(part.toLowerCase()) >= 0;
	}

}
