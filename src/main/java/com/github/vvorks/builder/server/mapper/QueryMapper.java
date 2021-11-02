/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface QueryMapper {

	/**
	 * クエリーを挿入する
	 *
	 * @param content 挿入するクエリー
	 * @return 処理成功の場合、真
	 */
	public boolean insert(QueryContent content);

	/**
	 * クエリーを更新する
	 *
	 * @param content 更新するクエリー
	 * @return 処理成功の場合、真
	 */
	public boolean update(QueryContent content);

	/**
	 * クエリーを削除する
	 *
	 * @param content 削除するクエリー
	 * @return 処理成功の場合、真
	 */
	public boolean delete(QueryContent content);

	/**
	 * クエリーを取得する
	 *
	 * @param queryId クエリーID
	 * @return 取得したクエリー
	 */
	public QueryContent get(
		@Param("queryId") int queryId
	);

	/**
	 * 全てのクエリーのサマリーを取得する
	 *
	 * @return クエリーのサマリー
	 */
	public QuerySummary listSummary();

	/**
	 * 全てのクエリーを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クエリーのリスト
	 */
	public List<QueryContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 所属クラスを取得する
	 *
	 * @param content クエリー
	 * @return 所属クラス
	 */
	public ClassContent getOwner(QueryContent content);

}
