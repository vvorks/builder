/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface LayoutMapper extends BuilderMapper<LayoutContent> {

	@Override
	public default List<LayoutContent> listAll() {
		return listContent(0, 0);
	}

	/**
	 * レイアウトを挿入する
	 *
	 * @param content 挿入するレイアウト
	 * @return 処理成功の場合、真
	 */
	public boolean insert(LayoutContent content);

	/**
	 * レイアウトを更新する
	 *
	 * @param content 更新するレイアウト
	 * @return 処理成功の場合、真
	 */
	public boolean update(LayoutContent content);

	/**
	 * レイアウトを削除する
	 *
	 * @param content 削除するレイアウト
	 * @return 処理成功の場合、真
	 */
	public boolean delete(LayoutContent content);

	/**
	 * レイアウトを取得する
	 *
	 * @param layoutId layoutId
	 * @return 取得したレイアウト
	 */
	public LayoutContent get(
		@Param("layoutId") int layoutId
	);

	/**
	 * 全てのレイアウトのサマリーを取得する
	 *
	 * @return レイアウトのサマリー
	 */
	public LayoutSummary<LayoutContent> listSummary();

	/**
	 * 全てのレイアウトを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return レイアウトのリスト
	 */
	public List<LayoutContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * ownerを取得する
	 *
	 * @param content レイアウト
	 * @return owner
	 */
	public FormVariantContent getOwner(LayoutContent content);

	/**
	 * ownerの候補サマリーを取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @return ownerの候補サマリー
	 */
	public FormVariantSummary<FormVariantSubject> listOwnerCandidateSummary(
		@Param("content") LayoutContent content,
		@Param("hint") String hint
	);

	/**
	 * ownerの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return ownerの候補一覧
	 */
	public List<FormVariantSubject> listOwnerCandidateSubject(
		@Param("content") LayoutContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * parentを取得する
	 *
	 * @param content レイアウト
	 * @return parent
	 */
	public LayoutContent getParent(LayoutContent content);

	/**
	 * parentの候補サマリーを取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @return parentの候補サマリー
	 */
	public LayoutSummary<LayoutSubject> listParentCandidateSummary(
		@Param("content") LayoutContent content,
		@Param("hint") String hint
	);

	/**
	 * parentの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return parentの候補一覧
	 */
	public List<LayoutSubject> listParentCandidateSubject(
		@Param("content") LayoutContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * targetFieldを取得する
	 *
	 * @param content レイアウト
	 * @return targetField
	 */
	public FieldContent getTargetField(LayoutContent content);

	/**
	 * targetFieldの候補サマリーを取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @return targetFieldの候補サマリー
	 */
	public FieldSummary<FieldSubject> listTargetFieldCandidateSummary(
		@Param("content") LayoutContent content,
		@Param("hint") String hint
	);

	/**
	 * targetFieldの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return targetFieldの候補一覧
	 */
	public List<FieldSubject> listTargetFieldCandidateSubject(
		@Param("content") LayoutContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * targetMessageを取得する
	 *
	 * @param content レイアウト
	 * @return targetMessage
	 */
	public MessageContent getTargetMessage(LayoutContent content);

	/**
	 * targetMessageの候補サマリーを取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @return targetMessageの候補サマリー
	 */
	public MessageSummary<MessageSubject> listTargetMessageCandidateSummary(
		@Param("content") LayoutContent content,
		@Param("hint") String hint
	);

	/**
	 * targetMessageの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return targetMessageの候補一覧
	 */
	public List<MessageSubject> listTargetMessageCandidateSubject(
		@Param("content") LayoutContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * styleを取得する
	 *
	 * @param content レイアウト
	 * @return style
	 */
	public StyleContent getStyle(LayoutContent content);

	/**
	 * styleの候補サマリーを取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @return styleの候補サマリー
	 */
	public StyleSummary<StyleSubject> listStyleCandidateSummary(
		@Param("content") LayoutContent content,
		@Param("hint") String hint
	);

	/**
	 * styleの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return styleの候補一覧
	 */
	public List<StyleSubject> listStyleCandidateSubject(
		@Param("content") LayoutContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * childrenのサマリーを取得する
	 *
	 * @param content レイアウト
	 * @return childrenのサマリー
	 */
	public LayoutSummary<LayoutContent> listChildrenSummary(
		@Param("content") LayoutContent content
	);

	/**
	 * childrenを取得する
	 *
	 * @param content レイアウト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return children
	 */
	public List<LayoutContent> listChildrenContent(
		@Param("content") LayoutContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
