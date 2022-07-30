/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface PageSetMapper extends MapperInterface<PageSetContent> {

	@Override
	public default List<PageSetContent> listAll() {
		return listContent(0, 0);
	}

	/**
	 * ページセットを挿入する
	 *
	 * @param content 挿入するページセット
	 * @return 処理成功の場合、真
	 */
	public boolean insert(PageSetContent content);

	/**
	 * ページセットを更新する
	 *
	 * @param content 更新するページセット
	 * @return 処理成功の場合、真
	 */
	public boolean update(PageSetContent content);

	/**
	 * ページセットを削除する
	 *
	 * @param content 削除するページセット
	 * @return 処理成功の場合、真
	 */
	public boolean delete(PageSetContent content);

	/**
	 * ページセットを取得する
	 *
	 * @param pageSetId ページセットId
	 * @return 取得したページセット
	 */
	public PageSetContent get(
		@Param("pageSetId") int pageSetId
	);

	/**
	 * 全てのページセットのサマリーを取得する
	 *
	 * @return ページセットのサマリー
	 */
	public PageSetSummary<PageSetContent> listSummary();

	/**
	 * 全てのページセットを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return ページセットのリスト
	 */
	public List<PageSetContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 所属プロジェクトを取得する
	 *
	 * @param content ページセット
	 * @return 所属プロジェクト
	 */
	public ProjectContent getOwner(PageSetContent content);

	/**
	 * 所属プロジェクトの候補サマリーを取得する
	 *
	 * @param content ページセット
	 * @param hint ヒント情報
	 * @return 所属プロジェクトの候補サマリー
	 */
	public ProjectSummary<ProjectSubject> listOwnerCandidateSummary(
		@Param("content") PageSetContent content,
		@Param("hint") String hint
	);

	/**
	 * 所属プロジェクトの候補一覧を取得する
	 *
	 * @param content ページセット
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 所属プロジェクトの候補一覧
	 */
	public List<ProjectSubject> listOwnerCandidateSubject(
		@Param("content") PageSetContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * ページ一覧のサマリーを取得する
	 *
	 * @param content ページセット
	 * @return ページ一覧のサマリー
	 */
	public PageSummary<PageContent> listPagesSummary(
		@Param("content") PageSetContent content
	);

	/**
	 * ページ一覧を取得する
	 *
	 * @param content ページセット
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return ページ一覧
	 */
	public List<PageContent> listPagesContent(
		@Param("content") PageSetContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
