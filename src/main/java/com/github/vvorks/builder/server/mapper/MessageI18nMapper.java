/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface MessageI18nMapper extends BuilderMapper<MessageI18nContent> {

	@Override
	public default List<MessageI18nContent> listAll() {
		return listContent(0, 0);
	}

	/**
	 * メッセージ(I18n)を挿入する
	 *
	 * @param content 挿入するメッセージ(I18n)
	 * @return 処理成功の場合、真
	 */
	public boolean insert(MessageI18nContent content);

	/**
	 * メッセージ(I18n)を更新する
	 *
	 * @param content 更新するメッセージ(I18n)
	 * @return 処理成功の場合、真
	 */
	public boolean update(MessageI18nContent content);

	/**
	 * メッセージ(I18n)を削除する
	 *
	 * @param content 削除するメッセージ(I18n)
	 * @return 処理成功の場合、真
	 */
	public boolean delete(MessageI18nContent content);

	/**
	 * メッセージ(I18n)を取得する
	 *
	 * @param ownerMessageId 所属列挙値のメッセージID
	 * @param targetLocaleId 対象ロケールのロケールID
	 * @return 取得したメッセージ(I18n)
	 */
	public MessageI18nContent get(
		@Param("ownerMessageId") int ownerMessageId, 
		@Param("targetLocaleId") String targetLocaleId
	);

	/**
	 * 全てのメッセージ(I18n)のサマリーを取得する
	 *
	 * @return メッセージ(I18n)のサマリー
	 */
	public MessageI18nSummary<MessageI18nContent> listSummary();

	/**
	 * 全てのメッセージ(I18n)を取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return メッセージ(I18n)のリスト
	 */
	public List<MessageI18nContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 所属列挙値を取得する
	 *
	 * @param content メッセージ(I18n)
	 * @return 所属列挙値
	 */
	public MessageContent getOwner(MessageI18nContent content);

	/**
	 * 所属列挙値の候補サマリーを取得する
	 *
	 * @param content メッセージ(I18n)
	 * @param hint ヒント情報
	 * @return 所属列挙値の候補サマリー
	 */
	public MessageSummary<MessageSubject> listOwnerCandidateSummary(
		@Param("content") MessageI18nContent content,
		@Param("hint") String hint
	);

	/**
	 * 所属列挙値の候補一覧を取得する
	 *
	 * @param content メッセージ(I18n)
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 所属列挙値の候補一覧
	 */
	public List<MessageSubject> listOwnerCandidateSubject(
		@Param("content") MessageI18nContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 対象ロケールを取得する
	 *
	 * @param content メッセージ(I18n)
	 * @return 対象ロケール
	 */
	public LocaleContent getTarget(MessageI18nContent content);

	/**
	 * 対象ロケールの候補サマリーを取得する
	 *
	 * @param content メッセージ(I18n)
	 * @param hint ヒント情報
	 * @return 対象ロケールの候補サマリー
	 */
	public LocaleSummary<LocaleSubject> listTargetCandidateSummary(
		@Param("content") MessageI18nContent content,
		@Param("hint") String hint
	);

	/**
	 * 対象ロケールの候補一覧を取得する
	 *
	 * @param content メッセージ(I18n)
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 対象ロケールの候補一覧
	 */
	public List<LocaleSubject> listTargetCandidateSubject(
		@Param("content") MessageI18nContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
