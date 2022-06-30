/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface MessageMapper extends BuilderMapper<MessageContent> {

	@Override
	public default List<MessageContent> listAll() {
		return listContent(0, 0);
	}

	/**
	 * メッセージを挿入する
	 *
	 * @param content 挿入するメッセージ
	 * @return 処理成功の場合、真
	 */
	public boolean insert(MessageContent content);

	/**
	 * メッセージを更新する
	 *
	 * @param content 更新するメッセージ
	 * @return 処理成功の場合、真
	 */
	public boolean update(MessageContent content);

	/**
	 * メッセージを削除する
	 *
	 * @param content 削除するメッセージ
	 * @return 処理成功の場合、真
	 */
	public boolean delete(MessageContent content);

	/**
	 * メッセージを取得する
	 *
	 * @param messageId メッセージID
	 * @return 取得したメッセージ
	 */
	public MessageContent get(
		@Param("messageId") int messageId
	);

	/**
	 * 全てのメッセージのサマリーを取得する
	 *
	 * @return メッセージのサマリー
	 */
	public MessageSummary<MessageContent> listSummary();

	/**
	 * 全てのメッセージを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return メッセージのリスト
	 */
	public List<MessageContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 所属プロジェクトを取得する
	 *
	 * @param content メッセージ
	 * @return 所属プロジェクト
	 */
	public ProjectContent getOwner(MessageContent content);

	/**
	 * 所属プロジェクトの候補サマリーを取得する
	 *
	 * @param content メッセージ
	 * @param hint ヒント情報
	 * @return 所属プロジェクトの候補サマリー
	 */
	public ProjectSummary<ProjectSubject> listOwnerCandidateSummary(
		@Param("content") MessageContent content,
		@Param("hint") String hint
	);

	/**
	 * 所属プロジェクトの候補一覧を取得する
	 *
	 * @param content メッセージ
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 所属プロジェクトの候補一覧
	 */
	public List<ProjectSubject> listOwnerCandidateSubject(
		@Param("content") MessageContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * メッセージを取得する
	 *
	 * @param content メッセージ
	 * @return メッセージ
	 */
	public ResourceContent getMessage(MessageContent content);

	/**
	 * メッセージの候補サマリーを取得する
	 *
	 * @param content メッセージ
	 * @param hint ヒント情報
	 * @return メッセージの候補サマリー
	 */
	public ResourceSummary<ResourceSubject> listMessageCandidateSummary(
		@Param("content") MessageContent content,
		@Param("hint") String hint
	);

	/**
	 * メッセージの候補一覧を取得する
	 *
	 * @param content メッセージ
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return メッセージの候補一覧
	 */
	public List<ResourceSubject> listMessageCandidateSubject(
		@Param("content") MessageContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
