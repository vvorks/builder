/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface ProjectMapper extends MapperInterface<ProjectContent> {

	@Override
	public default List<ProjectContent> listAll() {
		return listContent(0, 0);
	}

	/**
	 * プロジェクトを挿入する
	 *
	 * @param content 挿入するプロジェクト
	 * @return 処理成功の場合、真
	 */
	public boolean insert(ProjectContent content);

	/**
	 * プロジェクトを更新する
	 *
	 * @param content 更新するプロジェクト
	 * @return 処理成功の場合、真
	 */
	public boolean update(ProjectContent content);

	/**
	 * プロジェクトを削除する
	 *
	 * @param content 削除するプロジェクト
	 * @return 処理成功の場合、真
	 */
	public boolean delete(ProjectContent content);

	/**
	 * プロジェクトを取得する
	 *
	 * @param projectId プロジェクトID
	 * @return 取得したプロジェクト
	 */
	public ProjectContent get(
		@Param("projectId") int projectId
	);

	/**
	 * プロジェクトのトピックパスを取得する
	 *
	 * @param content プロジェクト
	 * @return プロジェクトのトピックパス
	 */
	public Map<String, Object> getTopicPath(ProjectContent content);

	/**
	 * 全てのプロジェクトのサマリーを取得する
	 *
	 * @return プロジェクトのサマリー
	 */
	public ProjectSummary<ProjectContent> listSummary();

	/**
	 * 全てのプロジェクトを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return プロジェクトのリスト
	 */
	public List<ProjectContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 名前に合致するプロジェクトのサマリーを取得する
	 *
	 * @param name name
	 * @return プロジェクトのサマリー
	 */
	public ProjectSummary<ProjectContent> listSummaryIfNameIs(
		@Param("name") String name
	);

	/**
	 * 名前に合致するプロジェクトを取得する
	 *
	 * @param name name
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return プロジェクトのリスト
	 */
	public List<ProjectContent> listContentIfNameIs(
		@Param("name") String name,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * クラス一覧のサマリーを取得する
	 *
	 * @param content プロジェクト
	 * @return クラス一覧のサマリー
	 */
	public ClassSummary<ClassContent> listClassesSummary(
		@Param("content") ProjectContent content
	);

	/**
	 * クラス一覧を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラス一覧
	 */
	public List<ClassContent> listClassesContent(
		@Param("content") ProjectContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 名前に合致するクラス一覧のサマリーを取得する
	 *
	 * @param content プロジェクト
	 * @return クラス一覧のサマリー
	 */
	public ClassSummary<ClassContent> listClassesSummaryIfNameIs(
		@Param("content") ProjectContent content,
		@Param("name") String name
	);

	/**
	 * 名前に合致するクラス一覧を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラス一覧
	 */
	public List<ClassContent> listClassesContentIfNameIs(
		@Param("content") ProjectContent content,
		@Param("name") String name,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 列挙一覧のサマリーを取得する
	 *
	 * @param content プロジェクト
	 * @return 列挙一覧のサマリー
	 */
	public EnumSummary<EnumContent> listEnumsSummary(
		@Param("content") ProjectContent content
	);

	/**
	 * 列挙一覧を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙一覧
	 */
	public List<EnumContent> listEnumsContent(
		@Param("content") ProjectContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 名前に合致する列挙一覧のサマリーを取得する
	 *
	 * @param content プロジェクト
	 * @return 列挙一覧のサマリー
	 */
	public EnumSummary<EnumContent> listEnumsSummaryIfNameIs(
		@Param("content") ProjectContent content,
		@Param("name") String name
	);

	/**
	 * 名前に合致する列挙一覧を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙一覧
	 */
	public List<EnumContent> listEnumsContentIfNameIs(
		@Param("content") ProjectContent content,
		@Param("name") String name,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * メッセージ一覧のサマリーを取得する
	 *
	 * @param content プロジェクト
	 * @return メッセージ一覧のサマリー
	 */
	public MessageSummary<MessageContent> listMessagesSummary(
		@Param("content") ProjectContent content
	);

	/**
	 * メッセージ一覧を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return メッセージ一覧
	 */
	public List<MessageContent> listMessagesContent(
		@Param("content") ProjectContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * スタイル一覧のサマリーを取得する
	 *
	 * @param content プロジェクト
	 * @return スタイル一覧のサマリー
	 */
	public StyleSummary<StyleContent> listStylesSummary(
		@Param("content") ProjectContent content
	);

	/**
	 * スタイル一覧を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return スタイル一覧
	 */
	public List<StyleContent> listStylesContent(
		@Param("content") ProjectContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * ページセット一覧のサマリーを取得する
	 *
	 * @param content プロジェクト
	 * @return ページセット一覧のサマリー
	 */
	public PageSetSummary<PageSetContent> listPageSetsSummary(
		@Param("content") ProjectContent content
	);

	/**
	 * ページセット一覧を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return ページセット一覧
	 */
	public List<PageSetContent> listPageSetsContent(
		@Param("content") ProjectContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * ロケール一覧のサマリーを取得する
	 *
	 * @param content プロジェクト
	 * @return ロケール一覧のサマリー
	 */
	public LocaleSummary<LocaleContent> listLocalesSummary(
		@Param("content") ProjectContent content
	);

	/**
	 * ロケール一覧を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return ロケール一覧
	 */
	public List<LocaleContent> listLocalesContent(
		@Param("content") ProjectContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * I18n一覧のサマリーを取得する
	 *
	 * @param content プロジェクト
	 * @return I18n一覧のサマリー
	 */
	public ProjectI18nSummary<ProjectI18nContent> listI18nsSummary(
		@Param("content") ProjectContent content
	);

	/**
	 * I18n一覧を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return I18n一覧
	 */
	public List<ProjectI18nContent> listI18nsContent(
		@Param("content") ProjectContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * プロジェクトが保持するスタイル一覧を全て削除する
	 *
	 * @param content プロジェクト
	 */
	public boolean deleteStylesAll(
		@Param("content") ProjectContent content
	);

	/**
	 * プロジェクトが保持するロケール一覧を全て削除する
	 *
	 * @param content プロジェクト
	 */
	public boolean deleteLocalesAll(
		@Param("content") ProjectContent content
	);

	/**
	 * プロジェクトが保持するI18n一覧を全て削除する
	 *
	 * @param content プロジェクト
	 */
	public boolean deleteI18nsAll(
		@Param("content") ProjectContent content
	);

}
