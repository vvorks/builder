package com.github.vvorks.builder.shared.common.lang;

/**
 * インスタンス生成I/F
 *
 * @param <T>
 * 		作成する型
 */
public interface Creator<T> {
	/**
	 * インスタンスを作成する
	 *
	 * @param args
	 * 		コンストラクタ引数
	 * @return
	 * 		インスタンス
	 * @throws Exception
	 * 		インスタンス作成が失敗した場合
	 */
	public T create(Object... args) throws Exception;
}