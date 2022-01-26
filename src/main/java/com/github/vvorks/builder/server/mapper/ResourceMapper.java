/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface ResourceMapper {

	/**
	 * リソースを挿入する
	 *
	 * @param content 挿入するリソース
	 * @return 処理成功の場合、真
	 */
	public boolean insert(ResourceContent content);

	/**
	 * リソースを更新する
	 *
	 * @param content 更新するリソース
	 * @return 処理成功の場合、真
	 */
	public boolean update(ResourceContent content);

	/**
	 * リソースを削除する
	 *
	 * @param content 削除するリソース
	 * @return 処理成功の場合、真
	 */
	public boolean delete(ResourceContent content);

	/**
	 * リソースを取得する
	 *
	 * @param resourceId resourceId
	 * @return 取得したリソース
	 */
	public ResourceContent get(
		@Param("resourceId") int resourceId
	);

	/**
	 * 全てのリソースのサマリーを取得する
	 *
	 * @return リソースのサマリー
	 */
	public ResourceSummary<ResourceContent> listSummary();

	/**
	 * 全てのリソースを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return リソースのリスト
	 */
	public List<ResourceContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * ownerを取得する
	 *
	 * @param content リソース
	 * @return owner
	 */
	public ProjectContent getOwner(ResourceContent content);

	/**
	 * ownerの候補サマリーを取得する
	 *
	 * @param content リソース
	 * @param hint ヒント情報
	 * @return ownerの候補サマリー
	 */
	public ProjectSummary<ProjectSubject> listOwnerCandidateSummary(
		@Param("content") ResourceContent content,
		@Param("hint") String hint
	);

	/**
	 * ownerの候補一覧を取得する
	 *
	 * @param content リソース
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return ownerの候補一覧
	 */
	public List<ProjectSubject> listOwnerCandidateSubject(
		@Param("content") ResourceContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * variationsのサマリーを取得する
	 *
	 * @param content リソース
	 * @return variationsのサマリー
	 */
	public LocalizedResourceSummary<LocalizedResourceContent> listVariationsSummary(
		@Param("content") ResourceContent content
	);

	/**
	 * variationsを取得する
	 *
	 * @param content リソース
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return variations
	 */
	public List<LocalizedResourceContent> listVariationsContent(
		@Param("content") ResourceContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
