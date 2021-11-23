package com.github.vvorks.builder.client.common.ui;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Asserts;

public class ListDataSource implements DataSource {

	private final List<Json> list;

	public ListDataSource(List<Json> list) {
		Asserts.requireNotNull(list);
		this.list = list;
	}

	@Override
	public void setCriteria(Json criteria) {
		//NOP
	}

	@Override
	public void setRange(int offset, int limit) {
		//NOP
	}

	@Override
	public OptionalInt getCount() {
		return OptionalInt.of(list.size());
	}

	@Override
	public OptionalInt getOffset() {
		return OptionalInt.of(0);
	}

	@Override
	public OptionalInt getLimit() {
		return getCount();
	}

	@Override
	public Optional<Json> getData(int index) {
		return Optional.of(list.get(index));
	}

	@Override
	public void insert(Json data) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(Json data) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove(Json data) {
		throw new UnsupportedOperationException();
	}

}
