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
	 * @param dto 挿入する列挙
	 * @return 処理成功の場合、真
	 */
	public boolean insert(EnumDto dto);

	/**
	 * 列挙を更新する
	 *
	 * @param dto 更新する列挙
	 * @return 処理成功の場合、真
	 */
	public boolean update(EnumDto dto);

	/**
	 * 列挙を削除する
	 *
	 * @param dto 削除する列挙
	 * @return 処理成功の場合、真
	 */
	public boolean delete(EnumDto dto);

	/**
	 * 列挙を取得する
	 *
	 * @param enumId 列挙ID
	 * @return 取得した列挙
	 */
	public EnumDto get(
		@Param("enumId") int enumId
	);

	/**
	 * 全ての列挙を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙のリスト
	 */
	public List<EnumDto> list(
		@Param("offset") int offset,
		@Param("limit") int limit);

	/**
	 * 指定した条件に合致する列挙を取得する
	 *
	 * @param enumName 列挙名
	 * @param title タイトル
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙のリスト
	 */
	public List<EnumDto> find(
		@Param("enumName") String enumName,
		@Param("title") String title,
		@Param("offset") int offset,
		@Param("limit") int limit);

	/**
	 * 所属プロジェクトを取得する
	 *
	 * @param dto 列挙
	 * @return 所属プロジェクト
	 */
	public ProjectDto getOwner(EnumDto dto);

	/**
	 * 列挙値一覧を取得する
	 *
	 * @param dto 列挙
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙値一覧
	 */
	public List<EnumValueDto> listValues(
		@Param("dto") EnumDto dto,
		@Param("offset") int offset,
		@Param("limit") int limit);

	/**
	 * 指定した条件に合致する列挙値一覧を取得する
	 *
	 * @param dto 列挙
	 * @param code 列挙コード
	 * @param title タイトル
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙値一覧
	 */
	public List<EnumValueDto> findValues(
		@Param("dto") EnumDto dto,
		@Param("code") Integer code,
		@Param("title") String title,
		@Param("offset") int offset,
		@Param("limit") int limit);

}
