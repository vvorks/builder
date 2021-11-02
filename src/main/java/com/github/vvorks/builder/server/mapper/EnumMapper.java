/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface EnumMapper {

	/**
	 * 列挙を挿入する
	 *
	 * @param content 挿入する列挙
	 * @return 処理成功の場合、真
	 */
	public boolean insert(EnumContent content);

	/**
	 * 列挙を更新する
	 *
	 * @param content 更新する列挙
	 * @return 処理成功の場合、真
	 */
	public boolean update(EnumContent content);

	/**
	 * 列挙を削除する
	 *
	 * @param content 削除する列挙
	 * @return 処理成功の場合、真
	 */
	public boolean delete(EnumContent content);

	/**
	 * 列挙を取得する
	 *
	 * @param enumId 列挙ID
	 * @return 取得した列挙
	 */
	public EnumContent get(
		@Param("enumId") int enumId
	);

	/**
	 * 全ての列挙のサマリーを取得する
	 *
	 * @return 列挙のサマリー
	 */
	public EnumSummary listSummary();

	/**
	 * 全ての列挙を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙のリスト
	 */
	public List<EnumContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * nameIsに合致する列挙のサマリーを取得する
	 *
	 * @return 列挙のサマリー
	 */
	public EnumSummary listIfNameIsSummary(
		@Param("name") String name
	);

	/**
	 * nameIsに合致する列挙を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙のリスト
	 */
	public List<EnumContent> listIfNameIsContent(
		@Param("name") String name,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 所属プロジェクトを取得する
	 *
	 * @param content 列挙
	 * @return 所属プロジェクト
	 */
	public ProjectContent getOwner(EnumContent content);

	/**
	 * 列挙値一覧のサマリーを取得する
	 *
	 * @param content 列挙
	 * @return 列挙値一覧のサマリー
	 */
	public EnumValueSummary listValuesSummary(
		@Param("content") EnumContent content
	);

	/**
	 * 列挙値一覧を取得する
	 *
	 * @param content 列挙
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙値一覧
	 */
	public List<EnumValueContent> listValuesContent(
		@Param("content") EnumContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
