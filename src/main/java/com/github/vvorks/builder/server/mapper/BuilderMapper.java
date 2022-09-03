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
public interface BuilderMapper extends MapperInterface<BuilderContent> {

	@Override
	public default List<BuilderContent> listAll() {
		return listContent(0, 0);
	}

	/**
	 * Builderを挿入する
	 *
	 * @param content 挿入するBuilder
	 * @return 処理成功の場合、真
	 */
	public boolean insert(BuilderContent content);

	/**
	 * Builderを更新する
	 *
	 * @param content 更新するBuilder
	 * @return 処理成功の場合、真
	 */
	public boolean update(BuilderContent content);

	/**
	 * Builderを削除する
	 *
	 * @param content 削除するBuilder
	 * @return 処理成功の場合、真
	 */
	public boolean delete(BuilderContent content);

	/**
	 * Builderを取得する
	 *
	 * @param className className
	 * @return 取得したBuilder
	 */
	public BuilderContent get(
		@Param("className") String className
	);

	/**
	 * Builderのトピックパスを取得する
	 *
	 * @param content Builder
	 * @return Builderのトピックパス
	 */
	public Map<String, Object> getTopicPath(BuilderContent content);

	/**
	 * 全てのBuilderのサマリーを取得する
	 *
	 * @return Builderのサマリー
	 */
	public BuilderSummary<BuilderContent> listSummary();

	/**
	 * 全てのBuilderを取得する
	 *
	 * @param offset 取得開始位置（全件取得の場合は無効）
	 * @param limit 件数（０または負値を指定した場合には全件）
	 * @return Builderのリスト
	 */
	public List<BuilderContent> listContent(
		@Param("offset") int offset,
		@Param("limit") int limit
	);

}
