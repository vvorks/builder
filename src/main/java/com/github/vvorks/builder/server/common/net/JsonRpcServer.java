package com.github.vvorks.builder.server.common.net;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.github.vvorks.builder.common.io.Closeables;
import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Asserts;
import com.github.vvorks.builder.common.logging.Logger;
import com.github.vvorks.builder.common.net.JsonRpcConstants;
import com.github.vvorks.builder.common.net.JsonRpcs;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcController;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcMethod;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcParam;
import com.github.vvorks.builder.server.common.util.Invoker;

public class JsonRpcServer extends TextWebSocketHandler implements JsonRpcConstants {

	public static final Class<?> THIS = JsonRpcServer.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	private static final String[] VERSIONS = {"2.0"};
	private static final Set<String> ACCEPTABLE_JSONRPC_VERSIONS = new HashSet<>(Arrays.asList(VERSIONS));

	private ApplicationContext context;

	private List<WebSocketSession> opens = new ArrayList<>();

	private Map<String, Invoker> methodMap = new HashMap<>();

	@Autowired
	public void context(ApplicationContext context) {
		this.context = context;
		loadControllers();
	}

	private void loadControllers() {
		Map<String, Object> beans = context.getBeansWithAnnotation(JsonRpcController.class);
		for (Map.Entry<String, Object> e : beans.entrySet()) {
			String path = e.getKey();
			Object controller = e.getValue();
			Method[] methods = controller.getClass().getMethods();
			for (Method m : methods) {
				JsonRpcMethod a = m.getDeclaredAnnotation(JsonRpcMethod.class);
				if (a != null && isInvocable(m)) {
					registerMethods(controller, m, path, a);
				}
			}
		}
	}

	private boolean isInvocable(Method method) {
		Parameter[] params = method.getParameters();
		if (params.length == 0) {
			return true;
		} else if (params.length == 1 && params[0].getType() == Json.class) {
			return true;
		} else {
			for (Parameter p : params) {
				JsonRpcParam a = p.getAnnotation(JsonRpcParam.class);
				if (a == null || a.value() == null || a.value().isEmpty()) {
					return false;
				}
			}
			return true;
		}
	}

	private void registerMethods(Object controller, Method method, String path, JsonRpcMethod a) {
		String[] value = a.value();
		if (value == null || value.length == 0) {
			String name = method.getName();
			registerMethod(controller, method, path, name);
		} else {
			for (String v : a.value()) {
				String name = (v == null || v.isEmpty()) ? method.getName() : v;
				registerMethod(controller, method, path, name);
			}
		}
	}

	private void registerMethod(Object controller, Method method, String path, String name) {
		String key = path + "/" + name;
		Invoker prevInfo = methodMap.get(key);
		if (prevInfo != null) {
			LOGGER.warn("%s ignored", method);
		} else {
			LOGGER.debug("%s mapped to path %s method %s", method, path, name);
			methodMap.put(key, new Invoker(controller, method));
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		opens.add(session);
		LOGGER.debug("OPEN %d", opens.size());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		opens.remove(session);
		LOGGER.debug("CLOSE %d", opens.size());
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) {
		String payload = message.getPayload();
		LOGGER.debug("RECV(%s) %s", session.getId(), payload);
		Json req = loadRequest(payload);
		if (req == null) {
			//要求元が正当なJsonRpcClientとは考えられないので直ちにセッションをクローズ
			LOGGER.error("KILL(%s)", session.getId());
			Closeables.close(session);
			return;
		}
		//JsonRpcリクエストをディスパッチ
		Json res = Json.createObject();
		res.setString(KEY_JSONRPC, req.getString(KEY_JSONRPC));
		res.setInt(KEY_ID, req.getInt(KEY_ID));
		URI uri = session.getUri();
		String uriStr = uri != null ? uri.toString() : "/";
		String path = uriStr.substring(uriStr.lastIndexOf('/'));
		LOGGER.info("path %s", path);
		String methodName = req.getString(KEY_METHOD);
		String key = path + "/" + methodName;
		Invoker invoker = methodMap.get(key);
		if (invoker == null) {
			//対象メソッドがない場合
			setError(res, METHOD_NOT_FOUND, String.format("Method '%s' not found.", methodName));
		} else {
			try {
				Json params = req.get(KEY_PARAMS);
				List<Parameter> ps = invoker.getParameters();
				Object ret;
				if (ps.isEmpty()) {
					ret = invoker.invoke();
				} else if (ps.size() == 1 && ps.get(0).getType() == Json.class) {
					ret = invoker.invoke(params);
				} else {
					Object[] paramObjs = mapParams(params, invoker);
					ret = invoker.invoke(paramObjs);
				}
				Json retJson = Json.createJson(ret);
				res.set(KEY_RESULT, retJson);
			} catch (RuntimeException err) {
				Throwable cause = err.getCause() != null ? err.getCause() : err;
				LOGGER.error(cause);
				setError(res, INTERNAL_ERROR, cause);
			}
		}
		try {
			String resp = res.toJsonString();
			TextMessage outputMessage = new TextMessage(resp);
			session.sendMessage(outputMessage);
			LOGGER.debug("SEND(%s) %s", session.getId(), resp);
		} catch (IOException err) {
			LOGGER.error(err, "ERROR AT SEND RESPONSE");
			Closeables.close(session);
		}
	}

	private Json loadRequest(String jsonString) {
		try {
			//正しいJsonRpcリクエストか確認
			Asserts.requireNotEmpty(jsonString);
			Json req = Json.createJson(jsonString);
			String version = req.getString(KEY_JSONRPC, null);
			Asserts.require(ACCEPTABLE_JSONRPC_VERSIONS.contains(version));
			double id = req.getDouble(KEY_ID, Double.NaN);
			Asserts.require(!Double.isNaN(id));
			String methodName = req.getString(KEY_METHOD, null);
			Asserts.requireNotNull(methodName);
			return req;
		} catch (RuntimeException err) {
			LOGGER.error(err, "ERROR loadRequest");
			return null;
		}
	}

	private Object[] mapParams(Json params, Invoker invoker) {
		List<Parameter> args = invoker.getParameters();
		Object[] objs = new Object[args.size()];
		for (int i = 0; i < args.size(); i++) {
			Parameter arg = args.get(i);
			String paramName = arg.getAnnotation(JsonRpcParam.class).value();
			Json param = params.get(paramName);
			objs[i] = param.toObject(arg.getType());
		}
		return objs;
	}

	private void setError(Json res, int errorCode, String errorMessage) {
		Json error = res.setNewObject(JsonRpcs.KEY_ERROR);
		error.setInt(JsonRpcs.KEY_CODE, errorCode);
		error.setString(JsonRpcs.KEY_MESSAGE, errorMessage);
	}

	private void setError(Json res, int errorCode, Throwable err) {
		Json error = res.setNewObject(JsonRpcs.KEY_ERROR);
		error.setInt(KEY_CODE, errorCode);
		String msg = err.getMessage();
		String errorMessage = (msg == null || msg.isEmpty()) ? err.getClass().getName() : msg;
		error.setString(KEY_MESSAGE, errorMessage);
	}

}
