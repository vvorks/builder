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
	 * @param dto 挿入するクラス
	 * @return 処理成功の場合、真
	 */
	public boolean insert(ClassDto dto);

	/**
	 * クラスを更新する
	 *
	 * @param dto 更新するクラス
	 * @return 処理成功の場合、真
	 */
	public boolean update(ClassDto dto);

	/**
	 * クラスを削除する
	 *
	 * @param dto 削除するクラス
	 * @return 処理成功の場合、真
	 */
	public boolean delete(ClassDto dto);

	/**
	 * クラスを取得する
	 *
	 * @param classId クラスID
	 * @return 取得したクラス
	 */
	public ClassDto get(
		@Param("classId") int classId
	);

	/**
	 * 全てのクラスを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラスのリスト
	 */
	public List<ClassDto> list(
		@Param("offset") int offset,
		@Param("limit") int limit);

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
	public List<ClassDto> find(
		@Param("ownerProjectId") Integer ownerProjectId,
		@Param("className") String className,
		@Param("title") String title,
		@Param("offset") int offset,
		@Param("limit") int limit);

	/**
	 * 所属プロジェクトを取得する
	 *
	 * @param dto クラス
	 * @return 所属プロジェクト
	 */
	public ProjectDto getOwner(ClassDto dto);

	/**
	 * フィールド一覧を取得する
	 *
	 * @param dto クラス
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールド一覧
	 */
	public List<FieldDto> listFields(
		@Param("dto") ClassDto dto,
		@Param("offset") int offset,
		@Param("limit") int limit);

	/**
	 * 指定した条件に合致するフィールド一覧を取得する
	 *
	 * @param dto クラス
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
	 * @return フィールド一覧
	 */
	public List<FieldDto> findFields(
		@Param("dto") ClassDto dto,
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

}
