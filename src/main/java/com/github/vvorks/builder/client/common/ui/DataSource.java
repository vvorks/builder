package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.json.Json;

public interface DataSource {

	/**
	 * データ読み込み状態を取得する
	 *
	 * @return データ読み込み済みの場合、真
	 */
	public boolean isLoaded();

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
	 * （データ読み込み済みならば）全件数情報を返す
	 *
	 * @return 全件数情報
	 */
	public int getCount();

	/**
	 * （データ読み込み済みならば）保持中のデータ開始位置を返す
	 *
	 * @return データ開始位置
	 */
	public int getOffset();

	/**
	 * （データ読み込み済みならば）保持中のデータ件数を返す
	 *
	 * @return データ件数
	 */
	public int getLimit();

	/**
	 * （データ読み込み済みならば）指定位置のデータを返す
	 *
	 * @param index 位置
	 * @return データ
	 */
	public Json getData(int index);

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

	/**
	 * 関連するアプリケーションを追加
	 *
	 * @param app アプリケーション
	 */
	public void attach(UiApplication app);

	/**
	 * 関連するアプリケーションを削除
	 *
	 * @param app アプリケーション
	 */
	public void detach(UiApplication app);

}
