package com.github.vvorks.builder.client.ui;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.github.vvorks.builder.client.common.net.JsonRpcClient;
import com.github.vvorks.builder.client.common.ui.DataSource;
import com.github.vvorks.builder.shared.common.json.Json;
import com.github.vvorks.builder.shared.common.lang.Callback;
import com.github.vvorks.builder.shared.common.logging.Logger;
import com.github.vvorks.builder.shared.common.util.CacheMap;
import com.github.vvorks.builder.shared.common.util.IntRange;

public class BuilderRpcDataSource extends DataSource {

	private static final Logger LOGGER = Logger.createLogger(BuilderRpcDataSource.class);

	public static final int DEFAULT_PAGE_SIZE = 50;

	public static final int DEFAULT_CACHE_SIZE = 200;

	/** rpcクライアント */
	private final JsonRpcClient rpc;

	/** データ（テーブル）名 */
	private String dataName;

	/** API名 */
	private String apiName;

	/** 画面あたりの行数 */
	private final int pageSize;

	/** データキャッシュ */
	private Map<Integer, Json> cache;

	/** コンテント */
	private Json content;

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

	public BuilderRpcDataSource(JsonRpcClient rpc, String dataName, String apiName) {
		this.rpc = rpc;
		this.dataName = dataName;
		this.apiName = apiName;
		this.pageSize = DEFAULT_PAGE_SIZE;
		cache = new CacheMap<>(Math.max(DEFAULT_CACHE_SIZE, DEFAULT_PAGE_SIZE * 4), true);
		content = null;
		criteria = null;
		requests = new HashSet<>();
	}

	@Override
	public void onAttached() {
		reload();
	}

	private void reload() {
		loaded = false;
		lastCount = -1;
		lastUpdatedAt = null;
		doRequest(content, criteria, -1, pageSize);
	}

	private void doRequest(Json content, Json criteria, int offset, int limit) {
		Json param = makeParam(content, criteria, offset, limit);
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

	private Json makeParam(Json content, Json criteria, int offset, int limit) {
		Json param = Json.createObject();
		param.setInt("offset", offset);
		param.setInt("limit", limit);
		if (content != null) {
			param.set("content", content);
		}
		if (criteria != null) {
			for (Entry<String, Json> e : criteria.entrySet()) {
				param.set(e.getKey(), e.getValue());
			}
		}
		return param;
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
	public void setCriteria(Json content, Json criteria) {
		this.content = content;
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
		doRequest(content, criteria, offset, limit);
	}

	@Override
	public void insert(Json data) {
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public void update(Json data) {
		// TODO 要実装
		LOGGER.warn("DO UPDATE! %s: %s", dataName, data);
	}

	@Override
	public void remove(Json data) {
		// TODO 自動生成されたメソッド・スタブ
	}

}
