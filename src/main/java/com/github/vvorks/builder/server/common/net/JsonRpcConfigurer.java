package com.github.vvorks.builder.server.common.net;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.util.Logger;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcController;

@Configuration
@EnableWebSocket
public class JsonRpcConfigurer implements WebSocketConfigurer {

	private static final Class<?> THIS = JsonRpcConfigurer.class;
	private static final Logger LOGGER = Factory.newInstance(Logger.class, THIS);

	@Autowired
	private ApplicationContext context;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		//登録されたJsonRpcControllerを列挙し、パス集合を作成する
		Set<String> pathSet = new HashSet<>();
		Map<String, Object> beans = context.getBeansWithAnnotation(JsonRpcController.class);
		for (Map.Entry<String, Object> e : beans.entrySet()) {
			String path = e.getKey();
			pathSet.add(path);
		}
		//サーバーとパスとのバインド
		JsonRpcServer server = jsonRpcServer();
		for (String path : pathSet) {
			registry.addHandler(server, path);
			LOGGER.debug("BIND PATH %s on WebSocketServer", path);
		}
	}

	@Bean
	public JsonRpcServer jsonRpcServer() {
		return new JsonRpcServer();
	}

}
