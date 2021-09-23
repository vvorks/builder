package com.github.vvorks.builder.common.lang;

/**
 * コールバックインターフェース
 *
 * @param <T> 成功時の返却データ型
 */
public interface Callback<T> {

	/**
	 * 完了通知
	 *
	 * @param result
	 * 		結果オブジェクト
	 */
	public void onDone(Result<T> result) throws Exception;

}
