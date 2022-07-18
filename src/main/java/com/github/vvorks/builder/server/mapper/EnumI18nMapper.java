/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface EnumI18nMapper extends BuilderMapper<EnumI18nContent> {

	@Override
	public default List<EnumI18nContent> listAll() {
		return listContent(0, 0);
	}

	/**
	 * 列挙(I18n)を挿入する
	 *
	 * @param content 挿入する列挙(I18n)
	 * @return 処理成功の場合、真
	 */
	public boolean insert(EnumI18nContent content);

	/**
	 * 列挙(I18n)を更新する
	 *
	 * @param content 更新する列挙(I18n)
	 * @return 処理成功の場合、真
	 */
	public boolean update(EnumI18nContent content);

	/**
	 * 列挙(I18n)を削除する
	 *
	 * @param content 削除する列挙(I18n)
	 * @return 処理成功の場合、真
	 */
	public boolean delete(EnumI18nContent content);

	/**
	 * 列挙(I18n)を取得する
	 *
	 * @param ownerEnumId 所属列挙の列挙ID
	 * @param targetLocaleId 対象ロケールのロケールID
	 * @return 取得した列挙(I18n)
	 */
	public EnumI18nContent get(
		@Param("ownerEnumId") int ownerEnumId, 
		@Param("targetLocaleId") String targetLocaleId
	);

	/**
	 * 全ての列挙(I18n)のサマリーを取得する
	 *
	 * @return 列挙(I18n)のサマリー
	 */
	public EnumI18nSummary<EnumI18nContent> listSummary();

	/**
	 * 全ての列挙(I18n)を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙(I18n)のリスト
	 */
	public List<EnumI18nContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 所属列挙を取得する
	 *
	 * @param content 列挙(I18n)
	 * @return 所属列挙
	 */
	public EnumContent getOwner(EnumI18nContent content);

	/**
	 * 所属列挙の候補サマリーを取得する
	 *
	 * @param content 列挙(I18n)
	 * @param hint ヒント情報
	 * @return 所属列挙の候補サマリー
	 */
	public EnumSummary<EnumSubject> listOwnerCandidateSummary(
		@Param("content") EnumI18nContent content,
		@Param("hint") String hint
	);

	/**
	 * 所属列挙の候補一覧を取得する
	 *
	 * @param content 列挙(I18n)
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 所属列挙の候補一覧
	 */
	public List<EnumSubject> listOwnerCandidateSubject(
		@Param("content") EnumI18nContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 対象ロケールを取得する
	 *
	 * @param content 列挙(I18n)
	 * @return 対象ロケール
	 */
	public LocaleContent getTarget(EnumI18nContent content);

	/**
	 * 対象ロケールの候補サマリーを取得する
	 *
	 * @param content 列挙(I18n)
	 * @param hint ヒント情報
	 * @return 対象ロケールの候補サマリー
	 */
	public LocaleSummary<LocaleSubject> listTargetCandidateSummary(
		@Param("content") EnumI18nContent content,
		@Param("hint") String hint
	);

	/**
	 * 対象ロケールの候補一覧を取得する
	 *
	 * @param content 列挙(I18n)
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 対象ロケールの候補一覧
	 */
	public List<LocaleSubject> listTargetCandidateSubject(
		@Param("content") EnumI18nContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
