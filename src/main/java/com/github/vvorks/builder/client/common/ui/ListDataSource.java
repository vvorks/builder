package com.github.vvorks.builder.client.common.ui;

import java.util.List;

import com.github.vvorks.builder.shared.common.json.Json;
import com.github.vvorks.builder.shared.common.lang.Asserts;

public class ListDataSource extends DataSource {

	private final List<Json> list;

	public ListDataSource(List<Json> list) {
		Asserts.requireNotNull(list);
		this.list = list;
	}

	@Override
	public boolean isLoaded() {
		return true;
	}

	@Override
	public void setCriteria(Json content, Json criteria) {
		//TODO create sublist
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Json getData(int index) {
		return list.get(index);
	}

	@Override
	public void insert(Json data) {
		list.add(data);
	}

	@Override
	public void update(Json data) {
		//NOP
	}

	@Override
	public void remove(Json data) {
		list.remove(data);
	}

}
