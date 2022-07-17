/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface FormVariantMapper extends BuilderMapper<FormVariantContent> {

	@Override
	public default List<FormVariantContent> listAll() {
		return listContent(0, 0);
	}

	/**
	 * フォームバリエーションを挿入する
	 *
	 * @param content 挿入するフォームバリエーション
	 * @return 処理成功の場合、真
	 */
	public boolean insert(FormVariantContent content);

	/**
	 * フォームバリエーションを更新する
	 *
	 * @param content 更新するフォームバリエーション
	 * @return 処理成功の場合、真
	 */
	public boolean update(FormVariantContent content);

	/**
	 * フォームバリエーションを削除する
	 *
	 * @param content 削除するフォームバリエーション
	 * @return 処理成功の場合、真
	 */
	public boolean delete(FormVariantContent content);

	/**
	 * フォームバリエーションを取得する
	 *
	 * @param variantId variantId
	 * @return 取得したフォームバリエーション
	 */
	public FormVariantContent get(
		@Param("variantId") int variantId
	);

	/**
	 * 全てのフォームバリエーションのサマリーを取得する
	 *
	 * @return フォームバリエーションのサマリー
	 */
	public FormVariantSummary<FormVariantContent> listSummary();

	/**
	 * 全てのフォームバリエーションを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フォームバリエーションのリスト
	 */
	public List<FormVariantContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * ownerを取得する
	 *
	 * @param content フォームバリエーション
	 * @return owner
	 */
	public FormContent getOwner(FormVariantContent content);

	/**
	 * ownerの候補サマリーを取得する
	 *
	 * @param content フォームバリエーション
	 * @param hint ヒント情報
	 * @return ownerの候補サマリー
	 */
	public FormSummary<FormSubject> listOwnerCandidateSummary(
		@Param("content") FormVariantContent content,
		@Param("hint") String hint
	);

	/**
	 * ownerの候補一覧を取得する
	 *
	 * @param content フォームバリエーション
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return ownerの候補一覧
	 */
	public List<FormSubject> listOwnerCandidateSubject(
		@Param("content") FormVariantContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * layoutsのサマリーを取得する
	 *
	 * @param content フォームバリエーション
	 * @return layoutsのサマリー
	 */
	public LayoutSummary<LayoutContent> listLayoutsSummary(
		@Param("content") FormVariantContent content
	);

	/**
	 * layoutsを取得する
	 *
	 * @param content フォームバリエーション
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return layouts
	 */
	public List<LayoutContent> listLayoutsContent(
		@Param("content") FormVariantContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
