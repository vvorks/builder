package com.github.vvorks.builder.shared.common.lang;

/**
 * コールバックインターフェース
 *
 * @param <T> 成功時の返却データ型
 */
public interface Callback<T> {

	/**
	 * 成功通知
	 *
	 * @param result 処理結果
	 */
	public void onSuccess(T result);

	/**
	 * 失敗通知
	 *
	 * @param caught エラー原因
	 */
	public void onFailure(Throwable caught);

}
