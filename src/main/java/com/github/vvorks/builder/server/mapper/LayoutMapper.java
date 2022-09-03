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
	 * レイアウトのトピックパスを取得する
	 *
	 * @param content レイアウト
	 * @return レイアウトのトピックパス
	 */
	public Map<String, Object> getTopicPath(LayoutContent content);

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
	 * rootに合致するレイアウトのサマリーを取得する
	 *
	 * @return レイアウトのサマリー
	 */
	public LayoutSummary<LayoutContent> listSummaryIfRoot(
	);

	/**
	 * rootに合致するレイアウトを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return レイアウトのリスト
	 */
	public List<LayoutContent> listContentIfRoot(
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
	 * 対象クラスを取得する
	 *
	 * @param content レイアウト
	 * @return 対象クラス
	 */
	public ClassContent getCref(LayoutContent content);

	/**
	 * 対象クラスの候補サマリーを取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @return 対象クラスの候補サマリー
	 */
	public ClassSummary<ClassSubject> listCrefCandidateSummary(
		@Param("content") LayoutContent content,
		@Param("hint") String hint
	);

	/**
	 * 対象クラスの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 対象クラスの候補一覧
	 */
	public List<ClassSubject> listCrefCandidateSubject(
		@Param("content") LayoutContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 対象列挙を取得する
	 *
	 * @param content レイアウト
	 * @return 対象列挙
	 */
	public EnumContent getEref(LayoutContent content);

	/**
	 * 対象列挙の候補サマリーを取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @return 対象列挙の候補サマリー
	 */
	public EnumSummary<EnumSubject> listErefCandidateSummary(
		@Param("content") LayoutContent content,
		@Param("hint") String hint
	);

	/**
	 * 対象列挙の候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 対象列挙の候補一覧
	 */
	public List<EnumSubject> listErefCandidateSubject(
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
	public FieldContent getFref(LayoutContent content);

	/**
	 * 対象フィールドの候補サマリーを取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @return 対象フィールドの候補サマリー
	 */
	public FieldSummary<FieldSubject> listFrefCandidateSummary(
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
	public List<FieldSubject> listFrefCandidateSubject(
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
	public MessageContent getMref(LayoutContent content);

	/**
	 * 対象メッセージの候補サマリーを取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @return 対象メッセージの候補サマリー
	 */
	public MessageSummary<MessageSubject> listMrefCandidateSummary(
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
	public List<MessageSubject> listMrefCandidateSubject(
		@Param("content") LayoutContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 関連レイアウトを取得する
	 *
	 * @param content レイアウト
	 * @return 関連レイアウト
	 */
	public LayoutContent getRelated(LayoutContent content);

	/**
	 * 関連レイアウトの候補サマリーを取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @return 関連レイアウトの候補サマリー
	 */
	public LayoutSummary<LayoutSubject> listRelatedCandidateSummary(
		@Param("content") LayoutContent content,
		@Param("hint") String hint
	);

	/**
	 * 関連レイアウトの候補一覧を取得する
	 *
	 * @param content レイアウト
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 関連レイアウトの候補一覧
	 */
	public List<LayoutSubject> listRelatedCandidateSubject(
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

	/**
	 * rootに合致する子レイアウトのサマリーを取得する
	 *
	 * @param content レイアウト
	 * @return 子レイアウトのサマリー
	 */
	public LayoutSummary<LayoutContent> listChildrenSummaryIfRoot(
		@Param("content") LayoutContent content
	);

	/**
	 * rootに合致する子レイアウトを取得する
	 *
	 * @param content レイアウト
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 子レイアウト
	 */
	public List<LayoutContent> listChildrenContentIfRoot(
		@Param("content") LayoutContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
