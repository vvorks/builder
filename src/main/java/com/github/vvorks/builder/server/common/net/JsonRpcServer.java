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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.vvorks.builder.common.io.Closeables;
import com.github.vvorks.builder.common.json.JsonRpcConstants;
import com.github.vvorks.builder.common.lang.Asserts;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.util.Logger;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcController;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcMethod;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcParam;
import com.github.vvorks.builder.server.common.util.Invoker;

public class JsonRpcServer extends TextWebSocketHandler implements JsonRpcConstants {

	private static final Class<?> THIS = JsonRpcServer.class;
	private static final Logger LOGGER = Factory.newInstance(Logger.class, THIS);

	private static final String[] VERSIONS = {"2.0"};
	private static final Set<String> ACCEPTABLE_JSONRPC_VERSIONS = new HashSet<>(Arrays.asList(VERSIONS));

	private ApplicationContext context;

	private List<WebSocketSession> opens = new ArrayList<>();

	private Map<String, Invoker> methodMap = new HashMap<>();

	private ObjectMapper mapper = new ObjectMapper();

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
		} else if (params.length == 1 && params[0].getType() == JsonNode.class) {
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
		JsonNode req = loadRequest(payload);
		if (req == null) {
			//要求元が正当なJsonRpcClientとは考えられないので直ちにセッションをクローズ
			LOGGER.error("KILL(%s)", session.getId());
			Closeables.close(session);
			return;
		}
		//JsonRpcリクエストをディスパッチ
		ObjectNode res = mapper.createObjectNode();
		res.set(KEY_JSONRPC, req.get(KEY_JSONRPC));
		res.set(KEY_ID, req.get(KEY_ID));
		URI uri = session.getUri();
		String uriStr = uri != null ? uri.toString() : "/";
		String path = uriStr.substring(uriStr.lastIndexOf('/'));
		LOGGER.info("path %s", path);
		String methodName = req.get(KEY_METHOD).asText();
		String key = path + "/" + methodName;
		Invoker invoker = methodMap.get(key);
		if (invoker == null) {
			//対象メソッドがない場合
			setError(res, METHOD_NOT_FOUND, String.format("Method '%s' not found.", methodName));
		} else {
			try {
				JsonNode params = req.get(KEY_PARAMS);
				List<Parameter> ps = invoker.getParameters();
				Object ret;
				if (ps.isEmpty()) {
					ret = invoker.invoke();
				} else if (ps.size() == 1 && ps.get(0).getType() == JsonNode.class) {
					ret = invoker.invoke(params);
				} else {
					Object[] paramObjs = mapParams(params, invoker);
					ret = invoker.invoke(paramObjs);
				}
				res.set(KEY_RESULT, toJsonNode(ret));
			} catch (IOException err) {
				setError(res, INVALID_PARAMS, err);
			} catch (RuntimeException err) {
				setError(res, INTERNAL_ERROR, err.getCause());
			}
		}
		try {
			String resp = res.toString();
			TextMessage outputMessage = new TextMessage(resp);
			session.sendMessage(outputMessage);
			LOGGER.debug("SEND(%s) %s", session.getId(), resp);
		} catch (IOException err) {
			LOGGER.error(err, "ERROR AT SEND RESPONSE");
			Closeables.close(session);
		}
	}

	private JsonNode loadRequest(String jsonString) {
		try {
			//正しいJsonRpcリクエストか確認
			Asserts.requireNotEmpty(jsonString);
			JsonNode req = mapper.readTree(jsonString);
			JsonNode versionNode = req.get(KEY_JSONRPC);
			Asserts.requireNotNull(versionNode);
			Asserts.require(versionNode.isTextual());
			String version = versionNode.asText();
			Asserts.require(ACCEPTABLE_JSONRPC_VERSIONS.contains(version));
			JsonNode idNode = req.get(KEY_ID);
			Asserts.requireNotNull(idNode);
			JsonNode methodNode = req.get(KEY_METHOD);
			Asserts.requireNotNull(methodNode);
			Asserts.require(methodNode.isTextual());
			return req;
		} catch (JsonProcessingException|RuntimeException err) {
			LOGGER.error(err, "ERROR loadRequest");
			return null;
		}
	}

	private Object[] mapParams(JsonNode params, Invoker invoker) throws IOException {
		List<Parameter> args = invoker.getParameters();
		Object[] objs = new Object[args.size()];
		for (int i = 0; i < args.size(); i++) {
			Parameter arg = args.get(i);
			String paramName = arg.getAnnotation(JsonRpcParam.class).value();
			JsonNode param = params.get(paramName);
			objs[i] = toPojo(param, arg.getType());
		}
		return objs;
	}

	private <T> T toPojo(JsonNode node, Class<T> cls) throws IOException {
		return mapper.readValue(mapper.treeAsTokens(node), cls);
	}

	private JsonNode toJsonNode(Object obj) {
		if (obj instanceof JsonNode) {
			return (JsonNode) obj;
		} else {
			return mapper.convertValue(obj, JsonNode.class);
		}
	}

	private void setError(ObjectNode res, int errorCode, String errorMessage) {
		ObjectNode error = mapper.createObjectNode();
		error.put(KEY_CODE, errorCode);
		error.put(KEY_MESSAGE, errorMessage);
		res.set(KEY_ERROR, error);
	}

	private void setError(ObjectNode res, int errorCode, Throwable err) {
		ObjectNode error = mapper.createObjectNode();
		error.put(KEY_CODE, errorCode);
		String msg = err.getMessage();
		String errorMessage = (msg == null || msg.isEmpty()) ? err.getClass().getName() : msg;
		error.put(KEY_MESSAGE, errorMessage);
		res.set(KEY_ERROR, error);
	}

}
