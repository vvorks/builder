package com.github.vvorks.builder.client.common.ui;

import java.util.List;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Asserts;

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
	public void setCriteria(Json criteria) {
		notifyToApps();
	}

	@Override
	public void setRange(int offset, int limit) {
		notifyToApps();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public int getLimit() {
		return getCount();
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
