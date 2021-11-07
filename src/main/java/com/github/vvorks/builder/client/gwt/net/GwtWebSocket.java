package com.github.vvorks.builder.client.gwt.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.vvorks.builder.client.common.net.WebSocket;
import com.github.vvorks.builder.client.common.net.WebSocketHandler;
import com.google.gwt.core.client.JavaScriptObject;

public class GwtWebSocket implements WebSocket {

	private JavaScriptObject ws;

	private final List<WebSocketHandler> handlers = new ArrayList<>();

	public GwtWebSocket() {
		ws = null;
	}

	public GwtWebSocket(String url) {
		open(url);
	}

	@Override
	public void addHandler(WebSocketHandler handler) {
		handlers.add(handler);
	}

	@Override
	public void removeHandler(WebSocketHandler handler) {
		handlers.remove(handler);
	}

	@Override
	public void open(String url) {
		ws = open0(url);
	}

	@Override
	public void close() {
		close0(ws);
		ws = null;
	}

	@Override
	public void send(String text) {
		send0(ws, text);
	}

	@Override
	public void broadcast(String text) throws IOException {
		send0(ws, text);
	}

	public State getState() {
		int readyState;
		if (ws == null) {
			readyState= -1;
		} else {
			readyState = getReadyState(ws);
		}
		State result;
		switch (readyState) {
		case 0:
			result = State.CONNECTING;
			break;
		case 1:
			result = State.OPEN;
			break;
		case 2:
			result = State.CLOSING;
			break;
		case 3:
			result = State.CLOSED;
			break;
		default:
			result = State.DISCONNECT;
			break;
		}
		return result;
	}

	public void onOpen() {
		for (WebSocketHandler h : handlers) {
			h.onOpen(this);
		}
	}

	public void onClose() {
		for (WebSocketHandler h : handlers) {
			h.onClose(this);
		}
	}

	public void onMessage(String msg) {
		for (WebSocketHandler h : handlers) {
			h.onMessage(this, msg);
		}
	}

	public void onError() {
		for (WebSocketHandler h : handlers) {
			h.onError(this);
		}
	}

	/**
	 * JavaScript Native WebSocketのオープンとコールバック設定
	 *
	 * @param url
	 * 		接続先URL
	 */
	private native JavaScriptObject open0(String url) /*-{
		var ws = new WebSocket(url);
		var self = this;
		ws.onopen = function(evt) {
			self.@com.github.vvorks.builder.client.gwt.net.GwtWebSocket::onOpen()();
		};
		ws.onclose = function(evt) {
			self.@com.github.vvorks.builder.client.gwt.net.GwtWebSocket::onClose()();
		};
		ws.onmessage = function(evt) {
			self.@com.github.vvorks.builder.client.gwt.net.GwtWebSocket::onMessage(Ljava/lang/String;)(evt.data);
		};
		ws.onerror = function(evt) {
			self.@com.github.vvorks.builder.client.gwt.net.GwtWebSocket::onError()();
		}
		return ws;
	}-*/;

	private native void send0(JavaScriptObject ws, String text) /*-{
		ws.send(text);
	}-*/;

	private native void close0(JavaScriptObject ws) /*-{
		ws.close();
	}-*/;

	private native int getReadyState(JavaScriptObject ws) /*-{
		return ws.readyState;
	}-*/;

}
