package com.github.vvorks.builder.server.controller;

import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.util.Logger;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcController;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcMethod;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcParam;
import com.github.vvorks.builder.server.grammar.ExprNode;
import com.github.vvorks.builder.server.grammar.ExprParser;
import com.github.vvorks.builder.server.grammar.ParseException;

@JsonRpcController("/rpc")
public class RpcController {

	private static final Class<?> THIS = RpcController.class;
	private static final Logger LOGGER = Factory.newInstance(Logger.class, THIS);

//	@Autowired
//	private JsonRpcServer server;

	@JsonRpcMethod("subtract")
	public int test(@JsonRpcParam("x") int x, @JsonRpcParam("y") int y) {
		return x - y;
	}

	@JsonRpcMethod("parse")
	public String parse(@JsonRpcParam("code") String code) {
		ExprParser parser = new ExprParser();
		String result;
		try {
			ExprNode node = parser.parse(code);
			result = node.dump("\t", 0);
		} catch (ParseException err) {
			LOGGER.error(err);
			result = err.getLocalizedMessage();
		}
		return result;
	}

}
