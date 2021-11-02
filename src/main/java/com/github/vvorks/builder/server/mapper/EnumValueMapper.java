/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface EnumValueMapper {

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
	 * @param valueId 列挙名
	 * @param ownerEnumId 所属列挙の列挙ID
	 * @return 取得した列挙値
	 */
	public EnumValueContent get(
		@Param("valueId") String valueId, 
		@Param("ownerEnumId") int ownerEnumId
	);

	/**
	 * 全ての列挙値のサマリーを取得する
	 *
	 * @return 列挙値のサマリー
	 */
	public EnumValueSummary listSummary();

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

}
