/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface LocalizedResourceMapper {

	/**
	 * ローカライズドリソースを挿入する
	 *
	 * @param content 挿入するローカライズドリソース
	 * @return 処理成功の場合、真
	 */
	public boolean insert(LocalizedResourceContent content);

	/**
	 * ローカライズドリソースを更新する
	 *
	 * @param content 更新するローカライズドリソース
	 * @return 処理成功の場合、真
	 */
	public boolean update(LocalizedResourceContent content);

	/**
	 * ローカライズドリソースを削除する
	 *
	 * @param content 削除するローカライズドリソース
	 * @return 処理成功の場合、真
	 */
	public boolean delete(LocalizedResourceContent content);

	/**
	 * ローカライズドリソースを取得する
	 *
	 * @param ownerResourceId 所属リソースのリソースID
	 * @param locale ロケール
	 * @return 取得したローカライズドリソース
	 */
	public LocalizedResourceContent get(
		@Param("ownerResourceId") int ownerResourceId, 
		@Param("locale") String locale
	);

	/**
	 * 全てのローカライズドリソースのサマリーを取得する
	 *
	 * @return ローカライズドリソースのサマリー
	 */
	public LocalizedResourceSummary<LocalizedResourceContent> listSummary();

	/**
	 * 全てのローカライズドリソースを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return ローカライズドリソースのリスト
	 */
	public List<LocalizedResourceContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 所属リソースを取得する
	 *
	 * @param content ローカライズドリソース
	 * @return 所属リソース
	 */
	public ResourceContent getOwner(LocalizedResourceContent content);

	/**
	 * 所属リソースの候補サマリーを取得する
	 *
	 * @param content ローカライズドリソース
	 * @param hint ヒント情報
	 * @return 所属リソースの候補サマリー
	 */
	public ResourceSummary<ResourceSubject> listOwnerCandidateSummary(
		@Param("content") LocalizedResourceContent content,
		@Param("hint") String hint
	);

	/**
	 * 所属リソースの候補一覧を取得する
	 *
	 * @param content ローカライズドリソース
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 所属リソースの候補一覧
	 */
	public List<ResourceSubject> listOwnerCandidateSubject(
		@Param("content") LocalizedResourceContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
