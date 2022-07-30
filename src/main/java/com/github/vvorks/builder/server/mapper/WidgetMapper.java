/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface WidgetMapper extends MapperInterface<WidgetContent> {

	@Override
	public default List<WidgetContent> listAll() {
		return listContent(0, 0);
	}

	/**
	 * ウィジェットを挿入する
	 *
	 * @param content 挿入するウィジェット
	 * @return 処理成功の場合、真
	 */
	public boolean insert(WidgetContent content);

	/**
	 * ウィジェットを更新する
	 *
	 * @param content 更新するウィジェット
	 * @return 処理成功の場合、真
	 */
	public boolean update(WidgetContent content);

	/**
	 * ウィジェットを削除する
	 *
	 * @param content 削除するウィジェット
	 * @return 処理成功の場合、真
	 */
	public boolean delete(WidgetContent content);

	/**
	 * ウィジェットを取得する
	 *
	 * @param widgetId ウィジェットId
	 * @return 取得したウィジェット
	 */
	public WidgetContent get(
		@Param("widgetId") int widgetId
	);

	/**
	 * 全てのウィジェットのサマリーを取得する
	 *
	 * @return ウィジェットのサマリー
	 */
	public WidgetSummary<WidgetContent> listSummary();

	/**
	 * 全てのウィジェットを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return ウィジェットのリスト
	 */
	public List<WidgetContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 所属プロジェクトを取得する
	 *
	 * @param content ウィジェット
	 * @return 所属プロジェクト
	 */
	public ProjectContent getOwner(WidgetContent content);

	/**
	 * 所属プロジェクトの候補サマリーを取得する
	 *
	 * @param content ウィジェット
	 * @param hint ヒント情報
	 * @return 所属プロジェクトの候補サマリー
	 */
	public ProjectSummary<ProjectSubject> listOwnerCandidateSummary(
		@Param("content") WidgetContent content,
		@Param("hint") String hint
	);

	/**
	 * 所属プロジェクトの候補一覧を取得する
	 *
	 * @param content ウィジェット
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 所属プロジェクトの候補一覧
	 */
	public List<ProjectSubject> listOwnerCandidateSubject(
		@Param("content") WidgetContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
