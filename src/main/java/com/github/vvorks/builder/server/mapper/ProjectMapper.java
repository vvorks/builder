/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface ProjectMapper {

	/**
	 * プロジェクト型を挿入する
	 *
	 * @param dto 挿入するプロジェクト型
	 * @return 処理成功の場合、真
	 */
	public boolean insert(ProjectDto dto);

	/**
	 * プロジェクト型を更新する
	 *
	 * @param dto 更新するプロジェクト型
	 * @return 処理成功の場合、真
	 */
	public boolean update(ProjectDto dto);

	/**
	 * プロジェクト型を削除する
	 *
	 * @param dto 削除するプロジェクト型
	 * @return 処理成功の場合、真
	 */
	public boolean delete(ProjectDto dto);

	/**
	 * プロジェクト型を取得する
	 *
	 * @param projectId プロジェクトID
	 * @return 取得したプロジェクト型
	 */
	public ProjectDto get(
		@Param("projectId") int projectId
	);

	/**
	 * 全てのプロジェクト型を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return プロジェクト型のリスト
	 */
	public List<ProjectDto> list(
		@Param("offset") int offset,
		@Param("limit") int limit);

	/**
	 * 指定した条件に合致するプロジェクト型を取得する
	 *
	 * @param projectName プロジェクト名
	 * @param title タイトル
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return プロジェクト型のリスト
	 */
	public List<ProjectDto> find(
		@Param("projectName") String projectName,
		@Param("title") String title,
		@Param("offset") int offset,
		@Param("limit") int limit);

	/**
	 * クラス一覧を取得する
	 *
	 * @param dto プロジェクト型
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラス一覧
	 */
	public List<ClassDto> listClasses(
		@Param("dto") ProjectDto dto,
		@Param("offset") int offset,
		@Param("limit") int limit);

	/**
	 * 指定した条件に合致するクラス一覧を取得する
	 *
	 * @param dto プロジェクト型
	 * @param className クラス名
	 * @param title タイトル
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラス一覧
	 */
	public List<ClassDto> findClasses(
		@Param("dto") ProjectDto dto,
		@Param("className") String className,
		@Param("title") String title,
		@Param("offset") int offset,
		@Param("limit") int limit);

	/**
	 * 列挙一覧を取得する
	 *
	 * @param dto プロジェクト型
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙一覧
	 */
	public List<EnumDto> listEnums(
		@Param("dto") ProjectDto dto,
		@Param("offset") int offset,
		@Param("limit") int limit);

	/**
	 * 指定した条件に合致する列挙一覧を取得する
	 *
	 * @param dto プロジェクト型
	 * @param enumName 列挙名
	 * @param title タイトル
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙一覧
	 */
	public List<EnumDto> findEnums(
		@Param("dto") ProjectDto dto,
		@Param("enumName") String enumName,
		@Param("title") String title,
		@Param("offset") int offset,
		@Param("limit") int limit);

}
