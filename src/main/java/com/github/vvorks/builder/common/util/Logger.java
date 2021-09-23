package com.github.vvorks.builder.common.util;

public interface Logger {

	/**
	 * トレースログを出力する.
	 *
	 * @param format 出力フォーマット
	 * @param args 引数
	 */
	public void trace(String format, Object... args);

	/**
	 * 詳細なデバッグログを出力する.
	 *
	 * @param format 出力フォーマット
	 * @param args 引数
	 */
	public void verbose(String format, Object... args);

	/**
	 * デバッグログを出力する.
	 *
	 * @param format 出力フォーマット
	 * @param args 引数
	 */
	public void debug(String format, Object... args);

	/**
	 * 情報ログを出力する
	 *
	 * @param format 出力フォーマット
	 * @param args 引数
	 */
	public void info(String format, Object... args);

	/**
	 * 警告ログを出力する.
	 *
	 * @param format 出力フォーマット
	 * @param args 引数
	 */
	public void warn(String format, Object... args);

	/**
	 * エラーログを出力する.
	 *
	 * @param format 出力フォーマット
	 * @param args 引数
	 */
	public void error(String format, Object... args);

	/**
	 * エラーログを出力する.
	 *
	 * @param err
	 * 		例外
	 */
	public void error(Throwable err);

	/**
	 * エラーログを出力する.
	 *
	 * @param err 例外
	 * @param format 出力フォーマット
	 * @param args 引数
	 */
	public void error(Throwable err, String format, Object... args);

}
