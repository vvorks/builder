/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface EnumValueI18nMapper extends MapperInterface<EnumValueI18nContent> {

	@Override
	public default List<EnumValueI18nContent> listAll() {
		return listContent(0, 0);
	}

	/**
	 * 列挙値(I18n)を挿入する
	 *
	 * @param content 挿入する列挙値(I18n)
	 * @return 処理成功の場合、真
	 */
	public boolean insert(EnumValueI18nContent content);

	/**
	 * 列挙値(I18n)を更新する
	 *
	 * @param content 更新する列挙値(I18n)
	 * @return 処理成功の場合、真
	 */
	public boolean update(EnumValueI18nContent content);

	/**
	 * 列挙値(I18n)を削除する
	 *
	 * @param content 削除する列挙値(I18n)
	 * @return 処理成功の場合、真
	 */
	public boolean delete(EnumValueI18nContent content);

	/**
	 * 列挙値(I18n)を取得する
	 *
	 * @param ownerOwnerEnumId 所属列挙値の所属列挙の列挙ID
	 * @param ownerValueId 所属列挙値の列挙名
	 * @param targetLocaleId 対象ロケールのロケールID
	 * @return 取得した列挙値(I18n)
	 */
	public EnumValueI18nContent get(
		@Param("ownerOwnerEnumId") int ownerOwnerEnumId, 
		@Param("ownerValueId") String ownerValueId, 
		@Param("targetLocaleId") String targetLocaleId
	);

	/**
	 * 列挙値(I18n)のトピックパスを取得する
	 *
	 * @param content 列挙値(I18n)
	 * @return 列挙値(I18n)のトピックパス
	 */
	public Map<String, Object> getTopicPath(EnumValueI18nContent content);

	/**
	 * 全ての列挙値(I18n)のサマリーを取得する
	 *
	 * @return 列挙値(I18n)のサマリー
	 */
	public EnumValueI18nSummary<EnumValueI18nContent> listSummary();

	/**
	 * 全ての列挙値(I18n)を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙値(I18n)のリスト
	 */
	public List<EnumValueI18nContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 所属列挙値を取得する
	 *
	 * @param content 列挙値(I18n)
	 * @return 所属列挙値
	 */
	public EnumValueContent getOwner(EnumValueI18nContent content);

	/**
	 * 所属列挙値の候補サマリーを取得する
	 *
	 * @param content 列挙値(I18n)
	 * @param hint ヒント情報
	 * @return 所属列挙値の候補サマリー
	 */
	public EnumValueSummary<EnumValueSubject> listOwnerCandidateSummary(
		@Param("content") EnumValueI18nContent content,
		@Param("hint") String hint
	);

	/**
	 * 所属列挙値の候補一覧を取得する
	 *
	 * @param content 列挙値(I18n)
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 所属列挙値の候補一覧
	 */
	public List<EnumValueSubject> listOwnerCandidateSubject(
		@Param("content") EnumValueI18nContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 対象ロケールを取得する
	 *
	 * @param content 列挙値(I18n)
	 * @return 対象ロケール
	 */
	public LocaleContent getTarget(EnumValueI18nContent content);

	/**
	 * 対象ロケールの候補サマリーを取得する
	 *
	 * @param content 列挙値(I18n)
	 * @param hint ヒント情報
	 * @return 対象ロケールの候補サマリー
	 */
	public LocaleSummary<LocaleSubject> listTargetCandidateSummary(
		@Param("content") EnumValueI18nContent content,
		@Param("hint") String hint
	);

	/**
	 * 対象ロケールの候補一覧を取得する
	 *
	 * @param content 列挙値(I18n)
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 対象ロケールの候補一覧
	 */
	public List<LocaleSubject> listTargetCandidateSubject(
		@Param("content") EnumValueI18nContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
