/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface FieldMapper {

	/**
	 * フィールドを挿入する
	 *
	 * @param content 挿入するフィールド
	 * @return 処理成功の場合、真
	 */
	public boolean insert(FieldContent content);

	/**
	 * フィールドを更新する
	 *
	 * @param content 更新するフィールド
	 * @return 処理成功の場合、真
	 */
	public boolean update(FieldContent content);

	/**
	 * フィールドを削除する
	 *
	 * @param content 削除するフィールド
	 * @return 処理成功の場合、真
	 */
	public boolean delete(FieldContent content);

	/**
	 * フィールドを取得する
	 *
	 * @param fieldId フィールドID
	 * @return 取得したフィールド
	 */
	public FieldContent get(
		@Param("fieldId") int fieldId
	);

	/**
	 * 全てのフィールドのサマリーを取得する
	 *
	 * @return フィールドのサマリー
	 */
	public FieldSummary listSummary();

	/**
	 * 全てのフィールドを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールドのリスト
	 */
	public List<FieldContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * nameIsに合致するフィールドのサマリーを取得する
	 *
	 * @return フィールドのサマリー
	 */
	public FieldSummary listIfNameIsSummary(
		@Param("name") String name
	);

	/**
	 * nameIsに合致するフィールドを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールドのリスト
	 */
	public List<FieldContent> listIfNameIsContent(
		@Param("name") String name,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 所属クラスを取得する
	 *
	 * @param content フィールド
	 * @return 所属クラス
	 */
	public ClassContent getOwner(FieldContent content);

	/**
	 * クラス参照先を取得する
	 *
	 * @param content フィールド
	 * @return クラス参照先
	 */
	public ClassContent getCref(FieldContent content);

	/**
	 * 列挙参照先を取得する
	 *
	 * @param content フィールド
	 * @return 列挙参照先
	 */
	public EnumContent getEref(FieldContent content);

	/**
	 * フィールド参照先を取得する
	 *
	 * @param content フィールド
	 * @return フィールド参照先
	 */
	public FieldContent getFref(FieldContent content);

}
