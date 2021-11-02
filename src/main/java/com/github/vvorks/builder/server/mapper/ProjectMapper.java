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
	 * @param content 挿入するプロジェクト型
	 * @return 処理成功の場合、真
	 */
	public boolean insert(ProjectContent content);

	/**
	 * プロジェクト型を更新する
	 *
	 * @param content 更新するプロジェクト型
	 * @return 処理成功の場合、真
	 */
	public boolean update(ProjectContent content);

	/**
	 * プロジェクト型を削除する
	 *
	 * @param content 削除するプロジェクト型
	 * @return 処理成功の場合、真
	 */
	public boolean delete(ProjectContent content);

	/**
	 * プロジェクト型を取得する
	 *
	 * @param projectId プロジェクトID
	 * @return 取得したプロジェクト型
	 */
	public ProjectContent get(
		@Param("projectId") int projectId
	);

	/**
	 * 全てのプロジェクト型のサマリーを取得する
	 *
	 * @return プロジェクト型のサマリー
	 */
	public ProjectSummary listSummary();

	/**
	 * 全てのプロジェクト型を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return プロジェクト型のリスト
	 */
	public List<ProjectContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * nameIsに合致するプロジェクト型のサマリーを取得する
	 *
	 * @return プロジェクト型のサマリー
	 */
	public ProjectSummary listIfNameIsSummary(
		@Param("name") String name
	);

	/**
	 * nameIsに合致するプロジェクト型を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return プロジェクト型のリスト
	 */
	public List<ProjectContent> listIfNameIsContent(
		@Param("name") String name,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * クラス一覧のサマリーを取得する
	 *
	 * @param content プロジェクト型
	 * @return クラス一覧のサマリー
	 */
	public ClassSummary listClassesSummary(
		@Param("content") ProjectContent content
	);

	/**
	 * クラス一覧を取得する
	 *
	 * @param content プロジェクト型
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラス一覧
	 */
	public List<ClassContent> listClassesContent(
		@Param("content") ProjectContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * nameIsに合致するクラス一覧のサマリーを取得する
	 *
	 * @param content プロジェクト型
	 * @return クラス一覧のサマリー
	 */
	public ClassSummary listClassesIfNameIsSummary(
		@Param("content") ProjectContent content,
		@Param("name") String name
	);

	/**
	 * nameIsに合致するクラス一覧を取得する
	 *
	 * @param content プロジェクト型
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラス一覧
	 */
	public List<ClassContent> listClassesIfNameIsContent(
		@Param("content") ProjectContent content,
		@Param("name") String name,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 列挙一覧のサマリーを取得する
	 *
	 * @param content プロジェクト型
	 * @return 列挙一覧のサマリー
	 */
	public EnumSummary listEnumsSummary(
		@Param("content") ProjectContent content
	);

	/**
	 * 列挙一覧を取得する
	 *
	 * @param content プロジェクト型
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙一覧
	 */
	public List<EnumContent> listEnumsContent(
		@Param("content") ProjectContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * nameIsに合致する列挙一覧のサマリーを取得する
	 *
	 * @param content プロジェクト型
	 * @return 列挙一覧のサマリー
	 */
	public EnumSummary listEnumsIfNameIsSummary(
		@Param("content") ProjectContent content,
		@Param("name") String name
	);

	/**
	 * nameIsに合致する列挙一覧を取得する
	 *
	 * @param content プロジェクト型
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙一覧
	 */
	public List<EnumContent> listEnumsIfNameIsContent(
		@Param("content") ProjectContent content,
		@Param("name") String name,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
