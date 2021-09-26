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
	 * @param dto 挿入するフィールド
	 * @return 処理成功の場合、真
	 */
	public boolean insert(FieldDto dto);

	/**
	 * フィールドを更新する
	 *
	 * @param dto 更新するフィールド
	 * @return 処理成功の場合、真
	 */
	public boolean update(FieldDto dto);

	/**
	 * フィールドを削除する
	 *
	 * @param dto 削除するフィールド
	 * @return 処理成功の場合、真
	 */
	public boolean delete(FieldDto dto);

	/**
	 * フィールドを取得する
	 *
	 * @param fieldId フィールドID
	 * @return 取得したフィールド
	 */
	public FieldDto get(
		@Param("fieldId") int fieldId
	);

	/**
	 * 全てのフィールドを取得する
	 *
	 * @return 取得したフィールド
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールドのリスト
	 */
	public List<FieldDto> listAll(
		@Param("offset") int offset,
		@Param("limit") int limit);

	/**
	 * 所属クラスを取得する
	 *
	 * @param dto フィールド
	 * @return 所属クラス
	 */
	public ClassDto getOwner(FieldDto dto);

}
