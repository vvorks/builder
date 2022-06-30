/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface ProjectI18nMapper extends BuilderMapper<ProjectI18nContent> {

	@Override
	public default List<ProjectI18nContent> listAll() {
		return listContent(0, 0);
	}

	/**
	 * プロジェクト(I18n)を挿入する
	 *
	 * @param content 挿入するプロジェクト(I18n)
	 * @return 処理成功の場合、真
	 */
	public boolean insert(ProjectI18nContent content);

	/**
	 * プロジェクト(I18n)を更新する
	 *
	 * @param content 更新するプロジェクト(I18n)
	 * @return 処理成功の場合、真
	 */
	public boolean update(ProjectI18nContent content);

	/**
	 * プロジェクト(I18n)を削除する
	 *
	 * @param content 削除するプロジェクト(I18n)
	 * @return 処理成功の場合、真
	 */
	public boolean delete(ProjectI18nContent content);

	/**
	 * プロジェクト(I18n)を取得する
	 *
	 * @param ownerProjectId ownerのプロジェクトID
	 * @param targetLocaleId targetのlocaleId
	 * @return 取得したプロジェクト(I18n)
	 */
	public ProjectI18nContent get(
		@Param("ownerProjectId") int ownerProjectId, 
		@Param("targetLocaleId") String targetLocaleId
	);

	/**
	 * 全てのプロジェクト(I18n)のサマリーを取得する
	 *
	 * @return プロジェクト(I18n)のサマリー
	 */
	public ProjectI18nSummary<ProjectI18nContent> listSummary();

	/**
	 * 全てのプロジェクト(I18n)を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return プロジェクト(I18n)のリスト
	 */
	public List<ProjectI18nContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * ownerを取得する
	 *
	 * @param content プロジェクト(I18n)
	 * @return owner
	 */
	public ProjectContent getOwner(ProjectI18nContent content);

	/**
	 * ownerの候補サマリーを取得する
	 *
	 * @param content プロジェクト(I18n)
	 * @param hint ヒント情報
	 * @return ownerの候補サマリー
	 */
	public ProjectSummary<ProjectSubject> listOwnerCandidateSummary(
		@Param("content") ProjectI18nContent content,
		@Param("hint") String hint
	);

	/**
	 * ownerの候補一覧を取得する
	 *
	 * @param content プロジェクト(I18n)
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return ownerの候補一覧
	 */
	public List<ProjectSubject> listOwnerCandidateSubject(
		@Param("content") ProjectI18nContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * targetを取得する
	 *
	 * @param content プロジェクト(I18n)
	 * @return target
	 */
	public LocaleContent getTarget(ProjectI18nContent content);

	/**
	 * targetの候補サマリーを取得する
	 *
	 * @param content プロジェクト(I18n)
	 * @param hint ヒント情報
	 * @return targetの候補サマリー
	 */
	public LocaleSummary<LocaleSubject> listTargetCandidateSummary(
		@Param("content") ProjectI18nContent content,
		@Param("hint") String hint
	);

	/**
	 * targetの候補一覧を取得する
	 *
	 * @param content プロジェクト(I18n)
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return targetの候補一覧
	 */
	public List<LocaleSubject> listTargetCandidateSubject(
		@Param("content") ProjectI18nContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
