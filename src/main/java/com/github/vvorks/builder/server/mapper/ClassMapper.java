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
	 * @return 取得したクラス
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラスのリスト
	 */
	public List<ClassDto> listAll(
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

}
