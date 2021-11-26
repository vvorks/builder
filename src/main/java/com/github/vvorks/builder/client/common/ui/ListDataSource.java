package com.github.vvorks.builder.client.common.ui;

import java.util.HashSet;
import java.util.List;
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
	public boolean isLoaded() {
		return true;
	}

	@Override
	public void setCriteria(Json criteria) {
		notifyToApps();
	}

	private void notifyToApps() {
		DelayedExecuter executer = Factory.getInstance(DelayedExecuter.class);
		executer.runAfter(100, () -> {
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

	@Override
	public void attach(UiApplication app) {
		apps.add(app);
		DelayedExecuter executer = Factory.getInstance(DelayedExecuter.class);
		executer.runAfter(100, () -> {
			app.processDataSourceUpdated(this, 0);
		});
	}

	@Override
	public void detach(UiApplication app) {
		apps.remove(app);
	}

}
