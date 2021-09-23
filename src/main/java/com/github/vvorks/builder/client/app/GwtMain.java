package com.github.vvorks.builder.client.app;

import com.github.vvorks.builder.client.common.net.JsonRpcClient;
import com.github.vvorks.builder.client.common.net.WebSocket;
import com.github.vvorks.builder.common.json.JsonContext;
import com.github.vvorks.builder.common.json.JsonValue;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.lang.Main;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class GwtMain implements Main {

	public GwtMain(String[] args) {
		// create ui
		final Label label = new Label("JavaWriter works 3");
		RootPanel.get().add(label);
		// create json rpc client
		WebSocket ws = Factory.newInstance(WebSocket.class, "ws://localhost:8080/rpc");
		JsonRpcClient rpc = new JsonRpcClient(ws);
		// send request
		JsonContext json = Factory.newInstance(JsonContext.class, "{}");
		json.setInt("x", 100);
		json.setInt("y", 40);
		rpc.request("subtract", json.toJsonValue(), 0, (result) -> {
			try {
				JsonValue text = result.get();
				label.setText(text.toString());
			} catch (Exception err) {
				label.setText(err.getMessage());
			}
		});
	}

}
