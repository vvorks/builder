/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.vvorks.builder.common.lang.Strings;
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
import com.github.vvorks.builder.server.domain.WidgetContent;
import com.github.vvorks.builder.server.domain.WidgetSubject;
import com.github.vvorks.builder.server.domain.WidgetSummary;
import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.DataTypeSubject;
import com.github.vvorks.builder.server.domain.DataTypeSummary;
import com.github.vvorks.builder.server.domain.LayoutType;
import com.github.vvorks.builder.server.domain.LayoutTypeSubject;
import com.github.vvorks.builder.server.domain.LayoutTypeSummary;
import com.github.vvorks.builder.server.domain.StyleCondition;
import com.github.vvorks.builder.server.domain.StyleConditionSubject;
import com.github.vvorks.builder.server.domain.StyleConditionSummary;
import com.github.vvorks.builder.server.mapper.BuilderMapper;
import com.github.vvorks.builder.server.mapper.ClassMapper;
import com.github.vvorks.builder.server.mapper.ClassI18nMapper;
import com.github.vvorks.builder.server.mapper.EnumMapper;
import com.github.vvorks.builder.server.mapper.EnumI18nMapper;
import com.github.vvorks.builder.server.mapper.EnumValueMapper;
import com.github.vvorks.builder.server.mapper.EnumValueI18nMapper;
import com.github.vvorks.builder.server.mapper.FieldMapper;
import com.github.vvorks.builder.server.mapper.FieldI18nMapper;
import com.github.vvorks.builder.server.mapper.LayoutMapper;
import com.github.vvorks.builder.server.mapper.LocaleMapper;
import com.github.vvorks.builder.server.mapper.MessageMapper;
import com.github.vvorks.builder.server.mapper.MessageI18nMapper;
import com.github.vvorks.builder.server.mapper.PageMapper;
import com.github.vvorks.builder.server.mapper.PageSetMapper;
import com.github.vvorks.builder.server.mapper.ProjectMapper;
import com.github.vvorks.builder.server.mapper.ProjectI18nMapper;
import com.github.vvorks.builder.server.mapper.QueryMapper;
import com.github.vvorks.builder.server.mapper.StyleMapper;
import com.github.vvorks.builder.server.mapper.WidgetMapper;

/**
 * ビルダープロジェクトの Json-Rpc (on Websocket) API
 */
@JsonRpcController("/builder")
public class BuilderRpcController {

	/** プロジェクトのMapper */
	@Autowired
	private ProjectMapper projectMapper;

	/** プロジェクト(I18n)のMapper */
	@Autowired
	private ProjectI18nMapper projectI18nMapper;

	/** クラスのMapper */
	@Autowired
	private ClassMapper classMapper;

	/** クラス(I18n)のMapper */
	@Autowired
	private ClassI18nMapper classI18nMapper;

	/** フィールドのMapper */
	@Autowired
	private FieldMapper fieldMapper;

	/** フィールド(I18n)のMapper */
	@Autowired
	private FieldI18nMapper fieldI18nMapper;

	/** クエリーのMapper */
	@Autowired
	private QueryMapper queryMapper;

	/** 列挙のMapper */
	@Autowired
	private EnumMapper enumMapper;

	/** 列挙(I18n)のMapper */
	@Autowired
	private EnumI18nMapper enumI18nMapper;

	/** 列挙値のMapper */
	@Autowired
	private EnumValueMapper enumValueMapper;

	/** 列挙値(I18n)のMapper */
	@Autowired
	private EnumValueI18nMapper enumValueI18nMapper;

	/** メッセージのMapper */
	@Autowired
	private MessageMapper messageMapper;

	/** メッセージ(I18n)のMapper */
	@Autowired
	private MessageI18nMapper messageI18nMapper;

	/** スタイルのMapper */
	@Autowired
	private StyleMapper styleMapper;

	/** ウィジェットのMapper */
	@Autowired
	private WidgetMapper widgetMapper;

	/** ページセットのMapper */
	@Autowired
	private PageSetMapper pageSetMapper;

	/** ページのMapper */
	@Autowired
	private PageMapper pageMapper;

	/** レイアウトのMapper */
	@Autowired
	private LayoutMapper layoutMapper;

	/** ロケールのMapper */
	@Autowired
	private LocaleMapper localeMapper;

	/** BuilderのMapper */
	@Autowired
	private BuilderMapper builderMapper;

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
		MessageSummary<MessageContent> summary = projectMapper.listMessagesSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<MessageContent> contents =
				projectMapper.listMessagesContent(content, offset, limit);
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
		StyleSummary<StyleContent> summary = projectMapper.listStylesSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<StyleContent> contents =
				projectMapper.listStylesContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * widgets情報を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return widgets情報
	 */
	@JsonRpcMethod
	public WidgetSummary<WidgetContent> listProjectWidgets(
		@JsonRpcParam("content") ProjectContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		WidgetSummary<WidgetContent> summary = projectMapper.listWidgetsSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<WidgetContent> contents =
				projectMapper.listWidgetsContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * pageSets情報を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return pageSets情報
	 */
	@JsonRpcMethod
	public PageSetSummary<PageSetContent> listProjectPageSets(
		@JsonRpcParam("content") ProjectContent content,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		PageSetSummary<PageSetContent> summary = projectMapper.listPageSetsSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<PageSetContent> contents =
				projectMapper.listPageSetsContent(content, offset, limit);
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
		LocaleSummary<LocaleContent> summary = projectMapper.listLocalesSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LocaleContent> contents =
				projectMapper.listLocalesContent(content, offset, limit);
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
		ProjectI18nSummary<ProjectI18nContent> summary = projectMapper.listI18nsSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectI18nContent> contents =
				projectMapper.listI18nsContent(content, offset, limit);
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
		return projectI18nMapper.insert(content);
	}

	/**
	 * プロジェクト(I18n)を更新する
	 *
	 * @param content 更新するプロジェクト(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateProjectI18n(ProjectI18nContent content) {
		return projectI18nMapper.update(content);
	}

	/**
	 * プロジェクト(I18n)を削除する
	 *
	 * @param content 削除するプロジェクト(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteProjectI18n(ProjectI18nContent content) {
		return projectI18nMapper.delete(content);
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
		return projectI18nMapper.get(
				ownerProjectId, 
				targetLocaleId
				);
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
		ProjectI18nSummary<ProjectI18nContent> summary = projectI18nMapper.listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectI18nContent> contents = projectI18nMapper.listContent(offset, limit);
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
		return projectI18nMapper.getOwner(content);
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
		ProjectSummary<ProjectSubject> summary = projectI18nMapper.listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectSubject> contents = projectI18nMapper.listOwnerCandidateSubject(
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
		return projectI18nMapper.getTarget(content);
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
		LocaleSummary<LocaleSubject> summary = projectI18nMapper.listTargetCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LocaleSubject> contents = projectI18nMapper.listTargetCandidateSubject(
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
		ClassI18nSummary<ClassI18nContent> summary = classMapper.listI18nsSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassI18nContent> contents =
				classMapper.listI18nsContent(content, offset, limit);
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
		return classI18nMapper.insert(content);
	}

	/**
	 * クラス(I18n)を更新する
	 *
	 * @param content 更新するクラス(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateClassI18n(ClassI18nContent content) {
		return classI18nMapper.update(content);
	}

	/**
	 * クラス(I18n)を削除する
	 *
	 * @param content 削除するクラス(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteClassI18n(ClassI18nContent content) {
		return classI18nMapper.delete(content);
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
		return classI18nMapper.get(
				ownerClassId, 
				targetLocaleId
				);
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
		ClassI18nSummary<ClassI18nContent> summary = classI18nMapper.listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassI18nContent> contents = classI18nMapper.listContent(offset, limit);
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
		return classI18nMapper.getOwner(content);
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
		ClassSummary<ClassSubject> summary = classI18nMapper.listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassSubject> contents = classI18nMapper.listOwnerCandidateSubject(
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
		return classI18nMapper.getTarget(content);
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
		LocaleSummary<LocaleSubject> summary = classI18nMapper.listTargetCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LocaleSubject> contents = classI18nMapper.listTargetCandidateSubject(
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
		FieldI18nSummary<FieldI18nContent> summary = fieldMapper.listI18nsSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<FieldI18nContent> contents =
				fieldMapper.listI18nsContent(content, offset, limit);
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
		return fieldI18nMapper.insert(content);
	}

	/**
	 * フィールド(I18n)を更新する
	 *
	 * @param content 更新するフィールド(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateFieldI18n(FieldI18nContent content) {
		return fieldI18nMapper.update(content);
	}

	/**
	 * フィールド(I18n)を削除する
	 *
	 * @param content 削除するフィールド(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteFieldI18n(FieldI18nContent content) {
		return fieldI18nMapper.delete(content);
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
		return fieldI18nMapper.get(
				ownerFieldId, 
				targetLocaleId
				);
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
		FieldI18nSummary<FieldI18nContent> summary = fieldI18nMapper.listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<FieldI18nContent> contents = fieldI18nMapper.listContent(offset, limit);
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
		return fieldI18nMapper.getOwner(content);
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
		FieldSummary<FieldSubject> summary = fieldI18nMapper.listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<FieldSubject> contents = fieldI18nMapper.listOwnerCandidateSubject(
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
		return fieldI18nMapper.getTarget(content);
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
		LocaleSummary<LocaleSubject> summary = fieldI18nMapper.listTargetCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LocaleSubject> contents = fieldI18nMapper.listTargetCandidateSubject(
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
		EnumI18nSummary<EnumI18nContent> summary = enumMapper.listI18nsSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumI18nContent> contents =
				enumMapper.listI18nsContent(content, offset, limit);
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
		return enumI18nMapper.insert(content);
	}

	/**
	 * 列挙(I18n)を更新する
	 *
	 * @param content 更新する列挙(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateEnumI18n(EnumI18nContent content) {
		return enumI18nMapper.update(content);
	}

	/**
	 * 列挙(I18n)を削除する
	 *
	 * @param content 削除する列挙(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteEnumI18n(EnumI18nContent content) {
		return enumI18nMapper.delete(content);
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
		return enumI18nMapper.get(
				ownerEnumId, 
				targetLocaleId
				);
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
		EnumI18nSummary<EnumI18nContent> summary = enumI18nMapper.listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumI18nContent> contents = enumI18nMapper.listContent(offset, limit);
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
		return enumI18nMapper.getOwner(content);
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
		EnumSummary<EnumSubject> summary = enumI18nMapper.listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumSubject> contents = enumI18nMapper.listOwnerCandidateSubject(
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
		return enumI18nMapper.getTarget(content);
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
		LocaleSummary<LocaleSubject> summary = enumI18nMapper.listTargetCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LocaleSubject> contents = enumI18nMapper.listTargetCandidateSubject(
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
		EnumValueI18nSummary<EnumValueI18nContent> summary = enumValueMapper.listI18nsSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumValueI18nContent> contents =
				enumValueMapper.listI18nsContent(content, offset, limit);
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
		return enumValueI18nMapper.insert(content);
	}

	/**
	 * 列挙値(I18n)を更新する
	 *
	 * @param content 更新する列挙値(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateEnumValueI18n(EnumValueI18nContent content) {
		return enumValueI18nMapper.update(content);
	}

	/**
	 * 列挙値(I18n)を削除する
	 *
	 * @param content 削除する列挙値(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteEnumValueI18n(EnumValueI18nContent content) {
		return enumValueI18nMapper.delete(content);
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
		return enumValueI18nMapper.get(
				ownerOwnerEnumId, 
				ownerValueId, 
				targetLocaleId
				);
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
		EnumValueI18nSummary<EnumValueI18nContent> summary = enumValueI18nMapper.listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumValueI18nContent> contents = enumValueI18nMapper.listContent(offset, limit);
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
		return enumValueI18nMapper.getOwner(content);
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
		EnumValueSummary<EnumValueSubject> summary = enumValueI18nMapper.listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<EnumValueSubject> contents = enumValueI18nMapper.listOwnerCandidateSubject(
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
		return enumValueI18nMapper.getTarget(content);
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
		LocaleSummary<LocaleSubject> summary = enumValueI18nMapper.listTargetCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LocaleSubject> contents = enumValueI18nMapper.listTargetCandidateSubject(
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
		return messageMapper.insert(content);
	}

	/**
	 * メッセージを更新する
	 *
	 * @param content 更新するメッセージ
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateMessage(MessageContent content) {
		return messageMapper.update(content);
	}

	/**
	 * メッセージを削除する
	 *
	 * @param content 削除するメッセージ
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteMessage(MessageContent content) {
		return messageMapper.delete(content);
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
		return messageMapper.get(
				messageId
				);
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
		MessageSummary<MessageContent> summary = messageMapper.listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<MessageContent> contents = messageMapper.listContent(offset, limit);
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
		return messageMapper.getOwner(content);
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
		ProjectSummary<ProjectSubject> summary = messageMapper.listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectSubject> contents = messageMapper.listOwnerCandidateSubject(
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
		MessageI18nSummary<MessageI18nContent> summary = messageMapper.listI18nsSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<MessageI18nContent> contents =
				messageMapper.listI18nsContent(content, offset, limit);
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
		return messageI18nMapper.insert(content);
	}

	/**
	 * メッセージ(I18n)を更新する
	 *
	 * @param content 更新するメッセージ(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateMessageI18n(MessageI18nContent content) {
		return messageI18nMapper.update(content);
	}

	/**
	 * メッセージ(I18n)を削除する
	 *
	 * @param content 削除するメッセージ(I18n)
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteMessageI18n(MessageI18nContent content) {
		return messageI18nMapper.delete(content);
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
		return messageI18nMapper.get(
				ownerMessageId, 
				targetLocaleId
				);
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
		MessageI18nSummary<MessageI18nContent> summary = messageI18nMapper.listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<MessageI18nContent> contents = messageI18nMapper.listContent(offset, limit);
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
		return messageI18nMapper.getOwner(content);
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
		MessageSummary<MessageSubject> summary = messageI18nMapper.listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<MessageSubject> contents = messageI18nMapper.listOwnerCandidateSubject(
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
		return messageI18nMapper.getTarget(content);
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
		LocaleSummary<LocaleSubject> summary = messageI18nMapper.listTargetCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LocaleSubject> contents = messageI18nMapper.listTargetCandidateSubject(
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
		return styleMapper.insert(content);
	}

	/**
	 * スタイルを更新する
	 *
	 * @param content 更新するスタイル
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateStyle(StyleContent content) {
		return styleMapper.update(content);
	}

	/**
	 * スタイルを削除する
	 *
	 * @param content 削除するスタイル
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteStyle(StyleContent content) {
		return styleMapper.delete(content);
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
		return styleMapper.get(
				styleId
				);
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
		StyleSummary<StyleContent> summary = styleMapper.listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<StyleContent> contents = styleMapper.listContent(offset, limit);
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
		return styleMapper.getOwner(content);
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
		ProjectSummary<ProjectSubject> summary = styleMapper.listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectSubject> contents = styleMapper.listOwnerCandidateSubject(
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
		return styleMapper.getParent(content);
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
		StyleSummary<StyleSubject> summary = styleMapper.listParentCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<StyleSubject> contents = styleMapper.listParentCandidateSubject(
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
		StyleSummary<StyleContent> summary = styleMapper.listChildrenSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<StyleContent> contents =
				styleMapper.listChildrenContent(content, offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * ウィジェットを挿入する
	 *
	 * @param content 挿入するウィジェット
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean insertWidget(WidgetContent content) {
		return widgetMapper.insert(content);
	}

	/**
	 * ウィジェットを更新する
	 *
	 * @param content 更新するウィジェット
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateWidget(WidgetContent content) {
		return widgetMapper.update(content);
	}

	/**
	 * ウィジェットを削除する
	 *
	 * @param content 削除するウィジェット
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteWidget(WidgetContent content) {
		return widgetMapper.delete(content);
	}

	/**
	 * ウィジェットを取得する
	 *
	 * @param widgetId ウィジェットId
	 * @return 取得したウィジェット
	 */
	@JsonRpcMethod
	public WidgetContent getWidget(
		@JsonRpcParam("widgetId") int widgetId
	) {
		return widgetMapper.get(
				widgetId
				);
	}

	/**
	 * 全てのウィジェット情報を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return ウィジェット情報
	 */
	@JsonRpcMethod
	public WidgetSummary<WidgetContent> listWidget(
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		WidgetSummary<WidgetContent> summary = widgetMapper.listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<WidgetContent> contents = widgetMapper.listContent(offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 所属プロジェクトを取得する
	 *
	 * @param content ウィジェット
	 * @return 所属プロジェクト
	 */
	@JsonRpcMethod
	public ProjectContent getWidgetOwner(
		@JsonRpcParam("content") WidgetContent content
	) {
		return widgetMapper.getOwner(content);
	}

	/**
	 * 所属プロジェクトの候補一覧を取得する
	 *
	 * @param content ウィジェット
	 * @return 所属プロジェクトの候補一覧
	 */
	@JsonRpcMethod
	public ProjectSummary<ProjectSubject> listWidgetOwnerCandidate(
		@JsonRpcParam("content") WidgetContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		ProjectSummary<ProjectSubject> summary = widgetMapper.listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectSubject> contents = widgetMapper.listOwnerCandidateSubject(
				content, hint,
				offset, limit);
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
		return pageSetMapper.insert(content);
	}

	/**
	 * ページセットを更新する
	 *
	 * @param content 更新するページセット
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updatePageSet(PageSetContent content) {
		return pageSetMapper.update(content);
	}

	/**
	 * ページセットを削除する
	 *
	 * @param content 削除するページセット
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deletePageSet(PageSetContent content) {
		return pageSetMapper.delete(content);
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
		return pageSetMapper.get(
				pageSetId
				);
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
		PageSetSummary<PageSetContent> summary = pageSetMapper.listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<PageSetContent> contents = pageSetMapper.listContent(offset, limit);
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
		return pageSetMapper.getOwner(content);
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
		ProjectSummary<ProjectSubject> summary = pageSetMapper.listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectSubject> contents = pageSetMapper.listOwnerCandidateSubject(
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
		PageSummary<PageContent> summary = pageSetMapper.listPagesSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<PageContent> contents =
				pageSetMapper.listPagesContent(content, offset, limit);
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
		return pageMapper.insert(content);
	}

	/**
	 * ページを更新する
	 *
	 * @param content 更新するページ
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updatePage(PageContent content) {
		return pageMapper.update(content);
	}

	/**
	 * ページを削除する
	 *
	 * @param content 削除するページ
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deletePage(PageContent content) {
		return pageMapper.delete(content);
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
		return pageMapper.get(
				pageId
				);
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
		PageSummary<PageContent> summary = pageMapper.listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<PageContent> contents = pageMapper.listContent(offset, limit);
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
		return pageMapper.getOwner(content);
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
		PageSetSummary<PageSetSubject> summary = pageMapper.listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<PageSetSubject> contents = pageMapper.listOwnerCandidateSubject(
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
		return pageMapper.getContext(content);
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
		ClassSummary<ClassSubject> summary = pageMapper.listContextCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ClassSubject> contents = pageMapper.listContextCandidateSubject(
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
		LayoutSummary<LayoutContent> summary = pageMapper.listLayoutsSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LayoutContent> contents =
				pageMapper.listLayoutsContent(content, offset, limit);
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
		return layoutMapper.insert(content);
	}

	/**
	 * レイアウトを更新する
	 *
	 * @param content 更新するレイアウト
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateLayout(LayoutContent content) {
		return layoutMapper.update(content);
	}

	/**
	 * レイアウトを削除する
	 *
	 * @param content 削除するレイアウト
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteLayout(LayoutContent content) {
		return layoutMapper.delete(content);
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
		return layoutMapper.get(
				layoutId
				);
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
		LayoutSummary<LayoutContent> summary = layoutMapper.listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LayoutContent> contents = layoutMapper.listContent(offset, limit);
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
		return layoutMapper.getOwner(content);
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
		PageSummary<PageSubject> summary = layoutMapper.listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<PageSubject> contents = layoutMapper.listOwnerCandidateSubject(
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
		return layoutMapper.getParent(content);
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
		LayoutSummary<LayoutSubject> summary = layoutMapper.listParentCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LayoutSubject> contents = layoutMapper.listParentCandidateSubject(
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
	public FieldContent getLayoutTarget(
		@JsonRpcParam("content") LayoutContent content
	) {
		return layoutMapper.getTarget(content);
	}

	/**
	 * 対象フィールドの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @return 対象フィールドの候補一覧
	 */
	@JsonRpcMethod
	public FieldSummary<FieldSubject> listLayoutTargetCandidate(
		@JsonRpcParam("content") LayoutContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		FieldSummary<FieldSubject> summary = layoutMapper.listTargetCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<FieldSubject> contents = layoutMapper.listTargetCandidateSubject(
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
	public MessageContent getLayoutConst(
		@JsonRpcParam("content") LayoutContent content
	) {
		return layoutMapper.getConst(content);
	}

	/**
	 * 対象メッセージの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @return 対象メッセージの候補一覧
	 */
	@JsonRpcMethod
	public MessageSummary<MessageSubject> listLayoutConstCandidate(
		@JsonRpcParam("content") LayoutContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		MessageSummary<MessageSubject> summary = layoutMapper.listConstCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<MessageSubject> contents = layoutMapper.listConstCandidateSubject(
				content, hint,
				offset, limit);
		summary.setContents(contents);
		return summary;
	}

	/**
	 * 使用Widgetを取得する
	 *
	 * @param content レイアウト
	 * @return 使用Widget
	 */
	@JsonRpcMethod
	public WidgetContent getLayoutWidget(
		@JsonRpcParam("content") LayoutContent content
	) {
		return layoutMapper.getWidget(content);
	}

	/**
	 * 使用Widgetの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @return 使用Widgetの候補一覧
	 */
	@JsonRpcMethod
	public WidgetSummary<WidgetSubject> listLayoutWidgetCandidate(
		@JsonRpcParam("content") LayoutContent content,
		@JsonRpcParam("hint") String hint,
		@JsonRpcParam("offset") int offset,
		@JsonRpcParam("limit") int limit
	) {
		WidgetSummary<WidgetSubject> summary = layoutMapper.listWidgetCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<WidgetSubject> contents = layoutMapper.listWidgetCandidateSubject(
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
		return layoutMapper.getStyle(content);
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
		StyleSummary<StyleSubject> summary = layoutMapper.listStyleCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<StyleSubject> contents = layoutMapper.listStyleCandidateSubject(
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
		LayoutSummary<LayoutContent> summary = layoutMapper.listChildrenSummary(content);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LayoutContent> contents =
				layoutMapper.listChildrenContent(content, offset, limit);
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
		return localeMapper.insert(content);
	}

	/**
	 * ロケールを更新する
	 *
	 * @param content 更新するロケール
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateLocale(LocaleContent content) {
		return localeMapper.update(content);
	}

	/**
	 * ロケールを削除する
	 *
	 * @param content 削除するロケール
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteLocale(LocaleContent content) {
		return localeMapper.delete(content);
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
		return localeMapper.get(
				localeId
				);
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
		LocaleSummary<LocaleContent> summary = localeMapper.listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<LocaleContent> contents = localeMapper.listContent(offset, limit);
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
		return localeMapper.getOwner(content);
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
		ProjectSummary<ProjectSubject> summary = localeMapper.listOwnerCandidateSummary(
				content, hint);
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<ProjectSubject> contents = localeMapper.listOwnerCandidateSubject(
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
		return builderMapper.insert(content);
	}

	/**
	 * Builderを更新する
	 *
	 * @param content 更新するBuilder
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean updateBuilder(BuilderContent content) {
		return builderMapper.update(content);
	}

	/**
	 * Builderを削除する
	 *
	 * @param content 削除するBuilder
	 * @return 処理成功の場合、真
	 */
	@JsonRpcMethod
	public boolean deleteBuilder(BuilderContent content) {
		return builderMapper.delete(content);
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
		return builderMapper.get(
				className
				);
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
		BuilderSummary<BuilderContent> summary = builderMapper.listSummary();
		if (offset < 0) {
			offset = summary.getFocus();
		}
		summary.setOffset(offset);
		List<BuilderContent> contents = builderMapper.listContent(offset, limit);
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
