/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface LayoutMapper extends MapperInterface<LayoutContent> {

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
	 * @param layoutId レイアウトId
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
	 * 所属ページを取得する
	 *
	 * @param content レイアウト
	 * @return 所属ページ
	 */
	public PageContent getOwner(LayoutContent content);

	/**
	 * 所属ページの候補サマリーを取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @return 所属ページの候補サマリー
	 */
	public PageSummary<PageSubject> listOwnerCandidateSummary(
		@Param("content") LayoutContent content,
		@Param("hint") String hint
	);

	/**
	 * 所属ページの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 所属ページの候補一覧
	 */
	public List<PageSubject> listOwnerCandidateSubject(
		@Param("content") LayoutContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 親レイアウトを取得する
	 *
	 * @param content レイアウト
	 * @return 親レイアウト
	 */
	public LayoutContent getParent(LayoutContent content);

	/**
	 * 親レイアウトの候補サマリーを取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @return 親レイアウトの候補サマリー
	 */
	public LayoutSummary<LayoutSubject> listParentCandidateSummary(
		@Param("content") LayoutContent content,
		@Param("hint") String hint
	);

	/**
	 * 親レイアウトの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 親レイアウトの候補一覧
	 */
	public List<LayoutSubject> listParentCandidateSubject(
		@Param("content") LayoutContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 対象フィールドを取得する
	 *
	 * @param content レイアウト
	 * @return 対象フィールド
	 */
	public FieldContent getTarget(LayoutContent content);

	/**
	 * 対象フィールドの候補サマリーを取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @return 対象フィールドの候補サマリー
	 */
	public FieldSummary<FieldSubject> listTargetCandidateSummary(
		@Param("content") LayoutContent content,
		@Param("hint") String hint
	);

	/**
	 * 対象フィールドの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 対象フィールドの候補一覧
	 */
	public List<FieldSubject> listTargetCandidateSubject(
		@Param("content") LayoutContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 対象メッセージを取得する
	 *
	 * @param content レイアウト
	 * @return 対象メッセージ
	 */
	public MessageContent getConst(LayoutContent content);

	/**
	 * 対象メッセージの候補サマリーを取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @return 対象メッセージの候補サマリー
	 */
	public MessageSummary<MessageSubject> listConstCandidateSummary(
		@Param("content") LayoutContent content,
		@Param("hint") String hint
	);

	/**
	 * 対象メッセージの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 対象メッセージの候補一覧
	 */
	public List<MessageSubject> listConstCandidateSubject(
		@Param("content") LayoutContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 使用Widgetを取得する
	 *
	 * @param content レイアウト
	 * @return 使用Widget
	 */
	public WidgetContent getWidget(LayoutContent content);

	/**
	 * 使用Widgetの候補サマリーを取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @return 使用Widgetの候補サマリー
	 */
	public WidgetSummary<WidgetSubject> listWidgetCandidateSummary(
		@Param("content") LayoutContent content,
		@Param("hint") String hint
	);

	/**
	 * 使用Widgetの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 使用Widgetの候補一覧
	 */
	public List<WidgetSubject> listWidgetCandidateSubject(
		@Param("content") LayoutContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 使用スタイルを取得する
	 *
	 * @param content レイアウト
	 * @return 使用スタイル
	 */
	public StyleContent getStyle(LayoutContent content);

	/**
	 * 使用スタイルの候補サマリーを取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @return 使用スタイルの候補サマリー
	 */
	public StyleSummary<StyleSubject> listStyleCandidateSummary(
		@Param("content") LayoutContent content,
		@Param("hint") String hint
	);

	/**
	 * 使用スタイルの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 使用スタイルの候補一覧
	 */
	public List<StyleSubject> listStyleCandidateSubject(
		@Param("content") LayoutContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 子レイアウトのサマリーを取得する
	 *
	 * @param content レイアウト
	 * @return 子レイアウトのサマリー
	 */
	public LayoutSummary<LayoutContent> listChildrenSummary(
		@Param("content") LayoutContent content
	);

	/**
	 * 子レイアウトを取得する
	 *
	 * @param content レイアウト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 子レイアウト
	 */
	public List<LayoutContent> listChildrenContent(
		@Param("content") LayoutContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
