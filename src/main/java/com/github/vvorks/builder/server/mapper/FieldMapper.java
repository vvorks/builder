/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.vvorks.builder.server.domain.*;

@Mapper
public interface FieldMapper extends BuilderMapper<FieldContent> {

	@Override
	public default List<FieldContent> listAll() {
		return listContent(0, 0);
	}

	/**
	 * フィールドを挿入する
	 *
	 * @param content 挿入するフィールド
	 * @return 処理成功の場合、真
	 */
	public boolean insert(FieldContent content);

	/**
	 * フィールドを更新する
	 *
	 * @param content 更新するフィールド
	 * @return 処理成功の場合、真
	 */
	public boolean update(FieldContent content);

	/**
	 * フィールドを削除する
	 *
	 * @param content 削除するフィールド
	 * @return 処理成功の場合、真
	 */
	public boolean delete(FieldContent content);

	/**
	 * フィールドをその従属要素も含めて削除する
	 *
	 * @param content 削除するフィールド
	 * @return 処理成功の場合、真
	 */
	public default boolean deleteFull(FieldContent content) {
		if (!deleteI18nsAll(content)) {
			return false;
		}
		return delete(content);
	}

	/**
	 * フィールドを取得する
	 *
	 * @param fieldId フィールドID
	 * @return 取得したフィールド
	 */
	public FieldContent get(
		@Param("fieldId") int fieldId
	);

	/**
	 * 全てのフィールドのサマリーを取得する
	 *
	 * @return フィールドのサマリー
	 */
	public FieldSummary<FieldContent> listSummary();

	/**
	 * 全てのフィールドを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールドのリスト
	 */
	public List<FieldContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 名前に合致するフィールドのサマリーを取得する
	 *
	 * @param name name
	 * @return フィールドのサマリー
	 */
	public FieldSummary<FieldContent> listSummaryIfNameIs(
		@Param("name") String name
	);

	/**
	 * 名前に合致するフィールドを取得する
	 *
	 * @param name name
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールドのリスト
	 */
	public List<FieldContent> listContentIfNameIs(
		@Param("name") String name,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 所属クラスを取得する
	 *
	 * @param content フィールド
	 * @return 所属クラス
	 */
	public ClassContent getOwner(FieldContent content);

	/**
	 * 所属クラスの候補サマリーを取得する
	 *
	 * @param content フィールド
	 * @param hint ヒント情報
	 * @return 所属クラスの候補サマリー
	 */
	public ClassSummary<ClassSubject> listOwnerCandidateSummary(
		@Param("content") FieldContent content,
		@Param("hint") String hint
	);

	/**
	 * 所属クラスの候補一覧を取得する
	 *
	 * @param content フィールド
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 所属クラスの候補一覧
	 */
	public List<ClassSubject> listOwnerCandidateSubject(
		@Param("content") FieldContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * クラス参照先を取得する
	 *
	 * @param content フィールド
	 * @return クラス参照先
	 */
	public ClassContent getCref(FieldContent content);

	/**
	 * クラス参照先の候補サマリーを取得する
	 *
	 * @param content フィールド
	 * @param hint ヒント情報
	 * @return クラス参照先の候補サマリー
	 */
	public ClassSummary<ClassSubject> listCrefCandidateSummary(
		@Param("content") FieldContent content,
		@Param("hint") String hint
	);

	/**
	 * クラス参照先の候補一覧を取得する
	 *
	 * @param content フィールド
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return クラス参照先の候補一覧
	 */
	public List<ClassSubject> listCrefCandidateSubject(
		@Param("content") FieldContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * 列挙参照先を取得する
	 *
	 * @param content フィールド
	 * @return 列挙参照先
	 */
	public EnumContent getEref(FieldContent content);

	/**
	 * 列挙参照先の候補サマリーを取得する
	 *
	 * @param content フィールド
	 * @param hint ヒント情報
	 * @return 列挙参照先の候補サマリー
	 */
	public EnumSummary<EnumSubject> listErefCandidateSummary(
		@Param("content") FieldContent content,
		@Param("hint") String hint
	);

	/**
	 * 列挙参照先の候補一覧を取得する
	 *
	 * @param content フィールド
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return 列挙参照先の候補一覧
	 */
	public List<EnumSubject> listErefCandidateSubject(
		@Param("content") FieldContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * フィールド参照先を取得する
	 *
	 * @param content フィールド
	 * @return フィールド参照先
	 */
	public FieldContent getFref(FieldContent content);

	/**
	 * フィールド参照先の候補サマリーを取得する
	 *
	 * @param content フィールド
	 * @param hint ヒント情報
	 * @return フィールド参照先の候補サマリー
	 */
	public FieldSummary<FieldSubject> listFrefCandidateSummary(
		@Param("content") FieldContent content,
		@Param("hint") String hint
	);

	/**
	 * フィールド参照先の候補一覧を取得する
	 *
	 * @param content フィールド
	 * @param hint ヒント情報
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return フィールド参照先の候補一覧
	 */
	public List<FieldSubject> listFrefCandidateSubject(
		@Param("content") FieldContent content,
		@Param("hint") String hint,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * I18n一覧のサマリーを取得する
	 *
	 * @param content フィールド
	 * @return I18n一覧のサマリー
	 */
	public FieldI18nSummary<FieldI18nContent> listI18nsSummary(
		@Param("content") FieldContent content
	);

	/**
	 * I18n一覧を取得する
	 *
	 * @param content フィールド
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return I18n一覧
	 */
	public List<FieldI18nContent> listI18nsContent(
		@Param("content") FieldContent content,
		@Param("offset") int offset,
		@Param("limit") int limit
	);

	/**
	 * フィールドが保持するI18n一覧を全て削除する
	 *
	 * @param content フィールド
	 */
	public boolean deleteI18nsAll(
		@Param("content") FieldContent content
	);

}
