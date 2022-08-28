package com.github.vvorks.builder.client.ui;

import java.util.Date;
import java.util.Map.Entry;

import com.github.vvorks.builder.client.common.net.JsonRpcClient;
import com.github.vvorks.builder.client.common.ui.DataSource;
import com.github.vvorks.builder.shared.common.json.Json;
import com.github.vvorks.builder.shared.common.lang.Callback;
import com.github.vvorks.builder.shared.common.logging.Logger;

/**
 * たかだか一件のデータを取得するデータソース
 */
public class BuilderRpcSingleDataSource extends DataSource {

	private static final Logger LOGGER = Logger.createLogger(BuilderRpcSingleDataSource.class);

	/** rpcクライアント */
	private final JsonRpcClient rpc;

	/** データ（テーブル）名 */
	private String dataName;

	/** API名 */
	private String apiName;

	/** キー */
	private Json key;

	/** （唯一の）データ */
	private Json data;

	/** ロード済みフラグ */
	private boolean loaded;

	/** 最新ロード時刻 */
	private Date lastUpdatedAt;

	public BuilderRpcSingleDataSource(JsonRpcClient rpc, String dataName, String apiName) {
		this.rpc = rpc;
		this.dataName = dataName;
		this.apiName = apiName;
		this.key = null;
		this.data = null;
		this.loaded = false;
		this.lastUpdatedAt = null;
	}

	@Override
	public void onAttached() {
		reload();
	}

	private void reload() {
		loaded = false;
		lastUpdatedAt = null;
		doRequest(key);
	}

	private void doRequest(Json key) {
		Json param = makeParam(key);
		rpc.request(apiName, param, 0, new Callback<Json>() {
			public void onSuccess(Json result) {
				doResponse(result);
			}
			public void onFailure(Throwable caught) {
				LOGGER.error(caught, caught.getMessage());
			}
		});
	}

	private Json makeParam(Json key) {
		Json param = Json.createObject();
		for (Entry<String, Json> e : key.entrySet()) {
			param.set(e.getKey(), e.getValue());
		}
		return param;
	}

	private void doResponse(Json result) {
		Date newLast = result.getDate("lastUpdatedAt");
		//到着データが古かった場合、無視する
		if (lastUpdatedAt != null && lastUpdatedAt.getTime() > newLast.getTime()) {
			return;
		}
		lastUpdatedAt = newLast;
		data = result;
		loaded = true;
		notifyToApps();
	}

	@Override
	public boolean isLoaded() {
		return loaded;
	}

	@Override
	public void setCriteria(Json content, Json criteria) {
		this.key = content;
		if (isAttached()) {
			reload();
		}
	}

	@Override
	public int getCount() {
		if (!loaded) {
			return 0;
		}
		return 1;
	}

	@Override
	public Json getData(int index) {
		if (!loaded || index != 0) {
			return null;
		}
		return data;
	}

	@Override
	public void insert(Json data) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(Json data) {
		// TODO 要実装
		LOGGER.warn("DO UPDATE! %s: %s", dataName, data);
	}

	@Override
	public void remove(Json data) {
		throw new UnsupportedOperationException();
	}

}
