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
public interface FieldI18nMapper extends MapperInterface<FieldI18nContent> {

	@Override
	public default List<FieldI18nContent> listAll() {
		return listContent(0, 0);
	}

	/**
	 * フィールド(I18n)を挿入する
	 *
	 * @param content 挿入するフィールド(I18n)
	 * @return 処理成功の場合、真
	 */
	public boolean insert(FieldI18nContent content);

	/**
	 * フィールド(I18n)を更新する
	 *
	 * @param content 更新するフィールド(I18n)
	 * @return 処理成功の場合、真
	 */
	public boolean update(FieldI18nContent content);

	/**
	 * フィールド(I18n)を削除する
	 *
	 * @param content 削除するフィールド(I18n)
	 * @return 処理成功の場合、真
	 */
	public boolean delete(FieldI18nContent content);

	/**
	 * フィールド(I18n)を取得する
	 *
	 * @param ownerFieldId 所属フィールドのフィールドID
	 * @param targetLocaleId 対象ロケールのロケールID
	 * @return 取得したフィールド(I18n)
	 */
	public FieldI18nContent get(
		@Param("ownerFieldId") int ownerFieldId, 
		@Param("targetLocaleId") String targetLocaleId
	);

	/**
	 * フィールド(I18n)のトピックパスを取得する
	 *
	 * @param content フィールド(I18n)
	 * @return フィールド(I18n)のトピックパス
	 */
	public Map<String, Object> getTopicPath(FieldI18nContent content);

	/**
	 * 全てのフィールド(I18n)のサマリーを取得する
	 *
	 * @return フィールド(I18n)のサマリー
	 */
	public FieldI18nSummary<FieldI18nContent> listSummary();

	/**
	 * 全てのフィールド(I18n)を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールド(I18n)のリスト
	 */
	public List<FieldI18nContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 所属フィールドを取得する
	 *
	 * @param content フィールド(I18n)
	 * @return 所属フィールド
	 */
	public FieldContent getOwner(FieldI18nContent content);

	/**
	 * 所属フィールドの候補サマリーを取得する
	 *
	 * @param content フィールド(I18n)
	 * @param hint ヒント情報
	 * @return 所属フィールドの候補サマリー
	 */
	public FieldSummary<FieldSubject> listOwnerCandidateSummary(
		@Param("content") FieldI18nContent content,
		@Param("hint") String hint
	);

	/**
	 * 所属フィールドの候補一覧を取得する
	 *
	 * @param content フィールド(I18n)
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 所属フィールドの候補一覧
	 */
	public List<FieldSubject> listOwnerCandidateSubject(
		@Param("content") FieldI18nContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 対象ロケールを取得する
	 *
	 * @param content フィールド(I18n)
	 * @return 対象ロケール
	 */
	public LocaleContent getTarget(FieldI18nContent content);

	/**
	 * 対象ロケールの候補サマリーを取得する
	 *
	 * @param content フィールド(I18n)
	 * @param hint ヒント情報
	 * @return 対象ロケールの候補サマリー
	 */
	public LocaleSummary<LocaleSubject> listTargetCandidateSummary(
		@Param("content") FieldI18nContent content,
		@Param("hint") String hint
	);

	/**
	 * 対象ロケールの候補一覧を取得する
	 *
	 * @param content フィールド(I18n)
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 対象ロケールの候補一覧
	 */
	public List<LocaleSubject> listTargetCandidateSubject(
		@Param("content") FieldI18nContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
