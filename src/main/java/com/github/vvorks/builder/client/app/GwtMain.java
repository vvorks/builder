package com.github.vvorks.builder.client.app;

import com.github.vvorks.builder.client.common.net.JsonRpcClient;
import com.github.vvorks.builder.client.common.net.WebSocket;
import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.lang.Main;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;

public class GwtMain implements Main {

	public GwtMain(String[] args) {
		// create json rpc client
		WebSocket ws = Factory.newInstance(WebSocket.class, "ws://localhost:8080/rpc");
		JsonRpcClient rpc = new JsonRpcClient(ws);
		// create ui
		SplitLayoutPanel p = new SplitLayoutPanel();
		TextArea tin = new TextArea();
		tin.setWidth("100%");
		tin.setHeight("100%");
		p.addNorth(tin, 300);
		TextArea tout = new TextArea();
		tout.setWidth("100%");
		tout.setHeight("100%");
		p.addSouth(tout, 300);
		p.setWidth("100%");
		p.setHeight("100%");
		RootLayoutPanel.get().add(p);
		//event handling
		tin.addKeyDownHandler(event -> {
			if (event.isControlKeyDown() && event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				// send request
				Json json = Json.createObject();
				json.setString("code", tin.getText());
				rpc.request("parse", json, 0, (result) -> {
					try {
						Json res = result.get();
						tout.setText(res.getStringValue());
					} catch (Exception err) {
						tout.setText(err.getMessage());
					}
				});
			}
		});
	}

}
