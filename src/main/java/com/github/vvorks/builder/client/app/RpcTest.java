package com.github.vvorks.builder.client.app;

import com.github.vvorks.builder.client.common.net.JsonRpcClient;
import com.github.vvorks.builder.client.common.net.WebSocket;
import com.github.vvorks.builder.common.json.Json;
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
		rpc.request("listProjectContent", param, 0, result -> {
			Json projects = result.get();
			for (Json prj : projects.values()) {
				LOGGER.debug("projectName='%s'", prj.getString("projectName", "?"));
				Json param2 = Json.createObject();
				param2.set("content", prj);
				param2.setNumber("offset", 0);
				param2.setNumber("limit", 0);
				rpc.request("listProjectClassesContent", param2, 0, result2 -> {
					Json classes = result2.get();
					for (Json cls : classes.values()) {
						LOGGER.debug("className='%s'", cls.getString("className", "?"));
					}
				});
			}
		});
	}

}
