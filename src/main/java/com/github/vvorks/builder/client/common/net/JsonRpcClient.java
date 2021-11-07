package com.github.vvorks.builder.client.common.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Callback;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.logging.Logger;
import com.github.vvorks.builder.common.net.JsonRpcs;
import com.github.vvorks.builder.common.util.DelayedExecuter;
import com.github.vvorks.builder.common.util.TimeoutException;

/**
 * WebSocket上でJsonRpc通信を行うサービスクラス
 */
public class JsonRpcClient implements WebSocketHandler {

	public static final Class<?> THIS = JsonRpcClient.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	private static final String UNKNOWN_METHOD = "__UNKNOWN__";

	private static final String RPC_VERSION = "2.0";

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
		private final Callback<Json> callback;
		private final long absTimeout;
		private RequestInfo(String method, Callback<Json> callback, int timeout) {
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
		public void handleRequest(Json params, Callback<Json> callback);
		public void handleNotify(Json params);
	}

	/**
	 * ハンドラアダプタ
	 */
	public static class Adapter implements Handler {
		public void handleRequest(Json params, Callback<Json> callback) {
			//nop
		}
		public void handleNotify(Json params) {
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
	public void request(String method, Json params, int timeout, Callback<Json> callback) {
		Json json = Json.createObject();
		json.setString(JsonRpcs.KEY_JSONRPC, RPC_VERSION);
		json.setString(JsonRpcs.KEY_METHOD, method);
		if (params != null) {
			json.set(JsonRpcs.KEY_PARAMS, params);
		} else {
			json.setNull(JsonRpcs.KEY_PARAMS);
		}
		int id = takeNumber();
		json.setNumber(JsonRpcs.KEY_ID, id);
		String msg = json.toJsonString();
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
							req.callback.onFailure(new TimeoutException());
						} catch (Exception err) {
							LOGGER.error(err, "RPC CALLBACK ERROR ON REQUEST TIMEOUT");
						}
					}
				});
			}
		} catch (IOException e) {
			RequestInfo req = removeWaiting(id);
			LOGGER.error(e, "RPC: SEND REQ %s ERROR", getShortName(method, id));
			try {
				req.callback.onFailure(e);
			} catch (Exception err) {
				LOGGER.error(err, "RPC CALLBACK ERROR ON REQUEST");
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
	public void notify(String method, Json params) {
		Json json = Json.createObject();
		json.setString(JsonRpcs.KEY_JSONRPC, RPC_VERSION);
		json.setString(JsonRpcs.KEY_METHOD, method);
		if (params != null) {
			json.set(JsonRpcs.KEY_PARAMS, params);
		} else {
			json.setNull(JsonRpcs.KEY_PARAMS);
		}
		String msg = json.toString();
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
			} catch (IOException err) {
				if (item.id != NOTIFY_ID) {
					RequestInfo req = removeWaiting(item.id);
					LOGGER.error(err, "RPC: SEND REQ %s ERROR", getShortName(req.method, item.id));
					try {
						req.callback.onFailure(err);
					} catch (Exception err2) {
						LOGGER.error(err2, "RPC CALLBACK ERROR ON OPEN");
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
		Json json = Json.createJson(msg);
		String ver = json.getString(JsonRpcs.KEY_JSONRPC);
		if (!(ver != null && ver.equals(RPC_VERSION))) {
			LOGGER.error("RPC: JSONRPC ERROR");
		}
		String method = json.getString(JsonRpcs.KEY_METHOD, UNKNOWN_METHOD);
		int id = (int) json.getNumber(JsonRpcs.KEY_ID, NOTIFY_ID);
		if (!method.equals(UNKNOWN_METHOD)) {
			//要求又は通知メッセージ。
			Json params = json.get(JsonRpcs.KEY_PARAMS);
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
					responseError(JsonRpcs.METHOD_NOT_FOUND, method, id);
				} else {
					LOGGER.error("RPC: RECV NTF %s IGNORED", getShortName(method, id));
				}
			}
		} else if (id != NOTIFY_ID) {
			//応答メッセージ
			Json result = json.get(JsonRpcs.KEY_RESULT);
			Json error = json.get(JsonRpcs.KEY_ERROR);
			RequestInfo req = removeWaiting(id);
			if (req != null) {
				method = req.method;
				if (result != null) {
					LOGGER.info("RPC: RECV RSP %s with %s", getShortName(method, id), result);
					try {
						req.callback.onSuccess(result);
					} catch (Exception err) {
						LOGGER.error(err, "RPC: CALLBACK ERROR");
					}
				} else {
					String errMsg = error == null ? "" : error.toJsonString();
					LOGGER.error("RPC: RECV RSP %s error %s", getShortName(method, id), errMsg);
					try {
						req.callback.onFailure(new JsonRpcException(errMsg));
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

	private void invokeHandler(String method, Json params, Handler handler, int id) {
		if (id != 0) {
			handler.handleRequest(params, new RequestCallback(method, id));
		} else {
			handler.handleNotify(params);
		}
	}

	private class RequestCallback implements Callback<Json> {

		private final String method;

		private final int id;

		private RequestCallback(String method, int id) {
			this.method = method;
			this.id = id;
		}

		public void onSuccess(Json result) {
			Json json = Json.createObject();
			json.setString(JsonRpcs.KEY_JSONRPC, RPC_VERSION);
			json.setNumber(JsonRpcs.KEY_ID, id);
			json.set(JsonRpcs.KEY_RESULT, result);
			LOGGER.info("RPC: SEND RSP %s with %s", getShortName(method, id), result.toJsonString());
			sendResponse(json);
		}

		public void onFailure(Throwable err) {
			Json json = Json.createObject();
			json.setString(JsonRpcs.KEY_JSONRPC, RPC_VERSION);
			json.setNumber(JsonRpcs.KEY_ID, id);
			Json ej = json.setNewObject(JsonRpcs.KEY_ERROR);
			ej.setNumber(JsonRpcs.KEY_CODE, JsonRpcs.getErrorCode(err));
			ej.setString(JsonRpcs.KEY_MESSAGE, err.getMessage());
			LOGGER.info("RPC: SEND RSP %s failure %s", getShortName(method, id), err.getMessage());
			sendResponse(json);
		}

		private void sendResponse(Json json) {
			try {
				String msg = json.toString();
				webSocket.send(msg);
			} catch (IOException e) {
				LOGGER.error(e, "RPC: SEND RSP %s ERROR", getShortName(method, id));
			}

		}
	}

	private void responseError(int errorCode, String method, int id) {
		Json json = Json.createObject();
		json.setString(JsonRpcs.KEY_JSONRPC, RPC_VERSION);
		json.setNumber(JsonRpcs.KEY_ID, id);
		Json ej = json.setNewObject(JsonRpcs.KEY_ERROR);
		ej.setNumber(JsonRpcs.KEY_CODE, errorCode);
		ej.setString(JsonRpcs.KEY_MESSAGE, method);
		String msg = json.toString();
		try {
			webSocket.send(msg);
		} catch (IOException e) {
			LOGGER.error(e, "RPC: SEND RSP ERROR", getShortName(method, id));
		}
	}

	private String getShortName(String fullName, int id) {
		int index = fullName.lastIndexOf('.');
		return fullName.substring(index + 1) + "#" + id;
	}

}
