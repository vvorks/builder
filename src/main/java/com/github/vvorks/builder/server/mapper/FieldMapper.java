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
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールドのリスト
	 */
	public List<FieldDto> list(
		@Param("offset") int offset,
		@Param("limit") int limit);

	/**
	 * 指定した条件に合致するフィールドを取得する
	 *
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
	 * @param title タイトル
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールドのリスト
	 */
	public List<FieldDto> find(
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
		@Param("title") String title,
		@Param("offset") int offset,
		@Param("limit") int limit);

	/**
	 * 所属クラスを取得する
	 *
	 * @param dto フィールド
	 * @return 所属クラス
	 */
	public ClassDto getOwner(FieldDto dto);

	/**
	 * クラス参照先を取得する
	 *
	 * @param dto フィールド
	 * @return クラス参照先
	 */
	public ClassDto getCref(FieldDto dto);

	/**
	 * 列挙参照先を取得する
	 *
	 * @param dto フィールド
	 * @return 列挙参照先
	 */
	public EnumDto getEref(FieldDto dto);

	/**
	 * フィールド参照先を取得する
	 *
	 * @param dto フィールド
	 * @return フィールド参照先
	 */
	public FieldDto getFref(FieldDto dto);

}
