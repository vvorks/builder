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
public interface PageMapper extends MapperInterface<PageContent> {

	@Override
	public default List<PageContent> listAll() {
		return listContent(0, 0);
	}

	/**
	 * ページを挿入する
	 *
	 * @param content 挿入するページ
	 * @return 処理成功の場合、真
	 */
	public boolean insert(PageContent content);

	/**
	 * ページを更新する
	 *
	 * @param content 更新するページ
	 * @return 処理成功の場合、真
	 */
	public boolean update(PageContent content);

	/**
	 * ページを削除する
	 *
	 * @param content 削除するページ
	 * @return 処理成功の場合、真
	 */
	public boolean delete(PageContent content);

	/**
	 * ページを取得する
	 *
	 * @param pageId ページId
	 * @return 取得したページ
	 */
	public PageContent get(
		@Param("pageId") int pageId
	);

	/**
	 * ページのトピックパスを取得する
	 *
	 * @param content ページ
	 * @return ページのトピックパス
	 */
	public Map<String, Object> getTopicPath(PageContent content);

	/**
	 * 全てのページのサマリーを取得する
	 *
	 * @return ページのサマリー
	 */
	public PageSummary<PageContent> listSummary();

	/**
	 * 全てのページを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return ページのリスト
	 */
	public List<PageContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 所属ページセットを取得する
	 *
	 * @param content ページ
	 * @return 所属ページセット
	 */
	public PageSetContent getOwner(PageContent content);

	/**
	 * 所属ページセットの候補サマリーを取得する
	 *
	 * @param content ページ
	 * @param hint ヒント情報
	 * @return 所属ページセットの候補サマリー
	 */
	public PageSetSummary<PageSetSubject> listOwnerCandidateSummary(
		@Param("content") PageContent content,
		@Param("hint") String hint
	);

	/**
	 * 所属ページセットの候補一覧を取得する
	 *
	 * @param content ページ
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 所属ページセットの候補一覧
	 */
	public List<PageSetSubject> listOwnerCandidateSubject(
		@Param("content") PageContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * コンテキストクラスを取得する
	 *
	 * @param content ページ
	 * @return コンテキストクラス
	 */
	public ClassContent getContext(PageContent content);

	/**
	 * コンテキストクラスの候補サマリーを取得する
	 *
	 * @param content ページ
	 * @param hint ヒント情報
	 * @return コンテキストクラスの候補サマリー
	 */
	public ClassSummary<ClassSubject> listContextCandidateSummary(
		@Param("content") PageContent content,
		@Param("hint") String hint
	);

	/**
	 * コンテキストクラスの候補一覧を取得する
	 *
	 * @param content ページ
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return コンテキストクラスの候補一覧
	 */
	public List<ClassSubject> listContextCandidateSubject(
		@Param("content") PageContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * レイアウト一覧のサマリーを取得する
	 *
	 * @param content ページ
	 * @return レイアウト一覧のサマリー
	 */
	public LayoutSummary<LayoutContent> listLayoutsSummary(
		@Param("content") PageContent content
	);

	/**
	 * レイアウト一覧を取得する
	 *
	 * @param content ページ
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return レイアウト一覧
	 */
	public List<LayoutContent> listLayoutsContent(
		@Param("content") PageContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * rootに合致するレイアウト一覧のサマリーを取得する
	 *
	 * @param content ページ
	 * @return レイアウト一覧のサマリー
	 */
	public LayoutSummary<LayoutContent> listLayoutsSummaryIfRoot(
		@Param("content") PageContent content
	);

	/**
	 * rootに合致するレイアウト一覧を取得する
	 *
	 * @param content ページ
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return レイアウト一覧
	 */
	public List<LayoutContent> listLayoutsContentIfRoot(
		@Param("content") PageContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * ページが保持するレイアウト一覧を全て削除する
	 *
	 * @param content ページ
	 */
	public boolean deleteLayoutsAll(
		@Param("content") PageContent content
	);

}
