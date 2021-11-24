package com.github.vvorks.builder.client.common.ui;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Asserts;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.util.DelayedExecuter;

public class ListDataSource implements DataSource {

	private Set<UiApplication> apps;

	private final List<Json> list;

	public ListDataSource(List<Json> list) {
		Asserts.requireNotNull(list);
		apps = new HashSet<>();
		this.list = list;
	}

	@Override
	public void attach(UiApplication app) {
		apps.add(app);
		Factory.getInstance(DelayedExecuter.class).runAfter(100, () -> {
			app.processDataSourceUpdated(this, 0);
		});
	}

	@Override
	public void detach(UiApplication app) {
		apps.remove(app);
	}

	@Override
	public void setCriteria(Json criteria) {
		notifyToApps();
	}

	private void notifyToApps() {
		Factory.getInstance(DelayedExecuter.class).runAfter(100, () -> {
			for (UiApplication app : apps) {
				app.processDataSourceUpdated(this, 0);
			}
		});
	}

	@Override
	public void setRange(int offset, int limit) {
		notifyToApps();
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
