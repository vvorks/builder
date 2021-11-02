/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface ClassMapper {

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
	public ClassSummary listSummary();

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
	 * nameIsに合致するクラスのサマリーを取得する
	 *
	 * @return クラスのサマリー
	 */
	public ClassSummary listIfNameIsSummary(
		@Param("name") String name
	);

	/**
	 * nameIsに合致するクラスを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラスのリスト
	 */
	public List<ClassContent> listIfNameIsContent(
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
	 * フィールド一覧のサマリーを取得する
	 *
	 * @param content クラス
	 * @return フィールド一覧のサマリー
	 */
	public FieldSummary listFieldsSummary(
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
	 * nameIsに合致するフィールド一覧のサマリーを取得する
	 *
	 * @param content クラス
	 * @return フィールド一覧のサマリー
	 */
	public FieldSummary listFieldsIfNameIsSummary(
		@Param("content") ClassContent content,
		@Param("name") String name
	);

	/**
	 * nameIsに合致するフィールド一覧を取得する
	 *
	 * @param content クラス
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールド一覧
	 */
	public List<FieldContent> listFieldsIfNameIsContent(
		@Param("content") ClassContent content,
		@Param("name") String name,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * queriesのサマリーを取得する
	 *
	 * @param content クラス
	 * @return queriesのサマリー
	 */
	public QuerySummary listQueriesSummary(
		@Param("content") ClassContent content
	);

	/**
	 * queriesを取得する
	 *
	 * @param content クラス
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return queries
	 */
	public List<QueryContent> listQueriesContent(
		@Param("content") ClassContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
