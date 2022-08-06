package com.github.vvorks.builder.shared.common.lang;

/**
 * コピー可能なオブジェクト向けインターフェース
 *
 * GWTではCloneableが利用できないための代替措置
 *
 * @param <T> コピー対象型（通常は、実装クラス自身）
 */
public interface Copyable<T> {

	/**
	 * オブジェクトをコピーする
	 *
	 * @return
	 * 		コピーされたオブジェクト
	 */
	public T copy();

}
