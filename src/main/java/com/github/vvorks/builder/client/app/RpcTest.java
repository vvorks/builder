package com.github.vvorks.builder.client.app;

import com.github.vvorks.builder.client.common.net.JsonRpcClient;
import com.github.vvorks.builder.client.common.net.WebSocket;
import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Callback;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.lang.Main;
import com.github.vvorks.builder.common.logging.Logger;

public class RpcTest implements Main {

	private static final Class<?> THIS = RpcTest.class;
	private static final Logger LOGGER = Factory.newInstance(Logger.class, THIS);

	public RpcTest(String[] args) {
		execute();
	}

	private void execute() {
		LOGGER.debug("start");
		WebSocket ws = Factory.newInstance(WebSocket.class, "ws://localhost:8080/builder");
		JsonRpcClient rpc = new JsonRpcClient(ws);
		Json param = Json.createObject();
		param.setNumber("offset", 0);
		param.setNumber("limit", 0);
		rpc.request("listProjectContent", param, 0, new Callback<Json>() {
			public void onSuccess(Json result) {
				for (Json prj : result.values()) {
					LOGGER.debug("projectName='%s'", prj.getString("projectName", "?"));
					Json param2 = Json.createObject();
					param2.set("content", prj);
					param2.setNumber("offset", 0);
					param2.setNumber("limit", 0);
					rpc.request("listProjectClassesContent", param2, 0, new Callback<Json>() {
						public void onSuccess(Json result) {
							for (Json cls : result.values()) {
								LOGGER.debug("className='%s'", cls.getString("className", "?"));
							}
						}
						public void onFailure(Throwable caught) {}
					});
				}
			}
			public void onFailure(Throwable caught) {}
		});
	}

}
