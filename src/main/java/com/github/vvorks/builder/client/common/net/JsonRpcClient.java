package com.github.vvorks.builder.client.common.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.vvorks.builder.common.json.JsonContext;
import com.github.vvorks.builder.common.json.JsonRpcConstants;
import com.github.vvorks.builder.common.json.JsonValue;
import com.github.vvorks.builder.common.lang.Callback;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.lang.Result;
import com.github.vvorks.builder.common.util.DelayedExecuter;
import com.github.vvorks.builder.common.util.Logger;
import com.github.vvorks.builder.common.util.TimeoutException;

/**
 * WebSocket上でJsonRpc通信を行うサービスクラス
 */
public class JsonRpcClient implements WebSocketHandler, JsonRpcConstants {

	private static final Class<?> THIS = JsonRpcClient.class;
	private static final Logger LOGGER = Factory.newInstance(Logger.class, THIS);

	private static final String UNKNOWN_METHOD = "__UNKNOWN__";

	private static final int NOTIFY_ID = 0;

	private static int lastNumber;

	private static synchronized int takeNumber() {
		lastNumber++;
		if (lastNumber <= 0) {
			lastNumber = 1;
		}
		return lastNumber;
	}

	private static class RequestInfo {
		private final String method;
		private final Callback<JsonValue> callback;
		private final long absTimeout;
		private RequestInfo(String method, Callback<JsonValue> callback, int timeout) {
			this.method = method;
			this.callback = callback;
			this.absTimeout = (timeout > 0) ? System.currentTimeMillis() + timeout : 0;
		}
		public boolean isTimeout(long now) {
			return absTimeout != 0 && absTimeout < now;
		}
	}

	/**
	 * 接続確立前に要求されたメッセージを一時保管するためのキューの要素
	 */
	private static class QueueItem {
		private final int id;
		private final String message;
		public QueueItem(int id, String message) {
			this.id = id;
			this.message = message;
		}
	}

	/**
	 * 応答待ちメッセージリスト
	 */
	private Map<Integer, RequestInfo> waitings;

	/**
	 * ハンドラインターフェース
	 */
	public static interface Handler {
		public void handleRequest(JsonValue params, Callback<JsonValue> callback);
		public void handleNotify(JsonValue params);
	}

	/**
	 * ハンドラアダプタ
	 */
	public static class Adapter implements Handler {
		public void handleRequest(JsonValue params, Callback<JsonValue> callback) {
			//nop
		}
		public void handleNotify(JsonValue params) {
			//nop
		}
	}

	/**
	 * サーバーからの要求／通知を処理するハンドラーのマップ
	 */
	private Map<String, Handler> handlers;

	/**
	 * WebSocket
	 */
	private WebSocket webSocket;

	/**
	 * 接続確立前に要求されたメッセージを一時保管するためのキュー
	 */
	private final List<QueueItem> sendQueue;

	/**
	 * 指定のURLと通信を行うJsonRpcサービスを作成する
	 *
	 * @param webSocket
	 * 		通信先WebSocket
	 * @throws IOException
	 */
	public JsonRpcClient(WebSocket webSocket) {
		this.waitings = new HashMap<>();
		this.handlers = new HashMap<>();
		this.sendQueue = new ArrayList<>();
		this.webSocket = webSocket;
		LOGGER.info("RPC: START");
		webSocket.addHandler(this);
	}

	/**
	 * 通信先に対し、要求を行う
	 *
	 * @param method
	 * 		メソッド名
	 * @param params
	 * 		パラメータ
	 * @param callback
	 * 		応答を受け取るコールバック
	 */
	public void request(String method, JsonValue params, int timeout, Callback<JsonValue> callback) {
		JsonContext b = Factory.newInstance(JsonContext.class, "{}");
		b.setString(KEY_JSONRPC, "2.0");
		b.setString(KEY_METHOD, method);
		if (params != null) {
			b.setValue(KEY_PARAMS, params);
		} else {
			b.setNull(KEY_PARAMS);
		}
		int id = takeNumber();
		b.setInt(KEY_ID, id);
		String msg = b.toString();
		addWaiting(id, new RequestInfo(method, callback, timeout));
		try {
			send(id, msg);
			LOGGER.info("RPC: SEND REQ %s with %s", getShortName(method, id), params);
			if (timeout > 0) {
				DelayedExecuter context = Factory.getInstance(DelayedExecuter.class);
				context.runAfter(timeout, () -> {
					List<RequestInfo> timeouts = removeTimeoutWaitings();
					for (RequestInfo req : timeouts) {
						try {
							req.callback.onDone(new Result<>(new TimeoutException()));
						} catch (Exception err) {
							LOGGER.error(err, "RPC CALLBACK ERROR");
						}
					}
				});
			}
		} catch (IOException e) {
			RequestInfo req = removeWaiting(id);
			LOGGER.error(e, "RPC: SEND REQ %s ERROR", getShortName(method, id));
			try {
				req.callback.onDone(new Result<>(e));
			} catch (Exception err) {
				LOGGER.error(err, "RPC CALLBACK ERROR");
			}
		}
	}

	private synchronized void addWaiting(int id, RequestInfo info) {
		waitings.put(id, info);
	}

	private synchronized RequestInfo removeWaiting(int id) {
		return waitings.remove(id);
	}

	private synchronized List<RequestInfo> removeTimeoutWaitings() {
		long now = System.currentTimeMillis();
		List<RequestInfo> timeouts = new ArrayList<>();
		Iterator<Map.Entry<Integer, RequestInfo>> itr = waitings.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<Integer, RequestInfo> e = itr.next();
			RequestInfo req = e.getValue();
			if (req.isTimeout(now)) {
				timeouts.add(req);
				itr.remove();
			}
		}
		return timeouts;
	}

	/**
	 * 通信先に対し、通知を行う。
	 *
	 * @param method
	 * 		メソッド名
	 * @param params
	 * 		パラメータ
	 */
	public void notify(String method, JsonValue params) {
		JsonContext b = Factory.newInstance(JsonContext.class, "{}");
		b.setString(KEY_JSONRPC, "2.0");
		b.setString(KEY_METHOD, method);
		if (params != null) {
			b.setValue(KEY_PARAMS, params);
		} else {
			b.setNull(KEY_PARAMS);
		}
		String msg = b.toString();
		try {
			send(NOTIFY_ID, msg);
			LOGGER.info("RPC: SEND NTF %s with %s", getShortName(method, NOTIFY_ID), params);
		} catch (IOException e) {
			LOGGER.error(e, "RPC: SEND NTF %s ERROR", getShortName(method, NOTIFY_ID));
		}
	}

	private void send(int id, String msg) throws IOException {
		switch (webSocket.getState()) {
		case DISCONNECT:
		case CONNECTING:
			sendQueue.add(new QueueItem(id, msg));
			break;
		case OPEN:
			webSocket.send(msg);
			break;
		case CLOSING:
		case CLOSED:
		default:
			throw new IllegalStateException();
		}
	}

	/**
	 * 通信先からの要求／通知を処理するハンドラを登録する
	 *
	 * @param method
	 * 		メソッド名
	 * @param handler
	 * 		処理ハンドラ
	 */
	public void addHandler(String method, Handler handler) {
		handlers.put(method, handler);
	}

	/**
	 * 通信先からの要求／通知を処理するハンドラを登録解除する
	 *
	 * @param method
	 * 		メソッド名
	 */
	public void removeHandler(String method) {
		handlers.remove(method);
	}

	@Override
	public void onOpen(WebSocket ws) {
		LOGGER.info("RPC: ON_OPEN %s", ws);
		for (QueueItem item : sendQueue) {
			try {
				webSocket.send(item.message);
			} catch (IOException e) {
				if (item.id != NOTIFY_ID) {
					RequestInfo req = removeWaiting(item.id);
					LOGGER.error(e, "RPC: SEND REQ %s ERROR", getShortName(req.method, item.id));
					try {
						req.callback.onDone(new Result<>(e));
					} catch (Exception err) {
						LOGGER.error(err, "RPC CALLBACK ERROR");
					}
				}
			}
		}
		sendQueue.clear();
	}

	@Override
	public void onError(WebSocket ws) {
		LOGGER.warn("RPC: ON_ERRROR");
	}

	@Override
	public void onClose(WebSocket ws) {
		LOGGER.info("RPC: ON_CLOSE");
	}

	@Override
	public void onMessage(WebSocket ws, String msg) {
		JsonContext b = Factory.newInstance(JsonContext.class, msg);
		String ver = b.getString(KEY_JSONRPC);
		if (!(ver != null && ver.equals("2.0"))) {
			LOGGER.error("RPC: JSONRPC ERROR");
		}
		String method = b.getString(KEY_METHOD, UNKNOWN_METHOD);
		int id = b.getInt(KEY_ID, NOTIFY_ID);
		JsonValue result = b.getValue(KEY_RESULT);
		JsonValue error = b.getValue(KEY_ERROR);
		if (!method.equals(UNKNOWN_METHOD)) {
			//要求又は通知メッセージ。
			JsonValue params = b.getValue(KEY_PARAMS);
			Handler handler = handlers.get(method);
			if (id != NOTIFY_ID) {
				LOGGER.info("RPC: RECV REQ %s with %s", getShortName(method, id), params);
			} else {
				LOGGER.info("RPC: RECV NTF %s with %s", getShortName(method, id), params);
			}
			if (handler != null) {
				invokeHandler(method, params, handler, id);
			} else {
				if (id != NOTIFY_ID) {
					LOGGER.error("RPC: RECV REQ %s IGNORED", getShortName(method, id));
				} else {
					LOGGER.error("RPC: RECV NTF %s IGNORED", getShortName(method, id));
				}
			}
		} else if (id != NOTIFY_ID) {
			//応答メッセージ
			RequestInfo req = removeWaiting(id);
			if (req != null) {
				method = req.method;
				if (!result.isUndefined()) {
					LOGGER.info("RPC: RECV RSP %s with %s", getShortName(method, id), result);
					try {
						req.callback.onDone(new Result<>(result));
					} catch (Exception err) {
						LOGGER.error(err, "RPC: CALLBACK ERROR");
					}
				} else {
					String errMsg = error.isUndefined() ? "" : error.toJsonString();
					LOGGER.error("RPC: RECV RSP %s error %s", getShortName(method, id), errMsg);
					try {
						req.callback.onDone(new Result<>(new JsonRpcException(errMsg)));
					} catch (Exception err) {
						LOGGER.error(err, "RPC: CALLBACK ERROR");
					}
				}
			} else {
				LOGGER.error("RPC: RECV RSP %s", getShortName("Missing", id));
			}
		} else {
			LOGGER.error("RPC: RECV UNKNOWN MESSAGE %s", msg);
		}
	}

	private void invokeHandler(String method, JsonValue params, Handler handler, int id) {
		if (id != 0) {
			handler.handleRequest(params, new RequestCallback(method, id));
		} else {
			handler.handleNotify(params);
		}
	}

	private class RequestCallback implements Callback<JsonValue> {

		private final String method;

		private final int id;

		private RequestCallback(String method, int id) {
			this.method = method;
			this.id = id;
		}

		public void onDone(Result<JsonValue> result) {
			if (id == NOTIFY_ID) {
				return;
			}
			JsonContext b = Factory.newInstance(JsonContext.class, "{}");
			b.setString("jsonrpc", "2.0");
			b.setInt("id", id);
			try {
				JsonValue value = result.get();
				b.setValue("result", value);
				LOGGER.info("RPC: SEND RSP %s with %s", getShortName(method, id), value.toJsonString());
			} catch (Exception err) {
				String message = err.getMessage();
				b.setValue("error", message);
				LOGGER.info("RPC: SEND RSP %s failure %s", getShortName(method, id), message);
			}
			String msg = b.toString();
			try {
				webSocket.send(msg);
			} catch (IOException e) {
				LOGGER.error(e, "RPC: SEND RSP %s ERROR", getShortName(method, id));
			}
		}
	}

	private String getShortName(String fullName, int id) {
		int index = fullName.lastIndexOf('.');
		return fullName.substring(index + 1) + "#" + id;
	}

}
