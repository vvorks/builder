package com.github.vvorks.builder.client.common.ui;

import java.util.HashSet;
import java.util.Set;

import com.github.vvorks.builder.common.json.Json;

public abstract class DataSource {

	private final Set<UiApplication> apps;

	protected DataSource() {
		apps = new HashSet<>();
	}

	/**
	 * データ読み込み状態を取得する
	 *
	 * @return データ読み込み済みの場合、真
	 */
	public abstract boolean isLoaded();

	/**
	 * 検索条件を指定する
	 *
	 * @param criteria 検索条件
	 */
	public abstract void setCriteria(Json criteria);

	/**
	 * （データ読み込み済みならば）全件数情報を返す
	 *
	 * @return 全件数情報
	 */
	public abstract int getCount();

	/**
	 * （データ読み込み済みならば）指定位置のデータを返す
	 *
	 * @param index 位置
	 * @return データ
	 */
	public abstract Json getData(int index);

	/**
	 * データを追加する
	 *
	 * @param data 追加するデータ
	 */
	public abstract void insert(Json data);

	/**
	 * データを更新する
	 *
	 * @param data 更新するデータ
	 */
	public abstract void update(Json data);

	/**
	 * データを削除する
	 *
	 * @param data 削除するデータ
	 */
	public abstract void remove(Json data);

	/**
	 * 関連するアプリケーションを追加
	 *
	 * @param app アプリケーション
	 */
	public void attach(UiApplication app) {
		if (apps.isEmpty()) {
			onAttached();
		}
		apps.add(app);
	}

	/**
	 * 関連するアプリケーションを削除
	 *
	 * @param app アプリケーション
	 */
	public void detach(UiApplication app) {
		apps.remove(app);
		if (apps.isEmpty()) {
			onDetached();
		}
	}

	protected void onAttached() {
	}

	protected void onDetached() {
	}

	protected boolean isAttached() {
		return !apps.isEmpty();
	}

	/**
	 * データ更新をアプリケーションに通知する
	 */
	protected void notifyToApps() {
		for (UiApplication app : apps) {
			app.processDataSourceUpdated(this, 0);
		}
	}

}
