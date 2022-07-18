/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface EnumValueMapper extends BuilderMapper<EnumValueContent> {

	@Override
	public default List<EnumValueContent> listAll() {
		return listContent(0, 0);
	}

	/**
	 * 列挙値を挿入する
	 *
	 * @param content 挿入する列挙値
	 * @return 処理成功の場合、真
	 */
	public boolean insert(EnumValueContent content);

	/**
	 * 列挙値を更新する
	 *
	 * @param content 更新する列挙値
	 * @return 処理成功の場合、真
	 */
	public boolean update(EnumValueContent content);

	/**
	 * 列挙値を削除する
	 *
	 * @param content 削除する列挙値
	 * @return 処理成功の場合、真
	 */
	public boolean delete(EnumValueContent content);

	/**
	 * 列挙値を取得する
	 *
	 * @param ownerEnumId 所属列挙の列挙ID
	 * @param valueId 列挙名
	 * @return 取得した列挙値
	 */
	public EnumValueContent get(
		@Param("ownerEnumId") int ownerEnumId, 
		@Param("valueId") String valueId
	);

	/**
	 * 全ての列挙値のサマリーを取得する
	 *
	 * @return 列挙値のサマリー
	 */
	public EnumValueSummary<EnumValueContent> listSummary();

	/**
	 * 全ての列挙値を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙値のリスト
	 */
	public List<EnumValueContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 所属列挙を取得する
	 *
	 * @param content 列挙値
	 * @return 所属列挙
	 */
	public EnumContent getOwner(EnumValueContent content);

	/**
	 * 所属列挙の候補サマリーを取得する
	 *
	 * @param content 列挙値
	 * @param hint ヒント情報
	 * @return 所属列挙の候補サマリー
	 */
	public EnumSummary<EnumSubject> listOwnerCandidateSummary(
		@Param("content") EnumValueContent content,
		@Param("hint") String hint
	);

	/**
	 * 所属列挙の候補一覧を取得する
	 *
	 * @param content 列挙値
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 所属列挙の候補一覧
	 */
	public List<EnumSubject> listOwnerCandidateSubject(
		@Param("content") EnumValueContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * I18n一覧のサマリーを取得する
	 *
	 * @param content 列挙値
	 * @return I18n一覧のサマリー
	 */
	public EnumValueI18nSummary<EnumValueI18nContent> listI18nsSummary(
		@Param("content") EnumValueContent content
	);

	/**
	 * I18n一覧を取得する
	 *
	 * @param content 列挙値
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return I18n一覧
	 */
	public List<EnumValueI18nContent> listI18nsContent(
		@Param("content") EnumValueContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 列挙値が保持するI18n一覧を全て削除する
	 *
	 * @param content 列挙値
	 */
	public boolean deleteI18nsAll(
		@Param("content") EnumValueContent content
	);

}
