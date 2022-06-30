/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.List;

/**
 * Mapper基底クラス
 */
public interface BuilderMapper<T> {

	/**
	 * 全データを取得する
	 */
	public List<T> listAll();

}
