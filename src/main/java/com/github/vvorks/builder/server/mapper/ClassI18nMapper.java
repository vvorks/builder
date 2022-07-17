/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface ClassI18nMapper extends BuilderMapper<ClassI18nContent> {

	@Override
	public default List<ClassI18nContent> listAll() {
		return listContent(0, 0);
	}

	/**
	 * クラス(I18n)を挿入する
	 *
	 * @param content 挿入するクラス(I18n)
	 * @return 処理成功の場合、真
	 */
	public boolean insert(ClassI18nContent content);

	/**
	 * クラス(I18n)を更新する
	 *
	 * @param content 更新するクラス(I18n)
	 * @return 処理成功の場合、真
	 */
	public boolean update(ClassI18nContent content);

	/**
	 * クラス(I18n)を削除する
	 *
	 * @param content 削除するクラス(I18n)
	 * @return 処理成功の場合、真
	 */
	public boolean delete(ClassI18nContent content);

	/**
	 * クラス(I18n)をその従属要素も含めて削除する
	 *
	 * @param content 削除するクラス(I18n)
	 * @return 処理成功の場合、真
	 */
	public default boolean deleteFull(ClassI18nContent content) {
		return delete(content);
	}

	/**
	 * クラス(I18n)を取得する
	 *
	 * @param ownerClassId 所属クラスのクラスID
	 * @param targetLocaleId 対象ロケールのロケールID
	 * @return 取得したクラス(I18n)
	 */
	public ClassI18nContent get(
		@Param("ownerClassId") int ownerClassId, 
		@Param("targetLocaleId") String targetLocaleId
	);

	/**
	 * 全てのクラス(I18n)のサマリーを取得する
	 *
	 * @return クラス(I18n)のサマリー
	 */
	public ClassI18nSummary<ClassI18nContent> listSummary();

	/**
	 * 全てのクラス(I18n)を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラス(I18n)のリスト
	 */
	public List<ClassI18nContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 所属クラスを取得する
	 *
	 * @param content クラス(I18n)
	 * @return 所属クラス
	 */
	public ClassContent getOwner(ClassI18nContent content);

	/**
	 * 所属クラスの候補サマリーを取得する
	 *
	 * @param content クラス(I18n)
	 * @param hint ヒント情報
	 * @return 所属クラスの候補サマリー
	 */
	public ClassSummary<ClassSubject> listOwnerCandidateSummary(
		@Param("content") ClassI18nContent content,
		@Param("hint") String hint
	);

	/**
	 * 所属クラスの候補一覧を取得する
	 *
	 * @param content クラス(I18n)
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 所属クラスの候補一覧
	 */
	public List<ClassSubject> listOwnerCandidateSubject(
		@Param("content") ClassI18nContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 対象ロケールを取得する
	 *
	 * @param content クラス(I18n)
	 * @return 対象ロケール
	 */
	public LocaleContent getTarget(ClassI18nContent content);

	/**
	 * 対象ロケールの候補サマリーを取得する
	 *
	 * @param content クラス(I18n)
	 * @param hint ヒント情報
	 * @return 対象ロケールの候補サマリー
	 */
	public LocaleSummary<LocaleSubject> listTargetCandidateSummary(
		@Param("content") ClassI18nContent content,
		@Param("hint") String hint
	);

	/**
	 * 対象ロケールの候補一覧を取得する
	 *
	 * @param content クラス(I18n)
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 対象ロケールの候補一覧
	 */
	public List<LocaleSubject> listTargetCandidateSubject(
		@Param("content") ClassI18nContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
