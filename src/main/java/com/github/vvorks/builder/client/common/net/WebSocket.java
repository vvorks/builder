package com.github.vvorks.builder.client.common.net;

import java.io.Closeable;
import java.io.IOException;

public interface WebSocket extends Closeable {

	public enum State {
		DISCONNECT,
		CONNECTING,
		OPEN,
		CLOSING,
		CLOSED
	}

	public void addHandler(WebSocketHandler handler);

	public void removeHandler(WebSocketHandler handler);

	public void open(String url) throws IOException;

	public void close() throws IOException;

	public void send(String text)throws IOException;

	public void broadcast(String text)throws IOException;

	public State getState();

}
