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
	 * 指定した条件に合致するクラスのサマリーを取得する
	 *
	 * @param ownerProjectId 所属プロジェクトのプロジェクトID
	 * @param className クラス名
	 * @param title タイトル
	 * @return クラスのサマリー
	 */
	public ClassSummary findSummary(
		@Param("ownerProjectId") Integer ownerProjectId, 
		@Param("className") String className, 
		@Param("title") String title
	);

	/**
	 * 指定した条件に合致するクラスを取得する
	 *
	 * @param ownerProjectId 所属プロジェクトのプロジェクトID
	 * @param className クラス名
	 * @param title タイトル
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラスのリスト
	 */
	public List<ClassContent> findContent(
		@Param("ownerProjectId") Integer ownerProjectId,
		@Param("className") String className,
		@Param("title") String title,
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
	 * isPkに合致するフィールド一覧のサマリーを取得する
	 *
	 * @param content クラス
	 * @return フィールド一覧のサマリー
	 */
	public FieldSummary listFieldsIfIsPkSummary(
		@Param("content") ClassContent content
	);

	/**
	 * isPkに合致するフィールド一覧を取得する
	 *
	 * @param content クラス
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールド一覧
	 */
	public List<FieldContent> listFieldsIfIsPkContent(
		@Param("content") ClassContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 指定した条件に合致するフィールド一覧のサマリーを取得する
	 *
	 * @param content クラス
	 * @param ownerClassId 所属クラスのクラスID
	 * @param fieldName フィールド名
	 * @param type フィールド型
	 * @param width フィールド型の幅
	 * @param scale フィールド型精度
	 * @param crefClassId クラス参照先のクラスID
	 * @param erefEnumId 列挙参照先の列挙ID
	 * @param frefFieldId フィールド参照先のフィールドID
	 * @param pk プライマリキー
	 * @param nullable NULL許容
	 * @param needsSum needsSum
	 * @param needsAvg needsAvg
	 * @param needsMax needsMax
	 * @param needsMin needsMin
	 * @param title タイトル
	 * @return フィールド一覧のサマリー
	 */
	public FieldSummary findFieldsSummary(
		@Param("content") ClassContent content,
		@Param("ownerClassId") Integer ownerClassId, 
		@Param("fieldName") String fieldName, 
		@Param("type") DataType type, 
		@Param("width") Integer width, 
		@Param("scale") Integer scale, 
		@Param("crefClassId") Integer crefClassId, 
		@Param("erefEnumId") Integer erefEnumId, 
		@Param("frefFieldId") Integer frefFieldId, 
		@Param("pk") Boolean pk, 
		@Param("nullable") Boolean nullable, 
		@Param("needsSum") Boolean needsSum, 
		@Param("needsAvg") Boolean needsAvg, 
		@Param("needsMax") Boolean needsMax, 
		@Param("needsMin") Boolean needsMin, 
		@Param("title") String title
	);

	/**
	 * 指定した条件に合致するフィールド一覧を取得する
	 *
	 * @param content クラス
	 * @param ownerClassId 所属クラスのクラスID
	 * @param fieldName フィールド名
	 * @param type フィールド型
	 * @param width フィールド型の幅
	 * @param scale フィールド型精度
	 * @param crefClassId クラス参照先のクラスID
	 * @param erefEnumId 列挙参照先の列挙ID
	 * @param frefFieldId フィールド参照先のフィールドID
	 * @param pk プライマリキー
	 * @param nullable NULL許容
	 * @param needsSum needsSum
	 * @param needsAvg needsAvg
	 * @param needsMax needsMax
	 * @param needsMin needsMin
	 * @param title タイトル
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールド一覧
	 */
	public List<FieldContent> findFieldsContent(
		@Param("content") ClassContent content,
		@Param("ownerClassId") Integer ownerClassId,
		@Param("fieldName") String fieldName,
		@Param("type") DataType type,
		@Param("width") Integer width,
		@Param("scale") Integer scale,
		@Param("crefClassId") Integer crefClassId,
		@Param("erefEnumId") Integer erefEnumId,
		@Param("frefFieldId") Integer frefFieldId,
		@Param("pk") Boolean pk,
		@Param("nullable") Boolean nullable,
		@Param("needsSum") Boolean needsSum,
		@Param("needsAvg") Boolean needsAvg,
		@Param("needsMax") Boolean needsMax,
		@Param("needsMin") Boolean needsMin,
		@Param("title") String title,
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

	/**
	 * 指定した条件に合致するqueriesのサマリーを取得する
	 *
	 * @param content クラス
	 * @param ownerClassId 所属クラスのクラスID
	 * @param queryName クエリー名
	 * @param filter 抽出条件
	 * @param order ソート条件
	 * @param title タイトル
	 * @return queriesのサマリー
	 */
	public QuerySummary findQueriesSummary(
		@Param("content") ClassContent content,
		@Param("ownerClassId") Integer ownerClassId, 
		@Param("queryName") String queryName, 
		@Param("filter") String filter, 
		@Param("order") String order, 
		@Param("title") String title
	);

	/**
	 * 指定した条件に合致するqueriesを取得する
	 *
	 * @param content クラス
	 * @param ownerClassId 所属クラスのクラスID
	 * @param queryName クエリー名
	 * @param filter 抽出条件
	 * @param order ソート条件
	 * @param title タイトル
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return queries
	 */
	public List<QueryContent> findQueriesContent(
		@Param("content") ClassContent content,
		@Param("ownerClassId") Integer ownerClassId,
		@Param("queryName") String queryName,
		@Param("filter") String filter,
		@Param("order") String order,
		@Param("title") String title,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
