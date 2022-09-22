/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.github.vvorks.builder.server.domain.Subject;

/**
 * Mapper基底インターフェース
 */
public interface MapperInterface<T> {

	/**
	 * 全データを取得する
	 */
	public List<T> listAll();

	/**
	 * トピックパスをリスト形式で取得する
	 *
	 * @param content 対象コンテント
	 * @return トピックパス要素のリスト
	 */
	public default List<Subject> listTopicPath(T content) {
		//トピック情報のマップを取得
		Map<String, Object> pathMap = getTopicPath(content);
		// キーの形式が「P<order>_<fieldName>」なので、一旦オーダー別のマップに変形
		Map<Integer, Map<String, Object>> subMaps = new TreeMap<>();
		for (Map.Entry<String, Object> e : pathMap.entrySet()) {
			String key = e.getKey();
			int index = key.indexOf("_");
			int order = Integer.parseInt(key.substring(1, index));
			String subKey = key.substring(index + 1);
			Map<String, Object> subMap = subMaps.computeIfAbsent(
					order, k -> new TreeMap<String, Object>());
			subMap.put(subKey, e.getValue());
		}
		List<Subject> result = new ArrayList<>();
		for (Map<String, Object> subMap : subMaps.values()) {
			result.add(Subject.createFrom(subMap));
		}
		return result;
	}

	/**
	 * トピックパスを取得する
	 */
	public Map<String, Object> getTopicPath(T content);

}
