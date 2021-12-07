package com.github.vvorks.builder.client.app;

import java.util.ArrayList;
import java.util.Date;
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

	private Date lastUpdatedAt;

	private int offset;

	private List<Json> block;

	public TestDataSource(JsonRpcClient rpc) {
		this.rpc = rpc;
		criteria = null;
		loaded = false;
		count = 0;
		lastUpdatedAt = new Date(0L);
		offset = 0;
		block = new ArrayList<>();
		doRequest(criteria);
	}

	private void doRequest(Json criteria) {
		Json param = Json.createObject();
		param.setInt("offset", offset);
		param.setInt("limit", DEFAULT_LIMIT);
		param.setInt("lastCount", -1);
		param.setDate("lastUpdatedAt", lastUpdatedAt);
		rpc.request("listProjectInfo", param, 0, new Callback<Json>() {
			public void onSuccess(Json result) {
				LOGGER.debug("result %s", result);
				Json summary = result.get("summary");
				Json contents = result.get("contents");
				TestDataSource ds = TestDataSource.this;
				ds.count = summary.getInt("count");
				ds.offset = result.getInt("offset");
				ds.block = new ArrayList<>();
				int n = contents.size();
				for (int i = 0; i < n; i++) {
					ds.block.add(contents.get(i));
				}
				ds.lastUpdatedAt = summary.getDate("_lastUpdatedAt");
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
		doRequest(criteria);
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
		return block.size();
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
