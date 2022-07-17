/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface FormMapper extends BuilderMapper<FormContent> {

	@Override
	public default List<FormContent> listAll() {
		return listContent(0, 0);
	}

	/**
	 * フォームを挿入する
	 *
	 * @param content 挿入するフォーム
	 * @return 処理成功の場合、真
	 */
	public boolean insert(FormContent content);

	/**
	 * フォームを更新する
	 *
	 * @param content 更新するフォーム
	 * @return 処理成功の場合、真
	 */
	public boolean update(FormContent content);

	/**
	 * フォームを削除する
	 *
	 * @param content 削除するフォーム
	 * @return 処理成功の場合、真
	 */
	public boolean delete(FormContent content);

	/**
	 * フォームをその従属要素も含めて削除する
	 *
	 * @param content 削除するフォーム
	 * @return 処理成功の場合、真
	 */
	public default boolean deleteFull(FormContent content) {
		for (FormVariantContent c : listVariantsContent(content, 0, 0)) {
			if (!Mappers.get().getFormVariantMapper().deleteFull(c)) {
				return false;
			}
		}
		return delete(content);
	}

	/**
	 * フォームを取得する
	 *
	 * @param formId formId
	 * @return 取得したフォーム
	 */
	public FormContent get(
		@Param("formId") int formId
	);

	/**
	 * 全てのフォームのサマリーを取得する
	 *
	 * @return フォームのサマリー
	 */
	public FormSummary<FormContent> listSummary();

	/**
	 * 全てのフォームを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フォームのリスト
	 */
	public List<FormContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * ownerを取得する
	 *
	 * @param content フォーム
	 * @return owner
	 */
	public ProjectContent getOwner(FormContent content);

	/**
	 * ownerの候補サマリーを取得する
	 *
	 * @param content フォーム
	 * @param hint ヒント情報
	 * @return ownerの候補サマリー
	 */
	public ProjectSummary<ProjectSubject> listOwnerCandidateSummary(
		@Param("content") FormContent content,
		@Param("hint") String hint
	);

	/**
	 * ownerの候補一覧を取得する
	 *
	 * @param content フォーム
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return ownerの候補一覧
	 */
	public List<ProjectSubject> listOwnerCandidateSubject(
		@Param("content") FormContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * variantsのサマリーを取得する
	 *
	 * @param content フォーム
	 * @return variantsのサマリー
	 */
	public FormVariantSummary<FormVariantContent> listVariantsSummary(
		@Param("content") FormContent content
	);

	/**
	 * variantsを取得する
	 *
	 * @param content フォーム
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return variants
	 */
	public List<FormVariantContent> listVariantsContent(
		@Param("content") FormContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
