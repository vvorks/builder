package com.github.vvorks.builder.client.common.net;

public interface WebSocketHandler {

	public void onOpen(WebSocket socket);

	public void onMessage(WebSocket socket, String msg);

	public void onError(WebSocket socket);

	public void onClose(WebSocket socket);

}
