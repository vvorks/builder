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
	 * プロジェクトを挿入する
	 *
	 * @param content 挿入するプロジェクト
	 * @return 処理成功の場合、真
	 */
	public boolean insert(ProjectContent content);

	/**
	 * プロジェクトを更新する
	 *
	 * @param content 更新するプロジェクト
	 * @return 処理成功の場合、真
	 */
	public boolean update(ProjectContent content);

	/**
	 * プロジェクトを削除する
	 *
	 * @param content 削除するプロジェクト
	 * @return 処理成功の場合、真
	 */
	public boolean delete(ProjectContent content);

	/**
	 * プロジェクトを取得する
	 *
	 * @param projectId プロジェクトID
	 * @return 取得したプロジェクト
	 */
	public ProjectContent get(
		@Param("projectId") int projectId
	);

	/**
	 * 全てのプロジェクトのサマリーを取得する
	 *
	 * @return プロジェクトのサマリー
	 */
	public ProjectSummary<ProjectContent> listSummary();

	/**
	 * 全てのプロジェクトを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return プロジェクトのリスト
	 */
	public List<ProjectContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 名前に合致するプロジェクトのサマリーを取得する
	 *
	 * @param name name
	 * @return プロジェクトのサマリー
	 */
	public ProjectSummary<ProjectContent> listSummaryIfNameIs(
		@Param("name") String name
	);

	/**
	 * 名前に合致するプロジェクトを取得する
	 *
	 * @param name name
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return プロジェクトのリスト
	 */
	public List<ProjectContent> listContentIfNameIs(
		@Param("name") String name,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * クラス一覧のサマリーを取得する
	 *
	 * @param content プロジェクト
	 * @return クラス一覧のサマリー
	 */
	public ClassSummary<ClassContent> listClassesSummary(
		@Param("content") ProjectContent content
	);

	/**
	 * クラス一覧を取得する
	 *
	 * @param content プロジェクト
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
	 * 名前に合致するクラス一覧のサマリーを取得する
	 *
	 * @param content プロジェクト
	 * @return クラス一覧のサマリー
	 */
	public ClassSummary<ClassContent> listClassesSummaryIfNameIs(
		@Param("content") ProjectContent content,
		@Param("name") String name
	);

	/**
	 * 名前に合致するクラス一覧を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラス一覧
	 */
	public List<ClassContent> listClassesContentIfNameIs(
		@Param("content") ProjectContent content,
		@Param("name") String name,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 列挙一覧のサマリーを取得する
	 *
	 * @param content プロジェクト
	 * @return 列挙一覧のサマリー
	 */
	public EnumSummary<EnumContent> listEnumsSummary(
		@Param("content") ProjectContent content
	);

	/**
	 * 列挙一覧を取得する
	 *
	 * @param content プロジェクト
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
	 * 名前に合致する列挙一覧のサマリーを取得する
	 *
	 * @param content プロジェクト
	 * @return 列挙一覧のサマリー
	 */
	public EnumSummary<EnumContent> listEnumsSummaryIfNameIs(
		@Param("content") ProjectContent content,
		@Param("name") String name
	);

	/**
	 * 名前に合致する列挙一覧を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙一覧
	 */
	public List<EnumContent> listEnumsContentIfNameIs(
		@Param("content") ProjectContent content,
		@Param("name") String name,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * メッセージ一覧のサマリーを取得する
	 *
	 * @param content プロジェクト
	 * @return メッセージ一覧のサマリー
	 */
	public MessageSummary<MessageContent> listMessagesSummary(
		@Param("content") ProjectContent content
	);

	/**
	 * メッセージ一覧を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return メッセージ一覧
	 */
	public List<MessageContent> listMessagesContent(
		@Param("content") ProjectContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * リソース一覧のサマリーを取得する
	 *
	 * @param content プロジェクト
	 * @return リソース一覧のサマリー
	 */
	public ResourceSummary<ResourceContent> listResourcesSummary(
		@Param("content") ProjectContent content
	);

	/**
	 * リソース一覧を取得する
	 *
	 * @param content プロジェクト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return リソース一覧
	 */
	public List<ResourceContent> listResourcesContent(
		@Param("content") ProjectContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
