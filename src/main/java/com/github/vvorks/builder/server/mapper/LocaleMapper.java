/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface LocaleMapper extends MapperInterface<LocaleContent> {

	@Override
	public default List<LocaleContent> listAll() {
		return listContent(0, 0);
	}

	/**
	 * ロケールを挿入する
	 *
	 * @param content 挿入するロケール
	 * @return 処理成功の場合、真
	 */
	public boolean insert(LocaleContent content);

	/**
	 * ロケールを更新する
	 *
	 * @param content 更新するロケール
	 * @return 処理成功の場合、真
	 */
	public boolean update(LocaleContent content);

	/**
	 * ロケールを削除する
	 *
	 * @param content 削除するロケール
	 * @return 処理成功の場合、真
	 */
	public boolean delete(LocaleContent content);

	/**
	 * ロケールを取得する
	 *
	 * @param localeId ロケールID
	 * @return 取得したロケール
	 */
	public LocaleContent get(
		@Param("localeId") String localeId
	);

	/**
	 * 全てのロケールのサマリーを取得する
	 *
	 * @return ロケールのサマリー
	 */
	public LocaleSummary<LocaleContent> listSummary();

	/**
	 * 全てのロケールを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return ロケールのリスト
	 */
	public List<LocaleContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 所属プロジェクトを取得する
	 *
	 * @param content ロケール
	 * @return 所属プロジェクト
	 */
	public ProjectContent getOwner(LocaleContent content);

	/**
	 * 所属プロジェクトの候補サマリーを取得する
	 *
	 * @param content ロケール
	 * @param hint ヒント情報
	 * @return 所属プロジェクトの候補サマリー
	 */
	public ProjectSummary<ProjectSubject> listOwnerCandidateSummary(
		@Param("content") LocaleContent content,
		@Param("hint") String hint
	);

	/**
	 * 所属プロジェクトの候補一覧を取得する
	 *
	 * @param content ロケール
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 所属プロジェクトの候補一覧
	 */
	public List<ProjectSubject> listOwnerCandidateSubject(
		@Param("content") LocaleContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
