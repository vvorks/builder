package com.github.vvorks.builder.common.json;

/**
 * JSONデータ型
 */
public enum JsonType {

	/** 未定義 */
	UNDEFINED,

	/** NULL */
	NULL,

	/** 真偽値 */
	BOOLEAN,

	/** 数値（実質double値） */
	NUMBER,

	/** 文字列 */
	STRING,

	/** オブジェクト */
	OBJECT,

	/** 配列 */
	ARRAY

}