package com.github.vvorks.builder.client.ui;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.github.vvorks.builder.client.common.net.JsonRpcClient;
import com.github.vvorks.builder.client.common.ui.DataSource;
import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Callback;
import com.github.vvorks.builder.common.logging.Logger;
import com.github.vvorks.builder.common.util.CacheMap;
import com.github.vvorks.builder.common.util.DelayedExecuter;
import com.github.vvorks.builder.common.util.IntRange;

public class BuilderRpcDataSource extends DataSource {

	private static final Logger LOGGER = Logger.createLogger(BuilderRpcDataSource.class);

	/** rpcクライアント */
	private final JsonRpcClient rpc;

	/** API名 */
	private String apiName;

	/** 画面あたりの行数 */
	private final int pageSize;

	/** データキャッシュ */
	private Map<Integer, Json> cache;

	/** 検索条件 */
	private Json criteria;

	/** 検索中の値範囲リスト */
	private Set<IntRange> requests;

	/** ロード済みフラグ */
	private boolean loaded;

	/** 最新ロード時の総データ数 */
	private int lastCount;

	/** 最新ロード時刻 */
	private Date lastUpdatedAt;

	public BuilderRpcDataSource(JsonRpcClient rpc, String apiName, int pageSize, int cacheSize) {
		this.rpc = rpc;
		this.apiName = apiName;
		this.pageSize = pageSize;
		cache = new CacheMap<>(Math.max(cacheSize, pageSize * 4), true);
		criteria = null;
		requests = new HashSet<>();
	}

	@Override
	public void onAttached() {
		if (!loaded) {
			reload();
		} else {
			DelayedExecuter.get().runLator(() -> notifyToApps());
		}
	}

	private void reload() {
		loaded = false;
		lastCount = -1;
		lastUpdatedAt = null;
		doRequest(criteria, -1, pageSize);
	}

	private void doRequest(Json criteria, int offset, int limit) {
		Json param = Json.createObject();
		param.setInt("offset", offset);
		param.setInt("limit", limit);
		if (criteria != null) {
			for (Entry<String, Json> e : criteria.entrySet()) {
				param.set(e.getKey(), e.getValue());
			}
		}
		IntRange requestRange = offset == -1 ? null : new IntRange(offset, offset + limit - 1);
		rpc.request(apiName, param, 0, new Callback<Json>() {
			public void onSuccess(Json result) {
				if (requestRange != null) {
					requests.remove(requestRange);
				}
				doResponse(result);
			}
			public void onFailure(Throwable caught) {
				if (requestRange != null) {
					requests.remove(requestRange);
				}
				LOGGER.error(caught, caught.getMessage());
			}
		});
		if (requestRange != null) {
			requests.add(requestRange);
		}
	}

	private void doResponse(Json result) {
		int newCount = result.getInt("count");
		Date newLast = result.getDate("lastUpdatedAt");
		//到着データが古かった場合、無視する
		if (lastUpdatedAt != null && lastUpdatedAt.getTime() > newLast.getTime()) {
			return;
		}
		//到着データが新しかった場合、キャッシュを破棄
		if (lastUpdatedAt == null || lastUpdatedAt.getTime() < newLast.getTime() || lastCount != newCount) {
			LOGGER.info("CLEAR CACHE!");
			cache.clear();
		}
		lastCount = newCount;
		lastUpdatedAt = newLast;
		Json contents = result.get("contents");
		int offset = result.getInt("offset");
		int n = contents.size();
		for (int i = 0; i < n; i++) {
			cache.put(offset + i, contents.get(i));
		}
		loaded = true;
		notifyToApps();
	}

	@Override
	public boolean isLoaded() {
		return loaded;
	}

	@Override
	public void setCriteria(Json criteria) {
		this.criteria = criteria;
		if (isAttached()) {
			reload();
		}
	}

	@Override
	public int getCount() {
		if (!loaded) {
			return 0;
		}
		return lastCount;
	}

	@Override
	public Json getData(int index) {
		Json data = cache.get(index);
		if (data == null) {
			if (!isRequested(index)) {
				requestAround(index);
			}
		}
		return data;
	}

	private boolean isRequested(int index) {
		for (IntRange r : requests) {
			if (r.contains(index)) {
				return true;
			}
		}
		return false;
	}

	private void requestAround(int index) {
		int count = loaded ? this.lastCount : Integer.MAX_VALUE;
		int halfSize = pageSize / 2;
		int head = Math.max(0, index - halfSize);
		int tail = Math.min(index + halfSize, count - 1);
		while (cache.get(head) != null) {
			head++;
		}
		while (cache.get(tail) != null) {
			tail--;
		}
		int offset = head;
		int limit = tail - head + 1;
		doRequest(criteria, offset, limit);
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
