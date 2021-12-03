package com.github.vvorks.builder.client.app;

import java.util.ArrayList;
import java.util.List;

import com.github.vvorks.builder.client.common.net.JsonRpcClient;
import com.github.vvorks.builder.client.common.ui.DataSource;
import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Callback;
import com.github.vvorks.builder.common.logging.Logger;

public class TestDataSource extends DataSource {

	public static final Class<?> THIS = TestDataSource.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	private static final int DEFAULT_LIMIT = 100;

	private final JsonRpcClient rpc;

	private Json criteria;

	private boolean loaded;

	private int count;

	private int offset;

	private int limit;

	private List<Json> block;

	public TestDataSource(JsonRpcClient rpc) {
		this.rpc = rpc;
		criteria = null;
		loaded = false;
		count = 0;
		offset = 0;
		limit = 0;
		block = new ArrayList<>();
		doRequest1(criteria);
	}

	private void doRequest1(Json criteria) {
		rpc.request("listProjectSummary", null, 0, new Callback<Json>() {
			public void onSuccess(Json result) {
				LOGGER.debug("result %s", result);
				int newCount = (int) result.getNumber("count", 0);
				setCount(newCount);
				doRequest2(criteria, 0, Math.min(newCount, DEFAULT_LIMIT));
			}
			public void onFailure(Throwable caught) {
				LOGGER.error(caught, caught.getMessage());
			}
		});
	}

	private void doRequest2(Json criteria, int offset, int limit) {
		Json param = Json.createObject();
		param.setNumber("offset", offset);
		param.setNumber("limit", limit);
		TestDataSource ds = this;
		rpc.request("listProjectContent", param, 0, new Callback<Json>() {
			public void onSuccess(Json result) {
				int n = result.size();
				List<Json> list = new ArrayList<>();
				for (int i = 0; i < n; i++) {
					list.add(result.get(i));
				}
				ds.offset = offset;
				ds.limit = limit;
				ds.block = list;
				ds.loaded = true;
				notifyToApps();
			}
			public void onFailure(Throwable caught) {
				LOGGER.error(caught, caught.getMessage());
			}
		});
	}

	@Override
	public boolean isLoaded() {
		return loaded;
	}

	@Override
	public void setCriteria(Json criteria) {
		this.criteria = criteria;
		doRequest1(criteria);
	}

	@Override
	public int getCount() {
		return count;
	}

	private void setCount(int count) {
		this.count = count;
	}

	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public int getLimit() {
		return limit;
	}

	@Override
	public void setRange(int offset, int limit) {
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public Json getData(int index) {
		return block.get(offset + index);
	}

	@Override
	public void insert(Json data) {
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public void update(Json data) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void remove(Json data) {
		// TODO 自動生成されたメソッド・スタブ
	}

}
