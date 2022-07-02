/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface ClassMapper extends BuilderMapper<ClassContent> {

	@Override
	public default List<ClassContent> listAll() {
		return listContent(0, 0);
	}

	/**
	 * クラスを挿入する
	 *
	 * @param content 挿入するクラス
	 * @return 処理成功の場合、真
	 */
	public boolean insert(ClassContent content);

	/**
	 * クラスを更新する
	 *
	 * @param content 更新するクラス
	 * @return 処理成功の場合、真
	 */
	public boolean update(ClassContent content);

	/**
	 * クラスを削除する
	 *
	 * @param content 削除するクラス
	 * @return 処理成功の場合、真
	 */
	public boolean delete(ClassContent content);

	/**
	 * クラスを取得する
	 *
	 * @param classId クラスID
	 * @return 取得したクラス
	 */
	public ClassContent get(
		@Param("classId") int classId
	);

	/**
	 * 全てのクラスのサマリーを取得する
	 *
	 * @return クラスのサマリー
	 */
	public ClassSummary<ClassContent> listSummary();

	/**
	 * 全てのクラスを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラスのリスト
	 */
	public List<ClassContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 名前に合致するクラスのサマリーを取得する
	 *
	 * @param name name
	 * @return クラスのサマリー
	 */
	public ClassSummary<ClassContent> listSummaryIfNameIs(
		@Param("name") String name
	);

	/**
	 * 名前に合致するクラスを取得する
	 *
	 * @param name name
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラスのリスト
	 */
	public List<ClassContent> listContentIfNameIs(
		@Param("name") String name,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 所属プロジェクトを取得する
	 *
	 * @param content クラス
	 * @return 所属プロジェクト
	 */
	public ProjectContent getOwner(ClassContent content);

	/**
	 * 所属プロジェクトの候補サマリーを取得する
	 *
	 * @param content クラス
	 * @param hint ヒント情報
	 * @return 所属プロジェクトの候補サマリー
	 */
	public ProjectSummary<ProjectSubject> listOwnerCandidateSummary(
		@Param("content") ClassContent content,
		@Param("hint") String hint
	);

	/**
	 * 所属プロジェクトの候補一覧を取得する
	 *
	 * @param content クラス
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 所属プロジェクトの候補一覧
	 */
	public List<ProjectSubject> listOwnerCandidateSubject(
		@Param("content") ClassContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * フィールド一覧のサマリーを取得する
	 *
	 * @param content クラス
	 * @return フィールド一覧のサマリー
	 */
	public FieldSummary<FieldContent> listFieldsSummary(
		@Param("content") ClassContent content
	);

	/**
	 * フィールド一覧を取得する
	 *
	 * @param content クラス
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールド一覧
	 */
	public List<FieldContent> listFieldsContent(
		@Param("content") ClassContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 名前に合致するフィールド一覧のサマリーを取得する
	 *
	 * @param content クラス
	 * @return フィールド一覧のサマリー
	 */
	public FieldSummary<FieldContent> listFieldsSummaryIfNameIs(
		@Param("content") ClassContent content,
		@Param("name") String name
	);

	/**
	 * 名前に合致するフィールド一覧を取得する
	 *
	 * @param content クラス
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールド一覧
	 */
	public List<FieldContent> listFieldsContentIfNameIs(
		@Param("content") ClassContent content,
		@Param("name") String name,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * クエリー一覧のサマリーを取得する
	 *
	 * @param content クラス
	 * @return クエリー一覧のサマリー
	 */
	public QuerySummary<QueryContent> listQueriesSummary(
		@Param("content") ClassContent content
	);

	/**
	 * クエリー一覧を取得する
	 *
	 * @param content クラス
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クエリー一覧
	 */
	public List<QueryContent> listQueriesContent(
		@Param("content") ClassContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * I18n一覧のサマリーを取得する
	 *
	 * @param content クラス
	 * @return I18n一覧のサマリー
	 */
	public ClassI18nSummary<ClassI18nContent> listI18nsSummary(
		@Param("content") ClassContent content
	);

	/**
	 * I18n一覧を取得する
	 *
	 * @param content クラス
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return I18n一覧
	 */
	public List<ClassI18nContent> listI18nsContent(
		@Param("content") ClassContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
