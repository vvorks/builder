/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface StyleMapper extends BuilderMapper<StyleContent> {

	@Override
	public default List<StyleContent> listAll() {
		return listContent(0, 0);
	}

	/**
	 * スタイルを挿入する
	 *
	 * @param content 挿入するスタイル
	 * @return 処理成功の場合、真
	 */
	public boolean insert(StyleContent content);

	/**
	 * スタイルを更新する
	 *
	 * @param content 更新するスタイル
	 * @return 処理成功の場合、真
	 */
	public boolean update(StyleContent content);

	/**
	 * スタイルを削除する
	 *
	 * @param content 削除するスタイル
	 * @return 処理成功の場合、真
	 */
	public boolean delete(StyleContent content);

	/**
	 * スタイルをその従属要素も含めて削除する
	 *
	 * @param content 削除するスタイル
	 * @return 処理成功の場合、真
	 */
	public default boolean deleteFull(StyleContent content) {
		return delete(content);
	}

	/**
	 * スタイルを取得する
	 *
	 * @param styleId スタイルID
	 * @return 取得したスタイル
	 */
	public StyleContent get(
		@Param("styleId") int styleId
	);

	/**
	 * 全てのスタイルのサマリーを取得する
	 *
	 * @return スタイルのサマリー
	 */
	public StyleSummary<StyleContent> listSummary();

	/**
	 * 全てのスタイルを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return スタイルのリスト
	 */
	public List<StyleContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 所属プロジェクトを取得する
	 *
	 * @param content スタイル
	 * @return 所属プロジェクト
	 */
	public ProjectContent getOwner(StyleContent content);

	/**
	 * 所属プロジェクトの候補サマリーを取得する
	 *
	 * @param content スタイル
	 * @param hint ヒント情報
	 * @return 所属プロジェクトの候補サマリー
	 */
	public ProjectSummary<ProjectSubject> listOwnerCandidateSummary(
		@Param("content") StyleContent content,
		@Param("hint") String hint
	);

	/**
	 * 所属プロジェクトの候補一覧を取得する
	 *
	 * @param content スタイル
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 所属プロジェクトの候補一覧
	 */
	public List<ProjectSubject> listOwnerCandidateSubject(
		@Param("content") StyleContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 基底スタイルを取得する
	 *
	 * @param content スタイル
	 * @return 基底スタイル
	 */
	public StyleContent getParent(StyleContent content);

	/**
	 * 基底スタイルの候補サマリーを取得する
	 *
	 * @param content スタイル
	 * @param hint ヒント情報
	 * @return 基底スタイルの候補サマリー
	 */
	public StyleSummary<StyleSubject> listParentCandidateSummary(
		@Param("content") StyleContent content,
		@Param("hint") String hint
	);

	/**
	 * 基底スタイルの候補一覧を取得する
	 *
	 * @param content スタイル
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 基底スタイルの候補一覧
	 */
	public List<StyleSubject> listParentCandidateSubject(
		@Param("content") StyleContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 派生スタイルのサマリーを取得する
	 *
	 * @param content スタイル
	 * @return 派生スタイルのサマリー
	 */
	public StyleSummary<StyleContent> listChildrenSummary(
		@Param("content") StyleContent content
	);

	/**
	 * 派生スタイルを取得する
	 *
	 * @param content スタイル
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 派生スタイル
	 */
	public List<StyleContent> listChildrenContent(
		@Param("content") StyleContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
