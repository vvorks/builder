package com.github.vvorks.builder.client.common.ui;

import java.util.Optional;
import java.util.OptionalInt;

import com.github.vvorks.builder.common.json.Json;

public interface DataSource {

	/** 関連するアプリケーションを追加 */
	public void attach(UiApplication app);

	/** 関連するアプリケーションを削除 */
	public void detach(UiApplication app);

	/**
	 * 検索条件を指定する
	 *
	 * @param criteria 検索条件
	 */
	public void setCriteria(Json criteria);

	/**
	 * データ取得範囲を設定する
	 *
	 * @param offset データ開始位置
	 * @param limit データ件数
	 */
	public void setRange(int offset, int limit);

	/**
	 * （保持していれば）全件数情報を返す
	 *
	 * @return 全件数情報
	 */
	public OptionalInt getCount();

	/**
	 * （保持していれば）保持中のデータ開始位置を返す
	 *
	 * @return データ開始位置
	 */
	public OptionalInt getOffset();

	/**
	 * （保持していれば）保持中のデータ件数を返す
	 *
	 * @return データ件数
	 */
	public OptionalInt getLimit();

	/**
	 * （保持していれば）指定位置のデータを返す
	 *
	 * @param index 位置
	 * @return データ
	 */
	public Optional<Json> getData(int index);

	/**
	 * データを追加する
	 *
	 * @param data 追加するデータ
	 */
	public void insert(Json data);

	/**
	 * データを更新する
	 *
	 * @param data 更新するデータ
	 */
	public void update(Json data);

	/**
	 * データを削除する
	 *
	 * @param data 削除するデータ
	 */
	public void remove(Json data);

}
